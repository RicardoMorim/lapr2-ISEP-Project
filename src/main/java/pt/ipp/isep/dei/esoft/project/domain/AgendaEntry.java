package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * The type Agenda entry.
 */
public class AgendaEntry implements Serializable {
    private Team team;
    private List<Vehicle> vehicles;
    private String duration;
    private Status status;
    private Entry entry;
    private Date startDate;
    private Date endDate;
    private final int hoursByDay = 8;

    /**
     * Instantiates a new Agenda entry.
     *
     * @param entry    the entry
     * @param team     the team
     * @param vehicles the vehicles equipment
     * @param duration the duration
     * @param status   the status
     */
    public AgendaEntry(Entry entry, Team team, List<Vehicle> vehicles, String duration, Status status, Date startDate) {
        this.entry = entry;
        this.team = team;
        this.vehicles = vehicles;
        this.duration = duration;
        this.status = status;
        this.startDate = startDate;
        this.endDate = getEndDateFromDuration();
    }

    public AgendaEntry(Entry entry, Team team, List<Vehicle> vehicles, Status status, Date startDate, Date endDate) {
        this.entry = entry;
        this.team = team;
        this.vehicles = vehicles;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = this.getDurationFromEndDate();
    }

    public AgendaEntry(Entry entry, List<Vehicle> vehicles, String duration, Status status, Date startDate) {
        this.entry = entry;
        this.team = null;
        this.vehicles = vehicles;
        this.duration = duration;
        this.status = status;
        this.startDate = startDate;
        this.endDate = getEndDateFromDuration();
    }

    public AgendaEntry(Entry entry, Team team, String duration, Status status, Date startDate) {
        this.entry = entry;
        this.team = team;
        this.vehicles = new ArrayList<>();
        this.duration = duration;
        this.status = status;
        this.startDate = startDate;
        this.endDate = getEndDateFromDuration();
    }

    public AgendaEntry(Entry entry, String duration, Status status, Date startDate) {
        this.entry = entry;
        this.team = null;
        this.vehicles = new ArrayList<>();
        this.duration = duration;
        this.status = status;
        this.startDate = startDate;
        this.endDate = getEndDateFromDuration();
    }

    public AgendaEntry(Entry entry, Date startDate, String duration) {
        this.entry = entry;
        this.team = null;
        this.vehicles = new ArrayList<>();
        this.startDate = startDate;
        this.duration = duration;
        this.endDate = getEndDateFromDuration();
        this.status = getStatusBasedOnDates();
        this.entry.setState(this.status);
    }


    public AgendaEntry(Entry entry, Date startDate, Date endDate) {
        this.entry = entry;
        this.team = null;
        this.vehicles = new ArrayList<>();
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = getDurationFromEndDate();
        this.status = getStatusBasedOnDates();
        this.entry.setState(this.status);
    }

    public AgendaEntry(Entry entry, Date startDate, Date endDate, String duration) {
        this.entry = entry;
        this.team = null;
        this.vehicles = new ArrayList<>();
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.status = getStatusBasedOnDates();
        this.entry.setState(this.status);
    }


    public void postPoneEntry(Date startDate){
        this.startDate = startDate;
        this.status = Status.POSTPONED;
        this.endDate = getEndDateFromDuration();
    }

    public Status getStatusBasedOnDates() {

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates are required to calculate the status");
        }

        java.util.Date utilStartDate = new java.util.Date(startDate.getTime());
        java.util.Date utilEndDate = new java.util.Date(endDate.getTime());


        LocalDate start = utilStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = utilEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate now = LocalDate.now();
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        if (now.isBefore(start) && this.status != Status.POSTPONED && this.status != Status.CANCELED ) {
            return Status.PLANNED;
        }
        if (now.isAfter(end) && this.status != Status.CANCELED) {
            return Status.DONE;
        }
        if (now.isAfter(start) && now.isBefore(end) && this.status != Status.CANCELED) {
            return Status.IN_PROGRESS;
        }

        return status;


    }


    public Date getEndDateFromDuration() {
        if (startDate == null) {
            return null;
        }

        java.util.Date utilStartDate = new java.util.Date(startDate.getTime());
        
        LocalDate start = utilStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int durationInDays = Integer.parseInt(duration) / hoursByDay;

        // If weekends should not be counted
        int weekends = (int) start.datesUntil(start.plusDays(durationInDays)).filter(d -> d.getDayOfWeek() == DayOfWeek.SATURDAY || d.getDayOfWeek() == DayOfWeek.SUNDAY).count();

        LocalDate end = start.plusDays(durationInDays + weekends);

        return Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public String getDurationFromEndDate() {
        if (endDate == null) {
            throw new IllegalArgumentException("End date is required to calculate the duration");
        }

        java.util.Date utilStartDate = new java.util.Date(startDate.getTime());
        java.util.Date utilEndDate = new java.util.Date(endDate.getTime());

        LocalDate start = utilStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = utilEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long daysBetween = ChronoUnit.DAYS.between(start, end);

        long businessDays = Stream.iterate(start, date -> date.plusDays(1))
                .limit(daysBetween)
                .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY)
                .count();

        long hours = businessDays * hoursByDay;

        return String.valueOf(hours);
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        if (dateToConvert instanceof java.sql.Date) {
            return ((java.sql.Date) dateToConvert).toLocalDate();
        } else {
            return dateToConvert.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets team.
     *
     * @return the team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Gets entry.
     *
     * @return the entry
     */
    public Entry getEntry() {
        return entry;
    }

    /**
     * Sets entry.
     *
     * @param entry the entry
     */
    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    /**
     * Sets team.
     *
     * @param team the team
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Gets vehicles equipment.
     *
     * @return the vehicles equipment
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets vehicles equipment.
     *
     * @param vehicles the vehicles equipment
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Entry name = " + entry.getTitle();
    }

    /**
     * Adds a vehicle to this agenda entry.
     *
     * @param vehicle the vehicle to add
     * @throws IllegalArgumentException if the vehicle is already in the list
     */
    public void addVehicle(Vehicle vehicle) {
        if (this.vehicles.contains(vehicle)) {
            throw new IllegalArgumentException("Vehicle already exists in the entry");
        }
        this.vehicles.add(vehicle);
    }

    /**
     * Removes a vehicle from this agenda entry.
     *
     * @param vehicle the vehicle to remove
     * @throws IllegalArgumentException if the vehicle is not in the list
     */
    public void removeVehicle(Vehicle vehicle) {
        if (!this.vehicles.contains(vehicle)) {
            throw new IllegalArgumentException("Vehicle does not exist in the entry");
        }
        this.vehicles.remove(vehicle);
    }


}