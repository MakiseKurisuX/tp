---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# InsureBook User Guide

**InsureBook** is an address book **desktop application** built to help insurance agents **manage and organize their client information more effectively**. Its primary purpose is to streamline the day-to-day workflow of agents, allowing them to **store, access, update and keep track of client data with ease**.

By combining the speed of a **Command Line Interface (CLI)** with the familiarity of a **Graphical User Interface (GUI)**, InsureBook is optimized for fast, efficient use. If you're a quick typist, you'll find that InsureBook lets you **perform contact management tasks faster than traditional point-and-click apps** — all while maintaining clarity and control over your client base.

As a result, with InsureBook, we're not just insuring policies—we're insuring smiles!

## How to Use This Guide

This User Guide is designed to help you get the most out of InsureBook. Here's how to navigate it effectively:

1. **[Quick Start](#quick-start)** (For New Users)
   * If you're new to InsureBook, start with the Quick Start section
   * This section provides step-by-step instructions to get you up and running quickly

2. **[Commands](#commands)** (For All Users)
   * The Commands section contains detailed information about all available commands
   * Commands are organized into categories:
     * Client Management ([add](#adding-a-person-add), [edit](#editing-a-person-edit), [delete](#deleting-a-person-delete))
     * Policy Management ([renew](#updating-a-policy-renewal-date-renew), [viewrenewals](#viewing-upcoming-policy-renewals-viewrenewals), [filter](#view-policy-renewals-in-date-range-filter))
     * Search and List ([find](#locating-persons-by-keyword-find), [list](#listing-all-persons-list))
     * General Commands ([help](#viewing-help-help), [clear](#clearing-all-entries-clear), [exit](#exiting-the-program-exit))

3. **[Command Summary](#command-summary)** (For Quick Reference)
   * The Command Summary section provides a quick overview of all commands
   * Use this section as a quick reference when you're familiar with the commands

4. **Additional Resources**
   * [FAQ](#faq): Answers to common questions
   * [Known Issues](#known-issues): Solutions to common issues

<br>
---

## Understanding Callout Boxes

Throughout this guide, you'll encounter different types of callout boxes that provide important information:

<box type="info" seamless>

**Note Box** 📝

Provides additional information or clarifications about a feature or command. Look for these when you need more details about how something works.
</box>

<box type="tip" seamless>

**Tip Box** 💡

Offers helpful tips and best practices. These can help you use InsureBook more effectively.
</box>

<box type="warning" seamless>

**Warning Box** ⚠️

Highlights important warnings or potential issues. Pay special attention to these to avoid problems.
</box>

<box type="danger" seamless>

**Danger Box** ⛔

Indicates critical warnings or irreversible actions. Always read these carefully before proceeding.
</box>
<br>
---  

## Table of Contents

1. [How to Use This Guide](#how-to-use-this-guide)
2. [Understanding Callout Boxes](#understanding-callout-boxes)
3. [Quick Start](#quick-start)
4. [Commands](#commands)
   * [Client Management Commands](#client-management-commands)
     * [Adding a person](#adding-a-person-add)
     * [Editing a person](#editing-a-person-edit)
     * [Deleting a person](#deleting-a-person-delete)
   * [Policy Management Commands](#policy-management-commands)
     * [Updating a policy renewal date](#updating-a-policy-renewal-date-renew)
     * [Viewing upcoming policy renewals](#viewing-upcoming-policy-renewals-viewrenewals)
     * [View policy renewals in date range](#view-policy-renewals-in-date-range-filter)
   * [Search Commands](#search-commands)
     * [Listing all persons](#listing-all-persons-list)
     * [Locating persons by keyword](#locating-persons-by-keyword-find)
   * [General Commands](#general-commands)
     * [Viewing help](#viewing-help-help)
     * [Clearing all entries](#clearing-all-entries-clear)
     * [Saving the data](#saving-the-data)
     * [Editing the data file](#editing-the-data-file)
     * [Exiting the program](#exiting-the-program-exit)
5. [FAQ](#faq)
6. [Known Issues](#known-issues)
7. [Command Summary](#command-summary)

<!-- * Table of Contents -->
<page-nav-print />

---

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103-F08-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your InsureBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar InsureBook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 pol/123456` : Adds a contact named `John Doe` to the Address Book.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

<box type="warning" seamless>

**Warning: Data Corruption**

If the data file is corrupted:
* InsureBook will start with an empty address book
* Your previous data will not be loaded
</box>

Refer to the [Commands](#commands) below for details of each command.

---

## Commands

InsureBook provides you with several types of commands to manage your client information effectively:

### [Client Management Commands](#client-management-commands)
* [Adding a person](#adding-a-person-add) - <span class="command-word" style="color: #CC0000">`add`</span>
* [Editing a person](#editing-a-person-edit) - <span class="command-word" style="color: #CC0000">`edit`</span>
* [Deleting a person](#deleting-a-person-delete) - <span class="command-word" style="color: #CC0000">`delete`</span>

### [Policy Management Commands](#policy-management-commands)
* [Updating a policy renewal date](#updating-a-policy-renewal-date-renew) - <span class="command-word" style="color: #CC0000">`renew`</span>
* [Viewing upcoming policy renewals](#viewing-upcoming-policy-renewals-viewrenewals) - <span class="command-word" style="color: #CC0000">`viewrenewals`</span>
* [View policy renewals in date range](#view-policy-renewals-in-the-specified-date-range-filter) - <span class="command-word" style="color: #CC0000">`filter`</span>

### [Search Commands](#search-commands)
* [Listing all persons](#listing-all-persons-list) - <span class="command-word" style="color: #CC0000">`list`</span>
* [Locating persons by keyword](#locating-persons-by-keyword-find) - <span class="command-word" style="color: #CC0000">`find`</span>

### [General Commands](#general-commands)
* [Viewing help](#viewing-help-help) - <span class="command-word" style="color: #CC0000">`help`</span>
* [Clearing all entries](#clearing-all-entries-clear) - <span class="command-word" style="color: #CC0000">`clear`</span>
* [Saving the data](#saving-the-data) - <span class="command-word" style="color: #CC0000">`save`</span>
* [Editing the data file](#editing-the-data-file) - <span class="command-word" style="color: #CC0000">`edit`</span>
* [Exiting the program](#exiting-the-program-exit) - <span class="command-word" style="color: #CC0000">`exit`</span>

A command consists of a <span class="command-word" style="color: #CC0000">command word</span> and zero or more <span class="parameter" style="color: #FF8C00">parameters</span>.

Example: <span class="command-word" style="color: #CC0000">add</span> <span class="parameter" style="color: #FF8C00">n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 pol/123456</span>

<box type="info" seamless>

**Note**

* For each command, the correct syntax is specified under "Format"
* Command words are case-insensitive
* Words in <span class="parameter" style="color: #FF8C00">`UPPER_CASE`</span> are the parameters to be supplied by the user
* Items in <span class="optional" style="color: #808080">[square brackets]</span> are optional
* Items with <span class="repeatable" style="color: #0066CC">`…`</span>​ after them can be used multiple times including zero times
    </box>

### Client Management Commands

#### Adding a person : <span class="command-word" style="color: #CC0000">`add`</span>

Adds a person to the address book.

Format: <span class="command-word" style="color: #CC0000">`add`</span> <span class="parameter" style="color: #FF8C00">n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS pol/POLICY_NUMBER</span> <span class="optional" style="color: #808080">[pt/POLICY_TYPE] [r/RENEWAL_DATE] [note/NOTE] [t/TAG]</span><span class="repeatable" style="color: #0066CC">…​</span>

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

*   NAME: Names must only include alphanumeric characters and spaces.
*   PHONE_NUMBER: Phone numbers must follow the E.164 standard.
*   EMAIL: The email must be in the localpart@domainname format. The localpart should only contain alphanumeric characters and these special characters, excluding the parentheses, (+_.-). The local-part may not start or end with any special characters. The domain name must: end with a domain label at least 2 characters long, have each domain label start and end with alphanumeric characters, and have each domain label consist of alphanumeric characters, separated only by hyphens, if any.
*   ADDRESS: The address can be any string value, but it must not be blank.
*   POLICY_NUMBER: The policy number must consist only of digits, ensuring that it is numeric and non‑blank.
*   POLICY_TYPE: Only the following case‑insensitive policy types are allowed: Life, Health, Property, Vehicle, Travel.
*   RENEWAL_DATE: The date must follow the DD-MM-YYYY format.
*   NOTE: There are no specific restrictions for notes; any string is accepted as a valid note.
*   TAG: A valid tag name must be entirely alphanumeric.

<box type="warning" seamless>

**Warning: Duplicate Entries**

A person is considered a duplicate if they have:
* The same policy number, OR
* The same name and email, OR
* The same name and phone number

If you attempt to add a person that matches any of these conditions:
* An error message will be shown indicating which condition was matched
* The person will not be added
* You should verify the information and try again with unique details
</box>

Examples:
* <span class="command-word" style="color: #CC0000">`add`</span> <span class="parameter" style="color: #FF8C00">n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 pol/12345678 pt/Life r/31-12-2025 note/Basketball Player</span>
* <span class="command-word" style="color: #CC0000">`add`</span> <span class="parameter" style="color: #FF8C00">n/Betsy Crowe t/friend pol/654321 pt/Health e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal</span>

Expected Output:

* Before running the command:
![Before Renew](images/add_before.png)

* After running the command:
![After Renew](images/add_success.png)

<box type="warning" seamless>

**Warning: Duplicate Policy Numbers**

Each policy number must be unique in the system. If you attempt to add a person with a policy number that already exists:
* An error message will be shown
* The person will not be added
* You should verify the correct policy number and try again
</box>

<box type="tip" seamless>

**Tip:** Names such as X Æ A-Xii Musk is not valid unfortunately as it contains non-ASCII characters.
</box>

#### Editing a person : <span class="command-word" style="color: #CC0000">`edit`</span>

Format: <span class="command-word" style="color: #CC0000">`edit`</span> <span class="parameter" style="color: #FF8C00">INDEX</span> <span class="optional" style="color: #808080">[n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pol/POLICY_NUMBER] [pt/POLICY_TYPE] [r/RENEWAL_DATE] [note/NOTE] [t/TAG]</span><span class="repeatable" style="color: #0066CC">…​</span>

*   Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
*   At least one of the optional fields must be provided.
*   Existing values will be updated to the input values.
*   When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
*   You can remove all the person's tags by typing `t/` without
    specifying any tags after it.

<box type="warning" seamless>

**Warning: Duplicate Entries**

A person is considered a duplicate if the edit would result in:
* The same policy number as another person, OR
* The same name and email as another person, OR
* The same name and phone number as another person

If your edit would create any of these duplicate conditions:
* An error message will be shown indicating which condition was matched
* The edit will not be applied
* You should modify your edit to ensure the result is unique
</box>

Examples:

* <span class="command-word" style="color: #CC0000">`edit`</span> <span class="parameter" style="color: #FF8C00">1 p/91234567 e/johndoe@example.com pt/Health r/31-12-2025</span>
* <span class="command-word" style="color: #CC0000">`edit`</span> <span class="parameter" style="color: #FF8C00">2 n/Betsy Crower t/</span>

Expected Output:
* Before running the command:
![Before Edit](images/edit_before.png)

* After running the command:
![Before Edit](images/edit_success.png)

#### Deleting a person : <span class="command-word" style="color: #CC0000">`delete`</span>

Format: <span class="command-word" style="color: #CC0000">`delete`</span> <span class="parameter" style="color: #FF8C00">INDEX</span>

*   Deletes the person at the specified `INDEX`.
*   The index refers to the index number shown in the displayed person list.
*   The index **must be a positive integer** 1, 2, 3, …​

<box type="danger" seamless>

**Warning: Irreversible Action ⛔**

* The delete command permanently removes the client's data
* This action cannot be undone
* Make sure you have selected the correct index before deleting
</box>

Examples:

* <span class="command-word" style="color: #CC0000">`delete`</span> <span class="parameter" style="color: #FF8C00">2</span>
* <span class="command-word" style="color: #CC0000">`find`</span> <span class="parameter" style="color: #FF8C00">n/Betsy</span> followed by <span class="command-word" style="color: #CC0000">`delete`</span> <span class="parameter" style="color: #FF8C00">1</span>

Expected Output:
* Before running the command:
![Before Delete](images/delete_before.png)

* After running the command:
![After Delete](images/delete_success.png)

### Policy Management Commands

#### Updating a policy renewal date : <span class="command-word" style="color: #CC0000">`renew`</span>

Format: <span class="command-word" style="color: #CC0000">`renew`</span> <span class="parameter" style="color: #FF8C00">pol/POLICY_NUMBER r/RENEWAL_DATE</span>

<box type="warning" seamless>

**Warning: Single Policy Renewal ⚠️**

* Only one policy can be renewed at a time
* To renew multiple policies, use the command separately for each policy
</box>

*   The `pol/POLICY_NUMBER` parameter must be a valid policy number in the system.
*   The `r/RENEWAL_DATE` parameter must be in the format `DD-MM-YYYY`.

Examples:

* <span class="command-word" style="color: #CC0000">`renew`</span> <span class="parameter" style="color: #FF8C00">pol/123456 r/01-01-2026</span>
<Br>

Expected Output:
* Before running the renew command:
![Before Renew](images/RenewCommandBefore.png)

* After running `renew pol/123456 r/01-01-2026`:
![After Renew](images/RenewCommandAfter.png)

#### Viewing upcoming policy renewals : <span class="command-word" style="color: #CC0000">`viewrenewals`</span>

Format: <span class="command-word" style="color: #CC0000">`viewrenewals`</span> <span class="optional" style="color: #808080">[n/NEXT_N_DAYS] [s/SORT_ORDER]</span>

<box type="warning" seamless>

**Warning: Integer Days Only ⚠️**

* The NEXT_N_DAYS parameter must be a positive integer
* Decimal numbers or negative values are not accepted
* Example: Use `n/30` for next 30 days, not `n/30.5` or `n/-30`
</box>

* The `n/NEXT_N_DAYS` parameter must be between 0 and 365.  
* The search results can be sorted using `s/SORT_ORDER` by `name` or by `date` only. The default sort order is by date if unspecified.

<box type="info" seamless>

**Note on Sorting Order for name:**

The sorting order is case-sensitive and follows standard computer ordering rules. This means that:
* Capital letters (A-Z) first
* Then lowercase letters (a-z)

For example: Alice, Bernice, Zebra, alpha, echo, zulu
</box>

Examples:

* <span class="command-word" style="color: #CC0000">`viewrenewals`</span>
* <span class="command-word" style="color: #CC0000">`viewrenewals`</span> <span class="parameter" style="color: #FF8C00">n/60</span>
* <span class="command-word" style="color: #CC0000">`viewrenewals`</span> <span class="parameter" style="color: #FF8C00">n/60 s/name</span>

Expected Output:
* Before running the viewrenewals command:
![Before View Renewals](images/ViewRenewalCommandBefore.png)

* After running `viewrenewals n/200 s/name`:
![After View Renewals](images/ViewRenewalCommandAfter.png)


#### View policy renewals in date range : <span class="command-word" style="color: #CC0000">`filter`</span>

Format: <span class="command-word" style="color: #CC0000">`filter`</span> <span class="parameter" style="color: #FF8C00">sd/START_DATE ed/END_DATE</span> <span class="optional" style="color: #808080">[s/SORT_ORDER]</span>

<box type="warning" seamless>

**Warning: Valid Date Range ⚠️**

* The END_DATE must be later than or equal to the START_DATE
* Both dates must be in DD-MM-YYYY format
* Example: `sd/01-01-2024 ed/31-12-2024` is valid
* Example: `sd/31-12-2024 ed/01-01-2024` is invalid (end date before start date)
</box>

*   The `sd/START_DATE` parameter must be in the format `DD-MM-YYYY`.
*   The `ed/END_DATE` parameter must be in the format `DD-MM-YYYY`.
*   The search results will include policy renewals between the specified start date and end date.
*   The search results can be sorted using `s/SORT_ORDER` by `name` or by `date` only. The default sort order is by date if unspecified. Name sorting follows the same rules as mentioned in the [viewrenewals](#viewing-upcoming-policy-renewals-viewrenewals) command.

Examples:

* <span class="command-word" style="color: #CC0000">`filter`</span> <span class="parameter" style="color: #FF8C00">sd/01-03-2025 ed/31-03-2025</span>
* <span class="command-word" style="color: #CC0000">`filter`</span> <span class="parameter" style="color: #FF8C00">sd/01-01-2025 ed/30-06-2025 s/name</span>

Expected Output:
* Before running the filter command:
![Before Filter](images/FilterBefore.png)

* After running `filter sd/01-06-2025 ed/01-01-2026`:
![After Filter](images/FilterAfter.png)


### Search Commands

#### Listing all persons : <span class="command-word" style="color: #CC0000">`list`</span>

Shows a list of all persons in the address book.

Format: <span class="command-word" style="color: #CC0000">`list`</span>

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

#### Locating persons by keyword : <span class="command-word" style="color: #CC0000">`find`</span>

Searches given person fields by specific keyword and returns any matching entries
Format: <span class="command-word" style="color: #CC0000">`find`</span> <span class="optional" style="color: #808080">[n/NAME]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[p/PHONE]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[e/EMAIL]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[a/ADDRESS]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[pol/POLICY_NUMBER]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[pt/POLICY_TYPE]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[t/TAG]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[s/SORT_ORDER]</span><span class="repeatable" style="color: #0066CC">…​</span>

*   At least one of the optional fields must be provided.
*   Each field may be provided more than once except `SORT_ORDER`.
*   The fields follow the same rules and constraints as detailed in the [add](#adding-a-person-add) command.
*   `NAME` and `ADDRESS` field may contain more than one word.
*   The search is case-insensitive. e.g `hans` will match `Hans`
*   The order of the values matter for a field but not for different fields. e.g. `n/Hans Bo` will not match `Bo Hans` but `n/Hans n/Bo` will match `Bo Hans`
*   Partial search for Name, Phone number, Email address, Physical address and Policy number is allowed
*   Exact search is required for Policy type and Tags
*   Partial words e.g. `n/Han` will match `Hans`
*   Partial numbers e.g. `p/123` will match `91234567` 
*   Partial search for emails must be concatenated with `@` followed by at least 2 characters e.g. `e/ice@ex` will match `alice@example.com` but `alice@e` will not match `alice@example.com`
*   Persons matching at least one field will be returned (i.e. `OR` search).
    e.g. `n/Hans n/Bo` will return `Hans Gruber`, `Bo Yang`
*   Tags are supported. You can add one or more tags using `t/TAG`. The search for tags is not case-sensitive and must be an exact word.
*   Policy types are supported. You can search for specific policy types using `pt/POLICY_TYPE`. Valid policy types are: Life, Health, Property, Vehicle, and Travel. The search is not case-sensitive.
*   The search results can be sorted using `s/SORT_ORDER` by `name` or by `tag` only. The default sort order is by name. Tag sorting sorts by entries with the most number of tags first. Name sorting follows the same rules as mentioned in the [viewrenewals](#viewing-upcoming-policy-renewals-viewrenewals) command.

Examples:

* <span class="command-word" style="color: #CC0000">`find`</span> <span class="parameter" style="color: #FF8C00">n/olive</span>
* <span class="command-word" style="color: #CC0000">`find`</span> <span class="parameter" style="color: #FF8C00">n/Amy p/999</span>
* <span class="command-word" style="color: #CC0000">`find`</span> <span class="parameter" style="color: #FF8C00">n/alex n/david</span>
* <span class="command-word" style="color: #CC0000">`find`</span> <span class="parameter" style="color: #FF8C00">t/friends t/colleagues s/tag</span>

Expected Output (Partial name searching example):
* Before running the find command:
  ![Before Find Command Example 1](images/FindCommandBefore.png)

* After running `find n/olive`:
  ![After Find Command Example 1](images/FindCommandAfter.png)

Expected Output (Tag searching example):
* Before running the find command:
  ![Before Find Command Example 2](images/FindCommand2Before.png)

* After running `find t/friends t/colleagues s/tag`:
  ![After Find Command Example 2](images/FindCommand2After.png)

### General Commands

#### Viewing help : <span class="command-word" style="color: #CC0000">`help`</span>

Shows a message explaining how to access the help page.

Format: <span class="command-word" style="color: #CC0000">`help`</span>

Expected output after running `help` : ![Help](images/Help.png)

#### Clearing all entries : <span class="command-word" style="color: #CC0000">`clear`</span>

Format: <span class="command-word" style="color: #CC0000">`clear`</span>

<box type="danger" seamless>

**Warning: Irreversible Action ⛔**

* The clear command permanently removes ALL client data
* This action CANNOT be undone
* Double-check that you really want to delete everything
</box>

Expected output after running `clear`: ![Clear](images/Clear.png)

#### Exiting the program : <span class="command-word" style="color: #CC0000">`exit`</span>

Format: <span class="command-word" style="color: #CC0000">`exit`</span>

Expected output after running `exit`: InsureBook **closes**.

### FAQ

**Q:** How do I add a new person to the address book?

**A:** Use the <span class="command-word" style="color: #CC0000">`add`</span> command. For example, <span class="command-word" style="color: #CC0000">`add`</span> <span class="parameter" style="color: #FF8C00">n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 pol/123456 pt/Life r/31-12-2024 note/Basketball Player</span>.

**Q:** How do I edit a person's information?

**A:** Use the <span class="command-word" style="color: #CC0000">`edit`</span> command. For example, <span class="command-word" style="color: #CC0000">`edit`</span> <span class="parameter" style="color: #FF8C00">1 p/91234567 e/johndoe@example.com pt/Health r/31-12-2024</span>.

**Q:** How do I delete a person from the address book?

**A:** Use the <span class="command-word" style="color: #CC0000">`delete`</span> command. For example, <span class="command-word" style="color: #CC0000">`delete`</span> <span class="parameter" style="color: #FF8C00">1</span>.

**Q:** How do I clear all entries from the address book?

**A:** Use the <span class="command-word" style="color: #CC0000">`clear`</span> command. For example, <span class="command-word" style="color: #CC0000">`clear`</span>.

**Q:** How do I exit the program?

**A:** Use the <span class="command-word" style="color: #CC0000">`exit`</span> command. For example, <span class="command-word" style="color: #CC0000">`exit`</span>.

**Q:** How do I update a policy renewal date?

**A:** Use the <span class="command-word" style="color: #CC0000">`renew`</span> command. For example, <span class="command-word" style="color: #CC0000">`renew`</span> <span class="parameter" style="color: #FF8C00">pol/123456 r/31-12-2025</span>.

**Q:** How do I view upcoming policy renewals?

**A:** Use the <span class="command-word" style="color: #CC0000">`viewrenewals`</span> command. For example, <span class="command-word" style="color: #CC0000">`viewrenewals`</span> <span class="parameter" style="color: #FF8C00">n/60</span>.

**Q:** How do I filter policy renewals in a date range?

**A:** Use the <span class="command-word" style="color: #CC0000">`filter`</span> command. For example, <span class="command-word" style="color: #CC0000">`filter`</span> <span class="parameter" style="color: #FF8C00">sd/01-03-2025 ed/31-03-2025</span>.

**Q:** How do I save the data?

**A:** Use the <span class="command-word" style="color: #CC0000">`save`</span> command. For example, <span class="command-word" style="color: #CC0000">`save`</span> <span class="parameter" style="color: #FF8C00">addressbook.json</span>.

**Q:** How do I edit the data file?

**A:** Use the <span class="command-word" style="color: #CC0000">`edit`</span> command. For example, <span class="command-word" style="color: #CC0000">`edit`</span> <span class="parameter" style="color: #FF8C00">addressbook.json</span>.

### Known Issues

**I:** I'm unable to add a new person to the address book.

**A:** Ensure that all fields are filled out correctly and that the phone number, email, and policy number are in the correct format.

**I:** I'm unable to edit a person's information.

**A:** Ensure that the index is correct and that all fields are filled out correctly.

**I:** I'm unable to delete a person from the address book.

**A:** Ensure that the index is correct and that the index exists in the address book.

**I:** I'm unable to clear all entries from the address book.

**A:** Ensure that you have the correct permissions to clear the address book.

**I:** I'm unable to exit the program.

**A:** Ensure that you have saved all changes before exiting the program.

**I:** I'm unable to update a policy renewal date.

**A:** Ensure that the policy number is correct and that the renewal date is in the correct format.

**I:** I'm unable to view upcoming policy renewals.

**A:** Ensure that the program is up to date and that there are no issues with the data file.

**I:** I'm unable to filter policy renewals in a date range.

**A:** Ensure that the start and end dates are in the correct format and that the data file is up to date.

### Command Summary

### Client Management Commands
| Command | Format |
|---------|---------|
| <span class="command-word" style="color: #CC0000">`add`</span> | <span class="command-word" style="color: #CC0000">`add`</span> <span class="parameter" style="color: #FF8C00">n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS pol/POLICY_NUMBER</span> <span class="optional" style="color: #808080">[pt/POLICY_TYPE] [r/RENEWAL_DATE] [note/NOTE] [t/TAG]</span><span class="repeatable" style="color: #0066CC">…​</span> |
| <span class="command-word" style="color: #CC0000">`edit`</span> | <span class="command-word" style="color: #CC0000">`edit`</span> <span class="parameter" style="color: #FF8C00">INDEX</span> <span class="optional" style="color: #808080">[n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pol/POLICY_NUMBER] [pt/POLICY_TYPE] [r/RENEWAL_DATE] [note/NOTE] [t/TAG]</span><span class="repeatable" style="color: #0066CC">…​</span> |
| <span class="command-word" style="color: #CC0000">`delete`</span> | <span class="command-word" style="color: #CC0000">`delete`</span> <span class="parameter" style="color: #FF8C00">INDEX</span> |

### Policy Management Commands
| Command | Format |
|---------|---------|
| <span class="command-word" style="color: #CC0000">`renew`</span> | <span class="command-word" style="color: #CC0000">`renew`</span> <span class="parameter" style="color: #FF8C00">pol/POLICY_NUMBER r/RENEWAL_DATE</span> |
| <span class="command-word" style="color: #CC0000">`viewrenewals`</span> | <span class="command-word" style="color: #CC0000">`viewrenewals`</span> <span class="optional" style="color: #808080">[n/NEXT_N_DAYS] [s/SORT_ORDER]</span> |
| <span class="command-word" style="color: #CC0000">`filter`</span> | <span class="command-word" style="color: #CC0000">`filter`</span> <span class="parameter" style="color: #FF8C00">sd/START_DATE ed/END_DATE</span> <span class="optional" style="color: #808080">[s/SORT_ORDER]</span> |

### Search Commands
| Command | Format |
|---------|---------|
| <span class="command-word" style="color: #CC0000">`list`</span> | <span class="command-word" style="color: #CC0000">`list`</span> |
| <span class="command-word" style="color: #CC0000">`find`</span> | <span class="command-word" style="color: #CC0000">`find`</span> <span class="optional" style="color: #808080">[n/NAME]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[p/PHONE]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[e/EMAIL]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[a/ADDRESS]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[pol/POLICY_NUMBER]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[pt/POLICY_TYPE]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[t/TAG]</span><span class="repeatable" style="color: #0066CC">…​</span> <span class="optional" style="color: #808080">[s/SORT_ORDER]</span><span class="repeatable" style="color: #0066CC">…​</span> |

### General Commands
| Command | Format |
|---------|---------|
| <span class="command-word" style="color: #CC0000">`help`</span> | <span class="command-word" style="color: #CC0000">`help`</span> |
| <span class="command-word" style="color: #CC0000">`clear`</span> | <span class="command-word" style="color: #CC0000">`clear`</span> |
| <span class="command-word" style="color: #CC0000">`exit`</span> | <span class="command-word" style="color: #CC0000">`exit`</span> |

---

## Glossary

| Term | Meaning |
|---------|---------|
| <span class="command-word" style="color: #CC0000">`Agent`</span> | <span class="command-word" style="color: #000000">Refers to the insurance agent who uses InsureBook to manage and organize client information.</span> |
| <span class="command-word" style="color: #CC0000">`Client`</span> | <span class="command-word" style="color: #000000">Refers to an individual’s entry in InsureBook, containing contact information (name, phone, email, address) and at least one insurance policy.</span> |
| <span class="command-word" style="color: #CC0000">`InsureBook`</span> | <span class="command-word" style="color: #000000">A desktop application (with both a CLI and GUI) that helps insurance agents manage and organize client information.</span> |
| <span class="command-word" style="color: #CC0000">`CLI (Command Line Interface)`</span> | <span class="command-word" style="color: #000000">A text-based interface where users type commands to interact with the application.</span> |
| <span class="command-word" style="color: #CC0000">`GUI (Graphical User Interface)`</span> | <span class="command-word" style="color: #000000">A visual interface that displays buttons, text fields, and other elements, providing a more familiar point-and-click experience.</span> |
| <span class="command-word" style="color: #CC0000">`Policy`</span> | <span class="command-word" style="color: #000000">An insurance policy associated with the client. The policy number is a unique numeric identifier (e.g., “123456”).</span> |
| <span class="command-word" style="color: #CC0000">`Policy Type`</span> | <span class="command-word" style="color: #000000">Category of an insurance policy. Valid options include Life, Health, Property, Vehicle, or Travel.</span> |
| <span class="command-word" style="color: #CC0000">`Renewal Date`</span> | <span class="command-word" style="color: #000000">The date (DD‑MM‑YYYY) on which the policy is due to be renewed. Used by commands like <code>renew</code>, <code>viewrenewals</code>, and <code>filter</code>.</span> |
| <span class="command-word" style="color: #CC0000">`Tag`</span> | <span class="command-word" style="color: #000000">A label that can be attached to a client for categorization or quick reference (e.g., “friend,” “colleague,” “VIP”).</span> |
| <span class="command-word" style="color: #CC0000">`Command Word`</span> | <span class="command-word" style="color: #000000">The keyword used to invoke a specific operation in InsureBook (e.g., <code>add</code>, <code>edit</code>, <code>delete</code>, <code>find</code>).</span> |
| <span class="command-word" style="color: #CC0000">`Parameter`</span> | <span class="command-word" style="color: #000000">Additional information following a command word, usually prefixed with a letter and slash (e.g., <code>n/NAME</code>, <code>p/PHONE_NUMBER</code>).</span> |
| <span class="command-word" style="color: #CC0000">`Index`</span> | <span class="command-word" style="color: #000000">The number shown beside a client’s entry in the displayed list. Used to identify which client to edit or delete.</span> |
| <span class="command-word" style="color: #CC0000">`Duplicate Entries`</span> | <span class="command-word" style="color: #000000">If you try to add or edit a client so that it shares a policy number or certain combination of name/phone/email with an existing client, InsureBook treats it as a duplicate and blocks the action.</span> |
| <span class="command-word" style="color: #CC0000">`Sort Order`</span> | <span class="command-word" style="color: #000000">Some commands (like <code>viewrenewals</code> and <code>find</code>) let you sort results by <code>name</code> or <code>tag</code>.</span> |
| <span class="command-word" style="color: #CC0000">`Data File`</span> | <span class="command-word" style="color: #000000">The file (often named <code>addressbook.json</code>) where InsureBook stores all client data. Used by commands like <code>save</code> and <code>edit</code>.</span> |

---
