package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class FilterDateCommandParserTest {

    private final FilterDateCommandParser parser = new FilterDateCommandParser();

    @Test
    public void parse_validArgs_returnsFilterDateCommand() throws Exception {
        String userInput = "filter sd/2025-03-01 ed/2025-03-31 s/name";
        FilterDateCommand expectedCommand = new FilterDateCommand(
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 31),
                "name"
        );

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_missingStartDate_throwsParseException() {
        String userInput = "filter ed/2025-03-31 s/date";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingEndDate_throwsParseException() {
        String userInput = "filter sd/2025-03-01 s=name";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_startDateAfterEndDate_throwsParseException() {
        String userInput = "filter sd/2025-04-01 ed/2025-03-01";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_endDateBeyondMaxYears_throwsParseException() {
        String userInput = "filter sd/2025-03-01 ed/2031-03-01";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        String userInput = "filter sd/03-01-2025 ed/2025-03-31 s/date"; // Incorrect date format
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_nonExistentDate_throwsParseException() {
        String userInput = "filter sd/2025-02-30 ed/2025-03-31 s/date"; // Feb 30 does not exist
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_nonExistentSortType_throwsParseException() {
        String userInput = "filter sd/2025-02-11 ed/2025-03-31 s/value"; // value is not a valid sort type
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_noSortOrder_defaultsToDate() throws Exception {
        String userInput = "filter sd/2025-03-01 ed/2025-03-31";
        FilterDateCommand expectedCommand = new FilterDateCommand(
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 31),
                "date"
        );

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_caseInsensitiveSortOrder_valid() throws Exception {
        String userInput = "filter sd/2025-03-01 ed/2025-03-31 s/NaMe"; // Mixed lower and upper case
        FilterDateCommand expectedCommand = new FilterDateCommand(
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 31),
                "name"
        );

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_extraSpaces_trimmedCorrectly() throws Exception {
        String userInput = " filter sd/2025-03-01   ed/2025-03-31   s/name   ";
        FilterDateCommand expectedCommand = new FilterDateCommand(
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 31),
                "name"
        );

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        String userInput = "";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_startDateEqualsEndDate_valid() throws Exception {
        String userInput = "filter sd/2025-03-01 ed/2025-03-01 s/date"; // Same start and end date
        FilterDateCommand expectedCommand = new FilterDateCommand(
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 1),
                "date"
        );

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_endDateAtMaxAllowedLimit_valid() throws Exception {
        LocalDate startDate = LocalDate.of(2025, 3, 1);
        LocalDate endDate = startDate.plusYears(5); // Max limit of 5 years

        String userInput = "filter sd/2025-03-01 ed/" + endDate.toString() + " s/date";
        FilterDateCommand expectedCommand = new FilterDateCommand(startDate, endDate, "date");

        assertEquals(expectedCommand, parser.parse(userInput));
    }
}
