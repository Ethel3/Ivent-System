package com.example.netmart.Controllers;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueueDB<T>{
    private int front;
    private int rear;
    private int capacity;
    private String cat;

    public QueueDB(int size, String cat){
        try{
            this.front = -1;
            this.rear = -1;
            this.capacity = size;
            this.cat = cat;

            // CONNECTING TO DB
            Connection connection = DatabaseConnection.getConnection();
//            Statement stmt = connection.createStatement();

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

            int _sizeOfTable = 0;
            String countQuery = "SELECT COUNT(*) FROM " + cat;
            Statement countStatement = connection.createStatement();
            ResultSet countResult = countStatement.executeQuery(countQuery);
            if (countResult.next()) {
                _sizeOfTable = countResult.getInt(1);
                if (_sizeOfTable == 0) {
                    capacity = size;
                }
            }

            // Get the top index
            String rearSelectionQuery = "SELECT * FROM " + cat;
            Statement rearSelectionStatement = connection.createStatement();
            ResultSet rearSelectionResult = rearSelectionStatement.executeQuery(rearSelectionQuery);
            while (rearSelectionResult.next()) {
                if (rearSelectionResult.getString("good_name") != null) {
                    rear++;
                } else {
                    break;
                }
            }
            if (rear > -1) {
                front = 0;
            }

            // If the queue is empty, fill it with null data
            if (_sizeOfTable == 0) {
                System.out.println("initializing " + cat + " with default EMPTY values...");
                for (int i = 0; i < capacity; i++) {
                    String insertQuery = String.format("INSERT INTO %s (id, good_name, quantity, buying_price, selling_price, gross_price, date)" +
                            " VALUES(%s, NULL, NULL, NULL, NULL, NULL, NULL)", cat, i);
                    Statement insertStatement = connection.createStatement();
                    insertStatement.execute(insertQuery);
                }
                System.out.println(cat + " initiated wil EMPTY values");
            }

        } catch (Exception ex){
            System.out.println("Error connecting to Database: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public void enqueue (String cat, String good_name, int quantity, double buying_price, double selling_price, double gross_price, String date) {
        if(isFull()){
            expandQueueSize();
        }

        String updateQuery = "";
        updateQuery = "UPDATE " + cat + " SET good_name = '" + good_name + "', quantity = " + quantity + ", buying_price = " + buying_price + ", selling_price = " + selling_price + ", gross_price = " + gross_price + ", date = '" + date + "' WHERE id = " + (rear + 1);
        rear += 1;

        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public T dequeue(String cat) {
        if (isEmpty()) {
            System.out.println("QUEUE IS EMPTY");
            JOptionPane.showMessageDialog(null, "Queue is Empty");
        }
        T dequeuedItem = null;
        try {
            System.out.println(cat + " front: " + front + " rear: " + rear);
            String selectQuery = "SELECT * FROM " + cat + " WHERE id = " + front;

            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                // dequeuedItem = new Item(result.getInt(1), result.getString());
                // set row to null
                String updateQuery = "UPDATE " + cat + " SET "
                        + "good_name = NULL, quantity = NULL, buying_price = NULL, selling_price = NULL, gross_price = NULL, date = NULL WHERE id = "
                        + front;
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Item Removed Successfully!");
                shiftRows(cat);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dequeuedItem;
    }

    private void expandQueueSize() {
        try {
            for (int i = capacity; i < capacity * 2; i++) {
                String insertQuery = "INSERT INTO " + cat
                        + " (id, good_name, quantity, buying_price, selling_price, gross_price, date) VALUES ("
                        + i + "NULL, NULL, NULL, NULL, NULL, NULL)";
                Connection connection = DatabaseConnection.getConnection();
                Statement insertStatement = connection.createStatement();
                insertStatement.execute(insertQuery);
            }
            capacity *= 2;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Expansion failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void shiftRows(String cat) {
        try {
            // select all rows from the table
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + cat);
            resultSet.next();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String good_name = resultSet.getString("good_name");
                int quantity = resultSet.getInt("quantity");
                double buying_price = resultSet.getDouble("buying_price");
                double selling_price = resultSet.getDouble("selling_price");
                double gross_price = resultSet.getDouble("gross_price");
                String date = resultSet.getString("date");

                if (good_name == null) {
                    break;
                }
                // shift row up by one
                String shiftQuery = "UPDATE " + cat + " SET good_name = '" + good_name + "', quantity = " + quantity + ", buying_price = " + buying_price + ", selling_price = " + selling_price + ", gross_price = " + gross_price + ", date = '" + date + "' WHERE id = " + (id - 1);
                PreparedStatement shiftStatement = connection.prepareStatement(shiftQuery);
                shiftStatement.executeUpdate();
            }

            String rearShiftQuery = "UPDATE " + cat + " SET "
                    + "good_name = NULL, quantity = NULL, buying_price = NULL, selling_price = NULL, gross_price = NULL, date = NULL WHERE id = "
                    + rear;
            PreparedStatement rearShiftStatement = connection.prepareStatement(rearShiftQuery);
            rearShiftStatement.executeUpdate();
            rear -= 1;
        } catch (Exception e) {
            System.out.println("An error occurred while shifting rows:");
            e.printStackTrace();
        }
    }


    public Boolean isEmpty() {
        return rear == -1 && front == -1;
    }

    public Boolean isFull() {
        return rear == capacity - 1;
    }

    public int getSize() {
        return capacity;
    }
}
