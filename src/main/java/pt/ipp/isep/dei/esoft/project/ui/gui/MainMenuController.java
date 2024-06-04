package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pt.ipp.isep.dei.esoft.project.ui.gui.authentication.LoginGUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.menu.AdminMenuGUI;

public class MainMenuController {

    public VBox menu;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnShowDevTeam;
    @FXML
    private Pane content;

    @FXML
    public void initialize() {
        // Set the default selected button
        btnLogin.fire();

        // Remove the margin between the buttons
        menu.setSpacing(0);

        menu.getChildren().addListener((ListChangeListener<Node>) c -> {
            // Get the number of buttons in the VBox
            int numButtons = menu.getChildren().size();

            // Calculate the height for each button
            double buttonHeight = menu.getHeight() / numButtons;

            // Set the height of each button
            for (Node node : menu.getChildren()) {
                if (node instanceof Button) {
                    ((Button) node).setPrefHeight(buttonHeight);
                }
            }
        });

        content.getChildren().addListener((ListChangeListener<Node>) c -> {
            content.setPrefWidth(1280 - menu.getWidth());
            content.setPrefHeight(720);
            content.getChildren().forEach(node -> {
                if (node instanceof Pane) {
                    ((Pane) node).setPrefWidth(content.getPrefWidth());
                    ((Pane) node).setPrefHeight(content.getPrefHeight());
                }
            });
        });

        // Add a ChangeListener to the height property of the VBox
        menu.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Get the number of buttons in the VBox
                int numButtons = menu.getChildren().size();

                // Calculate the height for each button
                double buttonHeight = newValue.doubleValue() / numButtons;

                // Set the height of each button
                for (Node node : menu.getChildren()) {
                    if (node instanceof Button) {
                        ((Button) node).setPrefHeight(buttonHeight);
                    }
                }
            }
        });
    }


    @FXML
    private void showAdminMenu() {
        AdminMenuGUI adminMenuGUI = new AdminMenuGUI(content);
        menu.getChildren().setAll(adminMenuGUI.getAdminMenuGUI().getChildren());
        content.getChildren().clear();
    }

    @FXML
    private void showLogin() {
        // Delay the execution of the showLogin method
        Platform.runLater(() -> {
            // Create and show the LoginGUI
            LoginGUI loginGUI = new LoginGUI();

            // Set the callback function
            loginGUI.setOnLoginSuccess(() -> {
                // Update the buttons on the left
                showAdminMenu();
            });

            // Get the login form and set it as the content of the Pane
            content.getChildren().setAll(loginGUI.getLoginForm(content.getHeight(), content.getWidth()));
        });
    }

    @FXML
    private void showDevTeam() {
        // Create and show the DevTeamGUI
        DevTeamGUI devTeamGUI = new DevTeamGUI();

        content.getChildren().setAll(devTeamGUI.getDevTeamGUI());
    }
}