package com.example.netmart.Controllers;

import com.example.netmart.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LeftMenuController implements Initializable {
    public Button dashboard_btn;
    public Button view_vendor_btn;
    public Button add_goods_btn;
    public Button view_goods_btn;
    public Button view_bills_btn;
    public Button issued_goods_btn;
    public Button view_issued_goods_btn;
    public Button logout_btn;
    public Button report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        dashboard_btn.setOnAction(event -> onDashboard());
        view_vendor_btn.setOnAction(event -> onViewVendors());
//        add_goods_btn.setOnAction(event -> onAddGoods());
        view_goods_btn.setOnAction(event -> onViewGoods());
        view_bills_btn.setOnAction(event -> onViewBills());
        issued_goods_btn.setOnAction(event -> onIssuedGoods());
//        view_issued_goods_btn.setOnAction(event -> onViewIssuedGoods());
    }

    private void onDashboard(){
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("Dashboard");
    }

    private void onViewVendors(){
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("viewVendors");
    }
    private void onAddGoods(){
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("addGoods");
    }
    private void onViewGoods(){Model.getInstance().getViewFactory().getSelectedMenuItem().set("viewGoods");}
    private void onViewBills(){Model.getInstance().getViewFactory().getSelectedMenuItem().set("viewBills");}
    private void onIssuedGoods(){Model.getInstance().getViewFactory().getSelectedMenuItem().set("issuedGoods");}
//    private void onViewIssuedGoods(){Model.getInstance().getViewFactory().getSelectedMenuItem().set("viewIssuedGoods");}
}
