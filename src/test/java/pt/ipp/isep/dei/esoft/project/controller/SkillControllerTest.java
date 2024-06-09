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
        assertEquals("Updated Short Description", skill.getDescription());
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


    @Test
    void testGetCollaboratorRepository() {
        CollaboratorRepository expected = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(expected);
        assertEquals(expected, controller.getCollaboratorRepository());
    }

    @Test
    void testGetCollaboratorList() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));
        repository.add(collaborator);
        List<Collaborator> expected = List.of(collaborator);
        assertEquals(expected, controller.getCollaboratorList());
    }

    @Test
    void testRegisterCollaboratorWithSkills() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        List<Skill> skills = List.of(new Skill("Java", "Code"));
        Collaborator collaborator = controller.registerCollaborator("John Doe", "john.doe@example.com", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), skills, new Date(), new Date(), "ID", 123456, 123456);
        assertEquals(skills, collaborator.getSkills());
    }
}