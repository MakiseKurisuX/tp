package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.RenewalProcessor.RenewalEntry;
import seedu.address.model.person.Person;
import seedu.address.model.person.RenewalDate;
import seedu.address.testutil.PersonBuilder;

public class RenewalProcessorTest {

    @Test
    public void renewalEntry_createFromPerson_allFieldsCorrect() {
        Person person = new PersonBuilder().build();
        RenewalEntry entry = new RenewalEntry(person);

        assertEquals(person.getName().toString(), entry.getClient());
        assertEquals(person.getPolicy().policyNumber, entry.getPolicy());
        assertEquals(person.getPolicy().renewalDate.value, entry.getRenewalDate());
        assertEquals(person.getPolicy().getDaysUntilRenewal(), entry.getDaysLeft());
        assertEquals("Life", entry.getType()); // Current placeholder value
        assertEquals(person.getPhone().toString(), entry.getContact());
    }

    @Test
    public void processRenewals_withMultiplePersons_preservesOrder() {
        Person person1 = new PersonBuilder()
                .withName("Alice")
                .withPolicy("111111", LocalDate.now().plusDays(10).format(RenewalDate.DATE_FORMATTER))
                .build();
        Person person2 = new PersonBuilder()
                .withName("Bob")
                .withPolicy("222222", LocalDate.now().plusDays(5).format(RenewalDate.DATE_FORMATTER))
                .build();
        Person person3 = new PersonBuilder()
                .withName("Charlie")
                .withPolicy("333333", LocalDate.now().plusDays(15).format(RenewalDate.DATE_FORMATTER))
                .build();

        List<Person> persons = Arrays.asList(person1, person2, person3);
        RenewalTableData tableData = RenewalProcessor.processRenewals(persons);
        List<RenewalEntry> entries = tableData.getEntries();

        // Check that order is preserved
        assertEquals(3, entries.size());
        assertEquals("Alice", entries.get(0).getClient());
        assertEquals("Bob", entries.get(1).getClient());
        assertEquals("Charlie", entries.get(2).getClient());
    }

    @Test
    public void processRenewals_withEmptyList_returnsEmptyTableData() {
        RenewalTableData tableData = RenewalProcessor.processRenewals(Arrays.asList());
        assertNotNull(tableData);
        assertEquals(0, tableData.getEntries().size());
    }
}
