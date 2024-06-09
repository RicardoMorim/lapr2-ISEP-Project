package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.ToDoListController;
import pt.ipp.isep.dei.esoft.project.domain.Entry;
import pt.ipp.isep.dei.esoft.project.domain.Urgency;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Add entry to agenda gui.
 */
public class AddEntryToAgendaGUI {

    private AgendaController agendaController;
    private ToDoListController toDoListController;

    /**
     * Instantiates a new Add entry to agenda gui.
     */
    public AddEntryToAgendaGUI() {

        agendaController = new AgendaController();
        toDoListController = new ToDoListController();
    }

    /**
     * Gets grid pane.
     *
     * @param height the height
     * @param width  the width
     * @return the grid pane
     */
    public GridPane getGridPane(double height, double width) {
        GridPane grid = new GridPane(height, width);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        TableView<Entry> tvTask = new TableView<>();
        TableColumn<Entry, String> colTitle = new TableColumn<>("Title");
        TableColumn<Entry, String> colDescription = new TableColumn<>("Description");
        TableColumn<Entry, String> colUrgency = new TableColumn<>("Urgency");
        TableColumn<Entry, Float> colExpectedDuration = new TableColumn<>("Expected Duration");

        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUrgency.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUrgency().toString()));
        colExpectedDuration.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getExpectedDuration()).asObject());


        tvTask.getColumns().add(colTitle);
        tvTask.getColumns().add(colDescription);
        tvTask.getColumns().add(colUrgency);
        tvTask.getColumns().add(colExpectedDuration);
        tvTask.setItems(FXCollections.observableArrayList(toDoListController.getToBeDoneEntries()));

        Map<String, Integer> statusOrder = new HashMap<>();
        statusOrder.put(Urgency.HIGH.toString(), 1);
        statusOrder.put(Urgency.MEDIUM.toString(), 2);
        statusOrder.put(Urgency.LOW.toString(), 3);

        colUrgency.setComparator(Comparator.nullsLast(Comparator.comparing(urgency ->
                statusOrder.getOrDefault(urgency, Integer.MAX_VALUE)
        )));
        tvTask.getSortOrder().add(colUrgency);
        tvTask.sort();

        TextField tfDuration = new TextField();
        DatePicker dpStartDate = new DatePicker();
        DatePicker dpEndDate = new DatePicker();

        grid.add(new Label("Task:"), 0, 0);
        grid.add(tvTask, 1, 0);

        dpEndDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !tfDuration.getText().isEmpty()) {
                tfDuration.clear();
            }
        });

        Label lblStartDate = new Label("Start Date:");
        Label lblEndDate = new Label("End Date (Optional):");
        Label lblDuration = new Label("Expected Duration in hours (Optional):");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        dpStartDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String dateString = newValue.toString();
                if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    // The date string is in the expected format
                    dpStartDate.setStyle("");
                    lblStartDate.setText("Start Date:");
                } else {
                    // The date string is not in the expected format
                    dpStartDate.setStyle("-fx-background-color: red;");
                    lblStartDate.setText("Start Date: Invalid date format.");
                }
            }
        });

        dpEndDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String dateString = newValue.toString();
                if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    // The date string is in the expected format
                    dpEndDate.setStyle("");
                    lblEndDate.setText("End Date (Optional):");
                } else {
                    // The date string is not in the expected format
                    dpEndDate.setStyle("-fx-background-color: red;");
                    lblEndDate.setText("End Date (Optional): Invalid date format.");
                }
            }
        });

        Button btnSubmit = new Button("Submit");
        btnSubmit.setOnAction(e -> {
            Entry selectedEntry = tvTask.getSelectionModel().getSelectedItem();
            if (selectedEntry != null) {
                if (lblStartDate.getText().contains("required") || lblEndDate.getText().contains("Cannot") || lblDuration.getText().contains("Cannot")){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please correct the errors before submitting.");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("logo.png"));
                    alert.showAndWait();
                }

                LocalDate startDate = dpStartDate.getValue();
                LocalDate endDate = dpEndDate.getValue();

                if (startDate == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Start date is required.");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("logo.png"));
                    alert.showAndWait();
                    return;
                }

                java.util.Date utilStartDate = java.util.Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.util.Date utilEndDate = endDate != null ? java.util.Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;

                if (utilEndDate != null && utilStartDate.after(utilEndDate)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "End date cannot be before start date.");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("logo.png"));
                    alert.showAndWait();
                    return;
                } else if (utilStartDate != null && tfDuration.getText().isEmpty() && utilEndDate != null)
                    agendaController.addEntry(selectedEntry, utilStartDate, utilEndDate);
                else if (utilStartDate != null && utilEndDate != null && !tfDuration.getText().isEmpty())
                    agendaController.addEntry(selectedEntry, utilStartDate, utilEndDate, tfDuration.getText());
                else if (utilStartDate != null && !tfDuration.getText().isEmpty() && utilEndDate == null)
                    agendaController.addEntry(selectedEntry, utilStartDate, tfDuration.getText());
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please provide a valid duration or end date and a start date.");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("logo.jpeg"));
                    alert.showAndWait();
                }


                tvTask.getItems().remove(selectedEntry);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Entry added to the Agenda successfully.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("logo.jpeg"));
                alert.showAndWait();


            }
        });

        btnSubmit.getStyleClass().add("add-button");


        grid.add(lblStartDate, 0, 3);
        grid.add(dpStartDate, 1, 3);
        grid.add(lblEndDate, 0, 4);
        grid.add(dpEndDate, 1, 4);
        grid.add(lblDuration, 0, 5);
        grid.add(tfDuration, 1, 5);
        grid.add(btnSubmit, 0, 7);

        return grid;
    }
}