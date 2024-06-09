package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.*;
/**
 * The VehicleRepository class is responsible for managing a collection of Vehicle objects.
 * It provides methods for adding, removing, and updating vehicles, as well as managing vehicle brands, models, and types.
 * It also provides methods for sorting vehicles and determining which vehicles need maintenance.
 */
public class VehicleRepository {
    private List<Vehicle> vehicleList;
    private List<String> BrandList;
    private Map<String, List<String>> brandToModelsMap;
    private List<String> TypeList;
    private final float percentageToNeedMaintenance = 0.85F;

    /**
     * Constructs a new VehicleRepository with the given list of vehicles.
     * @param vehicleList the list of vehicles
     */
    public VehicleRepository(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }
    /**
     * Constructs a new VehicleRepository with the given list of vehicles, brands, models, and types.
     * @param vehicleList the list of vehicles
     * @param BrandList the list of brands
     * @param brandToModelsMap the map of brands to models
     * @param TypeList the list of types
     */
    public VehicleRepository(List<Vehicle> vehicleList, List<String> BrandList, Map<String, List<String>> brandToModelsMap, List<String> TypeList) {
        this.vehicleList = vehicleList;
        this.BrandList = BrandList;
        this.brandToModelsMap = brandToModelsMap;
        this.TypeList = TypeList;
    }

    /**
     * Constructs a new VehicleRepository with the given brands, models, and types.
     * @param BrandList the list of brands
     * @param brandToModelsMap the map of brands to models
     * @param TypeList the list of types
     */
    public VehicleRepository(List<String> BrandList, Map<String, List<String>> brandToModelsMap, List<String> TypeList) {
        this.vehicleList = new ArrayList<>();
        this.BrandList = BrandList;
        this.brandToModelsMap = brandToModelsMap;
        this.TypeList = TypeList;
    }

    /**
     * Constructs a new VehicleRepository with no initial data.
     */
    public VehicleRepository() {
        this.vehicleList = new ArrayList<>();
        this.BrandList = new ArrayList<>();
        this.brandToModelsMap = new HashMap<>();
        this.TypeList = new ArrayList<>();
    }

    /**
     * Returns the list of vehicles in the repository.
     * @return the list of vehicles
     */
    public List<Vehicle> getVehicleList() {
        return this.vehicleList;
    }
    /**
     * Adds a vehicle to the repository.
     * @param vehicle the vehicle to add
     * @throws IllegalArgumentException if the vehicle already exists in the repository
     */
    public void addVehicle(Vehicle vehicle) {
        if (vehicleList.contains(vehicle))
            throw new IllegalArgumentException("Vehicle already exists");
        vehicleList.add(vehicle);
    }

    /**
     * Adds a vehicle to the repository.
     * @param plate the vehicle's plate
     * @param brand the vehicle's brand
     * @param model the vehicle's model
     * @param type the vehicle's type
     * @param tareWeight the vehicle's tare weight
     * @param grossWeight the vehicle's gross weight
     * @param CurrentKM the vehicle's current kilometers
     * @param registerDate the vehicle's register date
     * @param acquisitionDate the vehicle's acquisition date
     * @param checkupIntervalKM the vehicle's checkup interval in kilometers
     * @param kmLastMaintenance the vehicle's kilometers at last maintenance
     * @throws IllegalArgumentException if the vehicle already exists in the repository
     */
    public void addVehicle(String plate, String brand, String model, String type, int tareWeight, int grossWeight, int CurrentKM, Date registerDate, Date acquisitionDate, int checkupIntervalKM, int kmLastMaintenance) {
        Vehicle vehicle = new Vehicle(plate, brand, model, type, tareWeight, grossWeight, CurrentKM, registerDate, acquisitionDate, checkupIntervalKM, kmLastMaintenance);
        if (vehicleList.contains(vehicle))
            throw new IllegalArgumentException("Vehicle already exists");
        vehicleList.add(vehicle);
    }

    /**
     * Removes a vehicle from the repository.
     * @param vehicle the vehicle to remove
     * @throws IllegalArgumentException if the vehicle does not exist in the repository
     */
    public void removeVehicle(Vehicle vehicle) {
        if (!vehicleList.contains(vehicle))
            throw new IllegalArgumentException("Vehicle does not exist");
        vehicleList.remove(vehicle);
    }
    /**
     * Updates a vehicle in the repository.
     * @param old_vehicle the vehicle to replace
     * @param new_vehicle the vehicle to replace with
     * @return the new vehicle
     * @throws IllegalArgumentException if the old vehicle does not exist in the repository
     */
    public Vehicle updateVehicle(Vehicle old_vehicle, Vehicle new_vehicle) {
        if (!vehicleList.contains(old_vehicle))
            throw new IllegalArgumentException("Vehicle does not exist");
        vehicleList.remove(old_vehicle);
        vehicleList.add(new_vehicle);
        return new_vehicle;
    }

    /**
     * Adds a brand to the repository.
     * @param brand the brand to add
     * @throws IllegalArgumentException if the brand already exists in the repository
     */
    public void addBrand(String brand) {
        if (!BrandList.contains(brand)) {
            BrandList.add(brand);
            brandToModelsMap.put(brand, new ArrayList<>());
            return;
        }
        throw new IllegalArgumentException("Brand already exists");
    }

    /**
     * Adds a model to a specific brand in the repository.
     * @param model the model to add
     * @param brand the brand to which the model will be added
     * @throws IllegalArgumentException if the brand does not exist or the model already exists for this brand
     */
    public void addModel(String model, String brand) {
        if (!brandToModelsMap.containsKey(brand))
            throw new IllegalArgumentException("Brand does not exist");

        if (!brandToModelsMap.get(brand).contains(model)) {
            brandToModelsMap.get(brand).add(model);
            return;
        }
        throw new IllegalArgumentException("Model already exists for this brand");
    }

    /**
     * Returns a list of models for a specific brand.
     * @param brand the brand for which to get the models
     * @return the list of models for the specified brand
     */
    public List<String> getModelsForBrand(String brand) {
        return brandToModelsMap.getOrDefault(brand, new ArrayList<>());
    }

    /**
     * Adds a type to the repository.
     * @param type the type to add
     * @throws IllegalArgumentException if the type already exists
     */
    public void addType(String type) {
        if (!TypeList.contains(type)) {
            TypeList.add(type);
            return;
        }
        throw new IllegalArgumentException("Type already exists");
    }

    /**
     * Removes a brand from the repository.
     * @param brand the brand to remove
     * @throws IllegalArgumentException if the brand does not exist
     */
    public void removeBrand(String brand) {
        if (BrandList.contains(brand)) {
            BrandList.remove(brand);
            return;
        }
        throw new IllegalArgumentException("Brand does not exist");
    }

    /**
     * Removes a model from a specific brand in the repository.
     * @param model the model to remove
     * @param brand the brand from which the model will be removed
     * @throws IllegalArgumentException if the brand does not exist or the model does not exist for this brand
     */
    public void removeModel(String model, String brand) {
        if (!brandToModelsMap.containsKey(brand))
            throw new IllegalArgumentException("Brand does not exist");

        if (brandToModelsMap.get(brand).contains(model)) {
            brandToModelsMap.get(brand).remove(model);
            return;
        }
        throw new IllegalArgumentException("Model does not exist for this brand");
    }

    /**
     * Removes a type from the repository.
     * @param type the type to remove
     * @throws IllegalArgumentException if the type does not exist
     */
    public void removeType(String type) {
        if (TypeList.contains(type)) {
            TypeList.remove(type);
            return;
        }
        throw new IllegalArgumentException("Type does not exist");
    }


    /**
     * Returns a list of types in the repository.
     * @return the list of types
     */
    public List<String> getTypeList() {
        return TypeList;
    }

    /**
     * Sets the list of types in the repository.
     * @param typeList the list of types to set
     */
    public void setTypeList(List<String> typeList) {
        TypeList = typeList;
    }
    /**
     * Returns a map of brands to models in the repository.
     * @return the map of brands to models
     */
    public Map<String, List<String>> getBrandToModelsMap() {
        return brandToModelsMap;
    }

    /**
     * Sets the map of brands to models in the repository.
     * @param brandToModelsMap the map of brands to models to set
     */
    public void setBrandToModelsMap(Map<String, List<String>> brandToModelsMap) {
        this.brandToModelsMap = brandToModelsMap;
    }
    /**
     * Returns a list of brands in the repository.
     * @return the list of brands
     */
    public List<String> getBrandList() {
        return BrandList;
    }
    /**
     * Sets the list of brands in the repository.
     * @param brandList the list of brands to set
     */
    public void setBrandList(List<String> brandList) {
        BrandList = brandList;
    }

    /**
     * Sets the list of vehicles in the repository.
     * @param vehicleList the list of vehicles to set
     */
    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }
    /**
     * Sorts a list of vehicles based on their remaining kilometers to next maintenance and their kilometers at last maintenance.
     * @param vehicles the list of vehicles to sort
     * @return the sorted list of vehicles
     */
    public List<Vehicle> bubbleSortVehicles(List<Vehicle> vehicles) {
        int n = vehicles.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Vehicle currentVehicle = vehicles.get(j);
                Vehicle nextVehicle = vehicles.get(j + 1);
                int currentVehicleRemainingKM = currentVehicle.getKmNextMaintenance() - currentVehicle.getCurrentKM();
                int nextVehicleRemainingKM = nextVehicle.getKmNextMaintenance() - nextVehicle.getCurrentKM();
                if (currentVehicleRemainingKM > nextVehicleRemainingKM ||
                        (currentVehicleRemainingKM == nextVehicleRemainingKM &&
                                currentVehicle.getKmLastMaintenance() > nextVehicle.getKmLastMaintenance())) {
                    // swap vehicles[j+1] and vehicles[j]
                    swapVehicles(vehicles, j, j + 1);
                }
            }
        }
        return vehicles;
    }
    /**
     * Swaps the positions of two vehicles in the list.
     * @param vehicles the list of vehicles
     * @param i the index of the first vehicle
     * @param j the index of the second vehicle
     */
    private void swapVehicles(List<Vehicle> vehicles, int i, int j) {
        Vehicle temp = vehicles.get(i);
        vehicles.set(i, vehicles.get(j));
        vehicles.set(j, temp);
    }
    /**
     * Returns a list of vehicles that need maintenance.
     * @return the list of vehicles that need maintenance
     */
    public List<Vehicle> getVehiclesNeedingMaintenance() {
        List<Vehicle> sortedVehicles = bubbleSortVehicles(getVehicleList());
        sortedVehicles.removeIf((vehicle -> vehicle.getCurrentKM() - vehicle.getKmLastMaintenance() < vehicle.getCheckupIntervalKM() * percentageToNeedMaintenance));
        return sortedVehicles;
    }
    /**
     * Returns a list of vehicle information for vehicles that need maintenance.
     * @param vehicles the list of vehicles to get the information for
     * @return the list of vehicle information
     */
    public List<String> getMaintenanceList(List<Vehicle> vehicles) {
        List<String> maintenanceList = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            String vehicleInfo = "Plate: " + vehicle.getPlate() +
                    ", Brand: " + vehicle.getBrand() +
                    ", Model: " + vehicle.getModel() +
                    ", CurrentKM: " + vehicle.getCurrentKM() +
                    ", Checkup Frequency: " + vehicle.getCheckupIntervalKM() +
                    ", KM of Last Checkup: " + vehicle.getKmLastMaintenance() +
                    ", KM of Next Checkup: " + vehicle.getKmNextMaintenance() +
                    ", Maximum number of KM until next checkup: " + (vehicle.getKmNextMaintenance() - vehicle.getCurrentKM());
            maintenanceList.add(vehicleInfo);
        }
        return maintenanceList;
    }
}
