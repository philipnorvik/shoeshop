
package com.company.DBViewModels;

import com.company.Model.*;
import java.util.List;

public class ShoeViewModel {
    
    private int shoeId;
    private int size;
    private String color;
    private int lagerAntal;
    private String name;
    private int price;

    private Brand brand;
    private List<Catagory> catagories;

    public int getShoeId() {
        return shoeId;
    }

    public void setShoeId(int shoeId) {
        this.shoeId = shoeId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLagerAntal() {
        return lagerAntal;
    }

    public void setLagerAntal(int lagerAntal) {
        this.lagerAntal = lagerAntal;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Catagory> getCatagories() {
        return catagories;
    }

    public void setCatagories(List<Catagory> catagories) {
        this.catagories = catagories;
    }

}