package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.RenewalProcessor.RenewalEntry;
import seedu.address.testutil.PersonBuilder;

public class RenewalTableDataTest {

    @Test
    public void constructor_withEntries_storesEntriesCorrectly() {
        // Create some entries
        RenewalEntry entry1 = new RenewalEntry(new PersonBuilder().withName("Alice").build());
        RenewalEntry entry2 = new RenewalEntry(new PersonBuilder().withName("Bob").build());
        List<RenewalEntry> entries = Arrays.asList(entry1, entry2);

        RenewalTableData tableData = new RenewalTableData(entries);
        assertNotNull(tableData.getEntries());
        assertEquals(2, tableData.getEntries().size());
        assertSame(entries, tableData.getEntries(), "Should return the same list instance");
    }

    @Test
    public void columnConstants_correctValues() {
        assertEquals("client", RenewalTableData.CLIENT_COLUMN);
        assertEquals("policy", RenewalTableData.POLICY_COLUMN);
        assertEquals("renewalDate", RenewalTableData.RENEWAL_DATE_COLUMN);
        assertEquals("daysLeft", RenewalTableData.DAYS_LEFT_COLUMN);
        assertEquals("type", RenewalTableData.TYPE_COLUMN);
        assertEquals("contact", RenewalTableData.CONTACT_COLUMN);
    }
}
