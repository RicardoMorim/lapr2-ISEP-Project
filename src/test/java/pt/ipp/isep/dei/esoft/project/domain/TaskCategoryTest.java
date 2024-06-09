package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Task category test.
 */
class TaskCategoryTest {

    /**
     * Test equals same object.
     */
//Tests for equals and hashcode
    @Test
    void testEqualsSameObject() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        assertEquals(taskCategory, taskCategory);
    }

    /**
     * Test equals different class.
     */
    @Test
    void testEqualsDifferentClass() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        assertNotEquals("", taskCategory);
    }

    /**
     * Test equals null.
     */
    @Test
    void testEqualsNull() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        assertNotEquals(null, taskCategory);
    }

    /**
     * Test equals different object.
     */
    @Test
    void testEqualsDifferentObject() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        TaskCategory taskCategory1 = new TaskCategory("Task Category Description 1");
        assertNotEquals(taskCategory, taskCategory1);
    }

    /**
     * Test equals same object different description.
     */
    @Test
    void testEqualsSameObjectDifferentDescription() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        TaskCategory taskCategory1 = new TaskCategory("Task Category Description 1");
        assertNotEquals(taskCategory, taskCategory1);
    }

    /**
     * Test equals same object same description.
     */
    @Test
    void testEqualsSameObjectSameDescription() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        TaskCategory taskCategory1 = new TaskCategory("Task Category Description");
        assertEquals(taskCategory, taskCategory1);
    }

    /**
     * Test hash code same object.
     */
    @Test
    void testHashCodeSameObject() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        assertEquals(taskCategory.hashCode(), taskCategory.hashCode());
    }

    /**
     * Test hash code different object.
     */
    @Test
    void testHashCodeDifferentObject() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        TaskCategory taskCategory1 = new TaskCategory("Task Category Description 1");
        assertNotEquals(taskCategory.hashCode(), taskCategory1.hashCode());
    }

    /**
     * Test hash code same object different description.
     */
    @Test
    void testHashCodeSameObjectDifferentDescription() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        TaskCategory taskCategory1 = new TaskCategory("Task Category Description 1");
        assertNotEquals(taskCategory.hashCode(), taskCategory1.hashCode());
    }

    /**
     * Test hash code same object same description.
     */
    @Test
    void testHashCodeSameObjectSameDescription() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        TaskCategory taskCategory1 = new TaskCategory("Task Category Description");
        assertEquals(taskCategory.hashCode(), taskCategory1.hashCode());
    }

    /**
     * Test equals for different object type.
     */
    @Test
    void testEqualsForDifferentObjectType() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        assertNotEquals(taskCategory, new Object());
    }

    /**
     * Ensure get description works.
     */
    @Test
    void ensureGetDescriptionWorks() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");

        assertEquals("Task Category Description", taskCategory.getDescription());
    }

    /**
     * Ensure clone works.
     */
    @Test
    void ensureCloneWorks() {
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        TaskCategory clone = taskCategory.clone();
        assertEquals(taskCategory, clone);
    }
}