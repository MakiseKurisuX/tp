package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POLICY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RENEWAL_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RENEWAL_DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENEWAL_DATE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENEWAL_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.RenewCommand;
import seedu.address.model.person.Policy;
import seedu.address.model.person.RenewalDate;

public class RenewCommandParserTest {

    private RenewCommandParser parser = new RenewCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Valid policy number and renewal date
        assertParseSuccess(parser, POLICY_DESC_AMY + RENEWAL_DATE_DESC_AMY,
                new RenewCommand(VALID_POLICY_AMY, new RenewalDate(VALID_RENEWAL_DATE_AMY)));
        // whitespace only preamble
        assertParseSuccess(parser, "  " + POLICY_DESC_AMY + RENEWAL_DATE_DESC_AMY,
                new RenewCommand(VALID_POLICY_AMY, new RenewalDate(VALID_RENEWAL_DATE_AMY)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenewCommand.MESSAGE_USAGE);

        // missing policy prefix
        assertParseFailure(parser, VALID_POLICY_AMY + RENEWAL_DATE_DESC_AMY, expectedMessage);

        // missing renewal date prefix
        assertParseFailure(parser, POLICY_DESC_AMY + VALID_RENEWAL_DATE_AMY, expectedMessage);

        // missing both prefixes
        assertParseFailure(parser, VALID_POLICY_AMY + VALID_RENEWAL_DATE_AMY, expectedMessage);

        // missing renewal date value
        assertParseFailure(parser, POLICY_DESC_AMY + PREFIX_RENEWAL_DATE + " ", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid policy number
        assertParseFailure(parser, INVALID_POLICY_DESC + RENEWAL_DATE_DESC_AMY, Policy.MESSAGE_CONSTRAINTS);

        // invalid renewal date
        assertParseFailure(parser, POLICY_DESC_AMY + INVALID_RENEWAL_DATE_DESC, RenewalDate.DATE_CONSTRAINTS);

        // invalid policy number and invalid renewal date
        assertParseFailure(parser, INVALID_POLICY_DESC + INVALID_RENEWAL_DATE_DESC, Policy.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // duplicate policy prefix
        assertParseFailure(parser, POLICY_DESC_AMY + RENEWAL_DATE_DESC_AMY + POLICY_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POLICY));

        // duplicate renewal date prefix
        assertParseFailure(parser, POLICY_DESC_AMY + RENEWAL_DATE_DESC_AMY + RENEWAL_DATE_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RENEWAL_DATE));

        // both prefixes duplicated
        assertParseFailure(parser, POLICY_DESC_AMY + RENEWAL_DATE_DESC_AMY + POLICY_DESC_AMY + RENEWAL_DATE_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POLICY, PREFIX_RENEWAL_DATE));
    }
}
