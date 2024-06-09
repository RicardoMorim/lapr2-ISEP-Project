package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegisterVehicleMaintenanceUI implements Runnable {

    private VehicleController vehicleController;
    private AgendaController agendaController;

    public RegisterVehicleMaintenanceUI() {
        this.vehicleController = new VehicleController();
        this.agendaController = new AgendaController();
    }

    @Override
    public void run() {
        System.out.println("\n-----------Register a vehicle's maintenance.--------\n");

        List<Vehicle> vehicles = vehicleController.getVehiclesNeedingMaintenance();
        List<String> vehiclesForMaintenance = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            vehiclesForMaintenance.add(vehicle.toString());
        }

        vehiclesForMaintenance.add("Other car");
        int vehicleIndex = Utils.showAndSelectIndex(vehiclesForMaintenance, "Displaying vehicles needing maintenance.\nChoose one to register its maintenance: ");

        if (vehicleIndex == -1) {
            System.out.println("Aborting operation.");
            return;
        }

        if (vehicleIndex == vehiclesForMaintenance.size() - 1) {
            vehiclesForMaintenance = new ArrayList<>();
            vehicles = vehicleController.getVehicleList();
            for (Vehicle vehicle : vehicles) {
                vehiclesForMaintenance.add(vehicle.toString());
            }
            vehicleIndex = Utils.showAndSelectIndex(vehiclesForMaintenance, "Select the vehicle");
        }


        Date date = Utils.readDateFromConsole("Enter the maintenance date (dd-mm-yyyy): ");
        int km = Utils.readIntegerFromConsole("Enter the vehicle's current KM at that day: ");

        if (Utils.confirm("Do you want to register the maintenance? (S/N)")) {
            try {
                Vehicle selectedVehicle = vehicles.get(vehicleIndex);
                vehicleController.registerVehicleMaintenance(selectedVehicle.getPlate(), date, km);
                System.out.println("The vehicle's maintenance was successfully registered.");
            } catch (IllegalArgumentException e) {
                System.out.println("The maintenance was not registered.\nThere was an error registering it.\n");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Registration Cancelled.");
        }
    }
}