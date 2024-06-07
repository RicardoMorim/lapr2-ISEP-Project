package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TeamRepository implements Serializable {
    private List<Team> teams;

    public TeamRepository() {
        this.teams = new ArrayList<>();
    }

    public Team getTeamByCollaborator(Collaborator collaborator){
        for (Team team : teams) {
            if (team.getCollaborators().contains(collaborator)) {
                return team;
            }
        }
        return null;
    }

    public void add(Team team) {
        if (teams.contains(team))throw new IllegalArgumentException("Team already exists");
        teams.add(team);
    }

    public void remove(Team team) {
        if (!teams.contains(team))throw new IllegalArgumentException("Team does not exists");
        teams.remove(team);
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Team> getUnassignedTeams() {
        List<Team> unassignedTeams = new ArrayList<>();
        for (Team team : teams) {
            if (!team.isAssigned()) {
                unassignedTeams.add(team);
            }
        }
        return unassignedTeams;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<List<Collaborator>> GenerateTeamProposals(int minTeamSize, int maxTeamSize, List<Skill> requiredSkillList, List<Collaborator> collaborators) {
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
            Notification notification = new Notification("Task Postponed", message, new EmailWrapper(new Email(collaborator.getEmail())), collaborator.getName());
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
            Notification notification = new Notification("New Team", message, new EmailWrapper(new Email(collaborator.getEmail())), collaborator.getName());
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
            Notification notification = new Notification("New Task", message, new EmailWrapper(new Email(collaborator.getEmail())), collaborator.getName());
            collaborator.addNotification(notification);
        }
    }


    public void notifyTeamRemoved(AgendaEntry entry, Team team) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy");
        Date newStartDate = entry.getStartDate();
        String formatedNewStartDate = newStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        Date newEndDate = entry.getEndDate();
        String formatedNewEndDate = newEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        for (Collaborator collaborator : team.getCollaborators()) {
            String message = "Dear collaborator " + collaborator.getName() + ",\n\nYour team has been removed of the task " + entry.getEntry().getTitle() + "\nYour calendar should be from " + formatedNewStartDate + "\nto " + formatedNewEndDate + ", unless new changes are made, in which case you will be notified.\n\nBest regards, The Musgo Sublime Administration.\n";
            Notification notification = new Notification("Team removed from task", message, new EmailWrapper(new Email(collaborator.getEmail())), collaborator.getName());
            collaborator.addNotification(notification);
        }
    }

    public void notifyTeamCancelled(AgendaEntry entry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy");
        Date newStartDate = entry.getStartDate();
        String formatedNewStartDate = newStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        Date newEndDate = entry.getEndDate();
        String formatedNewEndDate = newEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        for (Collaborator collaborator : entry.getTeam().getCollaborators()) {
            String message = "Dear collaborator " + collaborator.getName() + ",\n\nYour team has been removed of the task " + entry.getEntry().getTitle() + "\nYour calendar should be from " + formatedNewStartDate + "\nto " + formatedNewEndDate + ", unless new changes are made, in which case you will be notified.\n\nBest regards, The Musgo Sublime Administration.\n";
            Notification notification = new Notification("Task Cancelled", message, new EmailWrapper(new Email(collaborator.getEmail())), collaborator.getName());
            collaborator.addNotification(notification);
        }
    }



}