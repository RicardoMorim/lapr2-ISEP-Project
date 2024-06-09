package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Skill test.
 */
class SkillTest {

    /**
     * Gets name returns correct name.
     */
    @Test
    void getNameReturnsCorrectName() {
        Skill skill = new Skill("Java", "Code");
        assertEquals("Java", skill.getName());
    }


    /**
     * Gets short description returns correct description.
     */
    @Test
    void getShortDescriptionReturnsCorrectDescription() {
        Skill skill = new Skill("Java", "Short Description");
        assertEquals("Short Description", skill.getDescription());
    }

    /**
     * Gets skill values returns correct values.
     */
    @Test
    void getSkillValuesReturnsCorrectValues() {
        Skill skill = new Skill("Java", "Short Description");
        List<String> expectedValues = Arrays.asList("Java", "Short Description");
        assertEquals(expectedValues, skill.getSkillValues());
    }

    /**
     * Sets name updates name.
     */
    @Test
    void setNameUpdatesName() {
        Skill skill = new Skill("java", "Code");
        skill.setName("Python");
        assertEquals("Python", skill.getName());
    }

    /**
     * Sets short description updates short description.
     */
    @Test
    void setShortDescriptionUpdatesShortDescription() {
        Skill skill = new Skill("Java", "Short Description");
        skill.setShortDescription("Updated Short Description");
        assertEquals("Updated Short Description", skill.getDescription());
    }


    /**
     * Equals returns true for same object.
     */
    @Test
    void equalsReturnsTrueForSameObject() {
        Skill skill = new Skill("java", "Code");
        assertTrue(skill.equals(skill));
    }

    /**
     * Equals returns false for null.
     */
    @Test
    void equalsReturnsFalseForNull() {
        Skill skill = new Skill("java", "Code");
        assertFalse(skill.equals(null));
    }


    /**
     * Equals returns true for same values.
     */
    @Test
    void equalsReturnsTrueForSameValues() {
        Skill skill1 = new Skill("Java", "Short Description");
        Skill skill2 = new Skill("Java", "Short Description");
        assertTrue(skill1.equals(skill2));
    }

    /**
     * Hash code returns same hash code for same values.
     */
    @Test
    void hashCodeReturnsSameHashCodeForSameValues() {
        Skill skill1 = new Skill("Java", "Short Description");
        Skill skill2 = new Skill("Java", "Short Description");
        assertEquals(skill1.hashCode(), skill2.hashCode());
    }
}