package com.example.netmart.Controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class AddVendor {
    public Button add_btn;
    public TextField vendor_name;
    public TextField vendor_location;
    public TextField vendor_email;
    public TextField vendor_phone;
    public TextField vendor_address;

    public void initialize() {
        add_btn.setOnAction(this::handleAddButtonClick);
    }

    @FXML
    private void handleAddButtonClick(ActionEvent event) {
        ViewVendorsController.toAdd.setName(vendor_name.getText());
        ViewVendorsController.toAdd.setLocation(vendor_location.getText());
        ViewVendorsController.toAdd.setEmail(vendor_email.getText());
        ViewVendorsController.toAdd.setNumber(vendor_phone.getText());
        ViewVendorsController.toAdd.setAddress(vendor_address.getText());


        // Close the window
        if (!ViewVendorsController.toAdd.isNull()) {
            Stage stage = (Stage) vendor_name.getScene().getWindow();
            stage.close();
        }

    }
}
