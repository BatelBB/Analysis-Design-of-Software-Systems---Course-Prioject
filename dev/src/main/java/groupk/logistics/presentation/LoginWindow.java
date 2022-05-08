package groupk.logistics.presentation;

import groupk.logistics.service.Response;
import groupk.logistics.service.Service;
import java.io.*;

import java.util.Scanner;

public class LoginWindow {
    public void login() {
        boolean wantToBack = false;
        Service service = new Service();
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        while (!wantToBack) {
            System.out.println("Enter Username");
            String username = myObj.nextLine();
            System.out.println("Enter Password");
            String password = myObj.nextLine();
            Response response = service.Login(username,password);
            if(response.ErrorOccured())
            {
                System.out.println(response.getErrorMessage());
                System.out.println("Enter 1 to try again or 2 for return to the menu");
                String choice = myObj.nextLine();
                while (!(choice.equals("1")|choice.equals("2")))
                {
                    System.out.println("wrong input, Enter 1 to try again or 2 for return to the menu");
                    choice = myObj.nextLine();
                }
                if(choice.equals("2")) wantToBack = true;
            }
            else
            {
                System.out.println("Login completed");
                User user;
                if(response.getValue().equals(true))
                    user = new Driver();
                else
                    user = new TruckManager();
                user.act();
                wantToBack = true;
            }
        }
    }
}
