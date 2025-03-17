package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Jackson-friendly version of a DateTime object.
 */
public class JsonAdaptedDateTime {

    private static final String DATE_TIME_PATTERN = "M/d/yyyy HHmm";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private final String dateTime;

    @JsonCreator
    public JsonAdaptedDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public JsonAdaptedDateTime(LocalDateTime source) {
        this.dateTime = source != null ? source.format(DATE_TIME_FORMATTER) : null;
    }

    @JsonValue
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Converts this JSON-adapted date-time object into the model's {@link java.time.LocalDateTime} object.
     *
     * @return the {@code LocalDateTime} which is stored date-time string, or {@code null} if the string null.
     * @throws IllegalValueException if the stored date-time string does not match the required format.
     */
    public LocalDateTime toModelType() throws IllegalValueException {
        if (dateTime == null) {
            return null;
        }

        try {
            return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid date-time format: " + dateTime);
        }
    }
}
