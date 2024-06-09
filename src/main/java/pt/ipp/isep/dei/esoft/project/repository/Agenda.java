package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;

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

    /**
     * Gets entries by team.
     *
     * @param team the team
     * @return the entries by team
     */
    public List<AgendaEntry> getEntriesByTeam(Team team) {
        return this.entries.stream()
                .filter(entry -> entry.getTeam() != null && entry.getTeam().equals(team))
                .collect(Collectors.toList());
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
        List<Vehicle> vehiclesNotAssigned = new ArrayList<>(vehicles);
        for (AgendaEntry entry : entries) {
            if (entry.getStartDate().before(endDate) && entry.getEndDate().after(startDate)) {
                vehiclesNotAssigned.removeAll(entry.getVehicles());
            }
        }
        return vehiclesNotAssigned;
    }

    /**
     * Gets not done entries.
     *
     * @return the not done entries
     */
    public List<AgendaEntry> getNotDoneEntries() {
        return this.entries.stream()
                .filter(entry -> entry.getStatus() != Status.DONE)
                .collect(Collectors.toList());
    }

    /**
     * Is date available for team boolean.
     *
     * @param chosenStartDate the chosen start date
     * @param entry           the entry
     * @return the boolean
     */
    public boolean isDateAvailableForTeam(Date chosenStartDate, AgendaEntry entry) {
        Date chosenEndDate = getEndDateFromDuration(chosenStartDate, entry.getDuration());
        for (AgendaEntry e : entries) {
            if (e == entry || e.getStatus() == Status.CANCELED) {
                continue;
            }
            if (e.getTeam() == entry.getTeam() && e.getTeam() != null){
                if (compareNewDatesToEntryDate(chosenStartDate, chosenEndDate, e)) return false;
            }
        }
        return true;
    }

    /**
     * Is date available for vehicles boolean.
     *
     * @param chosenStartDate the chosen start date
     * @param entry           the entry
     * @return the boolean
     */
    public boolean isDateAvailableForVehicles(Date chosenStartDate, AgendaEntry entry) {
        Date chosenEndDate = getEndDateFromDuration(chosenStartDate, entry.getDuration());
        for (AgendaEntry e : entries) {
            if (e == entry || e.getStatus() == Status.CANCELED) {
                continue;
            }
            for (Vehicle vehicle : entry.getVehicles()) {
                if (e.getVehicles().contains(vehicle)) {
                    if (compareNewDatesToEntryDate(chosenStartDate, chosenEndDate, e)) return false;
                }
            }
        }
        return true;
    }

    private boolean compareNewDatesToEntryDate(Date chosenStartDate, Date chosenEndDate, AgendaEntry e) {
        return (chosenStartDate.compareTo(e.getStartDate()) >= 0 && chosenStartDate.compareTo(e.getEndDate()) <= 0) ||
                (chosenEndDate.compareTo(e.getStartDate()) >= 0 && chosenEndDate.compareTo(e.getEndDate()) <= 0) ||
                (chosenStartDate.compareTo(e.getStartDate()) <= 0 && chosenEndDate.compareTo(e.getEndDate()) >= 0);
    }

    /**
     * Find nearest available dates list.
     *
     * @param date  the date
     * @param entry the entry
     * @return the list
     */
    public List<Date> findNearestAvailableDates(Date date, AgendaEntry entry) {
        List<Date> suggestions = new ArrayList<>();
        int daysBefore = 1;
        int daysAfter = 1;

        // Find the nearest available date before the chosen date
        Date before = new Date(date.getTime() - daysBefore * 24 * 60 * 60 * 1000);
        while (before.after(new Date())) { // Check if the date is not in the past
            if (isDateAvailableForTeam(before, entry) && isDateAvailableForVehicles(before, entry)) {
                suggestions.add(before);
                break;
            }
            daysBefore++;
            before = new Date(date.getTime() - (long) daysBefore * 24 * 60 * 60 * 1000);
        }

        // Find the nearest available date after the chosen date
        while (suggestions.size() < 2) {
            Date after = new Date(date.getTime() + (long) daysAfter * 24 * 60 * 60 * 1000);
            if (isDateAvailableForTeam(after, entry) && isDateAvailableForVehicles(after, entry)) {
                suggestions.add(after);
            }
            daysAfter++;
        }

        return suggestions;
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
    public void updateEntry(AgendaEntry old, AgendaEntry newEntry) {
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


    /**
     * Filter unavailable teams list.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @param teams     the teams
     * @return the list
     */
    public List<Team> filterUnavailableTeams(Date startDate, Date endDate, List<Team> teams) {
        List<Team> availableTeams = new ArrayList<>(teams);

        for (AgendaEntry entry : entries) {
            if (entry.getStartDate().before(endDate) && entry.getEndDate().after(startDate)) {
                availableTeams.remove(entry.getTeam());
            }
        }

        return availableTeams;
    }


    /**
     * Gets end date from duration.
     *
     * @param startDate the start date
     * @param duration  the duration
     * @return the end date from duration
     */
    public Date getEndDateFromDuration(Date startDate, String duration) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date is required to calculate the end date");
        }

        final int hoursByDay = 8;

        java.util.Date utilStartDate = new java.util.Date(startDate.getTime());

        LocalDate start = utilStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int durationInDays = Integer.parseInt(duration) / hoursByDay;

        // If weekends should not be counted
        int weekends = (int) start.datesUntil(start.plusDays(durationInDays)).filter(d -> d.getDayOfWeek() == DayOfWeek.SATURDAY || d.getDayOfWeek() == DayOfWeek.SUNDAY).count();

        LocalDate end = start.plusDays(durationInDays + weekends);

        return Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Gets to do entries not in agenda.
     *
     * @param entries the entries
     * @return the to do entries not in agenda
     */
    public List<Entry> getToDoEntriesNotInAgenda(List<Entry> entries) {
        List<Entry> toDoEntriesNotInAgenda = new ArrayList<>(entries);
        for (AgendaEntry entry : this.entries) {
            toDoEntriesNotInAgenda.remove(entry.getEntry());
        }
        return toDoEntriesNotInAgenda;
    }

    /**
     * Gets entries with team.
     *
     * @return the entries with team
     */
    public List<AgendaEntry> getEntriesWithTeam() {
        return this.entries.stream()
                .filter(entry -> entry.getTeam() != null)
                .collect(Collectors.toList());
    }
}