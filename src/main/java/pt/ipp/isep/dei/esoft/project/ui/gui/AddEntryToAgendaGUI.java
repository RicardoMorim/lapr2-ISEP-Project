package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AddEntryToAgendaGUI {

    public GridPane getGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        TextField tfTask = new TextField();
        TextField tfTeam = new TextField();
        TextField tfVehicles = new TextField();
        TextField tfDuration = new TextField();
        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("Planned", "Postponed", "Canceled", "Done");

        grid.add(new Label("Task:"), 0, 0);
        grid.add(tfTask, 1, 0);
        grid.add(new Label("Team:"), 0, 1);
        grid.add(tfTeam, 1, 1);
        grid.add(new Label("Vehicles/Equipment:"), 0, 2);
        grid.add(tfVehicles, 1, 2);
        grid.add(new Label("Expected Duration:"), 0, 3);
        grid.add(tfDuration, 1, 3);
        grid.add(new Label("Status:"), 0, 4);
        grid.add(cbStatus, 1, 4);

        Button btnSubmit = new Button("Submit");
        btnSubmit.setOnAction(e -> {
            // Add action to create a new Agenda entry with the entered data
        });
        grid.add(btnSubmit, 0, 5);

        return grid;
    }
}