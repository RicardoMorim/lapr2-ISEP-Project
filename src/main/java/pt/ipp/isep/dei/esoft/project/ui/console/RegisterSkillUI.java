package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.SkillController;

import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.lang.Runnable;

public class RegisterSkillUI implements Runnable {
    SkillController skillController;

    public RegisterSkillUI() {
        this.skillController = new SkillController();
    }


    @Override
    public void run() {
        System.out.println("\n-----------Register a skill that a collaborator may have.-----------\n");

        String strSkillName = Utils.readLineFromConsole("Skill Name: ");

        String strSkillShortDescription = Utils.readLineFromConsole("Skill Description: ");


        if (Utils.confirm("Do you want to register the skill? (S/N)")) {
            try {
                this.skillController.registerSkill(strSkillName, strSkillShortDescription);
                System.out.println("The skill was successfully registered.");
            } catch (IllegalArgumentException e) {
                System.out.println("The skill was not registered.\nThere was an error registering it.\n");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Registration Cancelled.");
        }

    }


}
