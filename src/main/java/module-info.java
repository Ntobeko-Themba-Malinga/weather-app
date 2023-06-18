module com.weather.weather {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.net.http;
    requires org.json;


    opens com.weather.weather to javafx.fxml;
    exports com.weather.weather;
}