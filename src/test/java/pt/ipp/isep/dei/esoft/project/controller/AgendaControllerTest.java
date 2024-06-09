package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Agenda controller test.
 */
class AgendaControllerTest {
    private AgendaController agendaController;
    private Agenda agenda;
    private AgendaEntry entry;

    private Entry todoEntry;
    private Team team;
    private Vehicle vehicle1;
    private Vehicle vehicle2;
    private DateTimeFormatter formatter;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        GreenSpace greenSpace = new GreenSpace("Cidade", new Address("Rua da Cidade", "Porto", "1234-456"), 10000, Type.LARGE_SIZED_PARK, new Email("grm@this.app"));
        todoEntry = new Entry(greenSpace, "entry", "e isso", Urgency.HIGH, 2);
        agenda = new Agenda();
        List<Skill> list3 = new ArrayList<>();
        Job job = new Job("Gardener", "The collaborator prunes the grass every week");
        Collaborator col1 = new Collaborator("ricardo@gmail.com", "ricardo", new Address("Rua do Ricardo", "Porto", "123-456"), "913456789", job, new Date(), new Date(), "CC", 13456789, 12345678, new ArrayList<>(list3));
        List<Collaborator> collaborators = new ArrayList<>();
        collaborators.add(col1);
        vehicle1 = new Vehicle("AA-00-00", "BMW", "X1", "SUV", 2000, 2500, 1000, Date.from(LocalDate.parse("01-12-2023", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("01-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 0);
        vehicle2 = new Vehicle("AA-00-01", "Mercedes", "A", "Sedan", 1500, 2000, 60000, Date.from(LocalDate.parse("02-01-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("02-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 50000);
        List<Vehicle> vehicles = new ArrayList<>(Arrays.asList(vehicle1, vehicle2));
        team = new Team(collaborators);
        entry = new AgendaEntry(todoEntry, team, vehicles, "1", new Date());
        agendaController = new AgendaController(agenda);
    }


    /**
     * Postpone entry should change status to postponed.
     */
    @Test
    void postponeEntryShouldChangeStatusToPostponed() {
        agendaController.postponeEntry(entry, new Date());
        assertEquals(Status.POSTPONED, entry.getStatus());
    }

    /**
     * Cancel agenda entry should change status to canceled.
     */
    @Test
    void cancelAgendaEntryShouldChangeStatusToCanceled() {
        agendaController.cancelAgendaEntry(entry);
        assertEquals(Status.CANCELED, entry.getStatus());
    }

    /**
     * Test for {@link AgendaController#getVehiclesNotAssignedToAnyAgendaEntry(List)}.
     * This test checks if the method correctly returns a list of vehicles that are not assigned to any agenda entry.
     */
    @Test
    void testGetVehiclesNotAssignedToAnyAgendaEntry() {
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        List<Vehicle> result = agendaController.getVehiclesNotAssignedToAnyAgendaEntry(vehicles);
        assertEquals(Arrays.asList(vehicle1, vehicle2), result);
    }

    /**
     * Test for {@link AgendaController#getVehiclesAssignedToAgendaEntry(AgendaEntry)}.
     * This test checks if the method correctly returns a list of vehicles that are assigned to a specific agenda entry.
     */
    @Test
    void testGetVehiclesAssignedToAgendaEntry() {
        List<Vehicle> result = agendaController.getVehiclesAssignedToAgendaEntry(entry);

        assertEquals(Arrays.asList(vehicle1, vehicle2), result);
    }

    /**
     * Add entry should add entry to agenda.
     */
    @Test
    void addEntryShouldAddEntryToAgenda() {
        AgendaEntry newEntry = new AgendaEntry(todoEntry, team, Arrays.asList(vehicle1, vehicle2), "1", new Date());
        agendaController.addEntry(newEntry);
        assertTrue(agenda.getEntries().contains(newEntry));
    }

    /**
     * Remove entry should remove entry from agenda.
     */
    @Test
    void removeEntryShouldRemoveEntryFromAgenda() {
        AgendaEntry newEntry = new AgendaEntry(todoEntry, team, Arrays.asList(vehicle1, vehicle2), "1", new Date());
        agendaController.addEntry(newEntry);
        agendaController.removeEntry(newEntry);
        assertFalse(agenda.getEntries().contains(newEntry));
    }

    /**
     * Gets agenda should return agenda.
     */
    @Test
    void getAgendaShouldReturnAgenda() {
        assertEquals(agenda, agendaController.getAgenda());
    }

    /**
     * Assign team to entry should set team and assigned status.
     */
    @Test
    void assignTeamToEntryShouldSetTeamAndAssignedStatus() {
        Team newTeam = new Team(Arrays.asList(new Collaborator("test@gmail.com", "test", new Address("Rua do Test", "Porto", "123-456"), "913456789", new Job("Gardener", "The collaborator prunes the grass every week"), new Date(), new Date(), "CC", 13456789, 12345678, new ArrayList<>())));
        agendaController.assignTeamToEntry(newTeam, entry);
        assertEquals(newTeam, entry.getTeam());
        assertTrue(newTeam.isAssigned());
    }

    /**
     * Add vehicles to agenda entry should add vehicle.
     */
    @Test
    void addVehiclesToAgendaEntryShouldAddVehicle() {
        Vehicle newVehicle = new Vehicle("AA-00-02", "Audi", "A4", "Sedan", 1500, 2000, 60000, Date.from(LocalDate.parse("02-01-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("02-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 50000);
        agendaController.addVehiclesToAgendaEntry(entry, newVehicle);
        assertTrue(entry.getVehicles().contains(newVehicle));
    }

    /**
     * Remove vehicles from agenda entry should remove vehicles.
     */
    @Test
    void removeVehiclesFromAgendaEntryShouldRemoveVehicles() {
        Vehicle newVehicle1 = new Vehicle("AA-00-03", "Audi", "A5", "Sedan", 1500, 2000, 60000, Date.from(LocalDate.parse("02-01-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("02-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 50000);
        Vehicle newVehicle2 = new Vehicle("AA-00-04", "Audi", "A6", "Sedan", 1500, 2000, 60000, Date.from(LocalDate.parse("02-01-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.parse("02-02-2024", formatter).atStartOfDay(ZoneId.systemDefault()).toInstant()), 10000, 50000);
        List<Vehicle> vehicles = Arrays.asList(newVehicle1, newVehicle2);
        entry.addVehicle(newVehicle1);
        entry.addVehicle(newVehicle2);
        agendaController.removeVehiclesFromAgendaEntry(entry, vehicles);
        assertFalse(entry.getVehicles().contains(newVehicle1));
        assertFalse(entry.getVehicles().contains(newVehicle2));
    }


    /**
     * When add entry with start date end date duration then entry is added to agenda.
     */
    @Test
    void whenAddEntryWithStartDateEndDateDuration_thenEntryIsAddedToAgenda() {
        AgendaController controller = new AgendaController(new Agenda());
        GreenSpace greenSpace = new GreenSpace("Cidade", new Address("Rua da Cidade", "Porto", "1234-456"), 10000, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));
        Entry entry = new Entry(greenSpace, "entry", "e isso", Urgency.HIGH, 2);
        Date startDate = new Date();
        Date endDate = new Date();
        String duration = "1h";

        // When
        controller.addEntry(entry, startDate, endDate, duration);

        // Then
        List<AgendaEntry> entries = controller.getEntries();
        assertEquals(1, entries.size());
        assertEquals(entry, entries.get(0).getEntry());
        assertEquals(startDate, entries.get(0).getStartDate());
        assertEquals(endDate, entries.get(0).getEndDate());
        assertEquals(duration, entries.get(0).getDuration());
    }

    /**
     * When remove entry then entry is removed from agenda.
     */
    @Test
    void whenRemoveEntry_thenEntryIsRemovedFromAgenda() {
        // Given
        AgendaController controller = new AgendaController(new Agenda());

        GreenSpace greenSpace = new GreenSpace("Cidade", new Address("Rua da Cidade", "Porto", "1234-456"), 10000, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));
        Entry entry = new Entry(greenSpace, "entry", "e isso", Urgency.HIGH, 2);
        Date startDate = new Date();
        Date endDate = new Date();
        String duration = "1h";
        AgendaEntry agendaEntry = new AgendaEntry(entry, startDate, endDate, duration);
        controller.addEntry(agendaEntry);

        // When
        controller.removeEntry(agendaEntry);

        // Then
        assertTrue(controller.getEntries().isEmpty());
    }

    /**
     * When get vehicles not assigned at dates then returns correct vehicles.
     */
    @Test
    void whenGetVehiclesNotAssignedAtDates_thenReturnsCorrectVehicles() {
        // Given
        AgendaController controller = new AgendaController(new Agenda());
        vehicle1 = new Vehicle("ABC-1234", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        vehicle2 = new Vehicle("XYZ-7890", "Brand", "Model", "Type", 1000, 2000, 0, new Date(), new Date(), 10000, 0);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        Date startDate = new Date();
        Date endDate = new Date();

        // When
        List<Vehicle> result = controller.getVehiclesNotAssignedAtDates(vehicles, startDate, endDate);

        // Then
        assertEquals(vehicles, result);
    }

    /**
     * When postpone entry then entry date is updated.
     */
    @Test
    void whenPostponeEntry_thenEntryDateIsUpdated() {
        // Given
        AgendaController controller = new AgendaController(new Agenda());

        GreenSpace greenSpace = new GreenSpace("Cidade", new Address("Rua da Cidade", "Porto", "1234-456"), 10000, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));
        Entry entry = new Entry(greenSpace, "entry", "e isso", Urgency.HIGH, 2);
        Date startDate = new Date();
        Date endDate = new Date();
        String duration = "1";
        AgendaEntry agendaEntry = new AgendaEntry(entry, startDate, endDate, duration);

        Date newDate = new Date();
        controller.addEntry(agendaEntry);

        // When
        controller.postponeEntry(agendaEntry, newDate);

        // Then
        assertEquals(newDate, agendaEntry.getStartDate());
    }

    /**
     * When assign team to entry then entry team is updated.
     */
    @Test
    void whenAssignTeamToEntry_thenEntryTeamIsUpdated() {
        // Given
        AgendaController controller = new AgendaController(new Agenda());
        GreenSpace greenSpace = new GreenSpace("Cidade", new Address("Rua da Cidade", "Porto", "1234-456"), 10000, Type.LARGE_SIZED_PARK, new Email("admin@this.app"));

        Entry entry = new Entry(greenSpace, "entry", "e isso", Urgency.HIGH, 2);
        Date startDate = new Date();
        Date endDate = new Date();
        String duration = "1h";
        AgendaEntry agendaEntry = new AgendaEntry(entry, startDate, endDate, duration);
        Collaborator collaborator = new Collaborator("john.doe@example.com", "John Doe", new Address("123 Street", "Porto", "123-456"), "1234567890", new Job("Developer", "java developer"), new Date(), new Date(), "ID", 123456, 123456, List.of(new Skill("Java", "Code")));

        Team team = new Team(Collections.singletonList(collaborator));
        controller.addEntry(agendaEntry);

        // When
        controller.assignTeamToEntry(team, agendaEntry);

        // Then
        assertEquals(team, agendaEntry.getTeam());
    }

    /**
     * Test is date available for team.
     */
    @Test
    void testIsDateAvailableForTeam() {
        // Given
        Date date = new Date();
        // When
        boolean result = agendaController.isDateAvailableForTeam(date, entry);
        // Then
        assertTrue(result);
    }

    /**
     * Test is date available for vehicles.
     */
    @Test
    void testIsDateAvailableForVehicles() {
        // Given
        Date date = new Date();
        // When
        boolean result = agendaController.isDateAvailableForVehicles(date, entry);
        // Then
        assertTrue(result);
    }

    /**
     * Test get not done entries.
     */
    @Test
    void testGetNotDoneEntries() {
        // Given
        // When
        List<AgendaEntry> result = agendaController.getNotDoneEntries();
        // Then
        assertNotNull(result);
    }

    /**
     * Test get entries.
     */
    @Test
    void testGetEntries() {
        // Given
        // When
        List<AgendaEntry> result = agendaController.getEntries();
        // Then
        assertNotNull(result);
    }
}