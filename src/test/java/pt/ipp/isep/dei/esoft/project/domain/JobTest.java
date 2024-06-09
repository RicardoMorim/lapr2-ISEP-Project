package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Job test.
 */
class JobTest {

    /**
     * Gets name returns correct name.
     */
    @Test
    void getNameReturnsCorrectName() {
        Job job = new Job("Developer", "Writes code");
        assertEquals("Developer", job.getName());
    }


    /**
     * Gets short description returns correct short description.
     */
    @Test
    void getShortDescriptionReturnsCorrectShortDescription() {
        Job job = new Job("Developer", "Writes code");
        assertEquals("Writes code", job.getShortDescription());
    }

    /**
     * Sets name updates name.
     */
    @Test
    void setNameUpdatesName() {
        Job job = new Job("Developer", "Writes code");
        job.setName("Designer");
        assertEquals("Designer", job.getName());
    }

    /**
     * Sets short description updates short description.
     */
    @Test
    void setShortDescriptionUpdatesShortDescription() {
        Job job = new Job("Developer", "Writes code");
        job.setShortDescription("Designs interfaces");
        assertEquals("Designs interfaces", job.getShortDescription());
    }


    /**
     * Equals returns true for same object.
     */
    @Test
    void equalsReturnsTrueForSameObject() {
        Job job = new Job("Developer", "Writes code");
        assertTrue(job.equals(job));
    }

    /**
     * Equals returns false for null.
     */
    @Test
    void equalsReturnsFalseForNull() {
        Job job = new Job("Developer", "Writes code");
        assertFalse(job.equals(null));
    }

    /**
     * Equals returns false for different class.
     */
    @Test
    void equalsReturnsFalseForDifferentClass() {
        Job job = new Job("Developer", "Writes code");
        assertFalse(job.equals("Developer"));
    }

    /**
     * Equals returns true for same values.
     */
    @Test
    void equalsReturnsTrueForSameValues() {
        Job job1 = new Job("Developer", "Writes code");
        Job job2 = new Job("Developer", "Writes code");
        assertTrue(job1.equals(job2));
    }

    /**
     * Hash code returns same hash code for same values.
     */
    @Test
    void hashCodeReturnsSameHashCodeForSameValues() {
        Job job1 = new Job("Developer", "Writes code");
        Job job2 = new Job("Developer", "Writes code");
        assertEquals(job1.hashCode(), job2.hashCode());
    }
}