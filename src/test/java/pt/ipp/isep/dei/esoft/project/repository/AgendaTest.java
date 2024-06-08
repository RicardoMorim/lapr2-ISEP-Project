package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.Arrays;
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

        GreenSpace greenSpace = new GreenSpace("Park", new Address("abacate", "morango", "1112-222"), 1000, Type.GARDEN, new Email("admin@this.app"));
        Entry entry = new Entry(greenSpace, "title", "description", Urgency.HIGH, 2.0f);
        Collaborator collaborator = new Collaborator("email@example.com", "John Doe", new Address("456 Street", "Porto", "123-456"), "123456789", new Job("Job Title", "Job Description"), new Date(), new Date(), "ID Type", 123, 456);
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Team t1 = new Team(Collections.singletonList(collaborator));
        entry1 = new AgendaEntry(entry, t1, Collections.singletonList(vehicle), "10", new Date());
        entry2 = new AgendaEntry(entry, t1, Collections.singletonList(vehicle), "20", new Date());
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
        var entriesBefore = new ArrayList<>(agenda.getEntries());
        agenda.removeEntry(entry1);
        var entriesAfter = agenda.getEntries();
        assertEquals(entriesBefore.size(), entriesAfter.size() + 1);
        assertTrue(entriesBefore.contains(entry1));
        assertFalse(entriesAfter.contains(entry1));
    }


    @Test
    void isDateAvailableForTeamShouldReturnTrueWhenDateIsAvailable() {
        agenda.addEntry(entry1);
        assertTrue(agenda.isDateAvailableForTeam(new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000), entry2)); // 1 hour later
    }

    @Test
    void isDateAvailableForTeamShouldReturnFalseWhenDateIsNotAvailable() {
        Date now = new Date();
        entry1.setStartDate(now);
        agenda.addEntry(entry1);
        assertFalse(agenda.isDateAvailableForTeam(now, entry2));
    }

    @Test
    void isDateAvailableForTeamShouldIgnoreCanceledEntries() {
        entry1.setStatus(Status.CANCELED);
        agenda.addEntry(entry1);
        assertTrue(agenda.isDateAvailableForTeam(new Date(), entry2)); // Now
    }

    @Test
    void isVehicleAssignedShouldReturnTrueWhenVehicleIsAssigned() {
        agenda.addEntry(entry1);
        assertTrue(agenda.isVehicleAssigned(entry1.getVehicles().get(0)));
    }

    @Test
    void isVehicleAssignedShouldReturnFalseWhenVehicleIsNotAssigned() {
        agenda.addEntry(entry1);
        Vehicle vehicle = new Vehicle("XYZ-7890", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        assertFalse(agenda.isVehicleAssigned(vehicle));
    }

    @Test
    void getVehicleByPlateShouldReturnVehicleWhenPlateExists() {
        agenda.addEntry(entry1);
        assertEquals(entry1.getVehicles().get(0), agenda.getVehicleByPlate("ABC-1234"));
    }

    @Test
    void getVehicleByPlateShouldReturnNullWhenPlateDoesNotExist() {
        agenda.addEntry(entry1);
        assertNull(agenda.getVehicleByPlate("XYZ-7890"));
    }

    @Test
    void getEntriesWithNoTeamShouldReturnEntriesWithNoTeam() {
        entry1.setTeam(null);
        agenda.addEntry(entry1);
        agenda.addEntry(entry2);
        assertEquals(Collections.singletonList(entry1), agenda.getEntriesWithNoTeam());
    }

    @Test
    void getEntriesWithTeamShouldReturnEntriesWithTeam() {
        agenda.addEntry(entry1);
        agenda.addEntry(entry2);
        assertEquals(Arrays.asList(entry1, entry2), agenda.getEntriesWithTeam());
    }
}
