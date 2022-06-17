package groupk.shared.PresentationLayer.Inventory;

import groupk.shared.business.Facade;
import groupk.shared.service.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class InventoryPresentationFacade {
    private String[] args;
    private final Service facade;

    public InventoryPresentationFacade(Service facade) {
        this.facade = facade;
    }

    public void execute(String input) {
        try {
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
                    case "createPeriodicOrder":
                        createPeriodicOrder();
                        break;
                    default:
                        System.out.println("unknown command, aborting..");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid syntax for inventory module command");
        }
    }


    //service callers
    private void addCategory() {
        facade.addCategory(args[0]);
    }

    private void removeCategory() {
        facade.removeCategory(args[0]);
    }

    private void addSubCategory() {
        facade.addSubCategory(args[0], args[1]);
    }

    private void removeSubCategory() {
        facade.removeSubCategory(args[0], args[1]);
    }

    private void addSubSubCategory() {

        facade.addSubSubCategory(args[0], args[1], args[2]);
    }

    private void removeSubSubCategory() {
        facade.removeSubSubCategory(args[0], args[1], args[2]);
    }

    private void updateCategoryCusDiscount() {

        facade.updateCategoryCusDiscount(
                convertFloat(args[0]), convertDate(args[1]),
                convertDate(args[2]),
                args[3], args[4], args[5]);
    }

    private void updateProductCusDiscount() {

        facade.updateProductCusDiscount(
                convertFloat(args[0]), convertDate(args[1]),
                convertDate(args[2]), convertInt(args[3]));
    }

    private void updateItemCusDiscount() {

        facade.updateItemCusDiscount(
                convertFloat(args[2]),
                convertDate(args[3]),
                convertDate(args[4]),
                convertInt(args[0]),
                convertInt(args[1])
        );
    }

    private void updateProductCusPrice() {

        facade.updateProductCusPrice(convertInt(args[0]), convertFloat(args[1]));
    }

    private void addProduct() {
        facade.addProduct(
                args[0], args[1], convertDouble(args[2]), convertFloat(args[3]),
                convertInt(args[4]), convertInt(args[5]),
                args[6], args[7], args[8]);
    }

    private void removeProduct() {

        facade.removeProduct(convertInt(args[0]));
    }

    private void addItem() {

        facade.addItem(
                convertInt(args[0]), args[1], args[2],
                convertInt(args[3]), convertDate(args[4]),
                Objects.requireNonNull(convertBoolean(args[5]))
        );
    }

    private void removeItem() {

        facade.removeItem(convertInt(args[0]), convertInt(args[0]));
    }

    private void updateItemDefect() {

        facade.updateItemDefect(
                convertInt(args[0]),
                convertInt(args[1]),
                convertBoolean(args[2]),
                args[3]
        );
    }

    private void getItemLocation() {
        facade.getItemLocation(convertInt(args[0]), convertInt(args[0]));
    }

    private void changeItemLocation() {


        if (args.length == 3 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1) {
            try {
                facade.setItemLocation(convertInt(args[0]), convertInt(args[0]), args[2]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void changeItemOnShelf() {

        facade.setItemOnShelf(
                convertInt(args[0]),
                convertInt(args[1]),
                convertBoolean(args[2])
        );
    }

    private void createMissingReport() {
        facade.createMissingReport(args[0], args[1]);
    }

    private void createExpiredReport() {
        facade.createExpiredReport(args[0], args[1]);
    }

    private void createSurplusesReport() {

        facade.createSurplusesReport(args[0], args[1]);
    }

    private void createDefectiveReport() {
        facade.createDefectiveReport(args[0], args[1]);
    }

    private void createBySupplierReport() {
        facade.createBySupplierReport(args[0], args[1], convertInt(args[2]));
    }

    private void createByProductReport() {
        facade.createByProductReport(args[0], args[1], args[2]);
    }

    private void createByCategoryReport() {
        facade.createByCategoryReport(args[0], args[1], args[2], args[3], args[4]);
    }

    private void removeReport() {

        facade.removeReport(convertInt(args[0]));
    }

    private void getReport() {
        facade.getReport(convertInt(args[0]));
    }

    private void createPeriodicOrder() {
        //facade.createPeriodicOrder(convertMap(args[0]), convertInt(args[1]));
    }

    private void confirmOrder() {
        Map<Integer, Integer> order_details = facade.confirmOrder(convertInt(args[0])).data;
        System.out.println("Order details");
        for (Map.Entry<Integer, Integer> pair : order_details.entrySet()) {
            System.out.println(pair.getKey() + " - " + pair.getValue());
        }
        System.out.println("please enter actual amount delivered\n(example format: \"[id0]-[amount0]_[id1]-[amount1]\"):");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        facade.confirmOrderAmount(0, convertMap(input)); // TODO
    }

    //converters
    private Map<Integer, Integer> convertMap(String input) {
        Map<Integer, Integer> values = new HashMap<>();
        String[] pairs = input.split("_");
        for (String pair : pairs) {
            String[] kv = pair.split("-");
            values.put(convertInt(kv[0]), convertInt(kv[1]));
        }
        return values;
    }

    private boolean convertBoolean(String input) {
        return Boolean.parseBoolean(input);
    }

    private double convertDouble(String input) {
        return Double.parseDouble(input);
    }

    private float convertFloat(String input) {
        return Float.parseFloat(input);
    }

    private LocalDate convertDate(String input) {
        DateTimeFormatter formatter;
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(input, formatter);
    }

    private int convertInt(String input) {
        return Integer.parseInt(input);
    }

}
