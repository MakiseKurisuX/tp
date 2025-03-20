package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Set<Tag> firstPredicateTagSet = Set.of(new Tag("friend"));
        Set<Tag> secondPredicateTagSet = Set.of(new Tag("friend"), new Tag("colleague"));

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateTagSet);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateTagSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy =
                new TagContainsKeywordsPredicate(firstPredicateTagSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tags -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagsContainKeywords_returnsTrue() {
        // Single keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Set.of(new Tag("friend")));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Set.of(new Tag("friend"), new Tag("colleague")));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Set.of(new Tag("friend"), new Tag("criminal")));
        assertTrue(predicate.test(new PersonBuilder().withTags("criminal", "enemy").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Set.of(new Tag("FriEnd"), new Tag("cOLLEAgue")));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));
    }

    @Test
    public void test_tagsDoNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(new HashSet<>());
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Set.of(new Tag("enemy")));
        assertFalse(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));

        // Keywords match other fields (name, phone, address), but not tags
        predicate = new TagContainsKeywordsPredicate(
                Set.of(new Tag("Alice"), new Tag("12345"), new Tag("MainStreet")));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("12345")
                .withAddress("MainStreet")
                .withTags("friend")
                .build()));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tags = Set.of(new Tag("friend"), new Tag("colleague"));
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(tags);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{tags=" + tags + "}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void getTags_nonNull_returnsImmutableSet() {
        Set<Tag> tagSet = Set.of(new Tag("friend"), new Tag("colleague"));
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(tagSet);
        Optional<Set<Tag>> optionalTags = predicate.getTags();
        assertTrue(optionalTags.isPresent());
        // Verify that the returned set is unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> {
            optionalTags.get().add(new Tag("enemy"));
        });
    }

    @Test
    public void getTags_null_returnsEmptyOptional() {
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(null);
        Optional<Set<Tag>> optionalTags = predicate.getTags();
        assertFalse(optionalTags.isPresent());
    }
}
