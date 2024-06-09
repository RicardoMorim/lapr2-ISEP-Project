package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Vehicle test.
 */
class VehicleTest {
    private Vehicle vehicle;
    private Date registerDate;
    private Date acquisitionDate;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        registerDate = new Date();
        acquisitionDate = new Date();
        vehicle = new Vehicle("XY-13-76", "Brand", "Model", "Type", 1000, 2000, 0, registerDate, acquisitionDate, 10000, 0);
    }

    /**
     * Validate vehicle returns true when all fields are valid.
     */
    @Test
    void validateVehicleReturnsTrueWhenAllFieldsAreValid() {
        assertTrue(vehicle.validateVehicle());
    }

    /**
     * Validate vehicle throws exception when plate is empty.
     */
    @Test
    void validateVehicleThrowsExceptionWhenPlateIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> vehicle.setPlate(""));
    }

    /**
     * Validate vehicle throws exception when brand is empty.
     */
    @Test
    void validateVehicleThrowsExceptionWhenBrandIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> vehicle.setBrand(""));
    }

    /**
     * Validate vehicle throws exception when model is empty.
     */
    @Test
    void validateVehicleThrowsExceptionWhenModelIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> vehicle.setModel(""));
    }

    /**
     * Validate vehicle throws exception when type is empty.
     */
    @Test
    void validateVehicleThrowsExceptionWhenTypeIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> vehicle.setType(""));
    }

    /**
     * Validate vehicle throws exception when tare weight is zero or negative.
     */
    @Test
    void validateVehicleThrowsExceptionWhenTareWeightIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> vehicle.setTareWeight(0));
        assertThrows(IllegalArgumentException.class, () -> vehicle.setTareWeight(-1));
    }

    /**
     * Validate vehicle throws exception when gross weight is zero or negative.
     */
    @Test
    void validateVehicleThrowsExceptionWhenGrossWeightIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> vehicle.setGrossWeight(0));
        assertThrows(IllegalArgumentException.class, () -> vehicle.setGrossWeight(-1));
    }

    /**
     * Validate vehicle throws exception when current km is negative.
     */
    @Test
    void validateVehicleThrowsExceptionWhenCurrentKMIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> vehicle.setCurrentKM(-1));
    }

    /**
     * Validate vehicle throws exception when checkup interval km is zero or negative.
     */
    @Test
    void validateVehicleThrowsExceptionWhenCheckupIntervalKMIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> vehicle.setCheckupIntervalKM(0));
        assertThrows(IllegalArgumentException.class, () -> vehicle.setCheckupIntervalKM(-1));
    }

    /**
     * Validate vehicle throws exception when plate length is not eight.
     */
    @Test
    void validateVehicleThrowsExceptionWhenPlateLengthIsNotEight() {
        assertThrows(IllegalArgumentException.class, () -> vehicle.setPlate("XY11213-76"));
    }

    /**
     * Register maintenance updates km last maintenance.
     */
    @Test
    void registerMaintenanceUpdatesKmLastMaintenance() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date futureDate = calendar.getTime();

        vehicle.registerMaintenance(futureDate, 5000);
        assertEquals(5000, vehicle.getKmLastMaintenance());
    }

    /**
     * Register maintenance updates km next maintenance.
     */
    @Test
    void registerMaintenanceUpdatesKmNextMaintenance() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date futureDate = calendar.getTime();

        vehicle.registerMaintenance(futureDate, 5000);
        assertEquals(15000, vehicle.getKmNextMaintenance());
    }

    /**
     * Test get maintenance.
     */
    @Test
    void testGetMaintenance() {
        Map<Date, Integer> maintenance = vehicle.getMaintenance();
        assertEquals(1, maintenance.size());
        assertTrue(maintenance.containsKey(registerDate));
        assertEquals(0, maintenance.get(registerDate));
    }

    /**
     * Test get maintenance list.
     */
    @Test
    void testGetMaintenanceList() {
        List<String> maintenanceList = vehicle.getMaintenanceList();
        assertEquals(1, maintenanceList.size());
        assertTrue(maintenanceList.get(0).contains(registerDate.toString()));
        assertTrue(maintenanceList.get(0).contains("0"));
    }

    /**
     * Test set maintenance list.
     */
    @Test
    void testSetMaintenanceList() {
        Map<Date, Integer> newMaintenanceList = new TreeMap<>();
        newMaintenanceList.put(new Date(), 1000);
        vehicle.setMaintenanceList(newMaintenanceList);

        assertEquals(newMaintenanceList, vehicle.getMaintenance());
    }

    /**
     * Test toString with maintenance list.
     */
    @Test
    void testToStringWithMaintenanceList() {
        String expected = "Vehicle{\n" +
                "plate='XY-13-76\n" +
                ", brand='Brand\n" +
                ", model='Model\n" +
                ", type='Type\n" +
                ", tareWeight=1000\n" +
                ", grossWeight=2000\n" +
                ", currentKM=0\n" +
                ", registerDate=" + registerDate + '\n' +
                ", acquisitionDate=" + acquisitionDate + '\n' +
                ", checkupIntervalKM=10000\n" +
                ", kmLastMaintenance=0\n" +
                ", kmNextMaintenance=10000\n" +
                "[" + registerDate.toString() + " - 0]\n" +
                '}';
        assertEquals(expected, vehicle.toStringWithMaintenanceList());
    }

    /**
     * Test clone.
     */
    @Test
    void testClone() {
        Vehicle clonedVehicle = vehicle.clone();
        assertEquals(vehicle, clonedVehicle);
        assertNotSame(vehicle, clonedVehicle);
    }

    /**
     * Test to string.
     */
    @Test
    void testToString() {
        String expected = "Brand Model (XY-13-76)";
        assertEquals(expected, vehicle.toString());
    }

    /**
     * Test equals and hash code.
     */
    @Test
    void testEqualsAndHashCode() {
        Vehicle sameVehicle = new Vehicle(vehicle);
        assertEquals(vehicle, sameVehicle);
        assertEquals(vehicle.hashCode(), sameVehicle.hashCode());

        Vehicle differentVehicle = new Vehicle("AB-56-36", "OtherBrand", "OtherModel", "OtherType", 1500, 2500, 100, new Date(), new Date(), 5000, 100);
        assertNotEquals(vehicle, differentVehicle);
        assertNotEquals(vehicle.hashCode(), differentVehicle.hashCode());
    }

    /**
     * Test getters and setters.
     */
    @Test
    void testGettersAndSetters() {
        vehicle.setPlate("XY-13-76");
        assertEquals("XY-13-76", vehicle.getPlate());

        vehicle.setBrand("NewBrand");
        assertEquals("NewBrand", vehicle.getBrand());

        vehicle.setModel("NewModel");
        assertEquals("NewModel", vehicle.getModel());

        vehicle.setType("NewType");
        assertEquals("NewType", vehicle.getType());

        vehicle.setTareWeight(1500);
        assertEquals(1500, vehicle.getTareWeight());

        vehicle.setGrossWeight(2500);
        assertEquals(2500, vehicle.getGrossWeight());

        vehicle.setCurrentKM(100);
        assertEquals(100, vehicle.getCurrentKM());

        Date newDate = new Date();
        vehicle.setRegisterDate(newDate);
        assertEquals(newDate, vehicle.getRegisterDate());

        vehicle.setAcquisitionDate(newDate);
        assertEquals(newDate, vehicle.getAcquisitionDate());

        vehicle.setCheckupIntervalKM(5000);
        assertEquals(5000, vehicle.getCheckupIntervalKM());

        vehicle.setKmLastMaintenance(100);
        assertEquals(100, vehicle.getKmLastMaintenance());
    }
}
