package pt.ipp.isep.dei.esoft.project.ui.gui;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.application.controller.ToDoListController;
import pt.ipp.isep.dei.esoft.project.domain.EmailWrapper;
import pt.ipp.isep.dei.esoft.project.domain.Entry;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Urgency;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;

public class ToDoListGUI extends Application {

    private ToDoListController controller = new ToDoListController();
    private GreenSpaceController greenSpaceController = new GreenSpaceController();

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        ComboBox<GreenSpace> greenSpaceComboBox = new ComboBox<>();
        Email user = Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId();
        List<GreenSpace> managedGreenSpaces = greenSpaceController.getGreenSpacesManagedByUser(new EmailWrapper((user)));
        greenSpaceComboBox.getItems().addAll(managedGreenSpaces);
        grid.add(new Label("Green Space:"), 0, 0);
        grid.add(greenSpaceComboBox, 1, 0);

        TextField stateField = new TextField();
        grid.add(new Label("State:"), 0, 1);
        grid.add(stateField, 1, 1);

        TextField titleField = new TextField();
        grid.add(new Label("Title:"), 0, 2);
        grid.add(titleField, 1, 2);

        TextField descriptionField = new TextField();
        grid.add(new Label("Description:"), 0, 3);
        grid.add(descriptionField, 1, 3);

        ComboBox<Urgency> urgencyComboBox = new ComboBox<>();
        urgencyComboBox.getItems().addAll(Urgency.values());
        grid.add(new Label("Urgency:"), 0, 4);
        grid.add(urgencyComboBox, 1, 4);

        TextField durationField = new TextField();
        grid.add(new Label("Expected Duration:"), 0, 5);
        grid.add(durationField, 1, 5);

        Button addButton = new Button("Add Entry");
        addButton.setOnAction(e -> {
            GreenSpace greenSpace = greenSpaceComboBox.getValue();
            String state = stateField.getText();
            String title = titleField.getText();
            String description = descriptionField.getText();
            Urgency urgency = urgencyComboBox.getValue();
            float expectedDuration = Float.parseFloat(durationField.getText());

            Entry entry = new Entry(state, greenSpace, title, description, urgency, expectedDuration);
            controller.addEntry(entry);
        });
        grid.add(addButton, 1, 6);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setTitle("To-Do List Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
