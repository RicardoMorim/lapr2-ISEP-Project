package pt.ipp.isep.dei.esoft.project.application.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class TeamControllerTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private Repositories repositories;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(repositories.getTeamRepository()).thenReturn(teamRepository);
    }

    @Test
    public void testGetTeamByCollaborator() {
        Collaborator collaborator = new Collaborator("John Doe");
        Team expectedTeam = new Team("Team A");
        when(teamRepository.getTeamByCollaborator(collaborator)).thenReturn(expectedTeam);

        Team result = teamController.getTeamByCollaborator(collaborator);

        assertEquals(expectedTeam, result);
        verify(teamRepository).getTeamByCollaborator(collaborator);
    }

    @Test
    public void testNotifyPostPoneTeamMembers() {
        AgendaEntry entry = new AgendaEntry();
        Date oldDate = new Date();

        teamController.notifyPostPoneTeamMembers(entry, oldDate);

        verify(teamRepository).notifyPostPoneTeamMembers(entry, oldDate);
    }

    @Test
    public void testNotifyNewTeam() {
        Team team = new Team("Team B");

        teamController.notifyNewTeam(team);

        verify(teamRepository).notifyNewTeam(team);
    }

    @Test
    public void testNotifyNewTaskTeamMembers() {
        AgendaEntry entry = new AgendaEntry();

        teamController.notifyNewTaskTeamMembers(entry);

        verify(teamRepository).notifyNewTaskTeamMembers(entry);
    }

    @Test
    public void testNotifyTeamRemoved() {
        AgendaEntry entry = new AgendaEntry();
        Team team = new Team("Team C");

        teamController.notifyTeamRemoved(entry, team);

        verify(teamRepository).notifyTeamRemoved(entry, team);
    }

    @Test
    public void testNotifyTeamCancelled() {
        Entry entry = new Entry();
        AgendaEntry entry = new AgendaEntry();

        teamController.notifyTeamCancelled(entry);

        verify(teamRepository).notifyTeamCancelled(entry);
    }

    @Test
    public void testAddTeam() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);
        Team team = new Team(Collections.singletonList(collaborator));
        teamController.addTeam(team);

        verify(teamRepository).add(team);
    }

    @Test
    public void testGetTeams() {
        List<Team> expectedTeams = new ArrayList<>();
        when(teamRepository.getTeams()).thenReturn(expectedTeams);

        List<Team> result = teamController.getTeams();

        assertEquals(expectedTeams, result);
        verify(teamRepository).getTeams();
    }

    @Test
    public void testGetUnassignedTeams() {
        List<Team> expectedTeams = new ArrayList<>();
        when(teamRepository.getUnassignedTeams()).thenReturn(expectedTeams);

        List<Team> result = teamController.getUnassignedTeams();

        assertEquals(expectedTeams, result);
        verify(teamRepository).getUnassignedTeams();
    }

    @Test
    public void testGenerateTeamProposals() {
        int minTeamSize = 2;
        int maxTeamSize = 5;
        List<Skill> requiredSkills = new ArrayList<>();
        List<Collaborator> collaborators = new ArrayList<>();
        List<List<Collaborator>> allTeams = new ArrayList<>();
        when(teamRepository.GenerateTeamProposals(minTeamSize, maxTeamSize, requiredSkills, collaborators)).thenReturn(allTeams);

        List<Team> result = teamController.generateTeamProposals(minTeamSize, maxTeamSize, requiredSkills, collaborators);

        assertEquals(allTeams.size(), result.size());
        verify(teamRepository).GenerateTeamProposals(minTeamSize, maxTeamSize, requiredSkills, collaborators);
    }

    @Test
    public void testUnassignTeam() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);
        Team team = new Team(Collections.singletonList(collaborator));
        team.setAssigned(true);
        AgendaEntry entry = mock(AgendaEntry.class);
        when(entry.getTeam()).thenReturn(team);

        teamController.unassignTeam(entry);

        verify(entry).setTeam(null);
        assertFalse(team.isAssigned());
    }
}
