package pt.ipp.isep.dei.esoft.project.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class MainGUI extends Application {
    private static Bootstrap bootstrap;

    public static void main(String[] args) {
        bootstrap = new Bootstrap();
        bootstrap.run();
        launch(args);
    }

//    @Override
//    public void start(Stage primaryStage) {
//        MainMenuGUI menu = new MainMenuGUI();
//        primaryStage.setOnCloseRequest(e -> {
//            bootstrap.saveData();
//        });
//        menu.start(primaryStage);
//    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        File fxmlFile = new File("src\\main\\java\\pt\\ipp\\isep\\dei\\esoft\\project\\ui\\gui\\fxml\\MainMenuFXML.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
        Parent root = loader.load();

        primaryStage.setTitle("Musgo Sublime");

        // Set the icon of the window
        primaryStage.getIcons().add(new Image("logo.jpeg"));


        primaryStage.setOnCloseRequest(e -> {
            bootstrap.saveData();
        });

        Scene scene = new Scene(root, 1600, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}