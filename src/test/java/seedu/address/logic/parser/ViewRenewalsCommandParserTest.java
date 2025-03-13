package seedu.address.logic.parser;

import static seedu.address.logic.commands.ViewRenewalsCommand.DEFAULT_DAYS;
import static seedu.address.logic.commands.ViewRenewalsCommand.DEFAULT_SORT;
import static seedu.address.logic.commands.ViewRenewalsCommand.SORT_BY_DATE;
import static seedu.address.logic.commands.ViewRenewalsCommand.SORT_BY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_N_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewRenewalsCommand;

public class ViewRenewalsCommandParserTest {

    private ViewRenewalsCommandParser parser = new ViewRenewalsCommandParser();

    @Test
    public void parse_emptyArg_returnsViewRenewalsCommand() {
        // empty string
        ViewRenewalsCommand expectedCommand = new ViewRenewalsCommand(DEFAULT_DAYS, DEFAULT_SORT);
        assertParseSuccess(parser, "", expectedCommand);

        // whitespace only
        assertParseSuccess(parser, "   ", expectedCommand);
    }

    @Test
    public void parse_invalidDays_throwsParseException() {
        // negative days
        assertParseFailure(parser, " " + PREFIX_NEXT_N_DAYS + "-30",
                ViewRenewalsCommandParser.MESSAGE_INVALID_DAYS);

        // zero days
        assertParseFailure(parser, " " + PREFIX_NEXT_N_DAYS + "0",
                ViewRenewalsCommandParser.MESSAGE_INVALID_DAYS);

        // more than 365 days
        assertParseFailure(parser, " " + PREFIX_NEXT_N_DAYS + "366",
                ViewRenewalsCommandParser.MESSAGE_INVALID_DAYS);

        // non-numeric days
        assertParseFailure(parser, " " + PREFIX_NEXT_N_DAYS + "abc",
                ViewRenewalsCommandParser.MESSAGE_INVALID_DAYS);
    }

    @Test
    public void parse_invalidSortOrder_throwsParseException() {
        // invalid sort order
        assertParseFailure(parser, " " + PREFIX_SORT_ORDER + "invalid",
                ViewRenewalsCommandParser.MESSAGE_INVALID_SORT);

        // empty sort order
        assertParseFailure(parser, " " + PREFIX_SORT_ORDER,
                ViewRenewalsCommandParser.MESSAGE_INVALID_SORT);
    }

    @Test
    public void parse_validDays_returnsViewRenewalsCommand() {
        // minimum days (1)
        ViewRenewalsCommand expectedCommand1 = new ViewRenewalsCommand(1, DEFAULT_SORT);
        assertParseSuccess(parser, " " + PREFIX_NEXT_N_DAYS + "1", expectedCommand1);

        // maximum days (365)
        ViewRenewalsCommand expectedCommand2 = new ViewRenewalsCommand(365, DEFAULT_SORT);
        assertParseSuccess(parser, " " + PREFIX_NEXT_N_DAYS + "365", expectedCommand2);

        // typical value
        ViewRenewalsCommand expectedCommand3 = new ViewRenewalsCommand(30, DEFAULT_SORT);
        assertParseSuccess(parser, " " + PREFIX_NEXT_N_DAYS + "30", expectedCommand3);
    }

    @Test
    public void parse_validSortOrder_returnsViewRenewalsCommand() {
        // sort by date
        ViewRenewalsCommand expectedCommand1 = new ViewRenewalsCommand(DEFAULT_DAYS, SORT_BY_DATE);
        assertParseSuccess(parser, " " + PREFIX_SORT_ORDER + "date", expectedCommand1);

        // sort by name
        ViewRenewalsCommand expectedCommand2 = new ViewRenewalsCommand(DEFAULT_DAYS, SORT_BY_NAME);
        assertParseSuccess(parser, " " + PREFIX_SORT_ORDER + "name", expectedCommand2);

        // case insensitive
        ViewRenewalsCommand expectedCommand3 = new ViewRenewalsCommand(DEFAULT_DAYS, SORT_BY_DATE);
        assertParseSuccess(parser, " " + PREFIX_SORT_ORDER + "DATE", expectedCommand3);
    }

    @Test
    public void parse_validDaysAndSortOrder_returnsViewRenewalsCommand() {
        // typical values
        ViewRenewalsCommand expectedCommand = new ViewRenewalsCommand(60, SORT_BY_NAME);
        assertParseSuccess(parser, " " + PREFIX_NEXT_N_DAYS + "60" + " " + PREFIX_SORT_ORDER + "name",
                expectedCommand);

        // different order of parameters
        assertParseSuccess(parser, " " + PREFIX_SORT_ORDER + "name" + " " + PREFIX_NEXT_N_DAYS + "60",
                expectedCommand);
    }
}
