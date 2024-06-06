# US024 - Postpone an entry in the Agenda

## 4. Tests

**Test 1:** Test if the status of the entry changes to POSTPONED after postponing it.

    @Test
     void postponeEntryShouldChangeStatusToPostponed() {
        agendaController.postponeEntry(entry, new Date());
        assertEquals(Status.POSTPONED, entry.getStatus());
    }

**Test 2:** Tests if the date of the entry changes after postponing it.

    @Test
    void postponeEntryShouldChangeDate() {
        Date newDate = new Date();
        agendaController.postponeEntry(entry, newDate);
        assertEquals(newDate, entry.getStartDate());
    }

**Test 3:** Tests if the entry is still in the agenda after postponing it.

    @Test
    void postponeEntryShouldStillBeInAgenda() {
        agendaController.postponeEntry(entry, new Date());
        assertTrue(agenda.getEntries().contains(entry));
    }

## 5. Construction (Implementation)

### Class EntryController

```java
public class EntryController {
    private AgendaController agendaController;

    public PostponeEntryController(AgendaController agendaController) {
        this.agendaController = agendaController;
    }

    public void postponeEntry(AgendaEntry entry, Date newDate) throws IllegalArgumentException {
        AgendaEntry oldEntry = entry.clone();
        agendaController.postponeEntry(entry, newDate);
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