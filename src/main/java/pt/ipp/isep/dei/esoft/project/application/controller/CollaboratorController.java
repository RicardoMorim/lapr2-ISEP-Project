package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Date;
import java.util.List;

/**
 * The type Collaborator controller.
 */
public class CollaboratorController {
    /**
     * The Collaborator repository.
     */
    CollaboratorRepository collaboratorRepository;

    /**
     * Instantiates a new Collaborator controller.
     */
    public CollaboratorController() {
        if (collaboratorRepository == null) {
            Repositories repositories = Repositories.getInstance();

            collaboratorRepository = repositories.getCollaboratorRepository();
        }
    }

    /**
     * Instantiates a new Collaborator controller.
     *
     * @param collaboratorRepository the collaborator repository
     */
    public CollaboratorController(CollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }


    /**
     * Gets collaborator repository.
     *
     * @return the collaborator repository
     */
    public CollaboratorRepository getCollaboratorRepository() {
        return collaboratorRepository;
    }


    /**
     * Gets collaborator by email.
     *
     * @param email the email
     * @return the collaborator by email
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Collaborator getCollaboratorByEmail(String email) throws IllegalArgumentException {
        return collaboratorRepository.getCollaboratorByEmail(email);
    }

    /**
     * Notify new collaborator.
     *
     * @param collaborator the collaborator
     */
    public void notifyNewCollaborator(Collaborator collaborator) {
        collaboratorRepository.notifyNewCollaborator(collaborator);
    }

    /**
     * Register collaborator collaborator.
     *
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
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Collaborator registerCollaborator(String name, String email, Address address, String phone, Job
            job, List<Skill> skills, Date birthDate, Date admissionDate, String IDtype, int taxpayerNumber,
                                             int citizenNumber) throws IllegalArgumentException {
        Collaborator collaborator = new Collaborator(email, name, address, phone, job, birthDate, admissionDate, IDtype, taxpayerNumber, citizenNumber, skills);
        collaboratorRepository.add(collaborator);
        return collaborator;
    }

    /**
     * Register collaborator collaborator.
     *
     * @param name           the name
     * @param email          the email
     * @param address        the address
     * @param phone          the phone
     * @param job            the job
     * @param birthDate      the birth date
     * @param admissionDate  the admission date
     * @param IDtype         the dtype
     * @param taxpayerNumber the taxpayer number
     * @param citizenNumber  the citizen number
     * @return the collaborator
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Collaborator registerCollaborator(String name, String email, Address address, String phone, Job
            job, Date birthDate, Date admissionDate, String IDtype, int taxpayerNumber, int citizenNumber) throws
            IllegalArgumentException {
        Collaborator collaborator = new Collaborator(email, name, address, phone, job, birthDate, admissionDate, IDtype, taxpayerNumber, citizenNumber);
        collaboratorRepository.add(collaborator);
        return collaborator;
    }


    /**
     * Gets collaborator list.
     *
     * @return the collaborator list
     */
    public List<Collaborator> getCollaboratorList() {
        return collaboratorRepository.getCollaborators();
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
     */
    public void updateCollaborator(Collaborator collaborator, String name, String email, Address address, String
            phone, Job job, List<Skill> skills, Date birthDate, Date admissionDate, String IDtype, int taxpayerNumber,
                                   int citizenNumber) {
        try {
            collaboratorRepository.update(collaborator, name, email, address, phone, job, skills, birthDate, admissionDate, IDtype, taxpayerNumber, citizenNumber);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Remove collaborator.
     *
     * @param collaborator the collaborator
     */
    public void removeCollaborator(Collaborator collaborator) {
        try {
            collaboratorRepository.remove(collaborator);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Start task.
     *
     * @param team the team
     */
    public void startTask(Team team) {
        for (Collaborator collaborator : team.getCollaborators()) {
            collaborator.setFree(false);
        }
    }

    /**
     * Add skill to a collaborator.
     *
     * @param skillList    the skill list
     * @param collaborator the collaborator
     */
    public void addSkillToACollaborator(List<Skill> skillList, Collaborator collaborator) {
        try {
            for (Skill skill : skillList) {
                collaborator.addSkill(skill);

            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Add skill to a collaborator.
     *
     * @param skill        the skill
     * @param collaborator the collaborator
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void addSkillToACollaborator(Skill skill, Collaborator collaborator) throws IllegalArgumentException {
        collaborator.addSkill(skill);

    }


    /**
     * Remove skill from a collaborator.
     *
     * @param skillList    the skill list
     * @param collaborator the collaborator
     */
    public void removeSkillFromACollaborator(List<Skill> skillList, Collaborator collaborator) {
        for (Skill skill : skillList) {
            collaborator.removeSkill(skill);
        }
    }

    /**
     * Remove skill from a collaborator.
     *
     * @param skill        the skill
     * @param collaborator the collaborator
     */
    public void removeSkillFromACollaborator(Skill skill, Collaborator collaborator) {
        collaborator.removeSkill(skill);
    }


}
