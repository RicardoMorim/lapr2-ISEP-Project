package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DevTeamGUI {

    public GridPane getDevTeamGUI() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.setVgap(30);
        grid.setHgap(30);

        grid.setAlignment(javafx.geometry.Pos.CENTER);

        String[] teamMembers = {
                "Ricardo Morim\n1230481@isep.ipp.pt",
                "Gonçalo Fernandes\n1231170@isep.ipp.pt",
                "Marisa Afonso\n1231151@isep.ipp.pt",
                "Ana Filipa Alves\n1230929@isep.ipp.pt",
                // "Afonso Marques\n1221018@isep.ipp.pt"
        };

        String[] imagePaths = {
                "ricardo.jpg",
                "goncalo.jpeg",
                "marisa.jpeg",
                "filipa.jpg",
                // "afonso.jpg"
        };

        for (int i = 0; i < teamMembers.length; i++) {
            ImageView imageView = new ImageView(new Image(imagePaths[i]));
            imageView.setFitWidth(150);
            imageView.setPreserveRatio(true);

            // Create a Circle object with radius equal to half of the desired width
            Circle clip = new Circle(imageView.getFitWidth() / 2);

            // Set the center of the Circle to be the center of the ImageView
            clip.setCenterX(75); // Half of the fit width
            clip.setCenterY(75); // Half of the fit height

            // Clip the ImageView to the Circle
            imageView.setClip(clip);

            Label label = new Label(teamMembers[i]);

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), imageView);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            grid.add(imageView, i, 0);
            grid.add(label, i, 1);

            // Add a margin to the ImageView
            GridPane.setMargin(imageView, new Insets(0, 10, 0, 0)); // top, right, bottom, left
        }

        return grid;
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Development Team");

        GridPane grid = getDevTeamGUI();

        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
