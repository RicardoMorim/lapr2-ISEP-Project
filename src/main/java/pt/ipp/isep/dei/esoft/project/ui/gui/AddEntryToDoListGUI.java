package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.application.controller.ToDoListController;
import pt.ipp.isep.dei.esoft.project.domain.EmailWrapper;
import pt.ipp.isep.dei.esoft.project.domain.Entry;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Urgency;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;

public class AddEntryToDoListGUI {

    private ToDoListController controller = new ToDoListController();
    private GreenSpaceController greenSpaceController = new GreenSpaceController();

    public GridPane getAddEntryToToDoList(double height, double width) {
        GridPane grid = new GridPane(height,width);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        // Create a new TableView for the green spaces
        TableView<GreenSpace> tableGreenSpaces = new TableView<>();
        TableColumn<GreenSpace, String> colName = new TableColumn<>("Name");
        TableColumn<GreenSpace, String> colLocation = new TableColumn<>("Location");

        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colLocation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().toString()));

        tableGreenSpaces.getColumns().addAll(colName, colLocation);

        Email user = Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId();
        List<GreenSpace> managedGreenSpaces = greenSpaceController.getGreenSpacesManagedByUser(new EmailWrapper((user)));
        tableGreenSpaces.getItems().addAll(managedGreenSpaces);

        grid.add(new Label("Your Green Spaces:"), 0, 0);
        grid.add(tableGreenSpaces, 1, 0);

        TextField titleField = new TextField();
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                titleField.setText(oldValue);
            }
        });
        grid.add(new Label("Title:"), 0, 2);
        grid.add(titleField, 1, 2);

        TextField descriptionField = new TextField();
        descriptionField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                descriptionField.setText(oldValue);
            }
        });
        grid.add(new Label("Description:"), 0, 3);
        grid.add(descriptionField, 1, 3);

        ComboBox<Urgency> urgencyComboBox = new ComboBox<>();
        urgencyComboBox.getItems().addAll(Urgency.values());
        grid.add(new Label("Urgency:"), 0, 4);
        grid.add(urgencyComboBox, 1, 4);

        TextField durationField = new TextField();
        durationField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    durationField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        grid.add(new Label("Expected Duration (in hours):"), 0, 5);
        grid.add(durationField, 1, 5);

        Button addButton = new Button("Add Entry");
        addButton.setOnAction(e -> {
            GreenSpace greenSpace = tableGreenSpaces.getSelectionModel().getSelectedItem();
            String title = titleField.getText();
            String description = descriptionField.getText();
            Urgency urgency = urgencyComboBox.getValue();
            float expectedDuration = Float.parseFloat(durationField.getText());

            Entry entry = new Entry(greenSpace, title, description, urgency, expectedDuration);
            controller.addEntry(entry);

            new Alert(Alert.AlertType.INFORMATION, "Entry added successfully!").show();

            titleField.clear();
            descriptionField.clear();
            urgencyComboBox.getSelectionModel().clearSelection();
            durationField.clear();

        });
        grid.add(addButton, 1, 6);
        addButton.getStyleClass().add("add-button");
        return grid;
    }

}