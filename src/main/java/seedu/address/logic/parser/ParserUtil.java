package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final String MESSAGE_TOO_MANY_ARGUMENT_FOR_ATTRIBUTE =
            Attribute.MESSAGE_USAGE + "\n" + "Also, " + Attribute.MESSAGE_CONSTRAINTS.toLowerCase();

    public static final String MESSAGE_MISSING_ARGUMENT_FOR_ATTRIBUTE =
            "Attribute names and values cannot be empty.";

    public static final String MESSAGE_MISSING_ARGUMENT_FOR_ATTRIBUTE_NAME = "Attribute names cannot be empty.";

    public static final String MESSAGE_MISSING_ARGUMENT_FOR_TAG =
            "Tag names cannot be empty.";

    public static final String MESSAGE_EMPTY_ARGUMENT_FOR_ATTRIBUTE =
            "You need to enter an attribute after " + PREFIX_ATTRIBUTE + ".\n"
                    + Attribute.MESSAGE_USAGE;

    private static final Logger logger = Logger.getLogger("ParserUtil");

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (trimmedTag.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_ARGUMENT_FOR_TAG);
        }
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    private static long countCharactors(String str, char target) {
        return str.chars().filter(c -> c == target).count();
    }

    /**
     * Checks if the given string, representing an Attribute, contains exactly one '=' in a proper position
     * (i.e., not at the beginning or end of the string).
     *
     * @param trimmedAttribute The raw string representation of Attribute.
     * @throws ParseException Thrown when the position or number of '=' is incorrect.
     */
    private static void checkCorrectnessForEquals(String trimmedAttribute) throws ParseException {
        long numOfEquals = countCharactors(trimmedAttribute, '=');

        if (numOfEquals == 0) {
            throw new ParseException(Attribute.MESSAGE_USAGE);
        } else if (numOfEquals >= 2) {
            throw new ParseException(MESSAGE_TOO_MANY_ARGUMENT_FOR_ATTRIBUTE);
        }

        assert !trimmedAttribute.isEmpty() : "The trimmed attribute should not be empty here.";

        if (trimmedAttribute.charAt(0) == '='
                || trimmedAttribute.charAt(trimmedAttribute.length() - 1) == '=') {
            throw new ParseException(MESSAGE_MISSING_ARGUMENT_FOR_ATTRIBUTE);
        }
    }

    /**
     * Parses a {@code String attribute} into an {@code Attribute}.
     * The attribute should be provided in the format {@code name=value}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code attribute} is invalid.
     */
    public static Attribute parseAttribute(String attribute) throws ParseException {
        requireNonNull(attribute);
        String trimmedAttribute = attribute.trim();

        if (trimmedAttribute.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_ARGUMENT_FOR_ATTRIBUTE);
        }

        checkCorrectnessForEquals(trimmedAttribute);

        Matcher matcher = Pattern.compile("^([^=]+)=([^=]+)$").matcher(trimmedAttribute);
        if (!matcher.find()) {
            throw new ParseException(Attribute.MESSAGE_CONSTRAINTS);
        }

        String attributeName = matcher.group(1).trim();
        String attributeValue = matcher.group(2).trim();

        assert !attributeName.isEmpty() : "Attribute name should not be empty here.";
        assert !attributeValue.isEmpty() : "Attribute value should not be empty here.";

        if (!Attribute.isValidAttribute(attributeName) || !Attribute.isValidAttribute(attributeValue)) {
            throw new ParseException(Attribute.MESSAGE_CONSTRAINTS);
        }

        return new Attribute(attributeName, attributeValue);
    }

    /**
     * Parses {@code Collection<String> attributes} into a {@code Set<Attribute>}.
     */
    public static Set<Attribute> parseAttributes(Collection<String> attributes,
            boolean isDuplicateAllowed) throws ParseException {
        requireNonNull(attributes);
        final Set<Attribute> attributeSet = new HashSet<>();
        final Set<String> attributeNames = new HashSet<>();
        final Set<String> caseAwareAttributeNames = new HashSet<>();

        for (String attribute : attributes) {
            Attribute newAttribute = parseAttribute(attribute);
            attributeSet.add(newAttribute);
            attributeNames.add(newAttribute.getAttributeName());
            caseAwareAttributeNames.add(newAttribute.getCaseAwareAttributeName());
        }

        if (!isDuplicateAllowed && attributeNames.size() != caseAwareAttributeNames.size()) {
            throw new ParseException(Attribute.NO_DUPLICATES_CASE_INSENSITIVITY);
        }

        if (!isDuplicateAllowed && attributeSet.size() != attributeNames.size()) {
            throw new ParseException(Attribute.NO_DUPLICATES);
        }

        return attributeSet;
    }

    /**
     * Returns the numerical value represented by the given string.
     * If the string cannot be parsed as a double, an empty Optional is returned.
     *
     * @param value String to be parsed into a Double.
     * @return Optional containing the parsed Double value, or empty if parsing fails.
     */
    public static Optional<Double> parseStringValueToNumericalValue(String value) {
        requireNonNull(value);

        try {
            logger.log(Level.INFO, "going to start parsing a string to a number");
            Optional<Double> numericalValue = Optional.of(Double.parseDouble(value));
            logger.log(Level.INFO, "going to start parsing a string to a number");
            return numericalValue;
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Parses {@code Collection<String> attributes} into a {@code Set<String>}.
     */
    public static Set<String> parseRemoveAttributes(Collection<String> attributes) throws ParseException {
        requireNonNull(attributes);
        final Set<String> attributeNames = new HashSet<>();
        for (String attribute : attributes) {
            attributeNames.add(attribute.toLowerCase());
        }
        return attributeNames;
    }
}
