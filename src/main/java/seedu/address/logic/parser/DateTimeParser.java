package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing and formatting date and time values.
 */
public class DateTimeParser {

    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter OUTPUT_DATETIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
    private static final LocalTime DEFAULT_TIME_FOR_DATE_ONLY = LocalTime.of(10, 0);

    /**
     * Parses a date-time string into a LocalDateTime object
     *
     * @param input Date-Time string to be parsed
     * @return LocalDateTime object representing the parsed date-time
     * @throws IllegalArgumentException if input format is invalid
     */
    public static LocalDateTime parseDateTime(String input) {
        try {
            return LocalDateTime.parse(input, DATETIME_FORMAT);
        } catch (DateTimeParseException e) {
            try {
                LocalDate dateOnly = LocalDate.parse(input, DATE_FORMAT);
                // Java needs random time value to convert into format.
                return LocalDateTime.of(dateOnly, DEFAULT_TIME_FOR_DATE_ONLY);
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Error in parsing Date. Use 'd/M/yyyy HHmm' or 'd/M/yyyy'.");
            }
        }
    }

    /**
     * Converts a LocalDateTime object back into String representation
     *
     * @param dateTime The LocalDateTime object to be converted
     * @return String representing the converted LocalDateTime object
     */
    public static String stringDateTime(LocalDateTime dateTime) {
        if (dateTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dateTime.format(DATE_FORMAT) + "H";
        } else {
            return dateTime.format(DATETIME_FORMAT) + "H";
        }
    }

    /**
     * Converts a LocalDateTime object back into a different String representation
     *
     * @param dateTime The LocalDateTime object to be converted
     * @return String representing the converted LocalDateTime object
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dateTime.format(OUTPUT_DATE_FORMAT) + "H";
        } else {
            return dateTime.format(OUTPUT_DATETIME_FORMAT) + "H";
        }
    }

}
