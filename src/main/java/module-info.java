module MainGUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires AuthLib;
    requires java.desktop;
    requires java.logging;
    requires org.apache.commons.lang3;
    requires gs.core;
    requires org.jfree.jfreechart;
    requires java.sql;

    opens pt.ipp.isep.dei.esoft.project.ui to javafx.graphics, javafx.fxml;
    opens pt.ipp.isep.dei.esoft.project.ui.gui.menu to javafx.graphics, javafx.fxml;
}