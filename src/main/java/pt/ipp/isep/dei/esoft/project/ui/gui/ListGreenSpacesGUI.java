package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.EmailWrapper;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class ListGreenSpacesGUI {

    private GreenSpaceController controller;

    public ListGreenSpacesGUI() {
        controller = new GreenSpaceController();
    }

    public GridPane getListGreenSpacesGridPane(double height, double width) {

        GridPane grid = new GridPane(height, width);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        // Load the properties file
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Create a ComboBox for the algorithms
        ComboBox<String> algorithmComboBox = new ComboBox<>();
        algorithmComboBox.getItems().addAll(prop.getProperty("QuickSortingAlgorithm"), prop.getProperty("BubbleSortingAlgorithm"));

        Label algorithmLabel = new Label("Algorithm: ");
        grid.add(algorithmLabel, 0, 0);
        grid.add(algorithmComboBox, 1, 0);

        Button fetchButton = new Button("Fetch Managed Green Spaces");

        TableView<GreenSpace> greenSpaceTableView = new TableView<>();
        TableColumn<GreenSpace, String> colName = new TableColumn<>("Name");
        TableColumn<GreenSpace, String> colAddress = new TableColumn<>("Address");
        TableColumn<GreenSpace, String> colArea = new TableColumn<>("Area");
        TableColumn<GreenSpace, String> colType = new TableColumn<>("Type");

        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().toString()));
        colArea.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getArea())));
        colType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));

        colName.setPrefWidth(150);
        colAddress.setPrefWidth(250);

        greenSpaceTableView.getColumns().addAll(colName, colAddress, colArea, colType);
        fetchButton.setOnAction(e -> {
            String algorithm = algorithmComboBox.getValue();
            if (algorithm == null) {
                showAlert("Algorithm cannot be empty.");
                return;
            }

            // Get the current logged user's email
            AuthenticationRepository authRepo = Repositories.getInstance().getAuthenticationRepository();
            Email user = authRepo.getCurrentUserSession().getUserId();
            EmailWrapper userEmail = new EmailWrapper(user);

            List<GreenSpace> managedGreenSpaces = controller.getGreenSpacesManagedByUser(userEmail);

            greenSpaceTableView.getItems().clear();
            if (managedGreenSpaces.isEmpty()) {
                greenSpaceTableView.setPlaceholder(new Label("No green spaces managed by the user."));
            } else {
                greenSpaceTableView.getItems().addAll(managedGreenSpaces);
            }

            // Use the selected algorithm
            if (algorithm.equals("QuickSort")) {
                prop.setProperty("SortingAlgorithm", "QuickSort");
            } else if (algorithm.equals("BubbleSort")) {
                prop.setProperty("SortingAlgorithm", "BubbleSort");
            }
        });

        fetchButton.getStyleClass().add("add-button");
        greenSpaceTableView.setPrefWidth(600);
        grid.add(fetchButton, 1, 1);
        grid.add(greenSpaceTableView, 0, 2, 2, 1);

        return grid;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}