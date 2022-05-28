package adss_group_k.PresentationLayer;

import adss_group_k.BusinessLayer.Inventory.Service.Service;
import adss_group_k.BusinessLayer.Suppliers.Service.SupplierService;
import adss_group_k.PresentationLayer.Inventory.InventoryPresentationFacade;
import adss_group_k.PresentationLayer.Suppliers.SupplierPresentationFacade;
import adss_group_k.PresentationLayer.Suppliers.UserInput;
import adss_group_k.PresentationLayer.Suppliers.UserOutput;
import adss_group_k.SchemaInit;
import adss_group_k.dataLayer.dao.PersistenceController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


public class App {
    private final PersistenceController dal;
    private final SupplierService supplierService;
    private final Service inventoryService;
    private final SupplierPresentationFacade supplierPresentation;
    private final InventoryPresentationFacade inventoryPresentationFacade;

    public App(String dbPath) {
        Connection conn = null;
        boolean shouldLoadExample = false;
        boolean isNew = !new java.io.File(dbPath).exists();
        try {
            if(isNew) {
                UserOutput.getInstance().println(
                        "You don't have a previous database file stored, so we'll create a new one for you" +
                                "from scratch.");
                shouldLoadExample = UserInput.getInstance().nextBoolean("Would you like to start with some example data?");
            }

            conn = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        if(isNew) {
            SchemaInit.init(conn);
        }
        dal = new PersistenceController(conn);
        supplierService = new SupplierService(dal);
        inventoryService = new Service(supplierService, dal);

        if(shouldLoadExample) {
            ExampleData.loadExampleData(inventoryService, supplierService);
        }
        supplierPresentation = new SupplierPresentationFacade(supplierService);
        inventoryPresentationFacade = new InventoryPresentationFacade(inventoryService);

    }

    public void main() {
        int in = 0;
        while (true) {
            in = UserInput.getInstance().nextInt("Which module do you need?\n" +
                    "1. Supplier module\n" +
                    "2. Inventory module\n" +
                    "3. Exit\n");
            switch (in) {
                case (1): {
                    supplierPresentation.startSupplierMenu();
                    break;
                }
                case (2): {
                    startInventoryMenu();
                    break;
                }
                case (3) : {
                    UserOutput.getInstance().println("Goodbye!");
                    return;
                }
                default:
                    UserOutput.getInstance().println("Please try again.");
            }

        }
    }


    private void startInventoryMenu() {
        Scanner scan = new Scanner(System.in);
        String input = "";
        do {
            input = scan.nextLine();
            inventoryPresentationFacade.execute(input);
        }
        while (!input.equals("exit"));
        System.out.println("thank you");
    }
}
