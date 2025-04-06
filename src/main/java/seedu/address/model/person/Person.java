package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Attribute> attributes = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Set<Tag> tags, Set<Attribute> attributes) {
        requireAllNonNull(name, phone, email, tags, attributes);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tags.addAll(tags);
        this.attributes.addAll(attributes);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Adds a tag.
     *
     * @param tag Tag to be updated or added
     */

    public void updateTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * Removes specified {@code Tag} object from list of tags in Person's list.
     *
     * @param tag Tag object to be removed
     * @return If the removal was successful
     */
    public boolean removeTag(Tag tag) {
        return tags.remove(tag);
    }

    /**
     * Returns an immutable attribute set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Attribute> getAttributes() {
        return Collections.unmodifiableSet(attributes);
    }

    /**
     * Finds an attribute by the attribute name. If it exists,
     * returns Optional object with matching Attribute. Else, returns empty Optional.
     *
     * @param attributeName attribute name to search for
     * @return Optional object with the matching attribute.
     */
    public Optional<Attribute> getAttribute(String attributeName) {
        for (Attribute attr: attributes) {
            if (attr.matchesName(attributeName)) {
                return Optional.of(attr);
            }
        }
        return Optional.empty();
    }

    /**
     * Updates an attribute if the attribute name exists, else adds the attribute.
     *
     * @param attribute Attribute to be updated or added
     */
    public void updateAttribute(Attribute attribute) {
        getAttribute(attribute.getAttributeName())
                .ifPresent(this::removeAttribute);
        attributes.add(attribute);
    }

    /**
     * Removes specified {@code Attribute} object from list of attributes in Person's list.
     *
     * @param attribute Attribute object to be removed
     * @return If the removal was successful
     */
    public boolean removeAttribute(Attribute attribute) {
        return attributes.remove(attribute);
    }

    /**
     * Removes attribute object from list of attributes in Person's list specified by attribute name
     * {@code attributeName}.
     *
     * @param attributeName Attribute name of attribute object to be removed
     * @return If the removal was successful
     */
    public boolean removeAttributeByName(String attributeName) {
        return getAttribute(attributeName)
                .map(this::removeAttribute)
                .orElse(false);
    }

    /**
     * Returns true if both persons have the same name when compared case-insensitively.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Checks if the person has no duplicates in attribute names.
     *
     * @return True if no duplicates, False otherwise.
     */
    public boolean hasNoDuplicateInAttributeNames() {
        return attributes.stream().map(attribute -> attribute.getAttributeName())
            .distinct().count() == attributes.size();
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (other instanceof Person otherPerson) {
            return name.equals(otherPerson.name)
                    && phone.equals(otherPerson.phone)
                    && email.equals(otherPerson.email)
                    && tags.equals(otherPerson.tags)
                    && attributes.equals(otherPerson.attributes);
        }

        return false;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, tags, attributes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("tags", tags)
                .add("attributes", attributes)
                .toString();
    }

}
