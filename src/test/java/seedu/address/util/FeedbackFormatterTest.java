package seedu.address.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FeedbackFormatterTest {

    @Test
    public void formatFeedback_emptyString_returnsEmptyString() {
        assertEquals("", FeedbackFormatter.formatFeedback(""));
    }

    @Test
    public void formatFeedback_singleLineNoColon_returnsUnchangedLine() {
        String input = "Simple message without colon";
        assertEquals(input, FeedbackFormatter.formatFeedback(input));
    }

    @Test
    public void formatFeedback_singleLineWithColon_formatsWithBullets() {
        String input = "Result: first item; second item; third item";
        String expected = "Result:\n• first item\n• second item\n• third item";
        assertEquals(expected, FeedbackFormatter.formatFeedback(input));
    }

    @Test
    public void formatFeedback_multipleLines_formatsCorrectly() {
        String input = "Header line\nResult: item1; item2\nFooter line";
        String expected = "Header line\nResult:\n• item1\n• item2\nFooter line";
        assertEquals(expected, FeedbackFormatter.formatFeedback(input));
    }

    @Test
    public void formatFeedback_emptyParams_skipsEmptyBullets() {
        String input = "Result: ; ; valid item; ";
        String expected = "Result:\n• valid item";
        assertEquals(expected, FeedbackFormatter.formatFeedback(input));
    }

    @Test
    public void formatFeedback_multipleColons_handlesCorrectly() {
        String input = "New person added: Name: John Doe; Phone: 12345678";
        String expected = "New person added:\n• Name: John Doe\n• Phone: 12345678";
        assertEquals(expected, FeedbackFormatter.formatFeedback(input));
    }

    @Test
    public void formatFeedback_extraWhitespace_trimsProperly() {
        String input = "  Spaced:    item1   ;   item2  \n  Extra line  ";
        String expected = "Spaced:\n• item1\n• item2\nExtra line";
        assertEquals(expected, FeedbackFormatter.formatFeedback(input));
    }

    @Test
    public void formatFeedback_realWorldExample_formatsCorrectly() {
        String input = "New person added: Susan; Phone: 98765432; Email: johnd@example.com; "
                + "Address: 311 Clementi Ave 2 #02-25; Policy number: 102312; Policy type: Life; "
                + "Renewal date: 02-04-2026; Note: ; Tags:";
        String expected = "New person added:\n• Susan\n• Phone: 98765432\n• Email: johnd@example.com\n"
                + "• Address: 311 Clementi Ave 2 #02-25\n• Policy number: 102312\n• Policy type: Life\n"
                + "• Renewal date: 02-04-2026\n• Note:\n• Tags:";
        assertEquals(expected, FeedbackFormatter.formatFeedback(input));
    }
}
