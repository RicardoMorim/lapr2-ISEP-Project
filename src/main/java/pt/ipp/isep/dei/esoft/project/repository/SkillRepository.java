package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Skill repository.
 */
public class SkillRepository implements Serializable {

    private List<Skill> skills;

    /**
     * Instantiates a new Skill repository.
     */
    public SkillRepository() {
        this.skills = new ArrayList<Skill>();
    }

    /**
     * Set skills.
     *
     * @param skills the skills
     */
    public void setSkills(List<Skill> skills){
        this.skills = skills;
    }

    /**
     * Update optional.
     *
     * @param skill            the skill
     * @param name             the name
     * @param shortDescription the short description
     * @return the optional
     */
    public Optional<Skill> update(Skill skill, String name, String shortDescription) {
        for (Skill s : this.skills) {
            if (s.equals(skill)) {
                s.setName(name);
                s.setShortDescription(shortDescription);
                return Optional.of(skill);

            }
        }

        throw new IllegalArgumentException("Skill not found.");
    }


    /**
     * Gets skill by name.
     *
     * @param name the name
     * @return the skill by name
     */
    public Skill getSkillByName(String name) {
        for (Skill s : this.skills) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Skill not found.");
    }

    /**
     * Add optional.
     *
     * @param skill the skill
     * @return the optional
     */
    public Optional<Skill> add(Skill skill) {
        Optional<Skill> newSkill = Optional.empty();
        if (skills.contains(skill)) {
            throw new IllegalArgumentException("Skill already exists.");
        }

        newSkill = Optional.of(skill);
        skills.add(newSkill.get());

        return newSkill;
    }


    /**
     * Remove optional.
     *
     * @param skill the skill
     * @return the optional
     */
    public Optional<Skill> remove(Skill skill) {
        Optional<Skill> newSkill = Optional.empty();


        if (!this.skills.contains(skill)) {
            throw new IllegalArgumentException("Skill not found.");
        }


        newSkill = Optional.of(skill);
        skills.remove(newSkill.get());


        return newSkill;
    }

    /**
     * Gets skills.
     *
     * @return the skills
     */
    public List<Skill> getSkills() {
        return skills;
    }

}