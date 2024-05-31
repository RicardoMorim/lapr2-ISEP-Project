package pt.ipp.isep.dei.esoft.project.ui.gui;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import java.util.List;
public class AddVehicleToAgendaEntryGUI extends Application {
    private AgendaController agendaController = new AgendaController();
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Add Vehicle to Agenda Entry");
        ComboBox<AgendaEntry> cbAgendaEntry = new ComboBox<>();
        cbAgendaEntry.getItems().addAll(agendaController.getAgenda().getEntries());
        ListView<Vehicle> lvVehicle = new ListView<>();
        lvVehicle.getItems().addAll(agendaController.getVehiclesNotAssignedToAnyAgendaEntry());
        Button btnAddVehicle = new Button("Add Vehicle");
        btnAddVehicle.setOnAction(e -> {
            Vehicle selectedVehicle = lvVehicle.getSelectionModel().getSelectedItem();
            AgendaEntry selectedAgendaEntry = cbAgendaEntry.getValue();
            if (selectedVehicle != null && selectedAgendaEntry != null) {
                agendaController.addVehiclesToAgendaEntry(selectedAgendaEntry, List.of(selectedVehicle));
                lvVehicle.getItems().remove(selectedVehicle);
            }
        });
        VBox vbox = new VBox(10, cbAgendaEntry, lvVehicle, btnAddVehicle);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}