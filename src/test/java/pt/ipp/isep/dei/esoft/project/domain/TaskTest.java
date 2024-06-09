package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Task test.
 */
class TaskTest {

    /**
     * Ensure task is created successfully.
     */
    @Test
    void ensureTaskIsCreatedSuccessfully() {
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);
    }

    /**
     * Ensure task reference is not null.
     */
    @Test
    void ensureTaskReferenceIsNotNull() {
        //Arrange
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");

        //Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Task(null, "description", "informal description", "technical description", 1, 1d,
                        taskCategory, employee));
    }

    /**
     * Test equals same object.
     */
    @Test
    void testEqualsSameObject() {
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);

        assertEquals(task, task);
    }

    /**
     * Test equals different class.
     */
    @Test
    void testEqualsDifferentClass() {
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);

        assertNotEquals(task, new Object());
    }

    /**
     * Test equals null.
     */
    @Test
    void testEqualsNull() {
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);

        assertNotEquals(task, null);
    }

    /**
     * Test equals different object.
     */
    @Test
    void testEqualsDifferentObject() {
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);
        Task task1 = new Task("reference1", "description1", "informal description1", "technical description1", 2, 2d,
                taskCategory, employee);

        assertNotEquals(task, task1);
    }

    /**
     * Test equals same object different description.
     */
    @Test
    void testEqualsSameObjectDifferentDescription() {
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);
        Task task1 = new Task("reference1", "description", "informal description1", "technical description1", 2, 2d,
                taskCategory, employee);

        assertNotEquals(task, task1);
    }

    /**
     * Test equals same object same description.
     */
    @Test
    void testEqualsSameObjectSameDescription() {
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);
        Task task1 = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);

        assertEquals(task, task1);
    }

    /**
     * Test hash code same object.
     */
    @Test
    void testHashCodeSameObject() {
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);

        assertEquals(task.hashCode(), task.hashCode());
    }

    /**
     * Test hash code different object.
     */
    @Test
    void testHashCodeDifferentObject() {
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);
        Task task1 = new Task("reference1", "description1", "informal description1", "technical description1", 2, 2d,
                taskCategory, employee);

        assertNotEquals(task.hashCode(), task1.hashCode());
    }

    /**
     * Ensure clone works.
     */
    @Test
    void ensureCloneWorks() {
        Employee employee = new Employee("john.doe@this.company.org");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        Task task = new Task("reference", "description", "informal description", "technical description", 1, 1d,
                taskCategory, employee);
        Task clone = task.clone();
        assertEquals(task, clone);
    }
}