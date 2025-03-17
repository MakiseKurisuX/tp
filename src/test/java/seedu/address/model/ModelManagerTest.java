package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.person.Policy;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.FindPersonsPredicateBuilder;
import seedu.address.testutil.PersonBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getRenewalsList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getRenewalsList().remove(0));
    }

    @Test
    public void updateSortedRenewalsList_nullComparator_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateSortedRenewalsList(null));
    }

    @Test
    public void updateSortedRenewalsList_validComparator_sortsList() {
        // Create persons with different renewal dates
        String today = LocalDate.now().format(Policy.DATE_FORMATTER);
        String tomorrow = LocalDate.now().plusDays(1).format(Policy.DATE_FORMATTER);
        String nextWeek = LocalDate.now().plusDays(7).format(Policy.DATE_FORMATTER);
        Person personToday = new PersonBuilder().withName("Today Person").withPolicy("111111", today).build();
        Person personTomorrow = new PersonBuilder().withName("Tomorrow Person").withPolicy("222222", tomorrow).build();
        Person personNextWeek = new PersonBuilder().withName("NextWeek Person").withPolicy("333333", nextWeek).build();
        // Add persons to model
        modelManager.addPerson(personNextWeek);
        modelManager.addPerson(personToday);
        modelManager.addPerson(personTomorrow);
        // Update renewals list with all persons
        modelManager.updateRenewalsList(PREDICATE_SHOW_ALL_PERSONS);
        // Sort by days until renewal (ascending)
        Comparator<Person> daysUntilRenewalComparator =
                Comparator.comparingLong(person -> person.getPolicy().getDaysUntilRenewal());
        modelManager.updateSortedRenewalsList(daysUntilRenewalComparator);
        // Check that the list is sorted correctly
        List<Person> sortedList = modelManager.getRenewalsList();
        assertEquals(3, sortedList.size());
        assertEquals(personToday, sortedList.get(0));
        assertEquals(personTomorrow, sortedList.get(1));
        assertEquals(personNextWeek, sortedList.get(2));
        // Sort by name
        Comparator<Person> nameComparator =
                Comparator.comparing(person -> person.getName().fullName);
        modelManager.updateSortedRenewalsList(nameComparator);
        // Check that the list is sorted by name
        sortedList = modelManager.getRenewalsList();
        assertEquals(3, sortedList.size());
        assertEquals(personNextWeek, sortedList.get(0)); // "NextWeek Person"
        assertEquals(personToday, sortedList.get(1)); // "Today Person"
        assertEquals(personTomorrow, sortedList.get(2)); // "Tomorrow Person"
    }

    @Test
    public void updateRenewalsList_withExistingComparator_maintainsSorting() {
        // Create persons with different renewal dates
        String today = LocalDate.now().format(Policy.DATE_FORMATTER);
        String tomorrow = LocalDate.now().plusDays(1).format(Policy.DATE_FORMATTER);
        String nextWeek = LocalDate.now().plusDays(7).format(Policy.DATE_FORMATTER);
        Person personToday = new PersonBuilder().withName("Today Person").withPolicy("111111", today).build();
        Person personTomorrow = new PersonBuilder().withName("Tomorrow Person").withPolicy("222222", tomorrow).build();
        Person personNextWeek = new PersonBuilder().withName("NextWeek Person").withPolicy("333333", nextWeek).build();
        // Add persons to model
        modelManager.addPerson(personNextWeek);
        modelManager.addPerson(personToday);
        modelManager.addPerson(personTomorrow);
        // Set a comparator first
        Comparator<Person> daysUntilRenewalComparator =
                Comparator.comparingLong(person -> person.getPolicy().getDaysUntilRenewal());
        modelManager.updateSortedRenewalsList(daysUntilRenewalComparator);
        // Now update the renewals list - it should maintain the sorting
        modelManager.updateRenewalsList(PREDICATE_SHOW_ALL_PERSONS);
        // Check that the list is still sorted correctly
        List<Person> sortedList = modelManager.getRenewalsList();
        assertEquals(3, sortedList.size());
        assertEquals(personToday, sortedList.get(0));
        assertEquals(personTomorrow, sortedList.get(1));
        assertEquals(personNextWeek, sortedList.get(2));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));
        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));
        // null -> returns false
        assertFalse(modelManager.equals(null));
        // different types -> returns false
        assertFalse(modelManager.equals(5));
        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));
        // different filteredList -> returns false
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManagerCopy.updateFilteredPersonList(p -> false);
        assertFalse(modelManager.equals(modelManagerCopy));
        // different renewalsList -> returns false
        modelManager.updateRenewalsList(PREDICATE_SHOW_ALL_PERSONS);
        modelManagerCopy.updateRenewalsList(p -> false);
        assertFalse(modelManager.equals(modelManagerCopy));
        modelManager.updateFilteredPersonList(new FindPersonsPredicateBuilder(ALICE).build());
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));
        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManagerCopy.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.updateRenewalsList(PREDICATE_SHOW_ALL_PERSONS);
        modelManagerCopy.updateRenewalsList(PREDICATE_SHOW_ALL_PERSONS);
        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

    @Test
    public void addPerson_updatesLastUpdated() {
        modelManager.addPerson(ALICE);
        // After adding a person, lastUpdated should be set.
        assertNotNull(modelManager.getAddressBook().getLastUpdated());
    }

    @Test
    public void deletePerson_updatesLastUpdated() {
        modelManager.addPerson(ALICE);
        LocalDateTime afterAdd = modelManager.getAddressBook().getLastUpdated();
        // Delete the person and check that lastUpdated is updated.
        modelManager.deletePerson(ALICE);
        LocalDateTime afterDelete = modelManager.getAddressBook().getLastUpdated();
        assertNotNull(afterDelete);
        // It is expected that the time will progress; thus afterDelete should be after or equal to afterAdd.
        assertTrue(afterDelete.isAfter(afterAdd) || afterDelete.isEqual(afterAdd));
    }

    @Test
    public void setPerson_updatesLastUpdated() {
        modelManager.addPerson(ALICE);
        LocalDateTime afterAdd = modelManager.getAddressBook().getLastUpdated();
        // Replace ALICE with BENSON (using a typical person) to test that lastUpdated is updated.
        modelManager.setPerson(ALICE, BENSON);
        LocalDateTime afterSet = modelManager.getAddressBook().getLastUpdated();
        assertNotNull(afterSet);
        // Ensure that lastUpdated is updated.
        assertTrue(afterSet.isAfter(afterAdd) || afterSet.isEqual(afterAdd));
    }

    @Test
    public void equals_differentRenewalsComparator_returnsFalse() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager1 = new ModelManager(addressBook, userPrefs);
        ModelManager modelManager2 = new ModelManager(addressBook, userPrefs);
        // Set different comparators
        modelManager1.updateSortedRenewalsList(
                Comparator.comparing(person -> person.getName().fullName));
        modelManager2.updateSortedRenewalsList(
                Comparator.comparingLong(person -> person.getPolicy().getDaysUntilRenewal()));
        // Models should not be equal due to different comparators
        assertFalse(modelManager1.equals(modelManager2));
    }
    @Test
    public void equals_differentFilteredRenewalsList_returnsFalse() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager1 = new ModelManager(addressBook, userPrefs);
        ModelManager modelManager2 = new ModelManager(addressBook, userPrefs);
        // Update renewals list with all persons
        modelManager1.updateRenewalsList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager2.updateRenewalsList(PREDICATE_SHOW_ALL_PERSONS);
        // Apply different predicates to the filtered renewals list
        modelManager1.setFilteredRenewalsListPredicate(p -> true);
        modelManager2.setFilteredRenewalsListPredicate(p -> false);
        // Models should not be equal due to different filtered renewals lists
        assertFalse(modelManager1.equals(modelManager2));
    }

    @Test
    public void updateSortedPersonList_nullComparator_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateSortedPersonList(null));
    }

    @Test
    public void updateSortedPersonList_validComparator_doesNotThrowException() {
        // Create a new model with a fresh address book
        AddressBook addressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager testModel = new ModelManager(addressBook, userPrefs);
        // Add persons to model
        Person personA = new PersonBuilder().withName("Alice").build();
        Person personB = new PersonBuilder().withName("Bob").build();
        Person personC = new PersonBuilder().withName("Charlie").build();
        testModel.addPerson(personC);
        testModel.addPerson(personA);
        testModel.addPerson(personB);
        // Sort by name - this should not throw an exception
        Comparator<Person> nameComparator = Comparator.comparing(person -> person.getName().fullName);
        testModel.updateSortedPersonList(nameComparator);
        // Verify that all persons are still in the list
        List<Person> sortedList = testModel.getFilteredPersonList();
        assertEquals(3, sortedList.size());
        assertTrue(sortedList.contains(personA));
        assertTrue(sortedList.contains(personB));
        assertTrue(sortedList.contains(personC));
    }

    @Test
    public void getRenewalsComparator_returnsCorrectComparator() {
        // Initially the comparator should be null
        assertFalse(modelManager.getRenewalsComparator() != null);
        // Set a comparator
        Comparator<Person> nameComparator = Comparator.comparing(person -> person.getName().fullName);
        modelManager.updateSortedRenewalsList(nameComparator);
        // Check that getRenewalsComparator returns the correct comparator
        assertEquals(nameComparator, modelManager.getRenewalsComparator());
        // Set a different comparator
        Comparator<Person> daysUntilRenewalComparator =
                Comparator.comparingLong(person -> person.getPolicy().getDaysUntilRenewal());
        modelManager.updateSortedRenewalsList(daysUntilRenewalComparator);
        // Check that getRenewalsComparator returns the updated comparator
        assertEquals(daysUntilRenewalComparator, modelManager.getRenewalsComparator());
    }
}
