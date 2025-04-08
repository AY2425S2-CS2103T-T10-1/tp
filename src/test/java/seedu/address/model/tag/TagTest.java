package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names
        assertFalse(Tag.isValidTagName("//////////////")); // contains /
        assertFalse(Tag.isValidTagName("\\\\\\\\\\\\\\\\\\\\")); // contains \
        assertFalse(Tag.isValidTagName("veryvery ve+ry ve-ry VERY vErY v e r y v_er_y long tagname")); // long tag name

        // valid tag names
        assertTrue(Tag.isValidTagName("alphabetsletters")); // alphabetic only
        assertTrue(Tag.isValidTagName("1234567890")); // numeric only
        assertTrue(Tag.isValidTagName("alphabetsAND12345")); // alphanumeric
        assertTrue(Tag.isValidTagName("a1!@#$%^&*(),.;'[]`~_-+=|{}<>")); // funny characters
        assertTrue(Tag.isValidTagName("this tag name has some spaces")); // spaces
        assertTrue(Tag.isValidTagName("50characterslongexactlytagnameMORELETTERSNEEDED!!!")); // 50 character
    }

}
