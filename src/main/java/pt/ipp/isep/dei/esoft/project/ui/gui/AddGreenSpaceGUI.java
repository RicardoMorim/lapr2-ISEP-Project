package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.Address;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Type;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

/**
 * GUI for managing GreenSpace objects.
 */
public class AddGreenSpaceGUI {

    /**
     * Constructs a new AddGreenSpaceGUI.
     */
    private GreenSpaceController controller;

    /**
     * Returns the main GridPane of the GUI.
     * @return the main GridPane
     */
    public GridPane getGridPane() {
        controller = new GreenSpaceController();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);


        TextField nameField = new TextField();
        TextField streetField = new TextField();
        TextField zipCodeField = new TextField();
        TextField cityField = new TextField();

        // Create a TableView for the green spaces
        TableView<GreenSpace> tableGreenSpaces = new TableView<>();
        TableColumn<GreenSpace, String> colName = new TableColumn<>("Name");
        TableColumn<GreenSpace, String> colAddress = new TableColumn<>("Address");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().toString()));
        tableGreenSpaces.getColumns().addAll(colName, colAddress);
        tableGreenSpaces.getItems().addAll(controller.getGreenSpaceList());


        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> {
            boolean allFieldsFilled = !nameField.getText().isEmpty() && !streetField.getText().isEmpty() &&
                    !zipCodeField.getText().isEmpty() && !cityField.getText().isEmpty();

            if (allFieldsFilled) {
                String name = nameField.getText();
                String street = streetField.getText();
                String zipCode = zipCodeField.getText();
                String city = cityField.getText();


                if (controller.checkIfGreenSpaceNameExists(name)) {
                    showAlert("A Green Space with this name already exists.");
                    return;
                }
                Address address = null;
                try {
                    address = new Address(street, city, zipCode);
                } catch (IllegalArgumentException error) {
                    System.out.println(error.getMessage());
                }
                if (controller.checkIfGreenSpaceAddressExists(address) && address != null) {
                    showAlert("A Green Space with this address already exists.");
                    return;
                }

                // Proceed to next stage
                grid.getChildren().setAll(showNextStage(name, address));
            } else {
                showAlert("All fields must be filled");
            }
        });


        Label nameLabel = new Label("Name: ");
        Label streetLabel = new Label("Street: ");
        Label zipCodeLabel = new Label("Zip Code: ");
        Label cityLabel = new Label("City: ");

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controller.checkIfGreenSpaceNameExists(newValue)) {
                nameField.setStyle("-fx-text-inner-color: red;");
                nameLabel.setStyle("-fx-text-fill: red;");
                nameLabel.setText("Name: (Name already exists)");

            } else if (newValue.isEmpty()) {
                nameField.setStyle("-fx-text-inner-color: red;");
                nameLabel.setStyle("-fx-text-fill: red;");
                nameLabel.setText("Name: (Name cannot be empty)");
            } else {
                nameField.setStyle("-fx-text-inner-color: black;");
                nameLabel.setStyle("-fx-text-fill: black;");
                nameLabel.setText("Name: ");
            }
        });

        streetField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                streetField.setStyle("-fx-text-inner-color: red;");
                streetLabel.setStyle("-fx-text-fill: red;");
                streetLabel.setText("Street: (Street cannot be empty)");
            } else {
                streetField.setStyle("-fx-text-inner-color: black;");
                streetLabel.setStyle("-fx-text-fill: black;");
                streetLabel.setText("Street: ");
            }
        });

        zipCodeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!controller.validateZipCode(newValue)) {
                zipCodeField.setStyle("-fx-text-inner-color: red;");
                zipCodeLabel.setStyle("-fx-text-fill: red;");
                zipCodeLabel.setText("Zip Code: (Invalid Zip Code, must be in the format 0000-000)");
            } else if (newValue.isEmpty()) {
                zipCodeField.setStyle("-fx-text-inner-color: red;");
                zipCodeLabel.setStyle("-fx-text-fill: red;");
                zipCodeLabel.setText("Zip Code: (Zip Code cannot be empty)");
            } else {
                zipCodeField.setStyle("-fx-text-inner-color: black;");
                zipCodeLabel.setStyle("-fx-text-fill: black;");
                zipCodeLabel.setText("Zip Code: ");
            }
        });

        cityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                cityField.setStyle("-fx-text-inner-color: red;");
                cityLabel.setStyle("-fx-text-fill: red;");
                cityLabel.setText("City: (City cannot be empty)");
            } else {
                cityField.setStyle("-fx-text-inner-color: black;");
                cityLabel.setStyle("-fx-text-fill: black;");
                cityLabel.setText("City: ");
            }
        });

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(streetLabel, 0, 1);
        grid.add(streetField, 1, 1);
        grid.add(zipCodeLabel, 0, 2);
        grid.add(zipCodeField, 1, 2);
        grid.add(cityLabel, 0, 3);
        grid.add(cityField, 1, 3);
        grid.add(nextButton, 1, 4);

        Button btnRemove = new Button("Remove Green Space");
        btnRemove.setOnAction(e -> {
            GreenSpace selectedGreenSpace = tableGreenSpaces.getSelectionModel().getSelectedItem();
            if (selectedGreenSpace != null) {
                controller.removeGreenSpace(selectedGreenSpace);
                tableGreenSpaces.getItems().remove(selectedGreenSpace);
            } else {
                showAlert("No green space selected.");
            }
        });

        VBox vboxButtons = new VBox(10);
        vboxButtons.setPadding(new Insets(15, 0, 0, 0));


        // Add a class to the buttons
        nextButton.getStyleClass().add("add-button");
        btnRemove.getStyleClass().add("remove-button");

        vboxButtons.getChildren().addAll(nextButton, btnRemove);

        grid.add(vboxButtons, 1, 4);

        GridPane.setConstraints(tableGreenSpaces, 3, 0, 1, 5);
        grid.getChildren().add(tableGreenSpaces);

        return grid;
    }

    /**
     * Shows an alert with the specified message.
     * @param message the message to display in the alert
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Returns a GridPane for the next stage of the GUI.
     * @param name the name of the GreenSpace
     * @param address the address of the GreenSpace
     * @return a GridPane for the next stage
     */
    private GridPane showNextStage(String name, Address address) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);


        TextField areaField = new TextField();
        ComboBox<Type> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll(Type.values());
        Label areaLabel = new Label("Area: ");

        areaField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                areaField.setStyle("-fx-text-inner-color: red;");
                areaLabel.setStyle("-fx-text-fill: red;");
                areaLabel.setText("Area: (Area must be a number)");
            } else {
                areaField.setStyle("-fx-text-inner-color: black;");
                areaLabel.setStyle("-fx-text-fill: black;");
                areaLabel.setText("Area: ");
            }
        });


        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            boolean allFieldsFilled = !areaField.getText().isEmpty() && typeComboBox.getValue() != null;

            if (allFieldsFilled) {
                double area;
                try {
                    area = Double.parseDouble(areaField.getText());
                } catch (NumberFormatException ex) {
                    showAlert("Area must be a number.");
                    return;
                }
                Type type = typeComboBox.getValue();

                if (area <= 0) {
                    showAlert("Area must be greater than 0.");
                    return;
                }

                try {
                    controller.addGreenSpace(name, address, area, type, Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId());

                    grid.getChildren().setAll(getGridPane().getChildren());
                } catch (IllegalArgumentException ex) {
                    showAlert(ex.getMessage());
                }
            } else {
                showAlert("All fields must be filled");
            }
        });

        grid.add(areaLabel, 0, 0);
        grid.add(areaField, 1, 0);
        grid.add(new Label("Type:"), 0, 1);
        grid.add(typeComboBox, 1, 1);
        grid.add(submitButton, 1, 2);

        // Create a TableView for the green spaces
        TableView<GreenSpace> tableGreenSpaces = new TableView<>();
        TableColumn<GreenSpace, String> colName = new TableColumn<>("Name");
        TableColumn<GreenSpace, String> colAddress = new TableColumn<>("Address");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().toString()));
        tableGreenSpaces.getColumns().addAll(colName, colAddress);
        tableGreenSpaces.getItems().addAll(controller.getGreenSpaceList());

        Button btnRemove = new Button("Remove Green Space");
        btnRemove.setOnAction(e -> {
            GreenSpace selectedGreenSpace = tableGreenSpaces.getSelectionModel().getSelectedItem();
            if (selectedGreenSpace != null) {
                controller.removeGreenSpace(selectedGreenSpace);
                tableGreenSpaces.getItems().remove(selectedGreenSpace);
            } else {
                showAlert("No green space selected.");
            }
        });

        VBox vboxButtons = new VBox(10);


        // Add a class to the buttons
        submitButton.getStyleClass().add("add-button");
        btnRemove.getStyleClass().add("remove-button");

        vboxButtons.getChildren().addAll(submitButton, btnRemove);

        grid.add(vboxButtons,1,5);

        GridPane.setConstraints(tableGreenSpaces, 3, 0, 1, 5); // Move the table to the third column
        grid.getChildren().add(tableGreenSpaces);


        return grid;
    }
}