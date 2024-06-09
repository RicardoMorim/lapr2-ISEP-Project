package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Skill class represents a skill that a Collaborator can have.
 * Each skill has a name and a short description.
 */
public class Skill implements Serializable {
    private String shortDescription;
    private String name;

    /**
     * Constructs a new Skill with the given name and short description.
     *
     * @param name the name of the skill
     * @param shortDescription a short description of the skill
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

    /**
     * Returns a string representation of the skill.
     *
     * @return a string representation of the skill
     */
    @Override
    public String toString() {
        return
                "Skill Name = '" + name + "' - Skill Description = '" + shortDescription + "'";
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(shortDescription, skill.shortDescription) && Objects.equals(name, skill.name);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(shortDescription, name);
    }


}

