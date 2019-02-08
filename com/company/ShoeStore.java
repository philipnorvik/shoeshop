package com.company;

import com.company.Controller.Controller;
import com.company.DBViewModels.*;
import com.company.Model.Customer;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ShoeStore {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    Scanner scanner;
    Controller controller;
    String kundID;
    List<ShoeViewModel> shoes;
    List<CartOrderViewModel> beställningar;
    Map<Integer, String> alternatives;
    List<String> stuff;
    int input;

    public ShoeStore() {
        scanner = new Scanner(System.in);
        controller = new Controller();
        shoes = new ArrayList<>();
        beställningar = new ArrayList<>();
        input = 0;
    }

    public void start() throws SQLException, ClassNotFoundException {
        while (true) {
            System.out.println("\nVad vill du göra?\n"
                    + "\n[1] Visa kundinformation"
                    + "\n[2] Visa Produkter per Catagory"
                    + "\n[3] Lägg beställning"
                    + "\n[0] Avsluta\n");

            switch (scanner.nextLine()) {
                case "1":
                    visaKundTotal();
                    break;
                case "2":
                    visaProdukterPerKategori();
                    break;
                case "3":
                    addOrder();
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nOgiltig inmatning");
                    break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    public void visaKundTotal() {
        controller.getKundlistaMedTotal(0)
                .entrySet()
                .stream()
                .forEach(k -> System.out.print("\nNamn: " + k.getKey() +
                        "\tTotal: " + k.getValue() +" kr" + "\n"));
    }

    public void visaProdukterPerKategori() {
        System.out.println();
        controller.shoePerCatagories()
                .entrySet()
                .stream()
                .forEach((t) -> {
                    System.out.println(t.getKey() + ": " + t.getValue());
                });
    }

    public void addOrder() {
        String inputNamn;
        String inputStorlek;
        String inputColor;
        String inputShoeId;
        String inputCartOrderId = null;
        String price;

        List<Customer> customers = controller.GetCustomers();
        System.out.println("\nVälj Kund:\n");
        for(int i=0; i < customers.size(); i++){
            System.out.println(i+1 + "."+ customers.get(i).getFirstName()+ " " + customers.get(i).getSurName());
        }
        //fel val
        input = Integer.parseInt(scanner.nextLine());
        if ( input > customers.size())
            return;
        // sätt vald kund
        Customer chosenCustomer = customers.get(input-1);


        shoes = controller.getAllShoes();
        filterByStock();
        System.out.println("\nVälj ShoeModel:\n");
        alternatives = generateMapFromList(stuff);
        printAlternatives(alternatives);
        System.out.println();
        if (!isValidInput(scanner.nextLine()))
            return;

        inputNamn = alternatives.get(input);
        filterByName(inputNamn);
        Collections.sort(stuff);
        System.out.println("\nVälj Storlek:\n");
        alternatives = generateMapFromList(stuff);
        printAlternatives(alternatives);
        System.out.println();
        if (!isValidInput(scanner.nextLine()))
            return;

        inputStorlek = alternatives.get(input);
        filterBySize(inputStorlek);
        System.out.println("\nVälj Color:\n");
        alternatives = generateMapFromList(stuff);
        printAlternatives(alternatives);
        System.out.println();
        if (!isValidInput(scanner.nextLine()))
            return;

        inputColor = alternatives.get(input);
        filterByColor(inputColor);
        inputShoeId = String.valueOf(shoes.get(0).getShoeId());
        price = String.valueOf(shoes.get(0).getPrice());
        System.out.println("\nDitt val:\n" +
                "\nModel:  \t" + inputNamn +
                "\nStorlek:\t" + inputStorlek +
                "\nColor:   \t" + inputColor +
                "\nPris:   \t" + price + ":-" +
                "\n\n[1] Bekräfta" +
                "\n[2] Avbryt\n");
        if (scanner.nextLine().trim().equals("1")) {
            beställningar = controller.getCustomerOrders(chosenCustomer.getId());
            stuff = beställningar.stream()
                    .map(CartOrderViewModel::getDatum)
                    .map(d -> d.toString()).map(s -> s.replace('T', ' '))
                    .collect(Collectors.toList());
            System.out.println("\nVälj Cart:\n");
            alternatives = generateMapFromList(stuff);
            addNewAlternative();
            printAlternatives(alternatives);
            if (!isValidInput(scanner.nextLine()))
                return;
            if (isTime(alternatives.get(input))) {
                for (CartOrderViewModel b : beställningar) {
                    if (b.getDatum().compareTo(LocalDateTime.parse(alternatives.get(input), formatter)) == 0) {
                        inputCartOrderId = String.valueOf(b.getID());
                        break;
                    }
                }
            }
            System.out.println("\n" + controller.placeOrder(inputShoeId, inputCartOrderId, chosenCustomer.getId()+""));
        }
    }

    private void filterByStock() {
        shoes = shoes.stream().filter(s -> s.getLagerAntal() > 0).collect(Collectors.toList());
        stuff = shoes.stream()
                .map(ShoeViewModel::getName)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }

    private void filterByName(String input) {
        shoes = shoes.stream()
                .filter(s -> s.getName().equalsIgnoreCase(input))
                .collect(Collectors.toList());
        stuff = shoes.stream()
                .map(t -> String.valueOf(t.getSize()))
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }

    private void filterBySize(String input) {
        shoes = shoes.stream()
                .filter(s -> s.getSize() == Integer.parseInt(input))
                .collect(Collectors.toList());
        stuff = shoes.stream()
                .map(ShoeViewModel::getColor)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }

    private void filterByColor(String input) {
        shoes = shoes.stream()
                .filter(s -> s.getColor().equals(input))
                .collect(Collectors.toList());
    }

    private boolean isValidInput(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            System.out.println("\nOgiltig inmatning");
            return false;
        }
        if (Integer.parseInt(input) > alternatives.size()) {
            System.out.println("\nOgiltig inmatning");
            return false;
        }
        this.input = Integer.parseInt(input);
        return true;
    }

    public void printAlternatives(Map<Integer, String> alternatives) {
        alternatives.entrySet()
                .stream()
                .forEach(m -> System.out.println("[" + m.getKey() + "] " + m.getValue()));
    }

    public Map<Integer, String> generateMapFromList(List stuff) {
        Map<Integer, String> theMap = new HashMap<>();
        for (int i = 0; i < stuff.size(); i++) {
            theMap.put(i + 1, (String) stuff.get(i));
        }
        stuff.clear();
        return theMap;
    }

    public void addNewAlternative() {
        alternatives.put(alternatives.size() + 1, "Ny Cart\n");
    }

    private boolean isTime(String s) {
        try {
            LocalDateTime.parse(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}