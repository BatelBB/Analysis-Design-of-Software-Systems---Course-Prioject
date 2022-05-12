package adss_group_k.PresentationLayer.Suppliers;

import adss_group_k.BusinessLayer.Suppliers.Entity.MutableContact;
import adss_group_k.BusinessLayer.Suppliers.Entity.PaymentCondition;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Item;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Order;
import adss_group_k.BusinessLayer.Suppliers.Entity.readonly.Supplier;
import adss_group_k.BusinessLayer.Suppliers.Service.SupplierService;
import adss_group_k.BusinessLayer.Suppliers.Service.ServiceResponseWithData;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

public class PresentationController {
    private static UserInput input = UserInput.getInstance();
    private static UserOutput output = UserOutput.getInstance();
    private static SupplierService service = new SupplierService();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        output.println("Welcome to the supplier module!");
        if (input.nextBoolean("Would you like to load example data?")) {
            service.seedExample();
            output.println("example data loaded.");
        }
    }
    public static void startSupplierMenu() {
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
                                String email = input.nextString("Enter the supplier's contact email: ");
                                String phoneNum = input.nextString("Enter the supplier's contact phone number: ");
                                output.print(service.createSupplier(ppn, bankAccount, name, isDelivering,
                                        paymentCondition, day, new MutableContact(contactName, email, phoneNum)).data.toString());
                                break;
                            }
                            case (2): {
                                //Edit existing supplier card
                                int ppn = checkPPN("Enter the ppn number: ");
                                output.println("What do you want to edit? ");
                                int edit = input.nextInt(Menu.getSupplierEditSubmenu());
                                try {
                                    Supplier supplier = service.getSupplier(ppn);
                                    switch (edit) {
                                        case (1): {
                                            //PPN NUMBER
                                            output.println("[Sorry, this operation isn't available]");
                                            break;
                                        }
                                        case (2): {
                                            //Edit bank account
                                            int bankAct = input.nextInt("Enter bank account: ");
                                            service.setSupplierBankAccount(supplier, bankAct);
                                            break;
                                        }
                                        case (3): {
                                            //Edit company name
                                            String newName = input.nextString("Enter name: ");
                                            service.setSupplierCompanyName(supplier, newName);
                                            break;
                                        }
                                        case (4): {
                                            //Edit delivery
                                            boolean newValue = input.nextBoolean("Is delivering?");
                                            service.setSupplierIsDelivering(supplier, newValue);
                                            break;
                                        }
                                        case (5): {
                                            //edit payment condition
                                            PaymentCondition payment = choosePayment(
                                                    "Which way will the supplier pay? ");
                                            service.setSupplierPaymentCondition(supplier, payment);
                                            break;
                                        }
                                        case (6): {
                                            //edit supplying days
                                            service.setSupplierRegularSupplyingDays(supplier, chooseDay());
                                            break;
                                        }
                                        case (7): {
                                            //Edit contact
                                            String contactName = input.nextString("Enter the supplier's contact name: ");
                                            String email = input.nextString("Enter the supplier's contact email: ");
                                            String phoneNum = input.nextString(
                                                    "Enter the supplier's contact phone number: ");
                                            service.setSupplierContact(supplier, contactName, phoneNum, email);
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
                                output.println(service.toStringSupplier());
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
                                String name = input.nextString("Enter the name of the item: ");
                                String category = input.nextString("Enter the item's category: ");
                                float price = (float) input.nextInt("Enter the item's price: ");
                                output.print(service.createItem(ppn, catalog, name, category, price).data.toString());
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
                                service.setPrice(item, input.nextFloat("Enter new price: "));
                                break;
                            }
                            case (4): {
                                //edit name of existing item
                                int[] arr = checkItem();
                                Item item = service.getItem(arr[0], arr[1]).data;
                                String name = input.nextString("Enter new name: ");
                                service.setItemName(item, name);
                                break;
                            }
                            case (5): {
                                //edit category of existing item

                                int[] arr = checkItem();
                                Item item = service.getItem(arr[0], arr[1]).data;
                                String category = input.nextString("Enter new category: ");
                                service.setItemCategory(item, category);
                                break;
                            }
                            case (6): {
                                //delete existing item
                                int[] arr = checkItem();
                                service.deleteItem(service.getItem(arr[0], arr[1]).data);
                                break;
                            }
                            case (7): {
                                //see summery of items
                                output.println(service.toStringItems());
                                break;
                            }
                        }
                        break;
                    }
                    case (3): {
                        userInput = input.nextInt(Menu.getOrderSubmenu());
                        switch (userInput) {
                            case (1): {
                                //create new order
                                Order order = null;
                                int ppn = checkPPN("Enter the supplier's ppn number: ");
                                LocalDate ordered = input.nextDate("What is the order date? ");
                                LocalDate deliver = input.nextDate("When is the order supposed to be delivered? ");
                                try {
                                    ServiceResponseWithData<Order> serviceResponse = service.createOrder(
                                            service.getSupplier(ppn), ordered, deliver, Order.OrderType.Periodical);
                                    order = serviceResponse.data;
                                    String err = serviceResponse.error;
                                    if (err != null) {
                                        output.println(err);
                                        break;
                                    }

                                } catch (Exception e) {
                                    output.println(e.getMessage());
                                }
                                output.println("Now it's time to add items to the order");
                                boolean retry = true;
                                int nextInt = 0;
                                while (retry) {
                                    int[] arr = checkItem();
                                    int amount = input.nextInt("How much of this item do you want to order? ");
                                    service.orderItem(order, service.getItem(arr[0], arr[1]).data, amount);
                                    String more = input.nextString("Do you want to add more items? n/y ");
                                    if (more.equals("n")) {
                                        retry = false;
                                    }
                                }
                                output.println(order.toString());
                                break;
                            }
                            case (2): {
                                //delete existing order
                                int ppn = checkPPN("Enter the supplier's ppn number: ");
                                service.deleteOrder(service.getOrder(ppn).data);
                                break;
                            }
                            case (3): {
                                //edit ordered date
                                int id = input.nextInt("Enter order's id number, see summery for info ");
                                checkId(id);
                                LocalDate delivered = input.nextDate("When is the order ordered? ");
                                try {
                                    Order order = service.getOrder(id).data;
                                    service.updateOrderOrdered(order, delivered);
                                } catch (Exception e) {
                                    output.println(e.getMessage());
                                }
                                break;
                            }
                            case (4): {
                                //edit delivery date
                                int id = input.nextInt("Enter order's id number, see summery for info ");
                                checkId(id);
                                LocalDate delivered = input.nextDate("When is the order supposed to be delivered? ");
                                try {
                                    Order order = service.getOrder(id).data;
                                    service.updateOrderProvided(order, delivered);
                                } catch (Exception e) {
                                    output.println(e.getMessage());
                                }
                                break;
                            }
                            case (5): {
                                int id = input.nextInt("Enter order's id number, see summery for info ");
                                checkId(id);
                                int[] itemCoords = checkItem();
                                try {
                                    Item item = service.getItem(itemCoords[0], itemCoords[1]).data;
                                    Order order = service.getOrder(id).data;
                                    service.updateOrderAmount(order, item, input.nextInt("Enter amount to order"));
                                } catch (Exception e) {
                                    output.println(e.getMessage());
                                }
                                break;
                            }
                            case (6): {
                                //see summery of all orders
                                output.print(service.toStringOrders());
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
                                output.println(service.toStringQuantity());
                                break;
                            }
                        }
                        break;
                    }

                }
            }
        }


    private static void createDiscount() {
        int[] arr = checkItem();
        int amount = input.nextInt("For which amount is the discount applicable?: ");
        float discount = input.nextFloat("What would be the discount for this amount?: ");
        output.print(service.createDiscount(service.getItem(arr[0], arr[1]).data, amount, discount)
                .data.toString());

    }

    private static void deleteDiscount() {
        int[] arr = checkDiscount();
        try {
            service.deleteDiscount(service.getDiscount(arr[2], arr[0], arr[1]));
        } catch (Exception e) {
            output.println(e.getMessage());
        }
    }

    private static int[] checkDiscount() {
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

    private static void checkId(int id) {
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


    private static int[] checkItem() {
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

    private static int checkPPN(String message) {
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


    private static DayOfWeek chooseDay() {
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

    private static PaymentCondition choosePayment(String s) {
        output.println(s);
        int pay = input.nextInt("1. Direct Debit\n2. Credit\n");
        return pay == 1 ? PaymentCondition.DirectDebit : PaymentCondition.Credit;
    }
}
