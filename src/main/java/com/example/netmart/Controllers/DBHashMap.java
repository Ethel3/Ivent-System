package com.example.netmart.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class DBHashMap<K, V> {
    private String cat;

    public DBHashMap(String cat){
        try{
            this.cat = cat;
            // CONNECTING TO DB
            Connection connection = DatabaseConnection.getConnection();

            // CREATING TABLE IF NOT EXISTING
            System.out.println("Creating Vendor table...");
            String createTable = "CREATE TABLE IF NOT EXISTS " + cat + " (" +
                    "key_hash INT UNIQUE, " +
                    "key_value VARCHAR(255) UNIQUE, " +
                    "value JSON " +
                    ")";
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(createTable);
            System.out.println("Created successfully...!");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public HashMap<String, String> put(String key, HashMap<String, String> value){
        int keyHash = key.hashCode();
        String keyValue = key.toString();
        String valueStr = toJSON(value);

        try {
            if (containsKey(key)) {
                String updateQuery = "UPDATE " + cat + " SET key = ?, value = ? WHERE key_hash = ?";
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, key);
                updateStatement.setString(2, valueStr);
                updateStatement.setInt(3, keyHash);
                updateStatement.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO " + cat + " (key_hash, key_value, value) VALUES (?, ?, ?)";
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, keyHash);
                insertStatement.setString(2, keyValue);
                insertStatement.setString(3, valueStr);
                insertStatement.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return value;
    }

    public HashMap<String, String> get(String key) {
        int keyHash = key.hashCode();

        try {
            String selectQuery = "SELECT value FROM " + cat + " WHERE key_hash = " + keyHash;
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                String valueStr = resultSet.getString("value");
                HashMap<String, String> value = toHashMap(valueStr);
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public HashMap<String, String> remove(String key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int keyHash = key.hashCode();
        HashMap<String, String> removedValue = null;

        try {
            // Get the value associated with the key
            removedValue = get(key);

            if (removedValue != null) {
                // Remove the entry from the table
                String deleteQuery = "DELETE FROM " + cat + " WHERE key_hash = ?";
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, keyHash);
                deleteStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return removedValue;
    }

    public boolean containsKey(String key) {
        int keyHash = key.hashCode();

        try {
            String selectQuery = "SELECT COUNT(*) FROM " + cat + " WHERE key_hash = " + keyHash;
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ObservableList<String> getVendors(boolean onlyNames) {
        return _getVendors(onlyNames);
    }

    public ObservableList<Vendor> getVendors() {
        return _getVendors(false);
    }

    private ObservableList _getVendors(boolean onlyNames) {
        System.out.println("Cat: " + cat);
        ObservableList<Vendor> vendors = FXCollections.observableArrayList();
        ObservableList<String> vendorsNames = FXCollections.observableArrayList();
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + cat);

            while (resultSet.next()) {
                String key_hash = resultSet.getString("key_hash");
                String key = resultSet.getString("key_value");
                String value = resultSet.getString("value");
                HashMap<String, String> _vendor = toHashMap(value);
                vendors.add(new Vendor(_vendor.get("name"), _vendor.get("location"), _vendor.get("email"), _vendor.get("phone"), _vendor.get("address")));
                vendorsNames.add(_vendor.get("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return onlyNames ? vendorsNames : vendors;
    }

    @SuppressWarnings("unchecked")
    private HashMap<String, String> toHashMap(String json) {
        Gson gson = new Gson();
        HashMap<String, String> hashMapFromJson = gson.fromJson(json, HashMap.class);
        return hashMapFromJson;
    }

    private String toJSON(HashMap<String, String> hashMap) {
        Gson gson = new Gson();
        String json = gson.toJson(hashMap);
        return json;
    }
}
