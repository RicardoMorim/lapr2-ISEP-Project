package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Skill controller.
 */
public class SkillController {
    /**
     * The Skill repository.
     */
    SkillRepository skillRepository;

    /**
     * Instantiates a new Skill controller.
     */
    public SkillController() {
        if (skillRepository == null) {
            Repositories repositories = Repositories.getInstance();

            //Get the TaskCategoryRepository
            skillRepository = repositories.getSkillRepository();
        }
    }

    /**
     * Instantiates a new Skill controller.
     *
     * @param skillRepository the skill repository
     */
    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }


    /**
     * Gets skill repository.
     *
     * @return the skill repository
     */
    public SkillRepository getSkillRepository() {
        return skillRepository;
    }

    /**
     * Gets skill by name.
     *
     * @param name the name
     * @return the skill by name
     */
    public Skill getSkillByName(String name) {
        return skillRepository.getSkillByName(name);
    }

    private boolean validateSkill(Skill skill) {
        List<Skill> skills = skillRepository.getSkills();
        return !skills.contains(skill) && skill.getName().matches("^[a-zA-Z\\s]*$");
    }

    /**
     * Register skill.
     *
     * @param skill the skill
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void registerSkill(Skill skill) throws IllegalArgumentException {
        if (skillRepository.getSkills().contains(skill))
            throw new IllegalArgumentException("Skill already exists.");

        if (skill.getName() == null || skill.getDescription() == null)
            throw new IllegalArgumentException("Skill name or shortDescription parameters are null.");

        if (skill.getName().isEmpty() || skill.getDescription().isEmpty())
            throw new IllegalArgumentException("Skill name or short description parameters are empty.");


        if (!validateSkill(skill))
            throw new IllegalArgumentException("Skill already exists. Or it has an invalid name.");

        skillRepository.add(skill);
    }

    /**
     * Register skill.
     *
     * @param name             the name
     * @param shortDescription the short description
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void registerSkill(String name, String shortDescription) throws IllegalArgumentException {
        Skill skill = new Skill(name, shortDescription);
        registerSkill(skill);
    }

    /**
     * Update skill.
     *
     * @param repo             the repo
     * @param skill            the skill
     * @param name             the name
     * @param shortDescription the short description
     */
    public void updateSkill(SkillRepository repo, Skill skill, String name, String shortDescription) {
        try {
            skill.setName(name);
            skill.setShortDescription(shortDescription);
            repo.update(skill, name, shortDescription);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Remove skill.
     *
     * @param skill the skill
     */
    public void removeSkill(Skill skill) {
        try {
            skillRepository.remove(skill);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Gets skill list.
     *
     * @return the skill list
     */
    public List<Skill> getSkillList() {
        return skillRepository.getSkills();
    }


    /**
     * Gets skill that the collaborator does not have.
     *
     * @param collaboratorSkills the collaborator skills
     * @return the skill that the collaborator does not have
     */
    public List<Skill> getSkillThatTheCollaboratorDoesNotHave(List<Skill> collaboratorSkills) {
        List<Skill> allSkills = new ArrayList<>(getSkillList());
        allSkills.removeAll(collaboratorSkills);
        return allSkills;
    }
}
