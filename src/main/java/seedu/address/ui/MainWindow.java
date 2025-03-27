package seedu.address.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private RenewalsTable renewalsTable;

    private StatusBarFooter statusBarFooter;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane renewalsTablePlaceholder;

    @FXML
    private StackPane personDetailPanelPlaceholder;

    @FXML
    private Button showAllButton;

    @FXML
    private Label filterLabel;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        String lastUpdated = logic.getModel().getAddressBook().getLastUpdatedString();
        int personCount = logic.getFilteredPersonList().size();
        statusBarFooter = new StatusBarFooter(
            logic.getAddressBookFilePath(), lastUpdated, personCount);
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, resultDisplay);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        renewalsTable = new RenewalsTable(logic.getModel());
        renewalsTablePlaceholder.getChildren().add(renewalsTable.getRoot());

        PersonDetailPanel personDetailPanel = new PersonDetailPanel();
        personDetailPanelPlaceholder.getChildren().add(personDetailPanel.getRoot());

        // Observe changes in the filtered person list
        logic.getFilteredPersonList().addListener((ListChangeListener<? super Person>) observable -> {
            boolean isFiltered = logic.getFilteredPersonList().size()
                    < logic.getModel().getAddressBook().getPersonList().size();
            showAllButton.setVisible(isFiltered);
            showAllButton.setManaged(isFiltered);
        });
        personListPanel.getListView().getSelectionModel().selectedItemProperty().addListener((
            observable, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    personDetailPanel.setPerson(newSelection);
                }
            }
        );
        if (personListPanel.getListView().getSelectionModel().getSelectedItem() != null) {
            personDetailPanel.setPerson(personListPanel.getListView().getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    @FXML
    private void handleShowAll() throws CommandException, ParseException {
        executeCommand("list"); // Reset filter to show all people
    }

    /**
     * Updates the filter label with the specified date range.
     * If both startDate and endDate are not null, the label will be set to display
     * the range in the format: "Filtered from [startDate] to [endDate]".
     *
     * @param startDate The start date of the filter range.
     * @param endDate The end date of the filter range.
     */
    public void updateFilterLabel(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            filterLabel.setText("Filtered from " + startDate + " to " + endDate);
        }
    }

    private void updateFilterLabelEmpty() {
        filterLabel.setText("No active filter");

    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandText.startsWith("filter")) {
                String feedback = commandResult.getFeedbackToUser();

                // Check if it contains a valid date range
                if (feedback.contains("between") && feedback.contains(".")) {
                    String[] parts = feedback.split("between|\\.");
                    if (parts.length >= 2) {
                        String[] dates = parts[1].trim().split(" and ");
                        if (dates.length == 2) {
                            LocalDate startDate = LocalDate.parse(dates[0].trim());
                            LocalDate endDate = LocalDate.parse(dates[1].trim());
                            updateFilterLabel(startDate, endDate);
                        }
                    }
                }
            } else {
                updateFilterLabelEmpty();
            }

            // Update renewals table after each command
            renewalsTable.updateRenewals(logic.getModel());

            String newLastUpdated = logic.getModel().getAddressBook().getLastUpdatedString();
            statusBarFooter.updateLastUpdated(newLastUpdated);
            int newPersonCount = logic.getFilteredPersonList().size();
            statusBarFooter.updatePersonCount(newPersonCount);

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
