package com.example.netmart.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ViewGoodsController {
    public ChoiceBox<String> category;
    public Button remove_btn;
    public Button add_good_btn;
    public Button issue_btn;
    public Label logic;


    @FXML
    private TableView<Goods> view_goods_table;
    @FXML

    public TableColumn<Goods, Integer> good_id;

    @FXML
    private TableColumn<Goods, String> good_date;

    @FXML
    private TableColumn<Goods, Integer> good_buying_price;

    @FXML
    private TableColumn<Goods, Integer> good_gross_price;

    @FXML
    private TableColumn<Goods, String> good_name;

    @FXML
    private TableColumn<Goods, Integer> good_qty;

    @FXML
    private TableColumn<Goods, Integer> good_selling_price;

    private static final String[] categories = {"Beverages", "Bakery", "Canned", "Dairy", "Dry",
            "Frozen", "Meat", "Produce", "Cleaner", "Paper", "Personal"};

    String selectedCategory = "All";
    public static Goods selectedItem = Goods.nullItem();
    public static ObservableList<Goods> listGoods = FXCollections.observableArrayList();
    protected static  Goods toAdd = Goods.nullItem();
    protected static IssuedGoods toIssue = IssuedGoods.nullItem();


    // STACKS INSTANCE
    public static StackDB beverages = new StackDB( "beverages");
    public static ObservableList<Goods> beverageItems;
    public static StackDB bakery = new StackDB( "bakery");
    public static ObservableList<Goods> bakeryItems;

    public static StackDB canned = new StackDB( "canned");
    public static ObservableList<Goods> cannedItems;

    public static StackDB dairy = new StackDB( "dairy");
    public static ObservableList<Goods> dairyItems;


    // QUEUE INSTANCE
    public static QueueDB dry = new QueueDB(3, "dry");
    public static ObservableList<Goods> dryItems;

    public static QueueDB frozen = new QueueDB(10, "frozen");
    public static ObservableList<Goods> frozenItems;

    public static QueueDB meat = new QueueDB(10, "meat");
    public static ObservableList<Goods> meatItems;


    // LIST INSTANCE
    public static ListDB<Goods> produce = new ListDB<Goods>("produce", Goods.class);
    public static ObservableList<Goods> produceItems;

    public static ListDB cleaner = new ListDB("cleaner", Goods.class);
    public static ObservableList<Goods> cleanerItems;

    public static ListDB paper = new ListDB("paper", Goods.class);
    public static ObservableList<Goods> paperItems;

    public static ListDB personal = new ListDB("personal", Goods.class);
    public static ObservableList<Goods> personalItems;


    public void initialize() {

        Integer lastId = null;
        int size = 0;
//        good_id.setCellValueFactory(new PropertyValueFactory<>("good_id"));
        good_name.setCellValueFactory(new PropertyValueFactory<>("good_name"));
        good_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        good_buying_price.setCellValueFactory(new PropertyValueFactory<>("buying_price"));
        good_selling_price.setCellValueFactory(new PropertyValueFactory<>("selling_price"));
        good_gross_price.setCellValueFactory(new PropertyValueFactory<>("gross_price"));
        good_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        category.getItems().addAll(categories);
        listGoods = getCategoryData("beverages");
        view_goods_table.setItems(listGoods);

//        System.out.println("BakeryItems: " + bakeryItems);

        // set the default category and add a listener
        category.setValue("All");
        category.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
//                listGoods = getCategoryData(newValue.toLowerCase());
//                view_goods_table.setItems(listGoods);
               handleCategoryChange(newValue);
            }
        });

        // Show all goods in the table
        view_goods_table.setItems(listGoods);

        // listen to TableView's selection changes
        view_goods_table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedItem = newValue;
            } else {
                // No item is selected
                selectedItem = Goods.nullItem();
            }
        });

        // ADD GOOD BTN
        add_good_btn.setOnAction(event -> addGood());

        // ADD VENDOR POP UP
        issue_btn.setOnAction(event -> vendorOptionModal());
        // REMOVE ITEMS
        remove_btn.setOnAction(event -> {
            String cat = category.getValue();
            String _cat = cat.toLowerCase().split("/")[0].split(" ")[0];
            int _toRemove = selectedItem.getGood_id();
            switch (_cat) {
                case "beverages":
                    beverages.pop(_cat);
                    beverageItems.remove(0);
                    break;
                case "bakery":
                    bakery.pop(_cat);
                    bakeryItems.remove(0);
                    break;
                case "canned":
                    canned.pop(_cat);
                    cannedItems.remove(0);
                    break;
                case "dairy":
                    dairy.pop(_cat);
                    dairyItems.remove(0);
                    break;
                case "dry":
                    dry.dequeue(_cat);
                    dryItems.remove(0);
                    break;
                case "frozen":
                    frozen.dequeue(_cat);
                    frozenItems.remove(0);
                    break;
                case "meat":
                    meat.dequeue(_cat);
                    meatItems.remove(0);
                    break;
                case "produce":
                    if (_toRemove < 0) {
                        produce.remove();
                        produceItems.remove(0);
                        updateIndexes(produceItems);
                    } else {
                        produce.remove(_toRemove);
                        produceItems.remove(0);
                        updateIndexes(produceItems);
                    }
                case "cleaner":
                    if (_toRemove < 0) {
                        cleaner.remove();
                        cleanerItems.remove(0);
                        updateIndexes(cleanerItems);
                    } else {
                        cleaner.remove(_toRemove);
                        cleanerItems.remove(0);
                        updateIndexes(cleanerItems);
                    }
                    break;
                case "paper":
                    if (_toRemove < 0) {
                        paper.remove();
                        paperItems.remove(0);
                        updateIndexes(paperItems);
                    } else {
                        paper.remove(_toRemove);
                        paperItems.remove(0);
                        updateIndexes(paperItems);
                    }
                    break;
                case "personal":
                    if (_toRemove < 0) {
                        personal.remove();
                        personalItems.remove(0);
                        updateIndexes(personalItems);
                    } else {
                        personal.remove(_toRemove);
                        personalItems.remove(0);
                        updateIndexes(personalItems);
                    }
                    break;
                default:
                    System.out.println("No Category Selected");
                    JOptionPane.showMessageDialog(null, "Category not supported!");
                    break;
            }
        });
    }


    public ObservableList<Goods> getCategoryData(String category){
        switch (category){
            case "beverages":
                if(beverageItems == null){
                    beverageItems = getData(category);
                    listGoods.addAll(beverageItems);
                }
                return beverageItems;
            case "bakery":
                if(bakeryItems == null){
                    bakeryItems = getData(category);
                    listGoods.addAll(bakeryItems);
//                    System.out.println("In Bake " + bakeryItems);

                }
                return bakeryItems;
            case "dry":
                if(dryItems == null){
                    dryItems = getData(category);
                    listGoods.addAll(dryItems);

                }
                return dryItems;
            case "frozen":
                if(frozenItems == null){
                    frozenItems = getData(category);
                    listGoods.addAll(frozenItems);

                }
                return frozenItems;
            case "meat":
                if(meatItems == null){
                    meatItems = getData(category);
                    listGoods.addAll(meatItems);

                }
                return meatItems;
            case "produce":
                if(produceItems == null) {
                    produceItems = getData(category);
                    listGoods.addAll(produceItems);

                    return produceItems;
                }
            case "canned":
                if(cannedItems == null){
                    cannedItems = getData(category);
                    listGoods.addAll(cannedItems);

                }
                return cannedItems;
            case "dairy":
                if(dairyItems == null){
                    dairyItems = getData(category);
                    listGoods.addAll(dairyItems);

                }
                return dairyItems;
            case "cleaner":
                if(cleanerItems == null){
                    cleanerItems = getData(category);
                    listGoods.addAll(cleanerItems);

                }
                return cleanerItems;
            case "paper":
                if(paperItems == null){
                    paperItems = getData(category);
                    listGoods.addAll(paperItems);

                }
                return paperItems;
            case "personal":
                if(personalItems == null){
                    personalItems = getData(category);
                    listGoods.addAll(personalItems);

                }
                return personalItems;
            default:
                JOptionPane.showMessageDialog(null, "No such category!");
        }
        return null;
    }
    private void addGood(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/addGoodModal.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add New Good");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void vendorOptionModal(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/issuedGoodModal.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Choose Vendor");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public ObservableList<Goods> getData(String category) {
//        System.out.println("Fetching for: " + category);
        ObservableList<Goods> goods = FXCollections.observableArrayList();
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = String.format("SELECT * FROM %s", category);
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString("good_name") != null) {
                    goods.add(new Goods(
                            rs.getInt("id"),
                            rs.getString("good_name"),
                            rs.getInt("quantity"),
                            rs.getDouble("buying_price"),
                            rs.getDouble("selling_price"),
                            rs.getDouble("gross_price"),
                            rs.getString("date")
                    ));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        System.out.println("Goods: " + goods);
        return goods;
    }



    private void handleCategoryChange(String cat) {
        selectedCategory = cat;
        view_goods_table.setItems(getCategoryData(cat.toLowerCase().split("/")[0].split(" ")[0]));
    }

    private void updateIndexes(ObservableList<Goods> goods) {
        for (int i = 0; i < goods.size(); i++) {
            goods.get(i).setGood_id(i);
        }
    }

    public void sell() {
        sell(selectedItem);
    }

    public static void sell(Goods selectedItem) {
        if (selectedItem == null) {
//            selectedItem = searchForItem(toIssue.getName());
        }
        Goods updatedItem = selectedItem;
//        updatedItem.setSold((selectedItem.getSold() + toIssue.getQuantity()));
        updatedItem.setQuantity((selectedItem.getQuantity() - toIssue.getQuantity()));
        String table = selectedItem.getGood_name().toLowerCase().split("/")[0].split(" ")[0];
        switch (table) {
            case "beverages" -> {
                beverageItems.set(selectedItem.getGood_id(), updatedItem);
                table = "beverages";
            }
            case "bakery" -> {
                bakeryItems.set(selectedItem.getGood_id(), updatedItem);
                table = "bakery";
            }
            case "canned" -> {
                cannedItems.set(selectedItem.getGood_id(), updatedItem);
                table = "canned";
            }
            case "dairy" -> {
                dairyItems.set(selectedItem.getGood_id(), updatedItem);
                table = "dairy";
            }
            case "dry" -> {
                dryItems.set(selectedItem.getGood_id(), updatedItem);
                table = "dry";
            }
            case "frozen" -> {
                frozenItems.set(selectedItem.getGood_id(), updatedItem);
                table = "frozen";
            }
            case "meat" -> {
                meatItems.set(selectedItem.getGood_id(), updatedItem);
                table = "meat";
            }
            case "produce" -> {
                produceItems.set(selectedItem.getGood_id(), updatedItem);
                table = "produce";
            }
            case "cleaners" -> {
                cleanerItems.set(selectedItem.getGood_id(), updatedItem);
                table = "cleaners";
            }
            case "paper" -> {
                paperItems.set(selectedItem.getGood_id(), updatedItem);
                table = "paper";
            }
            case "personal" -> {
                personalItems.set(selectedItem.getGood_id(), updatedItem);
                table = "personal";
            }
            default -> {
            }
        }
        for (int i = 0; i < listGoods.size(); i++) {
            if (listGoods.get(i) == selectedItem) {
                listGoods.set(i, updatedItem);
                break;
            }
        }

        ViewIssuedGoodsController.issuedGoodsList.add(0, toIssue);
        ViewIssuedGoodsController.issuedGoods.add(0, toIssue);

//        DBConnection dbConnection = DBConnection.getInstance();
//        Connection connection = DatabaseConnection.getConnection();
//        connection.execSQL("UPDATE " + table + " SET sold = " + (selectedItem.getSold() + toIssue.getQuantity()) + ", quantity = "
//                        + (selectedItem.getQuantity() - toIssue.getQuantity()) + " WHERE id = " + selectedItem.getGood_id());
    }


}

