package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.TeamController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class PostponeEntryGUI {

    private final AgendaController agendaController = new AgendaController();
    private final TeamController teamController = new TeamController();

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

        btnPostpone.getStyleClass().add("add-button");

        btnPostpone.setOnAction(e -> {
            AgendaEntry selectedEntry = tableEntries.getSelectionModel().getSelectedItem();
            LocalDate newDate = dpNewDate.getValue();
            java.util.Date date = java.util.Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date oldStartDate = selectedEntry.getStartDate();
            boolean isDateAvailableForTeam = agendaController.isDateAvailableForTeam(date, selectedEntry);
            boolean isDateAvailableForVehicles = agendaController.isDateAvailableForVehicles(date, selectedEntry);
            if (selectedEntry != null && date != null) {
                if (isDateAvailableForTeam && isDateAvailableForVehicles) {
                    selectedEntry = agendaController.postponeEntry(selectedEntry, date);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("logo.png"));
                    if (selectedEntry.getStatus().equals(Status.POSTPONED)) {
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("The entry has been successfully postponed.");
                        teamController.notifyPostPoneTeamMembers(selectedEntry, oldStartDate);
                    } else {
                        alert.setTitle("Failure");
                        alert.setHeaderText(null);
                        alert.setContentText("The entry could not be postponed. Try again later.");
                    }
                    alert.showAndWait();
                    tableEntries.getItems().setAll(agendaController.getAgenda().getEntries());
                } else {
                    List<Date> suggestions = agendaController.findNearestAvailableDates(date, selectedEntry);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    String alertMessage = "";
                    if (suggestions.size() == 1) {
                        LocalDate suggestedDate = suggestions.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        alert.setContentText("The chosen date is not available. Here is a suggestion: " + suggestedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    } else {
                        LocalDate suggestedDate1 = suggestions.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate suggestedDate2 = suggestions.get(1).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        alertMessage += "The chosen date is not available. Here are some suggestions: \n" +
                                "Closest date before: " + suggestedDate1.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                                "Closest date after: " + suggestedDate2.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    }
                    if (isDateAvailableForTeam && !isDateAvailableForVehicles) {
                        alert.setTitle("Can not postpone the entry due to vehicle availability");
                        alertMessage += "\nIf these dates are not suitable, try to change the vehicles in the entry, and try again later.";
                    } else if (!isDateAvailableForTeam && isDateAvailableForVehicles) {
                        alert.setTitle("Can not postpone the entry due to team availability");
                        alertMessage += "\nIf these dates are not suitable, try to change the team in the entry, and try again later.";
                    } else {
                        alert.setTitle("Can not postpone the entry due to team and vehicle availability");
                        alertMessage += "\nIf these dates are not suitable, try to change the team and vehicles in the entry, and try again later.";
                    }
                    alert.setContentText(alertMessage);
                    alert.setWidth(600);
                    alert.showAndWait();
                }
            }
        });

        vbox.getChildren().addAll(tableEntries, lblCurrentDate, lblDate, dpNewDate, btnPostpone);
        grid.add(vbox, 0, 0);
        return grid;
    }
}