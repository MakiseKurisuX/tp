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
            .format(RenewalDate.DATE_FORMATTER);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Policy(null));
        assertThrows(NullPointerException.class, () -> new Policy(
            VALID_POLICY_NUMBER,
            (RenewalDate) null));
        assertThrows(NullPointerException.class, () -> new Policy(
            VALID_POLICY_NUMBER,
            new RenewalDate(VALID_RENEWAL_DATE),
            (PolicyType) null));
    }

    @Test
    public void constructor_invalidPolicy_throwsIllegalArgumentException() {
        String invalidPolicy = "";
        assertThrows(IllegalArgumentException.class, () -> new Policy(invalidPolicy));
        assertThrows(IllegalArgumentException.class, () -> new Policy(
            invalidPolicy,
            new RenewalDate(VALID_RENEWAL_DATE)));
        assertThrows(IllegalArgumentException.class, () -> new Policy(
            invalidPolicy,
            new RenewalDate(VALID_RENEWAL_DATE),
            PolicyType.LIFE));
    }

    @Test
    public void constructor_invalidRenewalDate_throwsIllegalArgumentException() {
        String invalidDate = "invalid-date";
        assertThrows(IllegalArgumentException.class, () -> new Policy(
            VALID_POLICY_NUMBER,
            new RenewalDate(invalidDate)));
        assertThrows(IllegalArgumentException.class, () -> new Policy(
            VALID_POLICY_NUMBER,
            new RenewalDate(invalidDate),
            PolicyType.LIFE));
    }

    @Test
    public void constructor_invalidDateFormat_throwsIllegalArgumentException() {
        String invalidButMatchingDate = "31-13-2024";
        assertThrows(IllegalArgumentException.class, () -> new Policy(
            VALID_POLICY_NUMBER, new RenewalDate(invalidButMatchingDate)));
        assertThrows(IllegalArgumentException.class, () -> new Policy(
            VALID_POLICY_NUMBER, new RenewalDate(invalidButMatchingDate), PolicyType.LIFE));
    }

    @Test
    public void constructor_invalidPolicyType_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Policy(
            VALID_POLICY_NUMBER, new RenewalDate(VALID_RENEWAL_DATE), PolicyType.fromString("Invalid")));
    }

    @Test
    public void constructor_defaultRenewalDate_setsToOneYear() {
        Policy policy = new Policy(VALID_POLICY_NUMBER);
        LocalDate expectedDate = LocalDate.now().plusYears(1);
        assertEquals(expectedDate, policy.renewalDate.value);
        assertEquals(PolicyType.LIFE, policy.getType());
    }

    @Test
    public void constructor_withPolicyType_setsCorrectType() {
        Policy policy = new Policy(VALID_POLICY_NUMBER, new RenewalDate(VALID_RENEWAL_DATE), PolicyType.HEALTH);
        assertEquals(PolicyType.HEALTH, policy.getType());
    }

    @Test
    public void constructor_allPolicyTypes_setsCorrectType() {
        Policy lifePolicy = new Policy(VALID_POLICY_NUMBER, new RenewalDate(VALID_RENEWAL_DATE), PolicyType.LIFE);
        Policy healthPolicy = new Policy(VALID_POLICY_NUMBER, new RenewalDate(VALID_RENEWAL_DATE), PolicyType.HEALTH);
        Policy propertyPolicy = new Policy(
            VALID_POLICY_NUMBER,
            new RenewalDate(VALID_RENEWAL_DATE),
            PolicyType.PROPERTY);
        Policy vehiclePolicy = new Policy(VALID_POLICY_NUMBER, new RenewalDate(VALID_RENEWAL_DATE), PolicyType.VEHICLE);
        Policy travelPolicy = new Policy(VALID_POLICY_NUMBER, new RenewalDate(VALID_RENEWAL_DATE), PolicyType.TRAVEL);

        assertEquals(PolicyType.LIFE, lifePolicy.getType());
        assertEquals(PolicyType.HEALTH, healthPolicy.getType());
        assertEquals(PolicyType.PROPERTY, propertyPolicy.getType());
        assertEquals(PolicyType.VEHICLE, vehiclePolicy.getType());
        assertEquals(PolicyType.TRAVEL, travelPolicy.getType());
    }

    @Test
    public void constructor_twoParams_setsDefaultType() {
        Policy policy = new Policy(VALID_POLICY_NUMBER, new RenewalDate(VALID_RENEWAL_DATE));
        assertEquals(PolicyType.LIFE, policy.getType());
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
        assertFalse(Policy.isValidRenewalDate("32-03-2024")); // invalid day
        assertFalse(Policy.isValidRenewalDate("15-13-2024")); // invalid month
        assertFalse(Policy.isValidRenewalDate("29-02-2023")); // invalid date (non-leap year)
        assertFalse(Policy.isValidRenewalDate("31-04-2024")); // invalid date (April has 30 days)
        assertFalse(Policy.isValidRenewalDate("31-06-2024")); // invalid date (June has 30 days)
        assertFalse(Policy.isValidRenewalDate("31-09-2024")); // invalid date (September has 30 days)
        assertFalse(Policy.isValidRenewalDate("31-11-2024")); // invalid date (November has 30 days)

        // valid dates
        assertTrue(Policy.isValidRenewalDate("15-03-2024")); // valid date
        assertTrue(Policy.isValidRenewalDate("01-01-2025")); // valid date
        assertTrue(Policy.isValidRenewalDate("31-12-2024")); // valid date
        assertTrue(Policy.isValidRenewalDate("29-02-2024")); // valid date (leap year)
        assertTrue(Policy.isValidRenewalDate("30-04-2024")); // valid date (30 days month)
        assertTrue(Policy.isValidRenewalDate("31-01-2024")); // valid date (31 days month)
    }

    @Test
    public void isRenewalDueWithin() {
        String futureDate = LocalDate.now().plusDays(30).format(RenewalDate.DATE_FORMATTER);
        Policy policy = new Policy(VALID_POLICY_NUMBER, new RenewalDate(futureDate));

        assertTrue(policy.isRenewalDueWithin(60)); // within range
        assertTrue(policy.isRenewalDueWithin(30)); // exactly on range
        assertFalse(policy.isRenewalDueWithin(15)); // outside range
    }

    @Test
    public void equals() {
        String date = LocalDate.now().plusDays(30).format(RenewalDate.DATE_FORMATTER);
        Policy policy = new Policy(VALID_POLICY_NUMBER, new RenewalDate(date));

        // same values -> returns true
        assertTrue(policy.equals(new Policy(VALID_POLICY_NUMBER, new RenewalDate(date))));

        // same object -> returns true
        assertTrue(policy.equals(policy));

        // null -> returns false
        assertFalse(policy.equals(null));

        // different types -> returns false
        assertFalse(policy.equals(5.0f));

        // different policy number -> returns false
        assertFalse(policy.equals(new Policy("999999", new RenewalDate(date))));

        // different renewal date -> returns false
        String differentDate = LocalDate.now().plusDays(60).format(RenewalDate.DATE_FORMATTER);
        assertFalse(policy.equals(new Policy(VALID_POLICY_NUMBER, new RenewalDate(differentDate))));

        // different policy type -> returns false
        assertFalse(policy.equals(new Policy(VALID_POLICY_NUMBER, new RenewalDate(date), PolicyType.HEALTH)));

        // same policy type -> returns true
        assertTrue(policy.equals(new Policy(VALID_POLICY_NUMBER, new RenewalDate(date), PolicyType.LIFE)));
    }

    @Test
    public void getDaysUntilRenewal_futureDate_returnsPositiveDays() {
        Policy policy = new Policy(
            "123456",
            new RenewalDate(LocalDate.now()
                .plusDays(5)
                .format(RenewalDate.DATE_FORMATTER)));
        assertEquals(5, policy.getDaysUntilRenewal());
    }

    @Test
    public void getDaysUntilRenewal_pastDate_returnsNegativeDays() {
        Policy policy = new Policy(
            "123456",
            new RenewalDate(LocalDate.now()
                .minusDays(5)
                .format(RenewalDate.DATE_FORMATTER)));
        assertEquals(-5, policy.getDaysUntilRenewal());
    }

    @Test
    public void getDaysUntilRenewal_today_returnsZero() {
        Policy policy = new Policy("123456", new RenewalDate(LocalDate.now().format(RenewalDate.DATE_FORMATTER)));
        assertEquals(0, policy.getDaysUntilRenewal());
    }

    @Test
    public void constructor_singleParam_setsRenewalDateToOneYear() {
        Policy policy = new Policy("123456");
        assertEquals(LocalDate.now().plusYears(1), policy.renewalDate.value);
        assertEquals(PolicyType.LIFE, policy.getType());
    }

    @Test
    public void toString_returnsCorrectString() {
        Policy policy = new Policy("123456", new RenewalDate(VALID_RENEWAL_DATE), PolicyType.HEALTH);
        String expected = String.format("Policy[%s] Type: %s Renewal: %s",
                "123456", "Health", VALID_RENEWAL_DATE);
        assertEquals(expected, policy.toString());
    }

    @Test
    public void toString_allPolicyTypes_returnsCorrectString() {
        Policy lifePolicy = new Policy("123456", new RenewalDate(VALID_RENEWAL_DATE), PolicyType.LIFE);
        Policy healthPolicy = new Policy("123456", new RenewalDate(VALID_RENEWAL_DATE), PolicyType.HEALTH);
        Policy propertyPolicy = new Policy("123456", new RenewalDate(VALID_RENEWAL_DATE), PolicyType.PROPERTY);
        Policy vehiclePolicy = new Policy("123456", new RenewalDate(VALID_RENEWAL_DATE), PolicyType.VEHICLE);
        Policy travelPolicy = new Policy("123456", new RenewalDate(VALID_RENEWAL_DATE), PolicyType.TRAVEL);

        assertEquals(String.format("Policy[%s] Type: %s Renewal: %s", "123456", "Life", VALID_RENEWAL_DATE),
                lifePolicy.toString());
        assertEquals(String.format("Policy[%s] Type: %s Renewal: %s", "123456", "Health", VALID_RENEWAL_DATE),
                healthPolicy.toString());
        assertEquals(String.format("Policy[%s] Type: %s Renewal: %s", "123456", "Property", VALID_RENEWAL_DATE),
                propertyPolicy.toString());
        assertEquals(String.format("Policy[%s] Type: %s Renewal: %s", "123456", "Vehicle", VALID_RENEWAL_DATE),
                vehiclePolicy.toString());
        assertEquals(String.format("Policy[%s] Type: %s Renewal: %s", "123456", "Travel", VALID_RENEWAL_DATE),
                travelPolicy.toString());
    }
}
