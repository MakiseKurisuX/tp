package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindPersonsPredicate;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsNumbersPredicate;
import seedu.address.model.person.PolicyContainsNumbersPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String MESSAGE_INVALID_SORT = "Invalid sort order. Use 'name' or 'tag'";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                    PREFIX_ADDRESS, PREFIX_POLICY, PREFIX_TAG, PREFIX_SORT_ORDER);

        String sortOrder = FindCommand.DEFAULT_SORT;

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        FindCommand.FindPersonsPredicate findPersonsPredicate = new FindPersonsPredicate();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            findPersonsPredicate.setNamePredicate(new NameContainsKeywordsPredicate(
                    ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_NAME))));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            findPersonsPredicate.setPhonePredicate(new PhoneContainsNumbersPredicate(
                    ParserUtil.parsePhones(argMultimap.getAllValues(PREFIX_PHONE))));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            findPersonsPredicate.setEmailPredicate(new EmailContainsKeywordsPredicate(
                    ParserUtil.parseEmails(argMultimap.getAllValues(PREFIX_EMAIL))));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            findPersonsPredicate.setAddressPredicate(new AddressContainsKeywordsPredicate(
                    ParserUtil.parseAddresses(argMultimap.getAllValues(PREFIX_ADDRESS))));
        }
        if (argMultimap.getValue(PREFIX_POLICY).isPresent()) {
            findPersonsPredicate.setPolicyPredicate(new PolicyContainsNumbersPredicate(
                    ParserUtil.parsePolicies(argMultimap.getAllValues(PREFIX_POLICY))));
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            findPersonsPredicate.setTagPredicate(new TagContainsKeywordsPredicate(
                    ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG))));
        }

        if (!findPersonsPredicate.isAnyPredicateSet()) {
            throw new ParseException(FindCommand.MESSAGE_NOT_FOUND);
        }

        if (argMultimap.getValue(PREFIX_SORT_ORDER).isPresent()) {
            sortOrder = argMultimap.getValue(PREFIX_SORT_ORDER).get().toLowerCase();
            if (!isValidSortOrder(sortOrder)) {
                throw new ParseException(MESSAGE_INVALID_SORT);
            }
        }

        return new FindCommand(findPersonsPredicate, sortOrder);
    }

    private boolean isValidSortOrder(String sortOrder) {
        return FindCommand.SORT_BY_NAME.equals(sortOrder)
                || FindCommand.SORT_BY_TAG.equals(sortOrder);
    }

}
