package pt.ipp.isep.dei.esoft.project.ui.gui.authentication;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.gui.menu.AdminMenuGUI;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

public class LoginUI {

    private AuthenticationController authController = new AuthenticationController();

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label lblUser = new Label("User ID/Email:");
        TextField txtUser = new TextField();
        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();
        Button btnLogin = new Button("Login");
        Label lblError = new Label();

        grid.add(lblUser, 0, 0);
        grid.add(txtUser, 1, 0);
        grid.add(lblPassword, 0, 1);
        grid.add(txtPassword, 1, 1);
        grid.add(btnLogin, 1, 2);
        grid.add(lblError, 1, 3);

        btnLogin.setOnAction(e -> {
            String id = txtUser.getText();
            String pwd = txtPassword.getText();
            boolean success = authController.doLogin(id, pwd);
            if (success) {
                List<UserRoleDTO> roles = authController.getUserRoles();
                if (roles != null && !roles.isEmpty()) {
                    for (UserRoleDTO role : roles) {
                        if (role.getDescription().equals(AuthenticationController.ROLE_ADMIN)) {
                            AdminMenuGUI adminMenuGUI = new AdminMenuGUI();
                            adminMenuGUI.start(new Stage());
                            primaryStage.close();
                            break;
                        }
                        // TODO - Add more roles here as needed
                    }
                } else {
                    lblError.setText("No role assigned to user.");
                }
            } else {
                lblError.setText("Invalid UserId and/or Password.");
            }
        });

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}