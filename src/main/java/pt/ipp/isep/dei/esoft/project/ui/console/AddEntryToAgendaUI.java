package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Entry;
import pt.ipp.isep.dei.esoft.project.domain.Status;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class AddEntryToAgendaUI implements Runnable {

    private final AgendaController agendaController;

    public AddEntryToAgendaUI() {
        this.agendaController = new AgendaController(new Agenda());
    }

    @Override
    public void run() {
        System.out.println("\n-------Add an entry to the agenda.--------\n");

        // Get the necessary information from the user
        String state = Utils.readLineFromConsole("Enter the state: ");
        String title = Utils.readLineFromConsole("Enter the title: ");
        String description = Utils.readLineFromConsole("Enter the description: ");
        String urgency = Utils.readLineFromConsole("Enter the urgency: ");
        float expectedDuration = (float) Utils.readDoubleFromConsole("Enter the expected duration: ");
        String duration = Utils.readLineFromConsole("Enter the duration: ");
        Status[] statusValues = Status.values();
        List<Status> statusList = Arrays.asList(statusValues);
        int statusIndex = Utils.showAndSelectIndex(statusList, "");
        Status status = statusList.get(statusIndex);

        if (statusIndex < 0){
            System.out.println("cancelling operation");
            return;
        }

        // Create the new Entry
        Entry entry = new Entry(state, null, title, description, urgency, expectedDuration);

        // Create the new AgendaEntry
        AgendaEntry agendaEntry = new AgendaEntry(entry, null, null, duration, status);

        // Add the entry to the agenda
        agendaController.addEntry(agendaEntry);

        System.out.println("The entry was successfully added to the agenda.");
        for (AgendaEntry e: agendaController.getAgenda().getEntries())
            System.out.println(e);
    }
}