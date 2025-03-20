package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class DateTimeParserTest {

    @Test
    public void parseDateTime_validDateTime_returnsLocalDateTime() {
        String input = "18/3/2025 1415";
        LocalDateTime expected = LocalDateTime.of(2025, Month.MARCH, 18, 14, 15);
        assertEquals(expected, DateTimeParser.parseDateTime(input));
    }

    @Test
    public void parseDateTime_validDateOnly_returnsLocalDateTimeWithDefaultTime() {
        String input = "18/3/2025";
        LocalDateTime expected = LocalDateTime.of(2025, Month.MARCH, 18, 10, 0);
        assertEquals(expected, DateTimeParser.parseDateTime(input));
    }

    @Test
    public void parseDateTime_invalidFormat_throwsIllegalArgumentException() {
        String input = "invalid";
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parseDateTime(input));
    }

    @Test
    public void stringDateTime_midnight_returnsDateOnlyFormat() {
        LocalDateTime midnight = LocalDateTime.of(2025, Month.MARCH, 18, 0, 0);
        String expected = "18/3/2025H";
        assertEquals(expected, DateTimeParser.stringDateTime(midnight));
    }

    @Test
    public void stringDateTime_nonMidnight_returnsDateTimeFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2025, Month.MARCH, 18, 14, 15);
        String expected = "18/3/2025 1415H";
        assertEquals(expected, DateTimeParser.stringDateTime(dateTime));
    }

    @Test
    public void formatDateTime_midnight_returnsShortDateFormat() {
        LocalDateTime midnight = LocalDateTime.of(2025, Month.MARCH, 18, 0, 0);
        String expected = "Mar 18 2025H";
        assertEquals(expected, DateTimeParser.formatDateTime(midnight));
    }

    @Test
    public void formatDateTime_nonMidnight_returnsShortDateTimeFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2025, Month.MARCH, 18, 14, 15);
        String expected = "Mar 18 2025 2:15PMH";
        assertEquals(expected, DateTimeParser.formatDateTime(dateTime));
    }
}
