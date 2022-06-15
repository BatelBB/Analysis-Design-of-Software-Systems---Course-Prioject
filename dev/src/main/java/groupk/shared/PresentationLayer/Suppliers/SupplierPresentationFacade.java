package groupk.shared.PresentationLayer.Suppliers;

import groupk.shared.business.Inventory.Service.InventoryService;
import groupk.shared.business.Suppliers.BussinessObject.Item;
import groupk.shared.business.Suppliers.BussinessObject.Supplier;
import groupk.shared.business.Suppliers.Service.ISupplierService;
import groupk.inventory_suppliers.dataLayer.dao.records.PaymentCondition;

import static groupk.shared.service.ServiceBase.*;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

public class SupplierPresentationFacade {
    private UserInput input = UserInput.getInstance();
    private UserOutput output = UserOutput.getInstance();
    private final ISupplierService service;
    private final InventoryService inventory;

    public SupplierPresentationFacade(ISupplierService supplierService, InventoryService inventory) {
        this.service = supplierService;
        this.inventory = inventory;
    }

    static Scanner scanner = new Scanner(System.in);

    public void startSupplierMenu() {
        while (true) {
            int userInput = input.nextInt(Menu.getMainMenu());
            switch (userInput) {
                case (1): {
                    userInput = input.nextInt(Menu.getSupplierSubmenu());
                    switch (userInput) {
                        case (1): {
                            //Create Supplier Card
                            int ppn = input.nextInt("Enter supplier's ppn number: ");
                            int bankAccount = input.nextInt("Enter supplier's bank account number: ");
                            String name = input.nextString("Enter supplier's company name: ");
                            boolean isDelivering = input.nextBoolean("Is the supplier delivering by himself?");
                            PaymentCondition paymentCondition = choosePayment("Which way will the supplier pay? ");
                            DayOfWeek day = isDelivering ? chooseDay() : null;
                            String contactName = input.nextString("Enter the supplier's contact name: ");
                            String address = input.nextString("Enter the supplier's contact address: ");
                            String phoneNum = input.nextPhone("Enter the supplier's contact phone number: ");
                            ResponseT<Supplier> supplier = service.createSupplier(ppn, bankAccount, name, isDelivering,
                                    paymentCondition, day, contactName, phoneNum, address);
                            if (supplier.success) {
                                output.print(supplier.data.toString());
                            } else {
                                output.println(supplier.error);
                            }
                            break;
                        }
                        case (2): {
                            //Edit existing supplier card
                            int ppn = checkPPN("Enter the ppn number: ");
                            output.println("What do you want to edit? ");
                            int edit = input.nextInt(Menu.getSupplierEditSubmenu());
                            try {
                                Supplier supplier = service.getSupplier(ppn).data;
                                switch (edit) {
                                    case (1): {
                                        //PPN NUMBER
                                        output.println("[Sorry, this operation isn't available]");
                                        break;
                                    }
                                    case (2): {
                                        //Edit bank account
                                        int bankAct = input.nextInt("Enter bank account: ");
                                        service.setSupplierBankAccount(ppn, bankAct);
                                        break;
                                    }
                                    case (3): {
                                        //Edit company name
                                        String newName = input.nextString("Enter name: ");
                                        service.setSupplierCompanyName(ppn, newName);
                                        break;
                                    }
                                    case (4): {
                                        //Edit delivery
                                        boolean newValue = input.nextBoolean("Is delivering?");
                                        service.setSupplierIsDelivering(ppn, newValue);
                                        break;
                                    }
                                    case (5): {
                                        //edit payment condition
                                        PaymentCondition payment = choosePayment(
                                                "Which way will the supplier pay? ");
                                        service.setSupplierPaymentCondition(ppn, payment);
                                        break;
                                    }
                                    case (6): {
                                        //edit supplying days
                                        service.setSupplierRegularSupplyingDays(ppn, chooseDay());
                                        break;
                                    }
                                    case (7): {
                                        //Edit contact
                                        String contactName = input.nextString("Enter the supplier's contact name: ");
                                        String address = input.nextString("Enter the supplier's contact address: ");
                                        String phoneNum = input.nextString(
                                                "Enter the supplier's contact phone number: ");
                                        service.setSupplierContact(ppn, contactName, phoneNum, address);
                                        break;
                                    }

                                }

                            } catch (Exception e) {
                                output.print(e.getMessage());
                            }
                            break;
                        }
                        case (3): {
                            //Delete existing supplier
                            int ppn = checkPPN("Enter the ppn number: ");
                            service.deleteSupplier(ppn);
                            break;
                        }
                        case (4): {
                            //See summery of all suppliers
                            service.getSuppliers().forEach(s -> output.println(s.toString()));
                            break;
                        }
                    }
                    break;
                }
                case (2): {
                    userInput = input.nextInt(Menu.getItemSubmenu());
                    switch (userInput) {
                        case (1): {
                            //Create new item
                            int ppn = checkPPN("Enter the supplier's ppn number: ");
                            int catalog = input.nextInt("Enter the item's catalog number: ");
                            int productNumber = input.nextInt("Enter product number:");
                            float price = (float) input.nextInt("Enter the item's price: ");
                            output.print(service.createItem(ppn, catalog, productNumber, price).data.toString());
                            break;
                        }
                        case (2): {
                            //Edit catalog number of existing item
                            output.println("[Sorry, this operation isn't available]");
                            break;
                        }
                        case (3): {
                            //edit price of existing item
                            int[] arr = checkItem();
                            Item item = service.getItem(arr[0], arr[1]).data;
                            service.setPrice(item.getSupplier().getPpn(), item.getCatalogNumber(), input.nextFloat("Enter new price: "));
                            break;
                        }
                        case (4):
                        case (5):
                        case (6): {
                            //edit name of existing item
                            output.println("this option is no longer supported.");
                            break;
                        }
                        case (7): {
                            //see summery of items
                            output.println(service.getItems().toString());
                            break;
                        }
                    }
                    break;
                }
                case (3): {
                    userInput = input.nextInt(Menu.getOrderSubmenu());
                    switch (userInput) {
//                        case (0): { //DELETE IT
//                            //create new order
//                            Order order = null;
//                            int ppn = checkPPN("Enter the supplier's ppn number: ");
//                            LocalDate ordered = input.nextDate("What is the order date? ");
//                            LocalDate deliver = input.nextDate("When is the order supposed to be delivered? ");
//                            OrderType orderType = input.nextEnum("choose order type", OrderType.class);
//                            try {
//                                ResponseT<Order> serviceResponse = service.createOrder(
//                                        ppn, ordered, deliver, orderType);
//                                order = serviceResponse.data;
//                                String err = serviceResponse.error;
//                                if (err != null) {
//                                    output.println(err);
//                                    break;
//                                }
//
//                            } catch (Exception e) {
//                                output.println(e.getMessage());
//                            }
//                            output.println("Now it's time to add items to the order");
//                            boolean retry = true;
//                            int nextInt = 0;
//                            while (retry) {
//                                int[] arr = checkItem();
//                                Supplier sup = checkBestSupplier(service.getItem(arr[0], arr[1]).data);
//                                output.println("There is a better supplier that supplying this item: "
//                                        + sup.getName() + " with the better price: " + minPrice + " instead of the price: " +
//                                        service.getItem(arr[0], arr[1]).data.getPrice());
//                                int amount = input.nextInt("How much of this item do you want to order? ");
//                                service.orderItem(order.getId(),
//                                        sup.getPpn(),
//                                        service.getItem(arr[0], arr[1]).data.getCatalogNumber(), amount);
//
//                                String more = input.nextString("Do you want to add more items? n/y ");
//                                if (more.equals("n")) {
//                                    retry = false;
//                                }
//                            }
//                            output.println(order.toString());
//                            break;
//                        }
                        case (1): {
                            //delete existing order
                            int ppn = checkPPN("Enter the supplier's ppn number: ");
                            service.deleteOrder(service.getOrder(ppn).data.getId());
                            break;
                        }
                        case (2): {
                            //edit ordered date
                            int id = input.nextInt("Enter order's id number, see summery for info ");
                            checkId(id);
                            LocalDate delivered = input.nextDate("When is the order ordered? ");
                            try {
                                service.setOrderOrdered(id, delivered);
                            } catch (Exception e) {
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case (3): {
                            //edit delivery date
                            int id = input.nextInt("Enter order's id number, see summery for info ");
                            checkId(id);
                            LocalDate delivered = input.nextDate("When is the order supposed to be delivered? ");
                            try {
                                service.setOrderProvided(id, delivered);
                            } catch (Exception e) {
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case (4): {
                            int id = input.nextInt("Enter order's id number, see summery for info ");
                            checkId(id);
                            int[] itemCoords = checkItem();
                            try {
                                Item item = service.getItem(itemCoords[0], itemCoords[1]).data;
                                service.updateOrderAmount(id, item.getSupplier().getPpn(),
                                        item.getCatalogNumber(), input.nextInt("Enter amount to order"));
                            } catch (Exception e) {
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case (5): {
                            //see summery of all orders
                            output.print(service.getOrders().toString());
                            break;
                        }
                    }
                    break;
                }
                case (4): {
                    userInput = input.nextInt(Menu.getQuantityAgreementSubmenu());
                    switch (userInput) {
                        case (1): {
                            //create new quantity agreement
                            createDiscount();
                            break;
                        }
                        case (2): {
                            //edit existing quantity agreement
                            deleteDiscount();
                            createDiscount();
                            break;
                        }
                        case (3): {
                            //delete existing quantity agreement
                            deleteDiscount();
                            break;
                        }
                        case (4): {
                            //summery of quantity discount
                            output.println(service.getDiscounts().toString());
                            break;
                        }
                    }
                    break;
                }
                case (5): {
                    return;
                }
                default: {
                    UserOutput.getInstance().println("Please select valid option.");
                }
            }
        }
    }

    private void createDiscount() {
        int[] arr = checkItem();
        int amount = input.nextInt("For which amount is the discount applicable?: ");
        float discount = input.nextFloat("What would be the discount for this amount?: ");
        output.print(service.createDiscount(service.getItem(arr[0], arr[1]).data.getSupplier().getPpn(),
                        service.getItem(arr[0], arr[1]).data.getCatalogNumber(), amount, discount)
                .data.toString());

    }

    private void deleteDiscount() {
        int[] arr = checkDiscount();
        try {
            service.deleteDiscount(service.getDiscount(arr[2], arr[0], arr[1]));
        } catch (Exception e) {
            output.println(e.getMessage());
        }
    }

    private int[] checkDiscount() {
        boolean retry = true;
        int[] arr = new int[]{};
        int amount = 0;
        while (retry) {
            arr = checkItem();
            amount = input.nextInt("Enter the discount amount you would like to delete: ");
            try {
                service.getDiscount(amount, arr[0], arr[1]);
                retry = false;
            } catch (Exception e) {
                output.println("This discount doesn't exist, try again.");
            }
        }
        return new int[]{arr[0], arr[1], amount};
    }

    private void checkId(int id) {
        boolean retry = true;
        int nextInt = 0;
        int curId = id;
        while (retry) {
            if (service.getOrder(curId).success)
                retry = false;
            else {
                curId = input.nextInt("The id doesn't exist please try again\n");
            }
        }
    }


    private int[] checkItem() {
        boolean retry = true;
        int nextInt = 0;
        int ppn = 0, catalog = 0;
        while (retry) {
            ppn = checkPPN("Enter the supplier's ppn number: ");
            catalog = input.nextInt("Enter the item's catalog number: ");
            if (service.getItem(ppn, catalog).success) {
                retry = false;
            } else output.println("The item doesn't exist, please try again.");

        }
        return new int[]{ppn, catalog};

    }

    private int checkPPN(String message) {
        boolean retry = true;
        int nextInt = 0;
        while (retry) {
            try {
                nextInt = input.nextInt(message);
                service.getSupplier(nextInt);
                retry = false;
            } catch (Exception e) {
                output.println("There isn't supplier with this ppn number, please try again.");
            }
        }
        return nextInt;
    }


    private DayOfWeek chooseDay() {
        boolean retry = true;
        DayOfWeek day = null;
        String nextString = "";
        while (retry) {
            try {
                nextString = input.nextString("Enter constant day of week: ");
                DayOfWeek.valueOf(nextString.toUpperCase());
                retry = false;
            } catch (Exception e) {
                output.println("Please try again.");
            }
        }
        return DayOfWeek.valueOf(nextString.toUpperCase());
    }

    private PaymentCondition choosePayment(String s) {
        output.println(s);
        int pay = input.nextInt("1. Direct Debit\n2. Credit\n");
        return pay == 1 ? PaymentCondition.DirectDebit : PaymentCondition.Credit;
    }
}
