package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.util.FeedbackFormatter;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        String formattedFeedback = FeedbackFormatter.formatFeedback(feedbackToUser);
        resultDisplay.setText(formattedFeedback);
    }

    /**
     * Gets the current feedback text.
     */
    public String getFeedback() {
        return resultDisplay.getText();
    }
}
