package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.PostponeEntryController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PostponeEntryUI implements Runnable {

    private final PostponeEntryController controller = new PostponeEntryController();
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void run() {
        List<AgendaEntry> agendaEntries = controller.getAgendaEntries();
        int index = 1;
        for (AgendaEntry entry : agendaEntries) {
            System.out.println(index + ". " + entry);
            index++;
        }
        System.out.println("Choose the index of the desired entry:");
        int entryIndex = sc.nextInt();
        AgendaEntry chosenEntry = agendaEntries.get(entryIndex - 1);

        System.out.println("Enter the date in the format dd-MM-yyyy:");
        String dateString = sc.next();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate = null;

        try {
            newDate = dateFormat.parse(dateString);
            System.out.println("Date entered: " + newDate);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
            e.printStackTrace();
        }

        chosenEntry = controller.updateDate(chosenEntry,newDate);

        if(chosenEntry.getDate().equals(newDate))
            System.out.println("Entry postponed successfully");
        else
            System.out.println("Unable to postpone entry");

    }


}
