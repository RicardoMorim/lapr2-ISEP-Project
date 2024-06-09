package pt.ipp.isep.dei.esoft.project.ui.gui.menu;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pt.ipp.isep.dei.esoft.project.ui.gui.*;

public class AdminMenuGUI {


    private Pane content;

    public AdminMenuGUI(Pane content) {
        this.content = content;
    }


    public GridPane getAdminMenuGUI() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Button btnAddEntryToAgenda = new Button("Add an entry to the Agenda");
        btnAddEntryToAgenda.setOnAction(e -> {
            AddEntryToAgendaGUI addGreenSpaceGUI = new AddEntryToAgendaGUI();
            content.getChildren().setAll(addGreenSpaceGUI.getGridPane(content.getHeight(), content.getWidth()));
        });

        Button btnAddGreenSpace = new Button("Add a Green Space");

        btnAddGreenSpace.setOnAction(e -> {
            AddGreenSpaceGUI addGreenSpaceGUI = new AddGreenSpaceGUI();
            content.getChildren().setAll(addGreenSpaceGUI.getGridPane());
        });

        Button btnAssignTeamToAgendaEntry = new Button("Assign a Team to an Agenda Entry");
        btnAssignTeamToAgendaEntry.setOnAction(e -> {
            AssignTeamToAgendaEntryGUI assignTeamToAgendaEntryGUI = new AssignTeamToAgendaEntryGUI();
            content.getChildren().setAll(assignTeamToAgendaEntryGUI.getAssignTeamToAgendaEntryGridPane(content.getHeight(), content.getWidth()));
        });
        Button btnPostponeEntry = new Button("Postpone an Entry to the Agenda");
        btnPostponeEntry.setOnAction(e -> {
            PostponeEntryGUI postponeEntryGUI = new PostponeEntryGUI();
            content.getChildren().setAll(postponeEntryGUI.getPostponeEntryGridPane(content.getHeight(), content.getWidth()));
        });

        Button btnCancel = new Button("Cancel an entry in the Agenda");
        btnCancel.setOnAction(e -> {
            CancelGUI cancelGUI = new CancelGUI();
            content.getChildren().setAll(cancelGUI.getCancelEntryGridPane(content.getHeight(), content.getWidth()));
        });

        Button btnAddEntryToDoList = new Button("Add an entry to the ToDo List");
        btnAddEntryToDoList.setOnAction(e -> {
            AddEntryToDoListGUI addEntryToDoListGUI = new AddEntryToDoListGUI();
            content.getChildren().setAll(addEntryToDoListGUI.getAddEntryToToDoList(content.getHeight(), content.getWidth()));
        });

        Button btnListGreenSpaces = new Button("List Green Spaces");
        btnListGreenSpaces.setOnAction(e -> {
            ListGreenSpacesGUI listGreenSpacesGUI = new ListGreenSpacesGUI();
            content.getChildren().setAll(listGreenSpacesGUI.getListGreenSpacesGridPane(content.getHeight(), content.getWidth()));
        });

        Button btnRegisterSkill = new Button("Register a skill that a collaborator may have.");
        btnRegisterSkill.setOnAction(e -> content.getChildren().setAll(new RegisterSkillGUI().getRegisterSkillGUI()));

        Button btnRegisterJob = new Button("Register a job that a collaborator may have.");
        btnRegisterJob.setOnAction(e -> content.getChildren().setAll(new RegisterJobGUI().getRegisterJobGUI()));

        Button btnRegisterCollaborator = new Button("Register a collaborator");
        btnRegisterCollaborator.setOnAction(e -> content.getChildren().setAll(new RegisterCollaboratorGUI().getRegisterCollaboratorGUI(content.getHeight(), content.getWidth())));

        Button btnAddSkillToCollaborator = new Button("Add a skill to a collaborator");
        btnAddSkillToCollaborator.setOnAction(e -> content.getChildren().setAll(new AddSkillToCollaboratorGUI().getAddSkillToCollaboratorGUI(content.getHeight(), content.getWidth())));

        Button btnGenerateTeamProposal = new Button("Generate a team proposal automatically");
        btnGenerateTeamProposal.setOnAction(e -> content.getChildren().setAll(new GenerateTeamProposalGUI().getGenerateTeamProposalGUI(content.getHeight(), content.getWidth())));

        Button btnRegisterVehicle = new Button("Register Vehicle");
        btnRegisterVehicle.setOnAction(e -> {
            RegisterVehicleGUI registerVehicleGUI = new RegisterVehicleGUI();
            content.getChildren().setAll(registerVehicleGUI.getRegisterVehicleGUI(content.getHeight(), content.getWidth()));
        });
        Button btnRegisterVehicleMaintenance = new Button("Register Vehicle maintenance");
        btnRegisterVehicleMaintenance.setOnAction(e -> {
            RegisterVehicleMaintenanceGUI registerVehicleMaintenanceGUI = new RegisterVehicleMaintenanceGUI();
            content.getChildren().setAll(registerVehicleMaintenanceGUI.getRegisterVehicleMaintenanceGUI());
        });

        Button btnAddVehicleToAgendaEntry = new Button("Add a vehicle to an Agenda Entry");
        btnAddVehicleToAgendaEntry.setOnAction(e -> {
            AddVehicleToAgendaEntryGUI addVehicleToAgendaEntryGUI = new AddVehicleToAgendaEntryGUI();
            content.getChildren().setAll(addVehicleToAgendaEntryGUI.getAddVehicleToAgendaEntryGUIGridPane(content.getHeight(), content.getWidth()));
        });

        grid.add(btnRegisterSkill, 0, 0);
        grid.add(btnRegisterJob, 1, 0);
        grid.add(btnRegisterCollaborator, 0, 1);
        grid.add(btnGenerateTeamProposal, 0, 2);
        grid.add(btnRegisterVehicle, 1, 2);
        grid.add(btnRegisterVehicleMaintenance, 0, 3);
        grid.add(btnAddGreenSpace, 1, 3);
        grid.add(btnAddEntryToDoList, 0, 4);
        grid.add(btnAddEntryToAgenda, 1, 4);
        grid.add(btnAssignTeamToAgendaEntry, 0, 5);
        grid.add(btnAddVehicleToAgendaEntry, 1,5);
        grid.add(btnPostponeEntry, 0, 6);
        grid.add(btnCancel, 1, 6);
        grid.add(btnListGreenSpaces, 1, 7);

        applyButtonAnimations();
        btnRegisterSkill.fire();
        return grid;
    }


    private void applyButtonAnimations() {
        applyButtonAnimationsToNode(content);
    }

    private void applyButtonAnimationsToNode(Node node) {
        if (node instanceof Button && node.getStyleClass().contains("add-button")) {
            Button button = (Button) node;

            ScaleTransition st = new ScaleTransition(Duration.millis(300), button);
            st.setFromX(1);
            st.setFromY(1);
            st.setToX(1.1);
            st.setToY(1.1);
            st.setAutoReverse(true);

            button.setOnMouseEntered(event -> st.playFromStart());
            button.setOnMouseExited(event -> st.stop());
        } else if (node instanceof Parent) {
            Parent parent = (Parent) node;
            for (Node child : parent.getChildrenUnmodifiable()) {
                applyButtonAnimationsToNode(child);
            }
        }
    }


}