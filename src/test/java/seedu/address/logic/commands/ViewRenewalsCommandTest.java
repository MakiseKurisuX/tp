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

public class ViewRenewalsCommandTest {
    private Model model;
    private Person alice;
    private Person bob;
    private Person charlie;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        alice = new PersonBuilder().withName("Alice")
                .withPhone("85355255")
                .withEmail("amy@gmail.com")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withPolicy("12345", LocalDate.now().plusDays(60).format(RenewalDate.DATE_FORMATTER)).build();
        bob = new PersonBuilder().withName("Bob")
                .withPhone("85355255")
                .withEmail("amy@gmail.com")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withPolicy("67890", LocalDate.now().plusDays(30).format(RenewalDate.DATE_FORMATTER)).build();
        charlie = new PersonBuilder().withName("Charlie")
                .withPhone("85555255")
                .withEmail("email@gmail.com")
                .withAddress("123, Jurong West Ave 6")
                .withPolicy("11111", LocalDate.now().plusDays(15).format(RenewalDate.DATE_FORMATTER)).build();
    }

    @Test
    public void execute_emptyModel_noRenewals() {
        ViewRenewalsCommand command = new ViewRenewalsCommand(30, ViewRenewalsCommand.SORT_BY_DATE);
        CommandResult result = command.execute(model);
        assertEquals(ViewRenewalsCommand.MESSAGE_NO_RENEWALS, result.getFeedbackToUser());
        assertTrue(model.getRenewalsList().isEmpty());
    }

    @Test
    public void execute_withPeople_filtersCorrectly() {
        model.addPerson(alice);
        model.addPerson(bob);
        model.addPerson(charlie);
        ViewRenewalsCommand command = new ViewRenewalsCommand(30, ViewRenewalsCommand.SORT_BY_DATE);
        CommandResult result = command.execute(model);
        assertEquals(String.format(ViewRenewalsCommand.MESSAGE_SUCCESS, 2), result.getFeedbackToUser());
        List<Person> renewalsList = model.getRenewalsList();
        assertEquals(2, renewalsList.size());
        assertEquals(charlie, renewalsList.get(0));
        assertEquals(bob, renewalsList.get(1));
        // Verify that the main person list is not affected
        assertEquals(3, model.getFilteredPersonList().size());
        assertTrue(model.getFilteredPersonList().contains(alice));
        assertTrue(model.getFilteredPersonList().contains(bob));
        assertTrue(model.getFilteredPersonList().contains(charlie));
    }

    @Test
    public void execute_sortByName_sortsCorrectly() {
        model.addPerson(alice);
        model.addPerson(bob);
        model.addPerson(charlie);
        ViewRenewalsCommand command = new ViewRenewalsCommand(60, ViewRenewalsCommand.SORT_BY_NAME);
        command.execute(model);
        List<Person> renewalsList = model.getRenewalsList();
        assertEquals(3, renewalsList.size());
        assertEquals(alice, renewalsList.get(0));
        assertEquals(bob, renewalsList.get(1));
        assertEquals(charlie, renewalsList.get(2));
        // Verify that the main person list is not affected
        assertEquals(3, model.getFilteredPersonList().size());
        assertTrue(model.getFilteredPersonList().contains(alice));
        assertTrue(model.getFilteredPersonList().contains(bob));
        assertTrue(model.getFilteredPersonList().contains(charlie));
    }

    @Test
    public void execute_sortByDate_sortsCorrectly() {
        model.addPerson(alice);
        model.addPerson(bob);
        model.addPerson(charlie);
        ViewRenewalsCommand command = new ViewRenewalsCommand(60, ViewRenewalsCommand.SORT_BY_DATE);
        command.execute(model);
        List<Person> renewalsList = model.getRenewalsList();
        assertEquals(3, renewalsList.size());
        assertEquals(charlie, renewalsList.get(0));
        assertEquals(bob, renewalsList.get(1));
        assertEquals(alice, renewalsList.get(2));
        // Verify that the main person list is not affected
        assertEquals(3, model.getFilteredPersonList().size());
        assertTrue(model.getFilteredPersonList().contains(alice));
        assertTrue(model.getFilteredPersonList().contains(bob));
        assertTrue(model.getFilteredPersonList().contains(charlie));
    }

    @Test
    public void execute_noRenewalsInRange_returnsNoRenewals() {
        Person farFuturePerson = new PersonBuilder().withName("Future")
                .withPhone("85355255")
                .withEmail("future@gmail.com")
                .withAddress("Future Street")
                .withPolicy("99999", LocalDate.now().plusDays(400).format(RenewalDate.DATE_FORMATTER)).build();
        model.addPerson(farFuturePerson);
        ViewRenewalsCommand command = new ViewRenewalsCommand(30, ViewRenewalsCommand.SORT_BY_DATE);
        CommandResult result = command.execute(model);
        assertEquals(ViewRenewalsCommand.MESSAGE_NO_RENEWALS, result.getFeedbackToUser());
        assertTrue(model.getRenewalsList().isEmpty());
    }

    @Test
    public void orderedPredicate_test() {
        model.addPerson(alice);
        model.addPerson(bob);
        ViewRenewalsCommand command = new ViewRenewalsCommand(60, ViewRenewalsCommand.SORT_BY_DATE);
        command.execute(model);
        List<Person> renewalsList = model.getRenewalsList();
        ViewRenewalsCommand.OrderedPredicate predicate =
            new ViewRenewalsCommand.OrderedPredicate(renewalsList);
        // Test person in list
        assertTrue(predicate.test(alice));
        assertTrue(predicate.test(bob));
        // Test person not in list
        Person newPerson = new PersonBuilder().withName("New")
                .withPhone("12345678")
                .withEmail("new@gmail.com")
                .withAddress("New Address")
                .withPolicy("00000", LocalDate.now().plusDays(45).format(RenewalDate.DATE_FORMATTER)).build();
        assertFalse(predicate.test(newPerson));
    }

    @Test
    public void orderedPredicate_equals() {
        List<Person> list1 = List.of(alice, bob);
        List<Person> list2 = List.of(alice, bob);
        List<Person> differentList = List.of(charlie);
        ViewRenewalsCommand.OrderedPredicate predicate1 =
            new ViewRenewalsCommand.OrderedPredicate(list1);
        ViewRenewalsCommand.OrderedPredicate predicate2 =
            new ViewRenewalsCommand.OrderedPredicate(list2);
        ViewRenewalsCommand.OrderedPredicate differentPredicate =
            new ViewRenewalsCommand.OrderedPredicate(differentList);
        // Test equality
        assertTrue(predicate1.equals(predicate1)); // same object
        assertTrue(predicate1.equals(predicate2)); // same content
        assertFalse(predicate1.equals(null)); // null comparison
        assertFalse(predicate1.equals(differentPredicate)); // different content
    }

    @Test
    public void equals() {
        ViewRenewalsCommand standardCommand = new ViewRenewalsCommand(30, ViewRenewalsCommand.SORT_BY_DATE);
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // same values -> returns true
        ViewRenewalsCommand commandWithSameValues = new ViewRenewalsCommand(30, ViewRenewalsCommand.SORT_BY_DATE);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different days -> returns false
        ViewRenewalsCommand commandWithDifferentDays = new ViewRenewalsCommand(60, ViewRenewalsCommand.SORT_BY_DATE);
        assertFalse(standardCommand.equals(commandWithDifferentDays));
        // different sort order -> returns false
        ViewRenewalsCommand commandWithDifferentSort = new ViewRenewalsCommand(30, ViewRenewalsCommand.SORT_BY_NAME);
        assertFalse(standardCommand.equals(commandWithDifferentSort));
    }
}
