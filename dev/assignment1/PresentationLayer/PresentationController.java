package assignment1.PresentationLayer;

import assignment1.BusinessLayer.Entity.Contact;
import assignment1.BusinessLayer.Entity.Item;
import assignment1.BusinessLayer.Entity.Order;
import assignment1.BusinessLayer.Entity.PaymentCondition;
import assignment1.BusinessLayer.Service.Service;
import assignment1.BusinessLayer.Service.ServiceResponseWithData;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

public class PresentationController {
    private static Menu menu = new Menu();
    private static UserInput input = UserInput.getInstance();
    private static UserOutput output = UserOutput.getInstance();
    private static Service service = new Service();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        output.println("Welcome to the supplier module! What would you like to do?");

        while (true) {
            int userInput = input.nextInt(menu.getMainMenu());
            switch (userInput) {
                case (1): {
                    userInput = input.nextInt(menu.getSupplierSubmenu());
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
                            service.createSupplier(ppn, bankAccount, name, isDelivering, paymentCondition, day,
                                    new Contact(contactName, email, phoneNum));
                            output.println(String.format("**SUMMERY:\nPPN Number: %d | Bank Account: %d | " +
                                            "Company Name: %s | Is Delivering?: %b | Payment Condition: %s | " +
                                            "Supplying Day: %s |\n Contact Name: %s | Contact Email: %s | " +
                                            "Contact Phone Number: %s",
                                    ppn, bankAccount, name, isDelivering, paymentCondition.toString(), day.toString(),
                                    contactName, email, phoneNum));

                            break;
                        }
                        case (2): {
                            //Edit existing supplier card
                            int ppn = checkPPN("Enter the ppn number: ");
                            output.println("What do you want to edit? ");
                            int edit = input.nextInt(menu.getSupplierEditSubmenu());
                            try {
                                switch (edit) {
                                    case (1): {
                                        //PPN NUMBER
                                        output.println("[Sorry, this operation isn't available]");
                                        break;
                                    }
                                    case (2): {
                                        //Edit bank account
                                        service.getSupplier(ppn).setBankNumber(input.nextInt("Enter bank account: "));
                                        break;
                                    }
                                    case (3): {
                                        //Edit company name
                                        service.getSupplier(ppn).setName(input.nextString("Enter name: "));
                                        break;
                                    }
                                    case (4): {
                                        //Edit delivery
                                        service.getSupplier(ppn).setDelivering(
                                                input.nextBoolean("Is delivering?"));
                                        break;
                                    }
                                    case (5): {
                                        //edit payment condition
                                        service.getSupplier(ppn).setPaymentCondition(choosePayment(
                                                "Which way will the supplier pay? "));
                                        break;
                                    }
                                    case (6): {
                                        //edit supplying days
                                        service.getSupplier(ppn).setRegularSupplyingDays(chooseDay());
                                        break;
                                    }
                                    case (7): {
                                        //Edit contact
                                        String contactName = input.nextString("Enter the supplier's contact name: ");
                                        String email = input.nextString("Enter the supplier's contact email: ");
                                        String phoneNum = input.nextString(
                                                "Enter the supplier's contact phone number: ");
                                        service.getSupplier(ppn).setContact(new Contact(contactName, email, phoneNum));
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
                    }
                    break;
                }
                case (2): {
                    userInput = input.nextInt(menu.getItemSubmenu());
                    switch (userInput) {
                        case (1): {
                            //Create new item
                            int ppn = checkPPN("Enter the supplier's ppn number: ");
                            int catalog = input.nextInt("Enter the item's catalog number: ");
                            String name = input.nextString("Enter the name of the item: ");
                            String category = input.nextString("Enter the item's category: ");
                            float price = (float) input.nextInt("Enter the item's price: ");
                            service.createItem(ppn, catalog, name, category, price);
                            output.println(String.format("**SUMMERY:\nSupplier's ppn: %d | " +
                                            "Item's catalog number: %d | Item's name: %s | Item's category: %s | " +
                                            "Item's price: %s",
                                    ppn, catalog, name, category, price));
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
                            try {
                                Item item = service.getItem(arr[0], arr[1]).data;
                                service.setPrice(item, input.nextFloat("Enter new price: "));
                            } catch (Exception e) {
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case (4): {
                            //edit name of existing item
                            int[] arr = checkItem();
                            try {
                                service.getItem(arr[0], arr[1]).data.setName(input.nextString("Enter new name: "));
                            } catch (Exception e) {
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case (5): {
                            //edit category of existing item
                            int[] arr = checkItem();
                            try {
                                service.getItem(arr[0], arr[1]).data.setCategory(input.nextString(
                                        "Enter new category: "));
                            } catch (Exception e) {
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case (6): {
                            //delete existing item
                            int[] arr = checkItem();
                            try {
                                service.deleteItem(service.getItem(arr[0], arr[1]).data);
                            } catch (Exception e) {
                                output.println(e.getMessage());
                            }
                            break;
                        }
                    }
                    break;
                }
                case (3): {
                    userInput = input.nextInt(menu.getOrderSubmenu());
                    switch (userInput) {
                        case (1): {
                            //create new order
                            Order order = null;
                            int ppn = checkPPN("Enter the supplier's ppn number: ");
                            LocalDate ordered = input.nextDate("What is the order date? ");
                            LocalDate deliver = input.nextDate("When is the order supposed to be delivered? ");
                            try {
                                ServiceResponseWithData<Order> serviceResponse = service.createOrder(
                                        service.getSupplier(ppn), ordered, deliver);
                                order = serviceResponse.data;
                                String err = serviceResponse.error;
                                if (err != null) {
                                    output.println(err);
                                    break;
                                }
                                output.println(String.format("**SUMMERY:\nOrder id: %d | Supplier's ppn: %d | " +
                                                "Order date: %s | Deliver Date: %s",
                                        order.id, ppn, ordered, deliver));

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
                                String more = input.nextString("Do you want add more items? n/y ");
                                if (more.equals("n"))
                                    retry = false;
                            }
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
                            LocalDate ordered = input.nextDate("What is the order date? ");
                            try {
                                service.getOrder(id).data.updateOrdered(ordered);
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
                                service.getOrder(id).data.updateProvided(delivered);
                            } catch (Exception e) {
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case (5): {
                            //edit item's amount
                            int id = input.nextInt("Enter order's id number, see summery for info ");
                            checkId(id);
                            int[] arr = checkItem();
                            int amount = input.nextInt("Enter the correct amount: ");
                            service.getOrder(id).data.orderItem(service.getItem(arr[0], arr[1]).data, amount);
                            break;
                        }
                    }
                    break;
                }
                case (4): {
                    userInput = input.nextInt(menu.getQuantityAgreementSubmenu());
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
        try {
            service.createDiscount(service.getItem(arr[0], arr[1]).data, amount, discount);
        } catch (Exception e) {
            output.println(e.getMessage());
        }
        output.println(String.format("**SUMMERY:\nSupplier's ppn: %d | Item's catalog: %d | " +
                        "Item's amount number: %d | Discount number: %s",
                arr[0], arr[1], amount, discount));
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
                service.getDiscount(arr[0], arr[1], amount);
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
        while (retry) {
            if (service.getOrder(id).error != null)
                retry = false;
            else
                output.println("The id doesn't exist please try again");
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
                nextString = input.nextString("Enter day of week: ");
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
