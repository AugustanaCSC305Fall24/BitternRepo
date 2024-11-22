module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires org.json;
    requires com.google.gson;
    requires tyrus.standalone.client;

    opens edu.augustana.Controllers to javafx.fxml;
    exports edu.augustana.Controllers;
    opens edu.augustana.Radio to javafx.fxml;
    exports edu.augustana.Radio;
    opens edu.augustana.Chat to javafx.fxml, com.google.gson;
    exports edu.augustana.Chat;
    opens edu.augustana.Input to javafx.fxml;
    exports edu.augustana.Input;
    opens edu.augustana.WebSocket to javafx.fxml;
    exports edu.augustana.WebSocket;


}
