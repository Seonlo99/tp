package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.affiliation.Affiliation;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class InformationWindow extends UiPart<Region> {

    private static final String FXML = "InformationWindow.fxml";

    @FXML
    private VBox fullWindow;
    @FXML
    private Label name;
    @FXML
    private Label role;
    @FXML
    private VBox shiftBlock;
    @FXML
    private Label shiftHeader;
    @FXML
    private Label shiftMon;
    @FXML
    private Label shiftTue;
    @FXML
    private Label shiftWed;
    @FXML
    private Label shiftThu;
    @FXML
    private Label shiftFri;
    @FXML
    private Label shiftSat;
    @FXML
    private Label shiftSun;
    private Label[] shiftDays;

    @FXML
    private VBox affnBlock;
    @FXML
    private Label affnCount;
    @FXML
    private VBox affnListBlock;

    /**
     * Initialises the {@code InformationWindow}.
     */
    public InformationWindow() {
        super(FXML);
        shiftDays = new Label[]{shiftMon, shiftTue, shiftWed, shiftThu, shiftFri, shiftSat, shiftSun};

        // The initialisation should not render the information window.
        resetWindow();
    }

    /**
     * Displays information of the given {@code Person}.
     * @param person the intended person for display.
     */
    @FXML
    public void displayInformation(Person person) {
        requireNonNull(person);

        name.setText(person.getName().fullName);
        role.setText(person.getRole().value);

        if (person instanceof Doctor) {
            displayDoctorInformation((Doctor) person);
        } else if (person instanceof Patient) {
            displayPatientInformation((Patient) person);
        }

        showWindow();
    }

    /**
     * Displays information of the given {@code Doctor}.
     * @param doctor the intended doctor for display.
     */
    private void displayDoctorInformation(Doctor doctor) {
        role.setStyle("-fx-background-color: #89CFF0; -fx-font-weight: bold; -fx-text-fill: #0047AB");
        setShiftDays(doctor);
        setAffiliations(doctor);
        affnCount.setText("Tending to:");
        affnBlock.setStyle("-fx-border-color: #BBBBBB; -fx-border-width: 2 0 0 0;");
    }

    /**
     * Displays information of the given {@code Patient}.
     * @param patient the intended patient for display.
     */
    private void displayPatientInformation(Patient patient) {
        role.setStyle("-fx-background-color: #E97451; -fx-font-weight: bold; -fx-text-fill: #8B4000");
        clearShiftDays();
        setAffiliations(patient);
        affnCount.setText("Attended by:");
        affnBlock.setStyle("-fx-border-color: #BBBBBB; -fx-border-width: 2 0 0 0;");
    }

    /**
     * Sets the shift days information of the given {@code Doctor}.
     * @param doctor the intended person for display.
     */
    private void setShiftDays(Doctor doctor) {
        clearShiftDays();

        shiftBlock.setVisible(true);
        shiftBlock.setManaged(true);
        shiftBlock.setStyle("-fx-border-color: #BBBBBB; -fx-border-width: 2 0 0 0;");
        shiftHeader.setText("Shift days:");

        for (int shiftDay : doctor.getShiftDays()) {
            shiftDays[shiftDay - 1]
                    .setStyle("-fx-background-color: #AFE1AF; -fx-font-weight: bold; -fx-text-fill: #008000");
        }
    }

    /**
     * Sets the list of affiliation names into the information window.
     */
    private void setAffiliations(Person person) {
        clearAffiliations();
        for (Affiliation affiliation : person.getAffiliations()) {
            String name = affiliation.toString();
            Label label = new Label("- " + name);
            label.getStyleClass().add("information-affn-list");
            affnListBlock.getChildren().add(label);
        }

        if (affnListBlock.getChildren().size() == 0) {
            Label label = new Label("[EMPTY]");
            label.getStyleClass().add("information-affn-list");
            affnListBlock.getChildren().add(label);
        }
    }

    /**
     * Removes all affiliation names from the information window.
     */
    private void clearAffiliations() {
        affnListBlock.getChildren().clear();
    }

    /**
     * Removes the shift days information from the information window.
     */
    private void clearShiftDays() {
        for (Label shiftDay : shiftDays) {
            shiftDay.setStyle("-fx-background-color: transparent; -fx-font-weight: normal; -fx-text-fill: black");;
        }
        shiftBlock.setVisible(false);
        shiftBlock.setManaged(false);
    }

    /**
     * Displays the whole window.
     */
    public void showWindow() {
        fullWindow.setVisible(true);
        fullWindow.setManaged(true);
        fullWindow.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    }

    /**
     * Clears the whole window.
     */
    public void resetWindow() {
        fullWindow.setVisible(false);
        fullWindow.setManaged(false);
        fullWindow.setMinSize(0, 0);
    }

}
