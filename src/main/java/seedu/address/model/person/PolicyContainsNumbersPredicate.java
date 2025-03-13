package seedu.address.model.person;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Policy} matches any of the numbers given.
 */
public class PolicyContainsNumbersPredicate implements Predicate<Person> {
    private final Set<Policy> numbers;

    public PolicyContainsNumbersPredicate(Set<Policy> numbers) {
        this.numbers = numbers;
    }

    /**
     * Returns an unmodifiable policy set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code numbers} is null.
     */
    public Optional<Set<Policy>> getPolicies() {
        return (numbers != null) ? Optional.of(Collections.unmodifiableSet(numbers)) : Optional.empty();
    }

    @Override
    public boolean test(Person person) {
        return numbers.stream()
                .anyMatch(number -> StringUtil.containsPartialWordIgnoreCase(person.getPolicy().policyNumber,
                        number.policyNumber));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PolicyContainsNumbersPredicate)) {
            return false;
        }

        PolicyContainsNumbersPredicate otherPolicyContainsNumbersPredicate = (PolicyContainsNumbersPredicate) other;
        return numbers.equals(otherPolicyContainsNumbersPredicate.numbers);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("numbers", numbers).toString();
    }
}

