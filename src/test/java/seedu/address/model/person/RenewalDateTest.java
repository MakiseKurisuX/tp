package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class RenewalDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RenewalDate(null));
    }

    @Test
    public void constructor_invalidRenewalDate_throwsIllegalArgumentException() {
        String invalidDate = "invalid-date";
        assertThrows(IllegalArgumentException.class, () -> new RenewalDate(invalidDate));
    }

    @Test
    public void constructor_invalidDateFormat_throwsIllegalArgumentException() {
        // This date string matches the regex but is not a valid date
        String invalidButMatchingDate = "31-13-2024"; // invalid month
        assertThrows(IllegalArgumentException.class, () -> new RenewalDate(invalidButMatchingDate));
    }

    @Test
    public void constructor_defaultRenewalDate_setsToOneYear() {
        RenewalDate renewalDate = new RenewalDate();
        LocalDate expectedDate = LocalDate.now().plusYears(1);
        assertEquals(expectedDate, renewalDate.value);
    }

    @Test
    public void isValidRenewalDate() {
        // null renewal date
        assertFalse(RenewalDate.isValidRenewalDate(null));

        // invalid formats
        assertFalse(RenewalDate.isValidRenewalDate("")); // empty string
        assertFalse(RenewalDate.isValidRenewalDate(" ")); // spaces only
        assertFalse(RenewalDate.isValidRenewalDate("2024-03-15")); // wrong format
        assertFalse(RenewalDate.isValidRenewalDate("15/03/2024")); // wrong format
        assertFalse(RenewalDate.isValidRenewalDate("a1-03-2024")); // non-numeric day
        assertFalse(RenewalDate.isValidRenewalDate("15-b3-2024")); // non-numeric month
        assertFalse(RenewalDate.isValidRenewalDate("15-03-20c4")); // non-numeric year
        assertFalse(RenewalDate.isValidRenewalDate("32-03-2024")); // invalid day
        assertFalse(RenewalDate.isValidRenewalDate("15-13-2024")); // invalid month
        assertFalse(RenewalDate.isValidRenewalDate("29-02-2023")); // invalid date (non-leap year)
        assertFalse(RenewalDate.isValidRenewalDate("31-04-2024")); // invalid date (April has 30 days)
        assertFalse(RenewalDate.isValidRenewalDate("31-06-2024")); // invalid date (June has 30 days)
        assertFalse(RenewalDate.isValidRenewalDate("31-09-2024")); // invalid date (September has 30 days)
        assertFalse(RenewalDate.isValidRenewalDate("31-11-2024")); // invalid date (November has 30 days)

        // valid dates
        assertTrue(RenewalDate.isValidRenewalDate("15-03-2024")); // valid date
        assertTrue(RenewalDate.isValidRenewalDate("01-01-2025")); // valid date
        assertTrue(RenewalDate.isValidRenewalDate("31-12-2024")); // valid date
        assertTrue(RenewalDate.isValidRenewalDate("29-02-2024")); // valid date (leap year)
        assertTrue(RenewalDate.isValidRenewalDate("30-04-2024")); // valid date (30 days month)
        assertTrue(RenewalDate.isValidRenewalDate("31-01-2024")); // valid date (31 days month)
    }

    @Test
    public void isValidRenewalDate_thirtyDayMonths() {
        // April (4), June (6), September (9), November (11)
        assertTrue(RenewalDate.isValidRenewalDate("30-04-2024")); // valid last day
        assertFalse(RenewalDate.isValidRenewalDate("31-04-2024")); // invalid day

        assertTrue(RenewalDate.isValidRenewalDate("30-06-2024"));
        assertFalse(RenewalDate.isValidRenewalDate("31-06-2024"));

        assertTrue(RenewalDate.isValidRenewalDate("30-09-2024"));
        assertFalse(RenewalDate.isValidRenewalDate("31-09-2024"));

        assertTrue(RenewalDate.isValidRenewalDate("30-11-2024"));
        assertFalse(RenewalDate.isValidRenewalDate("31-11-2024"));
    }

    @Test
    public void isValidRenewalDate_thirtyOneDayMonths() {
        // January (1), March (3), May (5), July (7), August (8), October (10), December (12)
        assertTrue(RenewalDate.isValidRenewalDate("31-01-2024"));
        assertTrue(RenewalDate.isValidRenewalDate("31-03-2024"));
        assertTrue(RenewalDate.isValidRenewalDate("31-05-2024"));
        assertTrue(RenewalDate.isValidRenewalDate("31-07-2024"));
        assertTrue(RenewalDate.isValidRenewalDate("31-08-2024"));
        assertTrue(RenewalDate.isValidRenewalDate("31-10-2024"));
        assertTrue(RenewalDate.isValidRenewalDate("31-12-2024"));
    }

    @Test
    public void isValidRenewalDate_februaryLeapYearRules() {
        // Regular leap year (divisible by 4)
        assertTrue(RenewalDate.isValidRenewalDate("29-02-2024"));
        // Not a leap year
        assertFalse(RenewalDate.isValidRenewalDate("29-02-2023"));
        // Century year not divisible by 400 (not a leap year)
        assertFalse(RenewalDate.isValidRenewalDate("29-02-2100"));
        // Century year divisible by 400 (is a leap year)
        assertTrue(RenewalDate.isValidRenewalDate("29-02-2000"));
        // Invalid days for any February
        assertFalse(RenewalDate.isValidRenewalDate("30-02-2024"));
        assertFalse(RenewalDate.isValidRenewalDate("31-02-2024"));
    }

    @Test
    public void isValidRenewalDate_boundaryConditions() {
        // First day of month
        assertTrue(RenewalDate.isValidRenewalDate("01-01-2024"));
        assertTrue(RenewalDate.isValidRenewalDate("01-02-2024"));
        assertTrue(RenewalDate.isValidRenewalDate("01-12-2024"));
        // Invalid zero day
        assertFalse(RenewalDate.isValidRenewalDate("00-01-2024"));
        // Invalid zero month
        assertFalse(RenewalDate.isValidRenewalDate("01-00-2024"));
        // Invalid month > 12
        assertFalse(RenewalDate.isValidRenewalDate("01-13-2024"));
    }

    @Test
    public void getDaysUntilRenewal_futureDate_returnsPositiveDays() {
        RenewalDate renewalDate = new RenewalDate(LocalDate.now().plusDays(5).format(RenewalDate.DATE_FORMATTER));
        assertEquals(5, renewalDate.getDaysUntilRenewal());
    }

    @Test
    public void getDaysUntilRenewal_pastDate_returnsNegativeDays() {
        RenewalDate renewalDate = new RenewalDate(LocalDate.now().minusDays(5).format(RenewalDate.DATE_FORMATTER));
        assertEquals(-5, renewalDate.getDaysUntilRenewal());
    }

    @Test
    public void getDaysUntilRenewal_today_returnsZero() {
        RenewalDate renewalDate = new RenewalDate(LocalDate.now().format(RenewalDate.DATE_FORMATTER));
        assertEquals(0, renewalDate.getDaysUntilRenewal());
    }

    @Test
    public void isRenewalDueWithin() {
        RenewalDate renewalDate = new RenewalDate(LocalDate.now().plusDays(30).format(RenewalDate.DATE_FORMATTER));
        assertTrue(renewalDate.isRenewalDueWithin(60)); // within range
        assertTrue(renewalDate.isRenewalDueWithin(30)); // exactly on range
        assertFalse(renewalDate.isRenewalDueWithin(15)); // outside range
    }

    @Test
    public void equals() {
        String date = LocalDate.now().plusDays(30).format(RenewalDate.DATE_FORMATTER);
        RenewalDate renewalDate = new RenewalDate(date);
        // same values -> returns true
        assertTrue(renewalDate.equals(new RenewalDate(date)));
        // same object -> returns true
        assertTrue(renewalDate.equals(renewalDate));
        // null -> returns false
        assertFalse(renewalDate.equals(null));
        // different types -> returns false
        assertFalse(renewalDate.equals(5.0f));
        // different dates -> returns false
        String differentDate = LocalDate.now().plusDays(60).format(RenewalDate.DATE_FORMATTER);
        assertFalse(renewalDate.equals(new RenewalDate(differentDate)));
    }
}
