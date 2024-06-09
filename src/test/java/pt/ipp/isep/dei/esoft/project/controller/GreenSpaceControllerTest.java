package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GreenSpaceControllerTest {
    private GreenSpaceController controller;
    private GreenSpaceRepository repository;
    private GreenSpace greenSpace;

    @BeforeEach
    void setUp() {
        repository = new GreenSpaceRepository();
        controller = new GreenSpaceController(repository);
        Email email = new Email("admin@this.app");
        greenSpace = new GreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email);
    }

    @Test
    void addGreenSpaceByNameAddressAreaType_success() {
        Email email = new Email("admin@this.app");
        controller.addGreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email);
        assertEquals(1, repository.getGreenSpaces().size());
    }

    @Test
    void addGreenSpaceByNameAddressAreaType_duplicateName() {
        Email email = new Email("admin@this.app");
        controller.addGreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email);
        assertThrows(IllegalArgumentException.class, () -> controller.addGreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email));
    }

    @Test
    void addGreenSpaceByNameAddressAreaType_duplicateAddress() {
        Email email = new Email("admin@this.app");
        controller.addGreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email);
        assertThrows(IllegalArgumentException.class, () -> controller.addGreenSpace("Park2", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email));
    }

    @Test
    void checkIfGreenSpaceNameExists_true() {
        controller.addGreenSpace(greenSpace);
        assertTrue(controller.checkIfGreenSpaceNameExists("Park"));
    }

    @Test
    void checkIfGreenSpaceNameExists_false() {
        assertFalse(controller.checkIfGreenSpaceNameExists("Park"));
    }

    @Test
    void checkIfGreenSpaceAddressExists_true() {
        controller.addGreenSpace(greenSpace);
        assertTrue(controller.checkIfGreenSpaceAddressExists(new Address("Street", "City", "Zip")));
    }

    @Test
    void checkIfGreenSpaceAddressExists_false() {
        assertFalse(controller.checkIfGreenSpaceAddressExists(new Address("Street", "City", "Zip")));
    }

    @Test
    void validateZipCode_valid() {
        assertTrue(controller.validateZipCode("1234-555"));
    }

    @Test
    void validateZipCode_invalid() {
        assertFalse(controller.validateZipCode("1234a"));
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