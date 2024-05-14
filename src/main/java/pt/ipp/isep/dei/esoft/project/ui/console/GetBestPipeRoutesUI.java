package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.mdisc.IrrigationSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetBestPipeRoutesUI implements Runnable {


    @Override
    public void run() {
        System.out.println("----- Get Best Pipe Routes -----");
        System.setProperty("org.graphstream.ui", "swing");

        List<String> options = new ArrayList<>();

        options.add("Get best water pipe routes for the values in one csv file.");
        options.add("Test the execution times for all the .csv files inside the database folder.");

        int option = Utils.showAndSelectIndex(options, "Select an option: ");
        System.out.println("\nPath to the database folder: 'src/main/java/pt/ipp/isep/dei/mdisc/database'");
        IrrigationSystem irrigationSystem = new IrrigationSystem();
        if (option == 0) {

            boolean validFile = false;
            while (!validFile) {
                System.out.println("To use the default path just input the file name.\nElse type the path to the file.");
                String filePath = Utils.readLineFromConsole("Enter the file name: ");
                String defaultPath = "src/main/java/pt/ipp/isep/dei/mdisc/database/";

                File file = new File(defaultPath + filePath);
                File file2 = new File(filePath);
                if (file.exists()) {
                    irrigationSystem.importCsv(defaultPath + filePath);
                    irrigationSystem.planIrrigationSystem(filePath, true);
                    validFile = true;
                }else if (file2.exists()){
                    irrigationSystem.importCsv(filePath);
                    irrigationSystem.planIrrigationSystem(filePath, true);
                    validFile = true;
                }
                else {
                    System.out.println("File does not exist. Please try again.");
                }
            }

        } else if (option == 1) {
            String folder;
            File file;
            do {
                System.out.println("\nTo change the folder path type it in the next prompt.\nTo use the default path press enter.");

                folder = Utils.readLineFromConsole("Enter the folder path: ");
                System.out.println(folder);
                if (folder.isEmpty()) {
                    folder = "src/main/java/pt/ipp/isep/dei/mdisc/database";
                }
                file = new File(folder);
            } while (!file.exists() || !file.isDirectory());
            System.out.println("Testing execution times for all the .csv files inside the " + folder + " folder.");
            irrigationSystem.testExecutionTime(folder);
        }
    }
}
