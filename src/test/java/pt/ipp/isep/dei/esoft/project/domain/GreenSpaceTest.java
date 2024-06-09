package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;
import java.util.List;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Green space test.
 */
class GreenSpaceTest {
    private GreenSpace greenSpace;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        greenSpace = new GreenSpace("Park", new Address("maia", "porto", "1234-123"), 500.0f, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));
    }

    /**
     * Sets name success.
     */
    @Test
    void setName_success() {
        greenSpace.setName("Garden");
        assertEquals("Garden", greenSpace.getName());
    }

    /**
     * Sets name empty name.
     */
    @Test
    void setName_emptyName() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setName(""));
    }

    /**
     * Sets type success.
     */
    @Test
    void setType_success() {
        greenSpace.setType(Type.GARDEN);
        assertEquals(Type.GARDEN, greenSpace.getType());
    }

    /**
     * Sets address success.
     */
    @Test
    void setAddress_success() {
        greenSpace.setAddress(new Address("New Address", "New City", "1234-123"));
        assertEquals("New Address 1234-123 New City", greenSpace.getAddress().toString());
    }

    /**
     * Sets address empty address.
     */
    @Test
    void setAddress_emptyAddress() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setAddress(new Address("", "", "")));
    }

    /**
     * Sets area success.
     */
    @Test
    void setArea_success() {
        greenSpace.setArea(600.0f);
        assertEquals(600.0f, greenSpace.getArea());
    }

    /**
     * Sets area negative area.
     */
    @Test
    void setArea_negativeArea() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setArea(-1.0f));
    }

    /**
     * Validate park success.
     */
    @Test
    void validatePark_success() {
        assertTrue(greenSpace.validatePark());
    }

    /**
     * Validate park empty fields.
     */
    @Test
    void validatePark_emptyFields() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setName(""));
    }

    /**
     * Validate park negative area.
     */
    @Test
    void validatePark_negativeArea() {

        assertThrows(IllegalArgumentException.class, () -> greenSpace.setArea(-1.0f));
    }

    /**
     * Equals same object.
     */
    @Test
    void equals_sameObject() {
        assertTrue(greenSpace.equals(greenSpace));
    }

    /**
     * Equals different object.
     */
    @Test
    void equals_differentObject() {
        GreenSpace newGreenSpace = new GreenSpace("Garden", new Address("manga", "papai", "3456-789"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        assertFalse(greenSpace.equals(newGreenSpace));
    }

    /**
     * Equals null object.
     */
    @Test
    void equals_nullObject() {
        assertFalse(greenSpace.equals(null));
    }

    /**
     * Equals different class.
     */
    @Test
    void equals_differentClass() {
        assertFalse(greenSpace.equals(new Object()));
    }

    /**
     * Equals same name different attributes.
     */
    @Test
    void equals_sameNameDifferentAttributes() {
        GreenSpace newGreenSpace = new GreenSpace("Park", new Address("manga", "papai", "3456-789"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        assertTrue(greenSpace.equals(newGreenSpace));
    }

    /**
     * Equals same address different attributes.
     */
    @Test
    void equals_sameAddressDifferentAttributes() {
        GreenSpace newGreenSpace = new GreenSpace("Garden", new Address("maia", "porto", "1234-123"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        assertTrue(greenSpace.equals(newGreenSpace));
    }

    /**
     * Hash code equal objects.
     */
    @Test
    void hashCode_equalObjects() {
        GreenSpace newGreenSpace = new GreenSpace("Park", new Address("maia", "porto", "1234-123"), 500.0f, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));
        assertEquals(greenSpace.hashCode(), newGreenSpace.hashCode());
    }

    /**
     * To string success.
     */
    @Test
    void toString_success() {
        assertEquals("Park Name = Park - Address = maia 1234-123 porto - Area = 500.0 - Type = LARGE_SIZED_PARK", greenSpace.toString());
    }

    /**
     * Gets park values success.
     */
    @Test
    void getParkValues_success() {
        List<String> expectedValues = Arrays.asList("Park", "500.0", "maia 1234-123 porto", "LARGE_SIZED_PARK");
        assertEquals(expectedValues, greenSpace.getParkValues());
    }

    /**
     * Gets user success.
     */
    @Test
    void getUser_success() {
        assertEquals(new EmailWrapper(new Email("admin@this.app")), greenSpace.getUser());
    }

    /**
     * Validate park empty address.
     */
    @Test
    void validatePark_emptyAddress() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setAddress(new Address("", "", "")));
    }

    /**
     * Validate park zero area.
     */
    @Test
    void validatePark_zeroArea() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setArea(0.0f));
    }


}
