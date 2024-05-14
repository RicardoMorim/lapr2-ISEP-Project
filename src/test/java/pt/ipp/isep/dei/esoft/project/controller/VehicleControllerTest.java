package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleControllerTest {
    private VehicleController vehicleController;

    @BeforeEach
    void setUp() {
        VehicleRepository vehicleRepository = new VehicleRepository();
        vehicleController = new VehicleController(vehicleRepository);
    }

    @Test
    void addVehicleSuccessfully() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        assertTrue(vehicleController.checkIfVehicleExists(vehicle));
    }

    @Test
    void removeVehicleSuccessfully() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        vehicleController.removeVehicle(vehicle);
        assertFalse(vehicleController.checkIfVehicleExists(vehicle));
    }

    @Test
    void updateVehicleSuccessfully() {
        Vehicle oldVehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Vehicle newVehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(oldVehicle);
        vehicleController.updateVehicle(oldVehicle, newVehicle);
        assertFalse(vehicleController.checkIfVehicleExists(oldVehicle));
        assertTrue(vehicleController.checkIfVehicleExists(newVehicle));
    }

    @Test
    void getVehicleByPlateReturnsCorrectVehicle() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        Vehicle retrievedVehicle = vehicleController.getVehicleByPlate("ABC-1234");
        assertEquals(vehicle, retrievedVehicle);
    }

    @Test
    void getVehicleByPlateReturnsNullWhenVehicleDoesNotExist() {
        Vehicle retrievedVehicle = vehicleController.getVehicleByPlate("ABC-1234");
        assertNull(retrievedVehicle);
    }

    @Test
    void checkIfVehicleExistsReturnsTrueWhenVehicleExists() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        assertTrue(vehicleController.checkIfVehicleExists(vehicle));
    }

    @Test
    void checkIfVehicleExistsReturnsFalseWhenVehicleDoesNotExist() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        assertFalse(vehicleController.checkIfVehicleExists(vehicle));
    }

    @Test
    void checkIfVehicleExistsReturnsTrueWhenOldVehicleExistsAndNewVehicleDoesNotExist() {
        Vehicle oldVehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Vehicle newVehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(oldVehicle);
        assertTrue(vehicleController.checkIfVehicleExists(oldVehicle, newVehicle));
    }

    @Test
    void checkIfVehicleExistsReturnsFalseWhenOldVehicleDoesNotExistAndNewVehicleExists() {
        Vehicle oldVehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Vehicle newVehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(newVehicle);
        assertFalse(vehicleController.checkIfVehicleExists(oldVehicle, newVehicle));
    }

    @Test
    void registerVehicleMaintenanceAddsMaintenanceToVehicle() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        vehicleController.registerVehicleMaintenance("ABC-1234", new Date(), 5000);
        assertTrue(vehicle.getMaintenanceList().contains(new Date() + " - 5000"));
    }

    @Test
    void registerVehicleMaintenanceUpdatesKmLastMaintenance() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        vehicleController.registerVehicleMaintenance("ABC-1234", new Date(), 5000);
        assertEquals(5000, vehicle.getKmLastMaintenance());
    }

    @Test
    void registerVehicleMaintenanceUpdatesKmNextMaintenance() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        vehicleController.registerVehicleMaintenance("ABC-1234", new Date(), 5000);
        assertEquals(15000, vehicle.getKmNextMaintenance());
    }

    @Test
    void getMaintenanceListReturnsCorrectList() {
        Vehicle vehicle1 = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 9900, new Date(), new Date(), 10000, 0);
        Vehicle vehicle2 = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 9870, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle1);
        vehicleController.addVehicle(vehicle2);
        List<Vehicle> vehicleList = vehicleController.getVehiclesNeedingMaintenance();
        List<String> maintenanceList = vehicleController.getMaintenanceList(vehicleList);
        assertEquals(2, maintenanceList.size());
        assertTrue(maintenanceList.get(0).contains(vehicle1.getPlate()));
        assertTrue(maintenanceList.get(1).contains(vehicle2.getPlate()));
    }
}