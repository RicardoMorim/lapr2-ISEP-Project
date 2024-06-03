package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Status;

import java.util.List;

public class CancelGUI extends Application {

    private final AgendaController agendaController = new AgendaController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cancel an Entry");

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        Label lblEntry = new Label("Entry:");
        ComboBox<AgendaEntry> cbEntries = new ComboBox<>();
        List<AgendaEntry> agendaEntries = agendaController.getAgenda().getEntries();
        cbEntries.getItems().addAll(agendaEntries);

        Button btnCancel = new Button("Cancel");

        // Add event handler to the button
        btnCancel.setOnAction(e -> {
            AgendaEntry selectedEntry = cbEntries.getSelectionModel().getSelectedItem();

            if (selectedEntry != null) {
                agendaController.cancelAgendaEntry(selectedEntry);
            }

            if (selectedEntry.getStatus() == Status.CANCELED) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Entry Canceled");
                alert.setContentText("The entry was successfully canceled.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("There was an error canceling the entry.");
                alert.showAndWait();
            }
        });

        vbox.getChildren().addAll(lblEntry, cbEntries, btnCancel);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}