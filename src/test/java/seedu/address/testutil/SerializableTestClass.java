package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class used to test serialization and deserialization
 */
public class SerializableTestClass {
    public static final String JSON_STRING_REPRESENTATION = String.format("{%n"
                + "  \"name\" : \"This is a test class\",%n"
                + "  \"listOfLocalDateTimes\" : [ \"1/1/1000 0000\", \"12/31/9999 2359\", \"1/1/0001 0101\" ],%n"
                + "  \"mapOfIntegerToString\" : {%n"
                + "    \"1\" : \"One\",%n"
                + "    \"2\" : \"Two\",%n"
                + "    \"3\" : \"Three\"%n"
                + "  }%n"
                + "}");

    private static final String NAME_TEST_VALUE = "This is a test class";

    private String name;

    private List<LocalDateTime> listOfLocalDateTimes;
    private HashMap<Integer, String> mapOfIntegerToString;

    public static String getNameTestValue() {
        return NAME_TEST_VALUE;
    }

    public static List<LocalDateTime> getListTestValues() {
        List<LocalDateTime> listOfLocalDateTimes = new ArrayList<>();

        listOfLocalDateTimes.add(LocalDateTime.of(1000, 1, 1, 0, 0));
        listOfLocalDateTimes.add(LocalDateTime.of(9999, 12, 31, 23, 59));
        listOfLocalDateTimes.add(LocalDateTime.of(1, 1, 1, 1, 1));

        return listOfLocalDateTimes;
    }

    public static HashMap<Integer, String> getHashMapTestValues() {
        HashMap<Integer, String> mapOfIntegerToString = new HashMap<>();

        mapOfIntegerToString.put(1, "One");
        mapOfIntegerToString.put(2, "Two");
        mapOfIntegerToString.put(3, "Three");

        return mapOfIntegerToString;
    }

    public void setTestValues() {
        name = getNameTestValue();
        listOfLocalDateTimes = getListTestValues();
        mapOfIntegerToString = getHashMapTestValues();
    }

    public String getName() {
        return name;
    }

    public List<LocalDateTime> getListOfLocalDateTimes() {
        return listOfLocalDateTimes;
    }

    public HashMap<Integer, String> getMapOfIntegerToString() {
        return mapOfIntegerToString;
    }
}
