package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Skill.
 */
public class Skill implements Serializable {
    private String shortDescription;
    private String name;


    /**
     * Instantiates a new Skill.
     *
     * @param name             the name
     * @param shortDescription the short description
     */
    public Skill(String name, String shortDescription) {
        this.name = name;
        this.shortDescription = shortDescription;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }


    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return this.shortDescription;
    }

    /**
     * Gets skill values.
     *
     * @return the skill values
     */
    public List<String> getSkillValues() {
        List<String> values = new ArrayList<String>();
        values.add(this.name);
        values.add(this.shortDescription);
        return values;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets short description.
     *
     * @param shortDescription the short description
     */
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

