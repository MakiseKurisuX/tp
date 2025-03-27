package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand.FindPersonsPredicate;
import seedu.address.model.person.Address;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PhoneContainsNumbersPredicate;
import seedu.address.model.person.Policy;
import seedu.address.model.person.PolicyContainsNumbersPredicate;
import seedu.address.model.person.PolicyTypeContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building FindPersonsPredicate objects.
 */
public class FindPersonsPredicateBuilder {

    private FindPersonsPredicate predicate;

    public FindPersonsPredicateBuilder() {
        predicate = new FindPersonsPredicate();
    }

    public FindPersonsPredicateBuilder(FindPersonsPredicate predicate) {
        this.predicate = new FindPersonsPredicate(predicate);
    }

    /**
     * Returns an {@code FindPersonsPredicate} with predicates containing {@code person}'s details
     */
    public FindPersonsPredicateBuilder(Person person) {
        predicate = new FindPersonsPredicate();
        predicate.setNamePredicate(new NameContainsKeywordsPredicate(Set.of(person.getName())));
        predicate.setPhonePredicate(new PhoneContainsNumbersPredicate(Set.of(person.getPhone())));
        predicate.setEmailPredicate(new EmailContainsKeywordsPredicate(Set.of(person.getEmail())));
        predicate.setAddressPredicate(new AddressContainsKeywordsPredicate(Set.of(person.getAddress())));
        predicate.setPolicyPredicate(new PolicyContainsNumbersPredicate(Set.of(person.getPolicy())));
    }

    /**
     * Parses the {@code names} into a {@code NameContainsKeywordsPredicate} and set it to the
     * {@code FindPersonsPredicate} that we are building.
     */
    public FindPersonsPredicateBuilder withNames(String... names) {
        Set<Name> nameSet = Stream.of(names).map(Name::new).collect(Collectors.toSet());
        predicate.setNamePredicate(new NameContainsKeywordsPredicate(nameSet));
        return this;
    }

    /**
     * Parses the {@code phones} into a {@code PhoneContainsNumbersPredicate} and set it to the
     * {@code FindPersonsPredicate} that we are building.
     */
    public FindPersonsPredicateBuilder withPhones(String... phones) {
        Set<Phone> phoneSet = Stream.of(phones).map(Phone::new).collect(Collectors.toSet());
        predicate.setPhonePredicate(new PhoneContainsNumbersPredicate(phoneSet));
        return this;
    }

    /**
     * Parses the {@code emails} into a {@code EmailContainsNumbersPredicate} and set it to the
     * {@code FindPersonsPredicate} that we are building.
     */
    public FindPersonsPredicateBuilder withEmails(String... emails) {
        Set<Email> emailSet = Stream.of(emails).map(Email::new).collect(Collectors.toSet());
        predicate.setEmailPredicate(new EmailContainsKeywordsPredicate(emailSet));
        return this;
    }

    /**
     * Parses the {@code addresses} into a {@code AddressContainsKeywordsPredicate} and set it to the
     * {@code FindPersonsPredicate} that we are building.
     */
    public FindPersonsPredicateBuilder withAddresses(String... addresses) {
        Set<Address> addressSet = Stream.of(addresses).map(Address::new).collect(Collectors.toSet());
        predicate.setAddressPredicate(new AddressContainsKeywordsPredicate(addressSet));
        return this;
    }

    /**
     * Parses the {@code policies} into a {@code PolicyContainsNumbersPredicate} and set it to the
     * {@code FindPersonsPredicate} that we are building.
     */
    public FindPersonsPredicateBuilder withPolicies(String... policies) {
        Set<Policy> policySet = Stream.of(policies)
                .map(Policy::new)
                .collect(Collectors.toSet());
        predicate.setPolicyPredicate(new PolicyContainsNumbersPredicate(policySet));
        return this;
    }

    /**
     * Parses the {@code policyTypes} into a {@code PolicyTypeContainsKeywordsPredicate} and set it to the
     * {@code FindPersonsPredicate} that we are building.
     */
    public FindPersonsPredicateBuilder withPolicyTypes(String... policyTypes) {
        Set<String> policyTypeSet = Stream.of(policyTypes)
                .collect(Collectors.toSet());
        predicate.setPolicyTypePredicate(new PolicyTypeContainsKeywordsPredicate(policyTypeSet));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code TagContainsKeywordsPredicate} and sets it to the
     * {@code FindPersonsPredicate} that we are building.
     */
    public FindPersonsPredicateBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        predicate.setTagPredicate(new TagContainsKeywordsPredicate(tagSet));
        return this;
    }

    public FindPersonsPredicate build() {
        return predicate;
    }
}
