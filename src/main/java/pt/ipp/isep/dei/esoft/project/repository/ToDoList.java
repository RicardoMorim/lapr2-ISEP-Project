package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Entry;
import pt.ipp.isep.dei.esoft.project.domain.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * The type To do list.
 */
public class ToDoList {
    private List<Entry> entryList;

    /**
     * Instantiates a new To do list.
     */
    public ToDoList() {
        entryList = new ArrayList<>();
    }

    /**
     * Instantiates a new To do list.
     *
     * @param list the list
     */
    public ToDoList(List<Entry> list) {
        this.entryList = list;
    }

    /**
     * Add entry.
     *
     * @param entry the entry
     */
    public void addEntry(Entry entry){
        if (entryList.contains(entry)){
            throw new IllegalArgumentException("Entry already in ToDo List");
        }
        entryList.add(entry);
    }

    /**
     * Sets entries.
     *
     * @param list the list
     */
    public void setEntries(List<Entry> list) {
        this.entryList = list;
    }

    /**
     * Remove entry.
     *
     * @param entry the entry
     */
    public void removeEntry(Entry entry){
        if (!entryList.contains(entry)){
            throw new IllegalArgumentException("Entry not in ToDo List");
        }
        entryList.remove(entry);
    }

    /**
     * Update entry.
     *
     * @param old   the old
     * @param entry the entry
     */
    public void updateEntry(Entry old, Entry entry){
        removeEntry(old);
        addEntry(entry);
    }

    /**
     * Get entries list.
     *
     * @return the list
     */
    public List<Entry> getEntries(){
        return entryList;
    }

    /**
     * Gets to be done entries.
     *
     * @return the to be done entries
     */
    public List<Entry> getToBeDoneEntries() {
        List<Entry> entries = new ArrayList<>();
        for (Entry entry : entryList) {
            if (entry.getState().equals(Status.TO_BE_DONE)) {
                entries.add(entry);
            }
        }
        return entries;
    }
}
