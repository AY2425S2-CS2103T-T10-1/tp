package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_ATTRIBUTE;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attribute.Attribute;

/**
 * Parses input arguments and creates a new AliasCommand object.
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    private void checkNameInput(String attributeName) throws ParseException {
        if (attributeName.isEmpty()) {
            throw new ParseException(Attribute.MESSAGE_CONSTRAINT_NON_EMPTY_FOR_NAME);
        }
        if (!Attribute.isValidAttribute(attributeName)) {
            throw new ParseException(Attribute.MESSAGE_CONSTRAINTS_FOR_NAME);
        }
    }

    private void checkLinkInput(String siteLink) throws ParseException {
        if (siteLink.isEmpty()) {
            throw new ParseException(Attribute.MESSAGE_CONSTRAINT_NON_EMPTY_FOR_SITE_LINK);
        }
    }

    private AliasCommand parseAdding(String arg) throws ParseException {
        requireNonNull(arg);
        arg = arg.trim();

        Matcher matcher = Pattern.compile("^([^=]+)=(.*)$").matcher(arg);

        if (!matcher.find()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }

        String attributeName = matcher.group(1).trim();
        String siteLink = matcher.group(2).trim();

        checkNameInput(attributeName);

        checkLinkInput(siteLink);

        return AliasCommand.addAliasCommand(attributeName, siteLink);
    }

    private AliasCommand parseRemoving(String arg) throws ParseException {
        requireNonNull(arg);
        String attributeName = arg.trim();

        checkNameInput(attributeName);

        return AliasCommand.removeAliasCommand(attributeName);
    }

    @Override
    public AliasCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ATTRIBUTE, PREFIX_REMOVE_ATTRIBUTE);

        List<String> addAliasStrings = argMultimap.getAllValues(PREFIX_ATTRIBUTE);
        List<String> removeAliasStrings = argMultimap.getAllValues(PREFIX_REMOVE_ATTRIBUTE);

        if (addAliasStrings.size() + removeAliasStrings.size() != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AliasCommand.MESSAGE_ACCEPT_ONE_ARGUMENT));
        }

        if (addAliasStrings.size() == 1) {
            return parseAdding(addAliasStrings.get(0));
        } else {
            assert removeAliasStrings.size() == 1 : "The number of arguments was intially verified to be 1, "
                + "but in the second check, this condition was violated!";
            return parseRemoving(removeAliasStrings.get(0));
        }
    }

}
