package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Date;
import java.util.List;

public class PostponeEntryController {

    private Agenda agenda;

    public PostponeEntryController() {
        if (agenda == null) {
            Repositories repositories = Repositories.getInstance();

            //Get the TaskCategoryRepository
            agenda = repositories.getAgenda();
        }
    }


    public List<AgendaEntry> getAgendaEntries(){
        List<AgendaEntry> entries = agenda.getEntries();
        return entries;
    }

    public AgendaEntry updateDate(AgendaEntry entry, Date newDate){
        if(entry.isAfter(newDate)){
            entry.setDate(newDate);
            return entry;
        }
        else System.out.println("The entered date is not after the current date");
        return entry;
    }





}
