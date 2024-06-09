# US020 - Register a green space

## 4. Tests
### 4.1 GreenSpaceTest

**Test 1:** These tests check if the setter methods correctly set the attributes of a GreenSpace object when provided with valid arguments.
     
    @Test
    void setName_success() {
        greenSpace.setName("Garden");
        assertEquals("Garden", greenSpace.getName());
    }

     @Test
    void setType_success() {
        greenSpace.setType(Type.GARDEN);
        assertEquals(Type.GARDEN, greenSpace.getType());
    }

    @Test
    void setAddress_success() {
        greenSpace.setAddress(new Address("New Address", "New City", "1234-123"));
        assertEquals("New Address 1234-123 New City", greenSpace.getAddress().toString());
    }
    
    @Test
    void setArea_success() {
        greenSpace.setArea(600.0f);
        assertEquals(600.0f, greenSpace.getArea());
    }

**Test 2:** These tests check if the methods throw an IllegalArgumentException when provided with invalid arguments such as an empty string for name or address, a negative area, or a zero area.
    
    @Test
    void setName_emptyName() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setName(""));
    }

    @Test
    void setAddress_emptyAddress() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setAddress(new Address("", "", "")));
    }

    @Test
    void setArea_negativeArea() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setArea(-1.0f));
    }
    
    @Test
    void validatePark_emptyFields() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setName(""));
    }
    
     @Test
    void validatePark_negativeArea() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setArea(-1.0f));
    }

    @Test
    void validatePark_emptyAddress() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setAddress(new Address("", "", "")));
    }

    @Test
    void validatePark_zeroArea() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setArea(0.0f));
    }



**Test 3:** Checks if the method correctly validates a GreenSpace object when provided with valid attributes.
    
    @Test
    void validatePark_success() {
        assertTrue(greenSpace.validatePark());
    }

**Test 4:** These tests check if the equals method correctly compares two GreenSpace objects.
    
    @Test
    void equals_sameObject() {
        assertTrue(greenSpace.equals(greenSpace));
    }

    @Test
    void equals_differentObject() {
        GreenSpace newGreenSpace = new GreenSpace("Garden", new Address("manga", "papai", "3456-789"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        assertFalse(greenSpace.equals(newGreenSpace));
    }

    @Test
    void equals_nullObject() {
        assertFalse(greenSpace.equals(null));
    }

    @Test
    void equals_differentClass() {
        assertFalse(greenSpace.equals(new Object()));
    }

    @Test
    void equals_sameNameDifferentAttributes() {
        GreenSpace newGreenSpace = new GreenSpace("Park", new Address("manga", "papai", "3456-789"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        assertTrue(greenSpace.equals(newGreenSpace));
    }

    @Test
    void equals_sameAddressDifferentAttributes() {
        GreenSpace newGreenSpace = new GreenSpace("Garden", new Address("maia", "porto", "1234-123"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        assertTrue(greenSpace.equals(newGreenSpace));
    }

**Test 5:** These tests check if the hashCode method correctly generates the same hash code for two equal GreenSpace objects.

    @Test
    void hashCode_equalObjects() {
        GreenSpace newGreenSpace = new GreenSpace("Park", new Address("maia", "porto", "1234-123"), 500.0f, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));
        assertEquals(greenSpace.hashCode(), newGreenSpace.hashCode());
    }

**Test 6:** These tests check if the toString method correctly returns a string representation of a GreenSpace object.

    @Test
    void toString_success() {
        assertEquals("Park Name = Park - Address = maia 1234-123 porto - Area = 500.0 - Type = LARGE_SIZED_PARK", greenSpace.toString());
    }

**Test 7:** These tests check if the getParkValues method correctly returns a list of strings with the values of a GreenSpace object.
    
    @Test
    void getParkValues_success() {
        List<String> expectedValues = Arrays.asList("Park", "500.0", "maia 1234-123 porto", "LARGE_SIZED_PARK");
        assertEquals(expectedValues, greenSpace.getParkValues());
    }

    @Test
    void getUser_success() {
        assertEquals(new EmailWrapper(new Email("admin@this.app")), greenSpace.getUser());
    }

### 4.2 GreenSpaceControllerTest

**Test 1:** These tests check if the addGreenSpace() method correctly adds a GreenSpace object to the repository when provided with valid arguments, and if it throws an IllegalArgumentException when trying to add a GreenSpace with a name or address that already exists in the repository.
    
     @Test
    void addGreenSpaceByNameAddressAreaType_success() {
        Email email = new Email("admin@this.app");
        controller.addGreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email);
        assertEquals(1, repository.getGreenSpaces().size());
    }

    @Test
    void addGreenSpaceByNameAddressAreaType_duplicateName() {
        Email email = new Email("admin@this.app");
        controller.addGreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email);
        assertThrows(IllegalArgumentException.class, () -> controller.addGreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email));
    }

    @Test
    void addGreenSpaceByNameAddressAreaType_duplicateAddress() {
        Email email = new Email("admin@this.app");
        controller.addGreenSpace("Park", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email);
        assertThrows(IllegalArgumentException.class, () -> controller.addGreenSpace("Park2", new Address("Street", "City", "Zip"), 500.0, Type.GARDEN, email));
    }

**Test 2:** These tests check if the checkIfGreenSpaceNameExists() and checkIfGreenSpaceAddressExists() methods return the correct boolean value when the name or address exists or does not exist in the repository.

    @Test
    void checkIfGreenSpaceNameExists_true() {
        controller.addGreenSpace(greenSpace);
        assertTrue(controller.checkIfGreenSpaceNameExists("Park"));
    }

    @Test
    void checkIfGreenSpaceNameExists_false() {
        assertFalse(controller.checkIfGreenSpaceNameExists("Park"));
    }

    @Test
    void checkIfGreenSpaceAddressExists_true() {
        controller.addGreenSpace(greenSpace);
        assertTrue(controller.checkIfGreenSpaceAddressExists(new Address("Street", "City", "Zip")));
    }

    @Test
    void checkIfGreenSpaceAddressExists_false() {
        assertFalse(controller.checkIfGreenSpaceAddressExists(new Address("Street", "City", "Zip")));
    }

**Test 3:** These tests check if the validateZipCode() method returns true when provided with a valid zip code, and false when provided with an invalid zip code.

    @Test
    void validateZipCode_valid() {
        assertTrue(controller.validateZipCode("1234-555"));
    }

    @Test
    void validateZipCode_invalid() {
        assertFalse(controller.validateZipCode("1234a"));
    }

### 4.3 GreenSpaceRepositoryTest
    
**Test 1:** These tests check if the addGreenSpace() method correctly adds a GreenSpace object to the repository when provided with a valid GreenSpace, and if it throws an IllegalArgumentException when trying to add a GreenSpace that already exists in the repository or is null.

    @Test
    void addGreenSpace_success() {
        repository.addGreenSpace(greenSpace);
        List<GreenSpace> result = repository.getGreenSpaces();
        assertTrue(result.contains(greenSpace));
    }

    @Test
    void addGreenSpace_alreadyExists() {
        repository.addGreenSpace(greenSpace);
        assertThrows(IllegalArgumentException.class, () -> repository.addGreenSpace(greenSpace));
    }

     @Test
    void addGreenSpace_nullGreenSpace() {
        assertThrows(IllegalArgumentException.class, () -> repository.addGreenSpace(null));
    }

**Test 2:** These tests check if the removeGreenSpace() method correctly removes a GreenSpace object from the repository when provided with a valid GreenSpace, and if it throws an IllegalArgumentException when trying to remove a GreenSpace that does not exist in the repository or is null.

    @Test
    void removeGreenSpace_success() {
        repository.addGreenSpace(greenSpace);
        repository.removeGreenSpace(greenSpace);
        List<GreenSpace> result = repository.getGreenSpaces();
        assertFalse(result.contains(greenSpace));
    }

    @Test
    void removeGreenSpace_doesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeGreenSpace(greenSpace));
    }

    @Test
    void removeGreenSpace_nullGreenSpace() {
        assertThrows(IllegalArgumentException.class, () -> repository.removeGreenSpace(null));
    }

**Test 3:** These tests check if the updateGreenSpace() method correctly updates a GreenSpace object in the repository when provided with a valid old and new GreenSpace, and if it throws an IllegalArgumentException when trying to update a GreenSpace that does not exist in the repository or is null.
    
    @Test
    void updateGreenSpace_success() {
        repository.addGreenSpace(greenSpace);
        GreenSpace newGreenSpace = new GreenSpace("Garden", new Address("02", "sonso", "2222-000"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        repository.updateGreenSpace(greenSpace, newGreenSpace);
        List<GreenSpace> result = repository.getGreenSpaces();
        assertTrue(result.contains(newGreenSpace));
        assertFalse(result.contains(greenSpace));
    }

    @Test
    void updateGreenSpace_doesNotExist() {
        GreenSpace newGreenSpace = new GreenSpace("Garden", new Address("rafa", "el", "9595-095"), 300.0f, Type.GARDEN, new Email("admin@this.app"));
        assertThrows(IllegalArgumentException.class, () -> repository.updateGreenSpace(greenSpace, newGreenSpace));
    }

    @Test
    void updateGreenSpace_nullGreenSpace() {
        assertThrows(IllegalArgumentException.class, () -> repository.updateGreenSpace(null, greenSpace));
    }

**Test 4:** These tests check if the getGreenSpaceByDesignation() method correctly retrieves a GreenSpace object from the repository when provided with a valid designation, and if it throws an IllegalArgumentException when trying to retrieve a GreenSpace with a designation that does not exist in the repository or is null.

    @Test
    void getGreenSpaceByDesignation_success() {
        repository.addGreenSpace(greenSpace);
        GreenSpace result = repository.getGreenSpaceByDesignation("Park");
        assertEquals(greenSpace, result);
    }

    @Test
    void getGreenSpaceByDesignation_notFound() {
        assertThrows(IllegalArgumentException.class, () -> repository.getGreenSpaceByDesignation("Park"));
    }

     @Test
    void getGreenSpaceByDesignation_nonExistingDesignation() {
        assertThrows(IllegalArgumentException.class, () -> repository.getGreenSpaceByDesignation("NonExistingPark"));
    }

    @Test
    void getGreenSpaceByDesignation_nullDesignation() {
        assertThrows(IllegalArgumentException.class, () -> repository.getGreenSpaceByDesignation(null));
    }

## 5. Construction (Implementation)

### Class GreenSpaceRepository


```java
    public void addGreenSpace(GreenSpace greenSpace) {
    if (greenSpace == null) {
        throw new IllegalArgumentException("GreenSpace cannot be null");
    }
    if (greenSpaces.contains(greenSpace))
        throw new IllegalArgumentException("Green space already exists");
    greenSpaces.add(greenSpace);
}
```

```java
    public boolean checkIfGreenSpaceNameExists(String name) {
    for (GreenSpace greenSpace : greenSpaces) {
        if (greenSpace.getName().equals(name)) {
            return true;
        }
    }
    return false;
}
```
```java
    public boolean checkIfGreenSpaceAddressExists(Address address) {
    for (GreenSpace greenSpace : greenSpaces) {
        if (greenSpace.getAddress().equals(address)) {
            return true;
        }
    }
    return false;
}
```
```java
    public boolean validateZipCode(String zipCode) {
    if (zipCode.isEmpty()) {
        return false;
    }
    String zipPattern = "^[0-9]{4}-[0-9]{3}$";
    return zipCode.matches(zipPattern);
}
```

### GreenSpace

```java
    public GreenSpace(String name, Address address, double area, Type type, Email user) {
    this.name = name;
    this.address = address;
    this.type = type;
    this.area = area;
    this.user = new EmailWrapper(user);
    validatePark();
}
```

## 6. Integration and Demo

* A new option on the admin menu options was added.
* The user can now remove or update an existing green space.
* For demo purposes some green spaces are bootstrapped while system starts.

## 7. Observations

n/a