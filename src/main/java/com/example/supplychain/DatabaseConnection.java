package com.example.supplychain;
import java.sql.*;
public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/supply_chain";
    private static final String USER = "root"; //sql username
    private static final String PASS = "Prajjwal20@"; // sql password

    public Statement getStatement() {
        //Statement object is created for running queries;
        Statement statement = null;
        Connection conn;
        try {
            //Driver Manager is a class , it takes help of sql connector,& when you provide url,and some data ,it provides connection to database & return a connection object called conn.
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // Now we need a statement to run our queries from conn object,for that statement is created
            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("");
        }
        return statement;
    }

    public ResultSet getQueryTable(String query) {
        ResultSet rs = null;
        try {
            Statement statement = getStatement();
            return statement.executeQuery(query); // to execute query which we get from statement
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean executeQuery(String query) {
        try {
            Statement statement = getStatement();
            statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {

        DatabaseConnection dbConn = new DatabaseConnection();

        try {
            String query = "select * from product ";
            ResultSet rs = dbConn.getQueryTable(query);
            while (rs.next()) {
                System.out.println(rs.getInt("pid") + " " +
                        rs.getString("name") + " " +
                        rs.getDouble("price") + " " +
                        rs.getString(2)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
