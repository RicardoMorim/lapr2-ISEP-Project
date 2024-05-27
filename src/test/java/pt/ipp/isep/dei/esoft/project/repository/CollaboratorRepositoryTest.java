package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CollaboratorRepositoryTest {

    private CollaboratorRepository collaboratorRepository;
    private Collaborator collaborator;

    @BeforeEach
    void setUp() {
        collaboratorRepository = new CollaboratorRepository();
        collaborator = new Collaborator("john.doe@example.com", "John Doe", "123 Main St", "555-555-5555", new Job("Developer", "Description"), new Date(), new Date(), "Passport", 123456789, 987654321, new ArrayList<Skill>());
    }

    @Test
    void addCollaboratorReturnsOptionalWithCollaborator() {
        Optional<Collaborator> result = collaboratorRepository.add(collaborator);
        assertTrue(result.isPresent());
        assertEquals(collaborator, result.get());
    }

    @Test
    void addCollaboratorThrowsExceptionWhenCollaboratorAlreadyExists() {
        collaboratorRepository.add(collaborator);
        assertThrows(IllegalArgumentException.class, () -> collaboratorRepository.add(collaborator));
    }

    @Test
    void removeCollaboratorReturnsOptionalWithCollaborator() {
        collaboratorRepository.add(collaborator);
        Optional<Collaborator> result = collaboratorRepository.remove(collaborator);
        assertTrue(result.isPresent());
        assertEquals(collaborator, result.get());
    }

    @Test
    void removeCollaboratorReturnsEmptyOptionalWhenCollaboratorDoesNotExist() {
        Optional<Collaborator> result = collaboratorRepository.remove(collaborator);
        assertFalse(result.isPresent());
    }

    @Test
    void updateCollaboratorUpdatesAndReturnsCollaborator() {
        collaboratorRepository.add(collaborator);
        Collaborator updatedCollaborator = collaboratorRepository.update(collaborator, "Jane Doe", "jane.doe@example.com", "456 Main St", "555-555-5556", new Job("Manager", "Manages the collaborators"), new ArrayList<Skill>(), new Date(), new Date(), "Driver's License", 123456780, 987654320);
        assertEquals("Jane Doe", updatedCollaborator.getName());
        assertEquals("jane.doe@example.com", updatedCollaborator.getEmail());
    }

    @Test
    void updateCollaboratorThrowsExceptionWhenCollaboratorDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> collaboratorRepository.update(collaborator, "Jane Doe", "jane.doe@example.com", "456 Main St", "555-555-5556", new Job("Manager", "Manages the collaborators"), new ArrayList<Skill>(), new Date(), new Date(), "Driver's License", 123456780, 987654320));
    }

    @Test
    void getCollaboratorsReturnsListOfCollaborators() {
        collaboratorRepository.add(collaborator);
        List<Collaborator> collaborators = collaboratorRepository.getCollaborators();
        assertEquals(1, collaborators.size());
        assertEquals(collaborator, collaborators.get(0));
    }

}