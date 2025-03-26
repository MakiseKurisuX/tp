package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_emptyString_returnsEmptyNote() {
        Note note = new Note("");
        assertNotNull(note);
        assertEquals("", note.toString());
    }

    @Test
    public void constructor_validNote_returnsNote() {
        String validNote = "This is a test note.";
        Note note = new Note(validNote);
        assertNotNull(note);
        assertEquals(validNote, note.toString());
    }

    @Test
    public void equals() {
        Note note1 = new Note("Test note");
        Note note2 = new Note("Test note");
        Note note3 = new Note("Different note");

        // Same values -> returns true
        assertTrue(note1.equals(note2));

        // Same object -> returns true
        assertTrue(note1.equals(note1));

        // Null -> returns false
        assertFalse(note1.equals(null));

        // Different types -> returns false
        assertFalse(note1.equals("Test note"));

        // Different values -> returns false
        assertFalse(note1.equals(note3));
    }

    @Test
    public void hashCode_sameForEqualNotes() {
        Note note1 = new Note("Another test note");
        Note note2 = new Note("Another test note");
        assertEquals(note1.hashCode(), note2.hashCode());
    }
}
