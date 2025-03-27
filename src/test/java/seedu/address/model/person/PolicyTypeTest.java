package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PolicyTypeTest {

    @Test
    public void isValidPolicyType() {
        // null policy type
        assertThrows(NullPointerException.class, () -> PolicyType.isValidPolicyType(null));

        // invalid policy types
        assertFalse(PolicyType.isValidPolicyType("")); // empty string
        assertFalse(PolicyType.isValidPolicyType(" ")); // spaces only
        assertFalse(PolicyType.isValidPolicyType("Invalid")); // invalid type
        assertFalse(PolicyType.isValidPolicyType("life")); // wrong case
        assertFalse(PolicyType.isValidPolicyType("LIFE")); // wrong case

        // valid policy types
        assertTrue(PolicyType.isValidPolicyType("Life"));
        assertTrue(PolicyType.isValidPolicyType("Health"));
        assertTrue(PolicyType.isValidPolicyType("Property"));
        assertTrue(PolicyType.isValidPolicyType("Vehicle"));
        assertTrue(PolicyType.isValidPolicyType("Travel"));
    }

    @Test
    public void fromString() {
        // null policy type
        assertThrows(NullPointerException.class, () -> PolicyType.fromString(null));

        // invalid policy types
        assertThrows(IllegalArgumentException.class, () -> PolicyType.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> PolicyType.fromString(" "));
        assertThrows(IllegalArgumentException.class, () -> PolicyType.fromString("Invalid"));
        assertThrows(IllegalArgumentException.class, () -> PolicyType.fromString("life"));
        assertThrows(IllegalArgumentException.class, () -> PolicyType.fromString("LIFE"));

        // valid policy types
        assertEquals(PolicyType.LIFE, PolicyType.fromString("Life"));
        assertEquals(PolicyType.HEALTH, PolicyType.fromString("Health"));
        assertEquals(PolicyType.PROPERTY, PolicyType.fromString("Property"));
        assertEquals(PolicyType.VEHICLE, PolicyType.fromString("Vehicle"));
        assertEquals(PolicyType.TRAVEL, PolicyType.fromString("Travel"));
    }

    @Test
    public void toString_returnsCorrectString() {
        assertEquals("Life", PolicyType.LIFE.toString());
        assertEquals("Health", PolicyType.HEALTH.toString());
        assertEquals("Property", PolicyType.PROPERTY.toString());
        assertEquals("Vehicle", PolicyType.VEHICLE.toString());
        assertEquals("Travel", PolicyType.TRAVEL.toString());
    }
}
