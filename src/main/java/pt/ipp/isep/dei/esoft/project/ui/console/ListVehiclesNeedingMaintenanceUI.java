package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.List;

/**
 * The type List vehicles needing maintenance ui.
 */
public class ListVehiclesNeedingMaintenanceUI implements Runnable {

    private VehicleController vehicleController;

    /**
     * Instantiates a new List vehicles needing maintenance ui.
     */
    public ListVehiclesNeedingMaintenanceUI() {
        this.vehicleController = new VehicleController();
    }

    @Override
    public void run() {
        System.out.println("\n------List of vehicles needing maintenance--------\n");
        System.out.println("\nList of vehicles needing maintenance sorted from most urgent to less urgent:\n");
        List<Vehicle> vehicleListNeedingMaintenance = vehicleController.getVehiclesNeedingMaintenance();
        List<String> maintenanceList = vehicleController.getMaintenanceList(vehicleListNeedingMaintenance);

        if (maintenanceList.isEmpty()) {
            System.out.println("No vehicles need maintenance at the moment.");
        } else {
            for (String vehicleInfo : maintenanceList) {
                System.out.println(vehicleInfo);
            }
        }
    }
}