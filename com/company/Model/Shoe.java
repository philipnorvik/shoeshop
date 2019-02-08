package com.company.Model;

import java.util.List;

public class Shoe {
    
    private int id;
    private Color color;
    private int lagerAntal;
    private int size;
    private String name;
    private Brand brand;
    private int price;
    private List<Catagory> catagory;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public int getLagerAntal() {
        return lagerAntal;
    }

    public void setLagerAntal(int lagerAntal) {
        this.lagerAntal = lagerAntal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public List<Catagory> getCatagory() {
        return catagory;
    }

    public void setCatagory(List<Catagory> catagory) {
        this.catagory = catagory;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}