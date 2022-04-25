package PresentationLayer;

import ServiceLayer.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PresentationModel {
    private final String command;
    private final String[] args;

    public PresentationModel(String input) {
        command = input.substring(0, input.indexOf(" "));
        args = input.substring(input.indexOf(" ")).split(",", -1);
    }

    public void execute(Service service) {
        switch (command) {
            case "addCategory" -> addCategory(service);
            case "removeCategory" -> removeCategory(service);
            case "addSubCategory" -> addSubCategory(service);
            case "removeSubCategory" -> removeSubCategory(service);
            case "addSubSubCategory" -> addSubSubCategory(service);
            case "removeSubSubCategory" -> removeSubSubCategory(service);
            case "updateCategoryCusDiscount" -> updateCategoryCusDiscount(service);
            case "updateProductCusDiscount" -> updateProductCusDiscount(service);
            case "updateItemCusDiscount" -> updateItemCusDiscount(service);
            case "updateProductManPrice" -> updateProductManPrice(service);
            case "updateProductCusPrice" -> updateProductCusPrice(service);
            case "addProduct" -> addProduct(service);
            case "removeProduct" -> removeProduct(service);
            case "addItem" -> addItem(service);
            case "removeItem" -> removeItem(service);
            case "updateItemDefect" -> updateItemDefect(service);
            case "getItemLocation" -> getItemLocation(service);
            case "changeItemLocation" -> changeItemLocation(service);
            case "changeItemOnShelf" -> changeItemOnShelf(service);
            default -> System.out.println("unknown command, aborting..");
        }
    }


    //service callers
    private void addCategory(Service service) {
        if (args.length == 1)
            service.addCategory(args[0]);
    }

    private void removeCategory(Service service) {
        if (args.length == 1)
            service.removeCategory(args[0]);
    }

    private void addSubCategory(Service service) {
        if (args.length == 2)
            service.addSubCategory(args[0], args[1]);
    }

    private void removeSubCategory(Service service) {
        if (args.length == 2)
            service.removeSubCategory(args[0], args[1]);
    }

    private void addSubSubCategory(Service service) {
        if (args.length == 3)
            service.addSubSubCategory(args[0], args[1], args[2]);
    }

    private void removeSubSubCategory(Service service) {
        if (args.length == 3)
            service.removeSubSubCategory(args[0], args[1], args[2]);
    }

    private void updateCategoryCusDiscount(Service service) {
        if (args.length == 6 && convertDouble(args[0]) != -1.0 && convertDate(args[1]) != null && convertDate(args[2]) != null) {
            service.updateCategoryCusDiscount(convertDouble(args[0]), convertDate(args[1]), convertDate(args[2]), args[3], args[4], args[5]);
        }
    }

    private void updateProductCusDiscount(Service service) {
        if (args.length == 4 && convertDouble(args[0]) != -1.0 && convertDate(args[1]) != null && convertDate(args[2]) != null && convertDouble(args[3]) != -1) {
            service.updateProductCusDiscount(convertDouble(args[0]), convertDate(args[1]), convertDate(args[2]), convertInt(args[3]));
        }
    }

    private void updateItemCusDiscount(Service service) {
        if (args.length == 5 && convertDouble(args[0]) != -1.0 && convertDate(args[1]) != null && convertDate(args[2]) != null && convertDouble(args[3]) != -1 && convertDouble(args[4]) != -1) {
            service.updateItemCusDiscount(convertDouble(args[0]), convertDate(args[1]), convertDate(args[2]), convertInt(args[3]), convertInt(args[4]));
        }
    }

    private void updateProductManPrice(Service service) {
        if (args.length == 2 && convertDouble(args[1]) != -1.0 && convertInt(args[0]) != -1) {
            service.updateProductManPrice(convertInt(args[0]), convertDouble(args[1]));
        }
    }

    private void updateProductCusPrice(Service service) {
        if (args.length == 2 && convertDouble(args[1]) != -1.0 && convertInt(args[0]) != -1) {
            service.updateProductCusPrice(convertInt(args[0]), convertDouble(args[1]));
        }
    }

    private void addProduct(Service service) {
        double man_price = convertDouble(args[2]);
        double cus_price = convertDouble(args[3]);
        int min_qty = convertInt(args[4]);
        int supply_time = convertInt(args[5]);
        if (args.length == 9 && man_price != -1.0 && cus_price != -1.0 && min_qty != -1 && supply_time != -1)
            service.addProduct(args[0], args[1], man_price, cus_price, min_qty, supply_time, args[6], args[7], args[8]);
    }

    private void removeProduct(Service service) {
        if (args.length == 1 && convertInt(args[0]) != -1)
            service.removeProduct(convertInt(args[0]));
    }

    private void addItem(Service service) {
        int product_id = convertInt(args[0]);
        LocalDateTime expiration_date = convertDate(args[4]);
        if (args.length == 6 && product_id != -1 && expiration_date != null && convertBoolean(args[5]) != null)
            service.addItem(product_id, args[1], args[2], args[3], expiration_date, Objects.requireNonNull(convertBoolean(args[5])));
    }

    private void removeItem(Service service) {
        if (args.length == 2 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1)
            service.removeItem(convertInt(args[0]), convertInt(args[0]));
    }

    public void updateItemDefect(Service service) {
        int product_id = convertInt(args[0]);
        int item_id = convertInt(args[1]);
        if (args.length == 4 && product_id != -1 && item_id != -1 && convertBoolean(args[2]) != null)
            service.updateItemDefect(product_id, item_id, convertBoolean(args[2]), args[3]);
    }

    public void getItemLocation(Service service) {
        if (args.length == 2 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1)
            service.getItemLocation(convertInt(args[0]), convertInt(args[0]));
    }

    public void changeItemLocation(Service service) {
        if (args.length == 3 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1)
            service.changeItemLocation(convertInt(args[0]), convertInt(args[0]), args[2]);
    }

    public void changeItemOnShelf(Service service) {
        if (args.length == 3 && convertInt(args[0]) != -1 && convertInt(args[1]) != -1 && convertBoolean(args[2]) != null)
            service.changeItemOnShelf(convertInt(args[0]), convertInt(args[0]), convertBoolean(args[2]));
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
