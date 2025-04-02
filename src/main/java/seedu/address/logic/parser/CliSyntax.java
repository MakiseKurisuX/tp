package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_POLICY = new Prefix("pol/");
    public static final Prefix PREFIX_NOTE = new Prefix("note/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_NEXT_N_DAYS = new Prefix("n/");
    public static final Prefix PREFIX_SORT_ORDER = new Prefix("s/");
    public static final Prefix PREFIX_RENEWAL_DATE = new Prefix("r/");
    public static final Prefix PREFIX_POLICY_TYPE = new Prefix("pt/");
    public static final Prefix PREFIX_START_DATE = new Prefix("sd/");
    public static final Prefix PREFIX_END_DATE = new Prefix("ed/");
}
