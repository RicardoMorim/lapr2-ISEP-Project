package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Agenda test.
 */
class AgendaTest {
    private Agenda agenda;
    private AgendaEntry entry1;
    private AgendaEntry entry2;
    private Vehicle vehicle1;
    private Vehicle vehicle2;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        agenda = new Agenda();
        vehicle1 = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicle2 = new Vehicle("XYZ-7890", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        agenda = new Agenda();
        GreenSpace greenSpace = new GreenSpace("Park", new Address("abacate", "morango", "1112-222"), 1000, Type.GARDEN, new Email("admin@this.app"));
        Entry entry = new Entry(greenSpace, "title", "description", Urgency.HIGH, 2.0f);
        Collaborator collaborator = new Collaborator("email@example.com", "John Doe", new Address("456 Street", "Porto", "123-456"), "123456789", new Job("Job Title", "Job Description"), new Date(), new Date(), "ID Type", 123, 456);
        Vehicle vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Team t1 = new Team(Collections.singletonList(collaborator));
        entry1 = new AgendaEntry(entry, t1, Collections.singletonList(vehicle), "10", new Date());
        entry2 = new AgendaEntry(entry, t1, Collections.singletonList(vehicle), "20", new Date());
    }

    /**
     * Add entry should add entry to agenda.
     */
    @Test
    void addEntryShouldAddEntryToAgenda() {
        agenda.addEntry(entry1);
        assertTrue(agenda.getEntries().contains(entry1));
    }

    /**
     * Add entry should not add duplicate entries.
     */
    @Test
    void addEntryShouldNotAddDuplicateEntries() {
        agenda.addEntry(entry1);

        assertThrows(IllegalArgumentException.class, () -> agenda.addEntry(entry1));
    }

    /**
     * Remove entry should remove entry from agenda.
     */
    @Test
    void removeEntryShouldRemoveEntryFromAgenda() {
        agenda.addEntry(entry1);
        agenda.removeEntry(entry1);
        assertFalse(agenda.getEntries().contains(entry1));
    }

    /**
     * Remove entry should not remove non existent entry.
     */
    @Test
    void removeEntryShouldNotRemoveNonExistentEntry() {
        agenda.addEntry(entry1);
        assertThrows(IllegalArgumentException.class, () -> agenda.removeEntry(entry2));
    }

    /**
     * Gets entries should return copy of entries.
     */
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


    /**
     * Is date available for team should return true when date is available.
     */
    @Test
    void isDateAvailableForTeamShouldReturnTrueWhenDateIsAvailable() {
        agenda.addEntry(entry1);
        assertTrue(agenda.isDateAvailableForTeam(new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000), entry2)); // 1 hour later
    }

    /**
     * Is date available for team should return false when date is not available.
     */
    @Test
    void isDateAvailableForTeamShouldReturnFalseWhenDateIsNotAvailable() {
        Date now = new Date();
        entry1.setStartDate(now);
        agenda.addEntry(entry1);
        assertFalse(agenda.isDateAvailableForTeam(now, entry2));
    }

    /**
     * Is date available for team should ignore canceled entries.
     */
    @Test
    void isDateAvailableForTeamShouldIgnoreCanceledEntries() {
        entry1.setStatus(Status.CANCELED);
        agenda.addEntry(entry1);
        assertTrue(agenda.isDateAvailableForTeam(new Date(), entry2)); // Now
    }

    /**
     * Is vehicle assigned should return true when vehicle is assigned.
     */
    @Test
    void isVehicleAssignedShouldReturnTrueWhenVehicleIsAssigned() {
        agenda.addEntry(entry1);
        assertTrue(agenda.isVehicleAssigned(entry1.getVehicles().get(0)));
    }

    /**
     * Is vehicle assigned should return false when vehicle is not assigned.
     */
    @Test
    void isVehicleAssignedShouldReturnFalseWhenVehicleIsNotAssigned() {
        agenda.addEntry(entry1);
        Vehicle vehicle = new Vehicle("XYZ-7890", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        assertFalse(agenda.isVehicleAssigned(vehicle));
    }

    /**
     * Gets vehicle by plate should return vehicle when plate exists.
     */
    @Test
    void getVehicleByPlateShouldReturnVehicleWhenPlateExists() {
        agenda.addEntry(entry1);
        assertEquals(entry1.getVehicles().get(0), agenda.getVehicleByPlate("ABC-1234"));
    }

    /**
     * Gets vehicle by plate should return null when plate does not exist.
     */
    @Test
    void getVehicleByPlateShouldReturnNullWhenPlateDoesNotExist() {
        agenda.addEntry(entry1);
        assertNull(agenda.getVehicleByPlate("XYZ-7890"));
    }

    /**
     * Gets entries with no team should return entries with no team.
     */
    @Test
    void getEntriesWithNoTeamShouldReturnEntriesWithNoTeam() {
        entry1.setTeam(null);
        agenda.addEntry(entry1);
        agenda.addEntry(entry2);
        assertEquals(Collections.singletonList(entry1), agenda.getEntriesWithNoTeam());
    }

    /**
     * Gets entries with team should return entries with team.
     */
    @Test
    void getEntriesWithTeamShouldReturnEntriesWithTeam() {
        agenda.addEntry(entry1);
        agenda.addEntry(entry2);
        assertEquals(Arrays.asList(entry1, entry2), agenda.getEntriesWithTeam());
    }

    /**
     * Test for {@link Agenda#isVehicleAssigned(Vehicle)}.
     * This test checks if the method correctly identifies whether a vehicle is assigned or not.
     */
    @Test
    void testIsVehicleAssigned() {
        agenda.addEntry(entry1);
        assertTrue(agenda.isVehicleAssigned(vehicle1));
        assertFalse(agenda.isVehicleAssigned(vehicle2));
    }


    /**
     * Test for {@link Agenda#getVehiclesNotAssignedToAnyAgendaEntry(List)}.
     * This test checks if the method correctly returns a list of vehicles that are not assigned to any agenda entry.
     */
    @Test
    void testGetVehiclesNotAssignedToAnyAgendaEntry() {
        agenda.addEntry(entry1);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        assertEquals(Arrays.asList(vehicle2), agenda.getVehiclesNotAssignedToAnyAgendaEntry(vehicles));
    }

    /**
     * Test for {@link Agenda#getVehicleByPlate(String)}.
     * This test checks if the method correctly returns a vehicle by its plate number.
     */
    @Test
    void testGetVehicleByPlate() {
        agenda.addEntry(entry1);
        assertEquals(vehicle1, agenda.getVehicleByPlate("ABC-1234"));
        assertNull(agenda.getVehicleByPlate("XYZ-7890"));
    }
}
