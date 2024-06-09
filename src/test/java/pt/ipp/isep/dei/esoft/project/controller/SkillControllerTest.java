package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.SkillController;
import pt.ipp.isep.dei.esoft.project.domain.Address;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Skill controller test.
 */
class SkillControllerTest {

    /**
     * The Skill repository.
     */
    SkillRepository skillRepository;
    /**
     * The Skill controller.
     */
    SkillController skillController;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        skillRepository = new SkillRepository();
        skillController = new SkillController(skillRepository);
    }

    /**
     * Register skill successfully.
     */
    @Test
    void registerSkillSuccessfully() {
        Skill skill = new Skill("Java", "Short Description");

        skillController.registerSkill(skill);

        List<Skill> skills = skillRepository.getSkills();
        assertTrue(skills.contains(skill));
    }

    /**
     * Register skill fails when skill exists.
     */
    @Test
    void registerSkillFailsWhenSkillExists() {
        Skill skill = new Skill("Java", "Short Description");
        skillRepository.add(skill);

        assertThrows(IllegalArgumentException.class, () -> skillController.registerSkill(skill));
    }

    /**
     * Update skill successfully.
     */
    @Test
    void updateSkillSuccessfully() {
        Skill skill = new Skill("Java", "Short Description");
        skillController.updateSkill(skillRepository, skill, "Python", "Updated Short Description");

        assertEquals("Python", skill.getName());
        assertEquals("Updated Short Description", skill.getDescription());
    }

    /**
     * Remove skill successfully.
     */
    @Test
    void removeSkillSuccessfully() {
        Skill skill = new Skill("Java", "Short Description");
        skillRepository.add(skill);
        skillController.removeSkill(skill);

        List<Skill> skills = skillRepository.getSkills();
        assertFalse(skills.contains(skill));
    }

    /**
     * Gets skill list returns correct list.
     */
    @Test
    void getSkillListReturnsCorrectList() {
        Skill skill = new Skill("Java", "Short Description");
        skillRepository.add(skill);

        List<Skill> result = skillController.getSkillList();

        assertEquals(skillRepository.getSkills(), result);
    }


    /**
     * Test get collaborator repository.
     */
    @Test
    void testGetCollaboratorRepository() {
        CollaboratorRepository expected = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(expected);
        assertEquals(expected, controller.getCollaboratorRepository());
    }

    /**
     * Test get collaborator list.
     */
    @Test
    void testGetCollaboratorList() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));
        repository.add(collaborator);
        List<Collaborator> expected = List.of(collaborator);
        assertEquals(expected, controller.getCollaboratorList());
    }

    /**
     * Test register collaborator with skills.
     */
    @Test
    void testRegisterCollaboratorWithSkills() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        List<Skill> skills = List.of(new Skill("Java", "Code"));
        Collaborator collaborator = controller.registerCollaborator("John Doe", "john.doe@example.com", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), skills, new Date(), new Date(), "ID", 123456, 123456);
        assertEquals(skills, collaborator.getSkills());
    }

    /**
     * Test get skill by name.
     */
    @Test
    void testGetSkillByName() {
        Skill skill = new Skill("Java", "Code");
        skillRepository.add(skill);

        Skill result = skillController.getSkillByName("Java");

        assertEquals(skill, result);
    }

    /**
     * Test get skill by name when skill does not exist.
     */
    @Test
    void testGetSkillByNameWhenSkillDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> skillController.getSkillByName("Nonexistent Skill"));

    }

    /**
     * Test register skill with null name throws exception.
     */
    @Test
    void testRegisterSkillWithNullNameThrowsException() {

        assertThrows(IllegalArgumentException.class, () -> skillController.registerSkill(new Skill(null, "Code")));
    }

    /**
     * Test register skill with empty name throws exception.
     */
    @Test
    void testRegisterSkillWithEmptyNameThrowsException() {

        assertThrows(IllegalArgumentException.class, () -> skillController.registerSkill(new Skill("", "Code")));
    }

    /**
     * Test register skill with invalid name throws exception.
     */
    @Test
    void testRegisterSkillWithInvalidNameThrowsException() {
        Skill skill = new Skill("´?123_ AD.", "Code");

        assertThrows(IllegalArgumentException.class, () -> skillController.registerSkill(skill));
    }

    /**
     * Test get skill that the collaborator does not have.
     */
    @Test
    void testGetSkillThatTheCollaboratorDoesNotHave() {
        Skill skill1 = new Skill("Java", "Code");
        Skill skill2 = new Skill("Python", "Code");
        skillRepository.add(skill1);
        skillRepository.add(skill2);

        List<Skill> collaboratorSkills = List.of(skill1);
        List<Skill> expected = List.of(skill2);

        List<Skill> result = skillController.getSkillThatTheCollaboratorDoesNotHave(collaboratorSkills);

        assertEquals(expected, result);
    }

}