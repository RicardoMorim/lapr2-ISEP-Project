# US006 - Register a vehicle

## 4. Tests 

### 4.1  VehicleController tests

**Test 1:** This test ensures that the addVehicle method of the vehicleController object adds a vehicle successfully by checking if the vehicle exists in the vehicleController after adding it.

	    @Test
    void addVehicleSuccessfully() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        assertTrue(vehicleController.checkIfVehicleExists(vehicle));
    }
**Test 2:** This test checks if a vehicle can be successfully removed.

    @ @Test
    void removeVehicleSuccessfully() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        vehicleController.removeVehicle(vehicle);
        assertFalse(vehicleController.checkIfVehicleExists(vehicle));
    }
**Test 3:** This test verifies that the updateVehicle method correctly replaces a vehicle in the vehicleController.

    @Test
    void updateVehicleSuccessfully() {
        Vehicle oldVehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Vehicle newVehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(oldVehicle);
        vehicleController.updateVehicle(oldVehicle, newVehicle);
        assertFalse(vehicleController.checkIfVehicleExists(oldVehicle));
        assertTrue(vehicleController.checkIfVehicleExists(newVehicle));
    }
**Test 4:** This test verifies that the getVehicleByPlate method of the vehicleController correctly retrieves a Vehicle object by its plate number.

    @Test
    void getVehicleByPlateReturnsCorrectVehicle() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        Vehicle retrievedVehicle = vehicleController.getVehicleByPlate("ABC-1234");
        assertEquals(vehicle, retrievedVehicle);
    }
**Test 5:** This test verifies that when the getVehicleByPlate method is called with a plate number that does not exist in the vehicle controller, it returns null.

     @Test
    void getVehicleByPlateReturnsNullWhenVehicleDoesNotExist() {
        Vehicle retrievedVehicle = vehicleController.getVehicleByPlate("ABC-1234");
        assertNull(retrievedVehicle);
    }
**Test 6:** This test checks if the checkIfVehicleExists method in vehicleController returns true when a specific vehicle exists in the controller after adding it.

    @Test
    void checkIfVehicleExistsReturnsTrueWhenVehicleExists() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(vehicle);
        assertTrue(vehicleController.checkIfVehicleExists(vehicle));
    }
**Test 7:** This test verifies that the checkIfVehicleExists method correctly identifies when a vehicle does not exist in the system.

    @Test
    void checkIfVehicleExistsReturnsFalseWhenVehicleDoesNotExist() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        assertFalse(vehicleController.checkIfVehicleExists(vehicle));
    }
**Test 8:** This test ensures that the checkIfVehicleExists method correctly identifies when an old vehicle exists in the vehicle controller and the new vehicle does not exist.

     @Test
    void checkIfVehicleExistsReturnsTrueWhenOldVehicleExistsAndNewVehicleDoesNotExist() {
        Vehicle oldVehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Vehicle newVehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(oldVehicle);
        assertTrue(vehicleController.checkIfVehicleExists(oldVehicle, newVehicle));
    }
**Test 9:** This test method checks if a vehicle exists in a vehicle controller. It creates two vehicle objects, adds one to the controller, and then verifies that the controller correctly identifies that the old vehicle does not exist when the new vehicle is added.


    @Test
    void checkIfVehicleExistsReturnsFalseWhenOldVehicleDoesNotExistAndNewVehicleExists() {
        Vehicle oldVehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Vehicle newVehicle = new Vehicle("XYZ-5678", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleController.addVehicle(newVehicle);
        assertFalse(vehicleController.checkIfVehicleExists(oldVehicle, newVehicle));
    }
### 4.2 Vehicle tests
These tests verify that the fields regarding vehicle characteristics are not empty and that they are valid.

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
	
### 4.3 VehicleRepository tests

**Test 1:**  This test ensures that the addVehicle method correctly adds a vehicle to the list of vehicles in the vehicleRepository.
	 
    @Test
    void addVehicleAddsVehicleToList() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleRepository.addVehicle(vehicle);
        List<Vehicle> vehicleList = vehicleRepository.getVehicleList();
        assertTrue(vehicleList.contains(vehicle));
    }

**Test 2:** This test creates a vehicle object, adds it to the vehicleRepository, removes it, and then checks if the vehicleList no longer contains the removed Vehicle.

     @Test
    void removeVehicleRemovesVehicleFromList() {
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicleRepository.addVehicle(vehicle);
        vehicleRepository.removeVehicle(vehicle);
        List<Vehicle> vehicleList = vehicleRepository.getVehicleList();
        assertFalse(vehicleList.contains(vehicle));
    }
**Test 3:** This test checks if the updateVehicle method in the vehicleRepository class correctly updates a vehicle in the list by replacing it with the details of a new vehicle.
    
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
**Test 4:** This test creates two Vehicle objects, adds them to the repository, retrieves the list of vehicles, and then asserts that the retrieved list contains the expected number of vehicles and contains the added vehicles.
    
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

## 5. Construction (Implementation)

### Class VehicleController 

```java
 public void addVehicle(Vehicle vehicle) {
    vehicleRepository.addVehicle(vehicle);
}
```

### Class Vehicle

```java
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
    this.validateVehicle();
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
```
### Class VehicleRepository
```java
public void addVehicle(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }
```

## 6. Integration and Demo 

* For demo purposes some vehicles, brands, models and types are bootstrapped while system starts.


## 7. Observations

n/a