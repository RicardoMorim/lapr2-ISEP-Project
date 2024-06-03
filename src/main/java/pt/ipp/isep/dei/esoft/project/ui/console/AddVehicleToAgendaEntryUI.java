package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;

import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.List;

public class AddVehicleToAgendaEntryUI implements Runnable{
    private AgendaController agendaController = new AgendaController();
    private VehicleController vehicleController = new VehicleController();

   @Override
    public void run() {
        System.out.println("\n-------Add a vehicle to an agenda entry.--------\n");
        List<Vehicle> vehicleList = vehicleController.getVehicleList();
        int vehicleIndex = 1;
        while (vehicleIndex >= 0) {
            List<Vehicle> vehicles = agendaController.getVehiclesNotAssignedToAnyAgendaEntry(vehicleList);
            int agendaEntryIndex = Utils.showAndSelectIndex(agendaController.getAgenda().getEntries(), "Select an agenda entry: ");
            vehicleIndex = Utils.showAndSelectIndex(vehicles, "Select a vehicle: ");
            if (agendaEntryIndex >= 0) {
                System.out.println("Current vehicles in the entry:");
                System.out.println(agendaController.getAgenda().getEntries().get(agendaEntryIndex).getVehicles());
                if (Utils.confirm("Do you want to add the vehicle to the agenda entry? (S/N)")) {
                    try {
                        agendaController.addVehiclesToAgendaEntry(agendaController.getAgenda().getEntries().get(agendaEntryIndex),vehicles.get(vehicleIndex));
                        System.out.println("The vehicle was successfully added to the agenda entry.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("There was an error adding the vehicle to the agenda entry.\n" + e.getMessage());
                    }
                } else {
                    System.out.println("Operation cancelled.");
                }
            }
        }

    }
}
