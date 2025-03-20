package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

/**
 * Represents a Person's policy in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPolicy(String)}
 */
public class Policy {
    public static final String MESSAGE_CONSTRAINTS =
            "Policy should only contain numbers, and it should not be blank";
    public static final String VALIDATION_REGEX = "\\d+";

    public final String policyNumber;
    public final RenewalDate renewalDate;

    /**
     * Constructs a {@code Policy} with renewal date set to 1 year from now.
     *
     * @param policyNumber A valid policy number.
     */
    public Policy(String policyNumber) {
        requireNonNull(policyNumber);
        checkArgument(isValidPolicy(policyNumber), MESSAGE_CONSTRAINTS);
        this.policyNumber = policyNumber;
        this.renewalDate = new RenewalDate();
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
        this.policyNumber = policyNumber;
        this.renewalDate = new RenewalDate(renewalDate);
    }

    /**
     * Returns true if a given string is a valid policy.
     */
    public static boolean isValidPolicy(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    /**
     * Returns true if a given string is a valid renewal date.
     */
    public static boolean isValidRenewalDate(String test) {
        return RenewalDate.isValidRenewalDate(test);
    }

    /**
     * Returns the number of days until renewal.
     */
    public long getDaysUntilRenewal() {
        return renewalDate.getDaysUntilRenewal();
    }

    /**
     * Returns true if the policy is due for renewal within the specified number of days.
     */
    public boolean isRenewalDueWithin(int days) {
        return renewalDate.isRenewalDueWithin(days);
    }

    /**
     * Returns true if the policy is due for renewal within the specified date range.
     */
    public boolean isRenewalDueWithinDateRange(LocalDate startDate, LocalDate endDate) {
        return (renewalDate.value.isEqual(startDate) || renewalDate.value.isAfter(startDate))
                && (renewalDate.value.isEqual(endDate) || renewalDate.value.isBefore(endDate));
    }

    @Override
    public String toString() {
        return String.format("Policy[%s] Renewal: %s",
                policyNumber, renewalDate.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

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
