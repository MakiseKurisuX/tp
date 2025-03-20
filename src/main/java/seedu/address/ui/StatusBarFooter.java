package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;

    @FXML
    private Label lastUpdatedStatus;

    @FXML
    private Label personCountStatus;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(Path saveLocation, String lastUpdated, int personCount) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        lastUpdatedStatus.setText("Last Updated: " + lastUpdated);
        personCountStatus.setText("Person Count: " + personCount);
    }

    /**
     * Updates the displayed last updated timestamp.
     */
    public void updateLastUpdated(String lastUpdated) {
        lastUpdatedStatus.setText("Last Updated: " + lastUpdated);
    }

    /**
     * Updates the displayed person count.
     */
    public void updatePersonCount(int newCount) {
        personCountStatus.setText("Person Count: " + newCount);
    }

}
