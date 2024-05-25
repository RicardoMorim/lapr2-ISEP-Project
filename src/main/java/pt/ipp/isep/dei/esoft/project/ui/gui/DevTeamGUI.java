package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DevTeamGUI {

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Development Team");

        Label lblTeam = new Label();
        lblTeam.setText("------------- DEVELOPMENT TEAM -------------\n" +
                "  Ricardo Morim - 1XXXXXX@isep.ipp.pt\n" +
                "  Gonçalo Fernandes - 1231170@isep.ipp.pt\n" +
                "  Marisa Afonso - 1231151@isep.ipp.pt\n" +
                "  Ana Filipa Alves - 1230929@isep.ipp.pt\n" +
                "  Afonso Marques - 1221018@isep.ipp.pt\n");

        lblTeam.setWrapText(true); // Wrap the text in the label
        lblTeam.setAlignment(Pos.CENTER); // Center the text in the label

        VBox vbox = new VBox(lblTeam);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER); // Center the VBox

        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}