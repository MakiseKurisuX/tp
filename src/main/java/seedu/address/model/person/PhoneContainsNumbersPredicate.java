package seedu.address.model.person;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the numbers given.
 */
public class PhoneContainsNumbersPredicate implements Predicate<Person> {
    private final Set<Phone> numbers;

    public PhoneContainsNumbersPredicate(Set<Phone> numbers) {
        this.numbers = numbers;
    }

    /**
     * Returns an unmodifiable phone set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code numbers} is null.
     */
    public Optional<Set<Phone>> getNumbers() {
        return (numbers != null) ? Optional.of(Collections.unmodifiableSet(numbers)) : Optional.empty();
    }

    @Override
    public boolean test(Person person) {
        return numbers.stream()
                .anyMatch(number -> StringUtil.containsPartialWordIgnoreCase(person.getPhone().value, number.value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneContainsNumbersPredicate)) {
            return false;
        }

        PhoneContainsNumbersPredicate otherPhoneContainsNumbersPredicate = (PhoneContainsNumbersPredicate) other;
        return numbers.equals(otherPhoneContainsNumbersPredicate.numbers);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("numbers", numbers).toString();
    }
}
