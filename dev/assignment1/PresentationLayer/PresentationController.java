package assignment1.PresentationLayer;

import java.util.Scanner;

public class PresentationController {
    private Scanner sc = new Scanner(System.in);
    private static Menu menu = new Menu();
    private static UserInput input = UserInput.getInstance();
    private static UserOutput output = UserOutput.getInstance();
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
                            break;
                        }
                        case(2): {
                            //Edit existing supplier card
                            break;
                        }
                        case(3):{
                            //Delete existing supplier
                            break;
                        }
                    }
                }
                case(2): {
                    userInput = input.nextInt(menu.getItemSubmenu());
                    switch (userInput){
                        case(1):{
                            //Create new item
                            break;
                        }
                        case(2): {
                            //Edit quantity of existing item
                            break;
                        }
                        case(3): {
                            //edit price of existing item
                            break;
                        }
                        case(4): {
                            //delete existing item
                            break;
                        }
                    }
                }
                case(3):{
                    userInput = input.nextInt(menu.getOrderSubmenu());
                    switch (userInput){
                        case(1):{
                            //create new order
                            break;
                        }
                        case(2):{
                            //edit existing order
                            break;
                        }
                        case(3):{
                            //delete existing order
                            break;
                        }
                        case(4):{
                            //display existing order
                            break;
                        }
                    }
                }
                case(4):{
                    userInput = input.nextInt(menu.getQuantityAgreementSubmenu());
                    switch (userInput){
                        case(1):{
                            //create new quantity agreement
                            break;
                        }
                        case(2):{
                            //edit existing quantity agreement
                            break;
                        }
                        case(3):{
                            //delete existing quantity agreement
                            break;
                        }
                    }
                }
            }
        }
    }


}
