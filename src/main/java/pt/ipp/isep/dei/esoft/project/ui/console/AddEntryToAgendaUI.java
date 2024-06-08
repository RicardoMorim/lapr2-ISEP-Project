package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Entry;
import pt.ipp.isep.dei.esoft.project.domain.Status;
import pt.ipp.isep.dei.esoft.project.domain.Urgency;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        String title = Utils.readLineFromConsole("Enter the title: ");
        String description = Utils.readLineFromConsole("Enter the description: ");
        Urgency[] urgencyValues = Urgency.values();
        List<Urgency> urgencyList = new ArrayList<>(Arrays.asList(urgencyValues));
        int urgency = Utils.showAndSelectIndex(urgencyList, "Enter the urgency: ");
        Urgency urgencyValue = urgencyList.get(urgency);
        float expectedDuration = (float) Utils.readDoubleFromConsole("Enter the expected duration: ");
        String duration = Utils.readLineFromConsole("Enter the duration: ");

        if (urgency < 0) {
            System.out.println("cancelling operation");
            return;
        }

        // Create the new Entry
        Entry entry = new Entry(null, title, description, urgencyValue, expectedDuration);

        // Create the new AgendaEntry
        AgendaEntry agendaEntry = new AgendaEntry(entry, duration, new Date());

        // Add the entry to the agenda
        agendaController.addEntry(agendaEntry);

        System.out.println("The entry was successfully added to the agenda.");
        for (AgendaEntry e : agendaController.getAgenda().getEntries())
            System.out.println(e);
    }
}