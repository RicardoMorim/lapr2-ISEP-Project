package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.List;

/**
 * The type Cancel ui.
 */
public class CancelUI implements Runnable {
    private final AgendaController agendaController;

    /**
     * Instantiates a new Cancel ui.
     */
    public CancelUI() {
        this.agendaController = new AgendaController();
    }


    @Override
    public void run() {
        System.out.println("\n------- Cancel an entry in the Agenda --------\n");

        System.out.println("Requesting to cancel an agenda entry.");

        List<AgendaEntry> entries = agendaController.getAgenda().getEntries();

        int agendaIdx = Utils.showAndSelectIndex(entries, "Select an entry to cancel: ");

        if (agendaIdx == -1){
            System.out.println("Cancelling the operation.");
        }

        AgendaEntry selectedEntry = entries.get(agendaIdx);

        System.out.println("Confirm cancellation of: " + selectedEntry);

            if (Utils.confirm("Do you want cancel the selected entry? (S/N)")) {
                try {
                    agendaController.cancelAgendaEntry(selectedEntry);
                    System.out.println("The entry was successfully canceled ");
                } catch (IllegalArgumentException e) {
                    System.out.println("There was an error cancelling the entry.\n");
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Cancelation cancelled.");
            }
            for (AgendaEntry entry: agendaController.getAgenda().getEntries()){
                System.out.println(entry);
                System.out.println("Status: " + entry.getStatus());
            }
    }
}
