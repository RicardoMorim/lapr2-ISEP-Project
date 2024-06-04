package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Status;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Agenda controller.
 */
public class AgendaController {
    private Agenda agenda;

    /**
     * Instantiates a new Agenda controller.
     *
     * @param agenda the agenda
     */
    public AgendaController(Agenda agenda) {
        this.agenda = agenda;
    }

    public AgendaController() {
        this.agenda = Repositories.getInstance().getAgenda();
    }

    public AgendaEntry postponeEntry(AgendaEntry entry, Date date) {
        entry.setStatus(Status.POSTPONED);
        entry.setDate(date);
        return entry;
    }

    /**
     * Add entry.
     *
     * @param entry the entry
     */
    public void addEntry(AgendaEntry entry) {
        agenda.addEntry(entry);
    }

    /**
     * Remove entry.
     *
     * @param entry the entry
     */
    public void removeEntry(AgendaEntry entry) {
        agenda.removeEntry(entry);
    }

    /**
     * Gets agenda.
     *
     * @return the agenda
     */
    public Agenda getAgenda() {
        return agenda;
    }


    public void assignTeamToEntry(Team team, AgendaEntry entry) {
        entry.setTeam(team);
        team.setAssigned(true);
    }


    public void addVehiclesToAgendaEntry(AgendaEntry agendaEntry, Vehicle vehicle) {
        agendaEntry.addVehicle(vehicle);
    }

    public void removeVehiclesFromAgendaEntry(AgendaEntry agendaEntry, List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            agendaEntry.removeVehicle(vehicle);
        }
    }


    public void cancelAgendaEntry(AgendaEntry entry) {
        entry.setStatus(Status.CANCELED);
    }


    public List<Vehicle> getVehiclesNotAssignedToAnyAgendaEntry(List<Vehicle> vehicles) {
        return agenda.getVehiclesNotAssignedToAnyAgendaEntry(vehicles);
    }

    public List<Vehicle> getVehiclesAssignedToAgendaEntry(AgendaEntry entry) {
        return entry.getVehicles();
    }

    public void updateVehicle(Vehicle vehicle, Date date, int km) {
        agenda.getVehicleByPlate(vehicle.getPlate()).registerMaintenance(date, km);
    }
}

