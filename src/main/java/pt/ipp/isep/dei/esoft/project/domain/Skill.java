package pt.ipp.isep.dei.esoft.project.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Skill {
    private String shortDescription;
    private String name;


    public Skill(String name, String shortDescription) {
        this.name = name;
        this.shortDescription = shortDescription;

    }


    public String getName() {
        return this.name;
    }


    public String getShortDescription() {
        return this.shortDescription;
    }

    public List<String> getSkillValues() {
        List<String> values = new ArrayList<String>();
        values.add(this.name);
        values.add(this.shortDescription);
        return values;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }


    @Override
    public String toString() {
        return
                "Skill Name = '" + name + "' - Skill Description = '" + shortDescription + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(shortDescription, skill.shortDescription) && Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortDescription, name);
    }


}