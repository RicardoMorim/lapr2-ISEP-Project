package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Type;
import pt.isep.lei.esoft.auth.domain.model.Email;

public class AddGreenSpaceGUI extends Application {

    private StringProperty area = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private GreenSpaceController controller = new GreenSpaceController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Green Space");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);

        // Bind the text property of the nameField to the name property
        name.bind(nameField.textProperty());

        // Add a listener to the name property to validate the name in real-time
        name.addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                nameLabel.setTextFill(Color.RED);
                nameLabel.setText("Name: (Cannot be empty)");
                nameField.setStyle("-fx-border-color: red;");
            } else if (controller.checkIfGreenSpaceNameExists(newValue)) {
                nameLabel.setTextFill(Color.RED);
                nameLabel.setText("Name: (Already exists)");
                nameField.setStyle("-fx-border-color: red;");
            }
            else {
                nameLabel.setTextFill(Color.BLACK);
                nameLabel.setText("Name:");
                nameField.setStyle("");
            }
        });

        Label typeLabel = new Label("Type:");
        ComboBox<Type> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll(Type.values());
        grid.add(typeLabel, 0, 1);
        grid.add(typeComboBox, 1, 1);

        Label areaLabel = new Label("Area:");
        TextField areaField = new TextField();
        grid.add(areaLabel, 0, 2);
        grid.add(areaField, 1, 2);

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();

        // Bind the text property of the addressField to the address property
        address.bind(addressField.textProperty());

        // Add a listener to the address property to validate the address in real-time
        address.addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                addressLabel.setTextFill(Color.RED);
                addressLabel.setText("Address: (Cannot be empty)");
                addressField.setStyle("-fx-border-color: red;");
            } else {
                addressLabel.setTextFill(Color.BLACK);
                addressLabel.setText("Address:");
                addressField.setStyle("");
            }
        });
        grid.add(addressLabel, 0, 3);
        grid.add(addressField, 1, 3);

        Button submitButton = new Button("Submit");
        grid.add(submitButton, 1, 4);




        areaField.setStyle("-fx-border-color: red;");
        // Bind the text property of the areaField to the area property
        area.bind(areaField.textProperty());

        // Add a listener to the area property to validate the area in real-time
        area.addListener((observable,oldValue,newValue)->

        {
            try {
                float areaValue = Float.parseFloat(newValue);
                if (areaValue <= 0) {
                    areaLabel.setTextFill(Color.RED);
                    areaLabel.setText("Area: (Must be a positive number)");
                    areaField.setStyle("-fx-border-color: red;");
                } else {
                    areaLabel.setTextFill(Color.BLACK);
                    areaLabel.setText("Area:");
                    areaField.setStyle("");
                }
            } catch (NumberFormatException ex) {
                areaLabel.setTextFill(Color.RED);
                areaLabel.setText("Area: (Must be a number)");
            }
        });


        submitButton.setOnAction(e -> {
            try {
                // Here you can create a new GreenSpace object with the entered data
                GreenSpace greenSpace = new GreenSpace(nameField.getText(), addressField.getText(), Float.parseFloat(areaField.getText()), typeComboBox.getValue(), new Email("123@gmail.com"));
                // And then you can add it to your repository
                controller.addGreenSpace(greenSpace);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Green space added successfully.");
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}