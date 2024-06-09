package pt.ipp.isep.dei.esoft.project.ui.gui.menu;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pt.ipp.isep.dei.esoft.project.ui.gui.RegisterVehicleGUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.RegisterVehicleMaintenanceGUI;

/**
 * The type Main vfm menu gui.
 */
public class MainVFMMenuGUI  {


    private Pane content;

    /**
     * Instantiates a new Main vfm menu gui.
     *
     * @param content the content
     */
    public MainVFMMenuGUI(Pane content) {
        this.content = content;
    }


    /**
     * Gets vfm menu gui.
     *
     * @return the vfm menu gui
     */
    public GridPane getVFMMenuGUI() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Button btnRegisterVehicle = new Button("Register Vehicle");
        btnRegisterVehicle.setOnAction(e -> {
            RegisterVehicleGUI registerVehicleGUI = new RegisterVehicleGUI();
            content.getChildren().setAll(registerVehicleGUI.getRegisterVehicleGUI(content.getHeight(), content.getWidth()));
        });
        Button btnRegisterVehicleMaintenance = new Button("Register Vehicle maintenance");
        btnRegisterVehicleMaintenance.setOnAction(e -> {
            RegisterVehicleMaintenanceGUI registerVehicleMaintenanceGUI = new RegisterVehicleMaintenanceGUI();
            content.getChildren().setAll(registerVehicleMaintenanceGUI.getRegisterVehicleMaintenanceGUI());
        });

        grid.add(btnRegisterVehicle, 1, 2);
        grid.add(btnRegisterVehicleMaintenance, 0, 3);
        btnRegisterVehicle.fire();
        applyButtonAnimations();
        return grid;
    }


    private void applyButtonAnimations() {
        applyButtonAnimationsToNode(content);
    }

    private void applyButtonAnimationsToNode(Node node) {
        if (node instanceof Button && node.getStyleClass().contains("add-button")) {
            Button button = (Button) node;

            ScaleTransition st = new ScaleTransition(Duration.millis(300), button);
            st.setFromX(1);
            st.setFromY(1);
            st.setToX(1.1);
            st.setToY(1.1);
            st.setAutoReverse(true);

            button.setOnMouseEntered(event -> st.playFromStart());
            button.setOnMouseExited(event -> st.stop());
        } else if (node instanceof Parent) {
            Parent parent = (Parent) node;
            for (Node child : parent.getChildrenUnmodifiable()) {
                applyButtonAnimationsToNode(child);
            }
        }
    }



}