package com.company;

import com.company.Model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnection {


    String code = "******" ;
    String name = "******";
    String pass="******";


    String kundQuery =  "select * from customer";
    String brandQuery = "select * from brand";
    String catQuery =   "select * from catagory";
    String colorQuery = "select * from color";
    String addtoCartStoredProcedureQuery = "call AddToCart(?,?,?)";
    String skoQuery = "select * from shoe";
    String getShoeSpecificModelQuery = "select * from shoe where modellID = ?";
    String getStorageAmountQuery = "select lagerAntal from shoe where id = ?";
    String getCartOrderByCartOrderIdQuery = "select * from cart where cartOrderId = ?";
    String customerQuery = "select * from cart";
    String getCustomerOrderQuery = "select * from cart where customerId = ?";


    public DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public List<Catagory> getKategorier(String ID){
        List<Catagory> allaKategorier = new ArrayList<>();
        String query = "select * from Catagory";
        if (ID.length() > 0)
            query = "select * from Catagory where ID = ?";
        try(Connection con = DriverManager.getConnection(code, name, pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if (ID.length() > 0)
                stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Catagory temp = new Catagory();
                temp.setName(rs.getString("name"));
                allaKategorier.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaKategorier;
    }

    public List<Customer> getKunder(String namn){
        List<Customer> allaKunder = new ArrayList<>();

        kundQuery += " where namn = ?";
        try(Connection con = DriverManager.getConnection(code, name, pass);
            PreparedStatement stmt = con.prepareStatement(kundQuery);
        ){
            if(namn.length() > 0){
                stmt.setString(1, namn);
            }
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Customer temp = new Customer();
                temp.setId(rs.getInt("ID"));
                temp.setFirstName(rs.getString("namn"));
                allaKunder.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaKunder;
    }
    public List<Customer> getKunder(int namnID){
        List<Customer> allaKunder = new ArrayList<>();
        String query = kundQuery.toString();
        if(namnID > 0) {
            query += " where id = ?";

        }
        try(Connection con = DriverManager.getConnection(code, name, pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if(namnID > 0){
                stmt.setInt(1, namnID);
            }
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Customer temp = new Customer();
                temp.setId(rs.getInt("ID"));
                temp.setFirstName(rs.getString("firstname"));
                temp.setSurName(rs.getString("surname"));
                allaKunder.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaKunder;
    }
        
    public Map<Integer, Brand> getBrands(int id){
        Map<Integer, Brand> allBrands = new HashMap<>();
        String query = brandQuery;
        if(id > 0){
            query =  brandQuery + " where id = ?";
        }
        try(Connection con = DriverManager.getConnection(code, name, pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if (id > 0) {
                stmt.setInt(1, id);
            }
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Brand temp = new Brand();
                temp.setName(rs.getString("name"));
                allBrands.put(rs.getInt("id") ,temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allBrands;
    }

    public List<Catagory> getCatagoriesByShoeId(int ID){
        List<Catagory> allCatagories = new ArrayList<>();
        String query = "select name,catagory.id from catagory,shoecatagory where catagory.id = shoecatagory.id and shoeId = ?";

        try(Connection con = DriverManager.getConnection(code, name, pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if (ID > 0)
                stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Catagory temp = new Catagory();
                temp.setName(rs.getString("name"));
                allCatagories.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allCatagories;
    }

    public List<Catagory> getCatagories(int ID){
        List<Catagory> allCatagories = new ArrayList<>();
        String query = catQuery;
        if(ID  > 0) {
            //join with shoecatagory and catagory
            query = catQuery + " where id = ?";
        }
        try(Connection con = DriverManager.getConnection(code, name, pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if (ID > 0)
                stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Catagory temp = new Catagory();
                temp.setName(rs.getString("name"));
                allCatagories.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allCatagories;
    }


    public Map<Integer, Color> getColors(int ID){
        Map<Integer, Color> allColors = new HashMap<>();

        try(Connection con = DriverManager.getConnection(code, name, pass);
            PreparedStatement stmt = con.prepareStatement(colorQuery);
        ){
            if (ID > 0){
                stmt.setInt(1, ID);
            }

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Color temp = new Color();
                temp.setName(rs.getString("name"));
                allColors.put(rs.getInt("ID"), temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allColors;
    }


    public List<Shoe> getShoes(int ID){
        List<Shoe> allShoes = new ArrayList<>();
        Map<Integer, Color> colors = getColors(0);
        String query = skoQuery;
        if(ID > 0){
            query += " where ID = ?";
        }
        try(Connection con = DriverManager.getConnection(code, name, pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if(ID > 0)
                stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Shoe temp = new Shoe();
                temp.setId(rs.getInt("id"));
                temp.setSize(rs.getInt("size"));
                temp.setColor(colors.get(rs.getInt("colorID")));
                temp.setLagerAntal(getAntalILager(rs.getInt("id")));
                temp.setName(rs.getString("name"));
                temp.setPrice(rs.getInt("price"));
                temp.setBrand(getBrands(rs.getInt("brandid")).get(0));
                temp.setCatagory(getCatagoriesByShoeId(rs.getInt("id")));
                allShoes.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allShoes;
    }

    public int getAntalILager(int shoeId){

        try(Connection con = DriverManager.getConnection(code, name, pass);
            PreparedStatement stmt = con.prepareStatement(getStorageAmountQuery);
        ){
            stmt.setString(1, String.valueOf(shoeId));
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getInt("lagerAntal");
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    public List<Shoe> getAllShoesInCartOrder(int cartOrderId){
        List<Shoe> allShoesInCartOrder = new ArrayList<>();
        List<Integer> shoeId = new ArrayList<>();

        try(Connection con = DriverManager.getConnection(code, name, pass);
            PreparedStatement stmt = con.prepareStatement(getCartOrderByCartOrderIdQuery);
        ){
            stmt.setString(1, String.valueOf(cartOrderId));
            ResultSet rs1 = stmt.executeQuery();
            while(rs1.next()){
                shoeId.add(rs1.getInt("shoeId"));
            }
            for (Integer skoId : shoeId) {
                allShoesInCartOrder.add(getShoes(skoId).get(0));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allShoesInCartOrder;
    }

    public List<Cart> getCustomerOrders(int customerId){
        List<Cart> allCustomerOrders = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try(Connection con = DriverManager.getConnection(code, name, pass);
            PreparedStatement stmt = con.prepareStatement(getCustomerOrderQuery);
        ){
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Cart temp = new Cart();
                temp.setId(rs.getInt("id"));
                temp.setCartOrderId(rs.getInt("cartOrderId"));
                temp.setCartDate(LocalDateTime.parse(rs.getString("cartDate").substring(0, 19), formatter));
                temp.setExpiderad(rs.getBoolean("expiderad"));

                allCustomerOrders.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allCustomerOrders;
    }

    /**
     *  Anrop till stored procedure enl inlämninguppiften
     * @param shoeId
     * @param cartOrderId
     * @param customerId
     * @return
     */
    public String addToCart(String shoeId, String cartOrderId, String customerId){

        try(Connection con = DriverManager.getConnection(code, name, pass);
            CallableStatement stmt = con.prepareCall(addtoCartStoredProcedureQuery)
        ){
            stmt.setString(1, customerId);
            stmt.setString(2, cartOrderId);
            stmt.setString(3, shoeId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Du har nu lagt din order.");
        }   catch (SQLException ex) {
            System.out.println("något gick fel");
            //ex.printStackTrace();
        }
        return "";
    }
    

}