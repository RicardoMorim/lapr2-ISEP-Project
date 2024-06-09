package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * The type Agenda controller test.
 */
class AgendaControllerTest {
    private AgendaController agendaController;
    private Agenda agenda;
    private AgendaEntry entry;
    private List<Vehicle> vehicle;

    private Entry todoEntry;
    private Team team;
    private Vehicle vehicle1;
    private Vehicle vehicle2;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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

}