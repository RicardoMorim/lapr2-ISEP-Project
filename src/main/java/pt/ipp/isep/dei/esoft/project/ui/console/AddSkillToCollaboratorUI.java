package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.SkillController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.List;

/**
 * The type Add skill to collaborator ui.
 */
public class AddSkillToCollaboratorUI implements Runnable {

    private final CollaboratorController collaboratorController;
    private final SkillController skillController;


    /**
     * Instantiates a new Add skill to collaborator ui.
     */
    public AddSkillToCollaboratorUI(){
        this.collaboratorController = new CollaboratorController();
        this.skillController = new SkillController();
    }

    @Override
    public void run() {
        System.out.println("\n-------Add a skill to a collaborator.--------\n");

        List<Collaborator> collaboratorList = collaboratorController.getCollaboratorList();
        int collaboratorIndex = Utils.showAndSelectIndex(collaboratorList, "Select a collaborator: ");

        List<Skill> skillList = skillController.getSkillThatTheCollaboratorDoesNotHave(collaboratorList.get(collaboratorIndex).getSkills());
        int skillIndex = Utils.showAndSelectIndex(skillList, "Select a skill: ");

        if (skillIndex == -1) {
            System.out.println("Operation cancelled.");
            return;
        }

        if (Utils.confirm("Do you want to add the skill to the collaborator? (S/N)")) {
            try {
                collaboratorController.addSkillToACollaborator(skillList.get(skillIndex), collaboratorList.get(collaboratorIndex));
                System.out.println("The skill was successfully added to the collaborator.");
            } catch (IllegalArgumentException e) {
                System.out.println("There was an error adding the skill to the collaborator.\n" + e.getMessage());
            }
        } else {
            System.out.println("Operation cancelled.");
        }
    }
}