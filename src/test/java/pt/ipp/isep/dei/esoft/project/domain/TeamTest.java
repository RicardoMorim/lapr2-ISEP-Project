package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Team test.
 */
class TeamTest {

    private Team team;
    private Collaborator collaborator;
    private Skill skill;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        skill = new Skill("Java", "Code");
        collaborator = new Collaborator("123@gmail.com", "Ricardo", new Address("456 Street", "Porto", "123-456"), "908767", new Job("driver", "driver"), new Date(), new Date(), "ID", 123456, 78910, List.of(skill));
        List<Collaborator> collaborators = new ArrayList<>();
        collaborators.add(collaborator);
        team = new Team(collaborators);
    }

    /**
     * Test constructor.
     */
    @Test
    void testConstructor() {
        assertEquals(List.of(collaborator), team.getCollaborators());
        assertFalse(team.isAssigned());
    }

    /**
     * Test get skills.
     */
    @Test
    void testGetSkills() {
        assertEquals(List.of(skill), team.getSkills());
    }

    /**
     * Test set collaborators.
     */
    @Test
    void testSetCollaborators() {
        Collaborator newCollaborator = new Collaborator("123@gmail.com", "Ricardo", new Address("456 Street", "Porto", "123-456"), "908767", new Job("driver", "driver"), new Date(), new Date(), "ID", 123456, 78910, List.of(skill));
        List<Collaborator> newCollaborators = new ArrayList<>();
        newCollaborators.add(newCollaborator);
        team.setCollaborators(newCollaborators);
        assertEquals(newCollaborators, team.getCollaborators());
    }

    /**
     * Test set assigned.
     */
    @Test
    void testSetAssigned() {
        team.setAssigned(true);
        assertTrue(team.isAssigned());
    }

    /**
     * Test to string.
     */
    @Test
    void testToString() {
        String expected = "Team:\n" + collaborator.toString() + "\nIs Assigned: false";
        assertEquals(expected, team.toString());
    }
}