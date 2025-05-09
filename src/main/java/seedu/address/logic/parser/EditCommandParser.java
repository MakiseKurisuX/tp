package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENEWAL_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_POLICY,
                        PREFIX_RENEWAL_DATE, PREFIX_POLICY_TYPE, PREFIX_NOTE, PREFIX_TAG);

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_POLICY, PREFIX_RENEWAL_DATE, PREFIX_POLICY_TYPE, PREFIX_NOTE);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        try {
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
            }
            if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
                editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
            }
            if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
            }
            if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
                editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
            }
            if (argMultimap.getValue(PREFIX_POLICY).isPresent()) {
                editPersonDescriptor.setPolicy(
                        ParserUtil.parsePolicy(argMultimap.getValue(PREFIX_POLICY).get()));
            }
            if (argMultimap.getValue(PREFIX_RENEWAL_DATE).isPresent()) {
                editPersonDescriptor.setRenewalDate(
                        ParserUtil.parseRenewalDate(argMultimap.getValue(PREFIX_RENEWAL_DATE).get()));
            }
            if (argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent()) {
                editPersonDescriptor.setPolicyType(
                        ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get()));
            }
            if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
                editPersonDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
            }
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        } catch (ParseException pe) {
            throw pe;
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
