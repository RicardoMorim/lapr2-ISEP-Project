package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Entry;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.ToDoList;

import java.util.List;

public class ToDoListController {
    private ToDoList toDo;
    public ToDoListController(){
        if (toDo == null){
            toDo = Repositories.getInstance().getToDoList();
        }
    }

    public List<Entry> getToBeDoneEntries(){
        return toDo.getToBeDoneEntries();
    }

    public void addEntry(Entry entry){
        toDo.addEntry(entry);
    }

    public void removeEntry(Entry entry){
        toDo.removeEntry(entry);
    }

    public void updateEntry(Entry old, Entry entry){
        toDo.updateEntry(old, entry);
    }

    public List<Entry> getEntries(){
        return toDo.getEntries();
    }

}
