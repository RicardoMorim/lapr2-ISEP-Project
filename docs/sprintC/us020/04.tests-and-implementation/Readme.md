# US020 - Register a green space

## 4. Tests

**Test 1:** Checks if the setName() method correctly sets the name of a GreenSpace object when provided with a valid name or throws an IllegalArgumentException when provided with an empty string.
     
    @Test
    void setName_success() {
        greenSpace.setName("Garden");
        assertEquals("Garden", greenSpace.getName());
    }

    @Test
    void setName_emptyName() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setName(""));
    }


**Test 2:** This test checks if the setType() method correctly sets the type of GreenSpace object when provided with a valid type.

     @Test
    void setType_success() {
        greenSpace.setType(Type.GARDEN);
        assertEquals(Type.GARDEN, greenSpace.getType());
    }


**Test 3:** The first test checks if the setAddress() method correctly sets the address of a GreenSpace object when provided with a valid address and the other checks if the setAddress() method throws an IllegalArgumentException when provided with an empty address.

    @Test
    void setAddress_success() {
        greenSpace.setAddress(new Address("New Address", "New City", "1234-123"));
        assertEquals("New Address 1234-123 New City", greenSpace.getAddress().toString());
    }

    @Test
    void setAddress_emptyAddress() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setAddress(new Address("", "", "")));
    }

**Test 4:** Ensure that a skill cannot be null or have null parameters

     @Test
    void setArea_success() {
        greenSpace.setArea(600.0f);
        assertEquals(600.0f, greenSpace.getArea());
    }

    @Test
    void setArea_negativeArea() {
        assertThrows(IllegalArgumentException.class, () -> greenSpace.setArea(-1.0f));
    }


**Test 5:** Ensure an exception is thrown when a skill that the collaborator does not have is removed

    @Test
    void removeUnexistingSkill() {
        Job job = new Job("jardineiro", "jardineiro", "jardineiro");
        Collaborator col = new Collaborator("123@gmail.com", "Ricardo", "Collaborator", "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        Skill skill = new Skill("Carta A", "pode conduzir motas", "O collaborador pode conduzir qualquer mota com menos de 125cc :)");
        assertThrows(IllegalArgumentException.class, () -> {
            col.removeSkill(skill);
        });
    }



## 5. Construction (Implementation)

### Class CollaboratorController

```java
    public void addSkillToACollaborator(Skill skill, Collaborator collaborator) throws IllegalArgumentException {
    Collaborator old = collaborator.clone();
    collaborator.addSkill(skill);
    collaboratorRepository.update(old, collaborator);
}
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