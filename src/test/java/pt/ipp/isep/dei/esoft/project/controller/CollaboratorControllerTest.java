package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CollaboratorControllerTest {

    private CollaboratorController controller;
    private CollaboratorRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CollaboratorRepository();
        controller = new CollaboratorController(repository);
    }

    @Test
    void getCollaboratorByEmailReturnsCorrectCollaborator() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));
        repository.add(collaborator);
        assertEquals(collaborator, controller.getCollaboratorByEmail("john.doe@example.com"));
    }

    @Test
    void getCollaboratorByEmailThrowsExceptionWhenEmailNotFound() {
        assertThrows(IllegalArgumentException.class, () -> controller.getCollaboratorByEmail("not.found@example.com"));
    }

    @Test
    void registerCollaboratorAddsCollaboratorToRepository() {
        controller.registerCollaborator("John Doe", "john.doe@example.com", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), List.of(new Skill("Java", "Code")), new Date(), new Date(), "ID", 123456, 123456);
        assertNotNull(controller.getCollaboratorByEmail("john.doe@example.com"));
    }

    @Test
    void updateCollaboratorUpdatesCollaboratorInRepository() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java", "Code")));
        repository.add(collaborator);
        Address newAddress = new Address("456 Street", "Porto", "123-456");
        controller.updateCollaborator(collaborator, "Jane Doe", "jane.doe@example.com", newAddress, "0987654321", new Job("Designer", "java developer"), List.of(new Skill("Photoshop", "Photo editing")), new Date(), new Date(), "Passport", 654321, 654321);
        Collaborator updatedCollaborator = controller.getCollaboratorByEmail("jane.doe@example.com");
        assertEquals("Jane Doe", updatedCollaborator.getName());
        assertEquals(newAddress, updatedCollaborator.getAddress());
    }

    @Test
    void removeCollaboratorRemovesCollaboratorFromRepository() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java", "Code")));
        repository.add(collaborator);
        controller.removeCollaborator(collaborator);
        assertThrows(IllegalArgumentException.class, () -> controller.getCollaboratorByEmail("john.doe@example.com"));
    }


    @Test
    void registerCollaboratorShouldThrowExceptionWhenEmailIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> controller.registerCollaborator("John Doe", "invalid email", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), List.of(new Skill("Java", "Code")), new Date(), new Date(), "ID", 123456, 123456));
    }

    @Test
    void addSkillToACollaboratorShouldAddSkillToCollaborator() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, new ArrayList<>());
        repository.add(collaborator);
        Skill skill = new Skill("Python", "AI");
        controller.addSkillToACollaborator(skill, collaborator);
        assertTrue(collaborator.getSkills().contains(skill));
    }

    @Test
    void addSkillToACollaboratorShouldThrowExceptionWhenSkillAlreadyExists() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java", "Code")));
        repository.add(collaborator);
        Skill skill = new Skill("Java", "Code");
        assertThrows(IllegalArgumentException.class, () -> controller.addSkillToACollaborator(skill, collaborator));
    }

    @Test
    void removeSkillFromACollaboratorShouldRemoveSkillFromCollaborator() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("Java", "Code"));
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, skills);
        repository.add(collaborator);
        List<Skill> skillsToRemove = new ArrayList<>(skills);
        controller.removeSkillFromACollaborator(skillsToRemove, collaborator);
        assertTrue(Collections.disjoint(collaborator.getSkills(), skillsToRemove));
    }

    @Test
    void removeSkillFromACollaboratorShouldThrowExceptionWhenSkillDoesNotExist() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java", "Code")));
        repository.add(collaborator);
        Skill skill = new Skill("Python", "Code");
        assertThrows(IllegalArgumentException.class, () -> controller.removeSkillFromACollaborator(Collections.singletonList(skill), collaborator));
    }


    @Test
    void testNotifyNewCollaborator() {

        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));

        controller.notifyNewCollaborator(collaborator);

        assertEquals(1, collaborator.getNotifications().size());
    }

    @Test
    void testStartTask() {
        // Given
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));
        Team team = new Team(Collections.singletonList(collaborator));

        controller.startTask(team);

        assertFalse(collaborator.isFree());
    }

    @Test
    void testAddSkillToACollaborator() {
        // Given
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, new ArrayList<>());
        Skill skill = new Skill("Python", "AI");
        // When
        controller.addSkillToACollaborator(skill, collaborator);
        // Then
        assertTrue(collaborator.getSkills().contains(skill));
    }

    @Test
    void testRemoveSkillFromACollaborator() {

        Skill skill = new Skill("Java", "Code");
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(skill));

        controller.removeSkillFromACollaborator(skill, collaborator);

        assertFalse(collaborator.getSkills().contains(skill));
    }


    @Test
    void testGetCollaboratorRepository() {
        CollaboratorRepository expected = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(expected);
        assertEquals(expected, controller.getCollaboratorRepository());
    }

    @Test
    void testGetCollaboratorList() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));
        repository.add(collaborator);
        List<Collaborator> expected = List.of(collaborator);
        assertEquals(expected, controller.getCollaboratorList());
    }

    @Test
    void testRegisterCollaboratorWithSkills() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        List<Skill> skills = List.of(new Skill("Java", "Code"));
        Collaborator collaborator = controller.registerCollaborator("John Doe", "john.doe@example.com", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), skills, new Date(), new Date(), "ID", 123456, 123456);
        assertEquals(skills, collaborator.getSkills());
    }
}