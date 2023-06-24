module com.weather.weather {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.net.http;
    requires org.json;
    requires java.sql;


    opens com.weather.weather to javafx.fxml;
    exports com.weather.weather;
}