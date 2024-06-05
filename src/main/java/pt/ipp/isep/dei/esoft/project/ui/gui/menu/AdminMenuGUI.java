package pt.ipp.isep.dei.esoft.project.ui.gui.menu;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pt.ipp.isep.dei.esoft.project.ui.gui.AddGreenSpaceGUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.AddSkillToCollaboratorGUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.AssignTeamToAgendaEntryGUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.GenerateTeamProposalGUI;

public class AdminMenuGUI  {


    private Pane content;

    public AdminMenuGUI(Pane content) {
        this.content = content;
    }


    public GridPane getAdminMenuGUI() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Button btnRegisterSkill = new Button("Register a skill that a collaborator may have.");
        btnRegisterSkill.setOnAction(e -> {
            AddSkillToCollaboratorGUI addSkillToCollaboratorGUI = new AddSkillToCollaboratorGUI();
            content.getChildren().setAll(addSkillToCollaboratorGUI.getAddSkillToCollaboratorGUI(content.getHeight(), content.getWidth()));
        });

        Button btnRegisterJob = new Button("Register a job that a collaborator may have.");
        // TODO - Add GUI for the button (last sprint US)

        Button btnRegisterCollaborator = new Button("Register a collaborator");
        // TODO - Add GUI for the button (last sprint US)

        Button btnGenerateTeamProposal = new Button("Generate a team proposal automatically");
        btnGenerateTeamProposal.setOnAction(e -> {
            GenerateTeamProposalGUI generateTeamProposalGUI = new GenerateTeamProposalGUI();
            content.getChildren().setAll(generateTeamProposalGUI.getGenerateTeamProposalGUI(content.getHeight(), content.getWidth()));
        });

        Button btnRegisterVehicle = new Button("Register Vehicle");
        // TODO - Add GUI for the button (last sprint US)
        Button btnRegisterVehicleMaintenance = new Button("Register Vehicle maintenance");
        // TODO - Add GUI for the button (last sprint US)
        Button btnListVehiclesNeedingMaintenance = new Button("Get a list of vehicles needing maintenance");
        // TODO - Add GUI for the button - Not priority (last sprint US)
        Button btnAddEntryToAgenda = new Button("Add an entry to the Agenda");
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
        Button btnCancel = new Button("Cancel an entry in the Agenda");
        Button btnAddEntryToDoList = new Button("Add an entry to the ToDo List");
        Button btnListGreenSpaces = new Button("List Green Spaces");

        grid.add(btnRegisterSkill, 0, 0);
        grid.add(btnRegisterJob, 1, 0);
        grid.add(btnRegisterCollaborator, 0, 1);
        grid.add(btnGenerateTeamProposal, 0, 2);
        grid.add(btnRegisterVehicle, 1, 2);
        grid.add(btnRegisterVehicleMaintenance, 0, 3);
        grid.add(btnListVehiclesNeedingMaintenance, 1, 3);
        grid.add(btnAddEntryToAgenda, 0, 4);
        grid.add(btnAddGreenSpace, 1, 4);
        grid.add(btnAssignTeamToAgendaEntry, 0, 5);
        grid.add(btnPostponeEntry, 1, 5);
        grid.add(btnCancel, 0, 6);
        grid.add(btnAddEntryToDoList, 1, 6);
        grid.add(btnListGreenSpaces, 0, 7);

        applyButtonAnimations();
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