package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.ToDoListController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.ToDoList;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type To do list controller test.
 */
class ToDoListControllerTest {

    private ToDoListController toDoListController;
    private ToDoList toDoList;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        toDoList = new ToDoList();
        toDoListController = new ToDoListController(toDoList);

    }

    /**
     * Test get to be done entries.
     */
    @Test
    void testGetToBeDoneEntries() {
        Entry entry = new Entry(new GreenSpace("Green Channel", new Address("street", "porto", "1234-123"), 12345, Type.GARDEN, new Email("123@gmail.com")), "title", "desc", Urgency.MEDIUM, 5);
        toDoListController.addEntry(entry);
        List<Entry> expected = List.of(entry);

        List<Entry> result = toDoListController.getToBeDoneEntries();

        assertEquals(expected, result);
    }

    /**
     * Test get to be done entries when empty.
     */
    @Test
    void testGetToBeDoneEntriesWhenEmpty() {
        assertTrue(toDoListController.getToBeDoneEntries().isEmpty());
    }

    /**
     * Test add entry.
     */
    @Test
    void testAddEntry() {
        Entry entry = new Entry(new GreenSpace("Green Channel", new Address("street", "porto", "1234-123"), 12345, Type.GARDEN, new Email("123@gmail.com")), "title", "desc", Urgency.MEDIUM, 5);

        toDoListController.addEntry(entry);

        assertTrue(toDoListController.getEntries().contains(entry));
    }

    /**
     * Test get entries.
     */
    @Test
    void testGetEntries() {
        Entry entry = new Entry(new GreenSpace("Green Channel", new Address("street", "porto", "1234-123"), 12345, Type.GARDEN, new Email("123@gmail.com")), "title", "desc", Urgency.MEDIUM, 5);
        toDoListController.addEntry(entry);
        List<Entry> expected = List.of(entry);

        List<Entry> result = toDoListController.getEntries();

        assertEquals(expected, result);
    }

    /**
     * Test get entries when empty.
     */
    @Test
    void testGetEntriesWhenEmpty() {
        assertTrue(toDoListController.getEntries().isEmpty());
    }
}