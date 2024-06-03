package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Type;

import static org.junit.jupiter.api.Assertions.*;

class GreenSpaceTest {
    private GreenSpace greenSpace;

    @BeforeEach
    void setUp() {
        greenSpace = new GreenSpace("Park", new Address("maia", "porto", "1234-123"), 500.0f, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));
    }

    @Test
    void setName_success() {
        greenSpace.setName("Garden");
        assertEquals("Garden", greenSpace.getName());
    }

    @Test
    void setName_emptyName() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setName(""));
    }

    @Test
    void setType_success() {
        greenSpace.setType(Type.GARDEN);
        assertEquals(Type.GARDEN, greenSpace.getType());
    }

    @Test
    void setAddress_success() {
        greenSpace.setAddress(new Address("New Address", "New City", "1234-123"));
        assertEquals("New Address", greenSpace.getAddress());
    }

    @Test
    void setAddress_emptyAddress() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setAddress(new Address("", "", "")));
    }

    @Test
    void setArea_success() {
        greenSpace.setArea(600.0f);
        assertEquals(600.0f, greenSpace.getArea());
    }

    @Test
    void setArea_negativeArea() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setArea(-1.0f));
    }

    @Test
    void validatePark_success() {
        assertTrue(greenSpace.validatePark());
    }

    @Test
    void validatePark_emptyFields() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setName(""));
    }

    @Test
    void validatePark_negativeArea() {

        assertThrows(IllegalArgumentException.class, () -> greenSpace.setArea(-1.0f));
    }

    @Test
    void equals_sameObject() {
        assertTrue(greenSpace.equals(greenSpace));
    }

    @Test
    void equals_differentObject() {
        GreenSpace newGreenSpace = new GreenSpace("Garden", new Address("manga", "papai", "3456-789"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        assertFalse(greenSpace.equals(newGreenSpace));
    }
}