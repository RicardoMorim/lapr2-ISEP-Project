package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
<<<<<<< HEAD
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
=======
import javafx.scene.control.*;
>>>>>>> ricardo
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Status;

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

        Button btnPostpone = new Button("Postpone");

        btnPostpone.setOnAction(e -> {
            AgendaEntry selectedEntry = cbEntries.getSelectionModel().getSelectedItem();

            if (selectedEntry != null) {
<<<<<<< HEAD
                selectedEntry.setStatus(Status.POSTPONED);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (selectedEntry.getStatus() == Status.POSTPONED) {
=======

                agendaController.postponeEntry(selectedEntry);

                // Call the controller method to postpone the entry
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (selectedEntry.getStatus().equals(Status.POSTPONED)) {
>>>>>>> ricardo
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

        vbox.getChildren().addAll(lblEntry, cbEntries, btnPostpone);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}