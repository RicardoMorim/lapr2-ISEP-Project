package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Entry;

import java.util.ArrayList;
import java.util.List;

public class EntryRepository {
    List<Entry> entries;
    public EntryRepository(){
        this.entries = new ArrayList<>();
    }

    public void addEntry(Entry entry){
        if (entries.contains(entry)){
            throw new IllegalArgumentException("Entry in entry");
        }
        this.entries.add(entry);
    }

    public void removeEntry(Entry entry){
        if (!entries.contains(entry)){
            throw new IllegalArgumentException("Entry not found");
        }
        this.entries.remove(entry);
    }

    public void updateEntry(Entry old, Entry newEntry){
        removeEntry(old);
        addEntry(newEntry);
    }


}
