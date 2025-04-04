package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;

import java.util.Optional;

import seedu.address.model.Model;
import seedu.address.model.attribute.NumericalValueBasedAttributeComparator;
import seedu.address.model.person.AttributeBasedPersonComparator;

/**
 * Sort the candidates with the provided attribute.
 * Guarantees: immutable.
 */
public class NumSortCommand extends SortCommand {
    public static final String COMMAND_WORD = "sort-num";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort the candidates in ascending order based on numerical value of the specified attribute.\n"
            + "Candidates lacking the specified attribute name will be placed last, preserving their original order.\n"
            + "Among Candidates with the specified attribute name, "
            + "those without a numerical value will be placed last.\n"
            + "Parameters: ATTRIBUTE_NAME (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ATTRIBUTE + "Graduation Year";
    public static final String MESSAGE_WARNING_PARTIALLY_MISSING_NUMERICALS =
            "WARNING! Only entries up to index %1$d have valid numerical values for the specified attribute name.\n";
    public static final String MESSAGE_WARNING_MISSING_NUMERICALS =
            "WARNING! No entries have valid numerical values for the specified attribute name.\n";


    /**
     * Initializes an instance with the comparator based on the attribute name.
     *
     * @param attributeName The name of the attribute to sort the user input.
     */
    public NumSortCommand(String attributeName, boolean isAscending) {
        super(attributeName, isAscending);
    }

    @Override
    public AttributeBasedPersonComparator getComparator(boolean isAscending) {
        return new AttributeBasedPersonComparator(this.adjustedAttributeName.orElse(this.attributeName),
                new NumericalValueBasedAttributeComparator(isAscending));
    }

    @Override
    public String getWarningMessage(Model model) {
        Optional<Long> count = model.numOfPersonsWithNumericalValue(this.adjustedAttributeName.orElse(attributeName));
        String warning = super.getWarningMessage(model);
        if (count.isPresent()) {
            if (count.get() == 0) {
                warning += MESSAGE_WARNING_MISSING_NUMERICALS;
            } else {
                warning += String.format(MESSAGE_WARNING_PARTIALLY_MISSING_NUMERICALS, count.get());
            }
        }
        return warning;
    }
}
