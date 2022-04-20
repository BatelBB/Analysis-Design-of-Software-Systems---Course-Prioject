package assignment1.PresentationLayer;

import assignment1.BusinessLayer.Entity.Contact;
import assignment1.BusinessLayer.Entity.PaymentCondition;
import assignment1.BusinessLayer.Service.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class PresentationController {
    private static Menu menu = new Menu();
    private static UserInput input = UserInput.getInstance();
    private static UserOutput output = UserOutput.getInstance();
    private static Service service = new Service();

    public static void main(String[] args){
        output.println("Welcome to the supplier module! What would you like to do?");

        int userInput = input.nextInt(menu.getMainMenu());
        while(true){
            switch (userInput){
                case(1): {
                    userInput = input.nextInt(menu.getSupplierSubmenu());
                    switch (userInput){
                        case(1): {
                            //Create Supplier Card
                            int ppn = input.nextInt("Enter supplier's ppn number:");
                            int bankAccount = input.nextInt("Enter supplier's bank account number:");
                            String name = input.nextString("Enter supplier's company name:");
                            boolean isDelivering = input.nextBoolean("Is the supplier delivering by himself? " +
                                    "true/false");
                            PaymentCondition paymentCondition = choosePayment("Which way will the supplier pay?");
                            DayOfWeek day = isDelivering ? chooseDay() : null;
                            String contactName = input.nextString("Enter the supplier's contact name:");
                            String email = input.nextString("Enter the supplier's contact email:");
                            String phoneNum = input.nextString("Enter the supplier's contact phone number:");
                            service.createSupplier(ppn,bankAccount,name,isDelivering,paymentCondition,day,
                                    new Contact(contactName,email, phoneNum));
                            break;
                        }
                        case(2): {
                            //Edit existing supplier card
                            int ppn = input.nextInt("Enter the ppn number");
                            output.println("What do you want to edit?");
                            int edit = input.nextInt(menu.getSupplierEditSubmenu());
                            try {
                                switch (edit){
                                    case(1):{
                                        service.getSupplier(ppn).setPpn(input.nextInt("Enter ppn number:"));
                                        break;
                                    }
                                    case(2):{
                                        service.getSupplier(ppn).setBankNumber(input.nextInt("Enter bank account:"));
                                        break;
                                    }
                                    case(3):{
                                        service.getSupplier(ppn).setName(input.nextString("Enter name:"));
                                        break;
                                    }
                                    case(4):{
                                        service.getSupplier(ppn).setDelivering(
                                                input.nextBoolean("Is delivering? true/false"));
                                        break;
                                    }
                                    case(5):{
                                        service.getSupplier(ppn).setPaymentCondition(choosePayment(
                                                input.nextString("Which way will the supplier pay?")));
                                        break;
                                    }
                                    case(6):{
                                        service.getSupplier(ppn).setRegularSupplyingDays(chooseDay());
                                        break;
                                    }
                                    case(7):{
                                        String contactName = input.nextString("Enter the supplier's contact name:");
                                        String email = input.nextString("Enter the supplier's contact email:");
                                        String phoneNum = input.nextString("Enter the supplier's contact phone number:");
                                        service.getSupplier(ppn).setContact(new Contact(contactName, email, phoneNum));
                                        break;
                                    }

                                }

                            }catch (Exception e){
                                output.print(e.getMessage());
                            }
                            break;
                        }
                        case(3):{
                            //Delete existing supplier
                            int ppn = input.nextInt("Enter the ppn number");
                            service.deleteSupplier(ppn);
                            break;
                        }
                    }
                    break;
                }
                case(2): {
                    userInput = input.nextInt(menu.getItemSubmenu());
                    switch (userInput){
                        case(1):{
                            //Create new item
                            int ppn = input.nextInt("Enter the supplier's ppn number");
                            int catalog = input.nextInt("Enter the item's catalog number");
                            String name = input.nextString("Enter the name of the item");
                            String category = input.nextString("Enter the item's category");
                            float price = (float) input.nextInt("Enter the item's price");
                            service.createItem(ppn,catalog,name,category,price);
                            break;
                        }
                        case(2): {
                            //Edit catalog number of existing item
                            int ppn = input.nextInt("Enter the supplier's ppn number");
                            int catalog = input.nextInt("Enter the item's catalog number");
                            try{
                                service.getItem(ppn, catalog).setCatalogNumber(
                                        input.nextInt("Enter new catalog number"));
                            }catch (Exception e){
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case(3): {
                            //edit price of existing item
                            int ppn = input.nextInt("Enter the supplier's ppn number");
                            int catalog = input.nextInt("Enter the item's catalog number");
                            try{
                                service.getItem(ppn, catalog).setPrice(input.nextInt("Enter new price"));
                            }catch (Exception e){
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case(4):{
                            //edit name of existing item
                            int ppn = input.nextInt("Enter the supplier's ppn number");
                            int catalog = input.nextInt("Enter the item's catalog number");
                            try{
                                service.getItem(ppn, catalog).setName(input.nextString("Enter new name"));
                            }catch (Exception e){
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case(5):{
                            //edit category of existing item
                            int ppn = input.nextInt("Enter the supplier's ppn number");
                            int catalog = input.nextInt("Enter the item's catalog number");
                            try{
                                service.getItem(ppn, catalog).setCategory(input.nextString("Enter new category"));
                            }catch (Exception e){
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case(6): {
                            //delete existing item
                            int ppn = input.nextInt("Enter the supplier's ppn number");
                            int catalog = input.nextInt("Enter the item's catalog number");
                            try{
                                service.deleteItem(service.getItem(ppn, catalog));
                            }
                            catch (Exception e){
                                output.println(e.getMessage());
                            }
                            break;
                        }
                    }
                }
                break;
                case(3):{
                    userInput = input.nextInt(menu.getOrderSubmenu());
                    switch (userInput){
                        case(1):{
                            //create new order
                            int ppn = input.nextInt("Enter the supplier's ppn number");
                            try{
                                service.createOrder(service.getSupplier(ppn),LocalDate.now(), null);
                            }catch (Exception e){
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case(2):{
                            //edit existing order
                            // TODO - What should we edit exactly?
                            break;
                        }
                        case(3):{
                            //delete existing order
                            // TODO - should we delete a whole order?
                            break;
                        }
                        case(4):{
                            //display existing order
                            // TODO - How should we display it?
                            break;
                        }
                    }
                }
                break;
                case(4):{
                    userInput = input.nextInt(menu.getQuantityAgreementSubmenu());
                    switch (userInput){
                        case(1):{
                            //create new quantity agreement
                            int ppn = input.nextInt("Enter the supplier's ppn number");
                            int catalog = input.nextInt("Enter the item's catalog number");
                            int amount = input.nextInt("For which amount is the discount applicable?");
                            float discount = (float) input.nextInt("What would be the discount for this amount?");
                            try{
                                service.createDiscount(service.getItem(ppn, catalog), amount, discount);
                            }catch (Exception e){
                                output.println(e.getMessage());
                            }
                            break;
                        }
                        case(2):{
                            //edit existing quantity agreement
                            //TODO - Should we edit this? Maybe create just new one and delete the other
                            break;
                        }
                        case(3):{
                            //delete existing quantity agreement
                            int ppn = input.nextInt("Enter the supplier's ppn number");
                            int catalog = input.nextInt("Enter the item's catalog number");
                            int amount = input.nextInt("Enter the discount amount you would like to delete");
                            try{
                                service.deleteDiscount(service.getDiscount(amount, ppn, catalog));
                            }catch (Exception e){
                                output.println(e.getMessage());
                            }
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    private static DayOfWeek chooseDay() {
        return DayOfWeek.valueOf(input.nextString("Enter day of week:").toUpperCase());
    }

    private static PaymentCondition choosePayment(String s) {
        output.println(s);
        int pay = input.nextInt("1. Direct Debit\n2. Credit");
        return pay == 1 ? PaymentCondition.DirectDebit : PaymentCondition.Credit;
    }


}
