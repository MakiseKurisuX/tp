package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Set;

import org.junit.jupiter.api.Test;

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
import seedu.address.testutil.FindPersonsPredicateBuilder;
import seedu.address.testutil.PersonBuilder;

public class FindPersonsPredicateTest {

    @Test
    public void isAnyPredicateSet_emptyPredicate_returnsFalse() {
        FindCommand.FindPersonsPredicate predicate = new FindCommand.FindPersonsPredicate();
        assertFalse(predicate.isAnyPredicateSet());
    }

    @Test
    public void isAnyPredicateSet_onlyNameSet_returnsTrue() {
        FindCommand.FindPersonsPredicate predicate = new FindCommand.FindPersonsPredicate();
        predicate.setNamePredicate(new NameContainsKeywordsPredicate(Set.of(new Name("keyword"))));
        assertTrue(predicate.isAnyPredicateSet());
    }

    @Test
    public void isAnyPredicateSet_onlyPhoneSet_returnsTrue() {
        FindCommand.FindPersonsPredicate predicate = new FindCommand.FindPersonsPredicate();
        predicate.setPhonePredicate(new PhoneContainsNumbersPredicate(Set.of(new Phone("12345678"))));
        assertTrue(predicate.isAnyPredicateSet());
    }

    @Test
    public void isAnyPredicateSet_onlyEmailSet_returnsTrue() {
        FindCommand.FindPersonsPredicate predicate = new FindCommand.FindPersonsPredicate();
        predicate.setEmailPredicate(new EmailContainsKeywordsPredicate(Set.of(new Email("local-part@domain"))));
        assertTrue(predicate.isAnyPredicateSet());
    }

    @Test
    public void isAnyPredicateSet_onlyAddressSet_returnsTrue() {
        FindCommand.FindPersonsPredicate predicate = new FindCommand.FindPersonsPredicate();
        predicate.setAddressPredicate(new AddressContainsKeywordsPredicate(Set.of(new Address("keyword"))));
        assertTrue(predicate.isAnyPredicateSet());
    }

    @Test
    public void isAnyPredicateSet_onlyPolicySet_returnsTrue() {
        FindCommand.FindPersonsPredicate predicate = new FindCommand.FindPersonsPredicate();
        predicate.setPolicyPredicate(new PolicyContainsNumbersPredicate(Set.of(new Policy("123456"))));
        assertTrue(predicate.isAnyPredicateSet());
    }

    @Test
    public void isAnyPredicateSet_allSet_returnsTrue() {
        FindCommand.FindPersonsPredicate predicate = new FindCommand.FindPersonsPredicate();
        predicate.setNamePredicate(new NameContainsKeywordsPredicate(Set.of(new Name("keyword"))));
        predicate.setPhonePredicate(new PhoneContainsNumbersPredicate(Set.of(new Phone("12345678"))));
        predicate.setEmailPredicate(new EmailContainsKeywordsPredicate(Set.of(new Email("local-part@domain"))));
        predicate.setAddressPredicate(new AddressContainsKeywordsPredicate(Set.of(new Address("keyword"))));
        predicate.setPolicyPredicate(new PolicyContainsNumbersPredicate(Set.of(new Policy("123456"))));
        assertTrue(predicate.isAnyPredicateSet());
    }

    @Test
    public void test_emptyPredicate_returnsFalse() {
        FindCommand.FindPersonsPredicate predicate = new FindCommand.FindPersonsPredicate();
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void test_nameMatchOnly_returnsTrue() {
        // Create predicate with "Alice" name
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder()
                .withNames("Alice")
                .build();

        // Should match ALICE
        assertTrue(predicate.test(ALICE));

        // Should not match BOB
        assertFalse(predicate.test(BOB));
    }

    @Test
    public void test_phoneMatchOnly_returnsTrue() {
        // Create predicate with ALICE's phone number
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder()
                .withPhones("94351253")
                .build();

        // Should match ALICE
        assertTrue(predicate.test(ALICE));

        // Should not match BOB
        assertFalse(predicate.test(BOB));
    }

    @Test
    public void test_emailMatchOnly_returnsTrue() {
        // Create predicate with "Alice" email
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder()
                .withEmails("alice@example.com")
                .build();

        // Should match ALICE
        assertTrue(predicate.test(ALICE));

        // Should not match BOB
        assertFalse(predicate.test(BOB));
    }

    @Test
    public void test_addressMatchOnly_returnsTrue() {
        // Create predicate with "Alice" address
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder()
                .withAddresses("123, Jurong West Ave 6, #08-111")
                .build();

        // Should match ALICE
        assertTrue(predicate.test(ALICE));

        // Should not match BOB
        assertFalse(predicate.test(BOB));
    }

    @Test
    public void test_policyMatchOnly_returnsTrue() {
        // Create predicate with ALICE's policy number
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder()
                .withPolicies("135792")
                .build();

        // Should match ALICE
        assertTrue(predicate.test(ALICE));

        // Should not match BOB
        assertFalse(predicate.test(BOB));
    }

    @Test
    public void test_allFieldsMatch_returnsTrue() {
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder()
                .withNames("Alice")
                .withPhones("94351253")
                .withEmails("alice@example.com")
                .withAddresses("123, Jurong West Ave 6, #08-111")
                .withPolicies("135792")
                .build();

        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_eitherFieldMatch_returnsTrue() {
        // Predicate with Alice's name and Bob's phone
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder()
                .withNames("Alice")
                .withPhones("22222222")
                .withEmails("leonard@example.com")
                .withAddresses("Wonderland")
                .withPolicies("111111")
                .build();

        // Should match ALICE due to name
        assertTrue(predicate.test(ALICE));

        // Should match BOB due to phone
        Person bobWithDifferentName = new PersonBuilder(BOB).withName("NotBob").build();
        assertTrue(predicate.test(bobWithDifferentName));
    }

    @Test
    public void test_neitherNameNorPhoneMatch_returnsFalse() {
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder()
                .withNames("Charlie")
                .withPhones("33333333")
                .withEmails("charlie@example.com")
                .withAddresses("Wonderland")
                .withPolicies("111111")
                .build();

        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void copy_constructor_equalPredicates() {
        FindCommand.FindPersonsPredicate original = new FindPersonsPredicateBuilder()
                .withNames("Alice")
                .withPhones("12345678")
                .withEmails("alice@test.com")
                .withAddresses("123, Wonderland Ave 1")
                .withPolicies("123456")
                .build();

        FindCommand.FindPersonsPredicate copy = new FindCommand.FindPersonsPredicate(original);
        assertEquals(original, copy);
    }

    @Test
    public void equals() {
        FindCommand.FindPersonsPredicate firstPredicate = new FindPersonsPredicateBuilder().withNames("first")
                .withPhones("91234567").withEmails("first@example.com")
                .withAddresses("987 First Street").withPolicies("234567").build();
        FindCommand.FindPersonsPredicate secondPredicate = new FindPersonsPredicateBuilder()
                .withNames("second").build();

        // same values -> returns true
        FindCommand.FindPersonsPredicate firstPredicateCopy = new FindCommand.FindPersonsPredicate(firstPredicate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different types -> returns false
        assertFalse(firstPredicate.equals(5));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different name predicate -> returns false
        FindCommand.FindPersonsPredicate differentNamePredicate = new FindPersonsPredicateBuilder()
                .withNames("different").build();
        assertFalse(firstPredicate.equals(differentNamePredicate));

        // different phone predicate -> returns false
        FindCommand.FindPersonsPredicate differentPhonePredicate = new FindPersonsPredicateBuilder()
                .withNames("first")
                .withPhones("12345678")
                .withEmails("first@example.com")
                .withAddresses("987 First Street")
                .withPolicies("234567")
                .build();
        assertFalse(firstPredicate.equals(differentPhonePredicate));

        // different email predicate -> returns false
        FindCommand.FindPersonsPredicate differentEmailPredicate = new FindPersonsPredicateBuilder()
                .withNames("first")
                .withPhones("91234567")
                .withEmails("second@example.com")
                .withAddresses("987 First Street")
                .withPolicies("234567")
                .build();
        assertFalse(firstPredicate.equals(differentEmailPredicate));

        // different address predicate -> returns false
        FindCommand.FindPersonsPredicate differentAddressPredicate = new FindPersonsPredicateBuilder()
                .withNames("first")
                .withPhones("91234567")
                .withEmails("first@example.com")
                .withAddresses("Third Street")
                .withPolicies("234567")
                .build();
        assertFalse(firstPredicate.equals(differentAddressPredicate));

        // different policy predicate -> returns false
        FindCommand.FindPersonsPredicate differentPolicyPredicate = new FindPersonsPredicateBuilder()
                .withNames("first")
                .withPhones("91234567")
                .withEmails("first@example.com")
                .withAddresses("987 First Street")
                .withPolicies("222222")
                .build();
        assertFalse(firstPredicate.equals(differentPolicyPredicate));
    }

    @Test
    public void toStringMethod() {
        FindCommand.FindPersonsPredicate emptyPredicate = new FindCommand.FindPersonsPredicate();
        String expected = FindCommand.FindPersonsPredicate.class.getCanonicalName()
                + "{namePredicate=" + null
                + ", phonePredicate=" + null
                + ", emailPredicate=" + null
                + ", addressPredicate=" + null
                + ", policyPredicate=" + null + "}";
        assertEquals(expected, emptyPredicate.toString());

        FindCommand.FindPersonsPredicate fullPredicate = new FindPersonsPredicateBuilder()
                .withNames("Alice")
                .withPhones("12345678")
                .withEmails("alice@test.com")
                .withAddresses("123, Wonderland Ave 1")
                .withPolicies("123456")
                .build();
        String fullExpected = FindCommand.FindPersonsPredicate.class.getCanonicalName()
                + "{namePredicate=" + fullPredicate.getNamePredicate().get()
                + ", phonePredicate=" + fullPredicate.getPhonePredicate().get()
                + ", emailPredicate=" + fullPredicate.getEmailPredicate().get()
                + ", addressPredicate=" + fullPredicate.getAddressPredicate().get()
                + ", policyPredicate=" + fullPredicate.getPolicyPredicate().get() + "}";
        assertEquals(fullExpected, fullPredicate.toString());
    }
}
