package PresentaionLayer;

import ServiceLayer.Response;
import ServiceLayer.Service;

import java.util.Scanner;

public class RegisterWindow {
    Service service = new Service();
    public RegisterWindow() throws Exception {

    }
    public void  register() {
        boolean wantToBack = false;
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        while (!wantToBack) {
            System.out.println("Enter name");
            String name = myObj.nextLine();
            System.out.println("Enter Username");
            String username = myObj.nextLine();
            System.out.println("Enter Password");
            String password = myObj.nextLine();
            System.out.println("Enter Role");
            String role = myObj.nextLine();
            System.out.println("Enter Code");
            String code = myObj.nextLine();
            Response response = service.registerUser(name,username,password,role,code);
            if(response.ErrorOccured())
            {
                System.out.println(response.getErrorMessage());
                System.out.println("Enter 1 to try again or 2 for return to the menu");
                String choice = myObj.nextLine();
                while (!(choice.equals("1") | choice.equals("2")))
                {
                    System.out.println("wrong input, Enter 1 to try again or 2 for return to the menu");
                    choice = myObj.nextLine();
                }
                if(choice.equals("2")) wantToBack = true;
            }

            else {
                System.out.println("Register completed");
                wantToBack = true;
            }
        }
    }
}
