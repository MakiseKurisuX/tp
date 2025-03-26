package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POLICY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SORT_ORDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SORT_ORDER_DESC_NAME;
import static seedu.address.logic.commands.CommandTestUtil.SORT_ORDER_DESC_TAG;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Policy;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.FindPersonsPredicateBuilder;

public class FindCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "", FindCommand.MESSAGE_NOT_FOUND);

        // empty argument specified
        assertParseFailure(parser, "     ", FindCommand.MESSAGE_NOT_FOUND);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, INVALID_POLICY_DESC, Policy.MESSAGE_CONSTRAINTS); // invalid policy
        assertParseFailure(parser, INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid name
        assertParseFailure(parser, INVALID_PHONE_DESC + NAME_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_PHONE_DESC + INVALID_EMAIL_DESC
                + INVALID_ADDRESS_DESC + INVALID_POLICY_DESC + INVALID_TAG_DESC, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = PHONE_DESC_BOB + NAME_DESC_AMY + POLICY_DESC_AMY + ADDRESS_DESC_BOB
                + EMAIL_DESC_AMY + TAG_DESC_FRIEND;

        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder().withNames(VALID_NAME_AMY)
                .withPhones(VALID_PHONE_BOB).withEmails(VALID_EMAIL_AMY).withAddresses(VALID_ADDRESS_BOB)
                .withPolicies(VALID_POLICY_AMY).withTags(VALID_TAG_FRIEND).build();
        FindCommand expectedCommand = new FindCommand(predicate, FindCommand.DEFAULT_SORT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = NAME_DESC_AMY + PHONE_DESC_BOB;

        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder().withNames(VALID_NAME_AMY)
                .withPhones(VALID_PHONE_BOB).build();
        FindCommand expectedCommand = new FindCommand(predicate, FindCommand.DEFAULT_SORT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String userInput = NAME_DESC_AMY;
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder().withNames(VALID_NAME_AMY)
                .build();
        FindCommand expectedCommand = new FindCommand(predicate, FindCommand.DEFAULT_SORT);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = PHONE_DESC_AMY;
        predicate = new FindPersonsPredicateBuilder().withPhones(VALID_PHONE_AMY).build();
        expectedCommand = new FindCommand(predicate, FindCommand.DEFAULT_SORT);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = EMAIL_DESC_AMY;
        predicate = new FindPersonsPredicateBuilder().withEmails(VALID_EMAIL_AMY).build();
        expectedCommand = new FindCommand(predicate, FindCommand.DEFAULT_SORT);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = ADDRESS_DESC_AMY;
        predicate = new FindPersonsPredicateBuilder().withAddresses(VALID_ADDRESS_AMY).build();
        expectedCommand = new FindCommand(predicate, FindCommand.DEFAULT_SORT);
        assertParseSuccess(parser, userInput, expectedCommand);

        // policy
        userInput = POLICY_DESC_AMY;
        predicate = new FindPersonsPredicateBuilder().withPolicies(VALID_POLICY_AMY).build();
        expectedCommand = new FindCommand(predicate, FindCommand.DEFAULT_SORT);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = TAG_DESC_FRIEND;
        predicate = new FindPersonsPredicateBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new FindCommand(predicate, FindCommand.DEFAULT_SORT);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_success() {
        // multiple valid fields repeated
        String userInput = NAME_DESC_AMY + PHONE_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + EMAIL_DESC_BOB;
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder()
                .withNames(VALID_NAME_AMY, VALID_NAME_BOB).withPhones(VALID_PHONE_AMY, VALID_PHONE_BOB)
                .withEmails(VALID_EMAIL_AMY, VALID_EMAIL_BOB).build();
        FindCommand expectedCommand = new FindCommand(predicate, FindCommand.DEFAULT_SORT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by invalid
        String userInput = INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Phone.MESSAGE_CONSTRAINTS);

        // invalid followed by valid
        userInput = PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values
        userInput = INVALID_PHONE_DESC + INVALID_NAME_DESC
                + INVALID_PHONE_DESC + INVALID_NAME_DESC
                + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validSortOrderSpecified_success() {
        // sort by name
        String userInput = NAME_DESC_AMY + PHONE_DESC_BOB + SORT_ORDER_DESC_NAME;
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder().withNames(VALID_NAME_AMY)
                .withPhones(VALID_PHONE_BOB).build();
        FindCommand expectedCommand = new FindCommand(predicate, FindCommand.SORT_BY_NAME);

        assertParseSuccess(parser, userInput, expectedCommand);

        // sort by tag
        userInput = NAME_DESC_AMY + PHONE_DESC_BOB + SORT_ORDER_DESC_TAG;
        expectedCommand = new FindCommand(predicate, FindCommand.SORT_BY_TAG);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidSortOrderSpecified_failure() {
        // invalid sort order
        String userInput = NAME_DESC_AMY + PHONE_DESC_BOB + INVALID_SORT_ORDER_DESC;
        assertParseFailure(parser, userInput, FindCommandParser.MESSAGE_INVALID_SORT);
    }

}
