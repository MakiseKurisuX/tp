package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENEWAL_DATE;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Policy;
import seedu.address.model.person.PolicyType;
import seedu.address.model.person.RenewalDate;

/**
 * Updates the renewal date of a client in the address book.
 */
public class RenewCommand extends Command {

    public static final String COMMAND_WORD = "renew";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the renewal date of a client "
            + "identified by their policy number. "
            + "Parameters: "
            + PREFIX_POLICY + "POLICY_NUMBER "
            + PREFIX_RENEWAL_DATE + "DD-MM-YYYY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_POLICY + "123456 "
            + PREFIX_RENEWAL_DATE + "31-12-2025";

    public static final String MESSAGE_RENEW_SUCCESS = "Updated renewal date for policy %1$s: %2$s";
    public static final String MESSAGE_POLICY_NOT_FOUND = "No client with policy number %1$s was found";
    public static final String MESSAGE_MULTIPLE_POLICIES = "Multiple clients with policy number %1$s found. "
            + "Please use edit command with index instead.";

    private final String policyNumber;
    private final RenewalDate newRenewalDate;

    /**
     * Creates a RenewCommand to update the specified client's renewal date
     */
    public RenewCommand(String policyNumber, RenewalDate newRenewalDate) {
        requireNonNull(policyNumber);
        requireNonNull(newRenewalDate);
        this.policyNumber = policyNumber;
        this.newRenewalDate = newRenewalDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Find the person with the specified policy number
        List<Person> matchingPersons = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getPolicy().getPolicyNumber().equals(policyNumber)).toList();

        if (matchingPersons.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_POLICY_NOT_FOUND, policyNumber));
        }
        if (matchingPersons.size() > 1) {
            throw new CommandException(String.format(MESSAGE_MULTIPLE_POLICIES, policyNumber));
        }
        // NOTE: This is here because I do not want to use more
        // LOC to match the new no duplicate policy number requirement
        Person personToUpdate = matchingPersons.get(0);
        Person updatedPerson = createUpdatedPerson(personToUpdate);
        model.setPerson(personToUpdate, updatedPerson);
        return new CommandResult(String.format(MESSAGE_RENEW_SUCCESS, policyNumber, newRenewalDate));
    }

    /**
     * Creates and returns a {@code Person} with the updated renewal date.
     */
    private Person createUpdatedPerson(Person personToUpdate) {
        assert personToUpdate != null;
        String policyNumber = personToUpdate.getPolicy().getPolicyNumber();
        PolicyType policyType = personToUpdate.getPolicy().getType();
        Policy updatedPolicy = new Policy(policyNumber, newRenewalDate, policyType);
        return new Person(
                personToUpdate.getName(),
                personToUpdate.getPhone(),
                personToUpdate.getEmail(),
                personToUpdate.getAddress(),
                updatedPolicy,
                personToUpdate.getNote(),
                personToUpdate.getTags()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RenewCommand)) {
            return false;
        }
        RenewCommand otherRenewCommand = (RenewCommand) other;
        return policyNumber.equals(otherRenewCommand.policyNumber)
                && newRenewalDate.equals(otherRenewCommand.newRenewalDate);
    }
}
