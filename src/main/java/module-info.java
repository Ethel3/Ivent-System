 module com.example.netmart {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphicsEmpty;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires mysql.connector.java;
    requires java.desktop;
    requires com.google.gson;


    opens com.example.netmart to javafx.fxml;
    exports com.example.netmart;
    exports com.example.netmart.Controllers;
    exports com.example.netmart.Controllers.Admin;
    exports com.example.netmart.Controllers.Client;
    exports com.example.netmart.Models;
    exports com.example.netmart.Views;
    opens com.example.netmart.Controllers to javafx.fxml;
}