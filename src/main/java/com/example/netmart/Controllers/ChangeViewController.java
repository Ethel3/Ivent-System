package com.example.netmart.Controllers;

import com.example.netmart.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeViewController implements Initializable {
    // MAKING REFERENCE TO THE CONTROLLER OF THE BOARDER PANE IN THE WINDOW FXML
    public BorderPane dashboard_container;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getSelectedMenuItem().addListener(((observableValue, oldVal, newVal) -> {
            switch (newVal){
                case "viewVendors" -> dashboard_container.setCenter(Model.getInstance().getViewFactory().getViewVendorsView());
//                case "addGoods" -> dashboard_container.setCenter(Model.getInstance().getViewFactory().getAddGoodsView());
                case "viewGoods" -> dashboard_container.setCenter(Model.getInstance().getViewFactory().getViewGoodsView());
                case "viewBills" -> dashboard_container.setCenter(Model.getInstance().getViewFactory().getViewBillsView());
                case "issuedGoods" -> dashboard_container.setCenter(Model.getInstance().getViewFactory().getIssuedGoodsView());
//                case "viewIssuedGoods" -> dashboard_container.setCenter(Model.getInstance().getViewFactory().getViewIssuedGoodsView());
                default -> dashboard_container.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        }));

    }
}
