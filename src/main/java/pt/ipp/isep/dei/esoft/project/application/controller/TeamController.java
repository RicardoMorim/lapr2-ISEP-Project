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

public class TeamController {
    private TeamRepository teamRepository;

    public TeamController() {
        if (teamRepository == null){
            Repositories repositories = Repositories.getInstance();

            teamRepository = repositories.getTeamRepository();
        }
    }

    public Team getTeamByCollaborator(Collaborator collaborator){
        return teamRepository.getTeamByCollaborator(collaborator);
    }


    public void notifyPostPoneTeamMembers(AgendaEntry entry, Date oldDate) {
        teamRepository.notifyPostPoneTeamMembers(entry, oldDate);
    }

    public void notifyNewTeam(Team team) {
        teamRepository.notifyNewTeam(team);
    }

    public void notifyNewTaskTeamMembers(AgendaEntry entry) {
        teamRepository.notifyNewTaskTeamMembers(entry);
    }

    public void notifyTeamRemoved(AgendaEntry entry, Team team) {
        teamRepository.notifyTeamRemoved(entry, team);
    }

    public void notifyTeamCancelled(AgendaEntry entry) {
        teamRepository.notifyTeamCancelled(entry);
    }


    public void addTeam(Team team) {
        teamRepository.add(team);
    }

    public Team createTeam(List<Collaborator> collaborators) {
        Team team = new Team(collaborators);
        teamRepository.add(team);
        return team;
    }

    public List<Team> getTeams(){
        return teamRepository.getTeams();
    }
    public List<Team> getUnassignedTeams(){
        return teamRepository.getUnassignedTeams();
    }

    public List<Team> generateTeamProposals(int minTeamSize, int maxTeamSize, List<Skill> requiredSkillList, List<Collaborator> collaborators){
        List<List<Collaborator>> allTeams = teamRepository.GenerateTeamProposals(minTeamSize, maxTeamSize, requiredSkillList, collaborators);
        List<Team> teamProposals = new ArrayList<>();
        for (List<Collaborator> currTeam : allTeams) {
            Team team = new Team(currTeam);
            teamProposals.add(team);
        }
        return teamProposals;
    }

    public void unassignTeam(AgendaEntry entry){
        Team team = entry.getTeam();
        entry.setTeam(null);
        team.setAssigned(false);
    }
}