---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TalentFolio User Guide

TalentFolio is a powerful yet easy-to-use application designed for hiring managers to efficiently manage information on job candidates. It is optimized for users who prefer a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you're a fast typist, TalentFolio can significantly speed up your hiring workflow!

<!-- * Table of Contents -->
## Table of Contents

1. [Quick start](#quick-start)  
  a. [Install Java (if not already installed)](#1-install-java-if-not-already-installed)  
  b. [Download TalentFolio](#2-download-talentfolio)  
  c. [Set up TalentFolio](#3-set-up-talentfolio)  
  d. [Running TalentFolio](#4-running-talentfolio)  
  e. [Using TalentFolio](#5-using-talentfolio)  
  f. [Need more help?](#6-need-more-help)  
1. [Features](#features)  
  a. [Viewing help: `help`](#viewing-help-help)  
  b. [Showing a person's details: `show`](#showing-a-person-s-details-show)  
  c. [Adding a person: `add`](#adding-a-person-add)  
  d. [Listing all persons: `list`](#listing-all-persons-list)  
  e. [Editing a person: `edit`](#editing-a-person-edit)  
  f. [Linking an attribute name to a website: `link`](#linking-an-attribute-name-to-a-website-link)  
  g. [Locating persons by name: `find`](#locating-persons-by-name-find)  
  h. [Deleting a person: `delete`](#deleting-a-person-delete)  
  i. [Clearing all entries: `clear`](#clearing-all-entries-clear)  
  j. [Filtering candidates by attributes: `filter`](#filtering-candidates-by-attributes-filter)  
  l. [Sorting entries by an attribute: `sort`](#sorting-entries-by-an-attribute-sort)  
  m. [Sorting entries by the numerical value of an attribute: `sort-num`](#sorting-entries-by-the-numerical-value-of-an-attribute-sort-num)  
  n. [Undoing the last data change: `undo`](#undoing-the-last-data-change-undo)  
  o. [Navigating past commands: <kbd>↑</kbd> <kbd>↓</kbd>](#navigating-past-commands)  
  p. [Exiting the program: `exit`](#exiting-the-program-exit)  
  q. [Saving the data](#saving-the-data)  
  r. [Editing the data file](#editing-the-data-file)
1. [FAQ](#faq)
1. [Known issues](#known-issues)
1. [Command summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

Follow these simple steps to get TalentFolio up and running:

### 1. Install Java (if not already installed)
TalentFolio requires Java `17` or later to run. Check your Java version by opening a terminal or command prompt and typing:<br>
`java -version`<br>If Java is not installed or is an older version:
* **Windows/Linux users:** Download and install the latest Java Development Kit (JDK) from [Oracle's website](https://www.oracle.com/java/technologies/downloads/).
* **Mac users:** Follow the instructions and ensure you have the precise Java Development Kit (JDK) version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

### 2. Download TalentFolio
Get the latest version of TalentFolio from our [official release page](https://github.com/AY2425S2-CS2103T-T10-1/tp/releases). Download the latest `.jar` file available.

### 3. Set up TalentFolio
Move the `.jar` file to the folder you want to use as the _home folder_ for TalentFolio. This folder will store the TalentFolio application data. We recommend choosing a convenient location that you have read, write and execute permissions for.

### 4. Running TalentFolio
Open a command terminal:
* **Windows users:** Press `Win + R`, type `cmd`, and hit Enter.
* **Mac/Linux users** Open the Terminal app.<br>
Navigate to the folder where you placed the `.jar` file. You can do this using the `cd` command. For example:<br>
`cd path/to/your/folder`<br>
Run the following command in the terminal to launch the application:<br>
`java -jar talentfolio.jar`<br>
After a few seconds, the TalentFolio application should open, and you should see a GUI similar to the one shown below. Note that the app contains some sample data. It is recommended to clear the sample data before your own personal use as it will not be automatically overwritten.<br>

<pic src="images/Ui.png" alt="GUI">
TalentFolio's graphical user interface
</pic>
<br/>


### 5. Using TalentFolio
   Type the command in the command box and press Enter to execute it. For example, typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all candidates.

   * `add n/John Doe p/98765432 e/johnd@example.com` : Adds a candidate named `John Doe` to the database.

   * `edit 1 a/GPA=3.9 ra/CAP` : Adds a GPA and removes a CAP from the 1st candidate shown in the current list.

   * `filter a/Major=Computer Science` : Filters all candidates who major in Computer Science.

   * `sort a/Graduation Year` : Sorts candidates in alphabetically ascending order of Graduation Year.

   * `delete 3` : Deletes the 3rd candidate shown in the current list.

   * `clear` : Deletes all candidates.

   * `undo` : Undoes the last command.

   * `exit` : Exits the app.

### 6. Need more help?
   For a complete list of features and detailed instructions of each command, check out the [Features](#features) section below.<br>
   Enjoy using TalentFolio to streamline your hiring process!

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info">

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  For example: in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  For example: `n/NAME [t/TAG]` can be used as `n/John Doe t/Excel` or as `n/John Doe`.

* Items with `…`​ after them can be used as many times as desired–if the items are optional, 0 or more times; otherwise, 1 or more times. <br>
  For example: `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/Excel`, `t/C++ t/Java` etc.

* Parameters can be in any order.<br>
  For example: if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  For example: if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help: `help`

Shows a message explaining how to access the help page.

<pic src="images/helpMessage.png" alt="Help message">
Help message window
</pic>

Format: `help`

### Showing a person's details: `show`

Shows the specified person's details in the right panel.

Format: `show INDEX`

* You can also view a person's details by clicking on them in the left panel.

Example:
* `show 1` shows the details of the 1st person.<br/>
  <pic src="images/showAlexYeohResult.png" alt="Results after typing `show 1`">
  Results after typing `show 1`
  </pic>

<box type="tip">

**Tip:** In the right panel, you can click on the phone number or email address to copy it to your clipboard.
</box>

### Adding a person: `add`

Adds a person to the database.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL [t/TAG]… [a/ATTRIBUTE_NAME=ATTRIBUTE_VALUE]…`

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com` Adds a person named `John Doe` with phone number `98765432` and email address `johnd@example.com`.
* `add n/Betsy Crowe t/C++ e/betsycrowe@example.com p/1234567 t/Java a/Major=Data Science and Analytics` Adds a person named `Betsy Crowe` with phone number `1234567`, email address `betsycrowe@example.com`, tags `C++` and `Java`, and an attribute named `Major` with the value `Data Science and Analytics`.

<box type="tip">

**Tip:** A person can have any number of tags and attributes (including 0).
</box>

<box type="info">

**More information on attributes:**
* Attribute names are unique. A person cannot have multiple attributes with the same name.
* Attribute names are case-insensitive. For example, `Major` is treated the same as `major`, so a person cannot have both `Major` and `major` as an attribute.
  * The original casing of your input is preserved. If you input `Major`, that's how it will be stored and displayed.
* Attribute values are case-sensitive.
</box>

### Listing all persons: `list`

Shows a list of all persons in the database.

Format: `list`

### Editing a person: `edit`

Edits an existing person in the database.

Format: `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG]… [a/ATTRIBUTE_NAME=ATTRIBUTE_VALUE]… [ra/NAME_OF_ATTRIBUTE_TO_REMOVE]…`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
*  `edit 1 a/Graduation Year=2027` Adds or edits an attribute named `Graduation Year` to the 1st person.
*  `edit 1 ra/Graduation Year` Removes the attribute named `Graduation Year` from the 1st person.

<box type="info">

**For tags:**
* When editing tags, the existing tags of the person will be removed, i.e. adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without specifying any tags after it.
</box>

<box type="info">

**For attributes:**
* Updating an attribute requires you to specify both the attribute name and attribute value.
* If the attribute name already exists for this person, the corresponding attribute value will be updated. Otherwise, the attribute name and attribute value will be added to this person as a new attribute.
* Removing the attribute only requires you to specify the attribute name.
  * This must be an existing attribute.
  * You cannot remove an attribute that will be updated in the same command. For example, `edit 1 a/Major=Physics ra/Major` is not allowed.
</box>

<box type="info">

**Filters:**
* After editing a person, they may no longer pass the existing filtering criteria. Hence, to ensure the person is still shown in the UI, filters are cleared after a person is edited.

</box>

### Linking an attribute name to a website: `link`

Associates an attribute name with a website URL. This means that you can visit the website by clicking on the attribute in the person's detailed view (which can be shown by clicking on them or by using the [`show`](#showing-a-persons-details-show) command).

Format (to add a link): `link a/ATTRIBUTE_NAME=SITE_LINK`

* Associates `ATTRIBUTE_NAME` with `SITE_LINK`. This change applies to all persons that have an attribute with this name. Attributes with this name that are added in the future will also be linked to this site URL.

Format (to remove an existing link): `link ra/ATTRIBUTE_NAME`

* Removes the association between `ATTRIBUTE_NAME` and the website it was associated with.

Examples:
* `link a/github=https://github.com/`
  * You can now visit `https://github.com/ATTRIBUTE_VALUE` by clicking on the attribute.
  * For example, if a person has an attribute with the name `GitHub` and the value `pochitaro2025` (usually their username), you will be directed to `https://github.com/pochitaro2025`!
* `link ra/github`
  * This will delete the association between `github` and `https://github.com/`.
  * Clicking the attribute now will have no effect.

<box type="info">

Attribute names are case-insensitive. `link a/github=https://github.com/` has the same effect as `link a/GitHub=https://github.com/`.
</box>

<box type="tip">

**Tip:** Associations for `github` and `linkedin` (`https://github.com/` and `https://www.linkedin.com/in/`) are added by default!

Enjoy the easy management of websites!
</box>

<pic src="images/alexYeohDetails.png" alt="Alex Yeoh details">

Clicking on the attribute <span class="badge" style="background-color: #773963; text-decoration: underline; font-weight: normal; color: #b8e6ff">GitHub: example-alexyeoh</span> opens<br/>the link `https://github.com/example-alexyeoh`
</pic>

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD…`

* You must specify at least one keyword.
* You can specify more than one keyword.
* The search is case-insensitive. For example, `hans` will match `Hans`.
* The order of the keywords does not matter. For example, `Hans Bo` will match `Bo Hans`.
* Only the name is searched.
* Words containing the keyword as a substring will be matched. For example, `Han` will match `Hans`.
  * Note that the substring must be continuous. For example, `Has` will not match `Hans`.
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  For example, `Hans Bo` will match `Hans Gruber`, `Bo Yang`

Examples:
* `find John` matches any person with `John` as a substring in their name.
* `find alex david` matches any person with `alex` **OR** `david` as a substring in their name.<br>
  <pic src="images/findAlexDavidResult.png" alt="Results after typing `find alex david`">
  Results after typing `find alex david`
  </pic>

### Deleting a person: `delete`

Deletes the specified person.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the database.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries: `clear`

Clears all entries.

Format: `clear`

### Filtering candidates by attributes: `filter`

Filters the candidates based on whether they have a specific attribute or not.

Format: `filter a/ATTRIBUTE_NAME=ATTRIBUTE_VALUE…`
* You must specify at least one attribute.
* You can specify more than one attribute:
  1. If you specify multiple attributes of the **SAME** name, candidates who meet **ANY** one of them will be shown.
  1. If you specify multiple attributes of **DIFFERENT** names, candidates who meet **ALL** of them will be shown.
  1. You can specify attributes of different names, with multiple values of each. In this case, the 1st rule will be applied first, followed by the 2nd rule (see Examples).
  1. The order of input attributes does not matter (see Examples).
* `ATTRIBUTE_NAME` is matched case-insensitively, while `ATTRIBUTE_VALUE` is matched case-sensitively.

<box type="info">

Additional notes on warning messages:

* Attribute names are tolerant of typos. If no attribute is matched due to a minor typo, the app corrects it with a warning message.
  * For example, `GraduatOIn year` will be corrected to `GraduatIOn year` automatically, if only `Graduation year` exists as an attribute name.
* If there is no candidate having the specified attribute name/value, warning messages will also be shown.
  * Note that the check is done independently between name and value. For example, if there are exactly two attributes `Major=Engineering` and `Graduation year=2027`, no warning will be shown by an input `Major=2027`.

</box>

Examples:
| Command | Filtered condition |
| ------- | ------- |
| `filter a/Major=Computer Science a/Graduation year=2027` | major in Computer Science **AND** will graduate in 2027 |
| `filter a/Major=Computer Science a/Major=Mathematics` | major in Computer Science **OR** Mathematics. |
| `filter a/Major=Computer Science a/Major=Mathematics a/Graduation year=2027` | major in either Computer Science **OR** Mathematics, **AND** also graduating in 2027 | 

For the last command example,

| Major | Graduation Year | Matched? | Reason (if not matched) |
| ------- |-----------| ------- | ------- |
| Computer Science | 2027 | Yes | |
| Mathematics | 2027 | Yes | |
| Engineering | 2027 | No | do not meet the first condition |
| Computer Science | 2026 | No | do not meet the second condition |
| (no info) | 2027 | No | missing (= do not meet) the first condition |
| Computer Science | (no info) | No | missing (= do not meet) the second condition |

You can also obtain the same result with `filter a/Major=Computer Science a/Graduation year=2027 a/Major=Mathematics`, because the order of the arguments does not matter.

<pic src="images/filterMixedResult.png" alt="Filter results">

Results after typing `filter a/Major=Computer Science`<br/>`a/Major=Mathematics a/Graduation year=2027`
</pic>

### Sorting entries by an attribute: `sort`

Sorts the current view of entries by the value of the specified attribute name in alphabetically ascending or descending order.

Format: `sort a/ATTRIBUTE_NAME [o/ORDER]`

* Entries without the specified attribute will be placed at the back while preserving their relative order.
  * In this case, a warning will indicate the last entry in the current view that contains the specified attribute name, if any; otherwise, it will display a warning that the specified attribute is missing.
* `ATTRIBUTE_NAME` is matched case-insensitively. For instance, a command `sort a/graduation year` can sort all entries that have an attribute with name `Graduation Year`.
* If no `ORDER` is specified, entries will be sorted in ascending order by default. Otherwise, the first character of the user input (case-insensitive) will determine the order: if it starts with 'a', entries will be sorted in ascending order; if it starts with 'd', they will be sorted in descending order.  
* Attribute names are tolerant of typos. If no attribute with the specified attribute name is found due to a minor typo, the app corrects it with a warning message.

Example:
* `sort a/major` sorts entries in ascending order according to their `Major` alphabetically.
  * Entries without `Major` as an attribute will be sorted to the end of the list.
* `sort a/location o/desc` sorts entries in descending order according to their `Location` alphabetically.

### Sorting entries by the numerical value of an attribute: `sort-num`

Sorts the current view of entries by the numeric value of the specified attribute name in ascending or descending order.

Format: `sort-num a/ATTRIBUTE_NAME [o/ORDER]`

* For each attribute value which can be parsed into a number, its numerical value will be stored.
* Entries without the specified attribute will be placed at the back while preserving their relative order.
  * In this case, a warning will indicate the last entry in the current view that has the specified attribute, if any; otherwise, it will display a warning that the specified attribute is missing.
* If there is at least one entry with the specified attribute name and some of these entries lack a valid numerical value, those entries will be placed at the back while preserving their internal order.
  * In this case, a warning will indicate the last entry in the current view that contains a valid numerical value, if any; otherwise, it will display a warning that numerical values are completely missing.
* `ATTRIBUTE_NAME` is matched case-insensitively. For instance, a command `sort a/graduation year` can sort all entries that have an attribute with name `Graduation Year`.
* If no `ORDER` is specified, entries will be sorted in ascending order by default. Otherwise, the first character of the user input (case-insensitive) will determine the order: if it starts with 'a', entries will be sorted in ascending order; if it starts with 'd', they will be sorted in descending order.
* Attribute names are tolerant of typos. If no attribute with the specified attribute name is found due to a minor typo, the app corrects it with a warning message.

Example:
* `sort-num a/expected salary` sorts entries numerically (in ascending order) according to their `Expected Salary`.
  * Entries without a numerical value for `Expected Salary` will be placed at the end of the list, with those having `Expected Salary` as an attribute but lacking a valid numerical value appearing first.
* `sort-num a/GPA o/desc` sorts entries numerically (in descending order) according to their `GPA`.

### Undoing the last data change: `undo`

Undoes the last data change. Also clears any existing filters applied.

For instance, suppose a user intended to type `delete 5` but accidentally types and executes `delete 4`. They can just type `undo` to restore the deleted entry.

Note that commands that do not change the underlying data will be skipped -- such as `filter`, `find` and `show`.

Format: `undo`
* Only changes since the app was opened can be undone.
* Changes that are undone cannot be redone. However, you can simply re-execute the command. Previous commands can be retrieved by navigating past commands using <kbd>↑</kbd> <kbd>↓</kbd> key-presses.
* `undo` can be used multiple times in succession to undo more changes.
* Clears all applied filters.
* Does not work after exiting and re-opening the app. Using `undo` on a freshly opened app will not change anything.

Examples:
* `delete 4`, then `undo` will get the result: `Last data change command undone: delete 4`
* `sort-num a/Graduation Year`, then `undo` will get the result: `Last data change command undone: sort-num a/Graduation Year`
* Assuming the app was just opened, the following actions will get the result `There is no change to undo!`. Note that existing filters will not be cleared, as the command was not successful:
  * `show 3` then `undo`
  * `find n/Alex` then `undo`
  * `filter a/Graduation Year=2025` then `undo`
* `delete 4`, then `filter a/Graduation Year=2025`, then `undo` will skip over the filtering. It will undo `delete 4` and clear the filter

### Navigating past commands: <kbd>↑</kbd> <kbd>↓</kbd>

Navigates through command history, replacing the text in the command box with the past executed command.

Format: Press the up arrow (<kbd>↑</kbd>) or down arrow (<kbd>↓</kbd>) key while the command box is selected.
* The up arrow (<kbd>↑</kbd>) key shows the previous executed command while the down arrow (<kbd>↓</kbd>) key shows the next executed command.
* `ENTER` is not required to be pressed for navigation.
* No error message will be shown if attempting to navigate beyond the first and last executed commands.
* If attempting to navigate beyond the first executed command, the first executed command will remain shown.
* If attempting to navigate beyond the last executed command, the command box will be emptied.
* Executing any valid command will reset the last executed command to the command that was just executed.
* Editing the command shown without execution will not change the previous and next executed commands.
* After editing the command shown, navigating to previous or next executed commands will discard any edits done (these edits will not be maintained when navigating back).
* Navigating past commands requires the command box to be in focus.

### Exiting the program: `exit`

Exits the program.

Format: `exit`

### Saving the data

TalentFolio data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TalentFolio data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning">

**Caution:**
If your changes to the data file makes its format invalid, TalentFolio will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause TalentFolio to behave in unexpected ways (for example, if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

<box type="info">

Here are some tips on website linking. You can manually modify the correspondence between attribute names and site links by updating the `urlSettings` section in `addressbook.json`.

* Unlike the usual `link` command, you can leave the site link empty (i.e., `""`). This allows you to copy the raw attribute value!

* If you add multiple linkings with the same attribute name, the site link appearing the first will be used–note that attribute names are case-insensitive.

* Be careful: if the attribute name is empty of invalid (i.e., containing `/`, `\`, or `=`), the app will clear all data!

</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TalentFolio home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **If using Linux or Unix**, clicking on links will not open them. Instead, the link will be copied to the system clipboard, and a window will appear to notify you that the link has been copied. You will have to paste the link into your browser manually.
4. **If names, phone numbers, emails, tags or attributes are too long** the data may not display correctly and may be truncated with "...".

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Help**   | `help`
**Show**   | `show INDEX` <br> Example: `show 1`
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL [t/TAG]… [a/ATTRIBUTE_NAME=ATTRIBUTE_VALUE]…` <br> Example: `add n/James Ho p/22224444 e/jamesho@example.com t/C++ t/Java a/Major=Data Science and Analytics`
**List**   | `list`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG]… [a/ATTRIBUTE_NAME=ATTRIBUTE_VALUE]… [ra/ATTRIBUTE_NAME]…` <br> Example: `edit 2 n/James Lee e/jameslee@example.com`
**Link**   | `link a/ATTRIBUTE_NAME=SITE_LINK`
**Find**   | `find KEYWORD…`<br> Example: `find James Jake`
**Delete** | `delete INDEX`<br> Example: `delete 3`
**Clear**  | `clear`
**Filter** | `filter a/ATTRIBUTE_NAME=ATTRIBUTE_VALUE…` <br> Example: `filter a/Major=Computer Science`
**Sort** | `sort a/ATTRIBUTE_NAME [o/ORDER]`<br> Example: `sort a/Degree o/ascending`
**Numerical Sort** | `sort-num a/ATTRIBUTE_NAME [o/ORDER]`<br> Example: `sort-num a/GPA o/descending`
**Undo**   | `undo`
**Navigate Past Commands** | <kbd>↑</kbd> <kbd>↓</kbd>
**Exit**   | `exit`
