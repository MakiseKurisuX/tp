package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Person's policy in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPolicy(String)}
 */
public class Policy {
    public static final String MESSAGE_CONSTRAINTS =
            "Policy should only contain numbers, and it should not be blank";
    public static final String DATE_CONSTRAINTS =
            "Renewal date should be a valid date in DD-MM-YYYY format";
    public static final String VALIDATION_REGEX = "\\d+";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public final String policyNumber;
    public final LocalDate renewalDate;

    /**
     * Constructs a {@code Policy} with renewal date set to 1 year from now.
     *
     * @param policyNumber A valid policy number.
     */
    public Policy(String policyNumber) {
        requireNonNull(policyNumber);
        checkArgument(isValidPolicy(policyNumber), MESSAGE_CONSTRAINTS);
        this.policyNumber = policyNumber;
        this.renewalDate = LocalDate.now().plusYears(1);
    }

    /**
     * Constructs a {@code Policy} with a specific renewal date.
     *
     * @param policyNumber A valid policy number.
     * @param renewalDate A valid renewal date in DD-MM-YYYY format.
     */
    public Policy(String policyNumber, String renewalDate) {
        requireNonNull(policyNumber);
        requireNonNull(renewalDate);
        checkArgument(isValidPolicy(policyNumber), MESSAGE_CONSTRAINTS);
        checkArgument(isValidRenewalDate(renewalDate), DATE_CONSTRAINTS);
        this.policyNumber = policyNumber;
        this.renewalDate = LocalDate.parse(renewalDate, DATE_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid policy.
     */
    public static boolean isValidPolicy(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid renewal date.
     */
    public static boolean isValidRenewalDate(String test) {
        if (test == null) {
            return false;
        }
        // Only check if the string matches the date format pattern, not if it's a valid date
        return test.matches("\\d{2}-\\d{2}-\\d{4}");
    }

    /**
     * Returns the number of days until renewal.
     */
    public long getDaysUntilRenewal() {
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), renewalDate);
    }

    /**
     * Returns true if the policy is due for renewal within the specified number of days.
     */
    public boolean isRenewalDueWithin(int days) {
        long daysUntil = getDaysUntilRenewal();
        return daysUntil >= 0 && daysUntil <= days;
    }

    @Override
    public String toString() {
        return String.format("Policy[%s] Renewal: %s",
                policyNumber, renewalDate.format(DATE_FORMATTER));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Policy)) {
            return false;
        }

        Policy otherPolicy = (Policy) other;
        return policyNumber.equals(otherPolicy.policyNumber)
                && renewalDate.equals(otherPolicy.renewalDate);
    }

    @Override
    public int hashCode() {
        return policyNumber.hashCode();
    }

}
