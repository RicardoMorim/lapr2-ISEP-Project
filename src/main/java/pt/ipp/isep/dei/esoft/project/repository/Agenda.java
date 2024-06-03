package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
     * Gets entries.
     *
     * @return the entries
     */
    public List<AgendaEntry> getEntries() {
        // This is a defensive copy, so that the entries cannot be modified from the outside.
        return List.copyOf(entries);
    }

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

    public List<Vehicle> getVehiclesNotAssignedToAnyAgendaEntry(List<Vehicle> vehicles) {
        List<Vehicle> vehiclesNotAssigned = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (!isVehicleAssigned(vehicle)) {
                vehiclesNotAssigned.add(vehicle);
            }
        }
        return vehiclesNotAssigned;
    }

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
}