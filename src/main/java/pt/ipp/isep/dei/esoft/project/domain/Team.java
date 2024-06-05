package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    private List<Collaborator> collaborators;
    private boolean isAssigned;

    public Team(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
        this.isAssigned = false;
    }

    public List<Skill> getSkills() {
        List<Skill> skills = new ArrayList<>();
        for (Collaborator collaborator : collaborators) {
            skills.addAll(collaborator.getSkills());
        }
        return skills;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
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