package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Entry;
import pt.ipp.isep.dei.esoft.project.domain.ToDoList;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.ToDoListRepository;

public class ToDoListController {
    private ToDoListRepository repository;
    public ToDoListController(){
        if (repository == null){
            repository = Repositories.getInstance().getToDoListRepository();
        }
    }

    public void addToDoList(ToDoList toDoList){
        repository.addToDoList(toDoList);
    }

    public void removeToDoList(ToDoList toDoList){
        repository.removeToDoList(toDoList);
    }

    public void updateToDoList(ToDoList old, ToDoList list){
        repository.updateToDoList(old, list);
    }
}
