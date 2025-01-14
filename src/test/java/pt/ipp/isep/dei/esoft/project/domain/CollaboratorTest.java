package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Collaborator test.
 */
class CollaboratorTest {

    /**
     * Add skill.
     */
    @Test
    void addSkill() {
        Job job = new Job("jardineiro", "jardineiro");
        Collaborator col = new Collaborator("123@gmail.com", "Ricardo", new Address("456 Street", "Porto", "123-456"), "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        Skill skill = new Skill("Carta A", "pode conduzir motas");
        List<Skill> newSkills = col.addSkill(skill);
        Skill addedSkill = newSkills.get(0);
        Skill colaboratorSkill = col.getSkills().get(0);
        assertEquals(skill, addedSkill);
        assertEquals(skill, colaboratorSkill);
    }

    /**
     * Remove skill test with skills added through the constructor.
     */
    @Test
    void removeSkillTestWithSkillsAddedThroughTheConstructor() {
        Job job = new Job("jardineiro", "jardineiro");
        Skill skill = new Skill("Carta A", "pode conduzir motas");
        List<Skill> skills = new ArrayList<>();
        skills.add(skill);
        Collaborator col = new Collaborator("123@gmail.com", "Ricardo", new Address("456 Street", "Porto", "123-456"), "908767", job, new Date(), new Date(), "ID", 123456, 78910, skills);
        assertEquals(skill, col.getSkills().get(0));
        col.removeSkill(skill);
        assertTrue(col.getSkills().isEmpty());
    }

    /**
     * Remove skill with skills added later.
     */
    @Test
    void removeSkillWithSkillsAddedLater() {
        Job job = new Job("jardineiro", "jardineiro");
        Collaborator col = new Collaborator("123@gmail.com", "Ricardo", new Address("456 Street", "Porto", "123-456"), "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        Skill skill = new Skill("Carta A", "pode conduzir motas");
        col.addSkill(skill);
        assertEquals(skill, col.getSkills().get(0));
        col.removeSkill(skill);
        assertTrue(col.getSkills().isEmpty());
    }


    /**
     * Add null skill.
     */
    @Test
    void addNullSkill() {
        Job job = new Job("jardineiro", "jardineiro");
        Collaborator col = new Collaborator("123@gmail.com", "Ricardo", new Address("456 Street", "Porto", "123-456"), "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        assertThrows(IllegalArgumentException.class, () -> {
            Skill AllNull = new Skill(null, null);
            col.addSkill(AllNull);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Skill nameNull = new Skill(null, "Desc");
            col.addSkill(nameNull);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Skill ShortDescNull = new Skill("nome", null);
            col.addSkill(ShortDescNull);
        });
    }

    /**
     * Remove unexisting skill.
     */
    @Test
    void removeUnexistingSkill() {
        Job job = new Job("jardineiro", "jardineiro");
        Collaborator col = new Collaborator("123@gmail.com", "Ricardo", new Address("456 Street", "Porto", "123-456"), "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        Skill skill = new Skill("Carta A", "pode conduzir motas");
        assertThrows(IllegalArgumentException.class, () -> {
            col.removeSkill(skill);
        });
    }

    /**
     * Test equals and hash code.
     */
    @Test
    void testEqualsAndHashCode() {
        Job job = new Job("jardineiro", "jardineiro");
        Address add = new Address("456 Street", "Porto", "123-456");
        Collaborator col1 = new Collaborator("123@gmail.com", "Ricardo", add, "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        Collaborator col2 = new Collaborator("123@gmail.com", "Ricardo", add, "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        assertTrue(col1.equals(col2) && col2.equals(col1));
        assertEquals(col1.hashCode(), col2.hashCode());
    }

    /**
     * Test clone.
     */
    @Test
    void testClone() {
        Job job = new Job("jardineiro", "jardineiro");
        Collaborator col1 = new Collaborator("123@gmail.com", "Ricardo", new Address("456 Street", "Porto", "123-456"), "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        Collaborator col2 = col1.clone();
        assertNotSame(col1, col2);
        assertEquals(col1, col2);
    }


    /**
     * Add existing skill.
     */
    @Test
    void addExistingSkill() {
        Job job = new Job("jardineiro", "jardineiro");
        Collaborator col = new Collaborator("123@gmail.com", "Ricardo", new Address("456 Street", "Porto", "123-456"), "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        Skill skill = new Skill("Carta A", "pode conduzir motas");
        col.addSkill(skill);
        assertThrows(IllegalArgumentException.class, () -> {
            col.addSkill(skill);
        });
    }

    /**
     * Gets skills.
     */
    @Test
    void getSkills() {
        Job job = new Job("jardineiro", "jardineiro");
        Collaborator col = new Collaborator("123@gmail.com", "Ricardo", new Address("456 Street", "Porto", "123-456"), "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        Skill skill = new Skill("Carta A", "pode conduzir motas");
        col.addSkill(skill);
        Skill skill2 = new Skill("Carta B", "pode conduzir carros");
        col.addSkill(skill2);
        List<Skill> expectedSkills = new ArrayList<>();
        expectedSkills.add(skill);
        expectedSkills.add(skill2);
        assertEquals(expectedSkills, col.getSkills());
    }
}