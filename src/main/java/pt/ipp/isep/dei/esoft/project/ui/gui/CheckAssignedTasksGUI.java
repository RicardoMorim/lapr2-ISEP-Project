package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.TeamController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Status;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * The type Check assigned tasks gui.
 */
public class CheckAssignedTasksGUI {

    private Collaborator collaborator;
    private AgendaController agendaController;
    private TeamController teamController;
    private CollaboratorController collaboratorController;

    /**
     * Instantiates a new Check assigned tasks gui.
     */
    public CheckAssignedTasksGUI() {
        collaboratorController = new CollaboratorController();
        this.agendaController = new AgendaController();
        this.teamController = new TeamController();
        this.collaborator = collaboratorController.getCollaboratorByEmail(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().toString());
    }

    /**
     * Gets assigned tasks.
     *
     * @param height the height
     * @param width  the width
     * @return the assigned tasks
     */
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
        for (Status status : Status.values()) {
            statusComboBox.getItems().add(status.toString());
        }
        statusComboBox.getItems().add("All");
        statusComboBox.setValue("All");

        Button filterButton = new Button("Filter");

        TableView<AgendaEntry> table = createTasksTable();
        filterButton.setOnAction(e -> {
            Status status = statusComboBox.getValue().equals("All") ? null : Status.valueOf(statusComboBox.getValue());
            filterTasks(table, startDatePicker.getValue(), endDatePicker.getValue(), status);
        });

        Button setDoneButton = new Button("Set as Done");
        setDoneButton.setOnAction(e -> {
            AgendaEntry selectedTask = table.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                selectedTask.setStatus(Status.DONE);
                table.refresh();
            } else {
                // Show an error message if no task is selected
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a task to set as done.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("logo.jpeg"));
                alert.showAndWait();
            }
        });

        HBox filtersBox = new HBox(10, new Label("Start Date:"), startDatePicker, new Label("End Date:"), endDatePicker, new Label("Status:"), statusComboBox, filterButton);
        filtersBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(filtersBox, table, setDoneButton);
        setDoneButton.getStyleClass().add("add-button");

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
        descCol.setPrefWidth(200);
        startDateCol.setPrefWidth(150);
        endDateCol.setPrefWidth(150);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy", Locale.ENGLISH);

        titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntry().getTitle()));
        descCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntry().getDescription()));
        startDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)));
        endDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        table.getColumns().addAll(titleCol, descCol, startDateCol, endDateCol, statusCol);

        return table;
    }

    private void filterTasks(TableView<AgendaEntry> table, LocalDate startDate, LocalDate endDate, Status status) {
        List<AgendaEntry> allTasks = agendaController.getEntriesByTeam(teamController.getTeamByCollaborator(collaborator));


        List<AgendaEntry> filteredTasks = allTasks.stream()
                .filter(task -> {
                    LocalDate taskStartDate = task.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate taskEndDate = task.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


                    boolean isAfterOrEqualStartDate = startDate == null || !taskStartDate.isBefore(startDate);
                    boolean isBeforeOrEqualEndDate = endDate == null || !taskEndDate.isAfter(endDate);
                    boolean matchesStatus = status == null || task.getStatus() == status;


                    return isAfterOrEqualStartDate && isBeforeOrEqualEndDate && matchesStatus;
                })
                .sorted(Comparator.comparing(AgendaEntry::getStartDate))
                .collect(Collectors.toList());


        table.getItems().setAll(filteredTasks);
    }
}
