package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Entry;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Agenda.
 */
public class Agenda implements Serializable {
    private List<AgendaEntry> entries;

    /**
     * Instantiates a new Agenda.
     */
    public Agenda() {
        this.entries = new ArrayList<>();
    }

    public List<AgendaEntry> getEntriesByTeam(Team team) {
        return this.entries.stream()
                .filter(entry -> entry.getTeam() != null && entry.getTeam().equals(team))
                .collect(Collectors.toList());
    }

    /**
     * Sets entries.
     *
     * @param entries the entries
     */
    public void setEntries(List<AgendaEntry> entries) {
        this.entries = entries;
    }

    /**
     * Add entry.
     *
     * @param entry the entry
     */
    public void addEntry(AgendaEntry entry) {
        if (this.entries.contains(entry)) {
            throw new IllegalArgumentException("Entry already in Agenda");
        }
        this.entries.add(entry);
    }

    /**
     * Remove entry.
     *
     * @param entry the entry
     */
    public void removeEntry(AgendaEntry entry) {
        if (!this.entries.contains(entry)) {
            throw new IllegalArgumentException("Entry not in Agenda");
        }
        this.entries.remove(entry);
    }

    /**
     * Update entry.
     *
     * @param old      the old
     * @param newEntry the new entry
     */
    public void updateEntry(AgendaEntry old, AgendaEntry newEntry){
        removeEntry(old);
        addEntry(newEntry);
    }

    /**
     * Gets entries.
     *
     * @return the entries
     */
    public List<AgendaEntry> getEntries() {
        // This is a defensive copy, so that the entries cannot be modified from the outside.
        return entries;
    }

    /**
     * Gets entries with no team.
     *
     * @return the entries with no team
     */
    public List<AgendaEntry> getEntriesWithNoTeam() {
        List<AgendaEntry> entriesWithNoTeam = new ArrayList<>();
        for (AgendaEntry entry : entries) {
            if (entry.getTeam() == null) {
                entriesWithNoTeam.add(entry);
            }
        }
        return entriesWithNoTeam;
    }

    /**
     * Checks if a vehicle is assigned to any entry in the agenda.
     *
     * @param vehicle the vehicle to check
     * @return true if the vehicle is assigned to an entry, false otherwise
     */
    public boolean isVehicleAssigned(Vehicle vehicle) {
        for (AgendaEntry agendaEntry : this.entries) {
            if (agendaEntry.getVehicles().contains(vehicle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets vehicles not assigned to any agenda entry.
     *
     * @param vehicles the vehicles
     * @return the vehicles not assigned to any agenda entry
     */
    public List<Vehicle> getVehiclesNotAssignedToAnyAgendaEntry(List<Vehicle> vehicles) {
        List<Vehicle> vehiclesNotAssigned = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (!isVehicleAssigned(vehicle)) {
                vehiclesNotAssigned.add(vehicle);
            }
        }
        return vehiclesNotAssigned;
    }

    /**
     * Gets vehicle by plate.
     *
     * @param plate the plate
     * @return the vehicle by plate
     */
    public Vehicle getVehicleByPlate(String plate) {
        for (AgendaEntry agendaEntry : this.entries) {
            for (Vehicle vehicle : agendaEntry.getVehicles()) {
                if (vehicle.getPlate().equals(plate)) {
                    return vehicle;
                }
            }
        }
        return null;
    }


    public List<Team> filterUnavailableTeams(Date startDate, Date endDate, List<Team> teams){
        List<Team> availableTeams = new ArrayList<>(teams);

        for (AgendaEntry entry: entries){
            if (entry.getStartDate().before(endDate) && entry.getEndDate().after(startDate)){
                availableTeams.remove(entry.getTeam());
            }
        }

        return availableTeams;
    }


    public Date getEndDateFromDuration(Date startDate, String duration){
        int hoursByDay = 8;
        if (startDate == null) {
            return null;
        }

        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int durationInDays = Integer.parseInt(duration) / hoursByDay;

        // If weekends should not be counted
        int weekends = (int) start.datesUntil(start.plusDays(durationInDays)).filter(d -> d.getDayOfWeek() == DayOfWeek.SATURDAY || d.getDayOfWeek() == DayOfWeek.SUNDAY).count();

        LocalDate end = start.plusDays(durationInDays + weekends);

        return Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public List<Entry> getToDoEntriesNotInAgenda(List<Entry> entries){
        List<Entry> toDoEntriesNotInAgenda = new ArrayList<>(entries);
        for (AgendaEntry entry: this.entries){
            toDoEntriesNotInAgenda.remove(entry.getEntry());
        }
        return toDoEntriesNotInAgenda;
    }
}