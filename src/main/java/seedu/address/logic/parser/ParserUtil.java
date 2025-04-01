package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Policy;
import seedu.address.model.person.PolicyType;
import seedu.address.model.person.RenewalDate;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses {@code Collection<String> names} into a {@code Set<Name>}.
     */
    public static Set<Name> parseNames(Collection<String> names) throws ParseException {
        requireNonNull(names);
        final Set<Name> nameSet = new HashSet<>();
        for (String name : names) {
            nameSet.add(parseName(name));
        }
        return nameSet;
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses {@code Collection<String> phones} into a {@code Set<Phone>}.
     */
    public static Set<Phone> parsePhones(Collection<String> phones) throws ParseException {
        requireNonNull(phones);
        final Set<Phone> phoneSet = new HashSet<>();
        for (String phone : phones) {
            phoneSet.add(parsePhone(phone));
        }
        return phoneSet;
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses {@code Collection<String> addresses} into a {@code Set<Address>}.
     */
    public static Set<Address> parseAddresses(Collection<String> addresses) throws ParseException {
        requireNonNull(addresses);
        final Set<Address> addressSet = new HashSet<>();
        for (String address : addresses) {
            addressSet.add(parseAddress(address));
        }
        return addressSet;
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses {@code Collection<String> emails} into a {@code Set<Email>}.
     */
    public static Set<Email> parseEmails(Collection<String> emails) throws ParseException {
        requireNonNull(emails);
        final Set<Email> emailSet = new HashSet<>();
        for (String email : emails) {
            emailSet.add(parseEmail(email));
        }
        return emailSet;
    }

    /**
     * Parses a {@code String policy} into a {@code Policy}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code policy} is invalid.
     */
    public static Policy parsePolicy(String policy) throws ParseException {
        requireNonNull(policy);
        String trimmedPolicy = policy.trim();
        if (!Policy.isValidPolicy(trimmedPolicy)) {
            throw new ParseException(Policy.MESSAGE_CONSTRAINTS);
        }
        return new Policy(trimmedPolicy);
    }

    /**
     * Parses {@code Collection<String> policy} into a {@code Set<Policy>}.
     */
    public static Set<Policy> parsePolicies(Collection<String> policies) throws ParseException {
        requireNonNull(policies);
        final Set<Policy> policySet = new HashSet<>();
        for (String policy : policies) {
            policySet.add(parsePolicy(policy));
        }
        return policySet;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String renewalDate} into a {@code RenewalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code renewalDate} is invalid.
     */
    public static RenewalDate parseRenewalDate(String renewalDate) throws ParseException {
        requireNonNull(renewalDate);
        String trimmedRenewalDate = renewalDate.trim();
        if (!RenewalDate.isValidRenewalDate(trimmedRenewalDate)) {
            throw new ParseException(RenewalDate.DATE_CONSTRAINTS);
        }
        return new RenewalDate(trimmedRenewalDate);
    }

    /**
     * Parses a {@code String policyType} into a {@code PolicyType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code policyType} is invalid.
     */
    public static PolicyType parsePolicyType(String policyType) throws ParseException {
        requireNonNull(policyType);
        String trimmedPolicyType = policyType.trim();
        if (!PolicyType.isValidPolicyType(trimmedPolicyType)) {
            throw new ParseException(PolicyType.MESSAGE_CONSTRAINTS);
        }
        return PolicyType.fromString(trimmedPolicyType);
    }

    /**
     * Parses {@code Collection<String> policyTypes} into a {@code Set<PolicyType>}.
     */
    public static Set<PolicyType> parsePolicyTypes(Collection<String> policyTypes) throws ParseException {
        requireNonNull(policyTypes);
        final Set<PolicyType> policyTypeSet = new HashSet<>();
        for (String policyType : policyTypes) {
            policyTypeSet.add(parsePolicyType(policyType));
        }
        return policyTypeSet;
    }

    /**
     * Parses a {@code String dateStr} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param dateStr The date string to parse.
     * @return The parsed {@code LocalDate} object.
     * @throws ParseException if the given {@code dateStr} is invalid.
     */
    public static LocalDate parseDate(String dateStr) throws ParseException {
        try {
            return LocalDate.parse(dateStr.trim());
        } catch (DateTimeParseException e) {
            throw new ParseException(FilterDateCommandParser.MESSAGE_INVALID_DATE_FORMAT);
        }
    }
}
