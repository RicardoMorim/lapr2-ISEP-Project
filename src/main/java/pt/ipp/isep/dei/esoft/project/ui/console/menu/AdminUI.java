package pt.ipp.isep.dei.esoft.project.ui.console.menu;


import pt.ipp.isep.dei.esoft.project.application.controller.*;
import pt.ipp.isep.dei.esoft.project.ui.console.*;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paulo Maio pam@isep.ipp.pt
 */

public class AdminUI implements Runnable {
    private CollaboratorController collaboratorController;
    private JobController jobController;
    private SkillController skillController;
    private VehicleController vehicleController;

    public AdminUI() {
        System.out.println("AdminUI");
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register a skill that a collaborator may have", new RegisterSkillUI()));
        options.add(new MenuItem("Register a job that a collaborator may have", new RegisterJobUI()));
        options.add(new MenuItem("Register a collaborator", new RegisterCollaboratorUI()));
        options.add(new MenuItem("Add a skill to a collaborator", new AddSkillToCollaboratorUI()));
        options.add(new MenuItem("Generate a team proposal automatically", new GenerateTeamProposalUI()));
        options.add(new MenuItem("Register Vehicle", new RegisterVehicleUI()));
        options.add(new MenuItem("Register Vehicle maintenance", new RegisterVehicleMaintenanceUI()));
        options.add(new MenuItem("Get a list of vehicles needing maintenance", new ListVehiclesNeedingMaintenanceUI()));
        options.add(new MenuItem("Get best water pipe routes", new GetBestPipeRoutesUI()));
        options.add(new MenuItem("Add an entry to the Agenda", new AddEntryToAgendaUI()));
        options.add(new MenuItem("Postpone entry in the Agenda", new PostponeEntryUI()));
        options.add(new MenuItem("Cancel an entry in the Agenda", new CancelUI()));
        options.add(new MenuItem("Add a vehicle to an Agenda Entry", new AddVehicleToAgendaEntryUI()));
        options.add(new MenuItem("Register a green space", new AddGreenSpaceUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- ADMIN MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}