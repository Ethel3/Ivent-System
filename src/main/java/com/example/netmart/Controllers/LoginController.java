package com.example.netmart.Controllers;

import com.example.netmart.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {
    public TextField name_field;
    public TextField password_field;
    public Button login_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(event -> onLogin());
    }

    public void onLogin(){
        Stage stage = (Stage) error_lbl.getScene().getWindow();

        if(!name_field.getText().isBlank() && !password_field.getText().isBlank()){
            Model.getInstance().evaluateAdminCred(name_field.getText(), password_field.getText());
            if(Model.getInstance().getAdminLoginSuccessFlag()){
                Model.getInstance().getViewFactory().showMainWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
            }
        }else {
            name_field.setText("");
            password_field.setText("");
            error_lbl.setText("Wrong credentials!");
        }
    }
}
