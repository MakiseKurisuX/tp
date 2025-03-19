package seedu.address.model.person;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<Person> {
    private final Set<Address> keywords;

    public AddressContainsKeywordsPredicate(Set<Address> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns an unmodifiable address set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code keywords} is null.
     */
    public Optional<Set<Address>> getAddresses() {
        return (keywords != null) ? Optional.of(Collections.unmodifiableSet(keywords)) : Optional.empty();
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsPartialWordIgnoreCase(person.getAddress().value, keyword.value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressContainsKeywordsPredicate)) {
            return false;
        }

        AddressContainsKeywordsPredicate otherAddressContainsKeywordsPredicate =
                (AddressContainsKeywordsPredicate) other;
        return keywords.equals(otherAddressContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
