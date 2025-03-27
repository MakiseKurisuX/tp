package seedu.address.ui;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import seedu.address.model.Model;
import seedu.address.model.RenewalProcessor;
import seedu.address.model.RenewalProcessor.RenewalEntry;
import seedu.address.model.RenewalTableData;

/**
 * Panel containing the list of upcoming policy renewals.
 */
public class RenewalsTable extends UiPart<VBox> {

    private static final String FXML = "RenewalsTable.fxml";

    @FXML
    private TableView<RenewalEntry> renewalsTable;

    @FXML
    private TableColumn<RenewalEntry, String> clientColumn;

    @FXML
    private TableColumn<RenewalEntry, String> policyColumn;

    @FXML
    private TableColumn<RenewalEntry, LocalDate> renewalDateColumn;

    @FXML
    private TableColumn<RenewalEntry, Long> daysLeftColumn;

    @FXML
    private TableColumn<RenewalEntry, String> typeColumn;

    @FXML
    private TableColumn<RenewalEntry, String> contactColumn;

    /**
     * Creates a new RenewalsTable with the given Model.
     *
     * @param model The model containing the person data
     */
    public RenewalsTable(Model model) {
        super(FXML);
        setupColumns();
        updateRenewals(model);
    }

    /**
     * Sets up the table columns with their respective cell value factories.
     */
    private void setupColumns() {
        RenewalTableConfig.configureAllColumns(
            clientColumn,
            policyColumn,
            renewalDateColumn,
            daysLeftColumn,
            typeColumn,
            contactColumn
        );
    }

    /**
     * Updates the table with renewal entries from the model.
     *
     * @param model The model containing the updated person data
     */
    public void updateRenewals(Model model) {
        RenewalTableData tableData = RenewalProcessor.processRenewals(model.getRenewalsList());
        renewalsTable.setItems(FXCollections.observableArrayList(tableData.getEntries()));
    }

    /**
     * Shows the renewals table.
     */
    public void show() {
        getRoot().setVisible(true);
        getRoot().setManaged(true);
    }

    /**
     * Hides the renewals table.
     */
    public void hide() {
        getRoot().setVisible(false);
        getRoot().setManaged(false);
    }

    /**
     * Gets the renewals table for testing.
     *
     * @return The TableView containing the renewal entries
     */
    protected TableView<RenewalEntry> getRenewalsTable() {
        return renewalsTable;
    }
}
