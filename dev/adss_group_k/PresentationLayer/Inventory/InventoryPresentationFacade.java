package adss_group_k.PresentationLayer.Inventory;

import adss_group_k.BusinessLayer.Inventory.Service.CategoryService;
import adss_group_k.BusinessLayer.Inventory.Service.Objects.Product;
import adss_group_k.BusinessLayer.Inventory.Service.Objects.Report;
import adss_group_k.BusinessLayer.Inventory.Service.ProductService;
import adss_group_k.BusinessLayer.Inventory.Service.ReportService;
import adss_group_k.serviceLayer.ServiceBase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Supplier;

public class InventoryPresentationFacade {
    private String[] args;
    private final CategoryService categories;
    private final ProductService products;
    private final ReportService reports;

    public InventoryPresentationFacade(CategoryService categories, ProductService products, ReportService reports) {
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
        useService(args, 1, () ->  categories.addCategory(args[0]));
    }

    private void removeCategory() {
        useService(args, 1, () ->  categories.removeCategory(args[0], true));
    }

    private void addSubCategory() {
        useService(args, 2,
                () -> categories.addSubCategory(args[0], args[1]));
    }

    private void removeSubCategory() {
        useService(args, 2, () ->
                categories.removeSubCategory(args[0], args[1], true)
        );
    }

    private void addSubSubCategory() {
        useService(args, 3,
                () -> categories.addSubSubCategory(args[0], args[1], args[2])
        );
    }

    private void removeSubSubCategory() {
        useService(args, 3,
                () -> categories.removeSubSubCategory(args[0], args[1], args[2], true)
        );
    }

    private void updateCategoryCusDiscount() {
        useService(args, 6,
                () ->  products.updateCategoryCusDiscount(
                        convertFloat(args[0]), convertDate(args[1]),
                        convertDate(args[2]),
                        args[3], args[4], args[5])
        );
    }

    private void updateProductCusDiscount() {
        useService(args, 4, () ->
                products.updateProductCusDiscount(
                        convertFloat(args[0]), convertDate(args[1]),
                        convertDate(args[2]), convertInt(args[3]))
        );
    }

    private void updateItemCusDiscount() {
        useService(args, 5, () ->
                products.updateItemCusDiscount(
                        convertInt(args[0]),
                        convertInt(args[1]),
                        convertFloat(args[2]),
                        convertDate(args[3]),
                        convertDate(args[4])
            )
        );
    }

    private void updateProductCusPrice() {
        useService(args, 2, () ->
                products.updateProductCusPrice(convertInt(args[0]), convertFloat(args[1]))
        );
    }

    private void addProduct() {
        useService(args, 9, () -> products.addProduct(
                args[0], args[1], convertDouble(args[2]), convertFloat(args[3]),
                convertInt(args[4]), convertInt(args[5]),
                args[6], args[7], args[8])
        );
    }

    private void removeProduct() {
        useService(args, 1,
                () -> products.removeProduct(convertInt(args[0])));
    }

    private void addItem() {
        useService(args, 6,
                () -> products.addItem(
                        convertInt(args[0]), args[1], args[2],
                        convertInt(args[3]), convertDate(args[4]),
                        Objects.requireNonNull(convertBoolean(args[5]))
                )
        );
    }

    private void removeItem() {
        useService(args, 2,
                () -> products.removeItem(convertInt(args[0]), convertInt(args[0]))
        );
    }

    public void updateItemDefect() {
        useService(args, 4,
            () -> products.updateItemDefect(
                    convertInt(args[0]),
                    convertInt(args[1]),
                    convertBoolean(args[2]),
                    args[3]
            )
        );
    }

    public void getItemLocation() {
        useService(args, 2, () -> products.getItemLocation(convertInt(args[0]), convertInt(args[0])));
    }

    public void changeItemLocation() {
        useService(
                args, 3,
                () -> products.changeItemLocation(convertInt(args[0]), convertInt(args[0]), args[2])
        );

        if (args.length == 3 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1) {
            try {
                } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void changeItemOnShelf() {
        throw new RuntimeException(); // TODO
/*
        useService(args, 3,
                () -> // ?
        );
        if (args.length == 3 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1 && convertBoolean(args[2]) != null) {
            try {
                products.setItemOnShelf(convertInt(args[0]), convertInt(args[0]), convertBoolean(args[2]));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
*/
    }

    public void createMissingReport() {
        useService(args, 2,
                () -> reports.createMissingReport(args[0], args[1]));
    }

    public void createExpiredReport() {
        useService(args, 2,
                () -> reports.createExpiredReport(args[0], args[1]));
    }

    public void createSurplusesReport() {
        useService(args, 2,
                () -> reports.createSurplusesReport(args[0], args[1])
        );
    }

    public void createDefectiveReport() {
        useService(args, 2, () -> reports.createDefectiveReport(args[0], args[1]));
    }

    public void createBySupplierReport() {
        useService(args, 3,
                () -> reports.createBySupplierReport(args[0], args[1], convertInt(args[2]))
        );
    }

    public void createByProductReport() {
        useService(args, 3,
                () -> reports.createByProductReport(args[0], args[1], args[2])
        );
    }

    public void createByCategoryReport() {
        useService(args, 5,
            () -> reports.createByCategoryReport(args[0], args[1], args[2], args[3], args[4])
        );
    }

    public void removeReport() {
        useService(args, 1,
                () -> reports.removeReport(convertInt(args[0])));
    }

    public void getReport() {
        useService(args, 1,
                () -> reports.getReport(convertInt(args[0])));
    }

    //converters
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

    private void useService(String[] args, int argumentCount, Supplier<ServiceBase.Response> serviceUsage) {
        try {
            if (args.length != argumentCount) {
                throw new IllegalArgumentException("Expected " + argumentCount +
                        " arguments, but got " + args.length);
            }
            System.out.println(serviceUsage.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
