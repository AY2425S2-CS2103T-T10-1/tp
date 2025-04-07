package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.attribute.Attribute.CAPITALISATION_NOTE;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.attribute.AutoCorrectionUtil;
import seedu.address.model.person.AttributeMatchesPredicate;

/**
 * Filter the candidates with the provided attribute.
 * Guarantees: immutable.
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filter the candidates with the provided attribute.\n"
            + "Parameters: ATTRIBUTE_NAME=ATTRIBUTE_VALUE (case-sensitive, can be more than one)\n"
            + "Example: " + COMMAND_WORD + " a/major=Computer Science";
    public static final String MESSAGE_WARNING_DUPLICATE =
        "WARNING! There are duplicate attributes (i.e. name-value pairs). " + CAPITALISATION_NOTE;

    private final Set<Attribute> attributes;
    private final boolean wasDuplicate;

    /**
     * Initializes an instance with the predicate comprised of attributes.
     *
     * @param filter The filtering predicate created with the user input attributes.
     * @param wasDuplicate Indicates whether there was a duplicate attribute in the user input.
     */
    public FilterCommand(Set<Attribute> attributes, boolean wasDuplicate) {
        requireNonNull(attributes);
        this.attributes = attributes;
        this.wasDuplicate = wasDuplicate;
    }

    // Design has changed: we will autocorrect the attribute name, but no longer the attribute value.
    private void applyFilter(Model model) {
        Set<Attribute> adjustedAttribtues =
            attributes.stream()
                .map(attribute -> new Attribute(
                    model.autocorrectAttributeName(attribute.getAttributeName())
                        .orElse(attribute.getAttributeName()), attribute.getAttributeValue()))
                            .collect(Collectors.toSet());

        AttributeMatchesPredicate filter = new AttributeMatchesPredicate(adjustedAttribtues);

        model.updateFilteredPersonList(filter);
    }

    private String getWarningsForName(Model model) {
        return attributes.stream()
            .map(attribute -> attribute.getAttributeName())
            .map(name -> AutoCorrectionUtil.getWarningForName(name, model.autocorrectAttributeName(name)))
            .filter(warning -> warning.isPresent())
            .map(warning -> warning.get())
            .reduce("", (x, y) -> x + y + "\n");
    }

    // Design has changed: we will autocorrect the attribute name, but no longer the attribute value.
    private String getWarningsForValue(Model model) {
        return attributes.stream()
            .map(attribute -> attribute.getAttributeValue())
            .map(value -> AutoCorrectionUtil.getWarningForValue(value,
                // Now the corrected attribute value has to exactly match the original attrbute value.
                model.autocorrectAttributeValue(value).filter(correctedValue -> correctedValue.equals(value))))
            .filter(warning -> warning.isPresent())
            .map(warning -> warning.get())
            .reduce("", (x, y) -> x + y + "\n");
    }

    private String generateMessage(Model model) {
        String message = "";
        if (wasDuplicate) {
            message += MESSAGE_WARNING_DUPLICATE + "\n";
        }
        message += getWarningsForName(model);
        message += getWarningsForValue(model);
        message += String.format(Messages.MESSAGE_PERSONS_FILTERED_OVERVIEW, model.getFilteredPersonList().size());
        return message;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        applyFilter(model);

        String message = generateMessage(model);
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof FilterCommand otherFilterCommand) {
            return otherFilterCommand.attributes.equals(this.attributes)
                && otherFilterCommand.wasDuplicate == this.wasDuplicate;
        }
        return false;
    }
}
