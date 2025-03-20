package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Filters clients based on policy renewal date range.
 */
public class FilterDateCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters clients whose policy renewal date "
            + "falls within the specified date range.\n"
            + "Parameters: sd/START_DATE ed/END_DATE [s/SORT_ORDER]\n"
            + "Example: " + COMMAND_WORD + " sd/2025-03-01 ed/2025-03-31 s/name";

    public static final String MESSAGE_NO_RESULTS = "No renewals found between %s and %s.";

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String sortOrder;

    /**
     * Constructs a {@code FilterDateCommand} with the specified start date, end date, and sort order.
     * The {@code startDate} and {@code endDate} must not be null. If {@code sortOrder} is null,
     * it defaults to "date".
     *
     * @param startDate The starting date for filtering. Must not be null.
     * @param endDate   The ending date for filtering. Must not be null.
     * @param sortOrder The sort order for filtering. If null, defaults to "date".
     * @throws NullPointerException If {@code startDate} or {@code endDate} is null.
     */
    public FilterDateCommand(LocalDate startDate, LocalDate endDate, String sortOrder) {
        requireNonNull(startDate);
        requireNonNull(endDate);
        this.startDate = startDate;
        this.endDate = endDate;
        this.sortOrder = (sortOrder != null) ? sortOrder : "date";
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Person> renewalFilterPredicate = person ->
                person.getPolicy().isRenewalDueWithinDateRange(startDate, endDate);

        model.updateRenewalsList(renewalFilterPredicate);
        model.updateSortedRenewalsList(sortFilterDate());

        if (model.getRenewalsList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_RESULTS, startDate, endDate));
        }
        return new CommandResult(String.format(ViewRenewalsCommand.MESSAGE_SUCCESS, model.getRenewalsList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FilterDateCommand)) {
            return false;
        }
        FilterDateCommand otherCommand = (FilterDateCommand) other;
        return startDate.equals(otherCommand.startDate)
                && endDate.equals(otherCommand.endDate)
                && sortOrder.equals(otherCommand.sortOrder);
    }

    private Comparator<Person> sortFilterDate() {
        if (sortOrder.equals("date")) {
            return Comparator.comparing(Person::getRenewalDateValue);
        } else {
            // Sort by name (alphabetical order)
            return Comparator.comparing(person -> person.getName().fullName);
        }
    }
}
