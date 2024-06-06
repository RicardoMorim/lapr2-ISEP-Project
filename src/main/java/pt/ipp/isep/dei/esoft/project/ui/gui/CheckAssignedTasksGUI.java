package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.TeamController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CheckAssignedTasksGUI {

    private Collaborator collaborator;
    private AgendaController agendaController;
    private TeamController teamController;
    private CollaboratorController collaboratorController;

    public CheckAssignedTasksGUI() {
        collaboratorController = new CollaboratorController();
        this.agendaController = new AgendaController();
        this.teamController = new TeamController();
        this.collaborator = collaboratorController.getCollaboratorByEmail(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().toString());
    }

    public GridPane getAssignedTasks(double height, double width) {
        GridPane grid = new GridPane(height, width);
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("All", "Pending", "Completed", "In Progress");
        statusComboBox.setValue("All");

        Button filterButton = new Button("Filter");

        TableView<AgendaEntry> table = createTasksTable();
        filterButton.setOnAction(e -> filterTasks(table, startDatePicker.getValue(), endDatePicker.getValue(), statusComboBox.getValue()));

        HBox filtersBox = new HBox(10, new Label("Start Date:"), startDatePicker, new Label("End Date:"), endDatePicker, new Label("Status:"), statusComboBox, filterButton);
        filtersBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(filtersBox, table);

        grid.add(vbox, 0, 0);
        return grid;
    }

    private TableView<AgendaEntry> createTasksTable() {
        TableView<AgendaEntry> table = new TableView<>();

        TableColumn<AgendaEntry, String> titleCol = new TableColumn<>("Title");
        TableColumn<AgendaEntry, String> descCol = new TableColumn<>("Description");
        TableColumn<AgendaEntry, String> startDateCol = new TableColumn<>("Start Date");
        TableColumn<AgendaEntry, String> endDateCol = new TableColumn<>("End Date");
        TableColumn<AgendaEntry, String> statusCol = new TableColumn<>("Status");

        titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntry().getTitle()));
        descCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntry().getDescription()));
        startDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartDate().toString()));
        endDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndDate().toString()));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        table.getColumns().addAll(titleCol, descCol, startDateCol, endDateCol, statusCol);

        return table;
    }

    private void filterTasks(TableView<AgendaEntry> table, LocalDate startDate, LocalDate endDate, String status) {
        List<AgendaEntry> allTasks = agendaController.getEntriesByTeam(teamController.getTeamByCollaborator(collaborator));
        List<AgendaEntry> filteredTasks = allTasks.stream()
                .filter(task -> (startDate == null || !task.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(startDate)) &&
                        (endDate == null || !task.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(endDate)) &&
                        (status.equals("All") || task.getStatus().toString().equals(status)))
                .sorted(Comparator.comparing(AgendaEntry::getStartDate))
                .collect(Collectors.toList());

        table.getItems().setAll(filteredTasks);
    }
}
