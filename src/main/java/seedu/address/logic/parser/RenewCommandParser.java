package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENEWAL_DATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.RenewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Policy;
import seedu.address.model.person.RenewalDate;

/**
 * Parses input arguments and creates a new RenewCommand object
 */
public class RenewCommandParser implements Parser<RenewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RenewCommand
     * and returns a RenewCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public RenewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POLICY, PREFIX_RENEWAL_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_POLICY, PREFIX_RENEWAL_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenewCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY, PREFIX_RENEWAL_DATE);

        String policyNumber = argMultimap.getValue(PREFIX_POLICY).get();
        String renewalDate = argMultimap.getValue(PREFIX_RENEWAL_DATE).get();

        if (!Policy.isValidPolicy(policyNumber)) {
            throw new ParseException(Policy.MESSAGE_CONSTRAINTS);
        }

        if (!RenewalDate.isValidRenewalDate(renewalDate)) {
            throw new ParseException(RenewalDate.DATE_CONSTRAINTS);
        }

        return new RenewCommand(policyNumber, new RenewalDate(renewalDate));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
