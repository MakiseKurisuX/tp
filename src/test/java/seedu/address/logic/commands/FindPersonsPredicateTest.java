package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Set;

import org.junit.jupiter.api.Test;

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
    public void isAnyPredicateSet_onlyPolicySet_returnsTrue() {
        FindCommand.FindPersonsPredicate predicate = new FindCommand.FindPersonsPredicate();
        predicate.setPolicyPredicate(new PolicyContainsNumbersPredicate(Set.of(new Policy("123456"))));
        assertTrue(predicate.isAnyPredicateSet());
    }

    @Test
    public void isAnyPredicateSet_onlyPhoneSet_returnsTrue() {
        FindCommand.FindPersonsPredicate predicate = new FindCommand.FindPersonsPredicate();
        predicate.setPhonePredicate(new PhoneContainsNumbersPredicate(Set.of(new Phone("12345678"))));
        assertTrue(predicate.isAnyPredicateSet());
    }

    @Test
    public void isAnyPredicateSet_bothSet_returnsTrue() {
        FindCommand.FindPersonsPredicate predicate = new FindCommand.FindPersonsPredicate();
        predicate.setNamePredicate(new NameContainsKeywordsPredicate(Set.of(new Name("keyword"))));
        predicate.setPhonePredicate(new PhoneContainsNumbersPredicate(Set.of(new Phone("12345678"))));
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
    public void test_bothNameAndPhoneMatch_returnsTrue() {
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder()
                .withNames("Alice")
                .withPhones("94351253")
                .withPolicies("135792")
                .build();

        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_eitherNameOrPhoneMatch_returnsTrue() {
        // Predicate with Alice's name and Bob's phone
        FindCommand.FindPersonsPredicate predicate = new FindPersonsPredicateBuilder()
                .withNames("Alice")
                .withPhones("22222222")
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
                .withPolicies("111111")
                .build();

        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void copy_constructor_equalPredicates() {
        FindCommand.FindPersonsPredicate original = new FindPersonsPredicateBuilder()
                .withNames("Alice")
                .withPhones("12345678")
                .withPolicies("123456")
                .build();

        FindCommand.FindPersonsPredicate copy = new FindCommand.FindPersonsPredicate(original);
        assertEquals(original, copy);
    }

    @Test
    public void equals() {
        FindCommand.FindPersonsPredicate firstPredicate = new FindPersonsPredicateBuilder().withNames("first")
                .withPhones("91234567").withPolicies("234567").build();
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
                .withPolicies("234567")
                .build();
        assertFalse(firstPredicate.equals(differentPhonePredicate));

        // different policy predicate -> returns false
        FindCommand.FindPersonsPredicate differentPolicyPredicate = new FindPersonsPredicateBuilder()
                .withNames("first")
                .withPhones("91234567")
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
                + ", policyPredicate=" + null + "}";
        assertEquals(expected, emptyPredicate.toString());

        FindCommand.FindPersonsPredicate fullPredicate = new FindPersonsPredicateBuilder()
                .withNames("Alice")
                .withPhones("12345678")
                .withPolicies("123456")
                .build();
        String fullExpected = FindCommand.FindPersonsPredicate.class.getCanonicalName()
                + "{namePredicate=" + fullPredicate.getNamePredicate().get()
                + ", phonePredicate=" + fullPredicate.getPhonePredicate().get()
                + ", policyPredicate=" + fullPredicate.getPolicyPredicate().get() + "}";
        assertEquals(fullExpected, fullPredicate.toString());
    }
}
