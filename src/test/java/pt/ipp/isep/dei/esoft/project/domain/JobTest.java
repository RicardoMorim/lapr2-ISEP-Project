package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobTest {

    @Test
    void getNameReturnsCorrectName() {
        Job job = new Job("Developer", "Writes code");
        assertEquals("Developer", job.getName());
    }


    @Test
    void getShortDescriptionReturnsCorrectShortDescription() {
        Job job = new Job("Developer", "Writes code");
        assertEquals("Writes code", job.getShortDescription());
    }

    @Test
    void setNameUpdatesName() {
        Job job = new Job("Developer", "Writes code");
        job.setName("Designer");
        assertEquals("Designer", job.getName());
    }

    @Test
    void setShortDescriptionUpdatesShortDescription() {
        Job job = new Job("Developer", "Writes code");
        job.setShortDescription("Designs interfaces");
        assertEquals("Designs interfaces", job.getShortDescription());
    }


    @Test
    void equalsReturnsTrueForSameObject() {
        Job job = new Job("Developer", "Writes code");
        assertTrue(job.equals(job));
    }

    @Test
    void equalsReturnsFalseForNull() {
        Job job = new Job("Developer", "Writes code");
        assertFalse(job.equals(null));
    }

    @Test
    void equalsReturnsFalseForDifferentClass() {
        Job job = new Job("Developer", "Writes code");
        assertFalse(job.equals("Developer"));
    }

    @Test
    void equalsReturnsTrueForSameValues() {
        Job job1 = new Job("Developer", "Writes code");
        Job job2 = new Job("Developer", "Writes code");
        assertTrue(job1.equals(job2));
    }

    @Test
    void hashCodeReturnsSameHashCodeForSameValues() {
        Job job1 = new Job("Developer", "Writes code");
        Job job2 = new Job("Developer", "Writes code");
        assertEquals(job1.hashCode(), job2.hashCode());
    }
}