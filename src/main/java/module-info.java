//Ethel Akosua Akrasi - 10917233
 module com.example.ivent {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphicsEmpty;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires mysql.connector.java;
    requires java.desktop;
    requires com.google.gson;


    opens com.example.ivent to javafx.fxml;
    exports com.example.ivent;
    exports com.example.ivent.Controllers;
    exports com.example.ivent.Controllers.Admin;
    exports com.example.ivent.Controllers.Client;
    exports com.example.ivent.Models;
    exports com.example.ivent.Views;
    opens com.example.ivent.Controllers to javafx.fxml;
}