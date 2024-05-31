package pt.ipp.isep.dei.esoft.project.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.ui.gui.menu.MainMenuGUI;

public class MainGUI extends Application {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainMenuGUI menu = new MainMenuGUI();
        menu.start(primaryStage);
    }
}