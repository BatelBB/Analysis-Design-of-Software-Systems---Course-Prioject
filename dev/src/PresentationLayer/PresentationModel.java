package PresentationLayer;

import ServiceLayer.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PresentationModel {
    private String[] args;
    private final Service service;

    public PresentationModel(Service service) {
        this.service = service;
    }

    public void execute(String input) {
        if (!input.equals("exit")) {
            String command = input.substring(0, input.indexOf(" "));
            args = input.substring(input.indexOf(" ")).split(",", -1);
            switch (command) {
                case "addCategory" -> addCategory();
                case "removeCategory" -> removeCategory();
                case "addSubCategory" -> addSubCategory();
                case "removeSubCategory" -> removeSubCategory();
                case "addSubSubCategory" -> addSubSubCategory();
                case "removeSubSubCategory" -> removeSubSubCategory();
                case "updateCategoryCusDiscount" -> updateCategoryCusDiscount();
                case "updateProductCusDiscount" -> updateProductCusDiscount();
                case "updateItemCusDiscount" -> updateItemCusDiscount();
                case "updateProductManPrice" -> updateProductManPrice();
                case "updateProductCusPrice" -> updateProductCusPrice();
                case "addProduct" -> addProduct();
                case "removeProduct" -> removeProduct();
                case "addItem" -> addItem();
                case "removeItem" -> removeItem();
                case "updateItemDefect" -> updateItemDefect();
                case "getItemLocation" -> getItemLocation();
                case "changeItemLocation" -> changeItemLocation();
                case "changeItemOnShelf" -> changeItemOnShelf();
                case "createMissingReport" -> createMissingReport();
                case "createExpiredReport" -> createExpiredReport();
                case "createSurplusesReport" -> createSurplusesReport();
                case "createDefectiveReport" -> createDefectiveReport();
                case "createBySupplierReport" -> createBySupplierReport();
                case "createByProductReport" -> createByProductReport();
                case "createByCategoryReport" -> createByCategoryReport();
                case "removeReport" -> removeReport();
                case "getReport" -> getReport();
                default -> System.out.println("unknown command, aborting..");
            }
        }
    }


    //service callers
    private void addCategory() {
        if (args.length == 1)
            service.addCategory(args[0]);
    }

    private void removeCategory() {
        if (args.length == 1)
            service.removeCategory(args[0]);
    }

    private void addSubCategory() {
        if (args.length == 2)
            service.addSubCategory(args[0], args[1]);
    }

    private void removeSubCategory() {
        if (args.length == 2)
            service.removeSubCategory(args[0], args[1]);
    }

    private void addSubSubCategory() {
        if (args.length == 3)
            service.addSubSubCategory(args[0], args[1], args[2]);
    }

    private void removeSubSubCategory() {
        if (args.length == 3)
            service.removeSubSubCategory(args[0], args[1], args[2]);
    }

    private void updateCategoryCusDiscount() {
        if (args.length == 6 && convertDouble(args[0]) != -1.0 && convertDate(args[1]) != null && convertDate(args[2]) != null) {
            service.updateCategoryCusDiscount(convertDouble(args[0]), convertDate(args[1]), convertDate(args[2]), args[3], args[4], args[5]);
        }
    }

    private void updateProductCusDiscount() {
        if (args.length == 4 && convertDouble(args[0]) != -1.0 && convertDate(args[1]) != null && convertDate(args[2]) != null && convertDouble(args[3]) != -1) {
            service.updateProductCusDiscount(convertDouble(args[0]), convertDate(args[1]), convertDate(args[2]), convertInt(args[3]));
        }
    }

    private void updateItemCusDiscount() {
        if (args.length == 5 && convertDouble(args[0]) != -1.0 && convertDate(args[1]) != null && convertDate(args[2]) != null && convertDouble(args[3]) != -1 && convertDouble(args[4]) != -1) {
            service.updateItemCusDiscount(convertDouble(args[0]), convertDate(args[1]), convertDate(args[2]), convertInt(args[3]), convertInt(args[4]));
        }
    }

    private void updateProductManPrice() {
        if (args.length == 2 && convertDouble(args[1]) != -1.0 && convertInt(args[0]) != -1) {
            service.updateProductManPrice(convertInt(args[0]), convertDouble(args[1]));
        }
    }

    private void updateProductCusPrice() {
        if (args.length == 2 && convertDouble(args[1]) != -1.0 && convertInt(args[0]) != -1) {
            service.updateProductCusPrice(convertInt(args[0]), convertDouble(args[1]));
        }
    }

    private void addProduct() {
        double man_price = convertDouble(args[2]);
        double cus_price = convertDouble(args[3]);
        int min_qty = convertInt(args[4]);
        int supply_time = convertInt(args[5]);
        if (args.length == 9 && man_price != -1.0 && cus_price != -1.0 && min_qty != -1 && supply_time != -1)
            service.addProduct(args[0], args[1], man_price, cus_price, min_qty, supply_time, args[6], args[7], args[8]);
    }

    private void removeProduct() {
        if (args.length == 1 && convertInt(args[0]) != -1)
            service.removeProduct(convertInt(args[0]));
    }

    private void addItem() {
        int product_id = convertInt(args[0]);
        LocalDateTime expiration_date = convertDate(args[4]);
        if (args.length == 6 && product_id != -1 && expiration_date != null && convertBoolean(args[5]) != null)
            service.addItem(product_id, args[1], args[2], args[3], expiration_date, Objects.requireNonNull(convertBoolean(args[5])));
    }

    private void removeItem() {
        if (args.length == 2 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1)
            service.removeItem(convertInt(args[0]), convertInt(args[0]));
    }

    public void updateItemDefect() {
        int product_id = convertInt(args[0]);
        int item_id = convertInt(args[1]);
        if (args.length == 4 && product_id != -1 && item_id != -1 && convertBoolean(args[2]) != null)
            service.updateItemDefect(product_id, item_id, convertBoolean(args[2]), args[3]);
    }

    public void getItemLocation() {
        if (args.length == 2 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1)
            service.getItemLocation(convertInt(args[0]), convertInt(args[0]));
    }

    public void changeItemLocation() {
        if (args.length == 3 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1)
            service.changeItemLocation(convertInt(args[0]), convertInt(args[0]), args[2]);
    }

    public void changeItemOnShelf() {
        if (args.length == 3 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1 && convertBoolean(args[2]) != null)
            service.changeItemOnShelf(convertInt(args[0]), convertInt(args[0]), convertBoolean(args[2]));
    }

    public void createMissingReport() {
        if (args.length == 3 && convertInt(args[1]) != -1)
            service.createMissingReport(args[0], convertInt(args[1]), args[2]);
    }

    public void createExpiredReport() {
        if (args.length == 3 && convertInt(args[1]) != -1)
            service.createExpiredReport(args[0], convertInt(args[1]), args[2]);
    }

    public void createSurplusesReport() {
        if (args.length == 3 && convertInt(args[1]) != -1)
            service.createSurplusesReport(args[0], convertInt(args[1]), args[2]);
    }

    public void createDefectiveReport() {
        if (args.length == 3 && convertInt(args[1]) != -1)
            service.createDefectiveReport(args[0], convertInt(args[1]), args[2]);
    }

    public void createBySupplierReport() {
        if (args.length == 4 && convertInt(args[1]) != -1)
            service.createBySupplierReport(args[0], convertInt(args[1]), args[2], args[3]);
    }

    public void createByProductReport() {
        if (args.length == 4 && convertInt(args[1]) != -1)
            service.createByProductReport(args[0], convertInt(args[1]), args[2], args[3]);
    }

    public void createByCategoryReport() {
        if (args.length == 6 && convertInt(args[1]) != -1)
            service.createByCategoryReport(args[0], convertInt(args[1]), args[2], args[3], args[4], args[5]);
    }

    public void removeReport() {
        if (args.length == 1 && convertInt(args[0]) != -1)
            service.removeReport(convertInt(args[0]));
    }

    public void getReport() {
        if (args.length == 1 && convertInt(args[0]) != -1)
            service.getReport(convertInt(args[0]));
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

    private LocalDateTime convertDate(String input) {
        try {
            DateTimeFormatter formatter;
            if (input.contains(":"))
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            else
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDateTime.parse(input, formatter);
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
