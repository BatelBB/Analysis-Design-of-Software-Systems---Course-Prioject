package groupk.shared.PresentationLayer.Inventory;

import groupk.shared.PresentationLayer.Suppliers.UserOutput;
import groupk.shared.service.Service;
import groupk.shared.service.dto.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InventoryPresentationFacade {
    private String[] args;
    private final Service service;

    public InventoryPresentationFacade(Service service) {
        this.service = service;
    }

    public void execute(String input, Employee currentUser) {
        // TODO Use currentUser.role to check the role of the active user, when currentUser is null no user is logged in.
        try {
            if (!input.equals("exit")) {
                String command = input.substring(0, input.indexOf(" "));
                args = input.substring(input.indexOf(" ") + 1).split(",", -1);
                switch (command) {
                    case "addCategory":
                        if (checkUserStorage(currentUser)) addCategory();
                         break;
                    case "removeCategory":
                        if (checkUserStorage(currentUser)) removeCategory();
                         break;
                    case "addSubCategory":
                        if (checkUserStorage(currentUser)) addSubCategory();
                         break;
                    case "removeSubCategory":
                        if (checkUserStorage(currentUser)) removeSubCategory();
                         break;
                    case "addSubSubCategory":
                        if (checkUserStorage(currentUser)) addSubSubCategory();
                         break;
                    case "removeSubSubCategory":
                        if (checkUserStorage(currentUser)) removeSubSubCategory();
                         break;
                    case "updateCategoryCusDiscount":
                        if (checkUserStorage(currentUser)) updateCategoryCusDiscount();
                         break;
                    case "updateProductCusDiscount":
                        if (checkUserStorage(currentUser)) updateProductCusDiscount();
                         break;
                    case "updateItemCusDiscount":
                        if (checkUserStorage(currentUser)) updateItemCusDiscount();
                         break;
                    case "updateProductCusPrice":
                        if (checkUserStorage(currentUser)) updateProductCusPrice();
                         break;
                    case "addProduct":
                        if (checkUserStorage(currentUser)) addProduct();
                         break;
                    case "removeProduct":
                        if (checkUserStorage(currentUser)) removeProduct();
                         break;
                    case "addItem":
                        if (checkUserStorage(currentUser)) addItem();
                         break;
                    case "removeItem":
                        if (checkUserStorage(currentUser)) removeItem();
                         break;
                    case "updateItemDefect":
                        if (checkUserStorage(currentUser)) updateItemDefect();
                         break;
                    case "getItemLocation":
                        if (checkUserStorage(currentUser)) getItemLocation();
                         break;
                    case "changeItemLocation":
                        if (checkUserStorage(currentUser)) changeItemLocation();
                         break;
                    case "changeItemOnShelf":
                        if (checkUserStorage(currentUser)) changeItemOnShelf();
                         break;
                    case "createMissingReport":
                        if (checkUserStoreManager(currentUser)) createMissingReport();
                         break;
                    case "createExpiredReport":
                        if (checkUserStoreManager(currentUser)) createExpiredReport();
                         break;
                    case "createSurplusesReport":
                        if (checkUserStoreManager(currentUser)) createSurplusesReport();
                         break;
                    case "createDefectiveReport":
                        if (checkUserStoreManager(currentUser)) createDefectiveReport();
                         break;
                    case "createBySupplierReport":
                        if (checkUserStoreManager(currentUser)) createBySupplierReport();
                         break;
                    case "createByProductReport":
                        if (checkUserStoreManager(currentUser)) createByProductReport();
                         break;
                    case "createByCategoryReport":
                        if (checkUserStoreManager(currentUser)) createByCategoryReport();
                         break;
                    case "removeReport":
                        if (checkUserStoreManager(currentUser)) removeReport();
                         break;
                    case "getReport":
                        if (checkUserStoreManager(currentUser)) getReport();
                         break;
                    case "createPeriodicOrder":
                        if (checkUserStorage(currentUser)) createPeriodicOrder();
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

    private boolean checkUserStoreManager(Employee currentUser) {
        if(currentUser == null || !currentUser.role.equals(Employee.Role.StoreManager)){
            UserOutput.println("Only store manager can manage reports");
            return false;
        }
        return true;
    }

    private boolean checkUserStorage(Employee currentUser) {
        if(currentUser == null || !currentUser.role.equals(Employee.Role.Stocker)){
            UserOutput.println("Only Stocker can handle orders and items");
            return false;
        }
        return true;
    }


    //service callers
    private void addCategory() {
        service.addCategory(args[0]);
    }

    private void removeCategory() {
        service.removeCategory(args[0]);
    }

    private void addSubCategory() {
        service.addSubCategory(args[0], args[1]);
    }

    private void removeSubCategory() {
        service.removeSubCategory(args[0], args[1]);
    }

    private void addSubSubCategory() {

        service.addSubSubCategory(args[0], args[1], args[2]);
    }

    private void removeSubSubCategory() {
        service.removeSubSubCategory(args[0], args[1], args[2]);
    }

    private void updateCategoryCusDiscount() {

        service.updateCategoryCusDiscount(
                convertFloat(args[0]), convertDate(args[1]),
                convertDate(args[2]),
                args[3], args[4], args[5]);
    }

    private void updateProductCusDiscount() {

        service.updateProductCusDiscount(
                convertFloat(args[0]), convertDate(args[1]),
                convertDate(args[2]), convertInt(args[3]));
    }

    private void updateItemCusDiscount() {

        service.updateItemCusDiscount(
                convertFloat(args[2]),
                convertDate(args[3]),
                convertDate(args[4]),
                convertInt(args[0]),
                convertInt(args[1])
        );
    }

    private void updateProductCusPrice() {

        service.updateProductCusPrice(convertInt(args[0]), convertFloat(args[1]));
    }

    private void addProduct() {
        service.addProduct(
                args[0], args[1], convertDouble(args[2]), convertFloat(args[3]),
                convertInt(args[4]), convertInt(args[5]),
                args[6], args[7], args[8]);
    }

    private void removeProduct() {

        service.removeProduct(convertInt(args[0]));
    }

    private void addItem() {

        service.addItem(
                convertInt(args[0]), args[1], args[2],
                convertInt(args[3]), convertDate(args[4]),
                Objects.requireNonNull(convertBoolean(args[5]))
        );
    }

    private void removeItem() {

        service.removeItem(convertInt(args[0]), convertInt(args[0]));
    }

    private void updateItemDefect() {

        service.updateItemDefect(
                convertInt(args[0]),
                convertInt(args[1]),
                convertBoolean(args[2]),
                args[3]
        );
    }

    private void getItemLocation() {
        service.getItemLocation(convertInt(args[0]), convertInt(args[0]));
    }

    private void changeItemLocation() {


        if (args.length == 3 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1) {
            try {
                service.setItemLocation(convertInt(args[0]), convertInt(args[0]), args[2]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void changeItemOnShelf() {

        service.setItemOnShelf(
                convertInt(args[0]),
                convertInt(args[1]),
                convertBoolean(args[2])
        );
    }

    private void createMissingReport() {
        service.createMissingReport(args[0], args[1]);
    }

    private void createExpiredReport() {
        service.createExpiredReport(args[0], args[1]);
    }

    private void createSurplusesReport() {

        service.createSurplusesReport(args[0], args[1]);
    }

    private void createDefectiveReport() {
        service.createDefectiveReport(args[0], args[1]);
    }

    private void createBySupplierReport() {
        service.createBySupplierReport(args[0], args[1], convertInt(args[2]));
    }

    private void createByProductReport() {
        service.createByProductReport(args[0], args[1], args[2]);
    }

    private void createByCategoryReport() {
        service.createByCategoryReport(args[0], args[1], args[2], args[3], args[4]);
    }

    private void removeReport() {

        service.removeReport(convertInt(args[0]));
    }

    private void getReport() {
        service.getReport(convertInt(args[0]));
    }

    private void createPeriodicOrder() {
        //facade.createPeriodicOrder(convertMap(args[0]), convertInt(args[1]));
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
