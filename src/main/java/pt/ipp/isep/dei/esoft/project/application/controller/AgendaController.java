package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.ArrayList;
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

    /**
     * Adds a list of vehicles to an agenda entry.
     *
     * @param agendaEntry the agenda entry to add the vehicles to
     * @param vehicles    the list of vehicles to add
     * @throws IllegalArgumentException if a vehicle is already assigned to an entry
     */
    public void addVehiclesToAgendaEntry(AgendaEntry agendaEntry, List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            if (!getAgenda().isVehicleAssigned(vehicle)) {
                agendaEntry.addVehicle(vehicle);
            } else {
                throw new IllegalArgumentException("This vehicle is already assigned to an entry.");
            }
        }
    }
    /**
     * Removes a list of vehicles from an agenda entry.
     *
     * @param agendaEntry the agenda entry to remove the vehicles from
     * @param vehicles    the list of vehicles to remove
     * @throws IllegalArgumentException if a vehicle is not assigned to any entry
     */
    public void removeVehiclesFromAgendaEntry(AgendaEntry agendaEntry, List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            if (getAgenda().isVehicleAssigned(vehicle)) {
                agendaEntry.removeVehicle(vehicle);
            } else {
                throw new IllegalArgumentException("This vehicle is not assigned to any entry.");
            }
        }
    }
    /**
     * Gets all vehicles that are not assigned to any agenda entry.
     *
     * <p>This method iterates over all agenda entries and their associated vehicles.
     * If it finds a vehicle that is not assigned to any entry, it adds that vehicle to a list.
     * After checking all vehicles, it returns the list of unassigned vehicles.</p>
     *
     * @return the list of unassigned vehicles
     */
    public List<Vehicle> getVehiclesNotAssignedToAnyAgendaEntry() {
        List<Vehicle> unassignedVehicles = new ArrayList<>();
        for (AgendaEntry entry : agenda.getEntries()) {
            for (Vehicle vehicle : entry.getVehiclesEquipment()) {
                if (!agenda.isVehicleAssigned(vehicle)) {
                    unassignedVehicles.add(vehicle);
                }
            }
        }
        return unassignedVehicles;
    }

}