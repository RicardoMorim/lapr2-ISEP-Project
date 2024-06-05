# US003 - Register a collaborator and characteristics

## 4. Tests

**Test 1:** Ensure that a collaborator can be added


    @Test
    void addCollaboratorReturnsOptionalWithCollaborator() {
        Optional<Collaborator> result = collaboratorRepository.add(collaborator);
        assertTrue(result.isPresent());
        assertEquals(collaborator, result.get());
    }


**Test 2:** Ensure that a collaborator cannot be added if it already exists


    @Test
    void addCollaboratorThrowsExceptionWhenCollaboratorAlreadyExists() {
        collaboratorRepository.add(collaborator);
        assertThrows(IllegalArgumentException.class, () -> collaboratorRepository.add(collaborator));
    }


## 5. Construction (Implementation)

### Class CollaboratorController


    public void addCollaborator(Collaborator collaborator) throws IllegalArgumentException {
        Collaborator old = collaborator.clone();
        collaboratorRepository.update(old, collaborator);
    }


### Class CollaboratorRepository


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

## 6. Integration and Demo

* A new option on the Employee menu options was added.
* For demo purposes some tasks are bootstrapped while system starts.

## 7. Observations

n/a
