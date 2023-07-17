package com.example.netmart.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewBillsController {

    @FXML
    private TableColumn<?, ?> bill_date;

    @FXML
    private TableColumn<?, ?> bill_id;

    @FXML
    private TableColumn<?, ?> bill_number;

    @FXML
    private Button bill_save_btn;

    @FXML
    private TableView<?> bill_table;

    @FXML
    private TableColumn<?, ?> bill_total_amout;

}
