package pt.ipp.isep.dei.esoft.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        todoEntry = Repositories.getInstance().getToDoList().getEntries().get(0);
        agenda = new Agenda();
        List<Vehicle> vehicles = Repositories.getInstance().getVehicleRepository().getVehicleList();
        vehicle = Collections.singletonList(vehicles.get(0));
        team = new Team(Collections.singletonList(Repositories.getInstance().getCollaboratorRepository().getCollaborators().get(0)));
        entry = new AgendaEntry(todoEntry, team, vehicle, "1h", new Date());
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


}