package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Vehicle controller test.
 */
class VehicleControllerTest {
    private VehicleController vehicleController;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        VehicleRepository vehicleRepository = new VehicleRepository();
        vehicleController = new VehicleController(vehicleRepository);
    }

    /**
     * Add vehicle successfully.
     */
    @Test
    void addVehicleSuccessfully() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        assertTrue(vehicleController.checkIfVehicleExists(vehicle));
    }

    /**
     * Remove vehicle successfully.
     */
    @Test
    void removeVehicleSuccessfully() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        vehicleController.removeVehicle(vehicle);
        assertFalse(vehicleController.checkIfVehicleExists(vehicle));
    }

    /**
     * Update vehicle successfully.
     */
    @Test
    void updateVehicleSuccessfully() {
        Vehicle oldVehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Vehicle newVehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(oldVehicle);
        vehicleController.updateVehicle(oldVehicle, newVehicle);
        assertFalse(vehicleController.checkIfVehicleExists(oldVehicle));
        assertTrue(vehicleController.checkIfVehicleExists(newVehicle));
    }

    /**
     * Gets vehicle by plate returns correct vehicle.
     */
    @Test
    void getVehicleByPlateReturnsCorrectVehicle() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        Vehicle retrievedVehicle = vehicleController.getVehicleByPlate("ABC-1234");
        assertEquals(vehicle, retrievedVehicle);
    }

    /**
     * Gets vehicle by plate returns null when vehicle does not exist.
     */
    @Test
    void getVehicleByPlateReturnsNullWhenVehicleDoesNotExist() {
        Vehicle retrievedVehicle = vehicleController.getVehicleByPlate("ABC-1234");
        assertNull(retrievedVehicle);
    }

    /**
     * Check if vehicle exists returns true when vehicle exists.
     */
    @Test
    void checkIfVehicleExistsReturnsTrueWhenVehicleExists() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        assertTrue(vehicleController.checkIfVehicleExists(vehicle));
    }

    /**
     * Check if vehicle exists returns false when vehicle does not exist.
     */
    @Test
    void checkIfVehicleExistsReturnsFalseWhenVehicleDoesNotExist() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        assertFalse(vehicleController.checkIfVehicleExists(vehicle));
    }

    /**
     * Check if vehicle exists returns true when old vehicle exists and new vehicle does not exist.
     */
    @Test
    void checkIfVehicleExistsReturnsTrueWhenOldVehicleExistsAndNewVehicleDoesNotExist() {
        Vehicle oldVehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Vehicle newVehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(oldVehicle);
        assertTrue(vehicleController.checkIfVehicleExists(oldVehicle, newVehicle));
    }

    /**
     * Check if vehicle exists returns false when old vehicle does not exist and new vehicle exists.
     */
    @Test
    void checkIfVehicleExistsReturnsFalseWhenOldVehicleDoesNotExistAndNewVehicleExists() {
        Vehicle oldVehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Vehicle newVehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(newVehicle);
        assertFalse(vehicleController.checkIfVehicleExists(oldVehicle, newVehicle));
    }

    /**
     * Register vehicle maintenance adds maintenance to vehicle.
     */
    @Test
    void registerVehicleMaintenanceAddsMaintenanceToVehicle() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        vehicleController.registerVehicleMaintenance("ABC-1234", new Date(), 5000);
        assertTrue(vehicle.getMaintenanceList().contains(new Date() + " - 5000"));
    }

    /**
     * Register vehicle maintenance updates km last maintenance.
     */
    @Test
    void registerVehicleMaintenanceUpdatesKmLastMaintenance() {

        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        // Create a future date for the maintenance
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Adds one day to the current date
        Date futureDate = calendar.getTime();

        vehicleController.registerVehicleMaintenance("ABC-1234", futureDate, 5000);
        assertEquals(5000, vehicleController.getVehicleByPlate("ABC-1234").getKmLastMaintenance());
    }

    /**
     * Register vehicle maintenance updates km next maintenance.
     */
    @Test
    void registerVehicleMaintenanceUpdatesKmNextMaintenance() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);

        // Create a future date for the maintenance
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1000); // Adds one day to the current date
        Date futureDate = calendar.getTime();

        vehicleController.registerVehicleMaintenance("ABC-1234", futureDate, 5000);
        assertEquals(15000, vehicleController.getVehicleByPlate("ABC-1234").getKmNextMaintenance());
    }

    /**
     * Gets maintenance list returns correct list.
     */
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


    /**
     * Test get collaborator repository.
     */
    @Test
    void testGetCollaboratorRepository() {
        CollaboratorRepository expected = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(expected);
        assertEquals(expected, controller.getCollaboratorRepository());
    }

    /**
     * Test get collaborator list.
     */
    @Test
    void testGetCollaboratorList() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));
        repository.add(collaborator);
        List<Collaborator> expected = List.of(collaborator);
        assertEquals(expected, controller.getCollaboratorList());
    }

    /**
     * Test register collaborator with skills.
     */
    @Test
    void testRegisterCollaboratorWithSkills() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        List<Skill> skills = List.of(new Skill("Java", "Code"));
        Collaborator collaborator = controller.registerCollaborator("John Doe", "john.doe@example.com", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), skills, new Date(), new Date(), "ID", 123456, 123456);
        assertEquals(skills, collaborator.getSkills());
    }

    /**
     * Test validate plate.
     */
    @Test
    void testValidatePlate() {
        assertTrue(vehicleController.validatePlate("AB-12-23"));
        assertFalse(vehicleController.validatePlate("123-ABC"));
        assertFalse(vehicleController.validatePlate(""));
    }

    /**
     * Test set vehicle list.
     */
    @Test
    void testSetVehicleList() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        List<Vehicle> expected = List.of(vehicle);
        vehicleController.setVehicleList(expected);
        assertEquals(expected, vehicleController.getVehicleList());
    }


}