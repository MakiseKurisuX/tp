package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class PolicyTest {

    private static final String VALID_POLICY_NUMBER = "123456";
    private static final String VALID_RENEWAL_DATE = LocalDate.now().plusDays(30)
            .format(Policy.DATE_FORMATTER);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Policy(null));
        assertThrows(NullPointerException.class, () -> new Policy(VALID_POLICY_NUMBER, null));
    }

    @Test
    public void constructor_invalidPolicy_throwsIllegalArgumentException() {
        String invalidPolicy = "";
        assertThrows(IllegalArgumentException.class, () -> new Policy(invalidPolicy));
        assertThrows(IllegalArgumentException.class, () -> new Policy(invalidPolicy, VALID_RENEWAL_DATE));
    }

    @Test
    public void constructor_invalidRenewalDate_throwsIllegalArgumentException() {
        String invalidDate = "invalid-date";
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_POLICY_NUMBER, invalidDate));
    }

    @Test
    public void constructor_invalidDateFormat_throwsIllegalArgumentException() {
        // This date string matches the regex but is not a valid date
        String invalidButMatchingDate = "3133-02-2024"; // February 31st doesn't exist
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_POLICY_NUMBER, invalidButMatchingDate));
    }

    @Test
    public void constructor_defaultRenewalDate_setsToOneYear() {
        Policy policy = new Policy(VALID_POLICY_NUMBER);
        LocalDate expectedDate = LocalDate.now().plusYears(1);
        assertEquals(expectedDate, policy.renewalDate);
    }

    @Test
    public void isValidPolicy() {
        // null policy number
        assertThrows(NullPointerException.class, () -> Policy.isValidPolicy(null));

        // invalid policy numbers
        assertFalse(Policy.isValidPolicy("")); // empty string
        assertFalse(Policy.isValidPolicy(" ")); // spaces only
        assertFalse(Policy.isValidPolicy("policy")); // non-numeric
        assertFalse(Policy.isValidPolicy("1033p041")); // alphabets within digits
        assertFalse(Policy.isValidPolicy("3912 1534")); // spaces within digits

        // valid policy numbers
        assertTrue(Policy.isValidPolicy("1")); // exactly 1 number
        assertTrue(Policy.isValidPolicy("647274521"));
        assertTrue(Policy.isValidPolicy("12492648284628492623")); // long policy numbers
    }

    @Test
    public void isValidRenewalDate() {
        // null renewal date
        assertFalse(Policy.isValidRenewalDate(null));

        // invalid formats
        assertFalse(Policy.isValidRenewalDate("")); // empty string
        assertFalse(Policy.isValidRenewalDate(" ")); // spaces only
        assertFalse(Policy.isValidRenewalDate("2024-03-15")); // wrong format
        assertFalse(Policy.isValidRenewalDate("15/03/2024")); // wrong format
        assertFalse(Policy.isValidRenewalDate("a1-03-2024")); // non-numeric day
        assertFalse(Policy.isValidRenewalDate("15-b3-2024")); // non-numeric month
        assertFalse(Policy.isValidRenewalDate("15-03-20c4")); // non-numeric year

        // valid formats (note: these only test the format, not if the date is valid)
        assertTrue(Policy.isValidRenewalDate("15-03-2024")); // valid future date
        assertTrue(Policy.isValidRenewalDate("01-01-2025")); // valid future date
        assertTrue(Policy.isValidRenewalDate("31-12-2024")); // valid future date
        assertTrue(Policy.isValidRenewalDate("32-03-2024")); // invalid day but valid format
        assertTrue(Policy.isValidRenewalDate("15-13-2024")); // invalid month but valid format
        assertTrue(Policy.isValidRenewalDate("29-02-2023")); // invalid date (non-leap year) but valid format
    }

    @Test
    public void isRenewalDueWithin() {
        String futureDate = LocalDate.now().plusDays(30).format(Policy.DATE_FORMATTER);
        Policy policy = new Policy(VALID_POLICY_NUMBER, futureDate);

        assertTrue(policy.isRenewalDueWithin(60)); // within range
        assertTrue(policy.isRenewalDueWithin(30)); // exactly on range
        assertFalse(policy.isRenewalDueWithin(15)); // outside range
    }

    @Test
    public void equals() {
        String date = LocalDate.now().plusDays(30).format(Policy.DATE_FORMATTER);
        Policy policy = new Policy(VALID_POLICY_NUMBER, date);

        // same values -> returns true
        assertTrue(policy.equals(new Policy(VALID_POLICY_NUMBER, date)));

        // same object -> returns true
        assertTrue(policy.equals(policy));

        // null -> returns false
        assertFalse(policy.equals(null));

        // different types -> returns false
        assertFalse(policy.equals(5.0f));

        // different policy number -> returns false
        assertFalse(policy.equals(new Policy("999999", date)));

        // different renewal date -> returns false
        String differentDate = LocalDate.now().plusDays(60).format(Policy.DATE_FORMATTER);
        assertFalse(policy.equals(new Policy(VALID_POLICY_NUMBER, differentDate)));
    }

    @Test
    public void getDaysUntilRenewal_futureDate_returnsPositiveDays() {
        Policy policy = new Policy("123456", LocalDate.now().plusDays(5).format(Policy.DATE_FORMATTER));
        assertEquals(5, policy.getDaysUntilRenewal());
    }

    @Test
    public void getDaysUntilRenewal_pastDate_returnsNegativeDays() {
        Policy policy = new Policy("123456", LocalDate.now().minusDays(5).format(Policy.DATE_FORMATTER));
        assertEquals(-5, policy.getDaysUntilRenewal());
    }

    @Test
    public void getDaysUntilRenewal_today_returnsZero() {
        Policy policy = new Policy("123456", LocalDate.now().format(Policy.DATE_FORMATTER));
        assertEquals(0, policy.getDaysUntilRenewal());
    }

    @Test
    public void constructor_singleParam_setsRenewalDateToOneYear() {
        Policy policy = new Policy("123456");
        assertEquals(LocalDate.now().plusYears(1), policy.renewalDate);
    }

    @Test
    public void equals_sameRenewalDate_returnsTrue() {
        String date = LocalDate.now().format(Policy.DATE_FORMATTER);
        Policy policy1 = new Policy("123456", date);
        Policy policy2 = new Policy("123456", date);
        assertTrue(policy1.equals(policy2));
    }

    @Test
    public void equals_differentRenewalDate_returnsFalse() {
        Policy policy1 = new Policy("123456", LocalDate.now().format(Policy.DATE_FORMATTER));
        Policy policy2 = new Policy("123456", LocalDate.now().plusDays(1).format(Policy.DATE_FORMATTER));
        assertFalse(policy1.equals(policy2));
    }
}
