package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.Person;

/**
 * Removes an attribute from a person in the address book.
 */
public class AttributeRemoveCommand extends Command {

    public static final String COMMAND_WORD = "remove-attribute";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the attribute of the person identified by name.\n"
            + "Parameters: [person index] a/ [ATTRIBUTE_NAME]\n"
            + "Example: " + COMMAND_WORD + "n/ Alex Yeoh a/graduation year";

    private final Index index;
    private final List<String> attributeNames;

    /**
     * Constructs a {@code AttributeRemoveCommand}
     *
     * @param index name of the person to be edited
     * @param attributeNames List of names of the attributes to be deleted
     */
    public AttributeRemoveCommand(Index index, List<String> attributeNames) {
        requireAllNonNull(index, attributeNames);
        assert attributeNames.size() > 0;

        this.index = index;
        this.attributeNames = attributeNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        List<Attribute> attributesToRemove = new ArrayList<>();
        for (String attributeName : attributeNames) {
            Attribute matchingAttribute = personToEdit.getAttribute(attributeName).orElseThrow(()
                    -> new CommandException(String.format("%s has no matching attribute with name %s",
                    personToEdit.getName(), attributeName))
            );
            attributesToRemove.add(matchingAttribute);
        }
        for (Attribute attribute : attributesToRemove) {
            personToEdit.removeAttribute(attribute);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(personToEdit.getName() + "'s attributes were deleted successfully.");
    }
}
