package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Status;

import java.time.LocalDate;
import java.util.List;

public class PostponeEntryGUI extends Application {

    private final AgendaController agendaController = new AgendaController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Postpone an Entry");

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        Label lblEntry = new Label("Entry:");
        ComboBox<AgendaEntry> cbEntries = new ComboBox<>();
        List<AgendaEntry> agendaEntries = agendaController.getAgenda().getEntries();
        cbEntries.getItems().addAll(agendaEntries);

        Label lblCurrentDate = new Label();
        cbEntries.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblCurrentDate.setText("Current Date: " + newValue.getStartDate().toString());
            }
        });

        Label lblDate = new Label("New Date:");
        DatePicker dpNewDate = new DatePicker();

        Button btnPostpone = new Button("Postpone");

        btnPostpone.setOnAction(e -> {
            AgendaEntry selectedEntry = cbEntries.getSelectionModel().getSelectedItem();
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

        vbox.getChildren().addAll(lblEntry, cbEntries, lblCurrentDate, lblDate, dpNewDate, btnPostpone);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}