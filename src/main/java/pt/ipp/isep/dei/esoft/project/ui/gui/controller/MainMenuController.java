package pt.ipp.isep.dei.esoft.project.ui.gui.controller;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.gui.DevTeamGUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.authentication.LoginGUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.menu.*;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

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
            content.setPrefWidth(1600 - menu.getWidth());
            content.setPrefHeight(900);
            content.getChildren().forEach(node -> {
                if (node instanceof Pane) {
                    ((Pane) node).setPrefWidth(content.getPrefWidth());
                    ((Pane) node).setPrefHeight(content.getPrefHeight());
                }
            });

            applyButtonAnimations();
        });

        // Add a ChangeListener to the height property of the VBox
        menu.heightProperty().addListener((observable, oldValue, newValue) -> {
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
        });
    }

    @FXML
    private void showHRMMenu() {
        MainHRMMenuGUI mainHRMMenuGUI = new MainHRMMenuGUI(content);
        menu.getChildren().setAll(mainHRMMenuGUI.getGridPane().getChildren());
        content.getChildren().clear();
    }

    @FXML
    private void showGRMMenu() {
        MainGRMMenu mainGRMMenu = new MainGRMMenu(content);
        menu.getChildren().setAll(mainGRMMenu.getGridPane().getChildren());
        content.getChildren().clear();
    }

    @FXML
    private void showCollaboratorMenu() {
        MainCollaboratorMenu mainCollaboratorMenu = new MainCollaboratorMenu(content);
        menu.getChildren().setAll(mainCollaboratorMenu.getGridPane().getChildren());
        content.getChildren().clear();
    }


    @FXML
    private void showAdminMenu() {
        AdminMenuGUI adminMenuGUI = new AdminMenuGUI(content);
        menu.getChildren().setAll(adminMenuGUI.getAdminMenuGUI().getChildren());
        content.getChildren().clear();
    }

    @FXML
    private void showVFMMenu() {
        MainVFMMenuGUI vfmMenuGUI = new MainVFMMenuGUI(content);
        menu.getChildren().setAll(vfmMenuGUI.getVFMMenuGUI().getChildren());
        content.getChildren().clear();
    }

    @FXML
    private void showLogin() {
        Platform.runLater(() -> {
            // Create and show the LoginGUI
            LoginGUI loginGUI = new LoginGUI();
            AuthenticationController authController = new AuthenticationController();
            // Set the callback function
            loginGUI.setOnLoginSuccess(() -> {
                // Update the buttons on the left
                List<UserRoleDTO> roles = authController.getUserRoles();

                if (roles != null && !roles.isEmpty()) {
                    for (UserRoleDTO role : roles) {
                        if (role.getDescription().equals(AuthenticationController.ROLE_HRM)) {
                            showHRMMenu();
                        } else if (role.getDescription().equals(AuthenticationController.ROLE_ADMIN)) {
                            showAdminMenu();
                        } else if (role.getDescription().equals(AuthenticationController.ROLE_VFM)) {
                            showVFMMenu();
                        } else if (role.getDescription().equals(AuthenticationController.ROLE_GRM)) {
                            showGRMMenu();
                        } else if (role.getDescription().equals(AuthenticationController.ROLE_EMPLOYEE)) {
                            showCollaboratorMenu();
                        }
                    }
                }

            });


            // Get the login form and set it as the content of the Pane
            content.getChildren().setAll(loginGUI.getLoginForm(content.getHeight(), content.getWidth()));
        });
    }


    private void applyButtonAnimations() {
        for (Node node : content.getChildren()) {
            if (node instanceof Button && node.getStyleClass().contains("add-button")) {
                Button button = (Button) node;

                ScaleTransition st = new ScaleTransition(Duration.millis(300), button);
                st.setFromX(1);
                st.setFromY(1);
                st.setToX(1.1);
                st.setToY(1.1);
                st.setAutoReverse(true);

                button.setOnMouseEntered(event -> st.playFromStart());
                button.setOnMouseExited(event -> st.stop());
            }
        }
    }

    @FXML
    private void showDevTeam() {
        // Create and show the DevTeamGUI
        DevTeamGUI devTeamGUI = new DevTeamGUI();

        content.getChildren().setAll(devTeamGUI.getDevTeamGUI());
    }
}