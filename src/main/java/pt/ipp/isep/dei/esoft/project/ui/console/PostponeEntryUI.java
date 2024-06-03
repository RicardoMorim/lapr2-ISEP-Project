package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.Date;
import java.util.List;

public class PostponeEntryUI implements Runnable {

    private final AgendaController agendaController = new AgendaController();

    @Override
    public void run() {
        List<AgendaEntry> agendaEntries = agendaController.getAgenda().getEntries();
        int entryIndex = Utils.showAndSelectIndex(agendaEntries, "Choose an entry to postpone: ");
        if (entryIndex == -1) {
            System.out.println("Cancelling operation.");
            return;
        }

        AgendaEntry chosenEntry = agendaEntries.get(entryIndex);

        Date newDate = Utils.readDateFromConsole("Enter the date in the format dd-MM-yyyy: ");

        chosenEntry = agendaController.postponeEntry(chosenEntry, newDate);

        if (chosenEntry.getDate().equals(newDate))
            System.out.println("Entry postponed successfully");
        else
            System.out.println("Unable to postpone entry");

    }


}
