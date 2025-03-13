package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.RenewalProcessor.RenewalEntry;
import seedu.address.model.person.Person;
import seedu.address.model.person.Policy;
import seedu.address.testutil.PersonBuilder;

public class RenewalProcessorTest {

    @Test
    public void renewalEntry_createFromPerson_allFieldsCorrect() {
        Person person = new PersonBuilder().build();
        RenewalEntry entry = new RenewalEntry(person);

        assertEquals(person.getName().toString(), entry.getClient());
        assertEquals(person.getPolicy().policyNumber, entry.getPolicy());
        assertEquals(person.getPolicy().renewalDate, entry.getRenewalDate());
        assertEquals(person.getPolicy().getDaysUntilRenewal(), entry.getDaysLeft());
        assertEquals("Life", entry.getType()); // Current placeholder value
        assertEquals(person.getPhone().toString(), entry.getContact());
    }

    @Test
    public void processRenewals_withMultiplePersons_sortedByDaysLeft() {
        // Create persons with different renewal dates
        Person person1 = new PersonBuilder()
                .withName("Alice")
                .withPolicy("12345", LocalDate.now().plusDays(10).format(Policy.DATE_FORMATTER))
                .build();
        Person person2 = new PersonBuilder()
                .withName("Bob")
                .withPolicy("67890", LocalDate.now().plusDays(5).format(Policy.DATE_FORMATTER))
                .build();
        Person person3 = new PersonBuilder()
                .withName("Charlie")
                .withPolicy("11111", LocalDate.now().plusDays(15).format(Policy.DATE_FORMATTER))
                .build();

        List<Person> persons = Arrays.asList(person1, person2, person3);
        RenewalTableData tableData = RenewalProcessor.processRenewals(persons);
        List<RenewalEntry> entries = tableData.getEntries();

        // Check sorting
        assertEquals(3, entries.size());
        assertEquals("Bob", entries.get(0).getClient()); // Closest renewal date
        assertEquals("Alice", entries.get(1).getClient());
        assertEquals("Charlie", entries.get(2).getClient()); // Furthest renewal date
    }

    @Test
    public void processRenewals_withEmptyList_returnsEmptyTableData() {
        RenewalTableData tableData = RenewalProcessor.processRenewals(Arrays.asList());
        assertNotNull(tableData);
        assertEquals(0, tableData.getEntries().size());
    }
}
