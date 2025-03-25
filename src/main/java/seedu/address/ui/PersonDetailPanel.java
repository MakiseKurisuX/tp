package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A UI component that displays detailed information of a {@code Person}.
 */
public class PersonDetailPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailPanel.fxml";

    @FXML
    private Label policyLabel;

    @FXML
    private Label renewalDateLabel;

    public PersonDetailPanel() {
        super(FXML);
    }

    /**
     * Updates the panel to show the selected person's details.
     *
     * @param person the selected person.
     */
    public void setPerson(Person person) {
        policyLabel.setText(person.getPolicy().policyNumber);
        renewalDateLabel.setText(person.getRenewalDate());
    }
}
