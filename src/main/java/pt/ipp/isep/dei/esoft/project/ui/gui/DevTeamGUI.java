package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DevTeamGUI {

    public GridPane getDevTeamGUI() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        grid.setAlignment(javafx.geometry.Pos.CENTER);

        Label lblTeam = new Label("------------- DEVELOPMENT TEAM -------------\n" +
                "  Ricardo Morim - 1XXXXXX@isep.ipp.pt\n" +
                "  Gonçalo Fernandes - 1231170@isep.ipp.pt\n" +
                "  Marisa Afonso - 1231151@isep.ipp.pt\n" +
                "  Ana Filipa Alves - 1230929@isep.ipp.pt\n" +
                "  Afonso Marques - 1221018@isep.ipp.pt\n");


        grid.add(lblTeam, 0, 0);

        return grid;
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Development Team");

        GridPane grid = getDevTeamGUI();

        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}