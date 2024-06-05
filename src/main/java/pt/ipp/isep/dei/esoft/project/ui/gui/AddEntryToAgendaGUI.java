package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.ToDoListController;
import pt.ipp.isep.dei.esoft.project.domain.Entry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddEntryToAgendaGUI {

    private AgendaController agendaController;
    private ToDoListController toDoListController;

    public AddEntryToAgendaGUI() {

        agendaController = new AgendaController();
        toDoListController = new ToDoListController();
    }

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
        tvTask.setItems(FXCollections.observableArrayList(agendaController.getToDoEntriesNotInAgenda(toDoListController.getEntries())));

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
                if (lblStartDate.getText().contains("required") || lblEndDate.getText().contains("Cannot") || lblDuration.getText().contains("Cannot"))
                    new Alert(Alert.AlertType.ERROR, "Please correct the errors before submitting.").showAndWait();

                LocalDate startDate = dpStartDate.getValue();
                LocalDate endDate = dpEndDate.getValue();

                if (startDate == null) {
                    new Alert(Alert.AlertType.ERROR, "Start date is required.").showAndWait();
                    return;
                }

                java.util.Date utilStartDate = java.sql.Date.valueOf(startDate);

                java.util.Date utilEndDate = endDate != null ? java.sql.Date.valueOf(endDate) : null;

                if (utilEndDate != null && utilStartDate.after(utilEndDate))
                    new Alert(Alert.AlertType.ERROR, "End date cannot be before start date.").showAndWait();
                else if (utilStartDate != null && tfDuration.getText().isEmpty())
                    agendaController.addEntry(selectedEntry, utilStartDate, utilEndDate);
                else if (utilStartDate != null && !tfDuration.getText().isEmpty())
                    agendaController.addEntry(selectedEntry, utilStartDate, utilEndDate, tfDuration.getText());
                else if (utilStartDate == null && !tfDuration.getText().isEmpty())
                    agendaController.addEntry(selectedEntry, utilEndDate, tfDuration.getText());

                tvTask.getItems().remove(selectedEntry);

                new Alert(Alert.AlertType.INFORMATION, "Entry added to the Agenda successfully.");
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