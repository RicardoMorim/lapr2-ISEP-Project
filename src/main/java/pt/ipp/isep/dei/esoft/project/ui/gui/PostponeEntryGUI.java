package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class PostponeEntryGUI{

    private final AgendaController agendaController = new AgendaController();

    public GridPane getPostponeEntryGridPane(double height, double width) {
        GridPane grid = new GridPane(height, width);
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        // Create a new TableView for the entries
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

        List<AgendaEntry> agendaEntries = agendaController.getAgenda().getEntries();
        tableEntries.getItems().addAll(agendaEntries);

        Label lblCurrentDate = new Label();
        tableEntries.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblCurrentDate.setText("Current Date: " + newValue.getStartDate().toString());
            }
        });

        Label lblDate = new Label("New Date:");
        DatePicker dpNewDate = new DatePicker();

        Button btnPostpone = new Button("Postpone");

        // Add the CSS class to the button
        btnPostpone.getStyleClass().add("add-button");

        btnPostpone.setOnAction(e -> {
            AgendaEntry selectedEntry = tableEntries.getSelectionModel().getSelectedItem();
            LocalDate newDate = dpNewDate.getValue();
            java.util.Date date = java.sql.Date.valueOf(newDate);
            if (selectedEntry != null && date != null) {
                selectedEntry = agendaController.postponeEntry(selectedEntry, date);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (selectedEntry.getStatus().equals(Status.POSTPONED)) {
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("The entry has been successfully postponed.");
                } else {
                    alert.setTitle("Failure");
                    alert.setHeaderText(null);
                    alert.setContentText("The entry could not be postponed.");
                }
                alert.showAndWait();
            }
        });

        vbox.getChildren().addAll(tableEntries, lblCurrentDate, lblDate, dpNewDate, btnPostpone);
        grid.add(vbox, 0, 0);
        return grid;
    }
}