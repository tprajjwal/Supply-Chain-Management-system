package com.example.supplychain;

import java.sql.ResultSet;

public class Order {

    public static boolean orderProduct(int productId, String customerEmail){
        String query = String.format("INSERT INTO orders (quantity, customer_id, product_id) VALUES(1,(SELECT cid FROM customer WHERE email='%s'),%s)",customerEmail,productId);
        DatabaseConnection dbCon = new DatabaseConnection();
        System.out.println(dbCon.executeQuery(query));
        return true;

    }

    public static void main(String[] args) {
        Order.orderProduct(1,"tprajjwal22@gmail.com");
    }


}