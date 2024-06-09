package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.TeamController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class CancelGUI {

    private final AgendaController agendaController = new AgendaController();
    private final TeamController teamController = new TeamController();

    public GridPane getCancelEntryGridPane(double height, double width){
        GridPane grid = new GridPane(height, width);
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);


        VBox vbox = new VBox();
        vbox.setSpacing(10);

        // Create a new TableView for the entries
        TableView<AgendaEntry> tableEntries = new TableView<>();
        TableColumn<AgendaEntry, String> colTitle = new TableColumn<>("Title");
        TableColumn<AgendaEntry, String> colDescription = new TableColumn<>("Description");
        TableColumn<AgendaEntry, LocalDate> colStartDate = new TableColumn<>("Start Date");
        TableColumn<AgendaEntry, LocalDate> colExpectedDuration = new TableColumn<>("Expected EndDate");

        colTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntry().getTitle()));
        colDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntry().getDescription()));
        colStartDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        colExpectedDuration.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));

        tableEntries.getColumns().addAll(colTitle, colDescription, colStartDate, colExpectedDuration);

        List<AgendaEntry> agendaEntries = agendaController.getAgenda().getEntries();
        tableEntries.getItems().addAll(agendaEntries);

        Button btnCancel = new Button("Cancel");

        // Add event handler to the button
        btnCancel.setOnAction(e -> {
            AgendaEntry selectedEntry = tableEntries.getSelectionModel().getSelectedItem();

            if (selectedEntry != null) {
                agendaController.cancelAgendaEntry(selectedEntry);
                teamController.notifyTeamCancelled(selectedEntry);
            }

            if (selectedEntry.getStatus() == Status.CANCELED) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Entry Canceled");
                alert.setContentText("The entry was successfully canceled.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("logo.png"));
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("There was an error canceling the entry.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("logo.png"));
                alert.showAndWait();
            }
        });

        btnCancel.getStyleClass().add("remove-button");
        vbox.getChildren().addAll(tableEntries, btnCancel);

        grid.add(vbox, 0, 0);
        return grid;
    }
}