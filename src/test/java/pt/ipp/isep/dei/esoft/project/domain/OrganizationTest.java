package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Organization test.
 */
class OrganizationTest {

    /**
     * Test equals same object.
     */
    @Test
    void testEqualsSameObject() {
        Organization organization = new Organization("123456789");
        assertEquals(organization, organization);
    }

    /**
     * Test equals different class.
     */
    @Test
    void testEqualsDifferentClass() {
        Organization organization = new Organization("123456789");
        assertNotEquals("", organization);
    }

    /**
     * Test equals null.
     */
    @Test
    void testEqualsNull() {
        Organization organization = new Organization("123456789");
        assertNotEquals(null, organization);
    }

    /**
     * Test equals different object.
     */
    @Test
    void testEqualsDifferentObject() {
        Organization organization = new Organization("123456789");
        Organization organization1 = new Organization("123456789");
        assertEquals(organization, organization1);
    }

    /**
     * Test hash code same object.
     */
    @Test
    void testHashCodeSameObject() {
        Organization organization = new Organization("123456789");
        assertEquals(organization.hashCode(), organization.hashCode());
    }

    /**
     * Test hash code different object.
     */
    @Test
    void testHashCodeDifferentObject() {
        Organization organization = new Organization("123456789");
        Organization organization1 = new Organization("123456789");
        assertEquals(organization.hashCode(), organization1.hashCode());
    }

    /**
     * Test hash code same object same vat number.
     */
    @Test
        //same hashcode
    void testHashCodeSameObjectSameVATNumber() {
        Organization organization = new Organization("123456789");
        Organization organization1 = new Organization("123456789");
        assertEquals(organization.hashCode(), organization1.hashCode());
    }

    /**
     * Ensure hash code fails for different vat numbers.
     */
    @Test
    void ensureHashCodeFailsForDifferentVatNumbers() {
        Organization organization = new Organization("123456789");
        Organization organization1 = new Organization("123456788");
        assertNotEquals(organization.hashCode(), organization1.hashCode());
    }

    /**
     * Ensure equals fails for different object type.
     */
    @Test
    void ensureEqualsFailsForDifferentObjectType() {
        Organization organization = new Organization("123456789");
        Organization organization1 = new Organization("123456788");
        assertNotEquals(organization, organization1);
    }

    /**
     * Ensure equals fails when comparing null.
     */
    @Test
    void ensureEqualsFailsWhenComparingNull() {
        Organization organization = new Organization("123456789");
        assertNotEquals(organization, null);
    }

    /**
     * Ensure equals success when comparing same object.
     */
    @Test
    void ensureEqualsSuccessWhenComparingSameObject() {
        Organization organization = new Organization("123456789");
        assertEquals(organization, organization);
    }

    /**
     * Test that create task works.
     */
    @Test
    void testThatCreateTaskWorks() {
        Organization organization = new Organization("123456789");

        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");

        Task expected = new Task("Task Description", "Task Category Description", "informal description",
                "technical description", 1, 1d, taskCategory, employee);

        Optional<Task> task =
                organization.createTask("Task Description", "Task Category Description", "informal description",
                        "technical description", 1, 1d, taskCategory, employee);

        assertNotNull(task);
        assertTrue(task.isPresent());
        assertEquals(expected, task.get());
    }

    /**
     * Ensure adding duplicate task fails.
     */
    @Test
    void ensureAddingDuplicateTaskFails() {
        //Arrange
        Organization organization = new Organization("123456789");
        Employee employee = new Employee("john.doe@this.company.com");
        TaskCategory taskCategory = new TaskCategory("Task Category Description");
        //Add the first task
        Optional<Task> originalTask =
                organization.createTask("Task Description", "Task Category Description", "informal description",
                        "technical description", 1, 1d, taskCategory, employee);

        //Act
        Optional<Task> duplicateTask =
                organization.createTask("Task Description", "Task Category Description", "informal description",
                        "technical description", 1, 1d, taskCategory, employee);

        //Assert
        assertTrue(duplicateTask.isEmpty());
    }


    /**
     * Ensure employs fails.
     */
    @Test
    void ensureEmploysFails() {
        Organization organization = new Organization("123456789");
        Employee employee = new Employee("john.doe@this.company.com");

        assertFalse(organization.employs(employee));

    }

    /**
     * Ensure employs success.
     */
    @Test
    void ensureEmploysSuccess() {
        Organization organization = new Organization("123456789");
        Employee employee = new Employee("john.doe@this.company.com");
        organization.addEmployee(employee);
        assertTrue(organization.employs(employee));
    }

    /**
     * Ensure any employee has email fails.
     */
    @Test
    void ensureAnyEmployeeHasEmailFails() {
        Organization organization = new Organization("123456789");
        Employee employee = new Employee("john.doe@this.company.com");
        organization.addEmployee(employee);
        assertFalse(organization.anyEmployeeHasEmail("jane.doe@this.company.com"));
    }

    /**
     * Ensure any employee has email works.
     */
    @Test
    void ensureAnyEmployeeHasEmailWorks() {
        Organization organization = new Organization("123456789");
        Employee employee = new Employee("john.doe@this.company.com");
        organization.addEmployee(employee);
        assertTrue(organization.anyEmployeeHasEmail("john.doe@this.company.com"));
    }

    /**
     * Ensure add duplicate employee fails.
     */
    @Test
    void ensureAddDuplicateEmployeeFails() {
        Organization organization = new Organization("123456789");
        Employee employee = new Employee("john.doe@this.company.com");
        assertTrue(organization.addEmployee(employee));
        assertFalse(organization.addEmployee(employee));
    }

    /**
     * Ensure add employee works.
     */
    @Test
    void ensureAddEmployeeWorks() {
        Organization organization = new Organization("123456789");
        Employee employee = new Employee("john.doe@this.company.com");
        assertTrue(organization.addEmployee(employee));
    }

    /**
     * Ensure clone works.
     */
    @Test
    void ensureCloneWorks() {
        Organization organization = new Organization("123456789");
        Employee employee = new Employee("john.doe@this.company.com");
        organization.addEmployee(employee);
        organization.createTask("Task Description", "Task Category Description", "informal description",
                "technical description", 1, 1d, new TaskCategory("Task Category Description"), employee);

        Organization clone = organization.clone();
        assertEquals(organization, clone);
    }
}