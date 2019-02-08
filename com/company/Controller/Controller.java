package com.company.Controller;;

import com.company.DBConnection;
import com.company.DBViewModels.*;
import com.company.Model.*;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Controller {


    private final com.company.DBConnection DBConnection = new DBConnection();

    public Map<String, Integer> getKundlistaMedTotal(int namnID){
        Map<String, Integer> kundlista = new HashMap<>();
        DBConnection.getKunder(namnID).forEach(k -> kundlista.put(k.getFirstName(), getKundTotal(k.getId())));
        return kundlista;
    }



    public Map<String, List<String>> shoePerCatagories(){
        Map<String, List<String>> shoesPerCatagories = new HashMap<>();
        DBConnection.getKategorier("").forEach((t) -> {
            shoesPerCatagories.put(t.getName(), new ArrayList<>());
        });
        DBConnection.getShoes(0).forEach((t) -> {
            t.getCatagory().forEach((s) -> {
                shoesPerCatagories.get(s.getName()).add(t.getName());
            });
        });
        return shoesPerCatagories;
    }

    public List<Customer> GetCustomers(){
        //hämta alla kunder
        return DBConnection.getKunder(0);
    }
    private int getKundTotal(int namnID){
        List<Integer> total = new ArrayList<>();
        int customerId = DBConnection.getKunder(namnID).get(0).getId();

        DBConnection.getCustomerOrders(customerId).forEach((b) -> {
            DBConnection.getAllShoesInCartOrder(b.getCartOrderId()).forEach((s) -> {
                total.add(s.getPrice());
            });
        });
        return total.stream().reduce(0, (u,e) -> u+e);
    }

    /*
    public Map<String, List<String>> shoePerCatagories(){
        Map<String, List<String>> modellerPerKategori = new HashMap<>();
        DBConnection.getCatagories(0).forEach((t) -> {
            modellerPerKategori.put(t.getName(), new ArrayList<>());
        });
        DBConnection.getModeller("").forEach((t) -> {
            t.getCatagories().forEach((s) -> {
                modellerPerKategori.get(s.getName()).add(t.getName());
            });
        });
        return modellerPerKategori;
    }*/

    /**
     * Använd i vyn, vy blir då console programmet
     * @return
     */
    public List<ShoeViewModel> getAllShoes(){
        List<ShoeViewModel> allShoes = new ArrayList<>();
        for (Shoe s : DBConnection.getShoes(0)) {
            ShoeViewModel temp = new ShoeViewModel();
            temp.setLagerAntal(s.getLagerAntal());
            temp.setColor(s.getColor().getName());
            temp.setCatagories(s.getCatagory());
            temp.setBrand(s.getBrand());
            temp.setName(s.getName());
            temp.setPrice(s.getPrice());
            temp.setShoeId(s.getId());
            temp.setSize(s.getSize());
            allShoes.add(temp);
        }
        return allShoes;
    }


    /**
     * Använd i vyn, vår vy blir då console programmet
     * @return
     */
    public List<CartOrderViewModel> getCustomerOrders(int customerId){
        List<CartOrderViewModel> allCustomerOrders = new ArrayList<>();
        for (Cart cart : DBConnection.getCustomerOrders(customerId)) {
            if(!cart.isExpiderad()) {
                CartOrderViewModel temp = new CartOrderViewModel();
                //använd cartOrderId för den är gemensam för fler orderRader för samma order för kund
                temp.setID(cart.getCartOrderId());
                temp.setDatum(cart.getCartDate());
                allCustomerOrders.add(temp);
            }
        }
        return allCustomerOrders;
    }
    
    public String placeOrder(String shoeId, String cartOrderId, String customerId){
        if(cartOrderId == null) cartOrderId = "0";
        return DBConnection.addToCart(shoeId, cartOrderId, customerId);
    }
}