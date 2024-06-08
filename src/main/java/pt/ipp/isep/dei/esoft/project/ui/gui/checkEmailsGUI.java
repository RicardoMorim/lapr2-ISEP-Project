package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateTimeStringConverter;
import pt.ipp.isep.dei.esoft.project.application.controller.CollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Notification;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class checkEmailsGUI {

    private Collaborator loggedInCollaborator;

    public checkEmailsGUI() {
        CollaboratorController collaboratorController = new CollaboratorController();
        loggedInCollaborator = collaboratorController.getCollaboratorByEmail(String.valueOf((Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId())));
    }


    public GridPane getCheckEmailsGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        TableView<Notification> tableNotifications = new TableView<>();
        TableColumn<Notification, String> colTitle = new TableColumn<>("Title");
        TableColumn<Notification, LocalDateTime> colDate = new TableColumn<>("Date");
        TableColumn<Notification, Boolean> colReadStatus = new TableColumn<>("Read Status");

        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        colDate.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        colDate.setCellFactory(column -> new TextFieldTableCell<>(new LocalDateTimeStringConverter(formatter, null)));
        colReadStatus.setCellValueFactory(new PropertyValueFactory<>("readStatus"));

        tableNotifications.getColumns().addAll(colTitle, colDate, colReadStatus);

        FilteredList<Notification> filteredData = new FilteredList<>(FXCollections.observableArrayList(loggedInCollaborator.getNotifications()), p -> true);

        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        CheckBox readStatusCheckBox = new CheckBox("Show only unread notifications");

        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(createFilter(newValue, endDatePicker.getValue(), readStatusCheckBox.isSelected())));
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(createFilter(startDatePicker.getValue(), newValue, readStatusCheckBox.isSelected())));
        readStatusCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(createFilter(startDatePicker.getValue(), endDatePicker.getValue(), newValue)));

        tableNotifications.setItems(filteredData);
        tableNotifications.setPrefWidth(600);

        TextArea txtNotificationDetails = new TextArea();
        txtNotificationDetails.setEditable(false);

        tableNotifications.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtNotificationDetails.setText("Title: " + newValue.getTitle() + "\n\nMessage: " + newValue.getMessage() + "\n\nRead Status: " + (newValue.isReadStatus() ? "Read" : "Unread"));
                newValue.setReadStatus(true);
                tableNotifications.refresh();
            }
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(new Label("Start Date:"), startDatePicker, new Label("End Date:"), endDatePicker, readStatusCheckBox);
        hBox.setSpacing(10);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(tableNotifications);
        borderPane.setRight(txtNotificationDetails);

        borderPane.setPadding(new Insets(10, 10, 10, 10));

        vbox.getChildren().addAll(hBox, borderPane);
        grid.add(vbox, 0, 0);

        return grid;
    }

    private Predicate<Notification> createFilter(LocalDate startDate, LocalDate endDate, boolean onlyUnread) {
        return notification -> {
            LocalDateTime notificationDate = notification.getTimestamp();
            Instant instant = notificationDate.atZone(ZoneId.systemDefault()).toInstant();
            if (startDate != null && notificationDate.isBefore(startDate.atStartOfDay())) {
                return false;
            }
            if (endDate != null && notificationDate.isAfter(endDate.plusDays(1).atStartOfDay())) {
                return false;
            }
            if (onlyUnread && notification.isReadStatus()) {
                return false;
            }
            return true;
        };
    }
}