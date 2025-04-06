package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a renewal date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRenewalDate(String)}
 */
public class RenewalDate {
    public static final String DATE_CONSTRAINTS =
            "Renewal date should be a valid date in DD-MM-YYYY format and must be a future date";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public final LocalDate value;

    /**
     * Constructs a {@code RenewalDate} with renewal date set to 1 year from now.
     */
    public RenewalDate() {
        this.value = LocalDate.now().plusYears(1);
    }

    /**
     * Constructs a {@code RenewalDate} with a specific renewal date.
     *
     * @param renewalDate A valid renewal date in DD-MM-YYYY format.
     */
    public RenewalDate(String renewalDate) {
        requireNonNull(renewalDate);
        checkArgument(isValidRenewalDate(renewalDate), DATE_CONSTRAINTS);
        this.value = LocalDate.parse(renewalDate, DATE_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid renewal date.
     */
    public static boolean isValidRenewalDate(String test) {
        if (test == null) {
            return false;
        }
        // First check regex
        if (!test.matches("\\d{2}-\\d{2}-\\d{4}")) {
            return false;
        }
        try {
            // Split into components
            String[] parts = test.split("-");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            // validate month
            if (month < 1 || month > 12) {
                return false;
            }
            // validate days(for leap years too)
            int maxDays;
            switch (month) {
            case 4: case 6: case 9: case 11:
                maxDays = 30;
                break;
            case 2:
                boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                maxDays = isLeapYear ? 29 : 28;
                break;
            default:
                maxDays = 31;
            }
            if (day < 1 || day > maxDays) {
                return false;
            }
            LocalDate inputDate = LocalDate.parse(test, DATE_FORMATTER);
            LocalDate now = LocalDate.now();
            return inputDate.isAfter(now);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the number of days until renewal.
     */
    public long getDaysUntilRenewal() {
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), value);
    }

    /**
     * Returns true if the renewal is due within the specified number of days.
     */
    public boolean isRenewalDueWithin(int days) {
        long daysUntil = getDaysUntilRenewal();
        return daysUntil >= 0 && daysUntil <= days;
    }

    @Override
    public String toString() {
        return value.format(DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RenewalDate)) {
            return false;
        }

        RenewalDate otherRenewalDate = (RenewalDate) other;
        return value.equals(otherRenewalDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
