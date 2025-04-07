package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code searchTerm} as a partial or full word.
     * Ignores case.
     * <br>examples:<pre>
     *     containsPartialWordIgnoreCase("ABc def ghi", "abc") == true
     *     containsPartialWordIgnoreCase("ABc def ghi", "DEF") == true
     *     containsPartialWordIgnoreCase("ABc def ghi", "AB") == true  // partial word match
     *     containsPartialWordIgnoreCase("ABc def ghi", "ef gh") == true  // multi-word search term
     *     containsPartialWordIgnoreCase("ABc def ghi", "abc def") == true  // multi-word search term
     *     containsPartialWordIgnoreCase("ABc def ghi", "xyz") == false // no match
     *     </pre>
     * @param sentence cannot be null
     * @param searchTerm cannot be null, cannot be empty
     */
    public static boolean containsPartialWordIgnoreCase(String sentence, String searchTerm) {
        requireNonNull(sentence);
        requireNonNull(searchTerm);

        String preppedSearchTerm = searchTerm.trim().toLowerCase();
        checkArgument(!preppedSearchTerm.isEmpty(), "Search term parameter cannot be empty");

        String preppedSentence = sentence.toLowerCase();

        return preppedSentence.contains(preppedSearchTerm);
    }


    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a plain integer, i.e. without + sign if positive, with - sign if negative.
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "+1", and " +2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isPlainInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
