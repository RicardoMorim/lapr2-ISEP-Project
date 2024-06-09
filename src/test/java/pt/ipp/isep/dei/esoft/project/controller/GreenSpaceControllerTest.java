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

/**
 * The type Green space controller test.
 */
class GreenSpaceControllerTest {
    private GreenSpaceController controller;
    private GreenSpaceRepository repository;
    private GreenSpace greenSpace;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        repository = new GreenSpaceRepository();
        controller = new GreenSpaceController(repository);
        Email email = new Email("admin@this.app");
        greenSpace = new GreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email);
    }

    /**
     * Add green space by name address area type success.
     */
    @Test
    void addGreenSpaceByNameAddressAreaType_success() {
        Email email = new Email("admin@this.app");
        controller.addGreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email);
        assertEquals(1, repository.getGreenSpaces().size());
    }

    /**
     * Add green space by name address area type duplicate name.
     */
    @Test
    void addGreenSpaceByNameAddressAreaType_duplicateName() {
        Email email = new Email("admin@this.app");
        controller.addGreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email);
        assertThrows(IllegalArgumentException.class, () -> controller.addGreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email));
    }

    /**
     * Add green space by name address area type duplicate address.
     */
    @Test
    void addGreenSpaceByNameAddressAreaType_duplicateAddress() {
        Email email = new Email("admin@this.app");
        controller.addGreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email);
        assertThrows(IllegalArgumentException.class, () -> controller.addGreenSpace("Park2", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email));
    }

    /**
     * Check if green space name exists true.
     */
    @Test
    void checkIfGreenSpaceNameExists_true() {
        controller.addGreenSpace(greenSpace);
        assertTrue(controller.checkIfGreenSpaceNameExists("Park"));
    }

    /**
     * Check if green space name exists false.
     */
    @Test
    void checkIfGreenSpaceNameExists_false() {
        assertFalse(controller.checkIfGreenSpaceNameExists("Park"));
    }

    /**
     * Check if green space address exists true.
     */
    @Test
    void checkIfGreenSpaceAddressExists_true() {
        controller.addGreenSpace(greenSpace);
        assertTrue(controller.checkIfGreenSpaceAddressExists(new Address("Street", "City", "Zip")));
    }

    /**
     * Check if green space address exists false.
     */
    @Test
    void checkIfGreenSpaceAddressExists_false() {
        assertFalse(controller.checkIfGreenSpaceAddressExists(new Address("Street", "City", "Zip")));
    }

    /**
     * Validate zip code valid.
     */
    @Test
    void validateZipCode_valid() {
        assertTrue(controller.validateZipCode("1234-555"));
    }

    /**
     * Validate zip code invalid.
     */
    @Test
    void validateZipCode_invalid() {
        assertFalse(controller.validateZipCode("1234a"));
    }


    /**
     * Test get collaborator repository.
     */
    @Test
    void testGetCollaboratorRepository() {
        CollaboratorRepository expected = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(expected);
        assertEquals(expected, controller.getCollaboratorRepository());
    }

    /**
     * Test get collaborator list.
     */
    @Test
    void testGetCollaboratorList() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));
        repository.add(collaborator);
        List<Collaborator> expected = List.of(collaborator);
        assertEquals(expected, controller.getCollaboratorList());
    }

    /**
     * Test register collaborator with skills.
     */
    @Test
    void testRegisterCollaboratorWithSkills() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        List<Skill> skills = List.of(new Skill("Java", "Code"));
        Collaborator collaborator = controller.registerCollaborator("John Doe", "john.doe@example.com", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), skills, new Date(), new Date(), "ID", 123456, 123456);
        assertEquals(skills, collaborator.getSkills());
    }

    /**
     * Test add green space.
     */
    @Test
    void testAddGreenSpace() {
        GreenSpace greenSpace = new GreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, new Email("admin@this.app"));
        controller.addGreenSpace(greenSpace);
        assertTrue(controller.checkIfGreenSpaceNameExists("Park"));
    }

    /**
     * Test remove green space.
     */
    @Test
    void testRemoveGreenSpace() {
        GreenSpace greenSpace = new GreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, new Email("admin@this.app"));
        controller.addGreenSpace(greenSpace);
        controller.removeGreenSpace(greenSpace);
        assertFalse(controller.checkIfGreenSpaceNameExists("Park"));
    }

    /**
     * Test update green space.
     */
    @Test
    void testUpdateGreenSpace() {
        GreenSpace oldGreenSpace = new GreenSpace("Old Park", new Address("Old Street", "Old City", "Old Zip"), 500.0, Type.GARDEN, new Email("admin@this.app"));
        GreenSpace newGreenSpace = new GreenSpace("New Park", new Address("New Street", "New City", "New Zip"), 500.0, Type.GARDEN, new Email("admin@this.app"));
        controller.addGreenSpace(oldGreenSpace);
        controller.updateGreenSpace(oldGreenSpace, newGreenSpace);
        assertFalse(controller.checkIfGreenSpaceNameExists("Old Park"));
        assertTrue(controller.checkIfGreenSpaceNameExists("New Park"));
    }

    /**
     * Test get green space list.
     */
    @Test
    void testGetGreenSpaceList() {
        GreenSpace greenSpace = new GreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, new Email("admin@this.app"));
        controller.addGreenSpace(greenSpace);
        List<GreenSpace> expected = List.of(greenSpace);
        assertEquals(expected, controller.getGreenSpaceList());
    }

    /**
     * Test check if green space name exists.
     */
    @Test
    void testCheckIfGreenSpaceNameExists() {
        assertFalse(controller.checkIfGreenSpaceNameExists("Nonexistent Park"));
    }

    /**
     * Test check if green space address exists.
     */
    @Test
    void testCheckIfGreenSpaceAddressExists() {
        assertFalse(controller.checkIfGreenSpaceAddressExists(new Address("Nonexistent Street", "Nonexistent City", "Nonexistent Zip")));
    }

    /**
     * Test get green spaces managed by user.
     */
    @Test
    void testGetGreenSpacesManagedByUser() {
        GreenSpace greenSpace = new GreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, new Email("admin@this.app"));
        controller.addGreenSpace(greenSpace);
        List<GreenSpace> expected = List.of(greenSpace);
        assertEquals(expected, controller.getGreenSpacesManagedByUser(new EmailWrapper(new Email("admin@this.app"))));
    }

    /**
     * Test validate zip code.
     */
    @Test
    void testValidateZipCode() {
        assertTrue(controller.validateZipCode("1234-567"));
        assertFalse(controller.validateZipCode("1234567"));
    }

}