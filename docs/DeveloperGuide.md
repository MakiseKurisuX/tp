---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# InsureBook Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

---

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of InsureBook.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes `Main` and `MainApp`) is in charge of the app launch and shut down.

The bulk of the app's work is done by the following four components:

-   [**`UI`**](#ui-component): The UI of InsureBook.
-   [**`Logic`**](#logic-component): The command executor.
-   [**`Model`**](#model-component): Holds the data of InsureBook in memory.
-   [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

-   defines its _API_ in an `interface` with the same name as the Component.
-   implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `RenewalsTable`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

-   executes user commands using the `Logic` component.
-   listens for changes to `Model` data so that the UI can be updated with the modified data.
-   keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
-   depends on some classes in the `Model` component, as it displays `Person` and `Policy` objects residing in the `Model`.

#### Person Card UI

The person card UI is implemented using the following components:

-   `PersonListCard.fxml`: Defines the layout of each person card, including:

    -   Name and ID
    -   Contact information (phone, email, address)
    -   Policy information (policy number and renewal date)
    -   Tags

-   `PersonCard.java`: Controls the display of person information in the card:
    -   Binds UI elements to person data
    -   Formats the renewal date display with the prefix "Renewal date: " for clarity
    -   Manages tag display

The person card provides a compact view of all essential client information, making it easy for insurance agents to quickly access client details and track policy renewals. The renewal date is prominently displayed with a clear label to help agents quickly identify when policies need to be renewed.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

-   When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
-   All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

-   stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
-   stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
-   stores a `UserPref` object that represents the user's preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
-   does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

-   can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
-   inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
-   depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Policy Renewal Feature

The policy renewal feature allows insurance agents to track and manage policy renewals for their clients. It is implemented using the following components:

#### Policy Class

The `Policy` class represents an insurance policy and contains:

-   Policy number (in format POL-XXX)
-   Renewal date
-   Methods to calculate days until renewal

#### ViewRenewalsCommand

The `ViewRenewalsCommand` allows users to view policies due for renewal within a specified number of days:

-   Takes a parameter for number of days (1-365)
-   Optional sort parameter (by name or date)
-   Filters the person list based on policy renewal dates
-   Updates the UI to show filtered results

#### Implementation

The renewal tracking mechanism is facilitated by the `Policy` class and the `ViewRenewalsCommand`. Here's how it works:

1. When a user executes `viewrenewals n/30`, the command is parsed by `ViewRenewalsCommandParser`.
2. The parser validates the days parameter (must be 1-365) and optional sort parameter.
3. A new `ViewRenewalsCommand` is created with the validated parameters.
4. When executed, the command:
    - Filters the person list to include only those with policies due within the specified days
    - Updates the model's filtered person list
    - Updates the renewals table in the UI
    - Returns a command result with the number of matching entries

The following sequence diagram shows how the viewrenewals operation works:

<puml src="diagrams/ViewRenewalsSequenceDiagram.puml" width="800"/>

#### Design Considerations

**Aspect: How to calculate renewal due dates**

-   **Alternative 1 (current choice):** Calculate days until renewal on demand

    -   Pros: More memory efficient
    -   Cons: May impact performance if calculated frequently

-   **Alternative 2:** Store days until renewal as a field
    -   Pros: Faster retrieval
    -   Cons: Needs to be updated daily

**Aspect: Where to implement filtering logic**

-   **Alternative 1 (current choice):** In the command

    -   Pros: Keeps filtering logic with the command that needs it
    -   Cons: Logic might be duplicated if needed elsewhere

-   **Alternative 2:** In the Model
    -   Pros: Centralizes filtering logic
    -   Cons: Makes Model more complex

### Renewal Date Update Feature

The renewal date update feature allows insurance agents to directly update a client's policy renewal date by specifying the policy number, without needing to find the client's index in the list. This feature streamlines the renewal date management process.

#### Implementation

The renewal date update functionality is implemented through the `RenewCommand` class, which follows the command pattern used throughout the application. The feature is primarily made up of the following components:

1. `RenewCommand` - Executes the updating of a renewal date for a client with a specific policy number
2. `RenewCommandParser` - Parses and validates the user input into a RenewCommand object

The following class diagram shows the structure of the Renew Command:

<puml src="diagrams/RenewCommandClassDiagram.puml" width="800"/>

The feature works through the following process flow:

1. The user enters a command in the format `renew pol/POLICY_NUMBER r/RENEWAL_DATE`.
2. The `LogicManager` passes the command string to `AddressBookParser`.
3. `AddressBookParser` identifies the command as a `renew` command and delegates to `RenewCommandParser`.
4. `RenewCommandParser` extracts and validates:
    - Policy number (must be a valid policy number format)
    - Renewal date (must be a valid date in DD-MM-YYYY format)
5. `LogicManager` calls the `execute()` method of the command object.
6. The `RenewCommand`:
    - Filters the list of persons to find those with the specified policy number
    - Validates that exactly one match is found (not zero, not multiple)
    - Creates a new `Person` with the updated renewal date while preserving other fields (including policy type)
    - Updates the model with the new `Person` object
    - Returns a `CommandResult` with a success message

The following sequence diagram shows how the renew operation works:

<puml src="diagrams/RenewSequenceDiagram.puml" width="800"/>

#### Design Considerations

**Aspect: How to identify the client to update:**

-   **Alternative 1 (current choice):** Use policy number as identifier.

    -   Pros: More intuitive for insurance agents who often reference clients by policy number.
    -   Cons: Requires handling cases where multiple clients have the same policy number.

-   **Alternative 2:** Use client index in the displayed list.
    -   Pros: Consistent with other commands like `edit` and `delete`.
    -   Cons: Less convenient as agents need to find the index first.

**Aspect: Error handling for duplicate policy numbers:**

-   **Alternative 1 (current choice):** Show error and suggest using `edit` command.

    -   Pros: Prevents unintended updates to the wrong client.
    -   Cons: Less convenient when there are duplicate policy numbers.

-   **Alternative 2:** Update all clients with matching policy numbers.
    -   Pros: More convenient if updating all matching policies is the intended action.
    -   Cons: High risk of unintended updates; insurance operations generally require precision.

### \[Proposed\] Undo/redo feature

#### Current Implementation

The policy type feature enhances the insurance management capabilities of the application by allowing users to categorize policies into specific types (Life, Health, Property, Vehicle, and Travel). This helps insurance agents quickly identify and manage different types of policies.

The implementation consists of the following key components:

1. **`PolicyType` Enum** - Defines the available policy types and provides utilities for validation and conversion.

    ```java
    public enum PolicyType {
        LIFE, HEALTH, PROPERTY, VEHICLE, TRAVEL;

        public static PolicyType fromString(String type) { ... }
        public static boolean isValidPolicyType(String test) { ... }
    }
    ```

2. **`Policy` Class Extension** - The existing `Policy` class has been enhanced to include a `PolicyType` field.

    ```java
    public class Policy {
        // Existing fields
        public final String policyNumber;
        public final RenewalDate renewalDate;
        // New field
        private final PolicyType type;

        // Constructors that handle policy type
        public Policy(String policyNumber, String renewalDate, String type) { ... }

        // Getter for policy type
        public PolicyType getType() { ... }
    }
    ```

3. **Command Parsers** - The parsers for `AddCommand`, `EditCommand`, and `FindCommand` have been updated to recognize and process the policy type prefix (`pt/`).

4. **UI Components** - The `PersonCard` and `RenewalsTable` UI components have been modified to display the policy type.

5. **Predicate for Searching** - A `PolicyTypeContainsKeywordsPredicate` class has been added to support searching by policy type.

#### Design Considerations

**Aspect: Implementation of Policy Types**

-   **Alternative 1 (current choice):** Use an enumeration to represent policy types.

    -   Pros: Type safety, easy validation, prevents invalid policy types.
    -   Cons: Less flexible if new types need to be added (requires code changes).

-   **Alternative 2:** Use a string field without constraints.
    -   Pros: More flexible, users can add any type they want.
    -   Cons: Less type safety, harder to validate, potential for inconsistent data (e.g., "Health" vs "health").

**Aspect: Storage of Policy Type**

-   **Alternative 1 (current choice):** Store as part of the Policy object.

    -   Pros: Logical grouping, keeps policy information together.
    -   Cons: Increases complexity of the Policy class.

-   **Alternative 2:** Store as a separate field in the Person object.
    -   Pros: Simpler Policy class.
    -   Cons: Less logical grouping, policy information is split between different attributes.

#### Example Usage

The following diagram shows how the policy type feature interacts with the existing components:

```
User Input: add n/John Doe pt/Life ...
    v
AddCommandParser (parses policy type prefix)
    v
AddCommand (creates Person with Policy that includes PolicyType)
    v
Model (stores Person)
    v
UI (displays policy type in PersonCard and RenewalsTable)
```

When the user adds a new person with a policy type:

1. The `AddCommandParser` parses the policy type prefix and value.
2. A new `Policy` object is created with the specified policy type.
3. This `Policy` is included in the new `Person` object.
4. The UI components display the policy type along with other person information.

Similarly, when editing a person's policy type or finding persons by policy type, the appropriate parsers handle the policy type prefix and create the necessary commands or predicates.

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

---

## **Documentation, logging, testing, configuration, dev-ops**

-   [Documentation guide](Documentation.md)
-   [Testing guide](Testing.md)
-   [Logging guide](Logging.md)
-   [Configuration guide](Configuration.md)
-   [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

-   Insurance Agents who need to keep track of Customers / Potential Customers
-   Moderate Tech saviness.
-   prefer desktop apps over other types
-   can type fast
-   prefers typing to mouse interactions
-   is reasonably comfortable using CLI apps

**Value proposition**: It solves the issue of managing a large clientele by simplifying client tracking, automating follow-ups, and staying organized. By using InsureBook, insurance agents can focus more on growth and client retention, rather than spending more time on admin tasks and more time on sales.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​         | I want to …​                     | So that I can…​                              |
| -------- | --------------- | -------------------------------- | -------------------------------------------- |
| `* * *`  | Insurance Agent | add new clients                  | reach out to them when needed                |
| `* * *`  | Insurance Agent | view a list of clients           | quickly access current and potential clients |
| `* * *`  | Insurance Agent | update client information        | ensure records remain accurate               |
| `* * *`  | Insurance Agent | delete a client entry            | remove outdated clients                      |
| `* * *`  | Insurance Agent | search for a client              | quickly find them through their details      |
| `* * *`  | Insurance Agent | filter clients by renewal date   | prioritize follow-ups effectively            |
| `* * *`  | Insurance Agent | tag clients for sorting & search | organize and categorize my clients           |
| `* * *`  | Insurance Agent | set reminders for renewals       | never miss important deadlines               |
| `* * *`  | Insurance Agent | persist client data              | ensure no data is lost                       |
| `* * *`  | Insurance Agent | filter and sort clients by tags  | manage clients more efficiently              |

_{More to be added}_

### Use Cases

(For all use cases below, the **System** is the `Client Management System` and the **Actor** is the `Insurance Agent`, unless specified otherwise)

---

**Use case: Add a new client**

**MSS**

1. Insurance Agent requests to add a new client.
2. System prompts for client details.
3. Insurance Agent enters required details.
4. System validates and saves the new client.

    Use case ends.

**Extensions**

-   4a. The provided details are invalid.

    -   4a1. System shows an error message.
    -   4a2. Use case resumes at step 2.

-   4b. A client with the same name and phone number already exists.
    -   4b1. System shows a duplicate warning and rejects the addition.

---

**Use case: View a list of clients**

**MSS**

1. Insurance Agent requests to list clients.
2. System displays all stored clients in alphabetical order.

    Use case ends.

**Extensions**

-   2a. No clients have been added.
    -   2a1. System shows "No clients added yet."

---

**Use case: Update client information**

**MSS**

1. Insurance Agent requests to update a client's information.
2. System prompts for the client index and new details.
3. Insurance Agent provides updates.
4. System validates and updates the information.

    Use case ends.

**Extensions**

-   4a. Provided details are invalid.

    -   4a1. System shows an error message.
    -   4a2. Use case resumes at step 2.

-   4b. Client does not exist.
    -   4b1. System shows an error message.

---

**Use case: Delete a client**

**MSS**

1. Insurance Agent requests to list clients.
2. System shows a list of clients.
3. Insurance Agent requests to delete a specific client.
4. System deletes the client.

    Use case ends.

**Extensions**

-   2a. The list is empty.

    -   2a1. System shows "No clients available."

-   3a. The given index is invalid.
    -   3a1. System shows an error message.
    -   3a2. Use case resumes at step 2.

---

**Use case: Search for a client**

**MSS**

1. Insurance Agent requests to search for a client by specific criteria.
2. System displays matching clients.

    Use case ends.

**Extensions**

-   2a. No matching clients found.
    -   2a1. System shows "No clients found."

---

**Use case: Filter clients by renewal date**

**MSS**

1. Insurance Agent requests to filter clients by renewal date.
2. System displays clients with renewals within the specified period.

    Use case ends.

**Extensions**

-   2a. No clients match the criteria.
    -   2a1. System shows "No upcoming renewals."

---

**Use case: Tag clients for sorting & search**

**MSS**

1. Insurance Agent requests to tag a client.
2. System adds the tag to the client's record.

    Use case ends.

**Extensions**

-   2a. Tag exceeds character limit.

    -   2a1. System truncates the tag and shows a warning.

-   2b. Tag is a duplicate.
    -   2b1. System shows "Tag already exists."

---

**Use case: Set reminders for renewals**

**MSS**

1. Insurance Agent requests to set a renewal reminder for a client.
2. System schedules the reminder.

    Use case ends.

**Extensions**

-   2a. Client does not have a policy renewal date.
    -   2a1. System shows an error message.

---

**Use case: Persist client data**

**MSS**

1. System automatically saves client data when changes are made.

    Use case ends.

**Extensions**

-   1a. System encounters an error while saving.
    -   1a1. System shows an error message.

---

**Use case: Filter and sort clients by tags**

**MSS**

1. Insurance Agent requests to filter clients by specific tags.
2. System displays a list of clients with the matching tags.

    Use case ends.

**Extensions**

-   2a. No clients match the specified tags.
    -   2a1. System shows "No clients found for the selected tags."

---

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 clients without noticeable sluggishness in performance.
3. A user with above-average typing speed should be able to accomplish most tasks faster using commands than using the mouse.
4. Client data should persist even if the system shuts down unexpectedly.

### Glossary

-   **Mainstream OS**: Windows, Linux, Unix, MacOS
-   **Client**: A person managed within the system with relevant details such as contact, policy information, and tags.
-   **Tag**: A custom keyword used to categorize clients for sorting and filtering.

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more _exploratory_ testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
