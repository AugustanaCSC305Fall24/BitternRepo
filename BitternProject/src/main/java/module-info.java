module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires org.json;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
}
