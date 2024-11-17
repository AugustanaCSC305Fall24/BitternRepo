module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires org.json;
    requires com.google.gson;
    requires tyrus.standalone.client;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;

}
