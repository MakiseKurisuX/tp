package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_N_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;

import seedu.address.logic.commands.ViewRenewalsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewRenewalsCommand object
 */
public class ViewRenewalsCommandParser implements Parser<ViewRenewalsCommand> {

    public static final String MESSAGE_INVALID_DAYS = "NEXT_N_DAYS must be a positive number between 1 and 365";
    public static final String MESSAGE_INVALID_SORT = "Invalid sort order. Use 'date' or 'name'";

    /**
     * Parses the given {@code String} of arguments in the context of the ViewRenewalsCommand
     * and returns a ViewRenewalsCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ViewRenewalsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NEXT_N_DAYS, PREFIX_SORT_ORDER);

        int days = ViewRenewalsCommand.DEFAULT_DAYS;
        String sortOrder = ViewRenewalsCommand.DEFAULT_SORT;

        if (argMultimap.getValue(PREFIX_NEXT_N_DAYS).isPresent()) {
            try {
                days = Integer.parseInt(argMultimap.getValue(PREFIX_NEXT_N_DAYS).get());
                if (days < 1 || days > 365) {
                    throw new ParseException(MESSAGE_INVALID_DAYS);
                }
            } catch (NumberFormatException e) {
                throw new ParseException(MESSAGE_INVALID_DAYS);
            }
        }

        if (argMultimap.getValue(PREFIX_SORT_ORDER).isPresent()) {
            sortOrder = argMultimap.getValue(PREFIX_SORT_ORDER).get().toLowerCase();
            if (!isValidSortOrder(sortOrder)) {
                throw new ParseException(MESSAGE_INVALID_SORT);
            }
        }

        return new ViewRenewalsCommand(days, sortOrder);
    }

    private boolean isValidSortOrder(String sortOrder) {
        return ViewRenewalsCommand.SORT_BY_DATE.equals(sortOrder)
                || ViewRenewalsCommand.SORT_BY_NAME.equals(sortOrder);
    }
}
