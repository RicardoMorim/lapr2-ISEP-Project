package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkillTest {

    @Test
    void getNameReturnsCorrectName() {
        Skill skill = new Skill("Java", "Code");
        assertEquals("Java", skill.getName());
    }


    @Test
    void getShortDescriptionReturnsCorrectShortDescription() {
        Skill skill = new Skill("Java", "Short Description");
        assertEquals("Short Description", skill.getShortDescription());
    }

    @Test
    void getSkillValuesReturnsCorrectValues() {
        Skill skill = new Skill("Java", "Short Description");
        List<String> expectedValues = Arrays.asList("Java", "Short Description");
        assertEquals(expectedValues, skill.getSkillValues());
    }

    @Test
    void setNameUpdatesName() {
        Skill skill = new Skill("java", "Code");
        skill.setName("Python");
        assertEquals("Python", skill.getName());
    }

    @Test
    void setShortDescriptionUpdatesShortDescription() {
        Skill skill = new Skill("Java", "Short Description");
        skill.setShortDescription("Updated Short Description");
        assertEquals("Updated Short Description", skill.getShortDescription());
    }


    @Test
    void equalsReturnsTrueForSameObject() {
        Skill skill = new Skill("java", "Code");
        assertTrue(skill.equals(skill));
    }

    @Test
    void equalsReturnsFalseForNull() {
        Skill skill = new Skill("java", "Code");
        assertFalse(skill.equals(null));
    }


    @Test
    void equalsReturnsTrueForSameValues() {
        Skill skill1 = new Skill("Java", "Short Description");
        Skill skill2 = new Skill("Java", "Short Description");
        assertTrue(skill1.equals(skill2));
    }

    @Test
    void hashCodeReturnsSameHashCodeForSameValues() {
        Skill skill1 = new Skill("Java", "Short Description");
        Skill skill2 = new Skill("Java", "Short Description");
        assertEquals(skill1.hashCode(), skill2.hashCode());
    }
}