package com.example.netmart.Views;

import com.example.netmart.Controllers.ChangeViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    // TO HELP KNOW THE MENU WHICH IS SELECTED
    private final StringProperty selectedMenuItem;
    // DASHBOARD VIEW
    private AnchorPane dashboardView;
    private AnchorPane navbarView;
    // VIEW VENDORS VIEW
    private AnchorPane viewVendorsView;
    // ADD GOODS
//    private AnchorPane addGoodsView;
    // VIEW GOODS
    private AnchorPane viewGoodsView;
    // VIEW BILLS
    private AnchorPane viewBillsView;
    // ISSUED GOODS
    private AnchorPane IssuedGoodsView;
    // VIEW ISSUED GOODS
//    private AnchorPane viewIssuedGoodsView;

    // CONSTRUCTOR
    public ViewFactory(){
        this.selectedMenuItem = new SimpleStringProperty("");
    }
// GETTING SELECTED MENU ITEM
    public StringProperty getSelectedMenuItem() {
        return selectedMenuItem;
    }

    // GETTING DASHBOARD VIEW
    public AnchorPane getDashboardView(){
        if(dashboardView == null){ // PREVENTS LOADING DASHBOARD MORE THAN ONCE
            try{
                dashboardView = new FXMLLoader(getClass().getResource("/FXML/Dashboard.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    // GETTING VIEW VENDORS VIEW
    public AnchorPane getViewVendorsView() {
        if(viewVendorsView == null){ // PREVENTS LOADING DASHBOARD MORE THAN ONCE
            try{
                viewVendorsView = new FXMLLoader(getClass().getResource("/FXML/viewVendors.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return viewVendorsView;
    }

    // GETTING ADD GOODS VIEW
//    public AnchorPane getAddGoodsView() {
//        if(addGoodsView == null){ // PREVENTS LOADING DASHBOARD MORE THAN ONCE
//            try{
//                addGoodsView = new FXMLLoader(getClass().getResource("/FXML/addGoods.fxml")).load();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return addGoodsView;
//    }

    // GETTING VIEW GOODS VIEW
    public AnchorPane getViewGoodsView() {
        if(viewGoodsView == null){ // PREVENTS LOADING DASHBOARD MORE THAN ONCE
            try{
                viewGoodsView = new FXMLLoader(getClass().getResource("/FXML/viewGoods.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return viewGoodsView;
    }

    // GETTING VIEW BILLS VIEW
    public AnchorPane getViewBillsView() {
        if(viewBillsView == null){ // PREVENTS LOADING DASHBOARD MORE THAN ONCE
            try{
                viewBillsView = new FXMLLoader(getClass().getResource("/FXML/viewBills.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return viewBillsView;
    }

    // GETTING ISSUED GOODS VIEW
    public AnchorPane getIssuedGoodsView() {
        if(IssuedGoodsView == null){ // PREVENTS LOADING DASHBOARD MORE THAN ONCE
            try{
                IssuedGoodsView = new FXMLLoader(getClass().getResource("/FXML/issuedGoods.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return IssuedGoodsView;
    }

    // GETTING VIEW ISSUED GOODS VIEW
//    public AnchorPane getViewIssuedGoodsView() {
//        if(viewIssuedGoodsView == null){ // PREVENTS LOADING DASHBOARD MORE THAN ONCE
//            try{
//                viewIssuedGoodsView = new FXMLLoader(getClass().getResource("/FXML/viewIssuedGoods.fxml")).load();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return viewIssuedGoodsView;
//    }

    // TO DISPLAY LOGIN WINDOW FOR AUTHENTICATION
    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/login.fxml"));
        createStage(loader);
    }

    // TO DISPLAY MAIN WINDOW
    public void showMainWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Window.fxml"));
        ChangeViewController changeViewController = new ChangeViewController(); // CONTROLLER FOR THE WINDOW FXML SINCE THAT BORDER PANE DOESN'T HAVE A CONTROLLER
        loader.setController(changeViewController);
        createStage(loader);

    }
    // CREATE STAGE METHOD
    private void createStage(FXMLLoader loader){
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Net Mart");
        stage.setResizable(false);
        stage.show();
    }

    // CLOSE STAGE METHOD
    public void closeStage(Stage stage){
        stage.close();
    }
}
