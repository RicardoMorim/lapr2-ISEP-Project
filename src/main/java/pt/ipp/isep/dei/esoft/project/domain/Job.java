package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Job.
 */
public class Job implements Serializable {
    private String name;
    private String shortDescription;


    /**
     * Instantiates a new Job.
     *
     * @param name             the name
     * @param shortDescription the short description
     */
    public Job(String name, String shortDescription) {
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
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets short description.
     *
     * @return the short description
     */
    public String getShortDescription() {
        return shortDescription;
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
                "Job Name = '" + name + "' - shortDescription = '" + shortDescription + "'";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(name, job.name) &&
                Objects.equals(shortDescription, job.shortDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, shortDescription);
    }
}