module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires org.json;
//
    opens edu.augustana to javafx.fxml;
 //   exports edu.augustana;
    exports edu.augustana.Controllers;
    opens edu.augustana.Controllers to javafx.fxml;
    exports edu.augustana.Chat;
    opens edu.augustana.Chat to javafx.fxml;
    exports edu.augustana.Radio;
    opens edu.augustana.Radio to javafx.fxml;
    exports edu.augustana.Input;
    opens edu.augustana.Input to javafx.fxml;

}
