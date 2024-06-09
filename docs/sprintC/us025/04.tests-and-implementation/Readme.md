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
    public class EntryController {
    private AgendaController agendaController;

    public EntryController(AgendaController agendaController) {
        this.agendaController = agendaController;
    }

    public void cancelEntry(AgendaEntry entry) throws IllegalArgumentException {
        AgendaEntry oldEntry = entry.clone();
        agendaController.cancelEntry(entry);
        agendaController.update(oldEntry, entry);
    }
}
```

### Class AgendaController

```java
    public class AgendaController {

    public void update(AgendaEntry oldEntry, AgendaEntry newEntry) {
        boolean operationSuccess = false;

        if (agenda.getEntries().contains(oldEntry)) {
            this.agenda.getEntries().remove(oldEntry);
            operationSuccess = this.agenda.getEntries().add(newEntry);
        }

        if (!operationSuccess) {
            throw new IllegalArgumentException("Entry not found.");
        }
    }
}
```
### Class AgendaEntry

```java
    public class AgendaEntry {

    public AgendaEntry clone() {
        return new AgendaEntry(this.entry, this.team, this.vehicles, this.duration, this.status, this.startDate);
    }
}
```

## 6. Integration and Demo

* A new option on the admin menu options was added.

* For demo purposes some skills are bootstrapped while system starts.

## 7. Observations

n/a