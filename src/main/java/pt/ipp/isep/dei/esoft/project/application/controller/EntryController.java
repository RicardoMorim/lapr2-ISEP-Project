package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Entry;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

public class EntryController {
    private EntryRepository repository;
    public EntryController(){
        if (repository == null){
            this.repository = Repositories.getInstance().getEntryRepository();
        }
    }

    public void addEntry(Entry entry){
        repository.addEntry(entry);
    }
    public void removeEntry(Entry entry){
        repository.removeEntry(entry);
    }

    public void updateEntry(Entry old, Entry entry){
        repository.updateEntry(old, entry);
    }
}
