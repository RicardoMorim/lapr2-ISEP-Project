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

        // Top tables
        TableView<AgendaEntry> tableEntriesNoTeam = createEntriesTableNoTeam();
        tableTeams = createTeamsTable(null);  // Initialize with null
        vboxTeams.getChildren().addAll(new Label("Teams:"), tableTeams);

        // Bottom tables
        TableView<AgendaEntry> tableEntriesWithTeam = createEntriesTableWithTeam();
        ListView<String> lvTeam = new ListView<>();

        // Update team table when an entry is selected
        tableEntriesNoTeam.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateTeamsTable(newValue);
            }
        });

        // Update team list view when an entry is selected
        tableEntriesWithTeam.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lvTeam.getItems().setAll(newValue.getTeam() != null ? newValue.getTeam().getCollaborators().stream().map(Collaborator::getName).collect(Collectors.toList()) : Collections.emptyList());
            }
        });

        Button btnAdd = createAddButton(tableEntriesNoTeam, tableEntriesWithTeam, lvTeam);
        Button btnRemove = createRemoveButton(tableEntriesNoTeam, tableEntriesWithTeam, lvTeam);

        // Add the tables and buttons to the GridPane
        GridPane.setConstraints(tableEntriesNoTeam, 0, 0);
        GridPane.setConstraints(tableTeams, 1, 0);
        GridPane.setConstraints(btnAdd, 0, 1);
        GridPane.setConstraints(tableEntriesWithTeam, 0, 2);
        GridPane.setConstraints(lvTeam, 1, 2);
        GridPane.setConstraints(btnRemove, 0, 3);
        grid.getChildren().addAll(tableEntriesNoTeam, tableTeams, btnAdd, tableEntriesWithTeam, lvTeam, btnRemove);
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


        return table;
    }

    private void updateTeamsTable(AgendaEntry selectedEntry) {
        tableTeams.getItems().clear();
        tableTeams.getItems().setAll(agendaController.getAgenda().filterUnavailableTeams(selectedEntry.getStartDate(), selectedEntry.getEndDate(), teamController.getTeams()));
    }

    private TableView<AgendaEntry> createEntriesTableNoTeam() {
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

    private TableView<AgendaEntry> createEntriesTableWithTeam() {
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

        tableEntries.getItems().addAll(agendaController.getAgenda().getEntriesWithTeam());

        tableEntries.setPrefWidth(500);

        return tableEntries;
    }

    private Button createAddButton(TableView<AgendaEntry> tableEntriesNoTeam, TableView<AgendaEntry> tableEntriesWithTeam, ListView<String> lvTeam) {
        Button btnAdd = new Button("Add Team");
        btnAdd.getStyleClass().add("add-button");
        btnAdd.setOnAction(e -> {
            Team selectedTeam = tableTeams.getSelectionModel().getSelectedItem();
            AgendaEntry selectedEntry = tableEntriesNoTeam.getSelectionModel().getSelectedItem();

            if (selectedTeam == null) {
                System.out.println("No team selected.");
            }
            if (selectedEntry == null) {
                System.out.println("No entry selected.");
            }
            if (selectedTeam != null && selectedEntry != null) {
                agendaController.assignTeamToEntry(selectedTeam, selectedEntry);
                tableTeams.getItems().remove(selectedTeam);
                tableEntriesNoTeam.getItems().remove(selectedEntry);
                tableEntriesWithTeam.getItems().add(selectedEntry);
                lvTeam.getItems().setAll(selectedEntry.getTeam().getCollaborators().stream().map(Collaborator::getName).collect(Collectors.toList()));
            }
        });
        return btnAdd;
    }

    private Button createRemoveButton(TableView<AgendaEntry> tableEntriesNoTeam, TableView<AgendaEntry> tableEntriesWithTeam, ListView<String> lvTeam) {
        Button btnRemove = new Button("Remove Team");
        btnRemove.getStyleClass().add("remove-button");
        btnRemove.setOnAction(e -> {
            AgendaEntry selectedEntry = tableEntriesWithTeam.getSelectionModel().getSelectedItem();
            if (selectedEntry != null && selectedEntry.getTeam() != null) {
                teamController.unassignTeam(selectedEntry);
                tableEntriesWithTeam.getItems().remove(selectedEntry);
                tableEntriesNoTeam.getItems().add(selectedEntry);
                lvTeam.getItems().clear();
            }
        });
        return btnRemove;
    }
}
