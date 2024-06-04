package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AgendaEntryTest {
    private AgendaEntry agendaEntry;
    private Entry entry;
    private Team team;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        GreenSpace greenSpace = new GreenSpace("Park", new Address("porto", "maia", "1234-123"), 1000, Type.GARDEN, new Email("admin@this.app"));
        entry = new Entry("State", greenSpace, "Title", "Description", Urgency.HIGH, 2.0f);
        Collaborator collaborator = new Collaborator("email@example.com", "John Doe", new Address("456 Street", "Porto", "123-456"), "123456789", new Job("Job Title", "Job Description"), new Date(), new Date(), "ID Type", 123, 456);
        team = new Team(Arrays.asList(collaborator));
        vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);

        agendaEntry = new AgendaEntry(entry, team, Arrays.asList(vehicle), "1 hour", Status.PLANNED);
    }

    @Test
    void getTeamReturnsCorrectTeam() {
        assertEquals(team, agendaEntry.getTeam());
    }

    @Test
    void getEntryReturnsCorrectEntry() {
        assertEquals(entry, agendaEntry.getEntry());
    }

    @Test
    void getVehiclesReturnsCorrectVehicles() {
        assertEquals(Arrays.asList(vehicle), agendaEntry.getVehicles());
    }

    @Test
    void getStatusReturnsCorrectStatus() {
        assertEquals(Status.PLANNED, agendaEntry.getStatus());
    }

    @Test
    void getDurationReturnsCorrectDuration() {
        assertEquals("1 hour", agendaEntry.getDuration());
    }

    @Test
    void setEntryChangesEntry() {
        Entry newEntry = new Entry("New State", new GreenSpace("New Park", new Address("porto", "maia", "1234-123"), 1000, Type.GARDEN, new Email("admin@this.app")), "New Title", "New Description", Urgency.HIGH, 3.0f);
        agendaEntry.setEntry(newEntry);
        assertEquals(newEntry, agendaEntry.getEntry());
    }

    @Test
    void setTeamChangesTeam() {
        Collaborator newCollaborator = new Collaborator("newemail@example.com", "Jane Doe", new Address("123 Street", "Porto", "123-456"), "987654321", new Job("New Job Title", "New Job Description"), new Date(), new Date(), "New ID Type", 456, 789);
        Team newTeam = new Team(Arrays.asList(newCollaborator));
        agendaEntry.setTeam(newTeam);
        assertEquals(newTeam, agendaEntry.getTeam());
    }

    @Test
    void setVehiclesChangesVehicles() {
        Vehicle newVehicle = new Vehicle("DEF-5678", "New Brand", "New Model", "New Type", 2000, 4000, 0, new Date(), new Date(), 20000, 0);
        agendaEntry.setVehicles(Arrays.asList(newVehicle));
        assertEquals(Arrays.asList(newVehicle), agendaEntry.getVehicles());
    }

    @Test
    void setStatusChangesStatus() {
        agendaEntry.setStatus(Status.DONE);
        assertEquals(Status.DONE, agendaEntry.getStatus());
    }

    @Test
    void setDurationChangesDuration() {
        agendaEntry.setDuration("2 hours");
        assertEquals("2 hours", agendaEntry.getDuration());
    }

    @Test
    void addVehicleThrowsExceptionWhenVehicleAlreadyExists() {
        assertThrows(IllegalArgumentException.class, () -> agendaEntry.addVehicle(vehicle));
    }

    @Test
    void addVehicleAddsVehicleWhenVehicleDoesNotExist() {
        Vehicle newVehicle = new Vehicle("DEF-5678", "New Brand", "New Model", "New Type", 2000, 4000, 0, new Date(), new Date(), 20000, 0);
        agendaEntry.addVehicle(newVehicle);
        assertTrue(agendaEntry.getVehicles().contains(newVehicle));
    }

    @Test
    void removeVehicleThrowsExceptionWhenVehicleDoesNotExist() {
        Vehicle newVehicle = new Vehicle("DEF-5678", "New Brand", "New Model", "New Type", 2000, 4000, 0, new Date(), new Date(), 20000, 0);
        assertThrows(IllegalArgumentException.class, () -> agendaEntry.removeVehicle(newVehicle));
    }

    @Test
    void removeVehicleRemovesVehicleWhenVehicleExists() {
        agendaEntry.removeVehicle(vehicle);
        assertFalse(agendaEntry.getVehicles().contains(vehicle));
    }
}