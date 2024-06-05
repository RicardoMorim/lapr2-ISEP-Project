package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class RegisterVehicleGUI {

    VehicleController vehicleController;

    public RegisterVehicleGUI() {
        this.vehicleController = new VehicleController();
    }

    public GridPane getRegisterVehicleGUI(double height, double width) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label plateLabel = new Label("Plate: ");
        gridPane.add(plateLabel, 0, 1);
        TextField plateField = new TextField();
        plateField.setPrefWidth(150);
        gridPane.add(plateField, 1, 1);

        Label brandLabel = new Label("Brand: ");
        gridPane.add(brandLabel, 0, 2);
        TextField brandField = new TextField();
        brandField.setPrefWidth(150);
        gridPane.add(brandField, 1, 2);

        Label modelLabel = new Label("Model: ");
        gridPane.add(modelLabel, 0, 3);
        TextField modelField = new TextField();
        modelField.setPrefWidth(150);
        gridPane.add(modelField, 1, 3);

        Label typeLabel = new Label("Type: ");
        gridPane.add(typeLabel, 0, 4);
        TextField typeField = new TextField();
        typeField.setPrefWidth(150);
        gridPane.add(typeField, 1, 4);

        Label tareWeightLabel = new Label("Tare Weight: ");
        gridPane.add(tareWeightLabel, 0, 5);
        TextField tareWeightField = new TextField();
        tareWeightField.setPrefWidth(150);
        gridPane.add(tareWeightField, 1, 5);

        Label grossWeightLabel = new Label("Gross Weight: ");
        gridPane.add(grossWeightLabel, 0, 6);
        TextField grossWeightField = new TextField();
        grossWeightField.setPrefWidth(150);
        gridPane.add(grossWeightField, 1, 6);

        Label currentKMLabel = new Label("Current KM: ");
        gridPane.add(currentKMLabel, 0, 7);
        TextField currentKMField = new TextField();
        currentKMField.setPrefWidth(150);
        gridPane.add(currentKMField, 1, 7);

        Label registerDateLabel = new Label("Register Date: ");
        gridPane.add(registerDateLabel, 0, 8);
        DatePicker registerDatePicker = new DatePicker();
        gridPane.add(registerDatePicker, 1, 8);

        Label acquisitionDateLabel = new Label("Acquisition Date: ");
        gridPane.add(acquisitionDateLabel, 0, 9);
        DatePicker acquisitionDatePicker = new DatePicker();
        gridPane.add(acquisitionDatePicker, 1, 9);

        Label checkupIntervalKMLabel = new Label("Checkup Interval KM: ");
        gridPane.add(checkupIntervalKMLabel, 0, 10);
        TextField checkupIntervalKMField = new TextField();
        checkupIntervalKMField.setPrefWidth(150);
        gridPane.add(checkupIntervalKMField, 1, 10);

        Label kmLastMaintenanceLabel = new Label("KM Last Maintenance: ");
        gridPane.add(kmLastMaintenanceLabel, 0, 11);
        TextField kmLastMaintenanceField = new TextField();
        kmLastMaintenanceField.setPrefWidth(150);
        gridPane.add(kmLastMaintenanceField, 1, 11);

        Button submitButton = new Button("Register Vehicle");
        submitButton.getStyleClass().add("add-button");
        Button removeButton = new Button("Remove Vehicle");
        removeButton.getStyleClass().add("remove-button");

        HBox buttonBox = new HBox(10, submitButton, removeButton);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 12, 2, 1);

        TableView<Vehicle> tableView = new TableView<>();
        tableView.setPrefWidth(500); // Adjust as needed

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

        year.setCellFactory(column -> {
            return new TableCell<Vehicle, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    } else {
                        setText(formatter.format(item));
                    }
                }
            };
        });

        TableColumn<Vehicle, String> kmColumn = new TableColumn<>("Current KM");
        kmColumn.setCellValueFactory(new PropertyValueFactory<>("currentKM"));

        TableColumn<Vehicle, String> kmNextMaintenance = new TableColumn<>("KM of next maintenance");
        kmNextMaintenance.setCellValueFactory(new PropertyValueFactory<>("kmNextMaintenance"));


        // Add the columns to the table
        tableView.getColumns().addAll(plateColumn, brandColumn, modelColumn, year, kmColumn, kmNextMaintenance);


        // Populate the table with vehicles
        tableView.getItems().addAll(vehicleController.getVehicleList());

        VBox tableBox = new VBox(10, tableView, removeButton);
        tableBox.setPadding(new Insets(20, 20, 20, 20));
        tableBox.setAlignment(Pos.CENTER);

        HBox mainLayout = new HBox(10, gridPane, tableBox);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));
        mainLayout.setAlignment(Pos.CENTER);

        GridPane newGrid = new GridPane();
        newGrid.add(mainLayout, 0, 0);
        newGrid.setAlignment(Pos.CENTER);

        return newGrid;
    }

    // Add validation methods here, similar to the RegisterCollaboratorGUI class
    // For example:
    private boolean validateTextField(TextField textField, Label label) {
        if (textField.getText().trim().isEmpty()) {
            setFieldError(textField, label, "This field cannot be empty.");
            return false;
        } else {
            clearFieldError(textField, label);
            return true;
        }
    }

    private void setFieldError(Control control, Label label, String errorMessage) {
        control.setStyle("-fx-border-color: red;");
        label.setStyle("-fx-text-fill: red;");
        if (!label.getText().contains(" - ")) {
            label.setText(label.getText() + " - " + errorMessage);
        }
    }

    private void clearFieldError(Control control, Label label) {
        control.setStyle("");
        label.setStyle("");
        if (label.getText().contains(" - ")) {
            label.setText(label.getText().substring(0, label.getText().indexOf(" - ")));
        }
    }
}