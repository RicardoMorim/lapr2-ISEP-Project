package pt.ipp.isep.dei.esoft.project.ui.gui.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import pt.ipp.isep.dei.esoft.project.ui.gui.*;

public class MainCollaboratorMenu {

    private Pane content;

    public MainCollaboratorMenu(Pane content) {
        this.content = content;
    }

    public GridPane getGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        Button btnCheckAssignedTasks = new Button("Check assigned tasks");
        btnCheckAssignedTasks.setOnAction(e -> {
            CheckAssignedTasksGUI checkAssignedTasksGUI = new CheckAssignedTasksGUI();
            content.getChildren().setAll(checkAssignedTasksGUI.getAssignedTasks(content.getHeight(), content.getWidth()));
        });

        Button btnCheckEmails = new Button("Check emails");
        btnCheckEmails.setOnAction(e -> {
            checkEmailsGUI checkEmailsGUI = new checkEmailsGUI();
            content.getChildren().setAll(checkEmailsGUI.getCheckEmailsGridPane());
        });

        grid.getChildren().setAll(btnCheckAssignedTasks, btnCheckEmails);

        btnCheckAssignedTasks.fire();
        return grid;
    }
}