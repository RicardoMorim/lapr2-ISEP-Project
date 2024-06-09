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
import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.JobController;
import pt.ipp.isep.dei.esoft.project.application.controller.SkillController;
import pt.ipp.isep.dei.esoft.project.domain.Address;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Job;

import java.time.LocalDate;

public class RegisterCollaboratorGUI {

    JobController jobController;
    CollaboratorController collaboratorController;
    SkillController skillController;

    public RegisterCollaboratorGUI() {
        this.jobController = new JobController();
        this.collaboratorController = new CollaboratorController();
        this.skillController = new SkillController();
    }

    public GridPane getRegisterCollaboratorGUI(double height, double width) {
        GridPane gridPane = new GridPane(height, width);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label headerLabel = new Label("Register a Collaborator");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        gridPane.add(headerLabel, 0, 0, 2, 1);

        Label nameLabel = new Label("Collaborator Name: ");
        gridPane.add(nameLabel, 0, 1);
        TextField nameField = new TextField();
        nameField.setPrefWidth(150);
        gridPane.add(nameField, 1, 1);

        Label birthDateLabel = new Label("Birth Date: ");
        gridPane.add(birthDateLabel, 0, 2);
        DatePicker birthDatePicker = new DatePicker();
        gridPane.add(birthDatePicker, 1, 2);

        Label admissionDateLabel = new Label("Admission Date: ");
        gridPane.add(admissionDateLabel, 0, 3);
        DatePicker admissionDatePicker = new DatePicker();
        gridPane.add(admissionDatePicker, 1, 3);

        Label addressLabel = new Label("Street Address: ");
        gridPane.add(addressLabel, 0, 4);
        TextField addressField = new TextField();
        addressField.setPrefWidth(150);
        gridPane.add(addressField, 1, 4);

        Label cityLabel = new Label("City: ");
        gridPane.add(cityLabel, 0, 5);
        TextField cityField = new TextField();
        cityField.setPrefWidth(150);
        gridPane.add(cityField, 1, 5);

        Label zipCodeLabel = new Label("Zip Code: ");
        gridPane.add(zipCodeLabel, 0, 6);
        TextField zipCodeField = new TextField();
        zipCodeField.setPrefWidth(150);
        gridPane.add(zipCodeField, 1, 6);

        Label phoneLabel = new Label("Phone: ");
        gridPane.add(phoneLabel, 0, 7);
        TextField phoneField = new TextField();
        phoneField.setPrefWidth(150);
        gridPane.add(phoneField, 1, 7);

        Label emailLabel = new Label("Email: ");
        gridPane.add(emailLabel, 0, 8);
        TextField emailField = new TextField();
        emailField.setPrefWidth(150);
        gridPane.add(emailField, 1, 8);

        Label nifLabel = new Label("Taxpayer Number (NIF): ");
        gridPane.add(nifLabel, 0, 9);
        TextField nifField = new TextField();
        nifField.setPrefWidth(150);
        gridPane.add(nifField, 1, 9);

        Label idTypeLabel = new Label("ID Type: ");
        gridPane.add(idTypeLabel, 0, 10);
        TextField idTypeField = new TextField();
        idTypeField.setPrefWidth(150);
        gridPane.add(idTypeField, 1, 10);

        Label idNumberLabel = new Label("ID Number: ");
        gridPane.add(idNumberLabel, 0, 11);
        TextField idNumberField = new TextField();
        idNumberField.setPrefWidth(150);
        gridPane.add(idNumberField, 1, 11);

        Label jobLabel = new Label("Job: ");
        gridPane.add(jobLabel, 0, 12);
        ComboBox<Job> jobComboBox = new ComboBox<>();
        jobComboBox.getItems().addAll(jobController.getJobList());
        gridPane.add(jobComboBox, 1, 12);

        Button submitButton = new Button("Register Collaborator");
        submitButton.getStyleClass().add("add-button");
        Button removeButton = new Button("Remove Collaborator");
        removeButton.getStyleClass().add("remove-button");

        HBox buttonBox = new HBox(10, submitButton);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 13, 2, 1);

        TableView<Collaborator> tableView = new TableView<>();
        tableView.setPrefWidth(width / 2);

        TableColumn<Collaborator, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Collaborator, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Collaborator, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Collaborator, String> jobColumn = new TableColumn<>("Job");
        jobColumn.setCellValueFactory(new PropertyValueFactory<>("job"));

        tableView.getColumns().addAll(nameColumn, emailColumn, phoneColumn, jobColumn);

        // Populate the table with collaborators
        tableView.getItems().addAll(collaboratorController.getCollaboratorList());

        VBox tableBox = new VBox(10, tableView, removeButton);
        tableBox.setPadding(new Insets(20, 20, 20, 20));
        tableBox.setAlignment(Pos.CENTER);

        TitledPane titledPane = new TitledPane("Collaborators List", tableBox);
        Accordion accordion = new Accordion(titledPane);
        titledPane.setExpanded(false); // Start collapsed

        VBox mainLayout = new VBox(10, gridPane, accordion);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));
        mainLayout.setAlignment(Pos.CENTER);

        // Add listeners for validation
        addValidationListeners(nameField, nameLabel);
        addValidationListeners(addressField, addressLabel);
        addValidationListeners(cityField, cityLabel);
        addValidationListeners(zipCodeField, zipCodeLabel);
        addValidationListeners(phoneField, phoneLabel);
        addValidationListeners(emailField, emailLabel);
        addValidationListeners(nifField, nifLabel);
        addValidationListeners(idTypeField, idTypeLabel);
        addValidationListeners(idNumberField, idNumberLabel);

        submitButton.setOnAction(event -> {
            boolean valid = true;

            // Validate all fields
            valid &= validateTextField(nameField, nameLabel);
            valid &= validateDatePicker(birthDatePicker, birthDateLabel);
            valid &= validateDatePicker(admissionDatePicker, admissionDateLabel);
            valid &= validateTextField(addressField, addressLabel);
            valid &= validateTextField(cityField, cityLabel);
            valid &= validateTextField(zipCodeField, zipCodeLabel);
            valid &= validatePhoneField(phoneField, phoneLabel);
            valid &= validateEmailField(emailField, emailLabel);
            valid &= validateNumberField(nifField, nifLabel);
            valid &= validateTextField(idTypeField, idTypeLabel);
            valid &= validateNumberField(idNumberField, idNumberLabel);
            valid &= validateComboBox(jobComboBox, jobLabel);

            if (!valid) {
                return;
            }

            String name = nameField.getText();
            LocalDate birthDate = birthDatePicker.getValue();
            LocalDate admissionDate = admissionDatePicker.getValue();
            String address = addressField.getText();
            String city = cityField.getText();
            String zipCode = zipCodeField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            int nif = Integer.parseInt(nifField.getText());
            String idType = idTypeField.getText();
            int idNumber = Integer.parseInt(idNumberField.getText());
            Job job = jobComboBox.getValue();

            try {
                Collaborator collaborator = collaboratorController.registerCollaborator(name, email, new Address(address, city, zipCode),
                        phone, job, java.sql.Date.valueOf(birthDate),
                        java.sql.Date.valueOf(admissionDate), idType, nif, idNumber);

                collaboratorController.notifyNewCollaborator(collaborator);

                tableView.getItems().add(collaborator);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("Collaborator registered successfully.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("logo.png"));
                alert.showAndWait();

                // Optionally, clear the fields after successful registration
                nameField.clear();
                birthDatePicker.setValue(null);
                admissionDatePicker.setValue(null);
                addressField.clear();
                cityField.clear();
                zipCodeField.clear();
                phoneField.clear();
                emailField.clear();
                nifField.clear();
                idTypeField.clear();
                idNumberField.clear();
                jobComboBox.setValue(null);

            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Failed");
                alert.setHeaderText("Error registering collaborator");
                alert.setContentText(e.getMessage());
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("logo.png"));
                alert.showAndWait();
            }
        });

        removeButton.setOnAction(event -> {
            Collaborator selectedCollaborator = tableView.getSelectionModel().getSelectedItem();
            if (selectedCollaborator != null) {
                collaboratorController.removeCollaborator(selectedCollaborator);
                tableView.getItems().remove(selectedCollaborator);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText(null);
                alert.setContentText("Please select a collaborator to remove.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("logo.png"));
                alert.showAndWait();
            }
        });
        GridPane newGrid = new GridPane();
        newGrid.add(mainLayout, 0, 0);
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

    private boolean validateDatePicker(DatePicker datePicker, Label label) {
        if (datePicker.getValue() == null) {
            setFieldError(datePicker, label, "This field cannot be empty.");
            return false;
        } else {
            clearFieldError(datePicker, label);
            return true;
        }
    }

    private boolean validatePhoneField(TextField textField, Label label) {
        String phone = textField.getText().trim();
        if (phone.isEmpty() || !phone.matches("\\d{9,10}")) {
            setFieldError(textField, label, "Invalid phone number.");
            return false;
        } else {
            clearFieldError(textField, label);
            return true;
        }
    }

    private boolean validateEmailField(TextField textField, Label label) {
        String email = textField.getText().trim();
        if (email.isEmpty() || !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            setFieldError(textField, label, "Invalid email address.");
            return false;
        } else {
            clearFieldError(textField, label);
            return true;
        }
    }

    private boolean validateNumberField(TextField textField, Label label) {
        String number = textField.getText().trim();
        if (number.isEmpty() || !number.matches("\\d+")) {
            setFieldError(textField, label, "Invalid number.");
            return false;
        } else {
            clearFieldError(textField, label);
            return true;
        }
    }

    private boolean validateComboBox(ComboBox<?> comboBox, Label label) {
        if (comboBox.getValue() == null) {
            setFieldError(comboBox, label, "This field cannot be empty.");
            return false;
        } else {
            clearFieldError(comboBox, label);
            return true;
        }
    }

    private void setFieldError(Control control, Label label, String errorMessage) {
        control.setStyle("-fx-border-color: red;");
        label.setStyle("-fx-text-fill: red;");
        if (!label.getText().contains(" - ")) {
            label.setText(label.getText() + " - " + errorMessage);
        }
    }

    private void clearFieldError(Control control, Label label) {
        control.setStyle("");
        label.setStyle("");
        if (label.getText().contains(" - ")) {
            label.setText(label.getText().substring(0, label.getText().indexOf(" - ")));
        }
    }


}
