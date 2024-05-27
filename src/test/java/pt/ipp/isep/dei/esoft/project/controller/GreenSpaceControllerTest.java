package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Type;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
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
        greenSpace = new GreenSpace("Park", "large-sized park", 500.0f, Type.GARDEN, new Email("admin@this.app"));
    }

    @Test
    void addGreenSpace_success() {
        controller.addGreenSpace(greenSpace);
        assertEquals(1, repository.getGreenSpaces().size());
    }

    @Test
    void getGreenSpaceList_success() {
        List<GreenSpace> greenSpaces = new ArrayList<>();
        greenSpaces.add(greenSpace);
        repository.addGreenSpace(greenSpace);

        List<GreenSpace> result = controller.getGreenSpaceList();

        assertEquals(greenSpaces, result);
    }

    @Test
    void removeGreenSpace_success() {
        repository.addGreenSpace(greenSpace);
        controller.removeGreenSpace(greenSpace);
        assertEquals(0, repository.getGreenSpaces().size());
    }

    @Test
    void updateGreenSpace_success() {
        GreenSpace newGreenSpace = new GreenSpace("Garden",  "Rua almeida valente",300.0f, Type.GARDEN, new Email("admin@this.app") );
        repository.addGreenSpace(greenSpace);
        controller.updateGreenSpace(greenSpace, newGreenSpace);
        assertEquals(newGreenSpace, repository.getGreenSpaces().get(0));
    }
}