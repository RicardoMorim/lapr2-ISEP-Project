package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.JobController;

import pt.ipp.isep.dei.esoft.project.application.controller.SkillController;
import pt.ipp.isep.dei.esoft.project.domain.Address;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.lang.Runnable;
import java.util.Date;
import java.util.List;

/**
 * The type Register collaborator ui.
 */
public class RegisterCollaboratorUI implements Runnable {

    /**
     * The Job controller.
     */
    JobController jobController;
    /**
     * The Collaborator controller.
     */
    CollaboratorController collaboratorController;
    /**
     * The Skill controller.
     */
    SkillController skillController;

    /**
     * Instantiates a new Register collaborator ui.
     */
    public RegisterCollaboratorUI() {
        this.jobController = new JobController();
        this.collaboratorController = new CollaboratorController();
        this.skillController = new SkillController();
    }


    @Override
    public void run() {
        System.out.println("\n---------Register a collaborator-----------.\n");

        String strCollaboratorName = Utils.readLineFromConsole("Collaborator Name: ");

        Date strBirthDate = Utils.readDateFromConsole("Collaborator birth date: ");

        Date AdmissionDate = Utils.readDateFromConsole("Collaborator admission date: ");

        String strAdress = Utils.readLineFromConsole("Collaborator street name: ");

        String strCity = Utils.readLineFromConsole("Collaborator city: ");

        String strZipCode = Utils.readLineFromConsole("Collaborator zip code: ");

        int Phone = Utils.readIntegerFromConsole("Collaborator phone: ");

        String strEmail = Utils.readLineFromConsole("Collaborator email: ");

        int strNIF = Utils.readIntegerFromConsole("Collaborator taxpayer number: ");

        String strIDType = Utils.readLineFromConsole("Collaborator ID type: ");

        int strIDNumber = Utils.readIntegerFromConsole("Collaborator ID number: ");


        List<Job> jobList = jobController.getJobList();


        int jobIndex = Utils.showAndSelectIndex(jobList, "Select the job that the collaborator has: ");

        Collaborator collaborator;
        if (Utils.confirm("Do you want to register the Collaborator? (S/N)")) {
            try {
                collaborator = this.collaboratorController.registerCollaborator(strCollaboratorName, strEmail, new Address(strAdress, strCity, strZipCode), String.valueOf(Phone), jobList.get(jobIndex), strBirthDate, AdmissionDate, strIDType, strNIF, strIDNumber);
                System.out.println("The Collaborator was successfully registered.\n Collaborator:\n" + collaborator.toString() + "\n");

                while (Utils.confirm("Do you want to add a skill to the collaborator? (S/N)")) {

                    List<Skill> skillList = skillController.getSkillList();
                    int skillIndex = Utils.showAndSelectIndex(skillList, "Select the skill that the collaborator has: ");
                    if (Utils.confirm("Do you want to register the skill? (S/N)")) {
                        try {
                            this.collaboratorController.addSkillToACollaborator(skillList.get(skillIndex), collaborator);
                            System.out.println("The Skill was successfully registered.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("The Skill was not registered.\nThere was an error registering it.\n");
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Registration Cancelled.");
                    }
                }

            } catch (IllegalArgumentException e) {
                System.out.println("The Collaborator was not registered.\nThere was an error registering it.\n");
                System.out.println(e.getMessage());
            }


        } else {
            System.out.println("Registration Cancelled.");
        }


    }


}
