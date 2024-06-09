# US026 - Assign a vehicle to an entry in the agenda

## 4. Tests
### 4.1 AgendaControllerTest
**Test 1:** The test checks if the returned list of vehicles, which are not assigned to any agenda entry, matches the original list of vehicles.

    @Test
    void testGetVehiclesNotAssignedToAnyAgendaEntry() {
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        List<Vehicle> result = agendaController.getVehiclesNotAssignedToAnyAgendaEntry(vehicles);
        assertEquals(Arrays.asList(vehicle1, vehicle2), result);
    }
**Test 2:** The test checks if the returned list of vehicles, which are assigned to that agenda entry, matches the expected list of vehicles.

     @Test
    void testGetVehiclesAssignedToAgendaEntry() {
        List<Vehicle> result = agendaController.getVehiclesAssignedToAgendaEntry(entry);

        assertEquals(Arrays.asList(vehicle1, vehicle2), result);
    }

### 4.2 AgendaTest
**Test 1:** This test verifies if the isVehicleAssigned method correctly identifies whether a vehicle is assigned to an agenda entry or not.

     @Test
    void testIsVehicleAssigned() {
        agenda.addEntry(entry1);
        assertTrue(agenda.isVehicleAssigned(vehicle1));
        assertFalse(agenda.isVehicleAssigned(vehicle2));
    }
**Test 2:** This test verifies if the getVehiclesNotAssignedToAnyAgendaEntry method correctly returns the list of vehicles that are not assigned to any agenda entry.
     
    @Test
    void testGetVehiclesNotAssignedToAnyAgendaEntry() {
        agenda.addEntry(entry1);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        assertEquals(Collections.singletonList(vehicle2), agenda.getVehiclesNotAssignedToAnyAgendaEntry(vehicles));
    }
**Test 3:** This test verifies if the getVehiclesAssignedToAgendaEntry method correctly returns the list of vehicles assigned to a specific agenda entry.
   
     @Test
    void testGetVehicleByPlate() {
        agenda.addEntry(entry1);
        assertEquals(vehicle1, agenda.getVehicleByPlate("ABC-1234"));
        assertNull(agenda.getVehicleByPlate("XYZ-7890"));
    }
## 5. Construction (Implementation)

### Class Agenda

```java
    public boolean isVehicleAssigned(Vehicle vehicle) {
    for (AgendaEntry agendaEntry : this.entries) {
        if (agendaEntry.getVehicles().contains(vehicle)) {
            return true;
        }
    }
    return false;
    }

```
```java
public List<Vehicle> getVehiclesNotAssignedToAnyAgendaEntry(List<Vehicle> vehicles) {
        List<Vehicle> vehiclesNotAssigned = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (!isVehicleAssigned(vehicle)) {
                vehiclesNotAssigned.add(vehicle);
            }
        }
        return vehiclesNotAssigned;
    }
```

### Class AgendaEntry
```java
public void addVehicle(Vehicle vehicle) {
    if (this.vehicles.contains(vehicle)) {
        throw new IllegalArgumentException("Vehicle already exists in the entry");
    }
    this.vehicles.add(vehicle);
}
```
```java
public void removeVehicle(Vehicle vehicle) {
        if (!this.vehicles.contains(vehicle)) {
            throw new IllegalArgumentException("Vehicle does not exist in the entry");
        }
        this.vehicles.remove(vehicle);
    }
```
```java

```

### Class CollaboratorRepository


```java
    public Collaborator update(Collaborator oldCollaborator, Collaborator newCollaborator) {
    boolean operationSuccess = false;

    if (collaborators.contains(oldCollaborator)) {
        this.collaborators.remove(oldCollaborator);
        operationSuccess = this.collaborators.add(newCollaborator);
    }

    if (!operationSuccess) {
        throw new IllegalArgumentException("Collaborator not found.");
    }

    return newCollaborator;
}
```
### Class Collaborator

```java
    public Collaborator clone() {
        return new Collaborator(this.email, this.name, this.address, this.phone, this.job, this.birthDate, this.admissionDate , this.IDtype, this.taxpayerNumber, this.citizenNumber, new ArrayList<>(this.skills));
    }

    public List<Skill> addSkill(Skill skill) {
        if (this.skills.contains(skill)) {
            throw new IllegalArgumentException("Collaborator already contains the skill");
        }
        if (skill.getSkillValues().contains(null)) {
            throw new IllegalArgumentException("No parameter of the skill cannot be null");
        }
        this.skills.add(skill);
    
        return this.skills;
    }
```

## 6. Integration and Demo

* A new option on the admin menu options was added.

* For demo purposes some skills are bootstrapped while system starts.

## 7. Observations

n/a