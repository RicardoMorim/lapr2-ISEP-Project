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

    public List<Vehicle> getVehiclesNotAssignedAtDates(List<Vehicle> vehicles, Date startDate, Date endDate) {
        return agenda.getVehiclesNotAssignedAtDates(vehicles, startDate, endDate);
    }

    public AgendaEntry postponeEntry(AgendaEntry entry, Date date) {
        entry.postPoneEntry(date);
        return entry;
    }

    public List<Date> findNearestAvailableDates(Date date, AgendaEntry entry) {
        return agenda.findNearestAvailableDates(date, entry);
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

    public boolean isDateAvailableForTeam(Date date, AgendaEntry entry) {
        return agenda.isDateAvailableForTeam(date, entry);
    }


    public  boolean isDateAvailableForVehicles(Date date, AgendaEntry entry) {
        return agenda.isDateAvailableForVehicles(date, entry);
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

    public List<AgendaEntry> getNotDoneEntries() {
        return agenda.getNotDoneEntries();
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



    public List<AgendaEntry> getEntries() {
        return agenda.getEntries();
    }

}

