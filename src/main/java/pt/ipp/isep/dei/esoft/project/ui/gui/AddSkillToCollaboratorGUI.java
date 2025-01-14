package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.SkillController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

/**
 * The type Add skill to collaborator gui.
 */
public class AddSkillToCollaboratorGUI {

    private CollaboratorController collaboratorController = new CollaboratorController();
    private SkillController skillController = new SkillController();

    /**
     * Gets add skill to collaborator gui.
     *
     * @param height the height
     * @param width  the width
     * @return the add skill to collaborator gui
     */
    public GridPane getAddSkillToCollaboratorGUI(double height, double width) {
        GridPane grid = new GridPane(height, width);
        grid.setAlignment(Pos.CENTER); // Center the grid
        grid.setPadding(new Insets(20, 10, 10, 10)); // Add a small padding to the top
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setStyle("-fx-background-color: #ECECEC;"); // Set the background color of the grid


        Label lblCollaborator = new Label("Select a collaborator:");
        ComboBox<Collaborator> cbCollaborator = new ComboBox<>();
        cbCollaborator.getItems().addAll(collaboratorController.getCollaboratorList());

        Label lblSkill = new Label("Select a skill:");
        ComboBox<Skill> cbSkill = new ComboBox<>();

        Label lblAddedSkills = new Label("Added skills:");
        ListView<Skill> lvAddedSkills = new ListView<>();

        // Add a listener to the collaborator ComboBox
        cbCollaborator.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cbSkill.getItems().clear(); // Clear the skill ComboBox
            lvAddedSkills.getItems().clear(); // Clear the ListView
            if (newValue != null) { // If a collaborator is selected
                // Get the skills that the selected collaborator does not have and add them to the skill ComboBox
                cbSkill.getItems().addAll(skillController.getSkillThatTheCollaboratorDoesNotHave(newValue.getSkills()));
                // Get the skills that the selected collaborator has and add them to the ListView
                lvAddedSkills.getItems().addAll(newValue.getSkills());
            }
        });

        Button btnRemoveSkill = new Button("Remove Skill");

        Button btnAddSkill = new Button("Add Skill");
        btnAddSkill.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Set the background color and text color of the button
        btnAddSkill.setOnAction(e -> {
            Collaborator selectedCollaborator = cbCollaborator.getValue();
            Skill selectedSkill = cbSkill.getValue();
            if (selectedSkill != null) {
                collaboratorController.addSkillToACollaborator(selectedSkill, selectedCollaborator);
                cbSkill.getItems().remove(selectedSkill); // Remove the added skill from the skill ComboBox
                lvAddedSkills.getItems().add(selectedSkill); // Add the added skill to the ListView
            }
        });

        btnRemoveSkill.setOnAction(e -> {
            Collaborator selectedCollaborator = cbCollaborator.getValue();
            Skill selectedSkill = lvAddedSkills.getSelectionModel().getSelectedItem();
            if (selectedSkill != null) {
                collaboratorController.removeSkillFromACollaborator(selectedSkill, selectedCollaborator);
                lvAddedSkills.getItems().remove(selectedSkill);
                cbSkill.getItems().add(selectedSkill);
            }
        });

        btnRemoveSkill.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;");


        grid.add(lblCollaborator, 0, 0);
        grid.add(cbCollaborator, 1, 0);
        grid.add(lblSkill, 0, 1);
        grid.add(cbSkill, 1, 1);
        grid.add(btnAddSkill, 1, 2);
        grid.add(lblAddedSkills, 0, 3);
        grid.add(lvAddedSkills, 1, 3);
        grid.add(btnRemoveSkill, 1, 4);

        return grid;
    }

}