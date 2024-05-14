package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.util.*;
import java.util.stream.Collectors;

public class VehicleController {
    private VehicleRepository vehicleRepository;

    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public VehicleController() {
        if (vehicleRepository == null) {
            Repositories repositories = Repositories.getInstance();

            //Get the TaskCategoryRepository
            vehicleRepository = repositories.getVehicleRepository();
        }
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.addVehicle(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicleRepository.removeVehicle(vehicle);
    }

    public Vehicle updateVehicle(Vehicle old_vehicle, Vehicle new_vehicle) {
        return vehicleRepository.updateVehicle(old_vehicle, new_vehicle);
    }

    public VehicleRepository getVehicleRepository() {
        return vehicleRepository;
    }

    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;

    }

    public Vehicle getVehicleByPlate(String plate) {
        for (Vehicle vehicle : vehicleRepository.getVehicleList()) {
            if (vehicle.getPlate().equals(plate)) {
                return vehicle;
            }
        }
        return null;
    }

    public boolean checkIfVehicleExists(String plate) {
        for (Vehicle vehicle : vehicleRepository.getVehicleList()) {
            if (vehicle.getPlate().equals(plate)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfVehicleExists(Vehicle vehicle) {
        return vehicleRepository.getVehicleList().contains(vehicle);
    }

    public boolean checkIfVehicleExists(Vehicle old_vehicle, Vehicle new_vehicle) {
        return vehicleRepository.getVehicleList().contains(old_vehicle) && !vehicleRepository.getVehicleList().contains(new_vehicle);
    }

    public void registerVehicleMaintenance(String plate, Date date, int km) {
        Vehicle vehicle = getVehicleByPlate(plate);
        Vehicle old = vehicle.clone();
        vehicle.registerMaintenance(date, km);
        vehicleRepository.updateVehicle(old, vehicle);
    }

    public void getVehicleMaintenanceList(String plate) {
        Vehicle vehicle = getVehicleByPlate(plate);
        vehicle.getMaintenanceList();
    }


    public List<String> getMaintenanceList(List<Vehicle> vehicleList) {

        return vehicleRepository.getMaintenanceList(vehicleList);
    }

    public List<Vehicle> getVehiclesNeedingMaintenance() {
        return vehicleRepository.getVehiclesNeedingMaintenance();
    }

    public void addBrand(String brand) {
        if (brand.isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be empty.");
        }

        if (!brand.matches("[a-zA-Z]")) {
            throw new IllegalArgumentException("Brand must contain only letters.");
        }

        vehicleRepository.addBrand(brand);
    }

    public void addModel(String model, String brand) {
        if (model.isEmpty() || brand.isEmpty()) {
            throw new IllegalArgumentException("Model cannot be empty.");
        }

        if (!model.matches("[a-zA-Z]") || !brand.matches("[a-zA-Z]")) {
            throw new IllegalArgumentException("Model must contain only letters.");
        }

        vehicleRepository.addModel(model, brand);
    }

    public void addType(String type) {
        if (type.isEmpty()) {
            throw new IllegalArgumentException("Type cannot be empty.");
        }

        if (!type.matches("[a-zA-Z]")) {
            throw new IllegalArgumentException("Type must contain only letters.");
        }

        vehicleRepository.addType(type);
    }

    public List<String> getBrandList() {
        return vehicleRepository.getBrandList();
    }

    public Map<String, List<String>> getModelList() {
        return vehicleRepository.getBrandToModelsMap();
    }

    public List<String> getTypeList() {
        return vehicleRepository.getTypeList();
    }

    public void removeBrand(String brand) {
        vehicleRepository.removeBrand(brand);
    }

    public void removeModel(String model, String brand) {
        vehicleRepository.removeModel(model, brand);
    }

    public void removeType(String type) {
        vehicleRepository.removeType(type);
    }

    public void setBrandList(List<String> brandList) {
        vehicleRepository.setBrandList(brandList);
    }

    public void setModelList(Map<String, List<String>> brandToModelsMap) {
        vehicleRepository.setBrandToModelsMap(brandToModelsMap);
    }

    public void setTypeList(List<String> typeList) {
        vehicleRepository.setTypeList(typeList);
    }

    public List<Vehicle> getVehicleList() {

        return vehicleRepository.getVehicleList();
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        vehicleRepository.setVehicleList(vehicleList);
    }

    public List<String> getModelsForBrand(String brand) {
        return vehicleRepository.getModelsForBrand(brand);
    }

    public boolean validatePlate(String plate) {
        if (plate.isEmpty()) {
            return false;
        }
        String platePattern = "^[A-Za-z0-9]{2}-[A-Za-z0-9]{2}-[A-Za-z0-9]{2}$";
        return plate.matches(platePattern);
    }
}
