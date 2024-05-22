package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<AgendaEntry> entries;

    public Agenda() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(AgendaEntry entry) {
        this.entries.add(entry);
    }

    public void removeEntry(AgendaEntry entry) {
        this.entries.remove(entry);
    }

    public List<AgendaEntry> getEntries() {
        // This is a defensive copy, so that the entries cannot be modified from the outside.
        return List.copyOf(entries);
    }


}