package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.EmailWrapper;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class GreenSpaceManagerGUI extends Application {

    private GreenSpaceController controller;

    @Override
    public void start(Stage primaryStage) {
        controller = new GreenSpaceController();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label emailLabel = new Label("Email: ");
        TextField emailField = new TextField();
        Button fetchButton = new Button("Fetch Managed Green Spaces");

        ListView<String> greenSpaceListView = new ListView<>();

        fetchButton.setOnAction(e -> {
            String email = emailField.getText();
            if (email.isEmpty()) {
                showAlert("Email cannot be empty.");
                return;
            }
            try{
                new Email(email);
            } catch (IllegalArgumentException ex) {
                showAlert("Invalid email.");
                return;
            }
            EmailWrapper user = new EmailWrapper(new Email(email));
            List<GreenSpace> managedGreenSpaces = controller.getGreenSpacesManagedByUser(user);

            greenSpaceListView.getItems().clear();
            for (GreenSpace gs : managedGreenSpaces) {
                greenSpaceListView.getItems().add(gs.toString());
            }
        });

        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(fetchButton, 1, 1);
        grid.add(greenSpaceListView, 0, 2, 2, 1);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setTitle("Green Space Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
