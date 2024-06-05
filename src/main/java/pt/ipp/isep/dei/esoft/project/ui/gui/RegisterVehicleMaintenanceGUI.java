package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class RegisterVehicleMaintenanceGUI {

    private VehicleController vehicleController;
    private TableView<Vehicle> tableView;

    public RegisterVehicleMaintenanceGUI() {
        this.vehicleController = new VehicleController();
    }

    public GridPane getRegisterVehicleMaintenanceGUI() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        tableView = new TableView<>();
        tableView.setPrefWidth(750); // Adjust as needed

        // Create the table columns
        TableColumn<Vehicle, String> plateColumn = new TableColumn<>("Plate");
        plateColumn.setCellValueFactory(new PropertyValueFactory<>("plate"));

        TableColumn<Vehicle, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Vehicle, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Vehicle, LocalDate> year = new TableColumn<>("register date");
        year.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getRegisterDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
        );


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        year.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });

        TableColumn<Vehicle, String> kmLastMaintenance = new TableColumn<>("KM of last maintenance");
        kmLastMaintenance.setCellValueFactory(new PropertyValueFactory<>("kmLastMaintenance"));

        TableColumn<Vehicle, String> kmColumn = new TableColumn<>("Current KM");
        kmColumn.setCellValueFactory(new PropertyValueFactory<>("currentKM"));

        TableColumn<Vehicle, String> kmNextMaintenance = new TableColumn<>("KM of next maintenance");
        kmNextMaintenance.setCellValueFactory(new PropertyValueFactory<>("kmNextMaintenance"));

        TableColumn<Vehicle, Integer> kmDifferenceColumn = new TableColumn<>("Km before maintenance");
        kmDifferenceColumn.setCellValueFactory(cellData -> {
            Vehicle vehicle = cellData.getValue();
            int difference = vehicle.getKmNextMaintenance() - vehicle.getCurrentKM();
            return new SimpleObjectProperty<>(difference);
        });


        // Add the columns to the table
        tableView.getColumns().addAll(plateColumn, brandColumn, modelColumn, year, kmLastMaintenance, kmColumn, kmNextMaintenance, kmDifferenceColumn);

        // Set the sort type of the kmDifferenceColumn to DESCENDING

        // Populate the table with vehicles
        tableView.getItems().addAll(vehicleController.getVehicleList());

        kmDifferenceColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableView.getSortOrder().add(kmDifferenceColumn);

        Button registerMaintenanceButton = new Button("Register Maintenance");
        registerMaintenanceButton.getStyleClass().add("add-button");
        registerMaintenanceButton.setOnAction(e -> {
            Vehicle selectedVehicle = tableView.getSelectionModel().getSelectedItem();
            if (selectedVehicle != null) {
                showMaintenanceForm(selectedVehicle);
            }
        });

        VBox tableBox = new VBox(10, tableView, registerMaintenanceButton);
        tableBox.setPadding(new Insets(20, 20, 20, 20));
        tableBox.setAlignment(Pos.CENTER);

        gridPane.add(tableBox, 0, 0);

        return gridPane;
    }

    private void showMaintenanceForm(Vehicle selectedVehicle) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Register Maintenance");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        DatePicker dpRegisterDate = getDatePicker();

        TextField currentKM = new TextField();

        grid.add(new Label("Maintenance Date:"), 0, 0);
        grid.add(dpRegisterDate, 1, 0);
        grid.add(new Label("Current KM:"), 0, 1);
        grid.add(currentKM, 1, 1);
        Label lblError = new Label();
        dialog.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        Node submitButton = dialog.getDialogPane().lookupButton(buttonTypeOk);
        submitButton.managedProperty().bind(submitButton.visibleProperty());

        submitButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!lblError.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Input Error");
                alert.setContentText(lblError.getText());
                alert.showAndWait();
                event.consume();
            } else {
                LocalDate localDate = dpRegisterDate.getValue();
                Date date = java.sql.Date.valueOf(localDate);
                int km = Integer.parseInt(currentKM.getText());
                vehicleController.registerVehicleMaintenance(selectedVehicle, date, km);
                tableView.getItems().setAll(vehicleController.getVehicleList());
            }
        });
        grid.add(lblError, 0, 2);
        dialog.showAndWait();
    }

    private static DatePicker getDatePicker() {
        DatePicker dpRegisterDate = new DatePicker();
        Label lblError = new Label();

        dpRegisterDate.setOnAction(e -> {
            LocalDate selectedDate = dpRegisterDate.getValue();
            if (selectedDate == null || selectedDate.isAfter(LocalDate.now())) {
                dpRegisterDate.setStyle("-fx-border-color: red;");
                lblError.setText("Invalid date selected.");
                lblError.setTextFill(Color.RED);
            } else {
                dpRegisterDate.setStyle("");
                lblError.setText("");
            }
        });
        return dpRegisterDate;
    }
}