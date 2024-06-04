# US021 - Add a new toDoEntry to the To-Do List

## 4. Tests

**Test 1:** Ensure that a skill can be added

    @Test
    void addSkill() {
        Job job = new Job("jardineiro", "jardineiro", "jardineiro");
        Collaborator col = new Collaborator("123@gmail.com", "Ricardo", "Collaborator", "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        Skill skill = new Skill("Carta A", "pode conduzir motas", "O collaborador pode conduzir qualquer mota com menos de 125cc :)");
        List<Skill> newSkills = col.addSkill(skill);
        Skill addedSkill = newSkills.get(0);
        Skill colaboratorSkill = col.getSkills().get(0);
        assertEquals(skill, addedSkill);
        assertEquals(skill, colaboratorSkill);
    }

**Test 2:** Ensure that a skill added in the constructor can be removed

    @Test
    void removeSkillTestWithSkillsAddedThroughTheConstructor() {
        Job job = new Job("jardineiro", "jardineiro", "jardineiro");
        Skill skill = new Skill("Carta A", "pode conduzir motas", "O collaborador pode conduzir qualquer mota com menos de 125cc");
        List<Skill> skills = new ArrayList<>();
        skills.add(skill);
        Collaborator col = new Collaborator("123@gmail.com", "Ricardo", "Collaborator", "908767", job, new Date(), new Date(), "ID", 123456, 78910, skills);
        assertEquals(skill, col.getSkills().get(0));
        col.removeSkill(skill);
        assertTrue(col.getSkills().isEmpty());
    }

**Test 3:** Ensure that a skill added later to the collaborator can be removed

    @Test
    void removeSkillWithSkillsAddedLater() {
        Job job = new Job("jardineiro", "jardineiro", "jardineiro");
        Collaborator col = new Collaborator("123@gmail.com", "Ricardo", "Collaborator", "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        Skill skill = new Skill("Carta A", "pode conduzir motas", "O collaborador pode conduzir qualquer mota com menos de 125cc");
        col.addSkill(skill);
        assertEquals(skill, col.getSkills().get(0));
        col.removeSkill(skill);
        assertTrue(col.getSkills().isEmpty());
    }

**Test 4:** Ensure that a skill cannot be null or have null parameters

    @Test
    void addNullSkill() {
        Job job = new Job("jardineiro", "jardineiro", "jardineiro");
        Collaborator col = new Collaborator("123@gmail.com", "Ricardo", "Collaborator", "908767", job, new Date(), new Date(), "ID", 123456, 78910, new ArrayList<>());
        Skill AllNull = new Skill(null, null, null);
        assertThrows(IllegalArgumentException.class, () -> {
            col.addSkill(AllNull);
        });

        Skill nameNull = new Skill(null, "Desc", "Descrição");
        assertThrows(IllegalArgumentException.class, () -> {
            col.addSkill(nameNull);
        });
        Skill DescNull = new Skill("nome", "Desc", null);
        assertThrows(IllegalArgumentException.class, () -> {
            col.addSkill(DescNull);
        });
        Skill ShortDescNull = new Skill("nome", null, "Descrição");
        assertThrows(IllegalArgumentException.class, () -> {
            col.addSkill(ShortDescNull);
        });
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