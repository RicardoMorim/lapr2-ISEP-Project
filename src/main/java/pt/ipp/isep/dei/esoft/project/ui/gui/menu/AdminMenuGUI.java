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

public class AdminMenuGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Musgo Sublime - Admin Menu");

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

        Button btnRegisterVehicle = new Button("Register Vehicle");
        //btnRegisterVehicle.setOnAction(e -> new RegisterVehicleUI().start(new Stage()));

        Button btnRegisterVehicleMaintenance = new Button("Register Vehicle maintenance");
        //btnRegisterVehicleMaintenance.setOnAction(e -> new RegisterVehicleMaintenanceUI().start(new Stage()));

        Button btnListVehiclesNeedingMaintenance = new Button("Get a list of vehicles needing maintenance");
        //btnListVehiclesNeedingMaintenance.setOnAction(e -> new ListVehiclesNeedingMaintenanceUI().start(new Stage()));

        Button btnGetBestPipeRoutes = new Button("Get best water pipe routes.");
        //btnGetBestPipeRoutes.setOnAction(e -> new GetBestPipeRoutesUI().start(new Stage()));

        Button btnAddEntryToAgenda = new Button("Add an entry to the Agenda");
        //btnAddEntryToAgenda.setOnAction(e -> new AddEntryToAgendaUI().start(new Stage()));

        Button btnAddGreenSpace = new Button("Add a Green Space");
        btnAddGreenSpace.setOnAction(e -> new AddGreenSpaceGUI().start(primaryStage));

        Button btnAssignTeamToAgendaEntry = new Button("Assign a Team to an Agenda Entry");
        btnAssignTeamToAgendaEntry.setOnAction(e -> new AssignTeamToAgendaEntryGUI().start(primaryStage));

        Button btnAddVehicleToAgendaEntry = new Button("Add a vehicle to an Agenda Entry");
        btnAddVehicleToAgendaEntry.setOnAction(e -> new AddVehicleToAgendaEntryGUI().start(primaryStage));

        Button btnPostponeEntry = new Button("Postpone an Entry to the Agenda");
        btnPostponeEntry.setOnAction(e -> new PostponeEntryGUI().start(primaryStage));

        Button btnCancel = new Button("Cancel an entry in the Agenda");
        btnCancel.setOnAction(e -> new CancelGUI().start(primaryStage));

        Button btnAddEntryToDoList = new Button("Add an entry to the ToDo List");
        btnAddEntryToDoList.setOnAction(e -> new ToDoListGUI().start(primaryStage));

        Button btnListGreenSpaces = new Button("List Green Spaces");
        btnListGreenSpaces.setOnAction(e -> new GreenSpaceManagerGUI().start(primaryStage));

        VBox vbox = new VBox(btnRegisterSkill, btnRegisterJob, btnRegisterCollaborator, btnAddSkillToCollaborator,
                btnGenerateTeamProposal, btnRegisterVehicle, btnRegisterVehicleMaintenance, btnListVehiclesNeedingMaintenance,
                btnGetBestPipeRoutes, btnAddEntryToAgenda, btnAddGreenSpace, btnAssignTeamToAgendaEntry, btnPostponeEntry, btnCancel, btnAddEntryToDoList, btnListGreenSpaces);
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