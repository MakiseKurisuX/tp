package seedu.address.model;

import java.util.List;

/**
 * Represents the column structure for renewal data.
 */
public class RenewalTableData {
    public static final String CLIENT_COLUMN = "client";
    public static final String POLICY_COLUMN = "policy";
    public static final String RENEWAL_DATE_COLUMN = "renewalDate";
    public static final String DAYS_LEFT_COLUMN = "daysLeft";
    public static final String TYPE_COLUMN = "type";
    public static final String CONTACT_COLUMN = "contact";

    private final List<RenewalProcessor.RenewalEntry> entries;

    public RenewalTableData(List<RenewalProcessor.RenewalEntry> entries) {
        this.entries = entries;
    }

    public List<RenewalProcessor.RenewalEntry> getEntries() {
        return entries;
    }
}
