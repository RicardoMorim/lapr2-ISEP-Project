# US003 - Register a collaborator and characteristics 

## 4. Tests 

    @Test
    void addCollaboratorReturnsOptionalWithCollaborator() {
        Optional<Collaborator> result = collaboratorRepository.add(collaborator);
        assertTrue(result.isPresent());
        assertEquals(collaborator, result.get());
    }

    @Test
    void addCollaboratorThrowsExceptionWhenCollaboratorAlreadyExists() {
        collaboratorRepository.add(collaborator);
        assertThrows(IllegalArgumentException.class, () -> collaboratorRepository.add(collaborator));
    }



## 5. Construction (Implementation)

### Class CollaboratorRepository 


```java
    public Optional<Collaborator> add(Collaborator collaborator) {
    Optional<Collaborator> newCollaborator = Optional.empty();
    boolean operationSuccess = false;

    if (validateCollaborator(collaborator)) {
        newCollaborator = Optional.of(collaborator);
        operationSuccess = collaborators.add(newCollaborator.get());
    }

    if (!operationSuccess) {
        newCollaborator = Optional.empty();
    }

    return newCollaborator;
}
```



## 6. Integration and Demo 

* A new option on the Employee menu options was added.

* For demo purposes some tasks are bootstrapped while system starts.


## 7. Observations

n/a