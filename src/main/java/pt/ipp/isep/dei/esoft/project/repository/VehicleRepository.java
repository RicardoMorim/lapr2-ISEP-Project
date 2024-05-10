package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.*;

public class VehicleRepository {
    private List<Vehicle> vehicleList;
    private List<String> BrandList;
    private Map<String, List<String>> brandToModelsMap;
    private List<String> TypeList;


    public VehicleRepository(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public VehicleRepository(List<Vehicle> vehicleList, List<String> BrandList, Map<String, List<String>> brandToModelsMap, List<String> TypeList) {
        this.vehicleList = vehicleList;
        this.BrandList = BrandList;
        this.brandToModelsMap = brandToModelsMap;
        this.TypeList = TypeList;
    }

    public VehicleRepository(List<String> BrandList, Map<String, List<String>> brandToModelsMap, List<String> TypeList) {
        this.vehicleList = new ArrayList<>();
        this.BrandList = BrandList;
        this.brandToModelsMap = brandToModelsMap;
        this.TypeList = TypeList;
    }

    public VehicleRepository() {
        this.vehicleList = new ArrayList<>();
        this.BrandList = new ArrayList<>();
        this.brandToModelsMap = new HashMap<>();
        this.TypeList = new ArrayList<>();
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }

    public void addVehicle(String plate, String brand, String model, String type, int tareWeight, int grossWeight, int CurrentKM, Date registerDate, Date acquisitionDate, int checkupIntervalKM, int kmLastMaintenance) {
        Vehicle vehicle = new Vehicle(plate, brand, model, type, tareWeight, grossWeight, CurrentKM, registerDate, acquisitionDate, checkupIntervalKM, kmLastMaintenance);
        vehicleList.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicleList.remove(vehicle);
    }

    public Vehicle updateVehicle(Vehicle old_vehicle, Vehicle new_vehicle) {
        vehicleList.remove(old_vehicle);
        vehicleList.add(new_vehicle);
        return new_vehicle;
    }

    public void addBrand(String brand) {
        if (!BrandList.contains(brand)) {
            BrandList.add(brand);
            brandToModelsMap.put(brand, new ArrayList<>());
            return;
        }
        throw new IllegalArgumentException("Brand already exists");
    }

    public void addModel(String model, String brand) {
        if (!brandToModelsMap.containsKey(brand))
            throw new IllegalArgumentException("Brand does not exist");

        if (!brandToModelsMap.get(brand).contains(model)) {
            brandToModelsMap.get(brand).add(model);
            return;
        }
        throw new IllegalArgumentException("Model already exists for this brand");
    }

    public List<String> getModelsForBrand(String brand) {
        return brandToModelsMap.getOrDefault(brand, new ArrayList<>());
    }

    public void addType(String type) {
        if (!TypeList.contains(type)) {
            TypeList.add(type);
            return;
        }
        throw new IllegalArgumentException("Type already exists");
    }

    public void removeBrand(String brand) {
        if (BrandList.contains(brand)) {
            BrandList.remove(brand);
            return;
        }
        throw new IllegalArgumentException("Brand does not exist");
    }

    public void removeModel(String model, String brand) {
        if (!brandToModelsMap.containsKey(brand))
            throw new IllegalArgumentException("Brand does not exist");

        if (brandToModelsMap.get(brand).contains(model)) {
            brandToModelsMap.get(brand).remove(model);
            return;
        }
        throw new IllegalArgumentException("Model does not exist for this brand");
    }

    public void removeType(String type) {
        if (TypeList.contains(type)) {
            TypeList.remove(type);
            return;
        }
        throw new IllegalArgumentException("Type does not exist");
    }


    public List<String> getTypeList() {
        return TypeList;
    }

    public void setTypeList(List<String> typeList) {
        TypeList = typeList;
    }

    public Map<String, List<String>> getBrandToModelsMap() {
        return brandToModelsMap;
    }

    public void setBrandToModelsMap(Map<String, List<String>> brandToModelsMap) {
        this.brandToModelsMap = brandToModelsMap;
    }

    public List<String> getBrandList() {
        return BrandList;
    }

    public void setBrandList(List<String> brandList) {
        BrandList = brandList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }


}