package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Policy;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+11";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_POLICY = "123a";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME_1 = "Rachel Walker";
    private static final String VALID_NAME_2 = "Johnson Goh";
    private static final String VALID_PHONE_1 = "123456";
    private static final String VALID_PHONE_2 = "789012";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_POLICY = "123456";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(0)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME_1);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME_1));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME_1 + WHITESPACE;
        Name expectedName = new Name(VALID_NAME_1);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseNames_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNames(null));
    }

    @Test
    public void parseNames_collectionWithInvalidNames_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhones(Arrays.asList(VALID_NAME_1, INVALID_NAME)));
    }

    @Test
    public void parseNames_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parsePhones(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseNames_collectionWithValidNames_returnsNameSet() throws Exception {
        Set<Name> actualNameSet = ParserUtil.parseNames(Arrays.asList(VALID_NAME_1, VALID_NAME_2));
        Set<Name> expectedNameSet = new HashSet<Name>(Arrays.asList(new Name(VALID_NAME_1), new Name(VALID_NAME_2)));

        assertEquals(expectedNameSet, actualNameSet);
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE_1);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE_1));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE_1 + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE_1);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parsePhones_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhones(null));
    }

    @Test
    public void parsePhones_collectionWithInvalidPhones_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhones(Arrays.asList(VALID_PHONE_1, INVALID_PHONE)));
    }

    @Test
    public void parsePhones_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parsePhones(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parsePhones_collectionWithValidPhones_returnsPhoneSet() throws Exception {
        Set<Phone> actualPhoneSet = ParserUtil.parsePhones(Arrays.asList(VALID_PHONE_1, VALID_PHONE_2));
        Set<Phone> expectedPhoneSet = new HashSet<Phone>(Arrays.asList(
                new Phone(VALID_PHONE_1), new Phone(VALID_PHONE_2)));

        assertEquals(expectedPhoneSet, actualPhoneSet);
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parsePolicy_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePolicy((String) null));
    }

    @Test
    public void parsePolicy_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePolicy(INVALID_POLICY));
    }

    @Test
    public void parsePolicy_validValueWithoutWhitespace_returnsPolicy() throws Exception {
        Policy expectedPolicy = new Policy(VALID_POLICY);
        assertEquals(expectedPolicy, ParserUtil.parsePolicy(VALID_POLICY));
    }

    @Test
    public void parsePolicy_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String policyWithWhitespace = WHITESPACE + VALID_POLICY + WHITESPACE;
        Policy expectedPolicy = new Policy(VALID_POLICY);
        assertEquals(expectedPolicy, ParserUtil.parsePolicy(policyWithWhitespace));
    }

    @Test
    public void parseNote_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNote((String) null));
    }

    @Test
    public void parseNote_validValueWithoutWhitespace_returnsNote() throws Exception {
        String validNote = "Test note";
        assertEquals(new Note(validNote), ParserUtil.parseNote(validNote));
    }

    @Test
    public void parseNote_validValueWithWhitespace_returnsTrimmedNote() throws Exception {
        String noteWithWhitespace = WHITESPACE + "Test note" + WHITESPACE;
        assertEquals(new Note("Test note"), ParserUtil.parseNote(noteWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
