package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Entry;
import pt.ipp.isep.dei.esoft.project.domain.Status;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    private List<Entry> entryList;

    public ToDoList() {
        entryList = new ArrayList<>();
    }

    public ToDoList(List<Entry> list) {
        this.entryList = list;
    }

    public void addEntry(Entry entry){
        if (entryList.contains(entry)){
            throw new IllegalArgumentException("Entry already in ToDo List");
        }
        entryList.add(entry);
    }

    public void setEntries(List<Entry> list) {
        this.entryList = list;
    }

    public void removeEntry(Entry entry){
        if (!entryList.contains(entry)){
            throw new IllegalArgumentException("Entry not in ToDo List");
        }
        entryList.remove(entry);
    }

    public void updateEntry(Entry old, Entry entry){
        removeEntry(old);
        addEntry(entry);
    }

    public List<Entry> getEntries(){
        return entryList;
    }

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
