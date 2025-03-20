package seedu.address.model.person;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the provided tags.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final Set<Tag> tags;

    public TagContainsKeywordsPredicate(Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    @Override
    public boolean test(Person person) {
        return tags.stream()
                .anyMatch(tag ->
                        person.getTags().stream()
                                .anyMatch(personTag ->
                                        personTag.tagName.equalsIgnoreCase(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }
        TagContainsKeywordsPredicate otherPredicate = (TagContainsKeywordsPredicate) other;
        return tags.equals(otherPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tags", tags).toString();
    }
}
