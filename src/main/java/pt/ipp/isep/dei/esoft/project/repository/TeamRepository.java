package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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

}