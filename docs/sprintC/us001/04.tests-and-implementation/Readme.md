# US001 - Register a Skill 

## 4. Tests 
This test checks if the getName method of the Skill class returns the correct name. It creates a Skill object with the name "Java" and asserts that the name returned by getName is "Java".
    
    @Test
    void getNameReturnsCorrectName() {
    Skill skill = new Skill("Java", "Code");
    assertEquals("Java", skill.getName());
    }

This test checks if the getDescription method of the Skill class returns the correct description. It creates a Skill object with the description "Short Description" and asserts that the description returned by getDescription is "Short Description".
   
     @Test
    void getShortDescriptionReturnsCorrectDescription() {
        Skill skill = new Skill("Java", "Short Description");
        assertEquals("Short Description", skill.getDescription());
    }

This test checks if the getSkillValues method of the Skill class returns the correct values. It creates a Skill object with the name "Java" and description "Short Description", and asserts that the list returned by getSkillValues is equal to a list containing "Java" and "Short Description".

    @Test
    void getSkillValuesReturnsCorrectValues() {
        Skill skill = new Skill("Java", "Short Description");
        List<String> expectedValues = Arrays.asList("Java", "Short Description");
        assertEquals(expectedValues, skill.getSkillValues());
    }

This test checks if the setName method of the Skill class updates the name correctly. It creates a Skill object with the name "java", changes the name to "Python" using setName, and asserts that the new name returned by getName is "Python".

    @Test
    void setNameUpdatesName() {
        Skill skill = new Skill("java", "Code");
        skill.setName("Python");
        assertEquals("Python", skill.getName());
    }

This test checks if the setShortDescription method of the Skill class updates the description correctly. It creates a Skill object with the description "Short Description", changes the description to "Updated Short Description" using setShortDescription, and asserts that the new description returned by getDescription is "Updated Short Description".

    @Test
    void setShortDescriptionUpdatesShortDescription() {
        Skill skill = new Skill("Java", "Short Description");
        skill.setShortDescription("Updated Short Description");
        assertEquals("Updated Short Description", skill.getDescription());
    }

This test checks if the equals method of the Skill class returns true when comparing a Skill object to itself.

    @Test
    void equalsReturnsTrueForSameObject() {
        Skill skill = new Skill("java", "Code");
        assertTrue(skill.equals(skill));
    }

This test checks if the equals method of the Skill class returns false when comparing a Skill object to null.

    @Test
    void equalsReturnsFalseForNull() {
        Skill skill = new Skill("java", "Code");
        assertFalse(skill.equals(null));
    }

This test checks if the equals method of the Skill class returns true when comparing two Skill objects with the same name and description.

    @Test
    void equalsReturnsTrueForSameValues() {
        Skill skill1 = new Skill("Java", "Short Description");
        Skill skill2 = new Skill("Java", "Short Description");
        assertTrue(skill1.equals(skill2));
    }

This test checks if the hashCode method of the Skill class returns the same hash code for two Skill objects with the same name and description.

    @Test
    void hashCodeReturnsSameHashCodeForSameValues() {
        Skill skill1 = new Skill("Java", "Short Description");
        Skill skill2 = new Skill("Java", "Short Description");
        assertEquals(skill1.hashCode(), skill2.hashCode());
    }

his test checks if the getCollaboratorRepository method of the CollaboratorController class returns the correct CollaboratorRepository. It creates a CollaboratorController object with a specific CollaboratorRepository and asserts that the repository returned by getCollaboratorRepository is the same as the one used to create the controller.

    @Test
    void testGetCollaboratorRepository() {
    CollaboratorRepository expected = new CollaboratorRepository();
    CollaboratorController controller = new CollaboratorController(expected);
    assertEquals(expected, controller.getCollaboratorRepository());
    }

This test checks if the getCollaboratorList method of the CollaboratorController class returns the correct list of collaborators. It creates a CollaboratorController object with a CollaboratorRepository that contains a single Collaborator object, and asserts that the list returned by getCollaboratorList is equal to a list containing the same Collaborator object.

    @Test
    void testGetCollaboratorList() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));
        repository.add(collaborator);
        List<Collaborator> expected = List.of(collaborator);
        assertEquals(expected, controller.getCollaboratorList());
    }

This test checks if the registerCollaborator method of the CollaboratorController class correctly assigns skills to a collaborator. It creates a CollaboratorController object with a CollaboratorRepository, registers a Collaborator with a list of skills, and asserts that the skills of the registered Collaborator are equal to the list of skills used to register the collaborator.

    @Test
    void testRegisterCollaboratorWithSkills() {
        CollaboratorRepository repository = new CollaboratorRepository();
        CollaboratorController controller = new CollaboratorController(repository);
        List<Skill> skills = List.of(new Skill("Java", "Code"));
        Collaborator collaborator = controller.registerCollaborator("John Doe", "john.doe@example.com", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), skills, new Date(), new Date(), "ID", 123456, 123456);
        assertEquals(skills, collaborator.getSkills());
    }

## 5. Construction (Implementation)

### Class SkillController

```java
public void registerSkill(Skill skill) throws IllegalArgumentException {
    if (skillRepository.getSkills().contains(skill))
        throw new IllegalArgumentException("Skill already exists.");

    if (skill.getName() == null || skill.getDescription() == null)
        throw new IllegalArgumentException("Skill name or shortDescription parameters are null.");

    if (skill.getName().isEmpty() || skill.getDescription().isEmpty())
        throw new IllegalArgumentException("Skill name or short description parameters are empty.");


    if (!validateSkill(skill))
        throw new IllegalArgumentException("Skill already exists. Or it has an invalid name.");

    skillRepository.add(skill);
}

```

### Class Skill

```java
 public List<String> getSkillValues() {
    List<String> values = new ArrayList<String>();
    values.add(this.name);
    values.add(this.shortDescription);
    return values;
}

```

### Class SkillRepository

```java
public Optional<Skill> update(Skill skill, String name, String shortDescription) {
        for (Skill s : this.skills) {
            if (s.equals(skill)) {
                s.setName(name);
                s.setShortDescription(shortDescription);
                return Optional.of(skill);

            }
        }

        throw new IllegalArgumentException("Skill not found.");
    }
```

## 6. Integration and Demo 

* The user can also remove a skill from the system.

* For demo purposes some skills are bootstrapped while system starts.


## 7. Observations

n/a