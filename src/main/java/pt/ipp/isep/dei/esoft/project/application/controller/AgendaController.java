package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;

/**
 * The type Agenda controller.
 */
public class AgendaController {
    private Agenda agenda;

    /**
     * Instantiates a new Agenda controller.
     *
     * @param agenda the agenda
     */
    public AgendaController(Agenda agenda) {
        this.agenda = agenda;
    }

    /**
     * Add entry.
     *
     * @param entry the entry
     */
    public void addEntry(AgendaEntry entry) {
        agenda.addEntry(entry);
    }

    /**
     * Remove entry.
     *
     * @param entry the entry
     */
    public void removeEntry(AgendaEntry entry) {
        agenda.removeEntry(entry);
    }

    /**
     * Gets agenda.
     *
     * @return the agenda
     */
    public Agenda getAgenda() {
        return agenda;
    }

}