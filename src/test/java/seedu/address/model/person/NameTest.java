package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("R!chel")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("_Le_epic_man_")); // starts with special character
        assertFalse(Name.isValidName("A very very super duper ultra mega epic long goofy ahh name")); // very long name
        assertFalse(Name.isValidName("Now this name is exactly 51 characters surprisngly.")); // exactly 51 characters
        assertFalse(Name.isValidName("Müller")); // diacritic example 1
        assertFalse(Name.isValidName("François")); // diacritic example 2
        assertFalse(Name.isValidName("Катюша")); // cyrillic alphabet
        assertFalse(Name.isValidName("καλλίστη")); // greek alphabet
        assertFalse(Name.isValidName("孫正義")); // chinese/japanese/korean alphabet

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr the 2nd Ah Long, Esq.")); // valid long name
        assertTrue(Name.isValidName("A name that is exactly 50 characters surprisingly.")); // exactly 50 characters
        assertTrue(Name.isValidName("Nagaratnam s/o Suppiah")); // names with s/o
        assertTrue(Name.isValidName("Nagaratnam d/o Suppiah")); // names with d/o
        assertTrue(Name.isValidName("Tan Cheng Bok @ Adrian Tan")); // names with @
        assertTrue(Name.isValidName("Tan Ah-Kau")); // names with -
        assertTrue(Name.isValidName("Firstname Lastname Dialect Name (Chinese Name)")); // names with ()
        assertTrue(Name.isValidName("O'Reilly")); // names with '
        assertTrue(Name.isValidName("Macbeth, Esq")); // names with ,
        assertTrue(Name.isValidName("Macbeth Esq.")); // names with .
        assertTrue(Name.isValidName("A_name_that_probably_doesnt_exist")); // names with _
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // different case of same name -> returns true
        assertTrue(name.equals(new Name("valid name")));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
