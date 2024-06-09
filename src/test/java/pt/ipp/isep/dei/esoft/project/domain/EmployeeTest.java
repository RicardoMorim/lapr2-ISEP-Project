package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Employee test.
 */
class EmployeeTest {

    /**
     * Ensure two employees with same email equals.
     */
    @Test
    void ensureTwoEmployeesWithSameEmailEquals() {
        Employee employee1 = new Employee("john.doe@this.company.com");
        Employee employee2 = new Employee("john.doe@this.company.com");
        assertEquals(employee1, employee2);
    }

    /**
     * Ensure employee with different email not equals.
     */
    @Test
    void ensureEmployeeWithDifferentEmailNotEquals() {
        Employee employee1 = new Employee("john.doe@this.company.com");
        Employee employee2 = new Employee("jane.doe@this.company.com");
        assertNotEquals(employee1, employee2);
    }

    /**
     * Ensure employee does not equal null.
     */
    @Test
    void ensureEmployeeDoesNotEqualNull() {
        Employee employee1 = new Employee("john.doe@this.company.com");
        assertNotEquals(employee1, null);
    }

    /**
     * Ensure employee does not equal other object.
     */
    @Test
    void ensureEmployeeDoesNotEqualOtherObject() {
        Employee employee1 = new Employee("john.doe@this.company.com");
        assertNotEquals(employee1, new Object());
    }

    /**
     * Ensure the same object is equal.
     */
    @Test
    void ensureTheSameObjectIsEqual() {
        Employee employee1 = new Employee("john.doe@this.company.com");
        assertEquals(employee1, employee1);
    }

    /**
     * Ensure hash code is equal for equal objects.
     */
    @Test
    void ensureHashCodeIsEqualForEqualObjects() {
        String email = "john.doe@this.company.com";
        Employee employee1 = new Employee(email);
        Employee employee2 = new Employee(email);
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    /**
     * Ensure hash code is not equal for different objects.
     */
    @Test
    void ensureHashCodeIsNotEqualForDifferentObjects() {

        Employee employee1 = new Employee("john.doe@this.company.com");
        Employee employee2 = new Employee("jane.doe@this.company.com");
        assertNotEquals(employee1.hashCode(), employee2.hashCode());
    }

    /**
     * Ensure has email works for the same email.
     */
    @Test
    void ensureHasEmailWorksForTheSameEmail() {
        String email = "john.doe@this.compay.org";
        Employee employee = new Employee(email);
        assertTrue(employee.hasEmail(email));

    }

    /**
     * Ensure has email fails for different emails.
     */
    @Test
    void ensureHasEmailFailsForDifferentEmails() {
        String email = "john.doe@this.company.com";
        Employee employee = new Employee(email);
        assertFalse(employee.hasEmail("jane.doe@this.company.com"));

    }

    /**
     * Ensure clone works.
     */
    @Test
    void ensureCloneWorks() {
        String email = "john.doe@this.company.com";
        Employee employee = new Employee(email);
        Employee clone = employee.clone();
        assertEquals(employee, clone);
    }
}