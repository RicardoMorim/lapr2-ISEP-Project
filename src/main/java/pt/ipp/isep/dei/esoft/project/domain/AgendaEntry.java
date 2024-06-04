package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * The type Agenda entry.
 */
public class AgendaEntry implements Serializable {
    private Team team;
    private List<Vehicle> vehicles;
    private String duration;
    private Status status;
    private Entry entry;
    private Date date;

    /**
     * Instantiates a new Agenda entry.
     *
     * @param entry             the entry
     * @param team              the team
     * @param vehicles the vehicles equipment
     * @param duration          the duration
     * @param status            the status
     */
    public AgendaEntry(Entry entry, Team team, List<Vehicle> vehicles, String duration, Status status, Date date) {
        this.entry = entry;
        this.team = team;
        this.vehicles = vehicles;
        this.duration = duration;
        this.status = status;
        this.date = date;
    }

    public AgendaEntry(Entry entry, List<Vehicle> vehicles, String duration, Status status, Date date) {
        this.entry = entry;
        this.team = null;
        this.vehicles = vehicles;
        this.duration = duration;
        this.status = status;
        this.date = date;
    }

    public AgendaEntry(Entry entry, Team team, String duration, Status status, Date date) {
        this.entry = entry;
        this.team = team;
        this.vehicles = new ArrayList<>();
        this.duration = duration;
        this.status = status;
        this.date = date;
    }

    public AgendaEntry(Entry entry, String duration, Status status, Date date) {
        this.entry = entry;
        this.team = null;
        this.vehicles = new ArrayList<>();
        this.duration = duration;
        this.status = status;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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