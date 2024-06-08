package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Date;
import java.util.List;

public class CollaboratorController {
    CollaboratorRepository collaboratorRepository;

    public CollaboratorController() {
        if (collaboratorRepository == null) {
            Repositories repositories = Repositories.getInstance();

            collaboratorRepository = repositories.getCollaboratorRepository();
        }
    }

    public CollaboratorController(CollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }


    public CollaboratorRepository getCollaboratorRepository() {
        return collaboratorRepository;
    }


    public Collaborator getCollaboratorByEmail(String email) throws IllegalArgumentException {
        return collaboratorRepository.getCollaboratorByEmail(email);
    }

    public void notifyNewCollaborator(Collaborator collaborator) {
        collaboratorRepository.notifyNewCollaborator(collaborator);
    }

    public Collaborator registerCollaborator(String name, String email, Address address, String phone, Job
            job, List<Skill> skills, Date birthDate, Date admissionDate, String IDtype, int taxpayerNumber,
                                             int citizenNumber) throws IllegalArgumentException {
        Collaborator collaborator = new Collaborator(email, name, address, phone, job, birthDate, admissionDate, IDtype, taxpayerNumber, citizenNumber, skills);
        collaboratorRepository.add(collaborator);
        return collaborator;
    }

    public Collaborator registerCollaborator(String name, String email, Address address, String phone, Job
            job, Date birthDate, Date admissionDate, String IDtype, int taxpayerNumber, int citizenNumber) throws
            IllegalArgumentException {
        Collaborator collaborator = new Collaborator(email, name, address, phone, job, birthDate, admissionDate, IDtype, taxpayerNumber, citizenNumber);
        collaboratorRepository.add(collaborator);
        return collaborator;
    }


    public List<Collaborator> getCollaboratorList() {
        return collaboratorRepository.getCollaborators();
    }

    public void updateCollaborator(Collaborator collaborator, String name, String email, Address address, String
            phone, Job job, List<Skill> skills, Date birthDate, Date admissionDate, String IDtype, int taxpayerNumber,
                                   int citizenNumber) {
        try {
            collaboratorRepository.update(collaborator, name, email, address, phone, job, skills, birthDate, admissionDate, IDtype, taxpayerNumber, citizenNumber);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeCollaborator(Collaborator collaborator) {
        try {
            collaboratorRepository.remove(collaborator);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void startTask(List<Collaborator> collaboratorList) {
        for (Collaborator collaborator : collaboratorList) {
            collaborator.setFree(false);
        }
    }

    public void startTask(Team team) {
        for (Collaborator collaborator : team.getCollaborators()) {
            collaborator.setFree(false);
        }
    }

    public void addSkillToACollaborator(List<Skill> skillList, Collaborator collaborator) {
        try {
            for (Skill skill : skillList) {
                collaborator.addSkill(skill);

            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addSkillToACollaborator(Skill skill, Collaborator collaborator) throws IllegalArgumentException {
        collaborator.addSkill(skill);

    }


    public void removeSkillFromACollaborator(List<Skill> skillList, Collaborator collaborator) {
        for (Skill skill : skillList) {
            collaborator.removeSkill(skill);
        }
    }

    public void removeSkillFromACollaborator(Skill skill, Collaborator collaborator) {
        collaborator.removeSkill(skill);
    }


}
