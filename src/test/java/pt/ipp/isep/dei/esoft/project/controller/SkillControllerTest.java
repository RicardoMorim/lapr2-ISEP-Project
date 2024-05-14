package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.SkillController;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkillControllerTest {

    SkillRepository skillRepository;
    SkillController skillController;

    @BeforeEach
    void setUp() {
        skillRepository = new SkillRepository();
        skillController = new SkillController(skillRepository);
    }

    @Test
    void registerSkillSuccessfully() {
        Skill skill = new Skill("Java", "Short Description");

        skillController.registerSkill(skill);

        List<Skill> skills = skillRepository.getSkills();
        assertTrue(skills.contains(skill));
    }

    @Test
    void registerSkillFailsWhenSkillExists() {
        Skill skill = new Skill("Java", "Short Description");
        skillRepository.add(skill);

        assertThrows(IllegalArgumentException.class, () -> skillController.registerSkill(skill));
    }

    @Test
    void updateSkillSuccessfully() {
        Skill skill = new Skill("Java", "Short Description");
        skillController.updateSkill(skillRepository, skill, "Python", "Updated Short Description");

        assertEquals("Python", skill.getName());
        assertEquals("Updated Short Description", skill.getShortDescription());
    }

    @Test
    void removeSkillSuccessfully() {
        Skill skill = new Skill("Java", "Short Description");
        skillRepository.add(skill);
        skillController.removeSkill(skill);

        List<Skill> skills = skillRepository.getSkills();
        assertFalse(skills.contains(skill));
    }

    @Test
    void getSkillListReturnsCorrectList() {
        Skill skill = new Skill("Java", "Short Description");
        skillRepository.add(skill);

        List<Skill> result = skillController.getSkillList();

        assertEquals(skillRepository.getSkills(), result);
    }
}