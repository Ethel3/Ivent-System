package com.example.netmart.Models;

import com.example.netmart.Controllers.Admin.AdminController;
import com.example.netmart.Controllers.DatabaseConnection;
import com.example.netmart.Views.ViewFactory;

import java.sql.ResultSet;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseConnection databaseConnection;

    private final AdminController adminController;


    private boolean adminLoginSuccessFlag;

    private Model(){
        this.viewFactory =  new ViewFactory();
        this.databaseConnection = new DatabaseConnection();

        this.adminLoginSuccessFlag = false;
        this.adminController = new AdminController("", "");
    }
    public static synchronized Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory(){
        return viewFactory;
    }

    public DatabaseConnection getDatabaseConnection() {return databaseConnection;}

    public boolean getAdminLoginSuccessFlag() {
        return this.adminLoginSuccessFlag;
    }

    public void setAdminLoginSuccessFlag(boolean flag) {
        this.adminLoginSuccessFlag = flag;
    }

    public AdminController getAdminController(){
        return adminController;
    }

    public void evaluateAdminCred(String name, String password){
        ResultSet resultSet = databaseConnection.getAdminData(name, password);
        try {
            if(resultSet.next()){
                this.adminController.nameProperty().set(resultSet.getString("name"));
                this.adminController.passwordProperty().set(resultSet.getString("password"));
                this.adminLoginSuccessFlag = true;
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
