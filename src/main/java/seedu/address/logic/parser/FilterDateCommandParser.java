package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDate;

import seedu.address.logic.commands.FilterDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FilterDateCommand object.
 */
public class FilterDateCommandParser implements Parser<FilterDateCommand> {

    public static final String MESSAGE_INVALID_DATE_FORMAT =
            "Invalid date format: Must be valid date in DD-MM-YYYY format";
    public static final String MESSAGE_INVALID_START_DATE =
            "Invalid start date: Must be valid date in DD-MM-YYYY format "
                    + "and less than or equal to end date.";
    public static final String MESSAGE_INVALID_END_DATE =
            "Invalid end date: Must be valid date in DD-MM-YYYY format "
            + "and more than or equal to start date, and within 5 years from the start date";
    public static final String MESSAGE_INVALID_SORT =
            "Invalid sort. Use 'date' or 'name' (case-insensitive)";
    public static final String MESSAGE_REQUIRED_PREFIXES_NOT_FOUND =
            "Start date (sd/) and end date (ed/) are required.";
    private static final int MAX_YEARS_RANGE = 5;

    @Override
    public FilterDateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_SORT_ORDER);

        if (argMultimap.getValue(PREFIX_START_DATE).isEmpty() || argMultimap.getValue(PREFIX_END_DATE).isEmpty()) {
            throw new ParseException(MESSAGE_REQUIRED_PREFIXES_NOT_FOUND);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_SORT_ORDER);

        LocalDate startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
        LocalDate endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get());

        if (startDate.isAfter(endDate)) {
            throw new ParseException(MESSAGE_INVALID_START_DATE);
        }

        LocalDate maxAllowedDate = LocalDate.now().plusYears(MAX_YEARS_RANGE);
        if (endDate.isAfter(maxAllowedDate)) {
            throw new ParseException(MESSAGE_INVALID_END_DATE);
        }

        String sortOrder = argMultimap.getValue(PREFIX_SORT_ORDER).orElse("date").toLowerCase();
        if (!sortOrder.equals("date") && !sortOrder.equals("name")) {
            throw new ParseException(MESSAGE_INVALID_SORT);
        }

        return new FilterDateCommand(startDate, endDate, sortOrder);
    }
}
