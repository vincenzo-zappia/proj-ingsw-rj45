module proj.ingsw.rj {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.xml;

    opens it.polimi.ingsw.view.gui to javafx.fxml;
    exports it.polimi.ingsw.view.gui;
}