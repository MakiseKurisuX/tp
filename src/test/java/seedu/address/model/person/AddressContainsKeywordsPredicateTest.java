package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Set<Address> firstPredicateKeywordList = Set.of(new Address("First Street"));
        Set<Address> secondPredicateKeywordList = Set.of(new Address("First Street"), new Address("Second Avenue"));

        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Set.of(new Address("Wonderland")));
        assertTrue(predicate.test(new PersonBuilder().withAddress("3 Wonderland Lane").build()));

        // Multiple words in keyword
        predicate = new AddressContainsKeywordsPredicate(Set.of(new Address("Wonderland Lane")));
        assertTrue(predicate.test(new PersonBuilder().withAddress("3 Wonderland Lane").build()));

        // Partial words in keyword
        predicate = new AddressContainsKeywordsPredicate(Set.of(new Address("land la")));
        assertTrue(predicate.test(new PersonBuilder().withAddress("3 Wonderland Lane").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Set.of(new Address("Wonderland"), new Address("Lane")));
        assertTrue(predicate.test(new PersonBuilder().withAddress("3 Wonderland Lane").build()));

        // Only one matching keyword
        predicate = new AddressContainsKeywordsPredicate(Set.of(new Address("Lane"), new Address("Street")));
        assertTrue(predicate.test(new PersonBuilder().withAddress("3 Wonderland Street").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Set.of(new Address("wonderLand"), new Address("lANe")));
        assertTrue(predicate.test(new PersonBuilder().withAddress("3 Wonderland Lane").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(new HashSet<>());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Wonderland").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Set.of(new Address("Partyland")));
        assertFalse(predicate.test(new PersonBuilder().withAddress("3 Wonderland Lane").build()));

        // Non-matching multi-word keyword
        predicate = new AddressContainsKeywordsPredicate(Set.of(new Address("Partyland Lane")));
        assertFalse(predicate.test(new PersonBuilder().withAddress("3 Wonderland Lane").build()));

        // Keywords match name and phone, but does not match address
        predicate = new AddressContainsKeywordsPredicate(Set.of(new Address("12345"), new Address("Alice")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        Set<Address> keywords = Set.of(new Address("keyword1"), new Address("keyword2"));
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);

        String expected = AddressContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
