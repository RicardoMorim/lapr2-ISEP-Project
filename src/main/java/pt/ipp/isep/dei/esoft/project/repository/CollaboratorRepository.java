package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Address;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.io.Serializable;
import java.util.*;


public class CollaboratorRepository implements Serializable {

    private List<Collaborator> collaborators;

    public CollaboratorRepository() {
        this.collaborators = new ArrayList<>();
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    public Collaborator getCollaboratorByEmail(String email) throws IllegalArgumentException {
        for (Collaborator collaborator : collaborators) {
            if (collaborator.getEmail().equals(email)) {
                return collaborator;
            }
        }
        throw new IllegalArgumentException("Collaborator not found.");
    }


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

    public void checkForDuplicateCollaborator(Collaborator collaborator) {
        if (collaborators.contains(collaborator)) {
            throw new IllegalArgumentException("That collaborator already exists in the database");
        }
    }


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