package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Vehicle repository test.
 */
class VehicleRepositoryTest {

    private VehicleRepository vehicleRepository;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        vehicleRepository = new VehicleRepository();
    }

    /**
     * Add vehicle adds vehicle to list.
     */
    @Test
    void addVehicleAddsVehicleToList() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleRepository.addVehicle(vehicle);
        List<Vehicle> vehicleList = vehicleRepository.getVehicleList();
        assertTrue(vehicleList.contains(vehicle));
    }

    /**
     * Remove vehicle removes vehicle from list.
     */
    @Test
    void removeVehicleRemovesVehicleFromList() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleRepository.addVehicle(vehicle);
        vehicleRepository.removeVehicle(vehicle);
        List<Vehicle> vehicleList = vehicleRepository.getVehicleList();
        assertFalse(vehicleList.contains(vehicle));
    }

    /**
     * Update vehicle updates vehicle in list.
     */
    @Test
    void updateVehicleUpdatesVehicleInList() {
        Vehicle oldVehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Vehicle newVehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleRepository.addVehicle(oldVehicle);
        vehicleRepository.updateVehicle(oldVehicle, newVehicle);
        List<Vehicle> vehicleList = vehicleRepository.getVehicleList();
        assertFalse(vehicleList.contains(oldVehicle));
        assertTrue(vehicleList.contains(newVehicle));
    }

    /**
     * Gets vehicle list returns correct list.
     */
    @Test
    void getVehicleListReturnsCorrectList() {
        Vehicle vehicle1 = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Vehicle vehicle2 = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleRepository.addVehicle(vehicle1);
        vehicleRepository.addVehicle(vehicle2);
        List<Vehicle> vehicleList = vehicleRepository.getVehicleList();
        assertEquals(2, vehicleList.size());
        assertTrue(vehicleList.contains(vehicle1));
        assertTrue(vehicleList.contains(vehicle2));
    }


    /**
     * Add vehicle does not add duplicate vehicle.
     */
    @Test
    void addVehicleDoesNotAddDuplicateVehicle() {
        Vehicle vehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleRepository.addVehicle(vehicle);
        assertThrows(IllegalArgumentException.class, () -> vehicleRepository.addVehicle(vehicle));
    }


    /**
     * Remove vehicle throws exception when vehicle not found.
     */
    @Test
    void removeVehicleThrowsExceptionWhenVehicleNotFound() {
        Vehicle vehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        assertThrows(IllegalArgumentException.class, () -> vehicleRepository.removeVehicle(vehicle));
    }


    /**
     * Update vehicle throws exception when old vehicle not found.
     */
    @Test
    void updateVehicleThrowsExceptionWhenOldVehicleNotFound() {
        Vehicle oldVehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Vehicle newVehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        assertThrows(IllegalArgumentException.class, () -> vehicleRepository.updateVehicle(oldVehicle, newVehicle));
    }

    /**
     * Add brand adds brand to list.
     */
    @Test
    void addBrandAddsBrandToList() {
        vehicleRepository.addBrand("NewBrand");
        assertTrue(vehicleRepository.getBrandList().contains("NewBrand"));
    }

    /**
     * Add brand throws exception when brand already exists.
     */
    @Test
    void addBrandThrowsExceptionWhenBrandAlreadyExists() {
        vehicleRepository.addBrand("ExistingBrand");
        assertThrows(IllegalArgumentException.class, () -> vehicleRepository.addBrand("ExistingBrand"));
    }

    /**
     * Add model adds model to brand.
     */
    @Test
    void addModelAddsModelToBrand() {
        vehicleRepository.addBrand("Brand");
        vehicleRepository.addModel("Model", "Brand");
        assertTrue(vehicleRepository.getModelsForBrand("Brand").contains("Model"));
    }

    /**
     * Add model throws exception when brand does not exist.
     */
    @Test
    void addModelThrowsExceptionWhenBrandDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> vehicleRepository.addModel("Model", "NonExistingBrand"));
    }

    /**
     * Add model throws exception when model already exists for brand.
     */
    @Test
    void addModelThrowsExceptionWhenModelAlreadyExistsForBrand() {
        vehicleRepository.addBrand("Brand");
        vehicleRepository.addModel("Model", "Brand");
        assertThrows(IllegalArgumentException.class, () -> vehicleRepository.addModel("Model", "Brand"));
    }

    /**
     * Add type adds type to list.
     */
    @Test
    void addTypeAddsTypeToList() {
        vehicleRepository.addType("NewType");
        assertTrue(vehicleRepository.getTypeList().contains("NewType"));
    }

    /**
     * Add type throws exception when type already exists.
     */
    @Test
    void addTypeThrowsExceptionWhenTypeAlreadyExists() {
        vehicleRepository.addType("ExistingType");
        assertThrows(IllegalArgumentException.class, () -> vehicleRepository.addType("ExistingType"));
    }
}