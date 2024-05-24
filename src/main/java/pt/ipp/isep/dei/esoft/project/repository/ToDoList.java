package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Entry;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    private List<Entry> list;

    public ToDoList() {
        list = new ArrayList<>();
    }

    public ToDoList(List<Entry> list) {
        this.list = list;
    }

    public void addEntry(Entry entry){
        if (list.contains(entry)){
            throw new IllegalArgumentException("Entry already in ToDo List");
        }
        list.add(entry);
    }

    public void removeEntry(Entry entry){
        if (!list.contains(entry)){
            throw new IllegalArgumentException("Entry not in ToDo List");
        }
        list.remove(entry);
    }

    public void updateEntry(Entry old, Entry entry){
        removeEntry(old);
        addEntry(entry);
    }

    public List<Entry> getEntries(){
        return List.copyOf(list);
    }
}
