package pt.ipp.isep.dei.esoft.project.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vehicle {
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
    private List<String> maintenanceList;


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
        this.maintenanceList = new ArrayList<>();
        this.validateVehicle();
    }

    public String getPlate() {
        return plate;
    }

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

    public String getBrand() {
        return brand;
    }

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

    public String getModel() {
        return model;
    }

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

    public String getType() {
        return type;
    }

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

    public int getTareWeight() {
        return tareWeight;
    }

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

    public int getGrossWeight() {
        return grossWeight;
    }

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

    public int getCurrentKM() {
        return currentKM;
    }

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

    public Date getRegisterDate() {
        return registerDate;
    }

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

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

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

    public int getCheckupIntervalKM() {
        return checkupIntervalKM;
    }

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


    public int getKmLastMaintenance() {
        return kmLastMaintenance;
    }

    public void setKmLastMaintenance(int kmLastMaintenance) {
        this.kmLastMaintenance = kmLastMaintenance;
    }

    public int getKmNextMaintenance() {
        return kmNextMaintenance;
    }

    public boolean validateVehicle() throws IllegalArgumentException{
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

    private void validateGrossWeight() {
        if (this.grossWeight <= 0) {
            throw new IllegalArgumentException("Gross Weight must be a positive number.");
        }
    }

    public void registerMaintenance(Date date, int km) {
        this.kmLastMaintenance = km;
        this.kmNextMaintenance = km + this.checkupIntervalKM;
        this.maintenanceList.add(date.toString() + " - " + km);

    }

    public List<String> getMaintenanceList() {
        return this.maintenanceList;
    }

    public void setMaintenanceList(List<String> maintenenceList) {
        this.maintenanceList = maintenenceList;
    }


    public String toStringWithMaintenanceList() {
        StringBuilder maintenanceListString = new StringBuilder("Maintenance List: ");
        for (String maintenance : maintenanceList) {
            maintenanceListString.append(maintenance).append("\n");
        }
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
                maintenanceListString +'\n' +
                '}';
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "plate='" + plate + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", tareWeight=" + tareWeight +
                ", grossWeight=" + grossWeight +
                ", currentKM=" + currentKM +
                ", registerDate=" + registerDate +
                ", acquisitionDate=" + acquisitionDate +
                ", checkupIntervalKM=" + checkupIntervalKM +
                ", kmLastMaintenance=" + kmLastMaintenance +
                ", kmNextMaintenance=" + kmNextMaintenance +
                '}';
    }
}


