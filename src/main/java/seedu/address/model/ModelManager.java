package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedPersons;
    private final ObservableList<Person> renewalsListSource;
    private final FilteredList<Person> filteredRenewalsList;
    private Comparator<Person> renewalsComparator;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedPersons = new SortedList<>(filteredPersons);
        renewalsListSource = FXCollections.observableArrayList();
        filteredRenewalsList = new FilteredList<>(renewalsListSource);
        renewalsComparator = null;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
        addressBook.setLastUpdated(LocalDateTime.now());
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        addressBook.setLastUpdated(LocalDateTime.now());
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
        addressBook.setLastUpdated(LocalDateTime.now());
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return sortedPersons;
    }

    @Override
    public ObservableList<Person> getRenewalsList() {
        return FXCollections.unmodifiableObservableList(filteredRenewalsList);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateRenewalsList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        // Clear the current renewals list
        renewalsListSource.clear();
        // Add all persons that match the predicate
        List<Person> matchingPersons = addressBook.getPersonList().stream()
                .filter(predicate)
                .collect(Collectors.toList());
        renewalsListSource.addAll(matchingPersons);
        // Apply sorting if a comparator is set
        if (renewalsComparator != null) {
            updateSortedRenewalsList(renewalsComparator);
        }
    }

    @Override
    public void updateSortedPersonList(Comparator<Person> comparator) {
        sortedPersons.setComparator(comparator);
    }

    @Override
    public void updateSortedRenewalsList(Comparator<Person> comparator) {
        requireNonNull(comparator);
        this.renewalsComparator = comparator;
        // Create a sorted copy of the current list
        List<Person> sortedList = new ArrayList<>(renewalsListSource);
        sortedList.sort(comparator);
        // Clear and repopulate the source list in the sorted order
        renewalsListSource.clear();
        renewalsListSource.addAll(sortedList);
    }

    /**
     * Returns the current renewals comparator.
     * This method is for testing purposes only.
     */
    public Comparator<Person> getRenewalsComparator() {
        return renewalsComparator;
    }

    /**
     * Sets a predicate on the filtered renewals list.
     * This method is for testing purposes only.
     */
    public void setFilteredRenewalsListPredicate(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredRenewalsList.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredRenewalsList.equals(otherModelManager.filteredRenewalsList)
                && Objects.equals(renewalsComparator, otherModelManager.renewalsComparator);
    }
}
