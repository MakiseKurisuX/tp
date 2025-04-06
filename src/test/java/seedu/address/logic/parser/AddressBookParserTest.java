package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENEWAL_DATE_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterDateCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindPersonsPredicate;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RenewCommand;
import seedu.address.logic.commands.ViewRenewalsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.RenewalDate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.FindPersonsPredicateBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        Person person = new PersonBuilder().build();
        FindPersonsPredicate predicate = new FindPersonsPredicateBuilder(person).build();
        FindCommand command = (FindCommand) parser.parseCommand(FindCommand.COMMAND_WORD + " "
                + PersonUtil.getFindPersonDetails(predicate));
        assertEquals(new FindCommand(predicate, FindCommand.DEFAULT_SORT), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_viewRenewals() throws Exception {
        // Test with default parameters
        ViewRenewalsCommand defaultCommand =
                (ViewRenewalsCommand) parser.parseCommand(ViewRenewalsCommand.COMMAND_WORD);
        assertEquals(
                new ViewRenewalsCommand(ViewRenewalsCommand.DEFAULT_DAYS, ViewRenewalsCommand.DEFAULT_SORT),
                defaultCommand);

        // Test with custom parameters
        ViewRenewalsCommand customCommand = (ViewRenewalsCommand) parser.parseCommand(
                ViewRenewalsCommand.COMMAND_WORD + " n/60 s/name");
        assertEquals(new ViewRenewalsCommand(60, "name"), customCommand);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        LocalDate startDate = LocalDate.now().plusMonths(1);
        LocalDate endDate = startDate.plusMonths(1);

        // Test with default parameters
        FilterDateCommand defaultCommand = (FilterDateCommand) parser.parseCommand(
                FilterDateCommand.COMMAND_WORD + " sd/"
                        + startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " ed/"
                        + endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        assertEquals(
                new FilterDateCommand(startDate, endDate, "date"),
                defaultCommand);

        // Test with custom parameters
        FilterDateCommand customCommand = (FilterDateCommand) parser.parseCommand(
                FilterDateCommand.COMMAND_WORD + " sd/"
                        + startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " ed/"
                        + endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " s/name");
        assertEquals(new FilterDateCommand(startDate, endDate, "name"), customCommand);
    }

    @Test
    public void parseCommand_renew() throws Exception {
        RenewCommand command = (RenewCommand) parser.parseCommand(
                RenewCommand.COMMAND_WORD + " pol/" + VALID_POLICY_AMY + " r/" + VALID_RENEWAL_DATE_AMY);
        assertEquals(new RenewCommand(VALID_POLICY_AMY, new RenewalDate(VALID_RENEWAL_DATE_AMY)), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
