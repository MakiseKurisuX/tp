package seedu.address.testutil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Policy;
import seedu.address.model.person.PolicyType;
import seedu.address.model.person.RenewalDate;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_POLICY = "654321";
    public static final String DEFAULT_RENEWAL_DATE = LocalDate.now().plusYears(1)
            .format(RenewalDate.DATE_FORMATTER);

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Policy policy;
    private Note note;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        policy = new Policy(DEFAULT_POLICY, new RenewalDate(DEFAULT_RENEWAL_DATE));
        note = Note.EMPTY;
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        policy = personToCopy.getPolicy();
        note = personToCopy.getNote();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Policy} of the {@code Person} that we are building.
     */
    public PersonBuilder withPolicy(String policy) {
        this.policy = new Policy(policy);
        return this;
    }

    /**
     * Sets the {@code Policy} of the {@code Person} that we are building with specific policy number and renewal date.
     */
    public PersonBuilder withPolicy(String policyNumber, String renewalDate) {
        this.policy = new Policy(policyNumber, new RenewalDate(renewalDate));
        return this;
    }

    /**
     * Sets the {@code Policy} of the {@code Person} that we are building with a specific renewal date.
     */
    public PersonBuilder withRenewalDate(String renewalDate) {
        this.policy = new Policy(this.policy.policyNumber, new RenewalDate(renewalDate));
        return this;
    }

    /**
     * Sets the {@code Policy} of the {@code Person} that we are building with a specific policy type.
     */
    public PersonBuilder withPolicyType(String policyType) {
        this.policy = new Policy(this.policy.policyNumber, this.policy.renewalDate, PolicyType.fromString(policyType));
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PersonBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, policy, note, tags);
    }

}
