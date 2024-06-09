package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.TeamController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeamControllerTest {

    private TeamRepository teamRepository;
    private TeamController teamController;

    @BeforeEach
    public void setUp() {
        teamRepository = new TeamRepository();
        teamController = new TeamController(teamRepository);
    }

    @Test
    public void testGetTeamByCollaborator() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);

        Team team = new Team(Collections.singletonList(collaborator));
        teamRepository.add(team);

        Team result = teamController.getTeamByCollaborator(collaborator);

        assertEquals(team, result);
    }

    @Test
    public void testNotifyPostPoneTeamMembers() {
        GreenSpace greenSpace = new GreenSpace("Cidade", new Address("Rua da Cidade", "Porto", "1234-456"), 10000, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));

        Entry entry = new Entry(greenSpace, "entry", "e isso", Urgency.HIGH, 2);
        Date startDate = new Date();
        Date endDate = new Date();
        String duration = "1h";
        AgendaEntry agendaEntry = new AgendaEntry(entry, startDate, endDate, duration);
        Date oldDate = new Date();
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);

        Team team = new Team(Collections.singletonList(collaborator));
        agendaEntry.setTeam(team);
        teamController.notifyPostPoneTeamMembers(agendaEntry, oldDate);
        // No assertions needed since it's a void method, but ensure no exceptions
    }

    @Test
    public void testNotifyNewTeam() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);

        Team team = new Team(Collections.singletonList(collaborator));

        teamController.notifyNewTeam(team);
        // No assertions needed since it's a void method, but ensure no exceptions
    }

    @Test
    public void testNotifyNewTaskTeamMembers() {
        GreenSpace greenSpace = new GreenSpace("Cidade", new Address("Rua da Cidade", "Porto", "1234-456"), 10000, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));

        Entry entry = new Entry(greenSpace, "entry", "e isso", Urgency.HIGH, 2);
        Date startDate = new Date();
        Date endDate = new Date();
        String duration = "1h";
        AgendaEntry agendaEntry = new AgendaEntry(entry, startDate, endDate, duration);

        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);

        Team team = new Team(Collections.singletonList(collaborator));

        agendaEntry.setTeam(team);

        teamController.notifyNewTaskTeamMembers(agendaEntry);
        // No assertions needed since it's a void method, but ensure no exceptions
    }

    @Test
    public void testNotifyTeamRemoved() {
        GreenSpace greenSpace = new GreenSpace("Cidade", new Address("Rua da Cidade", "Porto", "1234-456"), 10000, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));

        Entry entry = new Entry(greenSpace, "entry", "e isso", Urgency.HIGH, 2);
        Date startDate = new Date();
        Date endDate = new Date();
        String duration = "1h";
        AgendaEntry agendaEntry = new AgendaEntry(entry, startDate, endDate, duration);
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);

        Team team = new Team(Collections.singletonList(collaborator));

        teamController.notifyTeamRemoved(agendaEntry, team);
        // No assertions needed since it's a void method, but ensure no exceptions
    }

    @Test
    public void testNotifyTeamCancelled() {
        GreenSpace greenSpace = new GreenSpace("Cidade", new Address("Rua da Cidade", "Porto", "1234-456"), 10000, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));

        Entry entry = new Entry(greenSpace, "entry", "e isso", Urgency.HIGH, 2);
        Date startDate = new Date();
        Date endDate = new Date();
        String duration = "1h";
        AgendaEntry agendaEntry = new AgendaEntry(entry, startDate, endDate, duration);
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);

        Team team = new Team(Collections.singletonList(collaborator));

        agendaEntry.setTeam(team);
        teamController.notifyTeamCancelled(agendaEntry);
        // No assertions needed since it's a void method, but ensure no exceptions
    }

    @Test
    public void testAddTeam() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);

        Team team = new Team(Collections.singletonList(collaborator));

        teamController.addTeam(team);

        assertTrue(teamRepository.getTeams().contains(team));
    }

    @Test
    public void testGetTeams() {
        List<Team> expectedTeams = new ArrayList<>();
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);

        Team team = new Team(Collections.singletonList(collaborator));
        expectedTeams.add(team);
        teamRepository.add(expectedTeams.get(0));

        List<Team> result = teamController.getTeams();

        assertEquals(expectedTeams, result);
    }

    @Test
    public void testGetUnassignedTeams() {
        List<Team> expectedTeams = new ArrayList<>();
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);

        Team unassignedTeam = new Team(Collections.singletonList(collaborator));
        unassignedTeam.setAssigned(false);
        expectedTeams.add(unassignedTeam);
        teamRepository.add(unassignedTeam);

        List<Team> result = teamController.getUnassignedTeams();

        assertEquals(expectedTeams, result);
    }

    @Test
    public void testGenerateTeamProposals() {
        int minTeamSize = 2;
        int maxTeamSize = 5;
        List<Skill> requiredSkills = new ArrayList<>();
        List<Collaborator> collaborators = new ArrayList<>();
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);

        Collaborator collaborator2 = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);

        collaborators.add(collaborator);
        collaborators.add(collaborator2);
        List<Team> result = teamController.generateTeamProposals(minTeamSize, maxTeamSize, requiredSkills, collaborators);

        assertNotNull(result);
    }

    @Test
    public void testUnassignTeam() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456);

        Team team = new Team(Collections.singletonList(collaborator));

        team.setAssigned(true);
        GreenSpace greenSpace = new GreenSpace("Cidade", new Address("Rua da Cidade", "Porto", "1234-456"), 10000, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));

        Entry entry = new Entry(greenSpace, "entry", "e isso", Urgency.HIGH, 2);
        Date startDate = new Date();
        Date endDate = new Date();
        String duration = "1h";
        AgendaEntry agendaEntry = new AgendaEntry(entry, startDate, endDate, duration);
        agendaEntry.setTeam(team);

        teamController.unassignTeam(agendaEntry);

        assertNull(agendaEntry.getTeam());
        assertFalse(team.isAssigned());
    }
}
