package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "New values will be added with the specified input values.\n"
            + "Removed attributes do not require a value to be specified.\n"
            + "Addition and removal of attributed will be executed in the order they appear in the command.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_REMOVE_TAG + "TAG]...\n"
            + "[" + PREFIX_ATTRIBUTE + "ATTRIBUTE_NAME=ATTRIBUTE_VALUE]... "
            + "[" + PREFIX_REMOVE_ATTRIBUTE + "ATTRIBUTE_NAME]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one non-empty field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_ATTRIBUTE_DOES_NOT_EXIST = "This person does not have the attribute: [%1$s]";
    public static final String MESSAGE_ATTRIBUTE_REMOVED_IMMEDIATELY =
            "This attribute is being added/edited and removed in the same command: [%1$s]";
    public static final String MESSAGE_TAG_DOES_NOT_EXIST = "This person does not have the tag: %1$s";
    public static final String MESSAGE_TAG_REMOVED_IMMEDIATELY =
            "This tag is being added/edited and removed in the same command: %1$s";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor)
            throws CommandException {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(null);
        Set<Tag> removedTags = editPersonDescriptor.getRemoveTags().orElse(null);

        // relies on Attribute being immutable
        Set<Attribute> prevAttributes = personToEdit.getAttributes();

        if (removedTags != null) {
            if (updatedTags != null) {
                Set<Tag> intersection = new HashSet<>(updatedTags);
                intersection.retainAll(removedTags);
                if (!intersection.isEmpty()) {
                    throw new CommandException(String.format(MESSAGE_TAG_REMOVED_IMMEDIATELY,
                            intersection.iterator().next()));
                }
            }

            Set<Tag> tagsToRemove = new HashSet<>(removedTags);
            tagsToRemove.removeAll(personToEdit.getTags());
            if (!tagsToRemove.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_TAG_DOES_NOT_EXIST, tagsToRemove.iterator().next()));
            }
        }

        Person personToReturn = Person.of(updatedName, updatedPhone, updatedEmail, personToEdit.getTags(),
                updatedTags, removedTags, prevAttributes);

        Set<Attribute> updatedAttributes = editPersonDescriptor.getUpdateAttributes().orElse(null);
        Set<String> removedAttributes = editPersonDescriptor.getRemoveAttributes().orElse(null);

        if (removedAttributes != null) {
            for (String attrName : removedAttributes) {
                boolean existsInUpdated = updatedAttributes == null ? false : updatedAttributes.stream()
                        .anyMatch(attr -> attr.getAttributeName().equals(attrName));

                if (existsInUpdated) {
                    throw new CommandException(String.format(MESSAGE_ATTRIBUTE_REMOVED_IMMEDIATELY, attrName));
                }

                boolean existsInPrev = prevAttributes == null ? false : prevAttributes.stream()
                        .anyMatch(attr -> attr.getAttributeName().equals(attrName));

                if (!existsInPrev) {
                    throw new CommandException(String.format(MESSAGE_ATTRIBUTE_DOES_NOT_EXIST, attrName));
                }
            }
        }

        if (updatedAttributes != null) {
            for (Attribute attr: updatedAttributes) {
                personToReturn.updateAttribute(attr);
            }
        }
        if (removedAttributes != null) {
            for (String attributeName: removedAttributes) {
                personToReturn.removeAttributeByName(attributeName);
            }
        }

        return personToReturn;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (other instanceof EditCommand otherEditCommand) {
            return index.equals(otherEditCommand.index)
                    && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
        }

        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Set<Tag> tags;
        private Set<Tag> removeTags;
        private Set<Attribute> updateAttributes;
        private Set<String> removeAttributes;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setTags(toCopy.tags);
            setRemoveTags(toCopy.removeTags);
            setUpdateAttributes(toCopy.updateAttributes);
            setRemoveAttributes(toCopy.removeAttributes);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, tags,
                    removeTags, updateAttributes, removeAttributes);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code updateAttributes} to this object's {@code updateAttributes}.
         * A defensive copy of {@code updateAttributes} is used internally.
         */
        public void setUpdateAttributes(Set<Attribute> updateAttributes) {
            this.updateAttributes = (updateAttributes != null) ? new HashSet<>(updateAttributes) : null;
        }

        public void setRemoveTags(Set<Tag> removeTags) {
            this.removeTags = (removeTags != null) ? new HashSet<>(removeTags) : null;
        }

        public Optional<Set<Tag>> getRemoveTags() {
            return (removeTags != null) ? Optional.of(Collections.unmodifiableSet(removeTags)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable attribute set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code updateAttributes} is null.
         */
        public Optional<Set<Attribute>> getUpdateAttributes() {
            return (updateAttributes != null)
                ? Optional.of(Collections.unmodifiableSet(updateAttributes)) : Optional.empty();
        }

        /**
         * Sets {@code removeAttributes} to this object's {@code removeAttributes}.
         * A defensive copy of {@code removeAttributes} is used internally.
         */
        public void setRemoveAttributes(Set<String> removeAttributes) {
            this.removeAttributes = (removeAttributes != null) ? new HashSet<>(removeAttributes) : null;
        }

        /**
         * Returns an unmodifiable string set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code removeAttributes} is null.
         */
        public Optional<Set<String>> getRemoveAttributes() {
            return (removeAttributes != null)
                ? Optional.of(Collections.unmodifiableSet(removeAttributes)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (other instanceof EditPersonDescriptor otherEditPersonDescriptor) {
                return Objects.equals(name, otherEditPersonDescriptor.name)
                        && Objects.equals(phone, otherEditPersonDescriptor.phone)
                        && Objects.equals(email, otherEditPersonDescriptor.email)
                        && Objects.equals(tags, otherEditPersonDescriptor.tags)
                        && Objects.equals(updateAttributes, otherEditPersonDescriptor.updateAttributes)
                        && Objects.equals(removeAttributes, otherEditPersonDescriptor.removeAttributes);
            }

            return false;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("tags", tags)
                    .add("updateAttributes", updateAttributes)
                    .add("removeAttributes", removeAttributes)
                    .toString();
        }
    }
}
