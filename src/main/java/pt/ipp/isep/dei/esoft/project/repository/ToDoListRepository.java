package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.ToDoList;

import java.util.ArrayList;
import java.util.List;

public class ToDoListRepository {
    private List<ToDoList> toDoListList;

    public ToDoListRepository(){
        this.toDoListList = new ArrayList<>();
    }

    public void addToDoList(ToDoList todo){
        if (toDoListList.contains(todo)){
            throw new IllegalArgumentException("ToDo List already exists");
        }
        toDoListList.add(todo);
    }


    public void removeToDoList(ToDoList todo){
        if (!toDoListList.contains(todo)){
            throw new IllegalArgumentException("ToDo List does not exist");
        }
        toDoListList.remove(todo);
    }

    public void updateToDoList(ToDoList old, ToDoList toDoList){
        removeToDoList(old);
        addToDoList(toDoList);
    }

}
