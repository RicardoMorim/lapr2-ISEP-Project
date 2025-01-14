package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.util.Date;
import java.util.List;

/**
 * The type Vehicle controller.
 */
public class VehicleController {
    private VehicleRepository vehicleRepository;

    /**
     * Instantiates a new Vehicle controller.
     *
     * @param vehicleRepository the vehicle repository
     */
    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Instantiates a new Vehicle controller.
     */
    public VehicleController() {
        if (vehicleRepository == null) {
            Repositories repositories = Repositories.getInstance();

            //Get the TaskCategoryRepository
            vehicleRepository = repositories.getVehicleRepository();
        }
    }

    /**
     * Add vehicle.
     *
     * @param vehicle the vehicle
     */
    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.addVehicle(vehicle);
    }

    /**
     * Remove vehicle.
     *
     * @param vehicle the vehicle
     */
    public void removeVehicle(Vehicle vehicle) {
        vehicleRepository.removeVehicle(vehicle);
    }

    /**
     * Update vehicle vehicle.
     *
     * @param old_vehicle the old vehicle
     * @param new_vehicle the new vehicle
     * @return the vehicle
     */
    public Vehicle updateVehicle(Vehicle old_vehicle, Vehicle new_vehicle) {
        return vehicleRepository.updateVehicle(old_vehicle, new_vehicle);
    }

    /**
     * Gets vehicle repository.
     *
     * @return the vehicle repository
     */
    public VehicleRepository getVehicleRepository() {
        return vehicleRepository;
    }

    /**
     * Sets vehicle repository.
     *
     * @param vehicleRepository the vehicle repository
     */
    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;

    }

    /**
     * Gets vehicle by plate.
     *
     * @param plate the plate
     * @return the vehicle by plate
     */
    public Vehicle getVehicleByPlate(String plate) {
        for (Vehicle vehicle : vehicleRepository.getVehicleList()) {
            if (vehicle.getPlate().equals(plate)) {
                return vehicle;
            }
        }
        return null;
    }

    /**
     * Check if vehicle exists boolean.
     *
     * @param plate the plate
     * @return the boolean
     */
    public boolean checkIfVehicleExists(String plate) {
        for (Vehicle vehicle : vehicleRepository.getVehicleList()) {
            if (vehicle.getPlate().equals(plate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if vehicle exists boolean.
     *
     * @param vehicle the vehicle
     * @return the boolean
     */
    public boolean checkIfVehicleExists(Vehicle vehicle) {
        return vehicleRepository.getVehicleList().contains(vehicle);
    }

    /**
     * Check if vehicle exists boolean.
     *
     * @param old_vehicle the old vehicle
     * @param new_vehicle the new vehicle
     * @return the boolean
     */
    public boolean checkIfVehicleExists(Vehicle old_vehicle, Vehicle new_vehicle) {
        return vehicleRepository.getVehicleList().contains(old_vehicle) && !vehicleRepository.getVehicleList().contains(new_vehicle);
    }

    /**
     * Register vehicle maintenance.
     *
     * @param plate the plate
     * @param date  the date
     * @param km    the km
     */
    public void registerVehicleMaintenance(String plate, Date date, int km) {
        Vehicle vehicle = getVehicleByPlate(plate);
        vehicle.registerMaintenance(date, km);
    }

    /**
     * Register vehicle maintenance.
     *
     * @param vehicle the vehicle
     * @param date    the date
     * @param km      the km
     */
    public void registerVehicleMaintenance(Vehicle vehicle, Date date, int km) {
        vehicle.registerMaintenance(date, km);
    }


    /**
     * Gets maintenance list.
     *
     * @param vehicleList the vehicle list
     * @return the maintenance list
     */
    public List<String> getMaintenanceList(List<Vehicle> vehicleList) {

        return vehicleRepository.getMaintenanceList(vehicleList);
    }

    /**
     * Gets vehicles needing maintenance.
     *
     * @return the vehicles needing maintenance
     */
    public List<Vehicle> getVehiclesNeedingMaintenance() {
        return vehicleRepository.getVehiclesNeedingMaintenance();
    }


    /**
     * Gets brand list.
     *
     * @return the brand list
     */
    public List<String> getBrandList() {
        return vehicleRepository.getBrandList();
    }


    /**
     * Gets type list.
     *
     * @return the type list
     */
    public List<String> getTypeList() {
        return vehicleRepository.getTypeList();
    }


    /**
     * Gets vehicle list.
     *
     * @return the vehicle list
     */
    public List<Vehicle> getVehicleList() {

        return vehicleRepository.getVehicleList();
    }

    /**
     * Sets vehicle list.
     *
     * @param vehicleList the vehicle list
     */
    public void setVehicleList(List<Vehicle> vehicleList) {
        vehicleRepository.setVehicleList(vehicleList);
    }

    /**
     * Gets models for brand.
     *
     * @param brand the brand
     * @return the models for brand
     */
    public List<String> getModelsForBrand(String brand) {
        return vehicleRepository.getModelsForBrand(brand);
    }

    /**
     * Validate plate boolean.
     *
     * @param plate the plate
     * @return the boolean
     */
    public boolean validatePlate(String plate) {
        if (plate.isEmpty()) {
            return false;
        }
        String platePattern = "^[A-Za-z0-9]{2}-[A-Za-z0-9]{2}-[A-Za-z0-9]{2}$";
        return plate.matches(platePattern);
    }
}
