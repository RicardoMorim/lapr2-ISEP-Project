package pt.ipp.isep.dei.esoft.project.ui.gui.authentication;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;

public class LoginGUI {

    private AuthenticationController authController = new AuthenticationController();


    private Runnable onLoginSuccess;

    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    private void animateTextField(TextField textField, Label label, double initialScale, double finalScale) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), label);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), label);

        label.setScaleX(initialScale);
        label.setScaleY(initialScale);

        // Listen for changes in the text property of the TextField
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                // If the user starts to write, animate the label to the top left corner and make it smaller
                translateTransition.setToX(-textField.getWidth() / 2 + label.getWidth() / 2);
                translateTransition.setToY(-textField.getHeight() / 2 + label.getHeight() / 2);
                scaleTransition.setToX(finalScale);
                scaleTransition.setToY(finalScale);
                translateTransition.play();
                scaleTransition.play();
            } else {
                // If the TextField is empty, animate the label back to the center and make it bigger
                translateTransition.setToX(0);
                translateTransition.setToY(0);
                scaleTransition.setToX(initialScale);
                scaleTransition.setToY(initialScale);
                translateTransition.play();
                scaleTransition.play();
            }
        });
    }

    public GridPane getLoginForm(double height, double width) {
        GridPane grid = new GridPane(height, width);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        // Add an ImageView to the GridPane
        ImageView imageView = new ImageView(new Image("musgo.jpeg"));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        // Set the blur effect
        BoxBlur blur = new BoxBlur();
        blur.setWidth(5);
        blur.setHeight(5);
        blur.setIterations(3);
        imageView.setEffect(blur);

        // Set the opacity
        imageView.setOpacity(0.7);


        // Adjust the padding
        grid.setPadding(new Insets(0, 0, 0, 0)); // Remove the padding


        Label lblUser = new Label("User ID/Email:");
        TextField txtUser = new TextField();
        txtUser.setPrefWidth(300);
        txtUser.setPrefHeight(50);
        txtUser.setStyle("-fx-border-color: transparent transparent black transparent; -fx-font-size: 30;"); // Increase the font size

        lblUser.setStyle("-fx-font-size: 15; -fx-font-weight: bold");

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(txtUser, lblUser);
        grid.add(stackPane, 1, 0);

        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPrefWidth(300); // Set the width of the PasswordField
        txtPassword.setPrefHeight(50); // Set the height of the PasswordField
        txtPassword.setStyle("-fx-border-color: transparent transparent black transparent; -fx-font-size: 30; -fx-background-color: transparent");

        lblPassword.setStyle("-fx-font-size: 15; -fx-font-weight: bold");

        Label lblError = new Label();

        Button btnLogin = new Button("Login");
        btnLogin.setPrefWidth(200); // Set the width of the Button
        btnLogin.setPrefHeight(50); // Set the height of the Button
        btnLogin.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 30;");

        txtUser.setStyle("-fx-border-color: transparent transparent black transparent; -fx-font-size: 20; -fx-background-color: transparent;");

        // Create a StackPane to place the label inside the PasswordField
        StackPane stackPanePassword = new StackPane();
        stackPanePassword.getChildren().addAll(txtPassword, lblPassword);
        grid.add(stackPanePassword, 1, 1);

        grid.add(btnLogin, 1, 2);
        GridPane.setHalignment(btnLogin, HPos.CENTER); // Center the button

        grid.add(lblError, 1, 3);

        animateTextField(txtUser, lblUser, 1, 0.8);
        animateTextField(txtPassword, lblPassword, 1, 0.8);

        StackPane newStackPane = new StackPane();
        newStackPane.getChildren().addAll(imageView, grid);

        // Create a new GridPane to hold the StackPane
        GridPane outerGrid = new GridPane();
        outerGrid.getChildren().add(newStackPane);

        btnLogin.setOnAction(e -> {
            String id = txtUser.getText();
            String pwd = txtPassword.getText();
            boolean success = authController.doLogin(id, pwd);
            if (success) {
                if (onLoginSuccess != null) {
                    onLoginSuccess.run();
                }
//                List<UserRoleDTO> roles = authController.getUserRoles();
//                if (roles != null && !roles.isEmpty()) {
//                    for (UserRoleDTO role : roles) {
//                        if (role.getDescription().equals(AuthenticationController.ROLE_ADMIN)) {
//                            AdminMenuGUI adminMenuGUI = new AdminMenuGUI();
//                            try {
//                                adminMenuGUI.start(new Stage());
//                            } catch (Exception ex) {
//
//                            }
//                            break;
//                        }
//                        if (role.getDescription().equals(AuthenticationController.ROLE_HRM)) {
//                            // CollaboratorMenuGUI collaboratorMenuGUI = new CollaboratorMenuGUI();
//                            // collaboratorMenuGUI.start(new Stage());
//                            // primaryStage.close();
//                            break;
//                        }
//
//                        if (role.getDescription().equals(AuthenticationController.ROLE_GRM)) {
//                            // FreelancerMenuGUI freelancerMenuGUI = new FreelancerMenuGUI();
//                            // freelancerMenuGUI.start(new Stage());
//                            // primaryStage.close();
//                            break;
//                        }
//                        if (role.getDescription().equals(AuthenticationController.ROLE_VFM)){
//                            // VehicleMenuGUI vehicleMenuGUI = new VehicleMenuGUI();
//                            // vehicleMenuGUI.start(new Stage());
//                            // primaryStage.close();
//                            break;
//                        }
//                        if (role.getDescription().equals(AuthenticationController.ROLE_EMPLOYEE)){
//                            // EmployeeMenuGUI employeeMenuGUI = new EmployeeMenuGUI();
//                            // employeeMenuGUI.start(new Stage());
//                            // primaryStage.close();
//                            break;
//
//                        }
//                    }
//                } else {
//                    lblError.setText("No role assigned to user.");
//                }
            } else {
                lblError.setText("Invalid UserId and/or Password.");
            }
        });
        return outerGrid;
    }
}