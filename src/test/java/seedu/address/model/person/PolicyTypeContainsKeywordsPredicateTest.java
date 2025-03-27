package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PolicyTypeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Set<String> firstPredicateKeywordSet = Set.of("Life");
        Set<String> secondPredicateKeywordSet = Set.of("Life", "Health");

        PolicyTypeContainsKeywordsPredicate firstPredicate =
                new PolicyTypeContainsKeywordsPredicate(firstPredicateKeywordSet);
        PolicyTypeContainsKeywordsPredicate secondPredicate =
                new PolicyTypeContainsKeywordsPredicate(secondPredicateKeywordSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PolicyTypeContainsKeywordsPredicate firstPredicateCopy =
                new PolicyTypeContainsKeywordsPredicate(firstPredicateKeywordSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_policyTypeContainsKeywords_returnsTrue() {
        // Exact match
        PolicyTypeContainsKeywordsPredicate predicate =
                new PolicyTypeContainsKeywordsPredicate(Set.of("Life"));
        assertTrue(predicate.test(new PersonBuilder().withPolicyType("Life").build()));

        // Partial matching
        predicate = new PolicyTypeContainsKeywordsPredicate(Set.of("Li"));
        assertTrue(predicate.test(new PersonBuilder().withPolicyType("Life").build()));

        // Case insensitive
        predicate = new PolicyTypeContainsKeywordsPredicate(Set.of("life"));
        assertTrue(predicate.test(new PersonBuilder().withPolicyType("Life").build()));

        // Multiple keywords, one matching
        predicate = new PolicyTypeContainsKeywordsPredicate(Set.of("Property", "Life"));
        assertTrue(predicate.test(new PersonBuilder().withPolicyType("Life").build()));
    }

    @Test
    public void test_policyTypeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PolicyTypeContainsKeywordsPredicate predicate = new PolicyTypeContainsKeywordsPredicate(new HashSet<>());
        assertFalse(predicate.test(new PersonBuilder().withPolicyType("Life").build()));

        // Non-matching keyword
        predicate = new PolicyTypeContainsKeywordsPredicate(Set.of("Vehicle"));
        assertFalse(predicate.test(new PersonBuilder().withPolicyType("Life").build()));

        // Keywords match name, phone, but not policy type
        predicate = new PolicyTypeContainsKeywordsPredicate(Set.of("Alice", "12345"));
        assertFalse(predicate.test(new PersonBuilder().withPolicyType("Life")
                .withName("Alice").withPhone("12345").build()));
    }

    @Test
    public void toStringMethod() {
        Set<String> keywords = Set.of("Life", "Health");
        PolicyTypeContainsKeywordsPredicate predicate = new PolicyTypeContainsKeywordsPredicate(keywords);

        String expected = PolicyTypeContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
