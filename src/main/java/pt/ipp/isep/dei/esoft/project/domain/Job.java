package pt.ipp.isep.dei.esoft.project.domain;

import java.util.Objects;

public class Job {
    private String name;
    private String description;
    private String shortDescription;

    public Job(String name) {
        this.name = name;
        this.description = null;
        this.shortDescription = null;
    }

    public Job(String name, String shortDescription, String description) {
        this.name = name;
        this.description = description;
        this.shortDescription = shortDescription;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
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

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Job: {\n" +
                "   name='" + name + "',\n" +
                "   shortDescription='" + shortDescription + "',\n" +
                (description.isEmpty() ? "" : "   description='" + description + "',\n") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(name, job.name) &&
                Objects.equals(description, job.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}