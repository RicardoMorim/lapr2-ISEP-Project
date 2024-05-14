package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
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
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", "123 Street", "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));
        repository.add(collaborator);
        assertEquals(collaborator, controller.getCollaboratorByEmail("john.doe@example.com"));
    }

    @Test
    void getCollaboratorByEmailThrowsExceptionWhenEmailNotFound() {
        assertThrows(IllegalArgumentException.class, () -> controller.getCollaboratorByEmail("not.found@example.com"));
    }

    @Test
    void registerCollaboratorAddsCollaboratorToRepository() {
        controller.registerCollaborator("John Doe", "john.doe@example.com", "123 Street", "1234567890", new Job("Developer", "java developer"), List.of(new Skill("Java", "Code")), new Date(), new Date(), "ID", 123456, 123456);
        assertNotNull(controller.getCollaboratorByEmail("john.doe@example.com"));
    }

    @Test
    void updateCollaboratorUpdatesCollaboratorInRepository() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", "123 Street", "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java", "Code")));
        repository.add(collaborator);
        controller.updateCollaborator(collaborator, "Jane Doe", "jane.doe@example.com", "456 Street", "0987654321", new Job("Designer", "java developer"), List.of(new Skill("Photoshop", "Photo editing")), new Date(), new Date(), "Passport", 654321, 654321);
        Collaborator updatedCollaborator = controller.getCollaboratorByEmail("jane.doe@example.com");
        assertEquals("Jane Doe", updatedCollaborator.getName());
        assertEquals("456 Street", updatedCollaborator.getAddress());
    }

    @Test
    void removeCollaboratorRemovesCollaboratorFromRepository() {
        Collaborator collaborator = new Collaborator("john.doe@example.com","John Doe",  "123 Street", "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java", "Code")));
        repository.add(collaborator);
        controller.removeCollaborator(collaborator);
        assertThrows(IllegalArgumentException.class, () -> controller.getCollaboratorByEmail("john.doe@example.com"));
    }


    @Test
    void generateTeamProposalsReturnsAllValidTeams() {
        Collaborator collaborator1 = new Collaborator("john.doe@example.com", "John Doe", "123 Street", "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java", "Code")));
        Collaborator collaborator2 = new Collaborator("jane.doe@example.com", "Jane Doe", "456 Street", "0987654321", new Job("Designer", "java developer"), new Date(), new Date(), "Passport", 654321, 654321, Arrays.asList(new Skill("Photoshop", "Photo editing")));
        Collaborator collaborator3 = new Collaborator("jao.doe@example.com", "Jaos", "123 Street", "123456127890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 1234656, 12341256, Arrays.asList(new Skill("Java", "Code")));
        Collaborator collaborator4 = new Collaborator("jaoA.doe@example.com", "Jao", "123 Street", "123467890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 1234556, 1234576, Arrays.asList(new Skill("Java", "Code")));
        repository.add(collaborator1);
        repository.add(collaborator2);
        repository.add(collaborator3);
        repository.add(collaborator4);
        List<List<Collaborator>> teamProposals = controller.GenerateTeamProposals(1, 2, Arrays.asList(new Skill("Java", "Code"), new Skill("Photoshop", "Photo editing")));
        assertEquals(3, teamProposals.size());
        assertTrue(teamProposals.stream().anyMatch(team -> team.contains(collaborator1) && team.contains(collaborator2)));
        assertFalse(teamProposals.stream().anyMatch(team -> team.contains(collaborator1) && !team.contains(collaborator2)));
        assertFalse(teamProposals.stream().anyMatch(team -> team.contains(collaborator1) && team.contains(collaborator3)));
        assertFalse(teamProposals.stream().anyMatch(team -> team.contains(collaborator1) && team.contains(collaborator4)));

    }

    @Test
    void generateTeamProposalsThrowsExceptionWhenNoValidTeamsCanBeGenerated() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", "123 Street", "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));
        repository.add(collaborator);
        assertThrows(IllegalArgumentException.class, () -> controller.GenerateTeamProposals(2, 2, Arrays.asList(new Skill("Java", "Code"), new Skill("Photoshop", "Photo editing"))));
    }

    @Test
    void registerCollaboratorShouldThrowExceptionWhenEmailIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> controller.registerCollaborator("John Doe", "invalid email", "123 Street", "1234567890", new Job("Developer", "java developer"), List.of(new Skill("Java", "Code")), new Date(), new Date(), "ID", 123456, 123456));
    }

    @Test
    void addSkillToACollaboratorShouldAddSkillToCollaborator() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", "123 Street", "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, new ArrayList<>());
        repository.add(collaborator);
        Skill skill = new Skill("Python", "AI");
        controller.addSkillToACollaborator(skill, collaborator);
        assertTrue(collaborator.getSkills().contains(skill));
    }

    @Test
    void addSkillToACollaboratorShouldThrowExceptionWhenSkillAlreadyExists() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", "123 Street", "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java", "Code")));
        repository.add(collaborator);
        Skill skill = new Skill("Java", "Code");
        assertThrows(IllegalArgumentException.class, () -> controller.addSkillToACollaborator(skill, collaborator));
    }

    @Test
    void removeSkillFromACollaboratorShouldRemoveSkillFromCollaborator() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("Java", "Code"));
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", "123 Street", "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, skills);
        repository.add(collaborator);
        List<Skill> skillsToRemove = new ArrayList<>(skills);
        controller.removeSkillFromACollaborator(skillsToRemove, collaborator);
        assertTrue(Collections.disjoint(collaborator.getSkills(), skillsToRemove));
    }

    @Test
    void removeSkillFromACollaboratorShouldThrowExceptionWhenSkillDoesNotExist() {
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", "123 Street", "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, Arrays.asList(new Skill("Java", "Code")));
        repository.add(collaborator);
        Skill skill = new Skill("Python", "Code");
        assertThrows(IllegalArgumentException.class, () -> controller.removeSkillFromACollaborator(Collections.singletonList(skill), collaborator));
    }



}