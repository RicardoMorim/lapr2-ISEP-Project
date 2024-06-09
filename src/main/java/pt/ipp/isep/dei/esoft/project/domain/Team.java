package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Team.
 */
public class Team implements Serializable {
    private List<Collaborator> collaborators;
    private boolean isAssigned;

    /**
     * Instantiates a new Team.
     *
     * @param collaborators the collaborators
     */
    public Team(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
        this.isAssigned = false;
    }

    /**
     * Gets skills.
     *
     * @return the skills
     */
    public List<Skill> getSkills() {
        List<Skill> skills = new ArrayList<>();
        for (Collaborator collaborator : collaborators) {
            skills.addAll(collaborator.getSkills());
        }
        return skills;
    }

    /**
     * Gets collaborators.
     *
     * @return the collaborators
     */
    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    /**
     * Sets collaborators.
     *
     * @param collaborators the collaborators
     */
    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * Is assigned boolean.
     *
     * @return the boolean
     */
    public boolean isAssigned() {
        return isAssigned;
    }

    /**
     * Sets assigned.
     *
     * @param assigned the assigned
     */
    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }


    @Override
    public int hashCode() {
        return Objects.hash(collaborators);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team:\n");
        for (Collaborator collaborator : collaborators) {
            sb.append(collaborator.toString()).append("\n");
        }
        sb.append("Is Assigned: ").append(isAssigned);
        return sb.toString();
    }
}