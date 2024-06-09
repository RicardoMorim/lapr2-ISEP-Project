package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * The type Team controller.
 */
public class TeamController {
    private TeamRepository teamRepository;

    /**
     * Instantiates a new Team controller.
     */
    public TeamController() {
        if (teamRepository == null){
            Repositories repositories = Repositories.getInstance();

            teamRepository = repositories.getTeamRepository();
        }
    }

    /**
     * Instantiates a new Team controller.
     *
     * @param teamRepository the team repository
     */
    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Get team by collaborator team.
     *
     * @param collaborator the collaborator
     * @return the team
     */
    public Team getTeamByCollaborator(Collaborator collaborator){
        return teamRepository.getTeamByCollaborator(collaborator);
    }


    /**
     * Notify post pone team members.
     *
     * @param entry   the entry
     * @param oldDate the old date
     */
    public void notifyPostPoneTeamMembers(AgendaEntry entry, Date oldDate) {
        teamRepository.notifyPostPoneTeamMembers(entry, oldDate);
    }

    /**
     * Notify new team.
     *
     * @param team the team
     */
    public void notifyNewTeam(Team team) {
        teamRepository.notifyNewTeam(team);
    }

    /**
     * Notify new task team members.
     *
     * @param entry the entry
     */
    public void notifyNewTaskTeamMembers(AgendaEntry entry) {
        teamRepository.notifyNewTaskTeamMembers(entry);
    }

    /**
     * Notify team removed.
     *
     * @param entry the entry
     * @param team  the team
     */
    public void notifyTeamRemoved(AgendaEntry entry, Team team) {
        teamRepository.notifyTeamRemoved(entry, team);
    }

    /**
     * Notify team cancelled.
     *
     * @param entry the entry
     */
    public void notifyTeamCancelled(AgendaEntry entry) {
        teamRepository.notifyTeamCancelled(entry);
    }


    /**
     * Add team.
     *
     * @param team the team
     */
    public void addTeam(Team team) {
        teamRepository.add(team);
    }


    /**
     * Get teams list.
     *
     * @return the list
     */
    public List<Team> getTeams(){
        return teamRepository.getTeams();
    }

    /**
     * Get unassigned teams list.
     *
     * @return the list
     */
    public List<Team> getUnassignedTeams(){
        return teamRepository.getUnassignedTeams();
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
    public List<Team> generateTeamProposals(int minTeamSize, int maxTeamSize, List<Skill> requiredSkillList, List<Collaborator> collaborators){
        List<List<Collaborator>> allTeams = teamRepository.GenerateTeamProposals(minTeamSize, maxTeamSize, requiredSkillList, collaborators);
        List<Team> teamProposals = new ArrayList<>();
        for (List<Collaborator> currTeam : allTeams) {
            Team team = new Team(currTeam);
            teamProposals.add(team);
        }
        return teamProposals;
    }

    /**
     * Unassign team.
     *
     * @param entry the entry
     */
    public void unassignTeam(AgendaEntry entry){
        Team team = entry.getTeam();
        entry.setTeam(null);
        team.setAssigned(false);
    }
}