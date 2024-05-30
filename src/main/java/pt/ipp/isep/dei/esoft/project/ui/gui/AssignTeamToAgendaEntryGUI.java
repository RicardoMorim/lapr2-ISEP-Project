package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.TeamController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Team;

public class AssignTeamToAgendaEntryGUI extends Application {

    private TeamController teamController = new TeamController();
    private AgendaController agendaController = new AgendaController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Assign a Team to an Agenda Entry");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setStyle("-fx-background-color: #ECECEC;");

        Label lblTeam = new Label("Select a team:");
        ComboBox<Team> cbTeam = new ComboBox<>();
        cbTeam.getItems().addAll(teamController.getUnassignedTeams());
        if (cbTeam.getItems().isEmpty()) {
            cbTeam.setDisable(true);
            cbTeam.setPromptText("No unassigned team found");
        }

        Label lblEntry = new Label("Select an entry:");
        ComboBox<AgendaEntry> cbEntry = new ComboBox<>();
        cbEntry.getItems().addAll(agendaController.getAgenda().getEntriesWithNoTeam());
        if (cbEntry.getItems().isEmpty()) {
            cbEntry.setDisable(true);
            cbEntry.setPromptText("No entries without a team found");
        }

        Button btnAssign = new Button("Assign Team");
        btnAssign.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnAssign.setOnAction(e -> {
            Team selectedTeam = cbTeam.getValue();
            AgendaEntry selectedEntry = cbEntry.getValue();
            if (selectedTeam != null && selectedEntry != null) {
                agendaController.assignTeamToEntry(selectedTeam, selectedEntry);
                cbEntry.getItems().clear();
                cbTeam.getItems().clear();
                cbEntry.getItems().setAll(agendaController.getAgenda().getEntriesWithNoTeam());
                cbTeam.getItems().setAll(teamController.getUnassignedTeams());

                new Alert(Alert.AlertType.INFORMATION, "Team assigned successfully.");
            }
        });

        grid.add(lblTeam, 0, 0);
        grid.add(cbTeam, 1, 0);
        grid.add(lblEntry, 0, 1);
        grid.add(cbEntry, 1, 1);
        grid.add(btnAssign, 1, 2);

        Scene scene = new Scene(grid, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}