package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AgendaEntryTest {
    private AgendaEntry agendaEntry;
    private Entry entry;
    private Collaborator collaborator;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        GreenSpace greenSpace = new GreenSpace("Park", "Type", 1000, Type.GARDEN, new Email("admin@this.app"));
        entry = new Entry("State", greenSpace, "Title", "Description", "High", 2.0f);
        collaborator = new Collaborator("email@example.com", "John Doe", new Address("456 Street", "Porto", "123-456"), "123456789", new Job("Job Title", "Job Description"), new Date(), new Date(), "ID Type", 123, 456);
        vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);

        agendaEntry = new AgendaEntry(entry, new Team(Collections.singletonList(collaborator)), Collections.singletonList(vehicle), "1 hour", Status.PLANNED);
    }

    @Test
    void getTeamShouldReturnCorrectTeam() {
        assertEquals(Collections.singletonList(collaborator), agendaEntry.getTeam().getCollaborators());
    }

    @Test
    void getEntryShouldReturnCorrectEntry() {
        assertEquals(entry, agendaEntry.getEntry());
    }

    @Test
    void getVehiclesEquipmentShouldReturnCorrectVehiclesEquipment() {
        assertEquals(Collections.singletonList(vehicle), agendaEntry.getVehiclesEquipment());
    }

    @Test
    void getStatusShouldReturnCorrectStatus() {
        assertEquals(Status.PLANNED, agendaEntry.getStatus());
    }

    @Test
    void getDurationShouldReturnCorrectDuration() {
        assertEquals("1 hour", agendaEntry.getDuration());
    }

    @Test
    void setEntryShouldChangeEntry() {
        Entry newEntry = new Entry("New State", new GreenSpace("New Park", "type", 2000, Type.GARDEN, new Email("admin@this.app")), "New Title", "New Description", "Low", 3.0f);
        agendaEntry.setEntry(newEntry);
        assertEquals(newEntry, agendaEntry.getEntry());
    }

    @Test
    void setTeamShouldChangeTeam() {
        Collaborator newCollaborator = new Collaborator("newemail@example.com", "Jane Doe", new Address("123 Street", "Porto", "123-456"), "987654321", new Job("New Job Title", "New Job Description"), new Date(), new Date(), "New ID Type", 456, 789);
        agendaEntry.setTeam(new Team((Collections.singletonList(newCollaborator))));
        assertEquals(Collections.singletonList(newCollaborator), agendaEntry.getTeam().getCollaborators());
    }

    @Test
    void setVehiclesEquipmentShouldChangeVehiclesEquipment() {
        Vehicle newVehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        agendaEntry.setVehiclesEquipment(Collections.singletonList(newVehicle));
        assertEquals(Collections.singletonList(newVehicle), agendaEntry.getVehiclesEquipment());
    }

    @Test
    void setStatusShouldChangeStatus() {
        agendaEntry.setStatus(Status.DONE);
        assertEquals(Status.DONE, agendaEntry.getStatus());
    }

    @Test
    void setDurationShouldChangeDuration() {
        agendaEntry.setDuration("2 hours");
        assertEquals("2 hours", agendaEntry.getDuration());
    }
}