package groupk.logistics.presentation;

import groupk.logistics.service.Service;
import groupk.logistics.service.Response;

import java.util.List;

public class DriverFunctionality extends UserFunctionality{

    public DriverFunctionality() throws Exception {
        super();
    }

    public void AddLicense() {
        boolean done = false;
        while (!done) {
            System.out.println("Choose driver license: ");
            System.out.println("1. B");
            System.out.println("2. C");
            System.out.println("3. C1");
            System.out.println("4. C + E");
            String dLicense = Main.getChoiceFromArray(new String[]{"B", "C", "C1", "C+E"});
            Response response = service.addLicenseToDriver(dLicense);
            if (response.ErrorOccured())
                System.out.println(response.getErrorMessage());
            else
                System.out.println("The driver's license was successfully added");
            System.out.println("Do you want to add another driver license? choose your selection:");
            System.out.println("1. Yes");
            System.out.println("2. No");
            String startAgain = Main.getChoiceFromArray(new String[]{"Yes", "No"});
            if(startAgain.equals("No"))
                done = true;
        }
    }

    public void removeLicense() {
        boolean done = false;
        while (!done) {
            Response<List<String>> licenses = service.getLicenses();
            if (licenses.ErrorOccured()) {
                System.out.println(licenses.getErrorMessage());
                done = true;
            }
            else if (licenses.getValue() == null | licenses.getValue().size() == 0) {
                System.out.println("Oops, first you need to add driver licenses to your profile");
                done = true;
            }
            else {
                System.out.println("Choose driver license to remove: ");
                Main.printOptionsList(licenses.getValue());
                String dLicense = Main.getChoiceFromArray(licenses.getValue().toArray(new String[licenses.getValue().size()]));
                Response response = service.removeLicenseFromDriver(dLicense);
                if (response.ErrorOccured())
                    System.out.println(response.getErrorMessage());
                else
                    System.out.println("The driver's license was successfully removed");
                System.out.println("Do you want to add another driver license? choose your selection:");
                System.out.println("1. Yes");
                System.out.println("2. No");
                String startAgain = Main.getChoiceFromArray(new String[]{"Yes", "No"});
                if(startAgain.equals("No"))
                    done = true;
            }
        }
    }

    public void printLicenses() {
        Response<List<String>> licenses = service.getLicenses();
        if (licenses.ErrorOccured())
            System.out.println(licenses.getErrorMessage());
        else if (licenses.getValue() == null | licenses.getValue().size() == 0)
            System.out.println("You have not yet added driver's licenses to your user");
        else {
            System.out.println("Your driver's licenses:");
            Main.printOptionsList(licenses.getValue());
        }
    }

    public void setWeightForTrucking() {
        System.out.print("Enter trucking id for weight update: ");
        int id = Main.getNumber();
        System.out.print("Enter weight with products: ");
        int weight = Main.getNumber();
        Response response = service.setWeightForTrucking(id, weight);
        if (response.ErrorOccured())
            System.out.println(response.getErrorMessage());
        else
            System.out.println("The weight successfully updated");
    }

    public void printTruckings() {
        Response<String> response = service.printMyTruckings();
        if (response.ErrorOccured())
            System.out.println(response.getErrorMessage());
        else
            System.out.println(response.getValue());
    }

    public void printTruckingsHistory() {
        Response<String> response = service.printMyTruckingsHistory();
        if (response.ErrorOccured())
            System.out.println(response.getErrorMessage());
        else
            System.out.println(response.getValue());
    }

    public void printFutureTruckings() {
        Response<String> response = service.showDriverHisFutureTruckings();
        if (response.ErrorOccured())
            System.out.println(response.getErrorMessage());
        else
            System.out.println(response.getValue());
    }
}
