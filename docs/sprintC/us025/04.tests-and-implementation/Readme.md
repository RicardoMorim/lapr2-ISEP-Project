# US025 - Cancel an entry in the Agenda

## 4. Tests

**Test 1:** Test if the status of the entry changes to CANCELLED after cancelling

    @Test
    void cancelEntryShouldChangeStatusToCancelled() {
        agendaController.cancelEntry(entry);
        assertEquals(Status.CANCELLED, entry.getStatus());
    }
}

**Test 2:** Test if the entry is still in the agenda after cancelling

    @Test
    void cancelEntryShouldStillBeInAgenda() {
        agendaController.cancelEntry(entry);
        assertTrue(agenda.getEntries().contains(entry));
    }
}

## 5. Construction (Implementation)

### Class EntryController

```java

```

### Class AgendaController

```java

```
### Class Collaborator

```java

```

## 6. Integration and Demo

* A new option on the admin menu options was added.

* For demo purposes some skills are bootstrapped while system starts.

## 7. Observations

n/a