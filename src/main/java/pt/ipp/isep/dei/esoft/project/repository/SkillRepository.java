package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillRepository implements Serializable {

    private List<Skill> skills;

    public SkillRepository() {
        this.skills = new ArrayList<Skill>();
    }

    public void setSkills(List<Skill> skills){
        this.skills = skills;
    }

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


    public Skill getSkillByName(String name) {
        for (Skill s : this.skills) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Skill not found.");
    }

    public Optional<Skill> add(Skill skill) {
        Optional<Skill> newSkill = Optional.empty();
        if (skills.contains(skill)) {
            throw new IllegalArgumentException("Skill already exists.");
        }

        newSkill = Optional.of(skill);
        skills.add(newSkill.get());

        return newSkill;
    }


    public Optional<Skill> remove(Skill skill) {
        Optional<Skill> newSkill = Optional.empty();


        if (!this.skills.contains(skill)) {
            throw new IllegalArgumentException("Skill not found.");
        }


        newSkill = Optional.of(skill);
        skills.remove(newSkill.get());


        return newSkill;
    }

    public List<Skill> getSkills() {
        return skills;
    }

}