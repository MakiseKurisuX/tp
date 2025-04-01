package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsNumbersPredicate;
import seedu.address.model.person.PolicyContainsNumbersPredicate;
import seedu.address.model.person.PolicyTypeContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose details contains any of the argument information.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    // to fix this in next push commit.
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose details contain any of "
            + "the specified information (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [" + PREFIX_NAME + "NAME]... "
            + "[" + PREFIX_PHONE + "PHONE]... "
            + "[" + PREFIX_EMAIL + "EMAIL]... "
            + "[" + PREFIX_ADDRESS + "ADDRESS]... "
            + "[" + PREFIX_POLICY + "POLICY_NUMBER]...\n"
            + "[" + PREFIX_POLICY_TYPE + "POLICY_TYPE]..."
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_SORT_ORDER + "SORT_ORDER]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "alice "
            + PREFIX_NAME + "bob "
            + PREFIX_NAME + "charlie "
            + PREFIX_EMAIL + "local-part@domain "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_POLICY + "104343 "
            + PREFIX_POLICY_TYPE + "Life "
            + PREFIX_TAG + "friends "
            + PREFIX_SORT_ORDER + "tag";

    public static final String MESSAGE_NOT_FOUND = "At least one field to find must be provided.";

    public static final String DEFAULT_SORT = "name";
    public static final String SORT_BY_TAG = "tag";
    public static final String SORT_BY_NAME = "name";

    public static final Comparator<Person> NAME_COMPARATOR = Comparator.comparing(person ->
            person.getName().fullName);
    public static final Comparator<Person> TAG_COMPARATOR = Comparator.comparing((Person person) ->
            person.getTags().size()).reversed();

    private final FindPersonsPredicate predicate;
    private final String sortOrder;

    /**
     * Creates a FindCommand with the specified parameters.
     *
     * @param predicate The predicate to test if a person's details match any of the given information
     * @param sortOrder How to sort the results
     */
    public FindCommand(FindPersonsPredicate predicate, String sortOrder) {
        this.predicate = predicate;
        this.sortOrder = sortOrder.toLowerCase();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        model.updateSortedPersonList(getComparator());
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    private Comparator<Person> getComparator() {
        if (SORT_BY_TAG.equals(sortOrder)) {
            return TAG_COMPARATOR;
        } else {
            return NAME_COMPARATOR;
        }
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
        return predicate.equals(otherFindCommand.predicate) && sortOrder.equals(otherFindCommand.sortOrder);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("sortOrder", sortOrder)
                .toString();
    }

    /**
     * Represents a predicate used to filter persons by their details.
     * Multiple predicates can be set for one or more fields.
     */
    public static class FindPersonsPredicate implements Predicate<Person> {
        private NameContainsKeywordsPredicate namePredicate;
        private PhoneContainsNumbersPredicate phonePredicate;
        private EmailContainsKeywordsPredicate emailPredicate;
        private AddressContainsKeywordsPredicate addressPredicate;
        private PolicyContainsNumbersPredicate policyPredicate;
        private PolicyTypeContainsKeywordsPredicate policyTypePredicate;
        private TagContainsKeywordsPredicate tagPredicate;

        public FindPersonsPredicate() {
        }

        /**
         * Copy constructor.
         */
        public FindPersonsPredicate(FindPersonsPredicate toCopy) {
            setNamePredicate(toCopy.namePredicate);
            setPhonePredicate(toCopy.phonePredicate);
            setEmailPredicate(toCopy.emailPredicate);
            setAddressPredicate(toCopy.addressPredicate);
            setPolicyPredicate(toCopy.policyPredicate);
            setPolicyTypePredicate(toCopy.policyTypePredicate);
            setTagPredicate(toCopy.tagPredicate);
        }

        /**
         * Returns true if at least one predicate is set.
         */
        public boolean isAnyPredicateSet() {
            return CollectionUtil.isAnyNonNull(
                    namePredicate,
                    phonePredicate,
                    emailPredicate,
                    addressPredicate,
                    policyPredicate,
                    policyTypePredicate,
                    tagPredicate);
        }

        public void setNamePredicate(NameContainsKeywordsPredicate namePredicate) {
            this.namePredicate = namePredicate;
        }

        public void setPhonePredicate(PhoneContainsNumbersPredicate phonePredicate) {
            this.phonePredicate = phonePredicate;
        }

        public void setEmailPredicate(EmailContainsKeywordsPredicate emailPredicate) {
            this.emailPredicate = emailPredicate;
        }

        public void setAddressPredicate(AddressContainsKeywordsPredicate addressPredicate) {
            this.addressPredicate = addressPredicate;
        }

        public void setPolicyPredicate(PolicyContainsNumbersPredicate policyPredicate) {
            this.policyPredicate = policyPredicate;
        }

        public void setPolicyTypePredicate(PolicyTypeContainsKeywordsPredicate policyTypePredicate) {
            this.policyTypePredicate = policyTypePredicate;
        }

        public void setTagPredicate(TagContainsKeywordsPredicate tagPredicate) {
            this.tagPredicate = tagPredicate;
        }

        public Optional<NameContainsKeywordsPredicate> getNamePredicate() {
            return Optional.ofNullable(namePredicate);
        }

        public Optional<PhoneContainsNumbersPredicate> getPhonePredicate() {
            return Optional.ofNullable(phonePredicate);
        }

        public Optional<EmailContainsKeywordsPredicate> getEmailPredicate() {
            return Optional.ofNullable(emailPredicate);
        }

        public Optional<AddressContainsKeywordsPredicate> getAddressPredicate() {
            return Optional.ofNullable(addressPredicate);
        }

        public Optional<PolicyContainsNumbersPredicate> getPolicyPredicate() {
            return Optional.ofNullable(policyPredicate);
        }

        public Optional<PolicyTypeContainsKeywordsPredicate> getPolicyTypePredicate() {
            return Optional.ofNullable(policyTypePredicate);
        }

        public Optional<TagContainsKeywordsPredicate> getTagPredicate() {
            return Optional.ofNullable(tagPredicate);
        }

        @Override
        public boolean test(Person person) {
            boolean nameMatch = getNamePredicate().map(pred -> pred.test(person)).orElse(false);
            boolean phoneMatch = getPhonePredicate().map(pred -> pred.test(person)).orElse(false);
            boolean emailMatch = getEmailPredicate().map(pred -> pred.test(person)).orElse(false);
            boolean addressMatch = getAddressPredicate().map(pred -> pred.test(person)).orElse(false);
            boolean policyMatch = getPolicyPredicate().map(pred -> pred.test(person)).orElse(false);
            boolean policyTypeMatch = getPolicyTypePredicate().map(pred -> pred.test(person)).orElse(false);
            boolean tagMatch = getTagPredicate().map(pred -> pred.test(person)).orElse(false);
            // Match if any predicate matches (OR logic)
            return nameMatch || phoneMatch || emailMatch || addressMatch || policyMatch || policyTypeMatch || tagMatch;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FindPersonsPredicate)) {
                return false;
            }

            FindPersonsPredicate otherFindPersonsPredicate = (FindPersonsPredicate) other;
            return Objects.equals(namePredicate, otherFindPersonsPredicate.namePredicate)
                    && Objects.equals(phonePredicate, otherFindPersonsPredicate.phonePredicate)
                    && Objects.equals(emailPredicate, otherFindPersonsPredicate.emailPredicate)
                    && Objects.equals(addressPredicate, otherFindPersonsPredicate.addressPredicate)
                    && Objects.equals(policyPredicate, otherFindPersonsPredicate.policyPredicate)
                    && Objects.equals(policyTypePredicate, otherFindPersonsPredicate.policyTypePredicate)
                    && Objects.equals(tagPredicate, otherFindPersonsPredicate.tagPredicate);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("namePredicate", namePredicate)
                    .add("phonePredicate", phonePredicate)
                    .add("emailPredicate", emailPredicate)
                    .add("addressPredicate", addressPredicate)
                    .add("policyPredicate", policyPredicate)
                    .add("policyTypePredicate", policyTypePredicate)
                    .add("tagPredicate", tagPredicate)
                    .toString();
        }
    }
}
