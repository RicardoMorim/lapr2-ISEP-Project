package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.JobController;

import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.lang.Runnable;

public class RegisterJobUI implements Runnable {

    JobController jobController;

    public RegisterJobUI() {
        this.jobController = new JobController();
    }


    @Override
    public void run() {
        System.out.println("\n----------Register a job that a collaborator may have.-----------\n");

        String strJobName = Utils.readLineFromConsole("Job Name: ");

        String strJobShortDescription = Utils.readLineFromConsole("Job Short Description: ");


        if (Utils.confirm("Do you want to register the job? (S/N)")) {
            try {
                Job job = this.jobController.registerJob(strJobName, strJobShortDescription);
                System.out.println("The Job was successfully registered.");
                System.out.println(job.toString());
            } catch (IllegalArgumentException e) {
                System.out.println("The Job was not registered.\nThere was an error registering it.\n");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Registration Cancelled.");
        }

    }


}
