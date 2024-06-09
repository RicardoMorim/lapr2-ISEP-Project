package pt.ipp.isep.dei.esoft.project.ui.gui.authentication;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;

/**
 * The type Login gui.
 */
public class LoginGUI {

    private AuthenticationController authController = new AuthenticationController();

    private String userRole;
    private Runnable onLoginSuccess;

    /**
     * Sets on login success.
     *
     * @param onLoginSuccess the on login success
     */
    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    /**
     * Get user role string.
     *
     * @return the string
     */
    public String getUserRole(){
        return userRole;
    }

    private void animateTextField(TextField textField, Label label, double initialScale, double finalScale) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), label);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), label);

        label.setScaleX(initialScale);
        label.setScaleY(initialScale);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                translateTransition.setToX(-textField.getWidth() / 2 + label.getWidth() / 2);
                translateTransition.setToY(-textField.getHeight() / 2 + label.getHeight() / 2);
                scaleTransition.setToX(finalScale);
                scaleTransition.setToY(finalScale);
                translateTransition.play();
                scaleTransition.play();
            } else {
                translateTransition.setToX(0);
                translateTransition.setToY(0);
                scaleTransition.setToX(initialScale);
                scaleTransition.setToY(initialScale);
                translateTransition.play();
                scaleTransition.play();
            }
        });
    }

    /**
     * Gets login form.
     *
     * @param height the height
     * @param width  the width
     * @return the login form
     */
    public GridPane getLoginForm(double height, double width) {
        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(10, 10, 10, 10));
        formGrid.setVgap(5);
        formGrid.setHgap(5);
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setStyle("-fx-background-color: white; -fx-background-radius: 20 20 0 0; -fx-max-height: 400; -fx-max-width: 400; -fx-border-radius: 20;");
        formGrid.setPrefHeight(350);

        // Load the avatar image
        Image avatarImage = new Image("avatar.png");
        ImageView avatarImageView = new ImageView(avatarImage);

        // Set the size of the avatar image
        avatarImageView.setFitWidth(100);
        avatarImageView.setFitHeight(100);

        // Center the avatar image horizontally
        avatarImageView.setPreserveRatio(true);
        GridPane.setHalignment(avatarImageView, HPos.CENTER);

        GridPane.setValignment(avatarImageView, VPos.CENTER);

        Label lblUser = new Label("User ID/Email:");
        TextField txtUser = new TextField();
        txtUser.setPrefWidth(300);
        txtUser.setPrefHeight(50);
        txtUser.setStyle("-fx-border-color: transparent transparent black transparent; -fx-font-size: 30; -fx-background-color: transparent");

        lblUser.setStyle("-fx-font-size: 15; -fx-font-weight: bold");

        StackPane stackPaneUser = new StackPane();
        stackPaneUser.getChildren().addAll(txtUser, lblUser);

        formGrid.add(avatarImageView, 1, 0);


        formGrid.add(stackPaneUser, 1, 2);



        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPrefWidth(300);
        txtPassword.setPrefHeight(50);
        txtPassword.setStyle("-fx-border-color: transparent transparent black transparent; -fx-font-size: 30; -fx-background-color: transparent");

        lblPassword.setStyle("-fx-font-size: 15; -fx-font-weight: bold");

        Label lblError = new Label();

        StackPane stackPanePassword = new StackPane();
        stackPanePassword.getChildren().addAll(txtPassword, lblPassword);
        formGrid.add(stackPanePassword, 1, 3);

        formGrid.add(lblError, 1, 3);

        animateTextField(txtUser, lblUser, 1, 0.8);
        animateTextField(txtPassword, lblPassword, 1, 0.8);

        Button btnLogin = new Button("Login");
        btnLogin.setPrefWidth(400);
        btnLogin.setPrefHeight(50);
        btnLogin.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 30; -fx-background-radius: 0 0 20 20;");

        formGrid.setId("formGrid");
        btnLogin.setId("btnLogin");

        btnLogin.setOnAction(e -> {
            String id = txtUser.getText();
            String pwd = txtPassword.getText();
            boolean success = authController.doLogin(id, pwd);
            if (success) {
                if (onLoginSuccess != null) {
                    onLoginSuccess.run();
                }
            } else {
                lblError.setText("Invalid UserId and/or Password.");
            }
        });

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(formGrid, btnLogin);

        StackPane mainPane = new StackPane();

        ImageView imageView = new ImageView(new Image("musgo.jpeg"));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        BoxBlur blur = new BoxBlur();
        blur.setWidth(5);
        blur.setHeight(5);
        blur.setIterations(3);
        imageView.setEffect(blur);
        imageView.setOpacity(0.7);

        mainPane.getChildren().addAll(imageView, vbox);

        GridPane outerGrid = new GridPane();
        outerGrid.setAlignment(Pos.CENTER);
        outerGrid.getChildren().add(mainPane);

        return outerGrid;
    }
}
