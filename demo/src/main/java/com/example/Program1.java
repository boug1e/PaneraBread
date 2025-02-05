package com.example;

import java.util.ArrayList;
import java.util.*;

public class Program1 {
    public static void main(String[] args) {
        ArrayList<PaneraItem> item = new ArrayList<PaneraItem>();
        ArrayList<PaneraUser> user = new ArrayList<PaneraUser>();
        Scanner in = new Scanner(System.in);

        System.out.println("1. Add Food Object to menu");
        System.out.println("2. Add Beverage Object to menu");
        System.out.println("3. Add Coffee Object to menu");
        System.out.println("4. Add Panera User");
        System.out.println("5. Order");
        System.out.println("6. Bulk add menu items");
        System.out.println("7. Print menu options");
        System.out.println("8. Exit\n");

        //Selects menu option
        System.out.print("Enter a menu option: ");
        int option = in.nextInt();
        while (option != 8) {
            in.nextLine();
            if (option >= 1 && option <= 4) {
                if (option == 1) { //Add food item to menu
                    System.out.print("Enter name for Food item: ");
                    String name = in.nextLine();
                    System.out.print("Enter item price: ");
                    double price = in.nextDouble();
                    in.nextLine();
                    System.out.print("Enter item side: ");
                    String side = in.nextLine();
                    System.out.print("Enter item size: ");
                    String size = in.nextLine();
                    item.add(new Food(name, price, side, size));
                }
                else if (option == 2) { //Add a beverage to menu
                    System.out.print("Enter name for Beverage item: ");
                    String name = in.nextLine();
                    System.out.print("Enter item price: ");
                    double price = in.nextDouble();
                    in.nextLine();
                    System.out.print("Enter item size: ");
                    String size = in.nextLine();
                    System.out.print("Is this item a sip club item (yes/no)? ");
                    String sipItem = in.nextLine();
                    item.add(new Beverage(name, price, stringToBoolean(sipItem), size));
                }
                else if (option == 3) { //Add a coffee to the menu
                    System.out.print("Enter name for Coffee item: ");
                    String name = in.nextLine();
                    System.out.print("Enter milk type: ");
                    String milk = in.nextLine();
                    System.out.print("Enter item price: ");
                    double price = in.nextDouble();
                    in.nextLine();
                    System.out.print("Enter item size: ");
                    String size = in.nextLine();
                    System.out.print("Is this item a sip club item (yes/no)? ");
                    String sipItem = in.nextLine();
                    item.add(new Coffee(name, price, stringToBoolean(sipItem), milk, size));
                }
                else { //create a panera user
                    System.out.print("Enter user's name: ");
                    String name = in.nextLine();
                    System.out.print("Is this user a sip club member (yes/no)? ");
                    String member = in.nextLine();
                    user.add(new PaneraUser(name, stringToBoolean(member)));
                }
            }
            else if (option > 4 && option < 8) {
                if (option == 5) { //Order
                    System.out.print("How many items in order? ");
                    int ORDER_AMOUNT = in.nextInt();
                    in.nextLine();
                    System.out.print("Name for order: ");
                    String name = in.nextLine();
                    double total = 0;
                    for (int i = 1; i <= ORDER_AMOUNT; i++) {
                        System.out.print("Enter order " + i + ": ");
                        String[] orderItem = in.nextLine().split(" ", 2);
                        boolean found = false;
                        paneraLoop:
                        for (PaneraItem target : item) { //Searches the paneraitem for specified target
                            if (target.getSize().equalsIgnoreCase(orderItem[0]) && target.getName().equalsIgnoreCase(orderItem[1])) {
                                if(target instanceof Beverage) {
                                    for (PaneraUser j : user) {
                                        if (j.getName().equalsIgnoreCase(name) && j.isSipClubMember() && ((Beverage) target).isSipClubDrink() == true) {
                                            found = true;
                                            break paneraLoop;
                                        }
                                    }
                                    total += target.getPrice();
                                }else{
                                    total += target.getPrice();
                                }
                                found = true; 
                                break;
                            }
                        }
                        if (found == false) {
                            System.out.println("Item not found");
                            i--;
                        }    
                    }
                    System.out.printf("Total is: $%.2f\n", total);
                }
                else if (option == 6) { //add multiple menu items
                    System.out.print("How many items are you importing? ");
                    int items = in.nextInt();
                    in.nextLine();
                    System.out.println("Input bulk added items below");
                    for (int i = 0; i < items; i++) {
                        String[] bulkItems = in.nextLine().split(",");
                        if (bulkItems[0].equalsIgnoreCase("Food")) { //Add new food
                            item.add(new Food(bulkItems[1], stringToDouble(bulkItems[2]), bulkItems[3], bulkItems[4]));
                        }else if (bulkItems[0].equalsIgnoreCase("Beverage")) { //Add new beverage
                            item.add(new Beverage(bulkItems[1], stringToDouble(bulkItems[2]), stringToBoolean(bulkItems[3]), bulkItems[4]));
                        }else if(bulkItems[0].equalsIgnoreCase("Coffee")) { // Add new coffee
                            item.add(new Coffee(bulkItems[1], stringToDouble(bulkItems[2]), stringToBoolean(bulkItems[3]), bulkItems[4], bulkItems[5]));
                        }else{
                            System.out.println("Please enter a valid menu item.");
                        }
                    }
                }
                else if (option == 7) {
                    System.out.println("1. Add Food Object to menu");
                    System.out.println("2. Add Beverage Object to menu");
                    System.out.println("3. Add Coffee Object to menu");
                    System.out.println("4. Add Panera User");
                    System.out.println("5. Order");
                    System.out.println("6. Bulk add menu items");
                    System.out.println("7. Print menu options");
                    System.out.println("8. Exit\n");
                }
            }
            System.out.print("Enter a menu option: ");
            option = in.nextInt();
        }
        System.out.println("Exiting...");
        System.exit(0);
    }

    public static boolean stringToBoolean(String str) {
        if (str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("true")) {
            return true;
        }else{
            return false;
        }
    }

    public static double stringToDouble(String str) {
        double str1 = Double.parseDouble(str);
        return str1;
    }
}

class PaneraUser {
    private String name;
    private boolean sipClubMember;

    public PaneraUser(String name, boolean sipClubMember) {
        this.name = name;
        this.sipClubMember = sipClubMember;
    }

    public String getName() {
        return name;
    }

    public boolean isSipClubMember() {
        return sipClubMember;
    }
}

class PaneraItem {
    private String name;
    private double price;
    private String size;
    public PaneraItem(String name, double price, String size) {
        this.name = name;
        this.price = price;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }  
}

class Food extends PaneraItem {
    private String side;
    public Food(String name, double price, String side, String size) {
        super(name, price, size);
        this.side = side;
    }
}

class Beverage extends PaneraItem {
    private boolean sipClubItem;
    public Beverage(String name, double price, boolean sipClubItem, String size) {
        super(name, price, size);
        this.sipClubItem = sipClubItem;
    }

    public boolean isSipClubDrink() {
        return sipClubItem;
    }
}

class Coffee extends Beverage {
    private String milk;
    public Coffee(String name, double price, boolean sipCupItem, String milk, String size){
        super(name, price, sipCupItem, size);
        this.milk = milk;
    }
    public boolean isSipClubDrink() {
        return super.isSipClubDrink();
    }
}