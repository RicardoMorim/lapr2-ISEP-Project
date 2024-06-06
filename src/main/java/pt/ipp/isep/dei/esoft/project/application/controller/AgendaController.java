package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

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
        entry.postPoneEntry(date);
        return entry;
    }

    public void addEntry(Entry entry, Date startDate, String duration) {
        agenda.addEntry(new AgendaEntry(entry, startDate, duration));
    }

    public void addEntry(Entry entry, Date startDate, Date endDate) {
        agenda.addEntry(new AgendaEntry(entry, startDate, endDate));
    }

    public void addEntry(Entry entry, Date startDate, Date endDate, String duration) {
        agenda.addEntry(new AgendaEntry(entry, endDate, startDate, duration));
    }

    public List<AgendaEntry> getEntriesByTeam(Team team) {
        return agenda.getEntriesByTeam(team);
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

    public List<AgendaEntry> getEntriesWithTeam(){
        return agenda.getEntriesWithTeam();
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

    public Date getEndDateFromDuration(Date startDate, String duration) {
        return agenda.getEndDateFromDuration(startDate, duration);
    }

    public List<Team> getAvailableTeams(Date startDate, Date endDate, List<Team> teams) {
        return agenda.filterUnavailableTeams(startDate, endDate, teams);
    }

    public List<AgendaEntry> getEntries() {
        return agenda.getEntries();
    }


    public List<Entry> getToDoEntriesNotInAgenda(List<Entry> entries){
        return agenda.getToDoEntriesNotInAgenda(entries);
    }
}

