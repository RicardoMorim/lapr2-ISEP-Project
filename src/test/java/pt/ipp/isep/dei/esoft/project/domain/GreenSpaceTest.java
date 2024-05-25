package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GreenSpaceTest {

    @Test
    public void shouldReturnCorrectName() {
        GreenSpace greenSpace = new GreenSpace("Park", "Public", 100.0f, new Email("admin@this.app"));
        assertEquals("Park", greenSpace.getName());
    }

    @Test
    public void shouldReturnCorrectType() {
        GreenSpace greenSpace = new GreenSpace("Park", "Public", 100.0f, new Email("admin@this.app"));
        assertEquals("Public", greenSpace.getType());
    }

    @Test
    public void shouldReturnCorrectArea() {
        GreenSpace greenSpace = new GreenSpace("Park", "Public", 100.0f, new Email("admin@this.app"));
        assertEquals(100.0f, greenSpace.getArea());
    }

    @Test
    public void shouldUpdateName() {
        GreenSpace greenSpace = new GreenSpace("Park", "Public", 100.0f, new Email("admin@this.app"));
        greenSpace.setName("New Park");
        assertEquals("New Park", greenSpace.getName());
    }

    @Test
    public void shouldUpdateType() {
        GreenSpace greenSpace = new GreenSpace("Park", "Public", 100.0f, new Email("admin@this.app"));
        greenSpace.setType("Private");
        assertEquals("Private", greenSpace.getType());
    }

    @Test
    public void shouldUpdateArea() {
        GreenSpace greenSpace = new GreenSpace("Park", "Public", 100.0f, new Email("admin@this.app"));
        greenSpace.setArea(200.0f);
        assertEquals(200.0f, greenSpace.getArea());
    }

    @Test
    public void shouldThrowExceptionWhenAreaIsNegative() {
        GreenSpace greenSpace = new GreenSpace("Park", "Public", 100.0f, new Email("admin@this.app"));
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setArea(-10.0f));
    }

    @Test
    public void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new GreenSpace("", "Public", 100.0f, new Email("admin@this.app")));
    }

    @Test
    public void shouldThrowExceptionWhenTypeIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new GreenSpace("Park", "", 100.0f, new Email("admin@this.app")));
    }

    @Test
    public void shouldThrowExceptionWhenAreaIsZero() {
        assertThrows(IllegalArgumentException.class, () -> new GreenSpace("Park", "Public", 0.0f, new Email("admin@this.app")));
    }
}