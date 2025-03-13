package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsNumbersPredicateTest {

    @Test
    public void equals() {
        Set<Phone> firstPredicateNumberList = Set.of(new Phone("123"));
        Set<Phone> secondPredicateNumberList = Set.of(new Phone("123"), new Phone("98345837"));

        PhoneContainsNumbersPredicate firstPredicate = new PhoneContainsNumbersPredicate(firstPredicateNumberList);
        PhoneContainsNumbersPredicate secondPredicate = new PhoneContainsNumbersPredicate(secondPredicateNumberList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsNumbersPredicate firstPredicateCopy = new PhoneContainsNumbersPredicate(firstPredicateNumberList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsNumbers_returnsTrue() {
        // Exact number
        PhoneContainsNumbersPredicate predicate = new PhoneContainsNumbersPredicate(Set.of(new Phone("93121534")));
        assertTrue(predicate.test(new PersonBuilder().withPhone("93121534").build()));

        // Partial number matching
        predicate = new PhoneContainsNumbersPredicate(Set.of(new Phone("1215")));
        assertTrue(predicate.test(new PersonBuilder().withPhone("93121534").build()));

        // Multiple numbers, one matching
        predicate = new PhoneContainsNumbersPredicate(Set.of(new Phone("93121534"), new Phone("98345837")));
        assertTrue(predicate.test(new PersonBuilder().withPhone("98345837").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero numbers
        PhoneContainsNumbersPredicate predicate = new PhoneContainsNumbersPredicate(new HashSet<>());
        assertFalse(predicate.test(new PersonBuilder().withPhone("93121534").build()));

        // Non-matching keyword
        predicate = new PhoneContainsNumbersPredicate(Set.of(new Phone("999")));
        assertFalse(predicate.test(new PersonBuilder().withPhone("98345837").build()));

        // Keywords match address, but does not match phone
        predicate = new PhoneContainsNumbersPredicate(Set.of(new Phone("12345")));
        assertFalse(predicate.test(new PersonBuilder().withPhone("999").withAddress("12345").build()));
    }

    @Test
    public void toStringMethod() {
        Set<Phone> numbers = Set.of(new Phone("12345678"), new Phone("87654321"));
        PhoneContainsNumbersPredicate predicate = new PhoneContainsNumbersPredicate(numbers);

        String expected = PhoneContainsNumbersPredicate.class.getCanonicalName() + "{numbers=" + numbers + "}";
        assertEquals(expected, predicate.toString());
    }
}
