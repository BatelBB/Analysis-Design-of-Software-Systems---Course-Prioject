package groupk.logistics.presentation;

import groupk.logistics.service.Response;
import groupk.logistics.service.Service;

import java.util.Scanner;

public class UserFunctionality {
    protected Service service;
    public UserFunctionality() throws Exception {
        service = new Service();
    }

    public void logOut() {
        Response response  = service.Logout();
        if(response.ErrorOccured())
            System.out.println(response.getErrorMessage());
        else
            System.out.println("Logout completed");
    }

    public void updatePassword() {
        System.out.println("Enter new password");
        Scanner in = new Scanner(System.in);
        String newPassword = in.next();
        Response response = service.updatePassword(newPassword);
        if(response.ErrorOccured())
            System.out.println(response.getErrorMessage());
        else
            System.out.println("password successfully changed");
    }

}
