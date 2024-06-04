package pt.ipp.isep.dei.esoft.project.ui.gui.menu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.ui.gui.*;

public class MainHRMMenuGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Musgo Sublime - HRM Menu");

        Button btnRegisterSkill = new Button("Register a skill that a collaborator may have.");
        //btnRegisterSkill.setOnAction(e -> new RegisterSkillUI().start(new Stage()));

        Button btnRegisterJob = new Button("Register a job that a collaborator may have.");
        //btnRegisterJob.setOnAction(e -> new RegisterJobUI().start(new Stage()));

        Button btnRegisterCollaborator = new Button("Register a collaborator");
        //btnRegisterCollaborator.setOnAction(e -> new RegisterCollaboratorUI().start(new Stage()));

        Button btnAddSkillToCollaborator = new Button("Add a skill to a collaborator");
        btnAddSkillToCollaborator.setOnAction(e -> new AddSkillToCollaboratorGUI().start(primaryStage));

        Button btnGenerateTeamProposal = new Button("Generate a team proposal automatically");
        btnGenerateTeamProposal.setOnAction(e -> new GenerateTeamProposalGUI().start(primaryStage));


        VBox vbox = new VBox(btnRegisterSkill, btnRegisterJob, btnRegisterCollaborator, btnAddSkillToCollaborator,
                btnGenerateTeamProposal);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vbox);


        // Create an ImageView and add it to the right side of the BorderPane
        Image image = new Image("file:src/main/resources/your_image.png"); // TODO change this path
        ImageView imageView = new ImageView(image);
        borderPane.setRight(imageView);

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}