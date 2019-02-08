package com.company.Model;

import java.util.*;

public class Customer {

    private int id;
    private String firstName;
    private String surName;
    private String Street;
    private String City;

    private List<Cart> cartOrders;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Cart> getCartOrders() {
        return cartOrders;
    }

    public void setCartOrders(List<Cart> cartOrders) {
        this.cartOrders = cartOrders;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}