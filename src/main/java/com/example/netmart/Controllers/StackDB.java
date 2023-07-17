package com.example.netmart.Controllers;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StackDB {
    Integer size;
    Integer lastId = null;
//    private static String cat;

    public StackDB(String cat){
        System.out.println("Testing");
        try{
             // CONNECTING TO DB
            Connection connection = DatabaseConnection.getConnection();
            Statement stmt = connection.createStatement();

            // CREATING TABLE IF NOT EXISTING
            String createTable = "CREATE TABLE IF NOT EXISTS " + cat + " (" +
                "id INT UNIQUE, " +
                "good_name VARCHAR(255), " +
                "quantity INT, " +
                "buying_price DECIMAL(10, 2), " +
                "selling_price DECIMAL(10, 2), " +
                "gross_price DECIMAL(10, 2), " +
                "date VARCHAR(255)" +
                ")";
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(createTable);

            // GETTING SIZE
            if(size == null){
                ResultSet rs = stmt.executeQuery(String.format("SELECT COUNT(*) FROM %s", cat));
                if (rs.next()) {
                    size = rs.getInt("COUNT(*)");
                    System.out.println("Size: " + size);
                }
            }

            // GETTING LAST ID
            if(lastId == null){
                ResultSet rs = stmt.executeQuery(String.format("SELECT COUNT(*) FROM %s WHERE good_name IS NULL", cat));
                if (rs.next()) {
                    lastId = rs.getInt("COUNT(*)");
                    System.out.println("Last ID: " + lastId);
                }
            }

            // If the stack is empty, fill it with null data
            if (size == 0) {
                System.out.println("initializing " + cat + " with default EMPTY values...");
                for (int i = 0; i < 3; i++) {
                    String insertQuery = String.format("INSERT INTO %s (id, good_name, quantity, buying_price, selling_price, gross_price, date)" +
                            " VALUES(%s, NULL, NULL, NULL, NULL, NULL, NULL)", cat, i);
                    Statement insertStatement = connection.createStatement();
                    insertStatement.execute(insertQuery);
                }
                System.out.println(cat + " initiated wil EMPTY values");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        };
    }


    public void push(String cat, String good_name, int quantity, double buying_price, double selling_price, double gross_price, String date) {
        String updateQuery = "";
        updateQuery = "UPDATE " + cat + " SET good_name = '" + good_name + "', quantity = " + quantity + ", buying_price = " + buying_price + ", selling_price = " + selling_price + ", gross_price = " + gross_price + ", date = '" + date + "' WHERE id = " + (lastId - 1);
        lastId -= 1;
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void pop(String cat) {
        if (isEmpty()) {
            System.out.println("STACK EMPTY");
            JOptionPane.showMessageDialog(null, "Database is Empty!");
        }

//        T poppedItem = null;
        String selectQuery = "SELECT * FROM " + cat + " WHERE id = " + lastId;
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet result = selectStatement.executeQuery();
            if (result.next()) {
                String updateQuery = "UPDATE " + cat + " SET "
                        + "good_name = NULL, quantity = NULL, buying_price = NULL, selling_price = NULL, gross_price = NULL, date = NULL WHERE id = "
                        + lastId;
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.executeUpdate();
                lastId += 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        return poppedItem;
    }

    public Boolean isEmpty() {
        System.out.println("Last ID: " + lastId);
        return lastId == null || lastId == size;
    }
}

