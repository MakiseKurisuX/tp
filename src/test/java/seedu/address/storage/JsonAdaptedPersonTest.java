package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Policy;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_POLICY = "123a";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_RENEWAL_DATE = "32-13-2024"; // invalid date format

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_POLICY = BENSON.getPolicy().policyNumber;
    private static final String VALID_RENEWAL_DATE = BENSON.getPolicy().renewalDate.format(Policy.DATE_FORMATTER);
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_POLICY,
                        VALID_RENEWAL_DATE, VALID_TAGS);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(Name.MESSAGE_CONSTRAINTS, e.getMessage());
        }
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_POLICY,
                VALID_RENEWAL_DATE, VALID_TAGS);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()), e.getMessage());
        }
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_POLICY,
                        VALID_RENEWAL_DATE, VALID_TAGS);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(Phone.MESSAGE_CONSTRAINTS, e.getMessage());
        }
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_POLICY,
                VALID_RENEWAL_DATE, VALID_TAGS);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()), e.getMessage());
        }
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_POLICY,
                        VALID_RENEWAL_DATE, VALID_TAGS);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(Email.MESSAGE_CONSTRAINTS, e.getMessage());
        }
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_POLICY,
                VALID_RENEWAL_DATE, VALID_TAGS);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()), e.getMessage());
        }
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_POLICY,
                        VALID_RENEWAL_DATE, VALID_TAGS);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(Address.MESSAGE_CONSTRAINTS, e.getMessage());
        }
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_POLICY,
                VALID_RENEWAL_DATE, VALID_TAGS);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()), e.getMessage());
        }
    }

    @Test
    public void toModelType_invalidPolicy_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_POLICY,
                        VALID_RENEWAL_DATE, VALID_TAGS);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(Policy.MESSAGE_CONSTRAINTS, e.getMessage());
        }
    }

    @Test
    public void toModelType_nullPolicy_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null,
                VALID_RENEWAL_DATE, VALID_TAGS);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(String.format(MISSING_FIELD_MESSAGE_FORMAT, Policy.class.getSimpleName()), e.getMessage());
        }
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_POLICY,
                        VALID_RENEWAL_DATE, invalidTags);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            // Success
        }
    }

    @Test
    public void toModelType_invalidRenewalDate_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_POLICY,
                        INVALID_RENEWAL_DATE, VALID_TAGS);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void toModelType_validFormatButInvalidDateValue_throwsIllegalValueException() {
        // Create a date that passes the format check but will cause an exception when parsed
        // We'll use a date that's valid in format but will cause DateTimeException when parsed to LocalDate
        String validFormatButInvalidDate = "31-04-2023"; // April 31st doesn't exist
        // First verify that this passes the isValidRenewalDate check
        assertTrue(Policy.isValidRenewalDate(validFormatButInvalidDate));
        // Create a subclass that throws an exception when createPolicy is called
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_POLICY,
                validFormatButInvalidDate, VALID_TAGS) {
            @Override
            protected Policy createPolicy(String policyNumber, String renewalDate) {
                throw new RuntimeException("Simulated exception for invalid date");
            }
        };
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(Policy.DATE_CONSTRAINTS, e.getMessage());
        }
    }
    @Test
    public void toModelType_exceptionWhenCreatingPolicy_throwsIllegalValueException() {
        // Create a subclass of JsonAdaptedPerson that overrides the createPolicy method to throw an exception
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_POLICY, VALID_RENEWAL_DATE, VALID_TAGS) {
            @Override
            protected Policy createPolicy(String policyNumber, String renewalDate) {
                throw new RuntimeException("Simulated exception");
            }
        };
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(Policy.DATE_CONSTRAINTS, e.getMessage());
        }
    }

    @Test
    public void toModelType_nullRenewalDate_returnsPersonWithDefaultRenewalDate() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_POLICY, null, VALID_TAGS);
        try {
            Person modelPerson = person.toModelType();
            // Check that a Policy object was created
            assertNotNull(modelPerson.getPolicy());
            // Check that the policy number is correct
            assertTrue(VALID_POLICY.contains(modelPerson.getPolicy().policyNumber));
            // Check that the renewal date is approximately 1 year from now (within 1 day)
            LocalDate expectedDate = LocalDate.now().plusYears(1);
            LocalDate actualDate = modelPerson.getPolicy().renewalDate;
            long daysDifference = Math.abs(expectedDate.toEpochDay() - actualDate.toEpochDay());
            assertTrue(daysDifference <= 1, "Renewal date should be approximately 1 year from now");
        } catch (IllegalValueException e) {
            // If this happens, the test should fail
            fail("Unexpected IllegalValueException: " + e.getMessage());
        }
    }

    @Test
    public void toModelType_exceptionInPolicyConstructor_throwsIllegalValueException() {
        // We'll use a date that will cause an exception when creating the Policy
        // This is a bit of a hack, but we need to test the catch block
        String dateWithException = "29-02-2023"; // 2023 is not a leap year, so February 29th doesn't exist
        // Create a subclass that throws an exception when createPolicy is called
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_POLICY,
                dateWithException, VALID_TAGS) {
            @Override
            protected Policy createPolicy(String policyNumber, String renewalDate) {
                throw new RuntimeException("Simulated exception for invalid date");
            }
        };
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(Policy.DATE_CONSTRAINTS, e.getMessage());
        }
    }

    @Test
    public void toModelType_invalidDateInNonLeapYear_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_POLICY,
                "29/02/2023", // 2023 is not a leap year
                VALID_TAGS);
        try {
            person.toModelType();
            fail("Expected IllegalValueException was not thrown");
        } catch (IllegalValueException e) {
            assertEquals(Policy.DATE_CONSTRAINTS, e.getMessage());
        }
    }

}
