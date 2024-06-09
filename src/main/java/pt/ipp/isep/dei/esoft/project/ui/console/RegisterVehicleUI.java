package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.Date;
import java.util.List;

/**
 * The type Register vehicle ui.
 */
public class RegisterVehicleUI implements Runnable {

    private VehicleController vehicleController;

    /**
     * Instantiates a new Register vehicle ui.
     */
    public RegisterVehicleUI() {
        this.vehicleController = new VehicleController();
    }

    @Override
    public void run() {
        System.out.println("\n--------Register a vehicle.---------\n");

        String plate = Utils.readLineFromConsole("Plate: ");
        if (vehicleController.checkIfVehicleExists(plate)) {
            System.out.println("The vehicle with the plate " + plate + " already exists.");
            return;
        }

        if (plate == null) {
            System.out.println("The plate can not be null");
            return;
        }
        if (!vehicleController.validatePlate(plate)) {
            System.out.println("The plate is not valid.");
            return;
        }

        String brand = chooseOrAddNew("Brand", vehicleController.getBrandList());
        String model = chooseOrAddNew("Model", vehicleController.getModelsForBrand(brand));
        String type = chooseOrAddNew("Type", vehicleController.getTypeList());

        int tareWeight = Utils.readIntegerFromConsole("Tare Weight: ");
        int grossWeight = Utils.readIntegerFromConsole("Gross Weight: ");
        int currentKM = Utils.readIntegerFromConsole("Current KM: ");

        Date registerDate;
        Date acquisitionDate;

        do {
            registerDate = Utils.readDateFromConsole("Register Date: ");
            acquisitionDate = Utils.readDateFromConsole("Acquisition Date: ");
        } while (registerDate.after(acquisitionDate));

        int checkupIntervalKM = Utils.readIntegerFromConsole("Checkup Interval KM: ");
        int kmLastMaintenance = Utils.readIntegerFromConsole("KM Last Maintenance: ");

        if (Utils.confirm("Do you want to register the vehicle? (S/N)")) {
            try {
                Vehicle vehicle = new Vehicle(plate, brand, model, type, tareWeight, grossWeight, currentKM, registerDate, acquisitionDate, checkupIntervalKM, kmLastMaintenance);
                vehicleController.addVehicle(vehicle);
                System.out.println("The vehicle was successfully registered.");
            } catch (IllegalArgumentException e) {
                System.out.println("The vehicle was not registered.\nThere was an error registering it.\n");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Registration Cancelled.");
        }
    }

    private String chooseOrAddNew(String attribute, List<String> list) {
        list.add("Add a new " + attribute.toLowerCase());
        int index = Utils.showAndSelectIndex(list, attribute + ": ");
        if (index == attribute.length() - 1) {
            String newValue = Utils.readLineFromConsole("Enter new " + attribute.toLowerCase() + ": ");
            list.add(newValue);
            return newValue;
        } else {
            return list.get(index);
        }
    }
}