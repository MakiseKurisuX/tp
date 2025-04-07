package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FilterDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class FilterDateCommandParserTest {

    private final FilterDateCommandParser parser = new FilterDateCommandParser();

    @Test
    public void parse_validArgs_returnsFilterDateCommand() throws Exception {
        String userInput = " sd/01-03-2025 ed/31-03-2025 s/name";
        FilterDateCommand expectedCommand = new FilterDateCommand(
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 31),
                "name"
        );

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_missingStartDate_throwsParseException() {
        String userInput = " ed/31-03-2025 s/date";
        assertParseFailure(parser, userInput, FilterDateCommandParser.MESSAGE_REQUIRED_PREFIXES_NOT_FOUND);
    }

    @Test
    public void parse_missingEndDate_throwsParseException() {
        String userInput = " sd/01-03-2025 s=name";
        assertParseFailure(parser, userInput, FilterDateCommandParser.MESSAGE_REQUIRED_PREFIXES_NOT_FOUND);
    }

    @Test
    public void parse_startDateAfterEndDate_throwsParseException() {
        String userInput = " sd/01-04-2025 ed/01-03-2025";
        assertParseFailure(parser, userInput, FilterDateCommandParser.MESSAGE_INVALID_START_DATE);
    }

    @Test
    public void parse_endDateBeyondMaxYears_throwsParseException() {
        String userInput = " sd/01-03-2025 ed/01-03-2031";
        assertParseFailure(parser, userInput, FilterDateCommandParser.MESSAGE_INVALID_END_DATE);
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        String userInput = " sd/2025-01-01 ed/2025-03-31 s/date"; // Incorrect date format
        assertParseFailure(parser, userInput, FilterDateCommandParser.MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    public void parse_nonExistentDate_throwsParseException() {
        String userInput = " sd/30-02-2025 ed/31-03-2025 s/date"; // Feb 30 does not exist
        assertParseFailure(parser, userInput, FilterDateCommandParser.MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    public void parse_leapYearDate_success() throws ParseException {
        String userInput = " sd/29-02-2024 ed/31-03-2024 s/date"; // 2024 is a leap year

        FilterDateCommand expectedCommand = new FilterDateCommand(
                LocalDate.of(2024, 2, 29),
                LocalDate.of(2024, 3, 31),
                "date"
        );

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_nonExistentSortType_throwsParseException() {
        String userInput = " sd/11-02-2025 ed/31-03-2025 s/value"; // value is not a valid sort type
        assertParseFailure(parser, userInput, FilterDateCommandParser.MESSAGE_INVALID_SORT);
    }

    @Test
    public void parse_noSortOrder_defaultsToDate() throws Exception {
        String userInput = " sd/01-03-2025 ed/31-03-2025";
        FilterDateCommand expectedCommand = new FilterDateCommand(
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 31),
                "date"
        );

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_caseInsensitiveSortOrder_valid() throws Exception {
        String userInput = " sd/01-03-2025 ed/31-03-2025 s/NaMe"; // Mixed lower and upper case
        FilterDateCommand expectedCommand = new FilterDateCommand(
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 31),
                "name"
        );

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_extraSpaces_trimmedCorrectly() throws Exception {
        String userInput = "  sd/01-03-2025   ed/31-03-2025   s/name   ";
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
        assertParseFailure(parser, userInput, FilterDateCommandParser.MESSAGE_REQUIRED_PREFIXES_NOT_FOUND);
    }

    @Test
    public void parse_startDateEqualsEndDate_valid() throws Exception {
        String userInput = " sd/01-03-2025 ed/01-03-2025 s/date"; // Same start and end date
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
        LocalDate endDate = LocalDate.of(2030, 3, 1); // Max limit of 5 years

        String userInput = " sd/01-03-2025 ed/01-03-2030 s/date";
        FilterDateCommand expectedCommand = new FilterDateCommand(startDate, endDate, "date");

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String userInput = " sd/01-01-2025 ed/31-12-2025 s/date sd/15-01-2025"; // Conflicting start dates
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_START_DATE));
    }
}
