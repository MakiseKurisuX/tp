package seedu.address.ui;

import java.time.LocalDate;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import seedu.address.model.RenewalProcessor.RenewalEntry;
import seedu.address.model.RenewalTableData;
import seedu.address.model.person.RenewalDate;

/**
 * JavaFX-specific configuration for the renewals table.
 */
public class RenewalTableConfig {
    /**
     * Configures a column with the given property name.
     *
     * @param column The column to configure
     * @param propertyName The name of the property to bind to
     * @param <T> The type of the property
     */
    private static <T> void configureColumn(TableColumn<RenewalEntry, T> column, String propertyName) {
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
    }

    /**
     * Configures the date column with proper formatting.
     *
     * @param dateColumn The date column to configure
     */
    private static void configureDateColumn(TableColumn<RenewalEntry, LocalDate> dateColumn) {
        configureColumn(dateColumn, RenewalTableData.RENEWAL_DATE_COLUMN);
        dateColumn.setCellFactory(column -> new TableCell<RenewalEntry, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(date.format(RenewalDate.DATE_FORMATTER));
                }
            }
        });
    }

    /**
     * Configures all columns for the renewals table.
     *
     * @param clientColumn Column for client names
     * @param policyColumn Column for policy numbers
     * @param renewalDateColumn Column for renewal dates
     * @param daysLeftColumn Column for days left until renewal
     * @param typeColumn Column for policy types
     * @param contactColumn Column for contact information
     */
    public static void configureAllColumns(
            TableColumn<RenewalEntry, String> clientColumn,
            TableColumn<RenewalEntry, String> policyColumn,
            TableColumn<RenewalEntry, LocalDate> renewalDateColumn,
            TableColumn<RenewalEntry, Long> daysLeftColumn,
            TableColumn<RenewalEntry, String> typeColumn,
            TableColumn<RenewalEntry, String> contactColumn) {
        configureColumn(clientColumn, RenewalTableData.CLIENT_COLUMN);
        configureColumn(policyColumn, RenewalTableData.POLICY_COLUMN);
        configureDateColumn(renewalDateColumn);
        configureColumn(daysLeftColumn, RenewalTableData.DAYS_LEFT_COLUMN);
        configureColumn(typeColumn, RenewalTableData.TYPE_COLUMN);
        configureColumn(contactColumn, RenewalTableData.CONTACT_COLUMN);
    }
}
