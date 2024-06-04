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

class GreenSpaceRepositoryTest {
    private GreenSpaceRepository repository;
    private GreenSpace greenSpace;

    @BeforeEach
    void setUp() {
        repository = new GreenSpaceRepository(new ArrayList<>());
        greenSpace = new GreenSpace("Park", new Address("mingas", "isep", "6666-666"), 500.0f, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));
    }

    @Test
    void addGreenSpace_success() {
        repository.addGreenSpace(greenSpace);
        List<GreenSpace> result = repository.getGreenSpaces();
        assertTrue(result.contains(greenSpace));
    }

    @Test
    void addGreenSpace_alreadyExists() {
        repository.addGreenSpace(greenSpace);
        assertThrows(IllegalArgumentException.class, () -> repository.addGreenSpace(greenSpace));
    }

    @Test
    void removeGreenSpace_success() {
        repository.addGreenSpace(greenSpace);
        repository.removeGreenSpace(greenSpace);
        List<GreenSpace> result = repository.getGreenSpaces();
        assertFalse(result.contains(greenSpace));
    }

    @Test
    void removeGreenSpace_doesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeGreenSpace(greenSpace));
    }

    @Test
    void updateGreenSpace_success() {
        repository.addGreenSpace(greenSpace);
        GreenSpace newGreenSpace = new GreenSpace("Garden", new Address("02", "sonso", "2222-000"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        repository.updateGreenSpace(greenSpace, newGreenSpace);
        List<GreenSpace> result = repository.getGreenSpaces();
        assertTrue(result.contains(newGreenSpace));
        assertFalse(result.contains(greenSpace));
    }

    @Test
    void updateGreenSpace_doesNotExist() {
        GreenSpace newGreenSpace = new GreenSpace("Garden", new Address("rafa", "el", "9595-095"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        assertThrows(IllegalArgumentException.class, () -> repository.updateGreenSpace(greenSpace, newGreenSpace));
    }

    @Test
    void getGreenSpaceByDesignation_success() {
        repository.addGreenSpace(greenSpace);
        GreenSpace result = repository.getGreenSpaceByDesignation("Park");
        assertEquals(greenSpace, result);
    }

    @Test
    void getGreenSpaceByDesignation_notFound() {
        assertThrows(IllegalArgumentException.class, () -> repository.getGreenSpaceByDesignation("Park"));
    }
}