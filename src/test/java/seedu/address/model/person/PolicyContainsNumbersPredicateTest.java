package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PolicyContainsNumbersPredicateTest {

    @Test
    public void equals() {
        Set<Policy> firstPredicateNumberList = Set.of(new Policy("123"));
        Set<Policy> secondPredicateNumberList = Set.of(new Policy("123"), new Policy("123456"));

        PolicyContainsNumbersPredicate firstPredicate = new PolicyContainsNumbersPredicate(firstPredicateNumberList);
        PolicyContainsNumbersPredicate secondPredicate = new PolicyContainsNumbersPredicate(secondPredicateNumberList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PolicyContainsNumbersPredicate firstPredicateCopy =
                new PolicyContainsNumbersPredicate(firstPredicateNumberList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_policyContainsNumbers_returnsTrue() {
        // Exact number
        PolicyContainsNumbersPredicate predicate = new PolicyContainsNumbersPredicate(
                Set.of(new Policy("123456")));
        assertTrue(predicate.test(new PersonBuilder().withPolicy("123456").build()));

        // Partial number matching
        predicate = new PolicyContainsNumbersPredicate(Set.of(new Policy("2345")));
        assertTrue(predicate.test(new PersonBuilder().withPolicy("123456").build()));

        // Multiple numbers, one matching
        predicate = new PolicyContainsNumbersPredicate(Set.of(new Policy("134253"), new Policy("123456")));
        assertTrue(predicate.test(new PersonBuilder().withPolicy("123456").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero numbers
        PolicyContainsNumbersPredicate predicate = new PolicyContainsNumbersPredicate(new HashSet<>());
        assertFalse(predicate.test(new PersonBuilder().withPolicy("931215").build()));

        // Non-matching keyword
        predicate = new PolicyContainsNumbersPredicate(Set.of(new Policy("999")));
        assertFalse(predicate.test(new PersonBuilder().withPolicy("912345").build()));

        // Keywords match address, but does not match policy number
        predicate = new PolicyContainsNumbersPredicate(Set.of(new Policy("12345")));
        assertFalse(predicate.test(new PersonBuilder().withPolicy("999999").withAddress("12345").withName("912345678")
                .build()));
    }

    @Test
    public void toStringMethod() {
        Set<Policy> numbers = Set.of(new Policy("123456"), new Policy("876543"));
        PolicyContainsNumbersPredicate predicate = new PolicyContainsNumbersPredicate(numbers);

        String expected = PolicyContainsNumbersPredicate.class.getCanonicalName() + "{numbers=" + numbers + "}";
        assertEquals(expected, predicate.toString());
    }
}
