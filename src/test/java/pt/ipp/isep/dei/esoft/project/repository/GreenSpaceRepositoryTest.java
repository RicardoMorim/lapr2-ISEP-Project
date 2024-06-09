package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Address;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Type;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Green space repository test.
 */
class GreenSpaceRepositoryTest {
    private GreenSpaceRepository repository;
    private GreenSpace greenSpace;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        repository = new GreenSpaceRepository(new ArrayList<>());
        greenSpace = new GreenSpace("Park", new Address("mingas", "isep", "6666-666"), 500.0f, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));
    }

    /**
     * Add green space success.
     */
    @Test
    void addGreenSpace_success() {
        repository.addGreenSpace(greenSpace);
        List<GreenSpace> result = repository.getGreenSpaces();
        assertTrue(result.contains(greenSpace));
    }

    /**
     * Add green space already exists.
     */
    @Test
    void addGreenSpace_alreadyExists() {
        repository.addGreenSpace(greenSpace);
        assertThrows(IllegalArgumentException.class, () -> repository.addGreenSpace(greenSpace));
    }

    /**
     * Remove green space success.
     */
    @Test
    void removeGreenSpace_success() {
        repository.addGreenSpace(greenSpace);
        repository.removeGreenSpace(greenSpace);
        List<GreenSpace> result = repository.getGreenSpaces();
        assertFalse(result.contains(greenSpace));
    }

    /**
     * Remove green space does not exist.
     */
    @Test
    void removeGreenSpace_doesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeGreenSpace(greenSpace));
    }

    /**
     * Update green space success.
     */
    @Test
    void updateGreenSpace_success() {
        repository.addGreenSpace(greenSpace);
        GreenSpace newGreenSpace = new GreenSpace("Garden", new Address("02", "sonso", "2222-000"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        repository.updateGreenSpace(greenSpace, newGreenSpace);
        List<GreenSpace> result = repository.getGreenSpaces();
        assertTrue(result.contains(newGreenSpace));
        assertFalse(result.contains(greenSpace));
    }

    /**
     * Update green space does not exist.
     */
    @Test
    void updateGreenSpace_doesNotExist() {
        GreenSpace newGreenSpace = new GreenSpace("Garden", new Address("rafa", "el", "9595-095"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        assertThrows(IllegalArgumentException.class, () -> repository.updateGreenSpace(greenSpace, newGreenSpace));
    }

    /**
     * Gets green space by designation success.
     */
    @Test
    void getGreenSpaceByDesignation_success() {
        repository.addGreenSpace(greenSpace);
        GreenSpace result = repository.getGreenSpaceByDesignation("Park");
        assertEquals(greenSpace, result);
    }

    /**
     * Gets green space by designation not found.
     */
    @Test
    void getGreenSpaceByDesignation_notFound() {
        assertThrows(IllegalArgumentException.class, () -> repository.getGreenSpaceByDesignation("Park"));
    }

    /**
     * Gets green spaces empty repository.
     */
    @Test
    void getGreenSpaces_emptyRepository() {
        assertTrue(repository.getGreenSpaces().isEmpty());
    }

    /**
     * Add green space null green space.
     */
    @Test
    void addGreenSpace_nullGreenSpace() {
        assertThrows(IllegalArgumentException.class, () -> repository.addGreenSpace(null));
    }

    /**
     * Remove green space null green space.
     */
    @Test
    void removeGreenSpace_nullGreenSpace() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeGreenSpace(null));
    }

    /**
     * Update green space null green space.
     */
    @Test
    void updateGreenSpace_nullGreenSpace() {
        assertThrows(IllegalArgumentException.class, () -> repository.updateGreenSpace(null, greenSpace));
    }

    /**
     * Gets green space by designation non existing designation.
     */
    @Test
    void getGreenSpaceByDesignation_nonExistingDesignation() {
        assertThrows(IllegalArgumentException.class, () -> repository.getGreenSpaceByDesignation("NonExistingPark"));
    }

    /**
     * Gets green space by designation null designation.
     */
    @Test
    void getGreenSpaceByDesignation_nullDesignation() {
        assertThrows(IllegalArgumentException.class, () -> repository.getGreenSpaceByDesignation(null));
    }
}