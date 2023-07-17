package com.example.netmart.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewIssuedGoodsController {
//    @FXML
//    private Button add_issue_btn;

    @FXML
    private TableView<IssuedGoods> issued_good_table;
    @FXML
    private TableColumn<IssuedGoods, String> issue_cat;

    @FXML
    private TableColumn<IssuedGoods, String> issue_date;

    @FXML
    private TableColumn<IssuedGoods, String> issue_good_name;

    @FXML
    private TableColumn<IssuedGoods, String> issue_gross_price;

    @FXML
    private TableColumn<IssuedGoods, Integer> issue_id;

    @FXML
    private TableColumn<IssuedGoods, Integer> issue_quantity;

    @FXML
    private TableColumn<IssuedGoods, String> issued_to;

    @FXML
    private Button remove_issue_btn;

    public static ListDB<IssuedGoods> issuedGoods = new ListDB<>("issued_goods", IssuedGoods.class);
    public static ObservableList<IssuedGoods> issuedGoodsList = FXCollections.observableArrayList();


    public void initialize() {
        // Set the cell value factories
        issue_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        issue_good_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        issue_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        issue_cat.setCellValueFactory(new PropertyValueFactory<>("category"));
        issued_to.setCellValueFactory(new PropertyValueFactory<>("issuedTo"));
        issue_date.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        issue_gross_price.setCellValueFactory(cellData -> {
            IssuedGoods item = cellData.getValue();
            int quantity = item.getQuantity();
            double unitCost = item.getUnitCost();
            double subTotal = quantity * unitCost;
            return new SimpleStringProperty(String.valueOf(subTotal));
        });

        // fetch data
        issuedGoodsList = issuedGoods.getItems();
        issued_good_table.setItems(issuedGoodsList);

//        addIssuedItemBtn.setOnAction(e -> openIssueItemPopup());
        remove_issue_btn.setOnAction(e -> removeIssuedItem());
    }

    private void removeIssuedItem() {
        IssuedGoods item = issued_good_table.getSelectionModel().getSelectedItem();
        if (item != null) {
            issuedGoodsList.remove(item.getId());
            issuedGoods.remove(item.getId());
        } else {
            issuedGoodsList.remove(0);
            issuedGoods.remove(0);
        }
        updateIndexes();
    }
    public static void addIssuedItem(IssuedGoods item) {
        issuedGoods.add(item);
    }

    private void updateIndexes() {
        for (int i = 0; i < issuedGoodsList.size(); i++) {
            issuedGoodsList.get(i).setId(i);
        }
    }
}
