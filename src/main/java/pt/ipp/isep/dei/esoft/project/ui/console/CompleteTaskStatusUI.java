package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Status;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.List;

public class CompleteTaskStatusUI implements Runnable{

    @Override
    public void run(){
        System.out.println("\n-------Completion of the Task-------\n");

        // Get the necessary information from the user
        List<AgendaEntry> entries = Repositories.getInstance().getAgenda().getEntries();
        int choosenEntryIdx = Utils.showAndSelectIndex(entries, "Select the task that has been completed");

        // Check if the index is valid
        if (choosenEntryIdx < 0 || choosenEntryIdx >= entries.size()) {
            System.out.println("Invalid task selection. Please try again.");
            return;
        }

        AgendaEntry choosenEntry = entries.get(choosenEntryIdx);

        // Check if the task is already completed
        if (choosenEntry.getStatus() == Status.DONE) {
            System.out.println("This task is already completed.");
            return;
        }

        choosenEntry.setStatus(Status.DONE);

        // Provide feedback to the user
        System.out.println("Task status has been updated to DONE.");
    }
}