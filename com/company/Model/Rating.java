package com.company.Model;

public class Rating {
    
    private int id;
    private String betyg;
    private Shoe shoe;
    private Customer customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getBetyg() {
        return betyg;
    }

    public void setBetyg(String betyg) {
        this.betyg = betyg;
    }


    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}