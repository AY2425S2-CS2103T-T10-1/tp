package seedu.address.ui;

import java.util.Comparator;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.attribute.Attribute;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane attributes;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        setLabelIcon(phone, "/images/telephone-receiver.png");
        phone.setText(person.getPhone().value);
        setLabelIcon(email, "/images/email.png");
        email.setText(person.getEmail().value);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getAttributes().stream()
                .sorted()
                .forEach(attribute -> attributes.getChildren().add(createAttributeLabel(attribute)));
    }

    private static Label createAttributeLabel(Attribute attribute) {
        Label label = new Label(attribute.getDisplayText());
        if (attribute.hasSiteLink()) {
            label.setId("site-attribute-list");
        }
        return label;
    }

    /**
     * Sets the {@code Label}'s icon as the image represented in the provided {@code path}.
     */
    private void setLabelIcon(Label label, String path) {
        ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))));
        icon.setFitHeight(16);
        icon.setFitWidth(16);
        icon.setPreserveRatio(true);
        label.setGraphic(icon);
    }
}
