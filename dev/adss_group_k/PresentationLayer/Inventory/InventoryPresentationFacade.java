package adss_group_k.PresentationLayer.Inventory;

import adss_group_k.BusinessLayer.Inventory.Service.Objects.*;
import adss_group_k.BusinessLayer.Inventory.Service.Service;
import adss_group_k.shared.response.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class InventoryPresentationFacade {
    private String[] args;
    private final Service service;

    public InventoryPresentationFacade(Service service) {
        this.service = service;
    }

    public void execute(String input) {
        try{
        if (!input.equals("exit")) {
            String command = input.substring(0, input.indexOf(" "));
            args = input.substring(input.indexOf(" ") + 1).split(",", -1);
            switch (command) {
                case "addCategory":
                    addCategory();
                    break;
                case "removeCategory":
                    removeCategory();
                    break;
                case "addSubCategory":
                    addSubCategory();
                    break;
                case "removeSubCategory":
                    removeSubCategory();
                    break;
                case "addSubSubCategory":
                    addSubSubCategory();
                    break;
                case "removeSubSubCategory":
                    removeSubSubCategory();
                    break;
                case "updateCategoryCusDiscount":
                    updateCategoryCusDiscount();
                    break;
                case "updateProductCusDiscount":
                    updateProductCusDiscount();
                    break;
                case "updateItemCusDiscount":
                    updateItemCusDiscount();
                    break;
                case "updateProductCusPrice":
                    updateProductCusPrice();
                    break;
                case "addProduct":
                    addProduct();
                    break;
                case "removeProduct":
                    removeProduct();
                    break;
                case "addItem":
                    addItem();
                    break;
                case "removeItem":
                    removeItem();
                    break;
                case "updateItemDefect":
                    updateItemDefect();
                    break;
                case "getItemLocation":
                    getItemLocation();
                    break;
                case "changeItemLocation":
                    changeItemLocation();
                    break;
                case "changeItemOnShelf":
                    changeItemOnShelf();
                    break;
                case "createMissingReport":
                    createMissingReport();
                    break;
                case "createExpiredReport":
                    createExpiredReport();
                    break;
                case "createSurplusesReport":
                    createSurplusesReport();
                    break;
                case "createDefectiveReport":
                    createDefectiveReport();
                    break;
                case "createBySupplierReport":
                    createBySupplierReport();
                    break;
                case "createByProductReport":
                    createByProductReport();
                    break;
                case "createByCategoryReport":
                    createByCategoryReport();
                    break;
                case "removeReport":
                    removeReport();
                    break;
                case "getReport":
                    getReport();
                    break;
                default:
                    System.out.println("unknown command, aborting..");
                    break;
            }
        }} catch (Exception e) {
            System.out.println("Invalid syntax for inventory module command");
        }
    }


    //service callers
    private void addCategory() {
        Response r;
        if (args.length == 1) {
            r = service.addCategory(args[0]);
            if (!r.success)
                System.out.println(r.error);
        }
    }

    private void removeCategory() {
        Response r;
        if (args.length == 1) {
            r = service.removeCategory(args[0]);
            if (!r.success)
                System.out.println(r.error);
        }
    }

    private void addSubCategory() {
        Response r;
        if (args.length == 2) {
            r = service.addSubCategory(args[0], args[1]);
            if (!r.success)
                System.out.println(r.error);
        }
    }

    private void removeSubCategory() {
        Response r;
        if (args.length == 2) {
            r = service.removeSubCategory(args[0], args[1]);
            if (!r.success)
                System.out.println(r.error);
        }
    }

    private void addSubSubCategory() {
        Response r;
        if (args.length == 3) {
            r = service.addSubSubCategory(args[0], args[1], args[2]);
            if (!r.success)
                System.out.println(r.error);
        }
    }

    private void removeSubSubCategory() {
        Response r;
        if (args.length == 3) {
            r = service.removeSubSubCategory(args[0], args[1], args[2]);
            if (!r.success)
                System.out.println(r.error);
        }
    }

    private void updateCategoryCusDiscount() {
        Response r;
        if (args.length == 6 && convertDouble(args[0]) != -1.0 && convertDate(args[1]) != null && convertDate(args[2]) != null) {
            r = service.updateCategoryCusDiscount(convertFloat(args[0]), convertDate(args[1]), convertDate(args[2]), args[3], args[4], args[5]);
            if (!r.success)
                System.out.println(r.error);
        }
    }

    private void updateProductCusDiscount() {
        Response r;
        if (args.length == 4 && convertDouble(args[0]) != -1.0 && convertDate(args[1]) != null && convertDate(args[2]) != null && convertDouble(args[3]) != -1) {
            r = service.updateProductCusDiscount(convertFloat(args[0]), convertDate(args[1]), convertDate(args[2]), convertInt(args[3]));
            if (!r.success)
                System.out.println(r.error);
        }
    }

    private void updateItemCusDiscount() {
        Response r;
        if (args.length == 5 && convertDouble(args[0]) != -1.0 && convertDate(args[1]) != null && convertDate(args[2]) != null && convertDouble(args[3]) != -1 && convertDouble(args[4]) != -1) {
            r = service.updateItemCusDiscount(convertFloat(args[0]), convertDate(args[1]), convertDate(args[2]), convertInt(args[3]), convertInt(args[4]));
            if (!r.success)
                System.out.println(r.error);
        }
    }

    private void updateProductCusPrice() {
        Response r;
        if (args.length == 2 && convertDouble(args[1]) != -1.0 && convertInt(args[0]) != -1) {
            r = service.updateProductCusPrice(convertInt(args[0]), convertDouble(args[1]));
            if (!r.success)
                System.out.println(r.error);
        }
    }

    private void addProduct() {
        ResponseT<Product> r;
        double man_price = convertDouble(args[2]);
        float cus_price = convertFloat(args[3]);
        int min_qty = convertInt(args[4]);
        int supply_time = convertInt(args[5]);
        if (args.length == 9 && man_price != -1.0 && cus_price != -1.0 && min_qty != -1 && supply_time != -1) {
            r = service.addProduct(args[0], args[1], man_price, cus_price, min_qty, supply_time, args[6], args[7], args[8]);
            if (!r.success)
                System.out.println(r.error);
            else
                System.out.println(r.data);
        }
    }

    private void removeProduct() {
        Response r;
        if (args.length == 1 && convertInt(args[0]) != -1) {
            r = service.removeProduct(convertInt(args[0]));
            if (!r.success)
                System.out.println(r.error);
        }
    }

    private void addItem() {
        Response r;
        int product_id = convertInt(args[0]);
        LocalDate expiration_date = convertDate(args[4]);
        if (args.length == 6 && product_id != -1 && expiration_date != null && convertBoolean(args[5]) != null) {
            r = service.addItem(product_id, args[1], args[2], convertInt(args[3]), expiration_date, Objects.requireNonNull(convertBoolean(args[5])));
            if (!r.success)
                System.out.println(r.error);
        }
    }

    private void removeItem() {
        Response r;
        if (args.length == 2 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1) {
            r = service.removeItem(convertInt(args[0]), convertInt(args[0]));
            if (!r.success)
                System.out.println(r.error);
        }
    }

    public void updateItemDefect() {
        Response r;
        int product_id = convertInt(args[0]);
        int item_id = convertInt(args[1]);
        if (args.length == 4 && product_id != -1 && item_id != -1 && convertBoolean(args[2]) != null) {
            r = service.updateItemDefect(product_id, item_id, convertBoolean(args[2]), args[3]);
            if (!r.success)
                System.out.println(r.error);
        }
    }

    public void getItemLocation() {
        ResponseT<String> r;
        if (args.length == 2 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1) {
            r = service.getItemLocation(convertInt(args[0]), convertInt(args[0]));
            if (!r.success)
                System.out.println(r.error);
            else
                System.out.println(r.data);
        }
    }

    public void changeItemLocation() {
        Response r;
        if (args.length == 3 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1) {
            r = service.setItemLocation(convertInt(args[0]), convertInt(args[0]), args[2]);
            if (!r.success)
                System.out.println(r.error);
        }
    }

    public void changeItemOnShelf() {
        Response r;
        if (args.length == 3 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1 && convertBoolean(args[2]) != null) {
            r = service.setItemOnShelf(convertInt(args[0]), convertInt(args[0]), convertBoolean(args[2]));
            if (!r.success)
                System.out.println(r.error);
        }
    }

    public void createMissingReport() {
        ResponseT<Report> r;
        if (args.length == 2) {
            r = service.createMissingReport(args[0], args[1]);
            if (!r.success)
                System.out.println(r.error);
            else
                System.out.println(r.data);
        }
    }

    public void createExpiredReport() {
        ResponseT<Report> r;
        if (args.length == 2) {
            r = service.createExpiredReport(args[0], args[1]);
            if (!r.success)
                System.out.println(r.error);
            else
                System.out.println(r.data);
        }
    }

    public void createSurplusesReport() {
        ResponseT<Report> r;
        if (args.length == 2) {
            r = service.createSurplusesReport(args[0], args[1]);
            if (!r.success)
                System.out.println(r.error);
            else
                System.out.println(r.data);
        }
    }

    public void createDefectiveReport() {
        ResponseT<Report> r;
        if (args.length == 2) {
            r = service.createDefectiveReport(args[0], args[1]);
            if (!r.success)
                System.out.println(r.error);
            else
                System.out.println(r.data);
        }
    }

    public void createBySupplierReport() {
        ResponseT<Report> r;
        if (args.length == 3) {
            r = service.createBySupplierReport(args[0], args[1], convertInt(args[2]));
            if (!r.success)
                System.out.println(r.error);
            else
                System.out.println(r.data);
        }
    }

    public void createByProductReport() {
        ResponseT<Report> r;
        if (args.length == 3) {
            r = service.createByProductReport(args[0], args[1], args[2]);
            if (!r.success)
                System.out.println(r.error);
            else
                System.out.println(r.data);
        }
    }

    public void createByCategoryReport() {
        ResponseT<Report> r;
        if (args.length == 5) {
            r = service.createByCategoryReport(args[0], args[1], args[2], args[3], args[4]);
            if (!r.success)
                System.out.println(r.error);
            else
                System.out.println(r.data);
        }
    }

    public void removeReport() {
        Response r;
        if (args.length == 1 && convertInt(args[0]) != -1) {
            r = service.removeReport(convertInt(args[0]));
            if (!r.success)
                System.out.println(r.error);
        }
    }

    public void getReport() {
        ResponseT<Report> r;
        if (args.length == 1 && convertInt(args[0]) != -1) {
            r = service.getReport(convertInt(args[0]));
            if (!r.success)
                System.out.println(r.error);
            else
                System.out.println(r.data);
        }
    }


    //converters
    private Boolean convertBoolean(String input) {
        try {
            return Boolean.parseBoolean(input);
        } catch (Exception e) {
            System.out.println("failed to parse boolean, " + e.getMessage());
            return null;
        }
    }

    private double convertDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (Exception e) {
            System.out.println("failed to parse double, " + e.getMessage());
            return -1.0;
        }
    }

    private float convertFloat(String input) {
        try {
            return Float.parseFloat(input);
        } catch (Exception e) {
            System.out.println("failed to parse float, " + e.getMessage());
            return -1;
        }
    }

    private LocalDate convertDate(String input) {
        try {
            DateTimeFormatter formatter;
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(input, formatter);
        } catch (Exception e) {
            System.out.println("failed to parse date");
            return null;
        }
    }

    private int convertInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("failed to parse int, " + e.getMessage());
            return -1;
        }
    }
}
