package pt.ipp.isep.dei.esoft.project.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.ui.gui.menu.MainMenuGUI;

public class MainGUI extends Application {
    private static Bootstrap bootstrap;

    public static void main(String[] args) {
        bootstrap = new Bootstrap();
        bootstrap.run();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainMenuGUI menu = new MainMenuGUI();
        primaryStage.setOnCloseRequest(e -> {
            bootstrap.saveData();
        });
        menu.start(primaryStage);
    }
}