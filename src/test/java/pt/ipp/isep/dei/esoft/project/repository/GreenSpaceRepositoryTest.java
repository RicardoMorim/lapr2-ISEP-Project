package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GreenSpaceRepositoryTest {
    private GreenSpaceRepository repository;
    private GreenSpace greenSpace;

    @BeforeEach
    void setUp() {
        repository = new GreenSpaceRepository();
        greenSpace = new GreenSpace("Park", "large-sized park", 500.0f);
    }

    @Test
    void addGreenSpace_success() {
        repository.addGreenSpace(greenSpace);
        assertEquals(1, repository.getGreenSpaces().size());
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
        assertEquals(0, repository.getGreenSpaces().size());
    }

    @Test
    void removeGreenSpace_doesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeGreenSpace(greenSpace));
    }

    @Test
    void updateGreenSpace_success() {
        repository.addGreenSpace(greenSpace);
        GreenSpace newGreenSpace = new GreenSpace("Garden", "medium-sized park", 300.0f);
        GreenSpace updatedGreenSpace = repository.updateGreenSpace(greenSpace, newGreenSpace);
        assertEquals(newGreenSpace, updatedGreenSpace);
    }

    @Test
    void updateGreenSpace_doesNotExist() {
        GreenSpace newGreenSpace = new GreenSpace("Garden", "medium-sized park", 300.0f);
        assertThrows(IllegalArgumentException.class, () -> repository.updateGreenSpace(greenSpace, newGreenSpace));
    }

    @Test
    void updateGreenSpaceWithDetails_success() {
        repository.addGreenSpace(greenSpace);
        Optional<GreenSpace> updatedGreenSpace = repository.updateGreenSpace(greenSpace, "Garden", "medium-sized park", 300.0f);
        assertTrue(updatedGreenSpace.isPresent());
        assertEquals("Garden", updatedGreenSpace.get().getName());
        assertEquals("medium-sized park", updatedGreenSpace.get().getType());
        assertEquals(300.0f, updatedGreenSpace.get().getArea());
    }

    @Test
    void updateGreenSpaceWithDetails_doesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> repository.updateGreenSpace(greenSpace, "Garden", "medium-sized park", 300.0f));
    }
}