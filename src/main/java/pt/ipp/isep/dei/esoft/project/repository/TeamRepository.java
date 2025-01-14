package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The type Team repository.
 */
public class TeamRepository implements Serializable {
    private List<Team> teams;

    /**
     * Instantiates a new Team repository.
     */
    public TeamRepository() {
        this.teams = new ArrayList<>();
    }

    /**
     * Gets team by collaborator.
     *
     * @param collaborator the collaborator
     * @return the team by collaborator
     */
    public Team getTeamByCollaborator(Collaborator collaborator) {
        for (Team team : teams) {
            if (team.getCollaborators().contains(collaborator)) {
                return team;
            }
        }
        return null;
    }

    /**
     * Add.
     *
     * @param team the team
     */
    public void add(Team team) {
        if (teams.contains(team)) throw new IllegalArgumentException("Team already exists");
        teams.add(team);
    }

    /**
     * Remove.
     *
     * @param team the team
     */
    public void remove(Team team) {
        if (!teams.contains(team)) throw new IllegalArgumentException("Team does not exists");
        teams.remove(team);
    }

    /**
     * Sets teams.
     *
     * @param teams the teams
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    /**
     * Gets unassigned teams.
     *
     * @return the unassigned teams
     */
    public List<Team> getUnassignedTeams() {
        List<Team> unassignedTeams = new ArrayList<>();
        for (Team team : teams) {
            if (!team.isAssigned()) {
                unassignedTeams.add(team);
            }
        }
        return unassignedTeams;
    }

    /**
     * Gets teams.
     *
     * @return the teams
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * Generate team proposals list.
     *
     * @param minTeamSize       the min team size
     * @param maxTeamSize       the max team size
     * @param requiredSkillList the required skill list
     * @param collaborators     the collaborators
     * @return the list
     */
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

    /**
     * Has required skills boolean.
     *
     * @param team           the team
     * @param requiredSkills the required skills
     * @return the boolean
     */
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

    /**
     * Notify post pone team members.
     *
     * @param entry   the entry
     * @param oldDate the old date
     */
    public void notifyPostPoneTeamMembers(AgendaEntry entry, Date oldDate) {
        LocalDate oldLocalDate = oldDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy").withLocale(Locale.ENGLISH);
        String formattedOldDate = oldLocalDate.format(formatter);
        Date newStartDate = entry.getStartDate();
        String formatedNewStartDate = newStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        Date newEndDate = entry.getEndDate();
        String formatedNewEndDate = newEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        for (Collaborator collaborator : entry.getTeam().getCollaborators()) {
            String message = String.format(
                    """
                            Hello %s,

                            Please note that the start date for the task '%s' has been moved from %s to %s, \
                            with the new completion date set for %s.
                            We apologize for any inconvenience and appreciate your flexibility.

                            Warm regards,
                            The Musgo Sublime Administration Team""",
                    collaborator.getName(), entry.getEntry().getTitle(),
                    formattedOldDate, formatedNewStartDate, formatedNewEndDate
            );
            Notification notification = new Notification("Task Postponed", message, new EmailWrapper(new Email(collaborator.getEmail())), collaborator.getName());
            collaborator.addNotification(notification);
        }
    }

    /**
     * Notify new team.
     *
     * @param team the team
     */
    public void notifyNewTeam(Team team) {
        StringBuilder teamMembers = new StringBuilder();
        for (Collaborator collaborator : team.getCollaborators()) {
            teamMembers.append(collaborator.getName()).append(", ");
        }
        teamMembers.deleteCharAt(teamMembers.length() - 1);
        teamMembers.deleteCharAt(teamMembers.length() - 1);

        for (Collaborator collaborator : team.getCollaborators()) {
            String message = String.format(
                    """
                            Greetings %s,

                            Exciting news! You've been selected to be part of a new team: %s.
                            This is a fantastic opportunity to collaborate and innovate with your peers.

                            Best wishes,
                            The Musgo Sublime Administration Team""",
                    collaborator.getName(), teamMembers
            );
            Notification notification = new Notification("New Team", message, new EmailWrapper(new Email(collaborator.getEmail())), collaborator.getName());
            collaborator.addNotification(notification);
        }
    }

    /**
     * Notify new task team members.
     *
     * @param entry the entry
     */
    public void notifyNewTaskTeamMembers(AgendaEntry entry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy").withLocale(Locale.ENGLISH);
        Date newStartDate = entry.getStartDate();
        String formatedNewStartDate = newStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        Date newEndDate = entry.getEndDate();
        String formatedNewEndDate = newEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        for (Collaborator collaborator : entry.getTeam().getCollaborators()) {
            String message = String.format(
                    """
                            Dear %s,

                            A new journey begins! Your team has been assigned the task: '%s'.
                            It kicks off on %s and the goal post is set for %s.
                            Let's make it a success together!

                            Cheers,
                            The Musgo Sublime Administration Team""",
                    collaborator.getName(), entry.getEntry().getTitle(),
                    formatedNewStartDate, formatedNewEndDate
            );
            Notification notification = new Notification("New Task", message, new EmailWrapper(new Email(collaborator.getEmail())), collaborator.getName());
            collaborator.addNotification(notification);
        }
    }


    /**
     * Notify team removed.
     *
     * @param entry the entry
     * @param team  the team
     */
    public void notifyTeamRemoved(AgendaEntry entry, Team team) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy").withLocale(Locale.ENGLISH);
        Date newStartDate = entry.getStartDate();
        String formatedNewStartDate = newStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        Date newEndDate = entry.getEndDate();
        String formatedNewEndDate = newEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        for (Collaborator collaborator : team.getCollaborators()) {
            String message = String.format(
                    """
                            Hello %s,

                            The task '%s' has been reassigned and your team is now off the hook from %s to %s.
                            Stay tuned for upcoming projects and updates.

                            All the best,
                            The Musgo Sublime Administration Team""",
                    collaborator.getName(), entry.getEntry().getTitle(),
                    formatedNewStartDate, formatedNewEndDate
            );
            Notification notification = new Notification("Team removed from task", message, new EmailWrapper(new Email(collaborator.getEmail())), collaborator.getName());
            collaborator.addNotification(notification);
        }
    }

    /**
     * Notify team cancelled.
     *
     * @param entry the entry
     */
    public void notifyTeamCancelled(AgendaEntry entry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy").withLocale(Locale.ENGLISH);
        Date newStartDate = entry.getStartDate();
        String formatedNewStartDate = newStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        Date newEndDate = entry.getEndDate();
        String formatedNewEndDate = newEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
        for (Collaborator collaborator : entry.getTeam().getCollaborators()) {
            String message = String.format(
                    """
                            Dear %s,

                            We regret to inform you that the task '%s' has been cancelled.
                            Your schedule from %s to %s has been cleared.
                            We appreciate your understanding and will keep you posted on new developments.

                            Regards,
                            The Musgo Sublime Administration Team""",
                    collaborator.getName(), entry.getEntry().getTitle(),
                    formatedNewStartDate, formatedNewEndDate
            );
            Notification notification = new Notification("Task Cancelled", message, new EmailWrapper(new Email(collaborator.getEmail())), collaborator.getName());
            collaborator.addNotification(notification);
        }
    }
}