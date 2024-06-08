package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Status;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.Date;
import java.util.List;

public class PostponeEntryUI implements Runnable {

    private final AgendaController agendaController = new AgendaController();

    @Override
    public void run() {
        System.out.println("\n------- Postpone an entry in the Agenda --------\n");
        List<AgendaEntry> agendaEntries = agendaController.getAgenda().getEntries();
        int entryIndex = Utils.showAndSelectIndex(agendaEntries, "Choose an entry to postpone: ");
        if (entryIndex == -1) {
            System.out.println("Cancelling operation.");
            return;
        }
        AgendaEntry chosenEntry = agendaEntries.get(entryIndex);

        // Ask for the new date
        Date newDate = Utils.readDateFromConsole("Enter the new date for the entry (dd-mm-yyyy): ");
        Date oldDate = chosenEntry.getStartDate();
        // Update the date and status of the chosen entry
        chosenEntry = agendaController.postponeEntry(chosenEntry, newDate);
        if (oldDate.before(newDate))
            if (chosenEntry.getStatus().equals(Status.POSTPONED))
                System.out.println("Entry postponed successfully");
            else
                System.out.println("Unable to postpone entry");
        else
            System.out.println("New date must be after old date");

    }
}