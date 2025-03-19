package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Set<Email> firstPredicateKeywordList = Set.of(new Email("first@example.com"));
        Set<Email> secondPredicateKeywordList = Set.of(new Email("first@example.com"), new Email("second@example.com"));

        EmailContainsKeywordsPredicate firstPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate = new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy = new EmailContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Set
                .of(new Email("alice@example.com")));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Partial words in keyword
        predicate = new EmailContainsKeywordsPredicate(Set.of(new Email("lice@example.com")));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Set.of(
                new Email("alice@example.com"), new Email("ice@example.com")));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Only one matching keyword
        predicate = new EmailContainsKeywordsPredicate(Set.of(new Email("Bob@example.com"),
                new Email("Carol@example.com")));
        assertTrue(predicate.test(new PersonBuilder().withEmail("carol@example.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Set.of(new Email("aLIce@example.com"),
                new Email("ICE@exaMple.com")));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(new HashSet<>());
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Set.of(new Email("carol@example.com")));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void toStringMethod() {
        Set<Email> keywords = Set.of(new Email("keyword1@example.com"), new Email("keyword2@example.com"));
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);

        String expected = EmailContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
