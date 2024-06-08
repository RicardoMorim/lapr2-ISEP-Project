# US022 - Add an entry to the Agenda

## 4. Tests

**Test 1:** Ensure that a new entry is created in the Agenda

    @Test
    void addEntryToAgenda(){
        Agenda agenda = new Agenda();
        Entry entry = new Entry("Meeting", "Meeting with the team", new Date(), new Date());
        agenda.addEntry(new AgendaEntry(entry, new Date(), 20));
        assertEquals(entry, agenda.getEntries().get(0).getEntry());
    }

**Test 2:** Ensure that the dates are correct

   
    @test
    void addEntryToAgendaWithDates(){
        Agenda agenda = new Agenda();
        Date startDate = new Date();
        Date endDate = new Date();
        Entry entry = new Entry("Meeting", "Meeting with the team", Urgency.NORMAL);
        agenda.addEntry(new AgendaEntry(entry, startDate, endDate));
        assertEquals(startDate, agenda.getEntries().get(0).getStartDate());
        assertEquals(endDate, agenda.getEntries().get(0).getEndDate());
    }

**Test 3:** Ensure that an end date is properly calculated from the start date and duration
  
    @Test
    void addEntryToAgendaWithDuration(){
        Agenda agenda = new Agenda();
        Date startDate = new Date();
        Entry entry = new Entry("Meeting", "Meeting with the team", Urgency.NORMAL);
        agenda.addEntry(new AgendaEntry(entry, startDate, 10));
        Date endDate = new Date(startDate.getTime() + 24 * 60 * 60 * 1000);
        assertEquals(endDate, agenda.getEntries().get(0).getEndDate());
    }


**Test 4:** Ensure that a duration can be calculated 

    @Test
    void addEntryToAgendaWithDuration(){
        Agenda agenda = new Agenda();
        Date startDate = new Date();
        Date endDate = startDate+24*60*60*1000;
        Entry entry = new Entry("Meeting", "Meeting with the team", Urgency.NORMAL);
        agenda.addEntry(new AgendaEntry(entry, startDate, endDate));
        assertEquals(10, agenda.getEntries().get(0).getDuration());
    }

**Test 5:** Ensure that the proper status is added to the entry when in progress

    @Test
    void addEntryToAgendaWithDuration(){
        Agenda agenda = new Agenda();
        Date startDate = new Date()-24*60*60*1000;
        Date endDate = startDate+2*24*60*60*1000;
        Entry entry = new Entry("Meeting", "Meeting with the team", Urgency.NORMAL);
        agenda.addEntry(new AgendaEntry(entry, startDate, endDate));
        assertEquals(Status.IN_PROGRESS, agenda.getEntries().get(0).getStatus());
    }

**Test 6:** Ensure that the proper status is added to the entry when planned


    @Test
    void addEntryToAgendaWithDuration(){
        Agenda agenda = new Agenda();
        Date startDate = new Date()+24*60*60*1000;
        Date endDate = startDate+3*24*60*60*1000;
        Entry entry = new Entry("Meeting", "Meeting with the team", Urgency.NORMAL);
        agenda.addEntry(new AgendaEntry(entry, startDate, endDate));
        assertEquals(Status.PLANNED, agenda.getEntries().get(0).getStatus());
    }



## 5. Construction (Implementation)

### Class Agenda

```java
public void addEntry(AgendaEntry entry) {
    this.entries.add(entry);
}
```

### Class AgendaEntry

```java
public AgendaEntry(Entry entry, Date startDate, Date endDate) {
    this.entry = entry;
    this.startDate = startDate;
    this.endDate = endDate;
    this.duration = getDurationFromdates();
    this.status = getStatusFromDates();
}
```

```java
public AgendaEntry(Entry entry, Date startDate, String duration) {
    this.entry = entry;
    this.startDate = startDate;
    this.duration = duration;
    this.endDate = getEndDateFromDuration;
    this.status = getStatusFromDates();
}
```

```java
public AgendaEntry(Entry entry, Date startDate, Date endDate, String duration) {
    this.entry = entry;
    this.startDate = startDate;
    this.endDate = endDate;
    this.duration = duration;
    this.status = getStatusFromDates();
}
```

## Class Agenda Controller
```java
public addEntryToAgenda(Entry entry, Date startDate, Date endDate){
    AgendaEntry agendaEntry = new AgendaEntry(entry, startDate, endDate);
    agenda.addEntry(agendaEntry);
}
```

```java
public addEntryToAgenda(Entry entry, Date startDate, String duration){
    AgendaEntry agendaEntry = new AgendaEntry(entry, startDate, duration);
    agenda.addEntry(agendaEntry);
}
```

```java
public addEntryToAgenda(Entry entry, Date startDate, Date endDate, String duration){
    AgendaEntry agendaEntry = new AgendaEntry(entry, startDate, endDate, duration);
    agenda.addEntry(agendaEntry);
}
```




## 6. Integration and Demo

* A new option on the admin and GRM menu options was added.

* For demo purposes some skills are bootstrapped while system starts.

## 7. Observations

n/a