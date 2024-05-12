package pt.ipp.isep.dei.esoft.project.application.controller;


import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;
import pt.ipp.isep.dei.esoft.project.domain.Skill;


import java.util.List;

public class SkillController {
    SkillRepository skillRepository;

    public SkillController() {
        if (skillRepository == null) {
            Repositories repositories = Repositories.getInstance();

            //Get the TaskCategoryRepository
            skillRepository = repositories.getSkillRepository();
        }
    }

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }


    public SkillRepository getSkillRepository() {
        return skillRepository;
    }

    public Skill getSkillByName(String name) {
        return skillRepository.getSkillByName(name);
    }

    public void setSkillRepository(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    private boolean validateSkill(Skill skill) {
        List<Skill> skills = skillRepository.getSkills();
        return !skills.contains(skill) && skill.getName().matches("^[a-zA-Z\\s]*$");
    }

    public void registerSkill(Skill skill) throws IllegalArgumentException {
        if (skillRepository.getSkills().contains(skill))
            throw new IllegalArgumentException("Skill already exists.");

        if (skill.getName() == null || skill.getShortDescription() == null)
            throw new IllegalArgumentException("Skill name or shortDescription parameters are null.");

        if (skill.getName().isEmpty() || skill.getShortDescription().isEmpty())
            throw new IllegalArgumentException("Skill name or short description parameters are empty.");


        if (!validateSkill(skill))
            throw new IllegalArgumentException("Skill already exists. Or it has an invalid name.");

        skillRepository.add(skill);
    }

    public void registerSkill(String name, String shortDescription) throws IllegalArgumentException {
        Skill skill = new Skill(name, shortDescription);
        registerSkill(skill);

    }

    public void updateSkill(SkillRepository repo, Skill skill, String name, String shortDescription) {
        try {
            skill.setName(name);
            skill.setShortDescription(shortDescription);
            repo.update(skill, name, shortDescription);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeSkill(Skill skill) {
        try {
            skillRepository.remove(skill);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    public List<Skill> getSkillList() {
        return skillRepository.getSkills();
    }
}