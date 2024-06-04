# US024 - Postpone an entry in the Agenda

## 4. Tests

**Test 1:** Returns correct list of entries

    @Test
    public void testGetAgendaEntries() {
        List<AgendaEntry> expectedEntries = new ArrayList<>();
        expectedEntries.add(new AgendaEntry("Title1", new Date()));
        expectedEntries.add(new AgendaEntry("Title2", new Date()));

        when(agendaMock.getEntries()).thenReturn(expectedEntries);

        List<AgendaEntry> actualEntries = controller.getAgendaEntries();

        assertEquals(expectedEntries, actualEntries);
    }

**Test 2:** Successfully updates the date

    @Test
    public void testUpdateDate_Success() {
        Date oldDate = new Date();
        Date newDate = new Date(oldDate.getTime() + 100000); // newDate after oldDate

        AgendaEntry entry = mock(AgendaEntry.class);
        when(entry.isAfter(newDate)).thenReturn(true);

        AgendaEntry updatedEntry = controller.updateDate(entry, newDate);

        verify(entry).setDate(newDate);
        assertEquals(entry, updatedEntry);
    }

**Test 3:** Fails to update if the new date is not after the current date

    @Test
    public void testUpdateDate_Failure() {
        Date oldDate = new Date();
        Date newDate = new Date(oldDate.getTime() - 100000); // newDate before oldDate

        AgendaEntry entry = mock(AgendaEntry.class);
        when(entry.isAfter(newDate)).thenReturn(false);

        AgendaEntry updatedEntry = controller.updateDate(entry, newDate);

        verify(entry, never()).setDate(newDate);
        assertEquals(entry, updatedEntry);
    }

## 5. Construction (Implementation)

### Class PostponeEntryController

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