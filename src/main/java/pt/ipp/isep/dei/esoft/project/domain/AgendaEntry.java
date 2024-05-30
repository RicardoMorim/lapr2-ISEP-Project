package pt.ipp.isep.dei.esoft.project.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Agenda entry.
 */
public class AgendaEntry {
    private Team team;
    private List<Vehicle> vehiclesEquipment;
    private String duration;
    private Status status;
    private Entry entry;

    /**
     * Instantiates a new Agenda entry.
     *
     * @param entry             the entry
     * @param team              the team
     * @param vehiclesEquipment the vehicles equipment
     * @param duration          the duration
     * @param status            the status
     */
    public AgendaEntry(Entry entry, Team team, List<Vehicle> vehiclesEquipment, String duration, Status status) {
        this.entry = entry;
        this.team = team;
        this.vehiclesEquipment = vehiclesEquipment;
        this.duration = duration;
        this.status = status;
    }

    public AgendaEntry(Entry entry, List<Vehicle> vehiclesEquipment, String duration, Status status) {
        this.entry = entry;
        this.team = null;
        this.vehiclesEquipment = vehiclesEquipment;
        this.duration = duration;
        this.status = status;
    }

    public AgendaEntry(Entry entry, Team team, String duration, Status status) {
        this.entry = entry;
        this.team = team;
        this.vehiclesEquipment = new ArrayList<>();
        this.duration = duration;
        this.status = status;
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
    public List<Vehicle> getVehiclesEquipment() {
        return vehiclesEquipment;
    }

    /**
     * Sets vehicles equipment.
     *
     * @param vehiclesEquipment the vehicles equipment
     */
    public void setVehiclesEquipment(List<Vehicle> vehiclesEquipment) {
        this.vehiclesEquipment = vehiclesEquipment;
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
        if (this.vehiclesEquipment.contains(vehicle)) {
            throw new IllegalArgumentException("Vehicle already exists in the list");

        }
        this.vehiclesEquipment.add(vehicle);
    }

    /**
     * Removes a vehicle from this agenda entry.
     *
     * @param vehicle the vehicle to remove
     * @throws IllegalArgumentException if the vehicle is not in the list
     */
    public void removeVehicle(Vehicle vehicle) {
        if (!this.vehiclesEquipment.contains(vehicle)) {
            throw new IllegalArgumentException("Vehicle does not exist in the list");
        }
        this.vehiclesEquipment.remove(vehicle);
    }

}