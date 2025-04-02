---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# AB-3 User Guide

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

---

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 pol/123456` : Adds a contact named `John Doe` to the Address Book.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

---

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

*   Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
    e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

*   Items in square brackets are optional.<br>
    e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

*   Items with `…`​ after them can be used multiple times including zero times.<br>
    e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

*   Parameters can be in any order.<br>
    e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

*   Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
    e.g. if the command specifies `help 123`, it will be interpreted as `help`.

*   If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
    </box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS pol/POLICY_NUMBER [pt/POLICY_TYPE] [r/RENEWAL_DATE] [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:

*   `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 pol/123456 pt/Life r/31-12-2024`
*   `add n/Betsy Crowe t/friend pol/654321 pt/Health e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

Each person card in the list displays:

*   Name
*   Phone number
*   Email address
*   Physical address
*   Policy number
*   Policy type (Life, Health, Property, Vehicle, or Travel)
*   Renewal date (displayed as "Renewal date: DD-MM-YYYY")
*   Tags (if any)

The policy type and renewal date are clearly labeled to help insurance agents quickly identify the types of policies and when they need to be renewed.

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pol/POLICY] [pt/POLICY_TYPE] [r/RENEWAL_DATE] [t/TAG]…​`

*   Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
*   At least one of the optional fields must be provided.
*   Existing values will be updated to the input values.
*   When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
*   You can remove all the person's tags by typing `t/` without
    specifying any tags after it.

Examples:

*   `edit 1 p/91234567 e/johndoe@example.com pt/Health r/31-12-2024` Edits the phone number, email address, policy type and renewal date of the 1st person to be `91234567`, `johndoe@example.com`, `Health` and `31-12-2024` respectively.
*   `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given values.

Format: `find [n/NAME]… [p/PHONE]… [e/EMAIL]… [a/ADDRESS]… [pol/POLICY_NUMBER]… [pt/POLICY_TYPE]… [t/TAG]…`

*   At least one of the optional fields must be provided.
*   Each field may be provided more than once.
*   Each field may contain more than one word.
*   The search is case-insensitive. e.g `hans` will match `Hans`
*   The order of the values matter for a field but not for different fields. e.g. `n/Hans Bo` will not match `Bo Hans` but `n/Hans n/Bo` will match `Bo Hans`
*   Partial words will also be matched e.g. `n/Han` will match `Hans`
*   Partial search for emails must be concatenated with `@` followed by at least 2 characters e.g. `e/ice@ex` will match `alice@example.com` but `alice@e` will not match `alice@example.com`
*   Persons matching at least one field will be returned (i.e. `OR` search).
    e.g. `n/Hans n/Bo` will return `Hans Gruber`, `Bo Yang`
*   Tags are supported. You can add one or more tags using `t/TAG`. The search for tags is not case-sensitive and must be an exact word.
*   Policy types are supported. You can search for specific policy types using `pt/POLICY_TYPE`. Valid policy types are: Life, Health, Property, Vehicle, and Travel. The search is not case-sensitive.
*   The search results can be sorted by `name` or by `tag`. The default sort order is by name. Tag sorting sorts by entries with the most number of tags first.

<box type="info" seamless>
**Note:** The sorting order is case-sensitive and follows ASCII values. This means lowercase letters are ordered after uppercase ones. For example, `alice` will appear after `Bernice`.
</box>

Examples:

*   `find n/John` returns `john` and `John Doe`
*   `find n/Amy p/999` returns `Amy Goh (96372716)` and `Local Police (999)`
*   `find n/alex n/david` returns `Alex Yeoh`, `David Li`
*   `find e/ice@example.com e/bob@ex` returns `alice@example.com` and `bob@example.com`<br>
    ![result for 'find alex david'](images/findAlexDavidResult.png)
*   `find t/colleagues` returns `Bernice Yu` and `Roy Balakrishnan`
*   `find pt/Health` returns all persons with health insurance policies
*   `find pt/Life pt/Health` returns all persons with either life or health insurance policies
*   `find t/friends t/colleagues s/tag` returns entries containing one or more of the tags `friends` or `colleagues`, sorted by entries with most number of tags first.

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

*   Deletes the person at the specified `INDEX`.
*   The index refers to the index number shown in the displayed person list.
*   The index **must be a positive integer** 1, 2, 3, …​

Examples:

*   `list` followed by `delete 2` deletes the 2nd person in the address book.
*   `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Updating a policy renewal date : `renew`

Updates the renewal date of a client's policy by directly using their policy number, without needing to search for their index.

Format: `renew pol/POLICY_NUMBER r/RENEWAL_DATE`

*   The `pol/POLICY_NUMBER` parameter must be a valid policy number in the system.
*   The `r/RENEWAL_DATE` parameter must be in the format `DD-MM-YYYY`.
*   If multiple clients have the same policy number, an error message will be shown. In this case, use the `edit` command with the client's index instead.

Examples:

*   `renew pol/123456 r/31-12-2025` - Updates the renewal date for policy 123456 to December 31, 2025

### Viewing upcoming policy renewals : `viewrenewals`

Helps insurance agents proactively track and manage upcoming policy renewals for their clients.

Format: `viewrenewals [n/NEXT_N_DAYS] [s/SORT_ORDER]`

*   The `n/NEXT_N_DAYS` parameter is optional:

    *   Acceptable values: Positive integer between 1-365
    *   If omitted: Defaults to 30 days
    *   Error message: "NEXT_N_DAYS must be a positive number between 1 and 365"
    *   Rationale: Setting an upper limit prevents performance issues with extremely large ranges

*   The `s/SORT_ORDER` parameter is optional:
    *   Acceptable values: "date" (chronological order), "name" (alphabetical order)
    *   Case-insensitive: Both "DATE" and "date" are valid
    *   If omitted: Defaults to "date"
    *   Error message: "Invalid sort order. Use 'date' or 'name'"
    *   Rationale: These two sort orders cover the most common use cases for reviewing renewals

The renewal table displays the following information for each policy:

*   Client name
*   Policy number
*   Policy type (Life, Health, Property, Vehicle, or Travel)
*   Renewal date
*   Days left until renewal
*   Contact number

Examples:

*   `viewrenewals` - Shows renewals due in the next 30 days, sorted by date
*   `viewrenewals n/60` - Shows renewals due in the next 60 days, sorted by date
*   `viewrenewals n/60 s/name` - Shows renewals due in the next 60 days, sorted alphabetically by name

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
1. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---

## Command summary

| Action            | Format, Examples                                                                                                                                                                                                    |
| ----------------- |---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**           | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS pol/POLICY_NUMBER [r/RENEWAL_DATE] [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 pol/183548 t/friend t/colleague` |
| **Clear**         | `clear`                                                                                                                                                                                                             |
| **Delete**        | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                                                 |
| **Edit**          | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [pol/POLICY] [r/RENEWAL_DATE] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                           |
| **Find**          | `find [n/NAME]… [p/PHONE]… [e/EMAIL]… [a/ADDRESS]… [pol/POLICY_NUMBER]… [t/TAG]… [s/SORT_ORDER]`<br> e.g., `find n/James n/Jake p/98765432`                                                                         |
| **List**          | `list`                                                                                                                                                                                                              |
| **Help**          | `help`                                                                                                                                                                                                              |
| **Renew**         | `renew pol/POLICY_NUMBER r/RENEWAL_DATE`<br> e.g., `renew pol/123456 r/31-12-2025`                                                                                                                                  |
| **View Renewals** | `viewrenewals [n/NEXT_N_DAYS] [s/SORT_ORDER]`<br> e.g., `viewrenewals n/60 s/name`                                                                                                                                  |

