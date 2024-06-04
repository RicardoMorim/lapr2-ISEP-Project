package pt.ipp.isep.dei.esoft.project.ui.gui.menu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.ui.gui.DevTeamGUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.authentication.LoginUI;

public class MainMenuGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Musgo Sublime - Main Menu");

        Button btnLogin = new Button("Do Login");
        btnLogin.setMinWidth(200); // Set the width of the button
        btnLogin.setMinHeight(50); // Set the height of the button
        btnLogin.setOnAction(e -> {
            LoginUI authUI = new LoginUI();
            authUI.start(primaryStage);
        });

        Button btnKnowTheDevTeam = new Button("Know the Development Team");
        btnKnowTheDevTeam.setMinWidth(200); // Set the width of the button
        btnKnowTheDevTeam.setMinHeight(50); // Set the height of the button
        btnKnowTheDevTeam.setOnAction(e -> {
            DevTeamGUI devTeamUI = new DevTeamGUI();
            devTeamUI.start(primaryStage);
        });

        VBox vbox = new VBox(btnLogin, btnKnowTheDevTeam);
        vbox.setAlignment(Pos.CENTER); // Center the VBox
        vbox.setSpacing(10); // Add some spacing between the buttons

        Scene scene = new Scene(vbox, 800, 600); // Set the size of the window

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}