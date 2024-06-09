package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Team repository test.
 */
class TeamRepositoryTest {

    private TeamRepository teamRepository;
    private Team team1;
    private Team team2;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        teamRepository = new TeamRepository();
        team1 = new Team(Collections.singletonList(new Collaborator("email@example.com", "John Doe", new Address("456 Street", "Porto", "123-456"), "123456789", new Job("Job Title", "Job Description"), new Date(), new Date(), "ID Type", 123, 456)));
        team2 = new Team(Collections.singletonList(new Collaborator("email2@example.com", "Jane Doe", new Address("789 Street", "Lisbon", "987-654"), "987654321", new Job("Job Title 2", "Job Description 2"), new Date(), new Date(), "ID Type", 456, 789)));
    }

    /**
     * Test add.
     */
    @Test
    void testAdd() {
        teamRepository.add(team1);
        List<Team> teams = teamRepository.getTeams();
        assertTrue(teams.contains(team1));
    }

    /**
     * Test remove.
     */
    @Test
    void testRemove() {
        teamRepository.add(team1);
        teamRepository.remove(team1);
        List<Team> teams = teamRepository.getTeams();
        assertFalse(teams.contains(team1));
    }

    /**
     * Test get unassigned teams.
     */
    @Test
    void testGetUnassignedTeams() {
        teamRepository.add(team1);
        teamRepository.add(team2);
        team1.setAssigned(true); // team1 is assigned
        List<Team> unassignedTeams = teamRepository.getUnassignedTeams();
        assertTrue(unassignedTeams.contains(team2) && !unassignedTeams.contains(team1));
    }

    /**
     * Test get team by collaborator.
     */
    @Test
    void testGetTeamByCollaborator() {
        teamRepository.add(team1);
        Collaborator collaborator = team1.getCollaborators().get(0);
        Team team = teamRepository.getTeamByCollaborator(collaborator);
        assertEquals(team1, team);
    }

    /**
     * Test set teams.
     */
    @Test
    void testSetTeams() {
        List<Team> newTeams = Arrays.asList(team1, team2);
        teamRepository.setTeams(newTeams);
        assertEquals(newTeams, teamRepository.getTeams());
    }

    /**
     * Test generate team proposals.
     */
    @Test
    void testGenerateTeamProposals() {
        List<Skill> requiredSkills = Arrays.asList(new Skill("Java","Desk"), new Skill("Python","Desk"));
        List<Collaborator> collaborators = Arrays.asList(
                new Collaborator("email1@example.com", "John Doe", new Address("456 Street", "Porto", "1234-456"), "123456789", new Job("Job Title", "Job Description"), new Date(), new Date(), "ID Type", 123, 456, Collections.singletonList(new Skill("Java","Desk"))),
                new Collaborator("email2@example.com", "Jane Doe", new Address("789 Street", "Lisbon", "9874-654"), "987654321", new Job("Job Title 2", "Job Description 2"), new Date(), new Date(), "ID Type", 456, 789, Collections.singletonList(new Skill("Python","Desk")))
        );
        List<List<Collaborator>> teamProposals = teamRepository.GenerateTeamProposals(1, 2, requiredSkills, collaborators);
        assertFalse(teamProposals.isEmpty());
    }

    /**
     * Test has required skills.
     */
    @Test
    void testHasRequiredSkills() {
        List<Collaborator> team = Arrays.asList(
                new Collaborator("email1@example.com", "John Doe", new Address("456 Street", "Porto", "123-456"), "123456789", new Job("Job Title", "Job Description"), new Date(), new Date(), "ID Type", 123, 456, Collections.singletonList(new Skill("Java","Desk"))),
                new Collaborator("email2@example.com", "Jane Doe", new Address("789 Street", "Lisbon", "987-654"), "987654321", new Job("Job Title 2", "Job Description 2"), new Date(), new Date(), "ID Type", 456, 789, Collections.singletonList(new Skill("Python","Desk")))
        );
        List<Skill> requiredSkills = Arrays.asList(new Skill("Java","Desk"), new Skill("Python","Desk"));
        assertTrue(teamRepository.hasRequiredSkills(team, requiredSkills));
    }
}