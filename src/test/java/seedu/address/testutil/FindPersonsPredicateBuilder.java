package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand.FindPersonsPredicate;
import seedu.address.model.person.Address;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PhoneContainsNumbersPredicate;
import seedu.address.model.person.Policy;
import seedu.address.model.person.PolicyContainsNumbersPredicate;

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
        Set<Policy> policySet = Stream.of(policies).map(Policy::new).collect(Collectors.toSet());
        predicate.setPolicyPredicate(new PolicyContainsNumbersPredicate(policySet));
        return this;
    }

    public FindPersonsPredicate build() {
        return predicate;
    }
}
