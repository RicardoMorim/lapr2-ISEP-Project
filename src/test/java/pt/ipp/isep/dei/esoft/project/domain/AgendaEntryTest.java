package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Agenda entry test.
 */
class AgendaEntryTest {
    private AgendaEntry agendaEntry;
    private Entry entry;
    private Team team;
    private Vehicle vehicle;

    /**
     * Gets next working day.
     *
     * @param date the date
     * @return the next working day
     */
    public static Date getNextWorkingDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 2);
        } else if (dayOfWeek == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return calendar.getTime();
    }

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        GreenSpace greenSpace = new GreenSpace("Park", new Address("porto", "maia", "1234-123"), 1000, Type.GARDEN, new Email("admin@this.app"));
        entry = new Entry(greenSpace, "Title", "Description", Urgency.HIGH, 2.0f);
        Collaborator collaborator = new Collaborator("email@example.com", "John Doe", new Address("456 Street", "Porto", "123-456"), "123456789", new Job("Job Title", "Job Description"), new Date(), new Date(), "ID Type", 123, 456);
        team = new Team(Arrays.asList(collaborator));
        vehicle = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        Date startDate = getNextWorkingDay(new Date());
        agendaEntry = new AgendaEntry(entry, team, Arrays.asList(vehicle), "10", startDate);
    }

    /**
     * Gets team returns correct team.
     */
    @Test
    void getTeamReturnsCorrectTeam() {
        assertEquals(team, agendaEntry.getTeam());
    }

    /**
     * Gets entry returns correct entry.
     */
    @Test
    void getEntryReturnsCorrectEntry() {
        assertEquals(entry, agendaEntry.getEntry());
    }

    /**
     * Gets vehicles returns correct vehicles.
     */
    @Test
    void getVehiclesReturnsCorrectVehicles() {
        assertEquals(Arrays.asList(vehicle), agendaEntry.getVehicles());
    }

    /**
     * Gets status returns correct status.
     */
    @Test
    void getStatusReturnsCorrectStatus() {
        assertEquals(Status.PLANNED, agendaEntry.getStatus());
    }

    /**
     * Gets duration returns correct duration.
     */
    @Test
    void getDurationReturnsCorrectDuration() {
        assertEquals("10", agendaEntry.getDuration());
    }

    /**
     * Sets entry changes entry.
     */
    @Test
    void setEntryChangesEntry() {
        Entry newEntry = new Entry(new GreenSpace("New Park", new Address("porto", "maia", "1234-123"), 1000, Type.GARDEN, new Email("admin@this.app")), "New Title", "New Description", Urgency.HIGH, 3.0f);
        agendaEntry.setEntry(newEntry);
        assertEquals(newEntry, agendaEntry.getEntry());
    }

    /**
     * Sets team changes team.
     */
    @Test
    void setTeamChangesTeam() {
        Collaborator newCollaborator = new Collaborator("newemail@example.com", "Jane Doe", new Address("123 Street", "Porto", "123-456"), "987654321", new Job("New Job Title", "New Job Description"), new Date(), new Date(), "New ID Type", 456, 789);
        Team newTeam = new Team(Arrays.asList(newCollaborator));
        agendaEntry.setTeam(newTeam);
        assertEquals(newTeam, agendaEntry.getTeam());
    }

    /**
     * Sets vehicles changes vehicles.
     */
    @Test
    void setVehiclesChangesVehicles() {
        Vehicle newVehicle = new Vehicle("DEF-5678", "New Brand", "New Model", "New Type", 2000, 4000, 0, new Date(), new Date(), 20000, 0);
        agendaEntry.setVehicles(Arrays.asList(newVehicle));
        assertEquals(Arrays.asList(newVehicle), agendaEntry.getVehicles());
    }

    /**
     * Sets status changes status.
     */
    @Test
    void setStatusChangesStatus() {
        agendaEntry.setStatus(Status.DONE);
        assertEquals(Status.DONE, agendaEntry.getStatus());
    }

    /**
     * Sets duration changes duration.
     */
    @Test
    void setDurationChangesDuration() {
        agendaEntry.setDuration("2");
        assertEquals("2", agendaEntry.getDuration());
    }

    /**
     * Add vehicle throws exception when vehicle already exists.
     */
    @Test
    void addVehicleThrowsExceptionWhenVehicleAlreadyExists() {
        assertThrows(IllegalArgumentException.class, () -> agendaEntry.addVehicle(vehicle));
    }

    /**
     * Add vehicle adds vehicle when vehicle does not exist.
     */
    @Test
    void addVehicleAddsVehicleWhenVehicleDoesNotExist() {
        Vehicle newVehicle = new Vehicle("DEF-5678", "New Brand", "New Model", "New Type", 2000, 4000, 0, new Date(), new Date(), 20000, 0);
        agendaEntry.addVehicle(newVehicle);
        assertTrue(agendaEntry.getVehicles().contains(newVehicle));
    }

    /**
     * Remove vehicle throws exception when vehicle does not exist.
     */
    @Test
    void removeVehicleThrowsExceptionWhenVehicleDoesNotExist() {
        Vehicle newVehicle = new Vehicle("DEF-5678", "New Brand", "New Model", "New Type", 2000, 4000, 0, new Date(), new Date(), 20000, 0);
        assertThrows(IllegalArgumentException.class, () -> agendaEntry.removeVehicle(newVehicle));
    }

    /**
     * Remove vehicle removes vehicle when vehicle exists.
     */
    @Test
    void removeVehicleRemovesVehicleWhenVehicleExists() {
        agendaEntry.removeVehicle(vehicle);
        assertFalse(agendaEntry.getVehicles().contains(vehicle));
    }

    /**
     * Gets end date from duration returns correct end date.
     */
//TODO fix these tests
    @Test
    void getEndDateFromDurationReturnsCorrectEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(agendaEntry.getStartDate());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date expectedEndDate = calendar.getTime();
        assertEquals(expectedEndDate, agendaEntry.getEndDateFromDuration());
    }

    /**
     * Gets duration from end date returns correct duration.
     */
    @Test
    void getDurationFromEndDateReturnsCorrectDuration() {
        String expectedDuration = "8";
        assertEquals(expectedDuration, agendaEntry.getDurationFromEndDate());
    }

    /**
     * Post pone entry changes start date and status.
     */
    @Test
    void postPoneEntryChangesStartDateAndStatus() {
        Date newStartDate = new Date(agendaEntry.getStartDate().getTime() + 7200000);
        agendaEntry.postPoneEntry(newStartDate);
        assertEquals(newStartDate, agendaEntry.getStartDate());
        assertEquals(Status.POSTPONED, agendaEntry.getStatus());
    }
}