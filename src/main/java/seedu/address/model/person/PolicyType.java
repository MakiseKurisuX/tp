package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a policy type in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPolicyType(String)}
 */
public enum PolicyType {
    LIFE("Life"),
    HEALTH("Health"),
    PROPERTY("Property"),
    VEHICLE("Vehicle"),
    TRAVEL("Travel");

    public static final String MESSAGE_CONSTRAINTS =
            "Policy type should be one of: Life, Health, Property, Vehicle, Travel";

    private final String value;

    PolicyType(String value) {
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid policy type.
     */
    public static boolean isValidPolicyType(String test) {
        requireNonNull(test);
        for (PolicyType type : PolicyType.values()) {
            if (type.value.equals(test)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the PolicyType from a string value.
     *
     * @throws IllegalArgumentException if the string is not a valid policy type
     */
    public static PolicyType fromString(String value) {
        requireNonNull(value);
        checkArgument(isValidPolicyType(value), MESSAGE_CONSTRAINTS);
        for (PolicyType type : PolicyType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }

    @Override
    public String toString() {
        return value;
    }
}
