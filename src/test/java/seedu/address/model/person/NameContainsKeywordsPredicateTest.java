package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Set<Name> firstPredicateKeywordList = Set.of(new Name("first"));
        Set<Name> secondPredicateKeywordList = Set.of(new Name("first"), new Name("second"));

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Set.of(new Name("Alice")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple words in keyword
        predicate = new NameContainsKeywordsPredicate(Set.of(new Name("Alice Bob")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Partial words in keyword
        predicate = new NameContainsKeywordsPredicate(Set.of(new Name("lice Bo")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Set.of(new Name("Alice"), new Name("Bob")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Set.of(new Name("Bob"), new Name("Carol")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Set.of(new Name("aLIce"), new Name("bOB")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(new HashSet<>());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Set.of(new Name("Carol")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Non-matching multi-word keyword
        predicate = new NameContainsKeywordsPredicate(Set.of(new Name("Carol Bob")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone and email, but does not match name
        predicate = new NameContainsKeywordsPredicate(Set.of(new Name("12345"), new Name("Main"), new Name("Street")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        Set<Name> keywords = Set.of(new Name("keyword1"), new Name("keyword2"));
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
