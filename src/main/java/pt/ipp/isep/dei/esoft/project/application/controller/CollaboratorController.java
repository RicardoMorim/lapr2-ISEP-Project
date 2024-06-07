package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    public void notifyPostPoneTeamMembers(AgendaEntry entry, Date oldDate) {
        LocalDate oldLocalDate = oldDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy");
        String formattedOldDate = oldLocalDate.format(formatter);
        Date newStartDate = entry.getStartDate();
        String formatedNewStartDate = newStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        Date newEndDate = entry.getEndDate();
        String formatedNewEndDate = newEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        for (Collaborator collaborator : entry.getTeam().getCollaborators()) {
            String message = "Dear collaborator " + collaborator.getName() + ",\n\nThe task " + entry.getEntry().getTitle() + " previously scheduled to start at " + formattedOldDate + " has been postponed to start at " + formatedNewStartDate + ".The new end date should be: " + formatedNewEndDate + ".\n\nBest regards, The Musgo Sublime Administration.\n";
            Notification notification = new Notification("Task Postponed", message, new EmailWrapper(new Email(collaborator.getEmail())));
            collaborator.addNotification(notification);
        }
    }

    public void notifyNewTeam(Team team) {
        StringBuilder teamMembers = new StringBuilder();
        for (Collaborator collaborator : team.getCollaborators()) {
            teamMembers.append(collaborator.getName()).append(", ");
        }
        teamMembers.deleteCharAt(teamMembers.length() - 1);
        teamMembers.deleteCharAt(teamMembers.length() - 1);

        for (Collaborator collaborator : team.getCollaborators()) {
            String message = "Dear collaborator " + collaborator.getName() + ",\n\nYou have been chosen to integrate a new team!\n The team is composed by: " + teamMembers + ".\n\nBest regards, The Musgo Sublime Administration.\n";
            Notification notification = new Notification("New Team", message, new EmailWrapper(new Email(collaborator.getEmail())));
            collaborator.addNotification(notification);
        }
    }

    public void notifyNewTaskTeamMembers(AgendaEntry entry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy");
        Date newStartDate = entry.getStartDate();
        String formatedNewStartDate = newStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        Date newEndDate = entry.getEndDate();
        String formatedNewEndDate = newEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        for (Collaborator collaborator : entry.getTeam().getCollaborators()) {
            String message = "Dear collaborator " + collaborator.getName() + ",\n\nYour team has been selected for a new task. We are forwarding you the details.\n\n" + entry.getEntry().getTitle() + "\nThe task should start on " + formatedNewStartDate + "\nIt should be completed by " + formatedNewEndDate + ".\n\nBest regards, The Musgo Sublime Administration.\n";
            Notification notification = new Notification("New Task", message, new EmailWrapper(new Email(collaborator.getEmail())));
            collaborator.addNotification(notification);
        }
    }


    public CollaboratorRepository getCollaboratorRepository() {
        return collaboratorRepository;
    }


    public Collaborator getCollaboratorByEmail(String email) throws IllegalArgumentException {
        return collaboratorRepository.getCollaboratorByEmail(email);
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
