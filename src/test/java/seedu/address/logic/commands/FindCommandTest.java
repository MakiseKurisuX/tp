package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand.FindPersonsPredicate;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.FindPersonsPredicateBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindPersonsPredicate predicate = new FindPersonsPredicateBuilder(ALICE).build();
        FindCommand command = new FindCommand(predicate, FindCommand.DEFAULT_SORT);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindPersonsPredicate predicate = new FindPersonsPredicateBuilder()
                .withNames("Kunz")
                .withPhones("94824427")
                .build();
        FindCommand command = new FindCommand(predicate, FindCommand.DEFAULT_SORT);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroFieldMatches_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindPersonsPredicate predicate = new FindPersonsPredicateBuilder().withNames("Amy").build();
        FindCommand command = new FindCommand(predicate, FindCommand.DEFAULT_SORT);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleFieldsMatches_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindPersonsPredicate predicate = new FindPersonsPredicateBuilder().withNames("Kurz", "Elle", "Kunz").build();
        FindCommand command = new FindCommand(predicate, FindCommand.DEFAULT_SORT);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByName_sortsCorrectly() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindPersonsPredicate predicate = new FindPersonsPredicateBuilder().withNames("Alice", "Benson").build();
        FindCommand command = new FindCommand(predicate, FindCommand.SORT_BY_NAME);
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updateSortedPersonList(FindCommand.NAME_COMPARATOR);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortByTag_sortsCorrectly() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindPersonsPredicate predicate = new FindPersonsPredicateBuilder().withNames("Alice", "Benson").build();
        FindCommand command = new FindCommand(predicate, FindCommand.SORT_BY_TAG);
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updateSortedPersonList(FindCommand.TAG_COMPARATOR);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ALICE), model.getFilteredPersonList());
    }

    @Test
    public void equals() {
        FindPersonsPredicate firstPredicate =
                new FindPersonsPredicateBuilder().withNames("first").build();
        FindPersonsPredicate secondPredicate =
                new FindPersonsPredicateBuilder().withNames("second").build();

        FindCommand findFirstCommand = new FindCommand(firstPredicate, FindCommand.SORT_BY_NAME);
        FindCommand findSecondCommand = new FindCommand(secondPredicate, FindCommand.SORT_BY_TAG);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate, FindCommand.SORT_BY_NAME);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void toStringMethod() {
        FindPersonsPredicate predicate = new FindPersonsPredicateBuilder().withNames("keyword").build();
        FindCommand findCommand = new FindCommand(predicate, FindCommand.DEFAULT_SORT);
        String expected = FindCommand.class.getCanonicalName()
                + "{predicate=" + predicate
                + ", sortOrder=" + FindCommand.DEFAULT_SORT + "}";
        assertEquals(expected, findCommand.toString());
    }
}
