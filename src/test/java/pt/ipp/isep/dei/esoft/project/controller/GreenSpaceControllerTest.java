package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.Address;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Type;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;
import pt.isep.lei.esoft.auth.domain.model.Email;

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
}