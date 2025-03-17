package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedDateTimeTest {

    private static final String VALID_DATE_TIME_STRING = "3/13/2025 1200";
    private static final LocalDateTime VALID_LOCAL_DATE_TIME = LocalDateTime.of(2025, 3, 13, 12, 0);

    @Test
    public void toModelType_validDateTime_returnsLocalDateTime() throws Exception {
        JsonAdaptedDateTime jsonAdaptedDateTime = new JsonAdaptedDateTime(VALID_DATE_TIME_STRING);
        LocalDateTime modelDateTime = jsonAdaptedDateTime.toModelType();
        assertEquals(VALID_LOCAL_DATE_TIME, modelDateTime);
    }

    @Test
    public void getDateTime_returnsFormattedString() {
        JsonAdaptedDateTime jsonAdaptedDateTime = new JsonAdaptedDateTime(VALID_LOCAL_DATE_TIME);
        assertEquals(VALID_DATE_TIME_STRING, jsonAdaptedDateTime.getDateTime());
    }

    @Test
    public void toModelType_nullDateTime_returnsNull() throws Exception {
        JsonAdaptedDateTime jsonAdaptedDateTime = new JsonAdaptedDateTime((String) null);
        assertNull(jsonAdaptedDateTime.toModelType());
    }

    @Test
    public void toModelType_invalidDateTimeFormat_throwsIllegalValueException() {
        String invalidDateTime = "3/13/2025 12:00";
        JsonAdaptedDateTime jsonAdaptedDateTime = new JsonAdaptedDateTime(invalidDateTime);
        IllegalValueException exception = assertThrows(IllegalValueException.class, () ->
                                                        jsonAdaptedDateTime.toModelType());
        assertEquals("Invalid date-time format: " + invalidDateTime, exception.getMessage());
    }
}
