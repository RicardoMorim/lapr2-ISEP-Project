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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

        Label lblDate = new Label("Date:");
        DatePicker datePicker = new DatePicker();

        Button btnPostpone = new Button("Postpone");

        // Add event handler to the button
        btnPostpone.setOnAction(e -> {
            AgendaEntry selectedEntry = cbEntries.getSelectionModel().getSelectedItem();
            LocalDate selectedDate = datePicker.getValue();
            agendaController.postponeEntry(selectedEntry, Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));



            if (selectedEntry != null && selectedDate != null) {
                // Call the controller method to postpone the entry
               LocalDate entryDate = selectedEntry.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (selectedDate.equals(entryDate)) {
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

        vbox.getChildren().addAll(lblEntry, cbEntries, lblDate, datePicker, btnPostpone);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}