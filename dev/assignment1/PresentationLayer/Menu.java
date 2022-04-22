package assignment1.PresentationLayer;

import java.util.*;

public class Menu {

    private final List<Menu> submenus = new ArrayList<>();

    public Menu() {
    }


    public Menu getSubMenu(int menuIndex) {
        return submenus.get(menuIndex);
    }

    public String getMainMenu() {
        return new String("1. Supplier menu\n" +
                "2. Item Menu\n" +
                "3. Order Menu\n" +
                "4. Quantity Agreement Menu\n" +
                "5. See summery of weekly orders\n");
    }

    public String getItemSubmenu() {
        return new String(
                "1. Create new item\n" +
                        "2. Edit catalog number of existing item\n" +
                        "3. Edit price of existing item\n" +
                        "4. Edit name of existing item\n" +
                        "5. Edit category of existing item\n" +
                        "6. Delete existing item\n" +
                        "7. See summery of all items\n");
    }

    public String getOrderSubmenu() {
        return new String(
                "1. Create new order\n" +
                        "2. Delete existing order\n" +
                        "3. Edit the ordered date\n" +
                        "4. Edit the delivery data\n" +
                        "5. Edit the item's amount\n" +
                        "6. See summery of all orders\n");
    }

    public String getSupplierSubmenu() {
        return new String(
                "1. Create new supplier card\n" +
                        "2. Edit existing supplier card\n" +
                        "3. Delete existing supplier\n" +
                        "4. See summery of all suppliers\n");
    }

    public String getQuantityAgreementSubmenu() {
        return new String(
                "1. Create new quantity agreement\n" +
                        "2. Edit existing quantity agreement\n" +
                        "3. Delete existing quantity agreement\n");
    }

    public String getSupplierEditSubmenu() {
        return new String(
                "1. Edit ppn number\n" +
                        "2. Edit bank account number\n" +
                        "3. Edit supplier's company name\n" +
                        "4. Edit supplier's isDelivery status\n" +
                        "5. Edit supplier's payment condition\n" +
                        "6. Edit supplier's day of delivery\n" +
                        "7. Edit supplier's contact\n");
    }
}
