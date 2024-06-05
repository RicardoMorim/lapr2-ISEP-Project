package pt.ipp.isep.dei.esoft.project.ui.gui.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import pt.ipp.isep.dei.esoft.project.ui.gui.AddSkillToCollaboratorGUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.GenerateTeamProposalGUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.RegisterCollaboratorGUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.RegisterJobGUI;

public class MainHRMMenuGUI {

    private Pane content;

    public MainHRMMenuGUI(Pane content) {
        this.content = content;
    }

    public GridPane getGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        Button btnRegisterSkill = new Button("Register a skill that a collaborator may have.");
        //btnRegisterSkill.setOnAction(e -> new RegisterSkillUI().start(new Stage()));

        Button btnRegisterJob = new Button("Register a job that a collaborator may have.");
        btnRegisterJob.setOnAction(e -> content.getChildren().setAll(new RegisterJobGUI().getRegisterJobGUI()));

        Button btnRegisterCollaborator = new Button("Register a collaborator");
        btnRegisterCollaborator.setOnAction(e -> content.getChildren().setAll(new RegisterCollaboratorGUI().getRegisterCollaboratorGUI(content.getHeight(), content.getWidth())));

        Button btnAddSkillToCollaborator = new Button("Add a skill to a collaborator");
        btnAddSkillToCollaborator.setOnAction(e -> content.getChildren().setAll(new AddSkillToCollaboratorGUI().getAddSkillToCollaboratorGUI(content.getHeight(), content.getWidth())));

        Button btnGenerateTeamProposal = new Button("Generate a team proposal automatically");
        btnGenerateTeamProposal.setOnAction(e -> content.getChildren().setAll(new GenerateTeamProposalGUI().getGenerateTeamProposalGUI(content.getHeight(), content.getWidth())));


        grid.getChildren().setAll(btnRegisterSkill, btnRegisterJob, btnRegisterCollaborator, btnAddSkillToCollaborator,
                btnGenerateTeamProposal);


        return grid;
    }
}