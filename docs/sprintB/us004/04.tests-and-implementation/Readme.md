# US004 - Add a Skill to a Collaborator

## 4. Tests

**Test 1:** Check that it is not possible to add a null Skill to a null collaborator.

	@Test(expected = IllegalArgumentException.class)
		public void ensureCanNotAddNullSkill() {
		
        Collaborator collaborator = new Collaborator("Ricardo","28/10/2005" ,"23/04/2024", "rua", "962345321", "112334123","CC", "1231412312");
        collaborator.addSkill(null);
	}

**Test 2:** Check that it is not possible to add a skill that the collaborator already has

	@Test(expected = IllegalArgumentException.class)
		public void ensureNoDoubledSkills() {
		Skill instance = new Skill("B type driving license", "Collaborator is able to drive a car", "Collaborator can drive all cars inside the B category");
        Collaborator collaborator = new Collaborator("Ricardo","28/10/2005" ,"23/04/2024", "rua", "962345321", "112334123","CC", "1231412312");
        collaborator.addSkill(instance);
        collaborator.addSkill(instance);
	}


**Test 3:** Check that it is possible to add a skill to a collaborator
    
        @Test
            public void ensureCanAddSkill() {
            Skill instance = new Skill("B type driving license", "Collaborator is able to drive a car", "Collaborator can drive all cars inside the B category");
            Collaborator collaborator = new Collaborator("Ricardo","28/10/2005" ,"23/04/2024", "rua", "962345321", "112334123","CC", "1231412312");
            collaborator.addSkill(instance);
            assertTrue(collaborator.getSkills().contains(instance));
        }

## 5. Construction (Implementation)

### Class CreateSkillController

```java
public void addSkill(Skill skill, Collaborator collaborator) {
    if (skill == null || collaborator == null) {
        throw new IllegalArgumentException("Skill and collaborator must not be null");
    }
    if (collaborator.getSkills().contains(skill)) {
        throw new IllegalArgumentException("Collaborator already has this skill");
    }
    
    collaborator.addSkill(skill);
    
}
```

### Class Organization

TODO

```java
public Optional<Task> createTask(String reference, String description, String informalDescription,
                                 String technicalDescription, Integer duration, Double cost, TaskCategory taskCategory,
                                 Employee employee) {

    Task task = new Task(reference, description, informalDescription, technicalDescription, duration, cost,
            taskCategory, employee);

    addTask(task);

    return task;
}
```

## 6. Integration and Demo



## 7. Observations

n/a