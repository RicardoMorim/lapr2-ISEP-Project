package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

public class RegisterVehicleGUI {

    VehicleController vehicleController;

    public RegisterVehicleGUI() {
        this.vehicleController = new VehicleController();
    }

    public GridPane getRegisterVehicleGUI(double height, double width) {
        GridPane gridPane = new GridPane(height, width);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add your form fields here, similar to the RegisterCollaboratorGUI class
        // For example:
        Label plateLabel = new Label("Plate: ");
        gridPane.add(plateLabel, 0, 1);
        TextField plateField = new TextField();
        plateField.setPrefWidth(150);
        gridPane.add(plateField, 1, 1);

        // Add more fields as needed...

        Button submitButton = new Button("Register Vehicle");
        submitButton.getStyleClass().add("add-button");
        Button removeButton = new Button("Remove Vehicle");
        removeButton.getStyleClass().add("remove-button");

        HBox buttonBox = new HBox(10, submitButton);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 13, 2, 1);

        TableView<Vehicle> tableView = new TableView<>();
        tableView.setPrefWidth(500); // Adjust as needed

        // Create the table columns
        TableColumn<Vehicle, String> plateColumn = new TableColumn<>("Plate");
        plateColumn.setCellValueFactory(new PropertyValueFactory<>("plate"));

        // Add more columns as needed...

        // Add the columns to the table
        tableView.getColumns().addAll(plateColumn);

        // Populate the table with vehicles
        tableView.getItems().addAll(vehicleController.getVehicleList());

        VBox tableBox = new VBox(10, tableView, removeButton);
        tableBox.setPadding(new Insets(20, 20, 20, 20));
        tableBox.setAlignment(Pos.CENTER);

        GridPane newGrid = new GridPane();
        newGrid.add(gridPane, 0, 0);
        newGrid.add(tableBox, 0, 1); // Adjust as needed
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