package com.company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            new ShoeStore().start();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
