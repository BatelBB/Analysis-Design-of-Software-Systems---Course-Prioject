package groupk.shared.PresentationLayer;

import groupk.shared.PresentationLayer.EmployeesLogistics.Application;
import groupk.shared.business.CategoryController;
import groupk.shared.business.ProductController;
import groupk.shared.business.Inventory.Service.CategoryService;
import groupk.shared.business.Inventory.Service.ProductService;
import groupk.shared.business.Inventory.Service.ReportService;
import groupk.shared.business.Inventory.Service.Service;
import groupk.shared.business.Suppliers.Service.ISupplierService;
import groupk.shared.business.Suppliers.Service.SupplierService;
import groupk.shared.PresentationLayer.Inventory.InventoryPresentationFacade;
import groupk.shared.PresentationLayer.Suppliers.SupplierPresentationFacade;
import groupk.shared.PresentationLayer.Suppliers.UserInput;
import groupk.shared.PresentationLayer.Suppliers.UserOutput;
import groupk.inventory_suppliers.SchemaInit;
import groupk.inventory_suppliers.dataLayer.dao.PersistenceController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


public class App {
    private final PersistenceController dal;
    private final ISupplierService supplierService;
    private final SupplierPresentationFacade supplierPresentation;
    private final InventoryPresentationFacade inventoryPresentationFacade;
    private final Service inventoryService;

    public App(String dbPath) {
        boolean isNew = !new java.io.File(dbPath).exists();
        boolean shouldLoadExample = false;

        AppContainer ioc = new AppContainer(dbPath);
        Connection appConnection = ioc.get(Connection.class);
        SchemaInit.init(appConnection);
        if (isNew) {
            UserOutput.getInstance().println(
                    "You don't have a previous database file stored, so we'll create a new one for you " +
                            "from scratch.");
            shouldLoadExample = UserInput.getInstance().nextBoolean("Would you like to start with some example data?");
        }

        dal = ioc.get(PersistenceController.class);
        inventoryPresentationFacade = ioc.get(InventoryPresentationFacade.class);
        supplierPresentation = ioc.get(SupplierPresentationFacade.class);
        inventoryService = ioc.get(Service.class);
        supplierService = ioc.get(ISupplierService.class);
        if (shouldLoadExample) {
            ExampleData.loadExampleData(inventoryService, supplierService);
        }
    }

    public void main() {
        int in = 0;
        while (true) {
            in = UserInput.getInstance().nextInt("Which module do you need?\n" +
                    "1. Supplier module\n" +
                    "2. Inventory module\n" +
                    "3. Employees and Logistics modules\n" +
                    "4. Exit\n");
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
                    Application.main(new String[]{});
                    break;
                }
                case (4): {
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
