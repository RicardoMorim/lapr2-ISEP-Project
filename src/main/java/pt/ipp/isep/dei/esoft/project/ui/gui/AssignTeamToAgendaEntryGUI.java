package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.TeamController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.stream.Collectors;

public class AssignTeamToAgendaEntryGUI {

    private TeamController teamController = new TeamController();
    private AgendaController agendaController = new AgendaController();
    private VBox vboxTeams = new VBox(5);
    private TableView<Team> tableTeams = new TableView<>();  // Single instance

    public GridPane getAssignTeamToAgendaEntryGridPane(double height, double width) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setStyle("-fx-background-color: #ECECEC;");
        grid.setPrefSize(width, height);

        tableTeams = createTeamsTable(null);  // Initialize with null
        vboxTeams.getChildren().addAll(new Label("Teams:"), tableTeams);

        // Create a VBox for the entries ComboBox and the buttons
        VBox vboxEntries = new VBox(5);
        TableView<AgendaEntry> tableEntries = createEntriesTable();

        // Create a VBox for the team of the chosen entry
        VBox vboxTeam = new VBox(5);
        ListView<String> lvTeam = new ListView<>();
        tableEntries.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lvTeam.getItems().setAll(newValue.getTeam() != null ? newValue.getTeam().getCollaborators().stream().map(Collaborator::getName).collect(Collectors.toList()) : Collections.emptyList());
                updateTeamsTable(newValue);
            }
        });

        Button btnRemove = createRemoveButton(tableEntries, lvTeam);
        Button btnAdd = createAddButton(tableEntries, lvTeam);
        vboxEntries.getChildren().addAll(new Label("Select an entry:"), tableEntries, btnAdd, btnRemove);
        btnAdd.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnRemove.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white;");

        vboxTeam.getChildren().addAll(new Label("Team of the selected entry:"), lvTeam);

        // Add the VBoxes to the GridPane
        GridPane.setConstraints(vboxEntries, 0, 0);
        GridPane.setConstraints(vboxTeams, 1, 0);
        GridPane.setConstraints(vboxTeam, 2, 0);
        grid.getChildren().addAll(vboxTeams, vboxEntries, vboxTeam);
        grid.setAlignment(Pos.CENTER);

        return grid;
    }

    private TableView<Team> createTeamsTable(AgendaEntry selectedEntry) {
        TableView<Team> table = new TableView<>();
        TableColumn<Team, Integer> colTeamId = new TableColumn<>("Team ID");
        TableColumn<Team, String> colCollaborators = new TableColumn<>("Collaborators");
        TableColumn<Team, String> colSkills = new TableColumn<>("Skills");

        colTeamId.setCellValueFactory(cellData -> new SimpleIntegerProperty(table.getItems().indexOf(cellData.getValue()) + 1).asObject());
        colCollaborators.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCollaborators().stream().map(Collaborator::getName).collect(Collectors.joining(", "))));
        colSkills.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSkills().stream().map(Skill::getName).collect(Collectors.joining(", "))));

        table.getColumns().addAll(colTeamId, colCollaborators, colSkills);
        table.setPrefWidth(600);

        if (selectedEntry != null) {
            // Clear the table before adding new teams
            table.getItems().clear();
            table.getItems().setAll(agendaController.getAgenda().filterUnavailableTeams(selectedEntry.getStartDate(), selectedEntry.getEndDate(), teamController.getTeams()));
        } else {
            table.getItems().setAll(teamController.getUnassignedTeams());
        }

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                System.out.println("Selected team is now null.");
            } else {
                System.out.println("Selected team is now: " + newValue);
            }
        });

        return table;
    }

    private void updateTeamsTable(AgendaEntry selectedEntry) {
        tableTeams.getItems().clear();
        tableTeams.getItems().setAll(agendaController.getAgenda().filterUnavailableTeams(selectedEntry.getStartDate(), selectedEntry.getEndDate(), teamController.getTeams()));
    }

    private TableView<AgendaEntry> createEntriesTable() {
        TableView<AgendaEntry> tableEntries = new TableView<>();
        TableColumn<AgendaEntry, String> colTitle = new TableColumn<>("Title");
        TableColumn<AgendaEntry, String> colDescription = new TableColumn<>("Description");
        TableColumn<AgendaEntry, LocalDate> colStartDate = new TableColumn<>("Start Date");
        TableColumn<AgendaEntry, LocalDate> colExpectedDuration = new TableColumn<>("Expected EndDate");

        colTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntry().getTitle()));
        colDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntry().getDescription()));
        colStartDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        colExpectedDuration.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));

        tableEntries.getColumns().addAll(colTitle, colDescription, colStartDate, colExpectedDuration);

        tableEntries.getItems().addAll(agendaController.getAgenda().getEntriesWithNoTeam());
        tableEntries.setPrefWidth(500);

        return tableEntries;
    }

    private Button createAddButton(TableView<AgendaEntry> tableEntries, ListView<String> lvTeam) {
        Button btnAdd = new Button("Add Team");
        btnAdd.setOnAction(e -> {
            Team selectedTeam = tableTeams.getSelectionModel().getSelectedItem();
            AgendaEntry selectedEntry = tableEntries.getSelectionModel().getSelectedItem();

            if (selectedTeam == null) {
                System.out.println("No team selected.");
            }
            if (selectedEntry == null) {
                System.out.println("No entry selected.");
            }
            if (selectedTeam != null && selectedEntry != null) {
                agendaController.assignTeamToEntry(selectedTeam, selectedEntry);
                tableTeams.getItems().remove(selectedTeam);
                lvTeam.getItems().setAll(selectedEntry.getTeam().getCollaborators().stream().map(Collaborator::getName).collect(Collectors.toList()));
            }
        });
        return btnAdd;
    }

    private Button createRemoveButton(TableView<AgendaEntry> tableEntries, ListView<String> lvTeam) {
        Button btnRemove = new Button("Remove Team");
        btnRemove.setOnAction(e -> {
            AgendaEntry selectedEntry = tableEntries.getSelectionModel().getSelectedItem();
            if (selectedEntry != null && selectedEntry.getTeam() != null) {
                tableTeams.getItems().add(selectedEntry.getTeam());
                teamController.unassignTeam(selectedEntry);
                lvTeam.getItems().clear();
            }
        });
        return btnRemove;
    }
}
