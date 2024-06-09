package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.Serializable;
import java.util.*;


/**
 * The type Collaborator repository.
 */
public class CollaboratorRepository implements Serializable {

    private List<Collaborator> collaborators;

    /**
     * Instantiates a new Collaborator repository.
     */
    public CollaboratorRepository() {
        this.collaborators = new ArrayList<>();
    }

    /**
     * Sets collaborators.
     *
     * @param collaborators the collaborators
     */
    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * Gets collaborator by email.
     *
     * @param email the email
     * @return the collaborator by email
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Collaborator getCollaboratorByEmail(String email) throws IllegalArgumentException {
        for (Collaborator collaborator : collaborators) {
            if (collaborator.getEmail().equals(email)) {
                return collaborator;
            }
        }
        throw new IllegalArgumentException("Collaborator not found.");
    }

    /**
     * Notify new collaborator.
     *
     * @param collaborator the collaborator
     */
    public void notifyNewCollaborator(Collaborator collaborator) {
        String message = "Hello " + collaborator.getName() + ",\n\n" +
                "Welcome aboard! We're thrilled to have you join us as a collaborator. Your unique skills and perspectives are a valuable addition to our team.\n\n" +
                "We're committed to providing a supportive and innovative environment where you can thrive and make a significant impact. Feel free to reach out to your team members or the administration for any assistance as you settle in.\n\n" +
                "We look forward to achieving great things together.\n\n" +
                "Warm regards,\n" +
                "The Musgo Sublime Administration Team";
        Notification notification = new Notification("A Warm Welcome to Our Team!", message, new EmailWrapper(new Email(collaborator.getEmail())), collaborator.getName());
        collaborator.addNotification(notification);
    }

    /**
     * Add optional.
     *
     * @param collaborator the collaborator
     * @return the optional
     */
    public Optional<Collaborator> add(Collaborator collaborator) {
        Optional<Collaborator> newCollaborator = Optional.empty();
        boolean operationSuccess = false;

        if (validateCollaborator(collaborator)) {
            newCollaborator = Optional.of(collaborator);
            operationSuccess = collaborators.add(newCollaborator.get());
        }

        if (!operationSuccess) {
            newCollaborator = Optional.empty();
        }

        return newCollaborator;
    }


    /**
     * Remove optional.
     *
     * @param collaborator the collaborator
     * @return the optional
     */
    public Optional<Collaborator> remove(Collaborator collaborator) {
        Optional<Collaborator> newCollaborator = Optional.empty();
        boolean operationSuccess = false;

        if (collaborators.contains(collaborator)) {
            newCollaborator = Optional.of(collaborator);
            operationSuccess = collaborators.remove(newCollaborator.get());
        }

        if (!operationSuccess) {
            newCollaborator = Optional.empty();
        }

        return newCollaborator;
    }

    /**
     * Update collaborator.
     *
     * @param collaborator   the collaborator
     * @param name           the name
     * @param email          the email
     * @param address        the address
     * @param phone          the phone
     * @param job            the job
     * @param skills         the skills
     * @param birthDate      the birth date
     * @param admissionDate  the admission date
     * @param IDtype         the dtype
     * @param taxpayerNumber the taxpayer number
     * @param citizenNumber  the citizen number
     * @return the collaborator
     */
    public Collaborator update(Collaborator collaborator, String name, String email, Address address, String phone, Job job, List<Skill> skills, Date birthDate, Date admissionDate, String IDtype, int taxpayerNumber, int citizenNumber) {
        boolean operationSuccess = false;

        if (collaborators.contains(collaborator)) {
            this.collaborators.remove(collaborator);
            collaborator.setName(name);
            collaborator.setEmail(email);
            collaborator.setAddress(address);
            collaborator.setPhone(phone);
            collaborator.setJob(job);
            collaborator.setSkills(skills);
            collaborator.setBirthDate(birthDate);
            collaborator.setAdmissionDate(admissionDate);
            collaborator.setIDtype(IDtype);
            collaborator.setTaxpayerNumber(taxpayerNumber);
            collaborator.setCitizenNumber(citizenNumber);
            operationSuccess = true;
            this.collaborators.add(collaborator);
        }

        if (!operationSuccess) {
            throw new IllegalArgumentException("Collaborator not found.");
        }

        return collaborator;
    }

    /**
     * Update collaborator.
     *
     * @param oldCollaborator the old collaborator
     * @param newCollaborator the new collaborator
     * @return the collaborator
     */
    public Collaborator update(Collaborator oldCollaborator, Collaborator newCollaborator) {
        boolean operationSuccess = false;

        if (collaborators.contains(oldCollaborator)) {
            this.collaborators.remove(oldCollaborator);
            operationSuccess = this.collaborators.add(newCollaborator);
        }

        if (!operationSuccess) {
            throw new IllegalArgumentException("Collaborator not found.");
        }

        return newCollaborator;
    }

    /**
     * Check for duplicate collaborator.
     *
     * @param collaborator the collaborator
     */
    public void checkForDuplicateCollaborator(Collaborator collaborator) {
        if (collaborators.contains(collaborator)) {
            throw new IllegalArgumentException("That collaborator already exists in the database");
        }
    }


    /**
     * Gets collaborators.
     *
     * @return the collaborators
     */
    public List<Collaborator> getCollaborators() {
        //This is a defensive copy, so that the repository cannot be modified from the outside.
        return collaborators;
    }

    private boolean validateCollaborator(Collaborator collaborator) {
        // Check if the collaborator is already in the repository
        if (collaborators.contains(collaborator)) {
            throw new IllegalArgumentException("Collaborator already exists.");
        }

        return true;
    }


}