package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG,
                        PREFIX_REMOVE_TAG, PREFIX_ATTRIBUTE, PREFIX_REMOVE_ATTRIBUTE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        parseTagsForRemoval(argMultimap.getAllValues(PREFIX_REMOVE_TAG)).ifPresent(editPersonDescriptor::setRemoveTags);

        parseAttributesForEdit(argMultimap.getAllValues(PREFIX_ATTRIBUTE))
                .ifPresent(editPersonDescriptor::setUpdateAttributes);

        parseAttributesForRemoval(argMultimap.getAllValues(PREFIX_REMOVE_ATTRIBUTE))
                .ifPresent(editPersonDescriptor::setRemoveAttributes);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseTags(tags));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     */
    private Optional<Set<Tag>> parseTagsForRemoval(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseTags(tags));
    }

    /**
     * Parses {@code Collection<String> atttibutes} into a {@code Set<Attribute} if {@code attributes} is
     * non-empty.
     */
    private Optional<Set<Attribute>> parseAttributesForEdit(Collection<String> attributes) throws ParseException {
        assert attributes != null;

        if (attributes.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> attributeSet = new HashSet<>(attributes);
        return Optional.of(ParserUtil.parseAttributes(attributeSet, false));
    }

    /**
     * Parses {@code Collection<String> attributes} into a {@code Set<String>} if {@code attributes} is
     * non-empty.
     */
    private Optional<Set<String>> parseAttributesForRemoval(Collection<String> attributes) throws ParseException {
        assert attributes != null;

        if (attributes.stream().anyMatch(String::isEmpty)) {
            throw new ParseException(ParserUtil.MESSAGE_MISSING_ARGUMENT_FOR_ATTRIBUTE_NAME);
        }

        if (attributes.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> attributeSet = new HashSet<>(attributes);
        return Optional.of(ParserUtil.parseRemoveAttributes(attributeSet));
    }

}
