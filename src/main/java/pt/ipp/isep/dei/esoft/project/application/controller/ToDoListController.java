package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Entry;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.ToDoList;

import java.util.List;

/**
 * The type To do list controller.
 */
public class ToDoListController {
    private ToDoList toDo;

    /**
     * Instantiates a new To do list controller.
     */
    public ToDoListController(){
        if (toDo == null){
            toDo = Repositories.getInstance().getToDoList();
        }
    }

    /**
     * Instantiates a new To do list controller.
     *
     * @param toDo the to do
     */
    public ToDoListController(ToDoList toDo){
        this.toDo = toDo;
    }

    /**
     * Get to be done entries list.
     *
     * @return the list
     */
    public List<Entry> getToBeDoneEntries(){
        return toDo.getToBeDoneEntries();
    }

    /**
     * Add entry.
     *
     * @param entry the entry
     */
    public void addEntry(Entry entry){
        toDo.addEntry(entry);
    }


    /**
     * Get entries list.
     *
     * @return the list
     */
    public List<Entry> getEntries(){
        return toDo.getEntries();
    }

}
