package com.example.netmart.Controllers.Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AdminController {
    private final StringProperty _name; //firstName
    private final StringProperty _password; // lastName

    public AdminController(String name, String password) {
        this._name = new SimpleStringProperty(this, "name", name);
        this._password = new SimpleStringProperty(this, "password", password);
    }

    public StringProperty nameProperty(){return _name;} // firstName
    public StringProperty passwordProperty(){return _password;} // lastName
}
