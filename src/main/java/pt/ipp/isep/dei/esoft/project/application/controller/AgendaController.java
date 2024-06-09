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

    /**
     * Instantiates a new Agenda controller.
     */
    public AgendaController() {
        this.agenda = Repositories.getInstance().getAgenda();
    }

    /**
     * Gets vehicles not assigned at dates.
     *
     * @param vehicles  the vehicles
     * @param startDate the start date
     * @param endDate   the end date
     * @return the vehicles not assigned at dates
     */
    public List<Vehicle> getVehiclesNotAssignedAtDates(List<Vehicle> vehicles, Date startDate, Date endDate) {
        return agenda.getVehiclesNotAssignedAtDates(vehicles, startDate, endDate);
    }

    /**
     * Postpone entry agenda entry.
     *
     * @param entry the entry
     * @param date  the date
     * @return the agenda entry
     */
    public AgendaEntry postponeEntry(AgendaEntry entry, Date date) {
        entry.postPoneEntry(date);
        return entry;
    }

    /**
     * Find nearest available dates list.
     *
     * @param date  the date
     * @param entry the entry
     * @return the list
     */
    public List<Date> findNearestAvailableDates(Date date, AgendaEntry entry) {
        return agenda.findNearestAvailableDates(date, entry);
    }

    /**
     * Add entry.
     *
     * @param entry     the entry
     * @param startDate the start date
     * @param duration  the duration
     */
    public void addEntry(Entry entry, Date startDate, String duration) {
        agenda.addEntry(new AgendaEntry(entry, startDate, duration));
    }

    /**
     * Add entry.
     *
     * @param entry     the entry
     * @param startDate the start date
     * @param endDate   the end date
     */
    public void addEntry(Entry entry, Date startDate, Date endDate) {
        agenda.addEntry(new AgendaEntry(entry, startDate, endDate));
    }

    /**
     * Add entry.
     *
     * @param entry     the entry
     * @param startDate the start date
     * @param endDate   the end date
     * @param duration  the duration
     */
    public void addEntry(Entry entry, Date startDate, Date endDate, String duration) {
        agenda.addEntry(new AgendaEntry(entry, endDate, startDate, duration));
    }

    /**
     * Gets entries by team.
     *
     * @param team the team
     * @return the entries by team
     */
    public List<AgendaEntry> getEntriesByTeam(Team team) {
        return agenda.getEntriesByTeam(team);
    }

    /**
     * Is date available for team boolean.
     *
     * @param date  the date
     * @param entry the entry
     * @return the boolean
     */
    public boolean isDateAvailableForTeam(Date date, AgendaEntry entry) {
        return agenda.isDateAvailableForTeam(date, entry);
    }


    /**
     * Is date available for vehicles boolean.
     *
     * @param date  the date
     * @param entry the entry
     * @return the boolean
     */
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

    /**
     * Gets not done entries.
     *
     * @return the not done entries
     */
    public List<AgendaEntry> getNotDoneEntries() {
        return agenda.getNotDoneEntries();
    }


    /**
     * Assign team to entry.
     *
     * @param team  the team
     * @param entry the entry
     */
    public void assignTeamToEntry(Team team, AgendaEntry entry) {
        entry.setTeam(team);
        team.setAssigned(true);
    }


    /**
     * Add vehicles to agenda entry.
     *
     * @param agendaEntry the agenda entry
     * @param vehicle     the vehicle
     */
    public void addVehiclesToAgendaEntry(AgendaEntry agendaEntry, Vehicle vehicle) {
        agendaEntry.addVehicle(vehicle);
    }

    /**
     * Remove vehicles from agenda entry.
     *
     * @param agendaEntry the agenda entry
     * @param vehicles    the vehicles
     */
    public void removeVehiclesFromAgendaEntry(AgendaEntry agendaEntry, List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            agendaEntry.removeVehicle(vehicle);
        }
    }


    /**
     * Cancel agenda entry.
     *
     * @param entry the entry
     */
    public void cancelAgendaEntry(AgendaEntry entry) {
        entry.setStatus(Status.CANCELED);
    }


    /**
     * Gets vehicles not assigned to any agenda entry.
     *
     * @param vehicles the vehicles
     * @return the vehicles not assigned to any agenda entry
     */
    public List<Vehicle> getVehiclesNotAssignedToAnyAgendaEntry(List<Vehicle> vehicles) {
        return agenda.getVehiclesNotAssignedToAnyAgendaEntry(vehicles);
    }

    /**
     * Gets vehicles assigned to agenda entry.
     *
     * @param entry the entry
     * @return the vehicles assigned to agenda entry
     */
    public List<Vehicle> getVehiclesAssignedToAgendaEntry(AgendaEntry entry) {
        return entry.getVehicles();
    }


    /**
     * Gets entries.
     *
     * @return the entries
     */
    public List<AgendaEntry> getEntries() {
        return agenda.getEntries();
    }

}

