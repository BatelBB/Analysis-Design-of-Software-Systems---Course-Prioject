package groupk.shared.PresentationLayer.Inventory;

import groupk.shared.business.Facade;
import groupk.shared.service.Inventory.CategoryService;
import groupk.shared.service.Inventory.ProductService;
import groupk.shared.service.Inventory.ReportService;
import groupk.shared.service.ServiceBase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Supplier;

public class InventoryPresentationFacade {
    private String[] args;
    private final CategoryService categories;
    private final ProductService products;
    private final ReportService reports;
    private final Facade facade;

    public InventoryPresentationFacade(Facade facade, CategoryService categories, ProductService products, ReportService reports) {
        this.facade = facade;
        this.categories = categories;
        this.products = products;
        this.reports = reports;
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
        categories.addCategory(args[0]);
    }

    private void removeCategory() {
        categories.removeCategory(args[0], true);
    }

    private void addSubCategory() {
        categories.addSubCategory(args[0], args[1]);
    }

    private void removeSubCategory() {
        categories.removeSubCategory(args[0], args[1], true);
    }

    private void addSubSubCategory() {

        categories.addSubSubCategory(args[0], args[1], args[2]);
    }

    private void removeSubSubCategory() {
        categories.removeSubSubCategory(args[0], args[1], args[2], true);
    }

    private void updateCategoryCusDiscount() {

        products.updateCategoryCusDiscount(
                convertFloat(args[0]), convertDate(args[1]),
                convertDate(args[2]),
                args[3], args[4], args[5]);
    }

    private void updateProductCusDiscount() {

        products.updateProductCusDiscount(
                convertFloat(args[0]), convertDate(args[1]),
                convertDate(args[2]), convertInt(args[3]));
    }

    private void updateItemCusDiscount() {

        products.updateItemCusDiscount(
                convertInt(args[0]),
                convertInt(args[1]),
                convertFloat(args[2]),
                convertDate(args[3]),
                convertDate(args[4])
        );
    }

    private void updateProductCusPrice() {

        products.updateProductCusPrice(convertInt(args[0]), convertFloat(args[1]));
    }

    private void addProduct() {
        products.addProduct(
                args[0], args[1], convertDouble(args[2]), convertFloat(args[3]),
                convertInt(args[4]), convertInt(args[5]),
                args[6], args[7], args[8]);
    }

    private void removeProduct() {

        products.removeProduct(convertInt(args[0]));
    }

    private void addItem() {

        products.addItem(
                convertInt(args[0]), args[1], args[2],
                convertInt(args[3]), convertDate(args[4]),
                Objects.requireNonNull(convertBoolean(args[5]))
        );
    }

    private void removeItem() {

        products.removeItem(convertInt(args[0]), convertInt(args[0]));
    }

    private void updateItemDefect() {

        products.updateItemDefect(
                convertInt(args[0]),
                convertInt(args[1]),
                convertBoolean(args[2]),
                args[3]
        );
    }

    private void getItemLocation() {
        products.getItemLocation(convertInt(args[0]), convertInt(args[0]));
    }

    private void changeItemLocation() {


        if (args.length == 3 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1) {
            try {
                products.changeItemLocation(convertInt(args[0]), convertInt(args[0]), args[2]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void changeItemOnShelf() {

        products.changeItemOnShelf(
                convertInt(args[0]),
                convertInt(args[1]),
                convertBoolean(args[2])
        );
    }

    private void createMissingReport() {
        reports.createMissingReport(args[0], args[1]);
    }

    private void createExpiredReport() {
        reports.createExpiredReport(args[0], args[1]);
    }

    private void createSurplusesReport() {

        reports.createSurplusesReport(args[0], args[1]);
    }

    private void createDefectiveReport() {
        reports.createDefectiveReport(args[0], args[1]);
    }

    private void createBySupplierReport() {
        reports.createBySupplierReport(args[0], args[1], convertInt(args[2]));
    }

    private void createByProductReport() {
        reports.createByProductReport(args[0], args[1], args[2]);
    }

    private void createByCategoryReport() {
        reports.createByCategoryReport(args[0], args[1], args[2], args[3], args[4]);
    }

    private void removeReport() {

        reports.removeReport(convertInt(args[0]));
    }

    private void getReport() {
        reports.getReport(convertInt(args[0]));
    }

    private void createPeriodicOrder() {
        facade.createPeriodicOrder(convertMap(args[0]), convertInt(args[1]));
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
        facade.confirmOrderAmount(convertMap(input));
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
