package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.person.RenewalDate;

/**
 * A utility class to update test data files with dynamic dates.
 */
public class UpdateTestData {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");

    public static void main(String[] args) throws IOException {
        LocalDate baseDate = LocalDate.now().plusYears(1);
        LocalDateTime currentDate = LocalDateTime.now();

        String content = Files.readString(TYPICAL_PERSONS_FILE);

        content = content.replace("{{BASE_DATE}}", baseDate.format(RenewalDate.DATE_FORMATTER));
        content = content.replace("{{BASE_DATE_PLUS_5}}", baseDate.plusDays(5).format(RenewalDate.DATE_FORMATTER));
        content = content.replace("{{BASE_DATE_PLUS_10}}", baseDate.plusDays(10).format(RenewalDate.DATE_FORMATTER));
        content = content.replace("{{BASE_DATE_PLUS_15}}", baseDate.plusDays(15).format(RenewalDate.DATE_FORMATTER));
        content = content.replace("{{BASE_DATE_PLUS_20}}", baseDate.plusDays(20).format(RenewalDate.DATE_FORMATTER));
        content = content.replace("{{BASE_DATE_PLUS_25}}", baseDate.plusDays(25).format(RenewalDate.DATE_FORMATTER));
        content = content.replace("{{BASE_DATE_PLUS_30}}", baseDate.plusDays(30).format(RenewalDate.DATE_FORMATTER));
        content = content.replace("{{CURRENT_DATE}}", currentDate.format(DateTimeFormatter.ofPattern("M/d/yyyy HHmm")));

        Files.writeString(TYPICAL_PERSONS_FILE, content);
    }
}
