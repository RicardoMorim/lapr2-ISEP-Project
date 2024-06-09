package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type To do list test.
 */
public class ToDoListTest {

    private ToDoList toDoList;
    private GreenSpace greenSpace;
    private Entry entry1;
    private Entry entry2;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        greenSpace = new GreenSpace("Park", new Address("123 Street", "City", "12345"), 100, Type.MEDIUM_SIZED_PARK, new Email("park@example.com"));
        entry1 = new Entry(greenSpace, "Title1", "Description1", Urgency.HIGH, 2.5f);
        entry2 = new Entry(greenSpace, "Title2", "Description2", Urgency.LOW, 3.5f);
        toDoList = new ToDoList();
    }

    /**
     * Test default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        ToDoList list = new ToDoList();
        assertNotNull(list.getEntries());
        assertTrue(list.getEntries().isEmpty());
    }

    /**
     * Test constructor with list.
     */
    @Test
    public void testConstructorWithList() {
        List<Entry> entries = new ArrayList<>();
        entries.add(entry1);
        ToDoList list = new ToDoList(entries);
        assertEquals(1, list.getEntries().size());
        assertEquals(entry1, list.getEntries().get(0));
    }

    /**
     * Test add entry.
     */
    @Test
    public void testAddEntry() {
        toDoList.addEntry(entry1);
        assertEquals(1, toDoList.getEntries().size());
        assertEquals(entry1, toDoList.getEntries().get(0));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            toDoList.addEntry(entry1);
        });
        assertEquals("Entry already in ToDo List", exception.getMessage());
    }

    /**
     * Test set entries.
     */
    @Test
    public void testSetEntries() {
        List<Entry> entries = new ArrayList<>();
        entries.add(entry1);
        toDoList.setEntries(entries);
        assertEquals(1, toDoList.getEntries().size());
        assertEquals(entry1, toDoList.getEntries().get(0));
    }

    /**
     * Test remove entry.
     */
    @Test
    public void testRemoveEntry() {
        toDoList.addEntry(entry1);
        toDoList.removeEntry(entry1);
        assertTrue(toDoList.getEntries().isEmpty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            toDoList.removeEntry(entry1);
        });
        assertEquals("Entry not in ToDo List", exception.getMessage());
    }

    /**
     * Test update entry.
     */
    @Test
    public void testUpdateEntry() {
        toDoList.addEntry(entry1);
        toDoList.updateEntry(entry1, entry2);
        assertEquals(1, toDoList.getEntries().size());
        assertEquals(entry2, toDoList.getEntries().get(0));
    }

    /**
     * Test get entries.
     */
    @Test
    public void testGetEntries() {
        List<Entry> entries = new ArrayList<>();
        entries.add(entry1);
        toDoList.setEntries(entries);
        assertEquals(entries, toDoList.getEntries());
    }

    /**
     * Test get to be done entries.
     */
    @Test
    public void testGetToBeDoneEntries() {
        entry1.setState(Status.TO_BE_DONE);
        entry2.setState(Status.IN_PROGRESS);
        toDoList.addEntry(entry1);
        toDoList.addEntry(entry2);

        List<Entry> toBeDoneEntries = toDoList.getToBeDoneEntries();
        assertEquals(1, toBeDoneEntries.size());
        assertEquals(entry1, toBeDoneEntries.get(0));
    }
}
