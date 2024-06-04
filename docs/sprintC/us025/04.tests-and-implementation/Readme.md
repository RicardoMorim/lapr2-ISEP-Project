# US025 - Cancel an entry in the Agenda

## 4. Tests

**Test 1:** Cancel exiting entry

    @Test
    public void testCancelEntry() {
        // Create a mock of the Agenda
        Agenda agendaMock = mock(Agenda.class);
        // Create a mock of the Entry
        Entry entryMock = mock(Entry.class);
        // Assume getStatus method returns CANCELED
        when(entryMock.getStatus()).thenReturn(Status.CANCELED);
        // Assume getEntries method returns a list with one entry
        when(agendaMock.getEntries()).thenReturn(Collections.singletonList(entryMock));

        // Create an instance of CancelUI with the mocked Agenda
        CancelUI cancelUI = new CancelUI(agendaMock);

        // Call the method to cancel an entry
        cancelUI.cancelEntry(entryMock);

        // Verify that the status of the entry is set to CANCELED
        verify(entryMock).setStatus(Status.CANCELED);
    }
}

**Test 2:** Cancel non-existing entry

    @Test
    public void testCancelNonExistingEntry() {
        Agenda agendaMock = mock(Agenda.class);
        Entry entryMock = mock(Entry.class);
        // Assume getEntries method returns an empty list
        when(agendaMock.getEntries()).thenReturn(Collections.emptyList());

        // Create an instance of CancelUI with the mocked Agenda
        CancelUI cancelUI = new CancelUI(agendaMock);

        // Call the method to cancel an entry
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cancelUI.cancelEntry(entryMock);
        });

        // Verify that the exception message is as expected
        assertEquals("Entry not found in the agenda.", exception.getMessage());
    }
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