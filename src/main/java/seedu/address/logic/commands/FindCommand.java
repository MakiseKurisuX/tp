package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsNumbersPredicate;
import seedu.address.model.person.PolicyContainsNumbersPredicate;

/**
 * Finds and lists all persons in address book whose details contains any of the argument information.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose details contain any of "
            + "the specified information (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [" + PREFIX_NAME + "NAME]... "
            + "[" + PREFIX_PHONE + "PHONE]... "
            + "[" + PREFIX_ADDRESS + "ADDRESS]... "
            + "[" + PREFIX_POLICY + "POLICY_NUMBER]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "alice "
            + PREFIX_NAME + "bob "
            + PREFIX_NAME + "charlie "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_POLICY + "104343 ";

    public static final String MESSAGE_NOT_FOUND = "At least one field to find must be provided.";

    private final FindPersonsPredicate predicate;

    public FindCommand(FindPersonsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

    /**
     * Tests that a {@code Person}'s details match any of the predicates given.
     */
    public static class FindPersonsPredicate implements Predicate<Person> {
        private NameContainsKeywordsPredicate namePredicate;
        private PhoneContainsNumbersPredicate phonePredicate;
        private AddressContainsKeywordsPredicate addressPredicate;
        private PolicyContainsNumbersPredicate policyPredicate;

        public FindPersonsPredicate() {}

        /**
         * Copy constructor.
         */
        public FindPersonsPredicate(FindCommand.FindPersonsPredicate toCopy) {
            setNamePredicate(toCopy.namePredicate);
            setPhonePredicate(toCopy.phonePredicate);
            setAddressPredicate(toCopy.addressPredicate);
            setPolicyPredicate(toCopy.policyPredicate);
        }

        /**
         * Returns true if at least one predicate is set.
         */
        public boolean isAnyPredicateSet() {
            return CollectionUtil.isAnyNonNull(namePredicate, phonePredicate, addressPredicate, policyPredicate);
        }

        public void setNamePredicate(NameContainsKeywordsPredicate namePredicate) {
            this.namePredicate = namePredicate;
        }

        public Optional<NameContainsKeywordsPredicate> getNamePredicate() {
            return Optional.ofNullable(namePredicate);
        }

        public void setPhonePredicate(PhoneContainsNumbersPredicate phonePredicate) {
            this.phonePredicate = phonePredicate;
        }

        public Optional<PhoneContainsNumbersPredicate> getPhonePredicate() {
            return Optional.ofNullable(phonePredicate);
        }

        public void setAddressPredicate(AddressContainsKeywordsPredicate addressPredicate) {
            this.addressPredicate = addressPredicate;
        }

        public Optional<AddressContainsKeywordsPredicate> getAddressPredicate() {
            return Optional.ofNullable(addressPredicate);
        }

        public void setPolicyPredicate(PolicyContainsNumbersPredicate policyPredicate) {
            this.policyPredicate = policyPredicate;
        }

        public Optional<PolicyContainsNumbersPredicate> getPolicyPredicate() {
            return Optional.ofNullable(policyPredicate);
        }

        @Override
        public boolean test(Person person) {
            boolean nameMatch = getNamePredicate().map(pred -> pred.test(person)).orElse(false);
            boolean phoneMatch = getPhonePredicate().map(pred -> pred.test(person)).orElse(false);
            boolean addressMatch = getAddressPredicate().map(pred -> pred.test(person)).orElse(false);
            boolean policyMatch = getPolicyPredicate().map(pred -> pred.test(person)).orElse(false);
            // Match if any predicate matches (OR logic)
            return nameMatch || phoneMatch || addressMatch || policyMatch;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FindCommand.FindPersonsPredicate)) {
                return false;
            }

            FindCommand.FindPersonsPredicate otherFindPersonsPredicate = (FindCommand.FindPersonsPredicate) other;
            return Objects.equals(namePredicate, otherFindPersonsPredicate.namePredicate)
                    && Objects.equals(phonePredicate, otherFindPersonsPredicate.phonePredicate)
                    && Objects.equals(addressPredicate, otherFindPersonsPredicate.addressPredicate)
                    && Objects.equals(policyPredicate, otherFindPersonsPredicate.policyPredicate);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("namePredicate", namePredicate)
                    .add("phonePredicate", phonePredicate)
                    .add("addressPredicate", addressPredicate)
                    .add("policyPredicate", policyPredicate)
                    .toString();
        }
    }
}
