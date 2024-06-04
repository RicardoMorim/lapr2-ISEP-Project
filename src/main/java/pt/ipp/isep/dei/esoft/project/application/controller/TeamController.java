package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;

public class TeamController {
    private TeamRepository teamRepository;

    public TeamController() {
        if (teamRepository == null){
            Repositories repositories = Repositories.getInstance();

            teamRepository = repositories.getTeamRepository();
        }
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