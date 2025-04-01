package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENEWAL_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENEWAL_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.RenewalDate;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code RenewCommand}.
 */
public class RenewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validPolicyAndRenewalDate_success() {
        Person firstPerson = model.getFilteredPersonList().get(0);
        Person updatedPerson = new PersonBuilder(firstPerson).withRenewalDate(VALID_RENEWAL_DATE_BOB).build();
        RenewCommand renewCommand = new RenewCommand(
                firstPerson.getPolicy().getPolicyNumber(), new RenewalDate(VALID_RENEWAL_DATE_BOB));
        String expectedMessage = String.format(
                RenewCommand.MESSAGE_RENEW_SUCCESS,
                firstPerson.getPolicy().getPolicyNumber(),
                VALID_RENEWAL_DATE_BOB);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, updatedPerson);

        assertCommandSuccess(renewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentPolicy_throwsCommandException() {
        String nonExistentPolicy = "999999";
        RenewCommand renewCommand = new RenewCommand(nonExistentPolicy, new RenewalDate(VALID_RENEWAL_DATE_AMY));

        String expectedMessage = String.format(RenewCommand.MESSAGE_POLICY_NOT_FOUND, nonExistentPolicy);
        assertThrows(CommandException.class, expectedMessage, () -> renewCommand.execute(model));
    }

    @Test
    public void execute_multiplePoliciesFound_throwsCommandException() {
        // Create a second person with the same policy number as the first person
        Person firstPerson = model.getFilteredPersonList().get(0);
        String existingPolicy = firstPerson.getPolicy().getPolicyNumber();

        Person duplicatePolicyPerson = new PersonBuilder()
                .withName("Different Name")
                .withPolicy(existingPolicy)
                .build();

        model.addPerson(duplicatePolicyPerson);

        RenewCommand renewCommand = new RenewCommand(existingPolicy, new RenewalDate(VALID_RENEWAL_DATE_BOB));

        String expectedMessage = String.format(RenewCommand.MESSAGE_MULTIPLE_POLICIES, existingPolicy);
        assertThrows(CommandException.class, expectedMessage, () -> renewCommand.execute(model));
    }

    @Test
    public void equals() {
        RenewCommand renewFirstCommand = new RenewCommand(VALID_POLICY_AMY, new RenewalDate(VALID_RENEWAL_DATE_AMY));
        RenewCommand renewSecondCommand = new RenewCommand(VALID_POLICY_BOB, new RenewalDate(VALID_RENEWAL_DATE_BOB));
        // same object -> returns true
        assertTrue(renewFirstCommand.equals(renewFirstCommand));
        // same values -> returns true
        RenewCommand renewFirstCommandCopy = new RenewCommand(
            VALID_POLICY_AMY,
            new RenewalDate(VALID_RENEWAL_DATE_AMY));
        assertTrue(renewFirstCommand.equals(renewFirstCommandCopy));
        // different types -> returns false
        assertFalse(renewFirstCommand.equals(1));
        // null -> returns false
        assertFalse(renewFirstCommand.equals(null));
        // different policy -> returns false
        assertFalse(renewFirstCommand.equals(renewSecondCommand));
        // different renewal date -> returns false
        RenewCommand renewFirstCommandDifferentDate = new RenewCommand(
            VALID_POLICY_AMY,
            new RenewalDate(VALID_RENEWAL_DATE_BOB));
        assertFalse(renewFirstCommand.equals(renewFirstCommandDifferentDate));
    }
}
