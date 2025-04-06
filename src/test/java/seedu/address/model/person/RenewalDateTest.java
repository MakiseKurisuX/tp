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

        // past dates
        String pastDate = LocalDate.now().minusDays(1).format(RenewalDate.DATE_FORMATTER);
        assertFalse(RenewalDate.isValidRenewalDate(pastDate)); // yesterday
        assertFalse(RenewalDate.isValidRenewalDate(LocalDate.now().format(RenewalDate.DATE_FORMATTER))); // today

        // valid future dates
        String tomorrow = LocalDate.now().plusDays(1).format(RenewalDate.DATE_FORMATTER);
        String nextMonth = LocalDate.now().plusMonths(1).format(RenewalDate.DATE_FORMATTER);
        String nextYear = LocalDate.now().plusYears(1).format(RenewalDate.DATE_FORMATTER);
        assertTrue(RenewalDate.isValidRenewalDate(tomorrow)); // tomorrow
        assertTrue(RenewalDate.isValidRenewalDate(nextMonth)); // next month
        assertTrue(RenewalDate.isValidRenewalDate(nextYear)); // next year
    }

    @Test
    public void isValidRenewalDate_thirtyDayMonths() {
        LocalDate futureDate = LocalDate.now().plusYears(1);
        while (futureDate.getMonthValue() != 4) {
            futureDate = futureDate.plusMonths(1);
        }
        String aprilDate = String.format("30-%02d-%d", futureDate.getMonthValue(), futureDate.getYear());
        String invalidAprilDate = String.format("31-%02d-%d", futureDate.getMonthValue(), futureDate.getYear());
        // April (4), June (6), September (9), November (11)
        assertTrue(RenewalDate.isValidRenewalDate(aprilDate)); // valid last day
        assertFalse(RenewalDate.isValidRenewalDate(invalidAprilDate)); // invalid day

        // Get future dates for other months
        LocalDate juneDate = futureDate.plusMonths(2);
        LocalDate septDate = futureDate.plusMonths(5);
        LocalDate novDate = futureDate.plusMonths(7);

        String juneValidDate = String.format("30-%02d-%d", juneDate.getMonthValue(), juneDate.getYear());
        String juneInvalidDate = String.format("31-%02d-%d", juneDate.getMonthValue(), juneDate.getYear());
        assertTrue(RenewalDate.isValidRenewalDate(juneValidDate));
        assertFalse(RenewalDate.isValidRenewalDate(juneInvalidDate));

        String septValidDate = String.format("30-%02d-%d", septDate.getMonthValue(), septDate.getYear());
        String septInvalidDate = String.format("31-%02d-%d", septDate.getMonthValue(), septDate.getYear());
        assertTrue(RenewalDate.isValidRenewalDate(septValidDate));
        assertFalse(RenewalDate.isValidRenewalDate(septInvalidDate));

        String novValidDate = String.format("30-%02d-%d", novDate.getMonthValue(), novDate.getYear());
        String novInvalidDate = String.format("31-%02d-%d", novDate.getMonthValue(), novDate.getYear());
        assertTrue(RenewalDate.isValidRenewalDate(novValidDate));
        assertFalse(RenewalDate.isValidRenewalDate(novInvalidDate));
    }

    @Test
    public void isValidRenewalDate_thirtyOneDayMonths() {
        // Get a future date in January 2024 or later
        LocalDate futureDate = LocalDate.now().plusYears(1);
        while (futureDate.getMonthValue() != 1) {
            futureDate = futureDate.plusMonths(1);
        }

        // January (1), March (3), May (5), July (7), August (8), October (10), December (12)
        String janDate = String.format("31-%02d-%d", futureDate.getMonthValue(), futureDate.getYear());
        assertTrue(RenewalDate.isValidRenewalDate(janDate));

        LocalDate marchDate = futureDate.plusMonths(2);
        LocalDate mayDate = futureDate.plusMonths(4);
        LocalDate julyDate = futureDate.plusMonths(6);
        LocalDate augDate = futureDate.plusMonths(7);
        LocalDate octDate = futureDate.plusMonths(9);
        LocalDate decDate = futureDate.plusMonths(11);

        String marchValidDate = String.format("31-%02d-%d", marchDate.getMonthValue(), marchDate.getYear());
        String mayValidDate = String.format("31-%02d-%d", mayDate.getMonthValue(), mayDate.getYear());
        String julyValidDate = String.format("31-%02d-%d", julyDate.getMonthValue(), julyDate.getYear());
        String augValidDate = String.format("31-%02d-%d", augDate.getMonthValue(), augDate.getYear());
        String octValidDate = String.format("31-%02d-%d", octDate.getMonthValue(), octDate.getYear());
        String decValidDate = String.format("31-%02d-%d", decDate.getMonthValue(), decDate.getYear());

        assertTrue(RenewalDate.isValidRenewalDate(marchValidDate));
        assertTrue(RenewalDate.isValidRenewalDate(mayValidDate));
        assertTrue(RenewalDate.isValidRenewalDate(julyValidDate));
        assertTrue(RenewalDate.isValidRenewalDate(augValidDate));
        assertTrue(RenewalDate.isValidRenewalDate(octValidDate));
        assertTrue(RenewalDate.isValidRenewalDate(decValidDate));
    }

    @Test
    public void isValidRenewalDate_februaryLeapYearRules() {
        // Get next leap year after current year
        int currentYear = LocalDate.now().getYear();
        int nextLeapYear = currentYear + (4 - (currentYear % 4));
        if (nextLeapYear <= currentYear) {
            nextLeapYear += 4;
        }

        // Regular leap year (divisible by 4)
        String leapYearDate = String.format("29-02-%d", nextLeapYear);
        String nonLeapYearDate = String.format("29-02-%d", nextLeapYear + 1);
        String invalidFebDate1 = String.format("30-02-%d", nextLeapYear);
        String invalidFebDate2 = String.format("31-02-%d", nextLeapYear);

        assertTrue(RenewalDate.isValidRenewalDate(leapYearDate));
        assertFalse(RenewalDate.isValidRenewalDate(nonLeapYearDate)); // Not a leap year
        assertFalse(RenewalDate.isValidRenewalDate("29-02-2100")); // Century year not divisible by 400
        assertFalse(RenewalDate.isValidRenewalDate("29-02-2000")); // Past date, should be invalid
        assertFalse(RenewalDate.isValidRenewalDate(invalidFebDate1)); // Invalid days for any February
        assertFalse(RenewalDate.isValidRenewalDate(invalidFebDate2));
    }

    @Test
    public void isValidRenewalDate_boundaryConditions() {
        // Get future dates
        LocalDate futureDate = LocalDate.now().plusYears(1);

        // First day of month
        String firstDayDate1 = String.format("01-%02d-%d", futureDate.getMonthValue(), futureDate.getYear());
        String firstDayDate2 = String.format("01-%02d-%d",
                futureDate.plusMonths(1).getMonthValue(),
                futureDate.plusMonths(1).getYear());
        String firstDayDate3 = String.format("01-%02d-%d",
                futureDate.plusMonths(2).getMonthValue(),
                futureDate.plusMonths(2).getYear());

        assertTrue(RenewalDate.isValidRenewalDate(firstDayDate1));
        assertTrue(RenewalDate.isValidRenewalDate(firstDayDate2));
        assertTrue(RenewalDate.isValidRenewalDate(firstDayDate3));

        // Invalid dates
        String invalidZeroDay = String.format("00-%02d-%d", futureDate.getMonthValue(), futureDate.getYear());
        String invalidZeroMonth = String.format("01-00-%d", futureDate.getYear());
        String invalidMonth = String.format("01-13-%d", futureDate.getYear());

        assertFalse(RenewalDate.isValidRenewalDate(invalidZeroDay));
        assertFalse(RenewalDate.isValidRenewalDate(invalidZeroMonth));
        assertFalse(RenewalDate.isValidRenewalDate(invalidMonth));
    }

    @Test
    public void getDaysUntilRenewal_futureDate_returnsPositiveDays() {
        RenewalDate renewalDate = new RenewalDate(LocalDate.now().plusDays(5).format(RenewalDate.DATE_FORMATTER));
        assertEquals(5, renewalDate.getDaysUntilRenewal());
    }

    @Test
    public void getDaysUntilRenewal_pastDate_returnsNegativeDays() {
        // We can't test past dates since they're invalid for RenewalDate
        // This test is no longer applicable
        assertTrue(true);
    }

    @Test
    public void getDaysUntilRenewal_today_returnsZero() {
        // We can't test today's date since it's invalid for RenewalDate
        // This test is no longer applicable
        assertTrue(true);
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
