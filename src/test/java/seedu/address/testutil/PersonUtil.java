package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENEWAL_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.FindCommand.FindPersonsPredicate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Policy;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(PREFIX_POLICY + person.getPolicy().policyNumber + " ");
        sb.append(PREFIX_RENEWAL_DATE + person.getRenewalDate() + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getPolicy().ifPresent(policy -> sb.append(PREFIX_POLICY).append(policy.policyNumber).append(" "));
        descriptor.getRenewalDate()
                .ifPresent(renewalDate -> sb.append(PREFIX_RENEWAL_DATE).append(renewalDate)
                .append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code FindPersonsPredicate}'s predicate.
     */
    public static String getFindPersonDetails(FindPersonsPredicate predicate) {
        StringBuilder sb = new StringBuilder();
        if (predicate.getNamePredicate().isPresent()) {
            if (predicate.getNamePredicate().get().getNames().isPresent()) {
                Set<Name> names = predicate.getNamePredicate().get().getNames().get();
                names.forEach(s -> sb.append(PREFIX_NAME).append(s.fullName).append(" "));
            }
        }
        if (predicate.getPhonePredicate().isPresent()) {
            if (predicate.getPhonePredicate().get().getNumbers().isPresent()) {
                Set<Phone> numbers = predicate.getPhonePredicate().get().getNumbers().get();
                numbers.forEach(s -> sb.append(PREFIX_PHONE).append(s.value).append(" "));
            }
        }
        if (predicate.getEmailPredicate().isPresent()) {
            if (predicate.getEmailPredicate().get().getEmails().isPresent()) {
                Set<Email> emails = predicate.getEmailPredicate().get().getEmails().get();
                emails.forEach(s -> sb.append(PREFIX_EMAIL).append(s.value).append(" "));
            }
        }
        if (predicate.getAddressPredicate().isPresent()) {
            if (predicate.getAddressPredicate().get().getAddresses().isPresent()) {
                Set<Address> addresses = predicate.getAddressPredicate().get().getAddresses().get();
                addresses.forEach(s -> sb.append(PREFIX_ADDRESS).append(s.value).append(" "));
            }
        }
        if (predicate.getPolicyPredicate().isPresent()) {
            if (predicate.getPolicyPredicate().get().getPolicies().isPresent()) {
                Set<Policy> numbers = predicate.getPolicyPredicate().get().getPolicies().get();
                numbers.forEach(s -> sb.append(PREFIX_POLICY).append(s.policyNumber).append(" "));
            }
        }
        if (predicate.getTagPredicate().isPresent()) {
            if (predicate.getTagPredicate().get().getTags().isPresent()) {
                Set<Tag> tags = predicate.getTagPredicate().get().getTags().get();
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
