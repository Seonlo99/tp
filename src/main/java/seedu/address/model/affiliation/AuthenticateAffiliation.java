package seedu.address.model.affiliation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.affiliation.exceptions.AffiliationPersonNotFoundException;
import seedu.address.model.affiliation.exceptions.SamePersonAffiliationException;
import seedu.address.model.affiliation.exceptions.SameRoleAffiliationException;
import seedu.address.model.person.Person;

/**
 * AffiliationChecker checks if affiliation is valid.
 */
public class AuthenticateAffiliation {

    /**
     * Returns true if affiliation list pass the check, throws an exception otherwise.
     *
     * @param model The current model that is running.
     * @throws CommandException If affiliation list fails the check.
     */
    public static Boolean check(Set<Affiliation> affiliationSet, Person personAddingAffiliation, Model model)
            throws CommandException {
        requireAllNonNull(affiliationSet, personAddingAffiliation, model);

        if (affiliationSet.isEmpty()) {
            return true;
        }

        ReadOnlyAddressBook addressBook = model.getAddressBook();

        for (Affiliation affiliation: affiliationSet) {
            Person affiliatedPerson = findAffiliatedPerson(affiliation, addressBook);

            if (affiliatedPerson == null) {
                throw new AffiliationPersonNotFoundException(affiliation.affiliationName);
            }

            if (affiliatedPerson.getName().equals(personAddingAffiliation.getName())) {
                throw new SamePersonAffiliationException(affiliatedPerson);
            }

            if (affiliatedPerson.getRole().equals(personAddingAffiliation.getRole())) {
                throw new SameRoleAffiliationException(affiliatedPerson);
            }
        }
        return true;
    }

    /**
     * Returns the person if affiliation person exist in address book, null otherwise.
     * @param affiliation The affiliation contains the affiliation person.
     * @param addressBook The address book to check for the affiliation person.
     */
    public static Person findAffiliatedPerson(Affiliation affiliation, ReadOnlyAddressBook addressBook) {
        requireNonNull(affiliation);
        requireNonNull(addressBook);
        for (Person p: addressBook.getPersonList()) {
            if (p.getName().fullName.equals(affiliation.affiliationName)) {
                return p;
            }
        }
        return null;
    }
}
