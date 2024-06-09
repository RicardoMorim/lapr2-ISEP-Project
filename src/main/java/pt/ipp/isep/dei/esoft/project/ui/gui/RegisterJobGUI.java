package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.ipp.isep.dei.esoft.project.application.controller.JobController;
import pt.ipp.isep.dei.esoft.project.domain.Job;

public class RegisterJobGUI {

    JobController jobController;

    public RegisterJobGUI() {
        this.jobController = new JobController();
    }

    public GridPane getRegisterJobGUI() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label headerLabel = new Label("Register a Job");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        gridPane.add(headerLabel, 0, 0, 2, 1);

        Label nameLabel = new Label("Job Name: ");
        gridPane.add(nameLabel, 0, 1);
        TextField nameField = new TextField();
        nameField.setPrefWidth(300); // Increased size
        gridPane.add(nameField, 1, 1);

        Label descriptionLabel = new Label("Job Description: ");
        gridPane.add(descriptionLabel, 0, 2);
        TextField descriptionField = new TextField();
        descriptionField.setPrefWidth(300); // Increased size
        gridPane.add(descriptionField, 1, 2);

        Button submitButton = new Button("Register Job");
        submitButton.getStyleClass().add("add-button");
        Button removeButton = new Button("Remove Job");
        removeButton.getStyleClass().add("remove-button");

        HBox buttonBox = new HBox(10, submitButton, removeButton);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 3, 2, 1);

        TableView<Job> tableView = new TableView<>();

        TableColumn<Job, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Job, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("shortDescription"));

        tableView.getColumns().addAll(nameColumn, descriptionColumn);

        // Populate the table with jobs
        tableView.getItems().addAll(jobController.getJobList());

        VBox tableBox = new VBox(10, tableView);
        tableBox.setPadding(new Insets(20, 20, 20, 20));
        tableBox.setAlignment(Pos.CENTER);

        TitledPane titledPane = new TitledPane("Jobs List", tableBox);
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
                Job job = jobController.registerJob(name, description);
                tableView.getItems().add(job);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("Job registered successfully.");
                alert.showAndWait();

                // Optionally, clear the fields after successful registration
                nameField.clear();
                descriptionField.clear();

            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Failed");
                alert.setHeaderText("Error registering job");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        removeButton.setOnAction(event -> {
            Job selectedJob = tableView.getSelectionModel().getSelectedItem();
            if (selectedJob != null) {
                jobController.removeJob(selectedJob);
                tableView.getItems().remove(selectedJob);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText(null);
                alert.setContentText("Please select a job to remove.");
                alert.showAndWait();
            }
        });


        // Populate the table with jobs
        tableView.getItems().addAll(jobController.getJobList());


        // Add the tableBox to the main layout
        GridPane newGrid = new GridPane();
        newGrid.add(mainLayout, 0, 0);
        newGrid.add(tableBox, 0, 1); // Adjust as needed
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

    private boolean validateTextField(TextField textField, Label label) {
        if (textField.getText().trim().isEmpty()) {
            setFieldError(textField, label, "This field cannot be empty.");
            return false;
        } else {
            clearFieldError(textField, label);
            return true;
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
}