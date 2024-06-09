package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Skill repository test.
 */
class SkillRepositoryTest {

    private SkillRepository skillRepository;
    private Skill skill;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        skillRepository = new SkillRepository();
        skill = new Skill("Java", "Short Description");
    }

    /**
     * Add skill returns optional with skill.
     */
    @Test
    void addSkillReturnsOptionalWithSkill() {
        Optional<Skill> result = skillRepository.add(skill);
        assertTrue(result.isPresent());
        assertEquals(skill, result.get());
    }

    /**
     * Add skill returns empty optional when skill exists.
     */
    @Test
    void addSkillReturnsEmptyOptionalWhenSkillExists() {
        skillRepository.add(skill);
        assertThrows(IllegalArgumentException.class, () -> skillRepository.add(skill));
    }

    /**
     * Remove skill returns optional with skill.
     */
    @Test
    void removeSkillReturnsOptionalWithSkill() {
        skillRepository.add(skill);
        Optional<Skill> result = skillRepository.remove(skill);
        assertTrue(result.isPresent());
        assertEquals(skill, result.get());
    }

    /**
     * Remove skill returns empty optional when skill does not exist.
     */
    @Test
    void removeSkillReturnsEmptyOptionalWhenSkillDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> skillRepository.remove(skill));
    }

    /**
     * Update skill updates and returns skill.
     */
    @Test
    void updateSkillUpdatesAndReturnsSkill() {
        skillRepository.add(skill);
        Skill updatedSkill = skillRepository.update(skill, "Python", "Short Description").get();
        assertEquals("Python", updatedSkill.getName());
        assertEquals("Short Description", updatedSkill.getDescription());
    }

    /**
     * Update skill throws exception when skill does not exist.
     */
    @Test
    void updateSkillThrowsExceptionWhenSkillDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> skillRepository.update(skill, "Python", "Short Description"));
    }

    /**
     * Gets skills returns list of skills.
     */
    @Test
    void getSkillsReturnsListOfSkills() {
        skillRepository.add(skill);
        assertEquals(1, skillRepository.getSkills().size());
        assertEquals(skill, skillRepository.getSkills().get(0));
    }
}