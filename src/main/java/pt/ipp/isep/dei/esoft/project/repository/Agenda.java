package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Agenda.
 */
public class Agenda {
    private List<AgendaEntry> entries;

    /**
     * Instantiates a new Agenda.
     */
    public Agenda() {
        this.entries = new ArrayList<>();
    }

    /**
     * Add entry.
     *
     * @param entry the entry
     * @throws IllegalArgumentException if the entry is already in the agenda
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
     * @throws IllegalArgumentException if the entry is not in the agenda
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

    /**
     * Checks if a vehicle is assigned to any entry in the agenda.
     *
     * @param vehicle the vehicle to check
     * @return true if the vehicle is assigned to an entry, false otherwise
     */
    public boolean isVehicleAssigned(Vehicle vehicle) {
        for (AgendaEntry agendaEntry : this.entries) {
            if (agendaEntry.getVehiclesEquipment().contains(vehicle)) {
                return true;
            }
        }
        return false;
    }

}