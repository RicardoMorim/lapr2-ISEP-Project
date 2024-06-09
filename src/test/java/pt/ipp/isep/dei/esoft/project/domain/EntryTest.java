package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EntryTest {

    private GreenSpace greenSpace;
    private Entry entry;

    @BeforeEach
    public void setUp() {
        greenSpace = new GreenSpace("Park", new Address("123 Street", "City", "12345"), 100, Type.MEDIUM_SIZED_PARK, new Email("park@example.com"));
        entry = new Entry(greenSpace, "Title", "Description", Urgency.HIGH, 2.5f);
    }

    @Test
    public void testConstructor() {
        assertNotNull(entry);
        assertEquals(Status.TO_BE_DONE, entry.getState());
        assertEquals(greenSpace, entry.getGreenSpace());
        assertEquals("Title", entry.getTitle());
        assertEquals("Description", entry.getDescription());
        assertEquals(Urgency.HIGH, entry.getUrgency());
        assertEquals(2.5f, entry.getExpectedDuration());
    }

    @Test
    public void testGetSetState() {
        assertEquals(Status.TO_BE_DONE, entry.getState());
        entry.setState(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, entry.getState());
    }

    @Test
    public void testGetSetGreenSpace() {
        assertEquals(greenSpace, entry.getGreenSpace());
        GreenSpace newGreenSpace = new GreenSpace("Garden", new Address("456 Avenue", "Town", "67890"), 200, Type.GARDEN, new Email("garden@example.com"));
        entry.setGreenSpace(newGreenSpace);
        assertEquals(newGreenSpace, entry.getGreenSpace());
    }

    @Test
    public void testGetSetTitle() {
        assertEquals("Title", entry.getTitle());
        entry.setTitle("New Title");
        assertEquals("New Title", entry.getTitle());
    }

    @Test
    public void testGetSetDescription() {
        assertEquals("Description", entry.getDescription());
        entry.setDescription("New Description");
        assertEquals("New Description", entry.getDescription());
    }

    @Test
    public void testGetSetUrgency() {
        assertEquals(Urgency.HIGH, entry.getUrgency());
        entry.setUrgency(Urgency.LOW);
        assertEquals(Urgency.LOW, entry.getUrgency());
    }

    @Test
    public void testGetSetExpectedDuration() {
        assertEquals(2.5f, entry.getExpectedDuration());
        entry.setExpectedDuration(3.5f);
        assertEquals(3.5f, entry.getExpectedDuration());
    }

    @Test
    public void testToString() {
        String expected = "Title'Description'HIGH'2.5";
        assertEquals(expected, entry.toString());
    }
}
