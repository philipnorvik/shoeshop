package com.company.Model;

import java.time.LocalDateTime;

public class Cart {

    private int Id;
    private int cartOrderId;
    private LocalDateTime cartDate;
    private Shoe sko;
    private Customer customer;
    private boolean expiderad;
    public Cart() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public LocalDateTime getCartDate() {
        return cartDate;
    }

    public void setCartDate(LocalDateTime cartDate) {
        this.cartDate = cartDate;
    }

    public int getCartOrderId() {
        return cartOrderId;
    }

    public void setCartOrderId(int cartOrderId) {
        this.cartOrderId = cartOrderId;
    }

    public Shoe getSko() {
        return sko;
    }

    public void setSko(Shoe sko) {
        this.sko = sko;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setExpiderad(boolean expiderad) {
        this.expiderad = expiderad;
    }

    public boolean isExpiderad() {
        return expiderad;
    }
}