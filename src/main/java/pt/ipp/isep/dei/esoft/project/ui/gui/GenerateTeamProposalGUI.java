package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.SkillController;
import pt.ipp.isep.dei.esoft.project.application.controller.TeamController;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.util.ArrayList;
import java.util.List;

public class GenerateTeamProposalGUI extends Application {

    private SkillController skillController = new SkillController();
    private TeamController teamController = new TeamController();
    private CollaboratorController collaboratorController = new CollaboratorController();
    private List<Team> teamProposals = new ArrayList<>();
    private int currentTeamIndex = 0;

    public GridPane getGenerateTeamProposalGUI(double height, double width) {

        GridPane grid = new GridPane(height, width);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label lblMinSize = new Label("Minimum Team Size:");
        TextField txtMinSize = new TextField();

        Label lblMaxSize = new Label("Maximum Team Size:");
        TextField txtMaxSize = new TextField();

        Label lblSkill = new Label("Select a required skill:");
        ComboBox<Skill> cbSkill = new ComboBox<>();
        cbSkill.getItems().addAll(skillController.getSkillList());

        Label lblSkillCount = new Label("Number of times skill is needed:");
        TextField txtSkillCount = new TextField();

        Button btnAddSkill = new Button("Add Skill");
        ListView<Skill> lvSkills = new ListView<>();

        btnAddSkill.setOnAction(e -> {
            Skill selectedSkill = cbSkill.getValue();
            int skillCount = Integer.parseInt(txtSkillCount.getText());
            for (int i = 0; i < skillCount; i++) {
                lvSkills.getItems().add(selectedSkill);
            }
        });

        ListView<Team> lvTeamProposals = new ListView<>();

        Label lblCurrentTeam = new Label();
        Button btnNext = new Button("Next Team");
        Button btnPrev = new Button("Previous Team");

        btnNext.setOnAction(e -> {
            currentTeamIndex++;
            updateTeamDisplay(lblCurrentTeam, btnNext, btnPrev);
        });

        btnPrev.setOnAction(e -> {
            currentTeamIndex--;
            updateTeamDisplay(lblCurrentTeam, btnNext, btnPrev);
        });


        Button btnGenerate = new Button("Generate Team Proposal");

        btnGenerate.setOnAction(e -> {
            try {
                int minSize = Integer.parseInt(txtMinSize.getText());
                int maxSize = Integer.parseInt(txtMaxSize.getText());
                List<Skill> requiredSkills = new ArrayList<>(lvSkills.getItems());
                teamProposals = teamController.generateTeamProposals(minSize, maxSize, requiredSkills, collaboratorController.getCollaboratorList());
                currentTeamIndex = 0;
                updateTeamDisplay(lblCurrentTeam, btnNext, btnPrev);
            } catch (IllegalArgumentException ex) {
                lblCurrentTeam.setText(ex.getMessage());
            }
        });

        Button btnAccept = new Button("Accept Team Proposal");

        btnAccept.setOnAction(e -> {
            if (!teamProposals.isEmpty()) {
                Team selectedTeam = teamProposals.get(currentTeamIndex);
                collaboratorController.startTask(selectedTeam);
                teamController.addTeam(selectedTeam);
                teamProposals.clear();
                currentTeamIndex = 0;
                updateTeamDisplay(lblCurrentTeam, btnNext, btnPrev);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Team Proposal Accepted");
                alert.setHeaderText(null);
                alert.setContentText("The team proposal was successfully accepted and stored.");
                alert.showAndWait();
            }
        });

        Button btnDeleteSkill = new Button("Delete Skill");

        btnDeleteSkill.setOnAction(e -> {
            Skill selectedSkill = lvSkills.getSelectionModel().getSelectedItem();
            if (selectedSkill != null) {
                lvSkills.getItems().remove(selectedSkill);
            }
        });
        grid.setAlignment(Pos.CENTER);
        GridPane.setHalignment(lblMinSize, HPos.CENTER);
        GridPane.setHalignment(txtMinSize, HPos.CENTER);

        grid.add(btnDeleteSkill, 1, 5);
        GridPane.setHalignment(btnDeleteSkill, HPos.RIGHT);

        grid.add(lblMinSize, 0, 0);

        grid.add(txtMinSize, 1, 0);

        grid.add(lblMaxSize, 0, 1);

        grid.add(txtMaxSize, 1, 1);

        grid.add(lblSkill, 0, 2);

        grid.add(cbSkill, 1, 2);

        grid.add(lblSkillCount, 0, 3);

        grid.widthProperty().addListener((observable, oldValue, newValue) -> {
            txtSkillCount.setMaxWidth(newValue.doubleValue() / 4);
        });

        grid.add(txtSkillCount, 1, 3);

        grid.add(btnAddSkill, 1, 3);
        GridPane.setHalignment(btnAddSkill, HPos.RIGHT);

        grid.add(lvSkills, 1, 4);

        grid.add(btnGenerate, 1, 5);

        grid.add(lvTeamProposals, 1, 6);

        grid.add(lblCurrentTeam, 1, 6);

        grid.add(btnAccept, 1, 8);
        GridPane.setHalignment(btnAccept, HPos.CENTER);


        grid.add(btnPrev, 1, 8);


        grid.add(btnNext, 1, 8);
        GridPane.setHalignment(btnNext, HPos.RIGHT);

        return grid;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Generate Team Proposal");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label lblMinSize = new Label("Minimum Team Size:");
        TextField txtMinSize = new TextField();

        Label lblMaxSize = new Label("Maximum Team Size:");
        TextField txtMaxSize = new TextField();

        Label lblSkill = new Label("Select a required skill:");
        ComboBox<Skill> cbSkill = new ComboBox<>();
        cbSkill.getItems().addAll(skillController.getSkillList());

        Label lblSkillCount = new Label("Number of times skill is needed:");
        TextField txtSkillCount = new TextField();

        Button btnAddSkill = new Button("Add Skill");
        ListView<Skill> lvSkills = new ListView<>();

        btnAddSkill.setOnAction(e -> {
            Skill selectedSkill = cbSkill.getValue();
            int skillCount = Integer.parseInt(txtSkillCount.getText());
            for (int i = 0; i < skillCount; i++) {
                lvSkills.getItems().add(selectedSkill);
            }
        });

        ListView<Team> lvTeamProposals = new ListView<>();

        Label lblCurrentTeam = new Label();
        Button btnNext = new Button("Next Team");
        Button btnPrev = new Button("Previous Team");

        btnNext.setOnAction(e -> {
            currentTeamIndex++;
            updateTeamDisplay(lblCurrentTeam, btnNext, btnPrev);
        });

        btnPrev.setOnAction(e -> {
            currentTeamIndex--;
            updateTeamDisplay(lblCurrentTeam, btnNext, btnPrev);
        });


        Button btnGenerate = new Button("Generate Team Proposal");

        btnGenerate.setOnAction(e -> {
            try {
                int minSize = Integer.parseInt(txtMinSize.getText());
                int maxSize = Integer.parseInt(txtMaxSize.getText());
                List<Skill> requiredSkills = new ArrayList<>(lvSkills.getItems());
                teamProposals = teamController.generateTeamProposals(minSize, maxSize, requiredSkills, collaboratorController.getCollaboratorList());
                currentTeamIndex = 0;
                updateTeamDisplay(lblCurrentTeam, btnNext, btnPrev);
            } catch (IllegalArgumentException ex) {
                lblCurrentTeam.setText(ex.getMessage());
            }
        });

        Button btnAccept = new Button("Accept Team Proposal");

        btnAccept.setOnAction(e -> {
            if (!teamProposals.isEmpty()) {
                Team selectedTeam = teamProposals.get(currentTeamIndex);
                collaboratorController.startTask(selectedTeam);
                teamController.addTeam(selectedTeam);
                teamProposals.clear();
                currentTeamIndex = 0;
                updateTeamDisplay(lblCurrentTeam, btnNext, btnPrev);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Team Proposal Accepted");
                alert.setHeaderText(null);
                alert.setContentText("The team proposal was successfully accepted and stored.");
                alert.showAndWait();
            }
        });

        Button btnDeleteSkill = new Button("Delete Skill");

        btnDeleteSkill.setOnAction(e -> {
            Skill selectedSkill = lvSkills.getSelectionModel().getSelectedItem();
            if (selectedSkill != null) {
                lvSkills.getItems().remove(selectedSkill);
            }
        });
        grid.setAlignment(Pos.CENTER);
        GridPane.setHalignment(lblMinSize, HPos.CENTER);
        GridPane.setHalignment(txtMinSize, HPos.CENTER);

        grid.add(btnDeleteSkill, 1, 5);
        GridPane.setHalignment(btnDeleteSkill, HPos.RIGHT);

        grid.add(lblMinSize, 0, 0);

        grid.add(txtMinSize, 1, 0);

        grid.add(lblMaxSize, 0, 1);

        grid.add(txtMaxSize, 1, 1);

        grid.add(lblSkill, 0, 2);

        grid.add(cbSkill, 1, 2);

        grid.add(lblSkillCount, 0, 3);

        grid.widthProperty().addListener((observable, oldValue, newValue) -> {
            txtSkillCount.setMaxWidth(newValue.doubleValue() / 4);
        });

        grid.add(txtSkillCount, 1, 3);

        grid.add(btnAddSkill, 1, 3);
        GridPane.setHalignment(btnAddSkill, HPos.RIGHT);

        grid.add(lvSkills, 1, 4);

        grid.add(btnGenerate, 1, 5);

        grid.add(lvTeamProposals, 1, 6);

        grid.add(lblCurrentTeam, 1, 6);

        grid.add(btnAccept, 1, 8);
        GridPane.setHalignment(btnAccept, HPos.CENTER);


        grid.add(btnPrev, 1, 8);


        grid.add(btnNext, 1, 8);
        GridPane.setHalignment(btnNext, HPos.RIGHT);


        Scene scene = new Scene(grid, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateTeamDisplay(Label lblCurrentTeam, Button btnNext, Button btnPrev) {
        if (teamProposals.isEmpty()) {
            lblCurrentTeam.setText("No team proposals generated.");
        } else {
            lblCurrentTeam.setText("Team Proposal " + (currentTeamIndex + 1) + ":\n" + teamProposals.get(currentTeamIndex).toString());
        }

        btnNext.setDisable(currentTeamIndex >= teamProposals.size() - 1);
        btnPrev.setDisable(currentTeamIndex <= 0);
    }
}