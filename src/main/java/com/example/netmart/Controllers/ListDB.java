package com.example.netmart.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.*;

public class ListDB<T> {
    private  int size;
    private String cat;
    private Class<T> clazz;

    public ListDB(String cat, Class<T> clazz){
        try{
            this.cat = cat;
            this.size = 0;
            this.clazz = clazz;

            // CONNECTING TO DB
            System.out.println("Connecting to DB");
            Connection connection = DatabaseConnection.getConnection();
            Statement stmt = connection.createStatement();
            System.out.println("Connected Successfully!");

            // CREATING TABLE IF NOT EXISTING
            String createTableQuery = "";
            if(clazz.equals(Goods.class)){
                createTableQuery = "CREATE TABLE IF NOT EXISTS " + cat + " (" +
                        "id INT UNIQUE, " +
                        "good_name VARCHAR(255), " +
                        "quantity INT, " +
                        "buying_price DECIMAL(10, 2), " +
                        "selling_price DECIMAL(10, 2), " +
                        "gross_price DECIMAL(10, 2), " +
                        "date VARCHAR(255)" +
                        ")";
            } else if(clazz.equals(IssuedGoods.class)){
                createTableQuery = "CREATE TABLE IF NOT EXISTS " + cat + " (" +
                        "id INT UNIQUE, " +
                        "good_name VARCHAR(255), " +
                        "quantity INT, " +
                        "buying_price DECIMAL(10, 2), " +
                        "selling_price DECIMAL(10, 2), " +
                        "gross_price DECIMAL(10, 2), " +
                        "issued_to VARCHAR(255)," +
                        "date VARCHAR(255)" +
                        ")";
            }
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(createTableQuery);

            // Get the current list size
            String countQuery = "SELECT COUNT(*) FROM " + cat;
            Statement countStatement = connection.createStatement();
            ResultSet countResult = countStatement.executeQuery(countQuery);
            if (countResult.next()) {
                size = countResult.getInt(1);
            }

        }catch (Exception ex){
            System.out.println("Error connecting to Database " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void add(T x) {
        addItem(x, size);
    }

    public void add(int index, T x) {
        if (index < 0 || index > size) {
            JOptionPane.showMessageDialog(null, "Index out of bounds" + index + " [" + size + "]");
//            throw new IndexOutOfBoundsException("Index out of bounds: " + index + " [" + size + "]");
        }
        addItem(x, index);
    }

    private  void addItem(T x, int index){
        try{
            // shifting all elements to the right of the index
            for(int i = size - 1; i >= index; i--){
                T currentItem = getItem(i);
                setItem(i + 1, currentItem);
            }
            String insertQuery = "";
            if(clazz.equals(Goods.class)){
                Goods goods = (Goods) x;
                if(index == size){
                    insertQuery = "INSERT INTO " + cat + "(id, good_name, quantity, buying_price, selling_price, gross_price, date) VALUES ("
                            + index + ", '" + goods.getGood_name() + "', " + goods.getQuantity() + ", " + goods.getBuying_price() + ", "
                            + goods.getSelling_price() + ", " + goods.getGross_price() + ", '" + goods.getDate()
                            + "')";
                }
                else{
                    insertQuery = "UPDATE " + cat + " SET " + goods.sqlStr() + " WHERE id = " + index;
                }
            }

            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.executeUpdate();
            size++;

        } catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public T get(int index){
        if(index < 0 || index >= size){
            JOptionPane.showMessageDialog(null, "Index out of bounds: " + index);
//            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return getItem(index);
    }
    public T remove() {
        return removeItem(size);
    }
    public T remove(int index) {
        return removeItem(index);
    }
    private T removeItem(int index) {
        if (index < 0 || index >= size) {
            JOptionPane.showMessageDialog(null, "Index out of bounds: " + index);
//            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        T removedItem = getItem(index);
        try {
//             Shift all elements to the left of the index
            for (int i = index; i < size - 1; i++) {
                T currentItem = getItem(i + 1);
                setItem(i, currentItem);
            }

            // Remove the last item
            String deleteQuery = "DELETE FROM " + cat + " WHERE id = " + (size - 1);
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.executeUpdate();
            size--;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return removedItem;
    }

    public boolean remove(T element) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (getItem(i).equals(element)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            JOptionPane.showMessageDialog(null, "Index out of bounds: " + index);
//            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        setItem(index, element);
    }
    private void setItem(int index, T goods){
        try{
            String updateQuery = "";
            if(clazz.equals(Goods.class)){

                Goods x = (Goods) goods;
                System.out.println("GoodsID = " + x.getGood_id());
                if(index == size){
                    updateQuery = "INSERT INTO " + cat + "(id, good_name, quantity, buying_price, selling_price, gross_price, date) VALUES (" +
                            + index + ", '" + x.getGood_name() + "', " + x.getQuantity() + ", '"
                            + x.getBuying_price() + "', " + x.getSelling_price() + ", " + x.getGross_price() + ", '" + x.getDate()
                            + "')";
                }else {
                    updateQuery = "UPDATE " + cat + " SET " + x.sqlStr() + " WHERE id = " + index;
                }
            }
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public int size(){
        return size;
    }
    @SuppressWarnings("unchecked")
    private T getItem(int index) {
        T item = null;
        try {
            String selectQuery = "SELECT * FROM " + cat + " WHERE id = " + index;
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                try {
                    if (clazz.equals(Goods.class)) {

                        item = (T) new Goods(
                                resultSet.getInt("id"),
                                resultSet.getString("good_name"),
                                resultSet.getInt("quantity"),
                                resultSet.getDouble("selling_price"),
                                resultSet.getDouble("buying_price"),
                                resultSet.getInt("gross_price"),
                                resultSet.getString("date"));

                    } else if(clazz.equals(IssuedGoods.class)){
                        item = (T) new IssuedGoods(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("issued_to"),
                                resultSet.getDate("issued_date"),
                                resultSet.getInt("quantity"),
                                resultSet.getDouble("unit_cost"),
                                resultSet.getString("category"));
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public ObservableList<T> getItems(){
        ObservableList<T> goods = FXCollections.observableArrayList();
        try{
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + cat);

            if (clazz.equals(Goods.class)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("good_name");
                    int quantity = resultSet.getInt("quantity");
                    double selling_price = resultSet.getDouble("selling_price");
                    double buying_price = resultSet.getDouble("buying_price");
                    double gross_price = resultSet.getInt("gross_price");
                    String date = resultSet.getString("date");
                    if (name != null) {
                        Goods item = new Goods(id, name,  quantity, selling_price, buying_price, gross_price, date);
                        goods.add((T) item);
                    }
                }
            } else if (clazz.equals(IssuedGoods.class)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String issued_to = resultSet.getString("issued_to");
                    int quantity = resultSet.getInt("quantity");
                    double unit_cost = resultSet.getDouble("unit_cost");
                    Date issued_date = resultSet.getDate("issued_date");
                    String category = resultSet.getString("category");
                    if (name != null) {
                        IssuedGoods item = new IssuedGoods(id, name, issued_to, issued_date, quantity, unit_cost, category);
                        goods.add((T) item);
                    }
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return goods;
    }
    public Class<T> getGenericType() {
        return clazz;
    }
}
