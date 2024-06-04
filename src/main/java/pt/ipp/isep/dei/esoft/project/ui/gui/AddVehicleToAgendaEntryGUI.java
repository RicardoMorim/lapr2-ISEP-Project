package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.List;

public class AddVehicleToAgendaEntryGUI extends Application {

    private AgendaController agendaController = new AgendaController();
    private VehicleController vehicleController = new VehicleController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Add/Remove Vehicle to/from Agenda Entry");

        ComboBox<AgendaEntry> cbAgendaEntry = new ComboBox<>();
        cbAgendaEntry.getItems().addAll(agendaController.getAgenda().getEntries());

        ListView<Vehicle> lvAvailableVehicles = new ListView<>();
        lvAvailableVehicles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lvAvailableVehicles.getItems().addAll(agendaController.getVehiclesNotAssignedToAnyAgendaEntry(vehicleController.getVehicleList()));

        ListView<Vehicle> lvAssignedVehicles = new ListView<>();
        lvAssignedVehicles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        if (cbAgendaEntry.getValue() != null)
            lvAssignedVehicles.getItems().addAll(agendaController.getVehiclesAssignedToAgendaEntry(cbAgendaEntry.getValue()));
        cbAgendaEntry.setOnAction(e -> {
            lvAssignedVehicles.getItems().removeAll(lvAssignedVehicles.getItems());
            lvAssignedVehicles.getItems().addAll(agendaController.getVehiclesAssignedToAgendaEntry(cbAgendaEntry.getValue()));
        });
        Button btnAddVehicle = new Button("Add Selected Vehicle");
        btnAddVehicle.setOnAction(e -> {
            Vehicle selectedVehicle = lvAvailableVehicles.getSelectionModel().getSelectedItem();
            AgendaEntry selectedAgendaEntry = cbAgendaEntry.getValue();
            if (selectedVehicle != null && selectedAgendaEntry != null) {
                try {
                    agendaController.addVehiclesToAgendaEntry(selectedAgendaEntry, selectedVehicle);
                    lvAvailableVehicles.getItems().removeAll(lvAvailableVehicles.getItems());
                    lvAvailableVehicles.getItems().addAll(agendaController.getVehiclesNotAssignedToAnyAgendaEntry(vehicleController.getVehicleList()));
                    lvAssignedVehicles.getItems().removeAll(lvAssignedVehicles.getItems());
                    lvAssignedVehicles.getItems().addAll(agendaController.getVehiclesAssignedToAgendaEntry(selectedAgendaEntry));

                } catch (IllegalArgumentException ex) {
                    System.out.println("There was an error adding the vehicle to the agenda entry.\n" + ex.getMessage());
                }
            }
        });

        Button btnRemoveVehicle = new Button("Remove Selected Vehicle");
        btnRemoveVehicle.setOnAction(e -> {
            Vehicle selectedVehicle = lvAssignedVehicles.getSelectionModel().getSelectedItem();
            AgendaEntry selectedAgendaEntry = cbAgendaEntry.getValue();
            if (selectedVehicle != null && selectedAgendaEntry != null) {
                try {
                    selectedAgendaEntry.removeVehicle(selectedVehicle);
                    lvAvailableVehicles.getItems().removeAll(lvAvailableVehicles.getItems());
                    lvAvailableVehicles.getItems().addAll(agendaController.getVehiclesNotAssignedToAnyAgendaEntry(vehicleController.getVehicleList()));
                    lvAssignedVehicles.getItems().removeAll(lvAssignedVehicles.getItems());
                    lvAssignedVehicles.getItems().addAll(agendaController.getVehiclesAssignedToAgendaEntry(selectedAgendaEntry));

                } catch (IllegalArgumentException ex) {
                    System.out.println("There was an error removing the vehicle from the agenda entry.\n" + ex.getMessage());
                }
            }
        });

        VBox vbox = new VBox(10, cbAgendaEntry, lvAvailableVehicles, lvAssignedVehicles, btnAddVehicle, btnRemoveVehicle);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}