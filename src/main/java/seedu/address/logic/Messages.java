package seedu.address.logic;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.affiliation.Affiliation;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_ROLE = "Invalid role!";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Role: ")
                .append(person.getRole())
                .append("; Affiliations: {");

        ArrayList<Affiliation> affiliationsList = new ArrayList<>(person.getAffiliations());
        for (int i = 0; i < affiliationsList.size(); i++) {
            builder.append(affiliationsList.get(i));
            if (i < affiliationsList.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append("}");
        builder.append("; Affiliation History: {");

        ArrayList<Affiliation> affiliationHistoryList = new ArrayList<>(person.getAffiliationHistory());
        for (int i = 0; i < affiliationHistoryList.size(); i++) {
            builder.append(affiliationHistoryList.get(i));
            if (i < affiliationHistoryList.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append("}");

        if (person instanceof Doctor) {
            builder.append("; Shift Days: ");
            builder.append(((Doctor) person).getShiftDays());
        }
        return builder.toString();
    }

}
