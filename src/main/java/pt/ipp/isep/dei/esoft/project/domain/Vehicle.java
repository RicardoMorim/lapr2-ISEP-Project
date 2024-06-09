package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.*;

/**
 * The Vehicle class represents a vehicle in the system.
 * It contains information about the vehicle such as its plate, brand, model, type, tare weight, gross weight, current kilometers, register date, acquisition date, checkup interval in kilometers, kilometers at last maintenance, kilometers at next maintenance, and a maintenance list.
 * The class provides methods for getting and setting these properties.
 * It also provides methods for validating the vehicle, registering maintenance, and getting a list of maintenance.
 * The class overrides the equals and hashCode methods to compare vehicles based on their properties.
 * It also overrides the toString method to provide a string representation of the vehicle.
 */
public class Vehicle implements Serializable {
    private String plate;
    private String brand;
    private String model;
    private String type;
    private int tareWeight;
    private int grossWeight;
    private int currentKM;
    private Date registerDate;
    private Date acquisitionDate;
    private int checkupIntervalKM;
    private int kmLastMaintenance;
    private int kmNextMaintenance;
    private Map<Date, Integer> maintenanceList;

    /**
     * Constructor that creates a new vehicle using another vehicle's data.
     *
     * @param vehicle The vehicle to copy data from.
     */
    public Vehicle(Vehicle vehicle) {
        this.plate = vehicle.getPlate();
        this.brand = vehicle.getBrand();
        this.model = vehicle.getModel();
        this.type = vehicle.getType();
        this.tareWeight = vehicle.getTareWeight();
        this.grossWeight = vehicle.getGrossWeight();
        this.currentKM = vehicle.getCurrentKM();
        this.registerDate = new Date(vehicle.getRegisterDate().getTime());
        this.acquisitionDate = new Date(vehicle.getAcquisitionDate().getTime());
        this.checkupIntervalKM = vehicle.getCheckupIntervalKM();
        this.kmLastMaintenance = vehicle.getKmLastMaintenance();
        this.kmNextMaintenance = vehicle.getKmNextMaintenance();
        this.maintenanceList = vehicle.getMaintenance();
    }


    /**
     * Constructor that creates a new vehicle with the specified data.
     *
     * @param plate             The vehicle's plate.
     * @param brand             The vehicle's brand.
     * @param model             The vehicle's model.
     * @param type              The vehicle's type.
     * @param tareWeight        The vehicle's tare weight.
     * @param grossWeight       The vehicle's gross weight.
     * @param CurrentKM         The vehicle's current kilometers.
     * @param registerDate      The vehicle's register date.
     * @param acquisitionDate   The vehicle's acquisition date.
     * @param checkupIntervalKM The vehicle's checkup interval in kilometers.
     * @param kmLastMaintenance The vehicle's kilometers at last maintenance.
     * @throws IllegalArgumentException If any of the arguments are invalid.
     */
    public Vehicle(String plate, String brand, String model, String type, int tareWeight, int grossWeight, int CurrentKM, Date registerDate, Date acquisitionDate, int checkupIntervalKM, int kmLastMaintenance) throws IllegalArgumentException {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.tareWeight = tareWeight;
        this.grossWeight = grossWeight;
        this.currentKM = CurrentKM;
        this.registerDate = registerDate;
        this.acquisitionDate = acquisitionDate;
        this.checkupIntervalKM = checkupIntervalKM;
        this.kmLastMaintenance = kmLastMaintenance;
        this.kmNextMaintenance = kmLastMaintenance + checkupIntervalKM;
        this.maintenanceList = new TreeMap<>();
        this.validateVehicle();
        this.registerMaintenance(registerDate, kmLastMaintenance);
    }

    /**
     * Instantiates a new Vehicle.
     *
     * @param plate             the plate
     * @param brand             the brand
     * @param model             the model
     * @param type              the type
     * @param tareWeight        the tare weight
     * @param grossWeight       the gross weight
     * @param CurrentKM         the current km
     * @param registerDate      the register date
     * @param acquisitionDate   the acquisition date
     * @param checkupIntervalKM the checkup interval km
     * @param kmLastMaintenance the km last maintenance
     * @param maintenanceList   the maintenance list
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Vehicle(String plate, String brand, String model, String type, int tareWeight, int grossWeight, int CurrentKM, Date registerDate, Date acquisitionDate, int checkupIntervalKM, int kmLastMaintenance, Map<Date, Integer> maintenanceList) throws IllegalArgumentException {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.tareWeight = tareWeight;
        this.grossWeight = grossWeight;
        this.currentKM = CurrentKM;
        this.registerDate = registerDate;
        this.acquisitionDate = acquisitionDate;
        this.checkupIntervalKM = checkupIntervalKM;
        this.kmLastMaintenance = kmLastMaintenance;
        this.kmNextMaintenance = kmLastMaintenance + checkupIntervalKM;
        this.maintenanceList = maintenanceList;
        this.validateVehicle();
        this.registerMaintenance(registerDate, 0);
    }

    /**
     * Gets maintenance.
     *
     * @return the maintenance
     */
    public Map<Date, Integer> getMaintenance() {
        return maintenanceList;
    }


    /**
     * Gets plate.
     *
     * @return the plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Sets plate.
     *
     * @param plate the plate
     */
    public void setPlate(String plate) {
        String old = this.plate;
        this.plate = plate;

        try {
            validateVehicle();
        } catch (IllegalArgumentException e) {
            this.plate = old;
            throw e;
        }
    }

    /**
     * Gets brand.
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets brand.
     *
     * @param brand the brand
     */
    public void setBrand(String brand) {
        String old = this.brand;
        this.brand = brand;

        try {
            validateVehicle();
        } catch (IllegalArgumentException e) {
            this.brand = old;
            throw e;
        }
        this.brand = brand;
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets model.
     *
     * @param model the model
     */
    public void setModel(String model) {
        String old = this.model;
        this.model = model;

        try {
            validateVehicle();
        } catch (IllegalArgumentException e) {
            this.model = old;
            throw e;
        }
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        String old = this.type;
        this.type = type;

        try {
            validateVehicle();
        } catch (IllegalArgumentException e) {
            this.plate = old;
            throw e;
        }
    }

    /**
     * Gets tare weight.
     *
     * @return the tare weight
     */
    public int getTareWeight() {
        return tareWeight;
    }

    /**
     * Sets tare weight.
     *
     * @param tareWeight the tare weight
     */
    public void setTareWeight(int tareWeight) {
        int old = this.tareWeight;
        this.tareWeight = tareWeight;

        try {
            validateVehicle();
        } catch (IllegalArgumentException e) {
            this.tareWeight = old;
            throw e;
        }
    }

    /**
     * Gets gross weight.
     *
     * @return the gross weight
     */
    public int getGrossWeight() {
        return grossWeight;
    }

    /**
     * Sets gross weight.
     *
     * @param grossWeight the gross weight
     */
    public void setGrossWeight(int grossWeight) {
        int old = this.grossWeight;
        this.grossWeight = grossWeight;

        try {
            validateVehicle();
        } catch (IllegalArgumentException e) {
            this.grossWeight = old;
            throw e;
        }
    }

    /**
     * Gets current km.
     *
     * @return the current km
     */
    public int getCurrentKM() {
        return currentKM;
    }

    /**
     * Sets current km.
     *
     * @param currentKM the current km
     */
    public void setCurrentKM(int currentKM) {
        int old = this.currentKM;
        this.currentKM = currentKM;

        try {
            validateVehicle();
        } catch (IllegalArgumentException e) {
            this.currentKM = old;
            throw e;
        }
    }

    /**
     * Gets register date.
     *
     * @return the register date
     */
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * Sets register date.
     *
     * @param registerDate the register date
     */
    public void setRegisterDate(Date registerDate) {
        Date old = this.registerDate;
        this.registerDate = registerDate;

        try {
            validateVehicle();
        } catch (IllegalArgumentException e) {
            this.registerDate = old;
            throw e;
        }
    }

    /**
     * Gets acquisition date.
     *
     * @return the acquisition date
     */
    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    /**
     * Sets acquisition date.
     *
     * @param acquisitionDate the acquisition date
     */
    public void setAcquisitionDate(Date acquisitionDate) {
        Date old = this.acquisitionDate;
        this.acquisitionDate = acquisitionDate;

        try {
            validateVehicle();
        } catch (IllegalArgumentException e) {
            this.acquisitionDate = old;
            throw e;
        }
    }

    /**
     * Gets checkup interval km.
     *
     * @return the checkup interval km
     */
    public int getCheckupIntervalKM() {
        return checkupIntervalKM;
    }

    /**
     * Sets checkup interval km.
     *
     * @param checkupIntervalKM the checkup interval km
     */
    public void setCheckupIntervalKM(int checkupIntervalKM) {
        int old = this.checkupIntervalKM;
        this.checkupIntervalKM = checkupIntervalKM;

        try {
            validateVehicle();
        } catch (IllegalArgumentException e) {
            this.checkupIntervalKM = old;
            throw e;
        }
    }


    /**
     * Gets km last maintenance.
     *
     * @return the km last maintenance
     */
    public int getKmLastMaintenance() {
        return kmLastMaintenance;
    }

    /**
     * Sets km last maintenance.
     *
     * @param kmLastMaintenance the km last maintenance
     */
    public void setKmLastMaintenance(int kmLastMaintenance) {
        this.kmLastMaintenance = kmLastMaintenance;
    }

    /**
     * Gets km next maintenance.
     *
     * @return the km next maintenance
     */
    public int getKmNextMaintenance() {
        return kmNextMaintenance;
    }


    /**
     * Validates the vehicle's data.
     *
     * @return true if the vehicle's data is valid, false otherwise.
     * @throws IllegalArgumentException If any of the vehicle's data is invalid.
     */
    public boolean validateVehicle() throws IllegalArgumentException {
        validateNotEmptyFields();
        validatePlateLength();
        validateCurrentKM();
        validateCheckupIntervalKM();
        validateTareWeight();
        validateGrossWeight();
        return true;
    }

    private void validateNotEmptyFields() {
        if (this.plate.isEmpty() || this.brand.isEmpty() || this.model.isEmpty() || this.type.isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled.");
        }
    }

    private void validatePlateLength() {
        if (this.plate.length() != 8) {
            throw new IllegalArgumentException("Plate must have 8 characters.");
        }
    }

    private void validateCurrentKM() {
        if (this.currentKM < 0) {
            throw new IllegalArgumentException("Current KM must be a positive number.");
        }
    }

    private void validateCheckupIntervalKM() {
        if (this.checkupIntervalKM <= 0) {
            throw new IllegalArgumentException("Checkup Interval KM must be a positive number.");
        }
    }

    private void validateTareWeight() {
        if (this.tareWeight <= 0) {
            throw new IllegalArgumentException("Tare Weight must be a positive number.");
        }
    }

    private void
    validateGrossWeight() {
        if (this.grossWeight <= 0) {
            throw new IllegalArgumentException("Gross Weight must be a positive number.");
        }
    }

    /**
     * Registers a maintenance operation for the vehicle.
     *
     * @param date The date of the maintenance operation.
     * @param km   The kilometers at the time of the maintenance operation.
     */
    public void registerMaintenance(Date date, int km) {
        if (this.maintenanceList.isEmpty() || date.after(((TreeMap<Date, Integer>) this.maintenanceList).lastKey())) {
            this.kmLastMaintenance = km;
            this.kmNextMaintenance = km + this.checkupIntervalKM;
        }
        this.maintenanceList.put(date, km);
    }

    /**
     * Gets maintenance list.
     *
     * @return the maintenance list
     */
    public List<String> getMaintenanceList() {
        List<String> maintenanceListString = new ArrayList<>();
        for (Map.Entry<Date, Integer> entry : this.maintenanceList.entrySet()) {
            maintenanceListString.add(entry.getKey().toString() + " - " + entry.getValue());
        }
        return maintenanceListString;
    }

    /**
     * Sets maintenance list.
     *
     * @param maintenenceList the maintenence list
     */
    public void setMaintenanceList(Map<Date, Integer> maintenenceList) {
        this.maintenanceList = maintenenceList;
    }


    /**
     * To string with maintenance list string.
     *
     * @return the string
     */
    public String toStringWithMaintenanceList() {

        return "Vehicle{\n" +
                "plate='" + plate + '\n' +
                ", brand='" + brand + '\n' +
                ", model='" + model + '\n' +
                ", type='" + type + '\n' +
                ", tareWeight=" + tareWeight + '\n' +
                ", grossWeight=" + grossWeight + '\n' +
                ", currentKM=" + currentKM + '\n' +
                ", registerDate=" + registerDate + '\n' +
                ", acquisitionDate=" + acquisitionDate + '\n' +
                ", checkupIntervalKM=" + checkupIntervalKM + '\n' +
                ", kmLastMaintenance=" + kmLastMaintenance + '\n' +
                ", kmNextMaintenance=" + kmNextMaintenance + '\n' +
                getMaintenanceList() + '\n' +
                '}';
    }

    @Override
    public Vehicle clone() {
        return new Vehicle(this.plate, this.brand, this.model, this.type, this.tareWeight, this.grossWeight, this.currentKM, this.registerDate, this.acquisitionDate, this.checkupIntervalKM, this.kmLastMaintenance, this.maintenanceList);
    }

    @Override
    public String toString() {
        return this.brand + " " + this.model + " (" + this.plate + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return plate.equals(vehicle.plate) &&
                brand.equals(vehicle.brand) &&
                model.equals(vehicle.model) &&
                type.equals(vehicle.type) &&
                tareWeight == vehicle.tareWeight &&
                grossWeight == vehicle.grossWeight &&
                currentKM == vehicle.currentKM &&
                registerDate.equals(vehicle.registerDate) &&
                acquisitionDate.equals(vehicle.acquisitionDate) &&
                checkupIntervalKM == vehicle.checkupIntervalKM &&
                kmLastMaintenance == vehicle.kmLastMaintenance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(plate, brand, model, type, tareWeight, grossWeight, currentKM, registerDate, acquisitionDate, checkupIntervalKM, kmLastMaintenance);
    }
}
