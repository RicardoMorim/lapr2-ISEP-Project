package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.Objects;

public class Job implements Serializable {
    private String name;
    private String shortDescription;

    public Job(String name, String shortDescription) {
        this.name = name;
        this.shortDescription = shortDescription;
    }

    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

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