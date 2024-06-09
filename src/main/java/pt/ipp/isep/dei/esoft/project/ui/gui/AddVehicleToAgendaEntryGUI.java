package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

/**
 * The type Add vehicle to agenda entry gui.
 */
public class AddVehicleToAgendaEntryGUI {

    private AgendaController agendaController = new AgendaController();
    private VehicleController vehicleController = new VehicleController();

    /**
     * Gets add vehicle to agenda entry gui grid pane.
     *
     * @param height the height
     * @param width  the width
     * @return the add vehicle to agenda entry gui grid pane
     */
    public GridPane getAddVehicleToAgendaEntryGUIGridPane(double height, double width) {
        GridPane grid = new GridPane(height, width);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);

        TableView<AgendaEntry> tableNotDoneEntries = new TableView<>();
        tableNotDoneEntries.setPrefWidth(400);

        TableColumn<AgendaEntry, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<AgendaEntry, String> descColumn = new TableColumn<>("Description");
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.ENGLISH);

        TableColumn<AgendaEntry, Date> startDateColumn = new TableColumn<>("Start Date");
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startDateColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<AgendaEntry, Date> call(TableColumn<AgendaEntry, Date> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(formatter.format(item.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
                        }
                    }
                };
            }
        });

        TableColumn<AgendaEntry, Date> endDateColumn = new TableColumn<>("End Date");
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        endDateColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<AgendaEntry, Date> call(TableColumn<AgendaEntry, Date> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(formatter.format(item.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
                        }
                    }
                };
            }
        });

        TableColumn<AgendaEntry, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableNotDoneEntries.getColumns().addAll(titleColumn, descColumn, startDateColumn, endDateColumn, statusColumn);
        tableNotDoneEntries.getItems().addAll(agendaController.getNotDoneEntries());

        ListView<Vehicle> lvAvailableVehicles = new ListView<>();
        lvAvailableVehicles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lvAvailableVehicles.getItems().addAll(agendaController.getVehiclesNotAssignedToAnyAgendaEntry(vehicleController.getVehicleList()));

        ListView<Vehicle> lvAssignedVehicles = new ListView<>();
        lvAssignedVehicles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tableNotDoneEntries.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            lvAssignedVehicles.getItems().clear();
            lvAvailableVehicles.getItems().clear();
            if (newSelection != null) {
                lvAssignedVehicles.getItems().addAll(agendaController.getVehiclesAssignedToAgendaEntry(newSelection));
                lvAvailableVehicles.getItems().addAll(agendaController.getVehiclesNotAssignedAtDates(vehicleController.getVehicleList(), newSelection.getStartDate(), newSelection.getEndDate()));
            }
        });

        Button btnAddVehicle = new Button("Add Selected Vehicle");
        btnAddVehicle.getStyleClass().add("add-button");
        btnAddVehicle.setOnAction(e -> {
            Vehicle selectedVehicle = lvAvailableVehicles.getSelectionModel().getSelectedItem();
            AgendaEntry selectedAgendaEntry = tableNotDoneEntries.getSelectionModel().getSelectedItem();
            if (selectedVehicle != null && selectedAgendaEntry != null) {
                try {
                    agendaController.addVehiclesToAgendaEntry(selectedAgendaEntry, selectedVehicle);
                    lvAvailableVehicles.getItems().remove(selectedVehicle);
                    lvAssignedVehicles.getItems().add(selectedVehicle);
                } catch (IllegalArgumentException ex) {
                    System.out.println("There was an error adding the vehicle to the agenda entry.\n" + ex.getMessage());
                }
            }
        });

        Button btnRemoveVehicle = new Button("Remove Selected Vehicle");
        btnRemoveVehicle.getStyleClass().add("remove-button");
        btnRemoveVehicle.setOnAction(e -> {
            Vehicle selectedVehicle = lvAssignedVehicles.getSelectionModel().getSelectedItem();
            AgendaEntry selectedAgendaEntry = tableNotDoneEntries.getSelectionModel().getSelectedItem();
            if (selectedVehicle != null && selectedAgendaEntry != null) {
                try {
                    selectedAgendaEntry.removeVehicle(selectedVehicle);
                    lvAssignedVehicles.getItems().remove(selectedVehicle);
                    lvAvailableVehicles.getItems().add(selectedVehicle);
                } catch (IllegalArgumentException ex) {
                    System.out.println("There was an error removing the vehicle from the agenda entry.\n" + ex.getMessage());
                }
            }
        });

        lvAvailableVehicles.setPrefWidth(500);
        lvAvailableVehicles.setPrefHeight(300);
        lvAssignedVehicles.setPrefWidth(500);
        lvAssignedVehicles.setPrefHeight(300);
        tableNotDoneEntries.setPrefWidth(500);
        tableNotDoneEntries.setPrefHeight(300);


        VBox vbox = new VBox(10, lvAvailableVehicles, btnAddVehicle, lvAssignedVehicles, btnRemoveVehicle);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        grid.add(tableNotDoneEntries, 0, 0);
        grid.add(vbox, 1, 0);
        return grid;
    }
}