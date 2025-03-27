package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.RenewalDate;
import seedu.address.testutil.PersonBuilder;

public class FilterDateCommandTest {
    private Model model;
    private Person alice;
    private Person bob;
    private Person charlie;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        alice = new PersonBuilder().withName("Alice")
                .withPhone("85355255")
                .withEmail("alice@gmail.com")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withPolicy("12345", LocalDate.of(2025, 3, 10).format(RenewalDate.DATE_FORMATTER)).build();
        bob = new PersonBuilder().withName("Bob")
                .withPhone("85355255")
                .withEmail("bob@gmail.com")
                .withAddress("456, Clementi Ave 3, #05-333")
                .withPolicy("67890", LocalDate.of(2025, 3, 20).format(RenewalDate.DATE_FORMATTER)).build();
        charlie = new PersonBuilder().withName("Charlie")
                .withPhone("85555255")
                .withEmail("charlie@gmail.com")
                .withAddress("789, Tampines St 12, #07-222")
                .withPolicy("11111", LocalDate.of(2025, 3, 30).format(RenewalDate.DATE_FORMATTER)).build();
    }

    @Test
    public void execute_emptyModel_noResults() {
        LocalDate startDate = LocalDate.of(2025, 3, 1);
        LocalDate endDate = LocalDate.of(2025, 3, 31);
        FilterDateCommand command = new FilterDateCommand(startDate, endDate, "date");
        CommandResult result = command.execute(model);
        assertEquals(String.format(FilterDateCommand.MESSAGE_NO_RESULTS, startDate, endDate),
                result.getFeedbackToUser());
        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_validDateRange_filtersCorrectly() {
        model.addPerson(alice);
        model.addPerson(bob);
        model.addPerson(charlie);

        LocalDate startDate = LocalDate.of(2025, 3, 1);
        LocalDate endDate = LocalDate.of(2025, 3, 20);

        FilterDateCommand command = new FilterDateCommand(startDate, endDate, "date");
        CommandResult result = command.execute(model);

        assertEquals(String.format(FilterDateCommand.MESSAGE_FILTER_SUCCESS, 2, startDate, endDate),
                result.getFeedbackToUser());
        List<Person> filteredList = model.getRenewalsList();
        assertEquals(2, filteredList.size());
        assertTrue(filteredList.contains(alice));
        assertTrue(filteredList.contains(bob));
        assertFalse(filteredList.contains(charlie));
    }

    @Test
    public void execute_sortByName_sortsCorrectly() {
        model.addPerson(bob);
        model.addPerson(alice);
        model.addPerson(charlie);

        FilterDateCommand command = new FilterDateCommand(LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 31), "name");
        command.execute(model);

        List<Person> filteredList = model.getRenewalsList();
        assertEquals(3, filteredList.size());
        assertEquals(alice, filteredList.get(0));
        assertEquals(bob, filteredList.get(1));
        assertEquals(charlie, filteredList.get(2));
    }

    @Test
    public void execute_sortByDate_sortsCorrectly() {
        model.addPerson(charlie);
        model.addPerson(bob);
        model.addPerson(alice);

        FilterDateCommand command = new FilterDateCommand(LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 31), "date");
        command.execute(model);

        List<Person> filteredList = model.getRenewalsList();
        assertEquals(3, filteredList.size());
        assertEquals(alice, filteredList.get(0));
        assertEquals(bob, filteredList.get(1));
        assertEquals(charlie, filteredList.get(2));
    }

    @Test
    public void execute_noResults_returnsNoResults() {
        Person futurePerson = new PersonBuilder().withName("Future")
                .withPhone("88888888")
                .withEmail("future@gmail.com")
                .withAddress("Future Avenue")
                .withPolicy("99999", LocalDate.of(2030, 1, 1).format(RenewalDate.DATE_FORMATTER)).build();
        model.addPerson(futurePerson);

        LocalDate startDate = LocalDate.of(2025, 3, 1);
        LocalDate endDate = LocalDate.of(2025, 3, 31);

        FilterDateCommand command = new FilterDateCommand(startDate, endDate, null);
        CommandResult result = command.execute(model);

        assertEquals(String.format(FilterDateCommand.MESSAGE_NO_RESULTS, startDate, endDate),
                result.getFeedbackToUser());
        assertTrue(model.getRenewalsList().isEmpty());
    }

    @Test
    public void equals() {
        FilterDateCommand command1 = new FilterDateCommand(LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 31), "date");
        FilterDateCommand command2 = new FilterDateCommand(LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 31), "date");
        FilterDateCommand command3 = new FilterDateCommand(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 30), "date");
        FilterDateCommand command4 = new FilterDateCommand(LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 31), "name");

        assertTrue(command1.equals(command1)); //same object
        assertTrue(command1.equals(command2));
        assertFalse(command1.equals(null)); // Null
        assertFalse(command1.equals(new ClearCommand())); // Different type
        assertFalse(command1.equals(command3)); // Different date range
        assertFalse(command1.equals(command4)); // Different sorting
    }
}
