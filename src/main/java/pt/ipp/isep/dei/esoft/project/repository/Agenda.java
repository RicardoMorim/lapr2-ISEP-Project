package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;

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

}