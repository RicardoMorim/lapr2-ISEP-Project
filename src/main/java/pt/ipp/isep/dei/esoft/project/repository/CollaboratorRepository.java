package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.util.*;


public class CollaboratorRepository {

    private final List<Collaborator> collaborators;

    public CollaboratorRepository() {
        this.collaborators = new ArrayList<>();
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

    public Collaborator update(Collaborator collaborator, String name, String email, String address, String phone, Job job, List<Skill> skills, Date birthDate, Date admissionDate, String IDtype, int taxpayerNumber, int citizenNumber) {
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
        return List.copyOf(collaborators);
    }

    private boolean validateCollaborator(Collaborator collaborator) {
        // Check if the collaborator is already in the repository
        if (collaborators.contains(collaborator)) {
            throw new IllegalArgumentException("Collaborator already exists.");
        }

        return true;
    }

    public List<List<Collaborator>> GenerateTeamProposals(int minTeamSize, int maxTeamSize, List<Skill> requiredSkillList) {
        List<List<Collaborator>> allTeams = new ArrayList<>();
        List<Collaborator> collaboratorList = new ArrayList<>(collaborators);
        collaboratorList.removeIf(collaborator -> !collaborator.isFree()); // only use the collaborators free to new work

        generateTeams(new ArrayList<>(), collaboratorList, requiredSkillList, minTeamSize, maxTeamSize, allTeams);
        if (allTeams.isEmpty()) {
            throw new IllegalArgumentException("No team proposal could be generated with the given parameters.");
        }
        return allTeams;
    }

    private void generateTeams(List<Collaborator> currentTeam, List<Collaborator> remainingCollaborators, List<Skill> requiredSkills, int minTeamSize, int maxTeamSize, List<List<Collaborator>> allTeams) {
        if (currentTeam.size() > maxTeamSize) {
            return;
        }

        if (currentTeam.size() >= minTeamSize && currentTeam.size() <= maxTeamSize && hasRequiredSkills(currentTeam, requiredSkills)) {
            allTeams.add(new ArrayList<>(currentTeam));
        }

        List<Collaborator> remainingCollaboratorsCopy = new ArrayList<>(remainingCollaborators);
        for (Collaborator collaborator : remainingCollaborators) {
            currentTeam.add(collaborator);
            remainingCollaboratorsCopy.remove(collaborator);
            generateTeams(currentTeam, remainingCollaboratorsCopy, requiredSkills, minTeamSize, maxTeamSize, allTeams);
            currentTeam.remove(collaborator);
        }
    }

    public boolean hasRequiredSkills(List<Collaborator> team, List<Skill> requiredSkills) {
        List<Skill> teamSkills = new ArrayList<>();
        for (Collaborator collaborator : team) {
            teamSkills.addAll(collaborator.getSkills());
        }

        for (Skill requiredSkill : requiredSkills) {
            if (Collections.frequency(teamSkills, requiredSkill) < Collections.frequency(requiredSkills, requiredSkill)) {
                return false;
            }
        }

        return true;
    }


}