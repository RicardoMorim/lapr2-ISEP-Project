package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.TeamController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.util.Collections;
import java.util.stream.Collectors;

public class AssignTeamToAgendaEntryGUI extends Application {

    private TeamController teamController = new TeamController();
    private AgendaController agendaController = new AgendaController();

    public GridPane getAssignTeamToAgendaEntryGridPane(double height, double width) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setStyle("-fx-background-color: #ECECEC;");

        // Create a VBox for the teams table
        VBox vboxTeams = new VBox(5);
        TableView<Team> tableTeams = createTeamsTable();
        vboxTeams.getChildren().add(tableTeams);

        // Create a VBox for the entries ComboBox and the buttons
        VBox vboxEntries = new VBox(5);
        ComboBox<AgendaEntry> cbEntry = createEntriesComboBox();


        // Create a VBox for the team of the chosen entry
        VBox vboxTeam = new VBox(5);
        ListView<String> lvTeam = new ListView<>();
        cbEntry.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lvTeam.getItems().setAll(newValue.getTeam() != null ? newValue.getTeam().getCollaborators().stream().map(Collaborator::getName).collect(Collectors.toList()) : Collections.emptyList());
            }
        });

        Button btnRemove = createRemoveButton(tableTeams, cbEntry, lvTeam);
        Button btnAdd = createAddButton(tableTeams, cbEntry, lvTeam);
        vboxEntries.getChildren().addAll(new Label("Select an entry:"), cbEntry, btnAdd, btnRemove);
        btnAdd.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnRemove.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white;");
        btnAdd.setDisable(cbEntry.getItems().isEmpty());
        btnRemove.setDisable(cbEntry.getItems().isEmpty());

        vboxTeam.getChildren().addAll(new Label("Team of the selected entry:"), lvTeam);
        
        // Add the VBoxes to the GridPane
        GridPane.setConstraints(vboxTeams, 0, 0);
        GridPane.setConstraints(vboxEntries, 1, 0);
        GridPane.setConstraints(vboxTeam, 2, 0);
        grid.getChildren().addAll(vboxTeams, vboxEntries, vboxTeam);
        grid.setAlignment(Pos.CENTER);
        return grid;
    }

    private TableView<Team> createTeamsTable() {
        TableView<Team> tableTeams = new TableView<>();
        TableColumn<Team, Integer> colTeamId = new TableColumn<>("Team ID");
        TableColumn<Team, String> colCollaborators = new TableColumn<>("Collaborators");

        colTeamId.setCellValueFactory(cellData -> new SimpleIntegerProperty(tableTeams.getItems().indexOf(cellData.getValue()) + 1).asObject());
        colCollaborators.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCollaborators().stream().map(Collaborator::getName).collect(Collectors.joining(", "))));

        tableTeams.getColumns().addAll(colTeamId, colCollaborators);
        tableTeams.getItems().addAll(teamController.getUnassignedTeams());
        return tableTeams;
    }

    private ComboBox<AgendaEntry> createEntriesComboBox() {
        ComboBox<AgendaEntry> cbEntry = new ComboBox<>();
        cbEntry.getItems().addAll(agendaController.getAgenda().getEntriesWithNoTeam());
        return cbEntry;
    }

    private Button createAddButton(TableView<Team> tableTeams, ComboBox<AgendaEntry> cbEntry, ListView<String> lvTeam) {
        Button btnAdd = new Button("Add Team");
        btnAdd.setOnAction(e -> {
            Team selectedTeam = tableTeams.getSelectionModel().getSelectedItem();
            AgendaEntry selectedEntry = cbEntry.getValue();
            if (selectedTeam != null && selectedEntry != null) {
                agendaController.assignTeamToEntry(selectedTeam, selectedEntry);
                tableTeams.getItems().remove(selectedTeam);
                lvTeam.getItems().setAll(selectedEntry.getTeam().getCollaborators().stream().map(Collaborator::getName).collect(Collectors.toList()));
            }
        });
        return btnAdd;
    }

    private Button createRemoveButton(TableView<Team> tableTeams, ComboBox<AgendaEntry> cbEntry, ListView<String> lvTeam) {
        Button btnRemove = new Button("Remove Team");
        btnRemove.setOnAction(e -> {
            AgendaEntry selectedEntry = cbEntry.getValue();
            if (selectedEntry != null && selectedEntry.getTeam() != null) {
                tableTeams.getItems().add(selectedEntry.getTeam());
                teamController.unassignTeam(selectedEntry);
                lvTeam.getItems().clear();
            }
        });
        return btnRemove;
    }

    // TODO - Remove Start method
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