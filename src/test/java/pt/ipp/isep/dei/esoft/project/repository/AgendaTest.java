package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AgendaTest {
    private Agenda agenda;
    private AgendaEntry entry1;
    private AgendaEntry entry2;

    @BeforeEach
    void setUp() {
        agenda = new Agenda();

        GreenSpace greenSpace = new GreenSpace("Park", "type", 1000, new Email("admin@this.app"));
        Entry entry = new Entry("State", greenSpace, "Title", "Description", "High", 2.0f);
        Collaborator collaborator = new Collaborator("email@example.com", "John Doe", "Address", "123456789", new Job("Job Title", "Job Description"), new Date(), new Date(), "ID Type", 123, 456);
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);

        entry1 = new AgendaEntry(entry, Collections.singletonList(collaborator), Collections.singletonList(vehicle), "1 hour", Status.PLANNED);
        entry2 = new AgendaEntry(entry, Collections.singletonList(collaborator), Collections.singletonList(vehicle), "2 hours", Status.POSTPONED);
    }

    @Test
    void addEntryShouldAddEntryToAgenda() {
        agenda.addEntry(entry1);
        assertTrue(agenda.getEntries().contains(entry1));
    }

    @Test
    void addEntryShouldNotAddDuplicateEntries() {
        agenda.addEntry(entry1);

        assertThrows(IllegalArgumentException.class, () -> agenda.addEntry(entry1));
    }

    @Test
    void removeEntryShouldRemoveEntryFromAgenda() {
        agenda.addEntry(entry1);
        agenda.removeEntry(entry1);
        assertFalse(agenda.getEntries().contains(entry1));
    }

    @Test
    void removeEntryShouldNotRemoveNonExistentEntry() {
        agenda.addEntry(entry1);
        assertThrows(IllegalArgumentException.class, () -> agenda.removeEntry(entry2));
    }

    @Test
    void getEntriesShouldReturnCopyOfEntries() {
        agenda.addEntry(entry1);
        var entriesBefore = agenda.getEntries();
        agenda.removeEntry(entry1);
        var entriesAfter = agenda.getEntries();
        assertEquals(entriesBefore.size(), entriesAfter.size() + 1);
        assertTrue(entriesBefore.contains(entry1));
        assertFalse(entriesAfter.contains(entry1));
    }
}
