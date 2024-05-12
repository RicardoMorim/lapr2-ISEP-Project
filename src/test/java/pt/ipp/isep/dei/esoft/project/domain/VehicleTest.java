package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date("1-1-2000"), new Date("1-1-2000") , 10000, 0);
    }

    @Test
    void validateVehicleReturnsTrueWhenAllFieldsAreValid() {
        assertTrue(vehicle.validateVehicle());
    }

    @Test
    void validateVehicleThrowsExceptionWhenPlateIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> vehicle.setPlate(""));
    }

    @Test
    void validateVehicleThrowsExceptionWhenBrandIsEmpty() {

        assertThrows(IllegalArgumentException.class, () -> vehicle.setBrand(""));
    }

    @Test
    void validateVehicleThrowsExceptionWhenModelIsEmpty() {

        assertThrows(IllegalArgumentException.class, () -> vehicle.setModel(""));
    }

    @Test
    void validateVehicleThrowsExceptionWhenTypeIsEmpty() {

        assertThrows(IllegalArgumentException.class, () -> vehicle.setType(""));
    }

    @Test
    void validateVehicleThrowsExceptionWhenTareWeightIsEmpty() {

        assertThrows(IllegalArgumentException.class, () -> vehicle.setTareWeight(0));
    }

    @Test
    void validateVehicleThrowsExceptionWhenGrossWeightIsEmpty() {

        assertThrows(IllegalArgumentException.class, () -> vehicle.setGrossWeight(0));
    }

    @Test
    void validateVehicleThrowsExceptionWhenCurrentKMIsNegative() {

        assertThrows(IllegalArgumentException.class, () -> vehicle.setCurrentKM(-1));
    }


    @Test
    void validateVehicleThrowsExceptionWhenCheckupIntervalKMIsNegative() {

        assertThrows(IllegalArgumentException.class, () -> vehicle.setCheckupIntervalKM(-1));
    }

    @Test
    void validateVehicleThrowsExceptionWhenPlateLengthIsNotSix() {

        assertThrows(IllegalArgumentException.class, () -> vehicle.setPlate("ABC-12"));
    }


    @Test
    void registerMaintenanceUpdatesKmLastMaintenance() {
        vehicle.registerMaintenance(new Date(), 5000);
        assertEquals(5000, vehicle.getKmLastMaintenance());
    }

    @Test
    void registerMaintenanceUpdatesKmNextMaintenance() {
        vehicle.registerMaintenance(new Date(), 5000);
        assertEquals(15000, vehicle.getKmNextMaintenance());
    }
}