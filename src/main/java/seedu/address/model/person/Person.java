package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Policy policy;
    private final Note note;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Policy policy, Note note, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.policy = policy;
        this.note = (note != null) ? note : Note.EMPTY;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Policy getPolicy() {
        return policy;
    }

    public String getRenewalDate() {
        return policy.renewalDate.toString();
    }

    public LocalDate getRenewalDateValue() {
        return policy.renewalDate.value;
    }

    public Note getNote() {
        return note;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons are considered the same.
     * Two persons are considered the same if they have:
     * - the same policy number, OR
     * - the same name and email, OR
     * - the same name and phone number
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == null) {
            return false;
        }

        if (otherPerson == this) {
            return true;
        }

        return hasSamePolicyNumber(otherPerson)
                || (hasSameNameAndEmail(otherPerson))
                || (hasSameNameAndPhone(otherPerson));
    }

    /**
     * Returns true if both persons have the same policy number.
     */
    public boolean hasSamePolicyNumber(Person otherPerson) {
        return otherPerson.getPolicy().getPolicyNumber().equals(getPolicy().getPolicyNumber());
    }

    /**
     * Returns true if both persons have the same name and email.
     */
    public boolean hasSameNameAndEmail(Person otherPerson) {
        return this.getName().equals(otherPerson.getName())
            && this.getEmail().equals(otherPerson.getEmail());
    }

    /**
     * Returns true if both persons have the same name and phone.
     */
    public boolean hasSameNameAndPhone(Person otherPerson) {
        return this.getName().equals(otherPerson.getName())
            && this.getPhone().equals(otherPerson.getPhone());
    }

    /**
     * Returns a message explaining why this person is considered the same as another person.
     */
    public String getDuplicateReason(Person otherPerson) {
        if (hasSamePolicyNumber(otherPerson)) {
            return "policy number";
        } else if (hasSameNameAndEmail(otherPerson)) {
            return "name and email";
        } else if (hasSameNameAndPhone(otherPerson)) {
            return "name and phone number";
        }
        return "";
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && policy.equals(otherPerson.policy)
                && note.equals(otherPerson.note)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, policy, note, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("policy", policy)
                .add("tags", tags)
                .add("note", note)
                .toString();
    }

}
