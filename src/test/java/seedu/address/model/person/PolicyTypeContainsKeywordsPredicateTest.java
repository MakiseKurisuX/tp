package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PolicyTypeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Set<PolicyType> firstPredicateKeywordSet = Set.of(PolicyType.LIFE);
        Set<PolicyType> secondPredicateKeywordSet = Set.of(PolicyType.LIFE, PolicyType.HEALTH);

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
                new PolicyTypeContainsKeywordsPredicate(Set.of(PolicyType.LIFE));
        assertTrue(predicate.test(new PersonBuilder().withPolicyType("Life").build()));

        // Case insensitive
        predicate = new PolicyTypeContainsKeywordsPredicate(Set.of(PolicyType.fromString("life")));
        assertTrue(predicate.test(new PersonBuilder().withPolicyType("LIFE").build()));

        // Multiple keywords, one matching
        predicate = new PolicyTypeContainsKeywordsPredicate(Set.of(PolicyType.fromString("PROPERTY"),
                PolicyType.fromString("LIFE")));
        assertTrue(predicate.test(new PersonBuilder().withPolicyType("LIFE").build()));
    }

    @Test
    public void test_policyTypeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PolicyTypeContainsKeywordsPredicate predicate = new PolicyTypeContainsKeywordsPredicate(new HashSet<>());
        assertFalse(predicate.test(new PersonBuilder().withPolicyType("LIFE").build()));

        // Non-matching keyword
        predicate = new PolicyTypeContainsKeywordsPredicate(Set.of(PolicyType.fromString("VEHICLE")));
        assertFalse(predicate.test(new PersonBuilder().withPolicyType("LIFE").build()));
    }

    @Test
    public void toStringMethod() {
        Set<PolicyType> keywords = Set.of(PolicyType.HEALTH, PolicyType.LIFE);
        PolicyTypeContainsKeywordsPredicate predicate = new PolicyTypeContainsKeywordsPredicate(keywords);

        String actual = predicate.toString();
        assertTrue(actual.startsWith(PolicyTypeContainsKeywordsPredicate.class.getCanonicalName()));
        assertTrue(actual.contains("Health"));
        assertTrue(actual.contains("Life"));
    }
}
