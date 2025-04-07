package seedu.address.model.attribute;

import java.util.Optional;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * Provides utility methods related to the auto-correction of attribute names/values.
 */
public class AutoCorrectionUtil {
    public static final String MESSAGE_WARNING_NAME_CORRECTION =
        "WARNING! The input attribute name '%1$s' is auto-corrected to '%2$s'. "
            + "Note the case-insensitivitity.";
    public static final String MESSAGE_WARNING_VALUE_CORRECTION =
        "WARNING! The input attribute value '%1$s' is auto-corrected to '%2$s'.";
    public static final String MESSAGE_WARNING_NAME_NOT_EXIST =
        "WARNING! The input attribute name '%1$s' does not appear in any candidate's attributes.";
    public static final String MESSAGE_WARNING_VALUE_NOT_EXIST =
        "WARNING! The input attribute value '%1$s' does not appear in any candidate's attributes. "
            + "Note the case-sensitivity.";

    // Verified on LeetCode: https://leetcode.com/problems/edit-distance/submissions/1583973463.
    private static int editDistance(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[][] dp = new int[n + 1][m + 1];
        // dp[i][j] holds the edit distance between s[1:i] and t[1:j].
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = i + j;
                if (0 < i && dp[i - 1][j] + 1 < dp[i][j]) {
                    dp[i][j] = dp[i - 1][j] + 1;
                }
                if (0 < j && dp[i][j - 1] + 1 < dp[i][j]) {
                    dp[i][j] = dp[i][j - 1] + 1;
                }
                if (0 < i && 0 < j) {
                    int updateValue = dp[i - 1][j - 1];
                    if (s.charAt(i - 1) != t.charAt(j - 1)) {
                        updateValue++;
                    }
                    if (updateValue < dp[i][j]) {
                        dp[i][j] = updateValue;
                    }
                }
            }
        }
        return dp[n][m];
    }

    private static Optional<String> autocorrectName(Stream<String> names, String target) {
        Optional<String> closestName =
                names.reduce((name1, name2) -> (
                    editDistance(name1, target) < editDistance(name2, target) ? name1 : name2));
        // Set the cutoff for 'close enough' as 'no more than half of the characters need to be changed.'
        closestName = closestName.filter(name -> editDistance(name, target) * 2 < target.length());
        return closestName;
    }

    private static Stream<Attribute> getAttributesFromPersons(ObservableList<Person> persons) {
        return persons.stream().flatMap(person -> person.getAttributes().stream());
    }

    /**
     * Returns the closest matching existing attribute name, given a {@code target} string.
     * An empty {@code Optional} will be returned if there is no name close enough.
     */
    public static Optional<String> autocorrectAttributeName(
        ObservableList<Person> persons, String target) {
        String adjustedTarget = target.toLowerCase();
        Stream<String> attributeNames = getAttributesFromPersons(persons).map(Attribute::getAttributeName);
        return autocorrectName(attributeNames, adjustedTarget);
    }

    /**
     * Returns the closest matching existing attribute value, given a {@code target} string.
     * An empty {@code Optional} will be returned if there is no value close enough.
     */
    public static Optional<String> autocorrectAttributeValue(
        ObservableList<Person> persons, String target) {
        Stream<String> attributeValues = getAttributesFromPersons(persons).map(Attribute::getAttributeValue);
        return autocorrectName(attributeValues, target);
    }

    /**
     * Returns a warning message related to the auto-correction of an attribute name,
     * mapping from {@code originalName} to {@code correctedName}. If {@code correctedName} is Empty,
     * it implies that {@code originalName} does not appear in any {@code Person}'s attributes.
     *
     * The warning message will be about either:
     * - the absense of {@code originalName} from any {@code Person}'s attribute.
     * - the auto-correction itself.
     * An empty {@code Optional} will be returned if there is no need for the warning message,
     * i.e., no correction took place and {@code originalName} appears in some {@code Person}'s attributes.
     */
    public static Optional<String> getWarningForName(String originalName, Optional<String> correctedName) {
        if (correctedName.isPresent()) {
            if (originalName.toLowerCase().equals(correctedName.get().toLowerCase())) {
                return Optional.empty();
            } else {
                return Optional.of(
                    String.format(MESSAGE_WARNING_NAME_CORRECTION, originalName, correctedName.get()));
            }
        } else {
            return Optional.of(
                String.format(MESSAGE_WARNING_NAME_NOT_EXIST, originalName));
        }
    }

    /**
     * Returns a warning message related to the auto-correction of an attribute value,
     * mapping from {@code originalValue} to {@code correctedValue}. If {@code correctedValue} is Empty,
     * it implies that {@code originalValue} does not appear in any {@code Person}'s attributes.
     *
     * The warning message will be about either:
     * - the absense of {@code originalValue} from any {@code Person}'s attribute.
     * - the auto-correction itself.
     * An empty {@code Optional} will be returned if there is no need for the warning message,
     * i.e., no correction took place and {@code originalValue} appears in some {@code Person}'s attributes.
     */
    public static Optional<String> getWarningForValue(String originalValue, Optional<String> correctedValue) {
        if (correctedValue.isPresent()) {
            if (originalValue.equals(correctedValue.get())) {
                return Optional.empty();
            } else {
                return Optional.of(
                    String.format(MESSAGE_WARNING_VALUE_CORRECTION, originalValue, correctedValue.get()));
            }
        } else {
            return Optional.of(
                String.format(MESSAGE_WARNING_VALUE_NOT_EXIST, originalValue));
        }
    }
}
