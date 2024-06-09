package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.SkillController;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

public class RegisterSkillGUI {

    SkillController skillController;

    public RegisterSkillGUI() {
        this.skillController = new SkillController();
    }

    public GridPane getRegisterSkillGUI() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label headerLabel = new Label("Register a Skill");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        gridPane.add(headerLabel, 0, 0, 2, 1);

        Label nameLabel = new Label("Skill Name: ");
        gridPane.add(nameLabel, 0, 1);
        TextField nameField = new TextField();
        nameField.setPrefWidth(300); // Increased size
        gridPane.add(nameField, 1, 1);

        Label descriptionLabel = new Label("Skill Description: ");
        gridPane.add(descriptionLabel, 0, 2);
        TextArea descriptionField = new TextArea();
        descriptionField.setPrefWidth(300); // Increased size
        gridPane.add(descriptionField, 1, 2);

        Button submitButton = new Button("Register Skill");
        submitButton.getStyleClass().add("add-button");
        Button removeButton = new Button("Remove Skill");
        removeButton.getStyleClass().add("remove-button");

        HBox buttonBox = new HBox(10, submitButton, removeButton);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 3, 2, 1);

        TableView<Skill> tableView = new TableView<>();

        TableColumn<Skill, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Skill, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableView.getColumns().addAll(nameColumn, descriptionColumn);

        // Populate the table with skills
        tableView.getItems().addAll(skillController.getSkillList());

        VBox tableBox = new VBox(10, tableView);
        tableBox.setPadding(new Insets(20, 20, 20, 20));
        tableBox.setAlignment(Pos.CENTER);

        TitledPane titledPane = new TitledPane("Skills List", tableBox);
        Accordion accordion = new Accordion(titledPane);
        titledPane.setExpanded(false); // Start collapsed

        VBox mainLayout = new VBox(10, gridPane, accordion);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));
        mainLayout.setAlignment(Pos.CENTER);

        // Add listeners for validation
        addValidationListeners(nameField, nameLabel);
        addValidationListeners(descriptionField, descriptionLabel);

        submitButton.setOnAction(event -> {
            boolean valid = true;

            // Validate all fields
            valid &= validateTextField(nameField, nameLabel);
            valid &= validateTextField(descriptionField, descriptionLabel);

            if (!valid) {
                return;
            }

            String name = nameField.getText();
            String description = descriptionField.getText();

            try {
                Skill skill = new Skill(name, description);
                skillController.registerSkill(skill);
                tableView.getItems().add(skill);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("Skill registered successfully.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("logo.png"));
                alert.showAndWait();

                // Optionally, clear the fields after successful registration
                nameField.clear();
                descriptionField.clear();

            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Failed");
                alert.setHeaderText("Error registering skill");
                alert.setContentText(e.getMessage());
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("logo.png"));
                alert.showAndWait();
            }
        });

        removeButton.setOnAction(event -> {
            Skill selectedSkill = tableView.getSelectionModel().getSelectedItem();
            if (selectedSkill != null) {
                skillController.removeSkill(selectedSkill);
                tableView.getItems().remove(selectedSkill);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText(null);
                alert.setContentText("Please select a skill to remove.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("logo.png"));
                alert.showAndWait();
            }
        });


        // Add the tableBox to the main layout
        GridPane newGrid = new GridPane();
        newGrid.add(mainLayout, 0, 0);
        newGrid.add(tableBox, 0, 1);
        newGrid.setAlignment(Pos.CENTER);

        return newGrid;
    }

    private void addValidationListeners(TextField textField, Label label) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateTextField(textField, label);
            }
        });
    }

    private boolean validateTextField(TextInputControl textInputControl, Label label) {
        if (textInputControl.getText().trim().isEmpty()) {
            setFieldError(textInputControl, label, "This field cannot be empty.");
            return false;
        } else {
            clearFieldError(textInputControl, label);
            return true;
        }
    }

    private void setFieldError(TextInputControl textInputControl, Label label, String errorMessage) {
        textInputControl.setStyle("-fx-border-color: red;");
        label.setStyle("-fx-text-fill: red;");
        if (!label.getText().contains(" - ")) {
            label.setText(label.getText() + " - " + errorMessage);
        }
    }

    private void clearFieldError(TextInputControl textInputControl, Label label) {
        textInputControl.setStyle("");
        label.setStyle("");
        if (label.getText().contains(" - ")) {
            label.setText(label.getText().substring(0, label.getText().indexOf(" - ")));
        }
    }

    private void setFieldError(TextField textField, Label label, String errorMessage) {
        textField.setStyle("-fx-border-color: red;");
        label.setStyle("-fx-text-fill: red;");
        if (!label.getText().contains(" - ")) {
            label.setText(label.getText() + " - " + errorMessage);
        }
    }

    private void clearFieldError(TextField textField, Label label) {
        textField.setStyle("");
        label.setStyle("");
        if (label.getText().contains(" - ")) {
            label.setText(label.getText().substring(0, label.getText().indexOf(" - ")));
        }
    }

    private void addValidationListeners(TextArea textArea, Label label) {
        textArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateTextField(textArea, label);
            }
        });
    }
}