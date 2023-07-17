package com.example.netmart.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ViewVendorsController{
    @FXML
    private TableView<Vendor> vendor_table;
    @FXML
    private TableColumn<Vendor, String> v_address;

    @FXML
    private TableColumn<Vendor, String> v_email;

    @FXML
    private TableColumn<Vendor, String> v_location;

    @FXML
    private TableColumn<Vendor, String> v_name;

    @FXML
    private TableColumn<Vendor, String> v_number;

    @FXML
    private Button vendor_add_btn;
    @FXML
    private Button vendor_remove_btn;

    private String selectedKey = "";
    public static ObservableList<String> vendorNames = FXCollections.observableArrayList();

    private DBHashMap<String, HashMap<String, String>> vendors = new DBHashMap<>("vendors");

    ObservableList<Vendor> vendorsList = FXCollections.observableArrayList();
    protected static Vendor toAdd = Vendor.nullVendor();


    public void initialize() {
        v_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        v_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        v_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        v_number.setCellValueFactory(new PropertyValueFactory<>("number"));
        v_address.setCellValueFactory(new PropertyValueFactory<>("address"));

        // fetch all vendors
        fetchVendors();
        fetchVendorNames();

        // Set the items in the ListView
        vendor_table.setItems(vendorsList);

        // listen to TableView's selection changes
        vendor_table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedKey = newValue.getName();
            } else {
                selectedKey = "";
            }
        });

        vendor_add_btn.setOnAction(e -> addVendorModal());
        vendor_remove_btn.setOnAction(e -> removeVendor(selectedKey));
    }

    public void addVendorModal(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/addVendor.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add New Vendor");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            // Show the popup and wait for it to be closed
            stage.showAndWait();
            if (!toAdd.isNull())
                addVendor(toAdd.getName(), toAdd);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void addVendor(String name, Vendor vendor) {
        vendors.put(name, vendor.toHashMap());
        vendorsList.removeIf((element) -> element.getName().hashCode() == name.hashCode());
        vendorsList.add(vendor);
        vendorNames.add(name);
        toAdd = Vendor.nullVendor();
    }

    private void removeVendor(String name) {
        vendors.remove(name);
        vendorsList.removeIf((element) -> element.getName() == name);
        vendorNames.remove(name);
    }

    private void fetchVendors() {
        vendorsList = vendors.getVendors();
//         vendorsList.addAll(Vendor.getVendors());
    }

    public void fetchVendorNames() {
        vendorNames = vendors.getVendors(true);
    }
}
