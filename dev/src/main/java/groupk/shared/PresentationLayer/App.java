package groupk.shared.PresentationLayer;

import groupk.shared.PresentationLayer.EmployeesLogistics.MainEmployeesAndDelivery;
import groupk.shared.service.Inventory.InventoryService;
import groupk.shared.business.Suppliers.Service.ISupplierService;
import groupk.shared.PresentationLayer.Inventory.InventoryPresentationFacade;
import groupk.shared.PresentationLayer.Suppliers.SupplierPresentationFacade;
import groupk.shared.PresentationLayer.Suppliers.UserInput;
import groupk.shared.PresentationLayer.Suppliers.UserOutput;
import groupk.inventory_suppliers.SchemaInit;
import groupk.inventory_suppliers.dataLayer.dao.PersistenceController;
import groupk.shared.service.Response;
import groupk.shared.service.Service;
import groupk.shared.service.dto.Employee;

import java.sql.Connection;
import java.util.Scanner;


public class App {
    private final PersistenceController dal;
    private final ISupplierService supplierService;
    private final SupplierPresentationFacade supplierPresentation;
    private final InventoryPresentationFacade inventoryPresentationFacade;
    private final InventoryService inventoryService;
    private final Service employeesLogisticsService;
    private final Connection appConnection;
    private Employee subject;

    public App(String dbPath) {
        boolean isNew = !new java.io.File(dbPath).exists();
        boolean shouldLoadExample = false;

        AppContainer ioc = new AppContainer(dbPath);
        appConnection = ioc.get(Connection.class);
        SchemaInit.init(appConnection);
        if (isNew) {
            UserOutput.getInstance().println(
                    "You don't have a previous database file stored, so we'll create a new one for you " +
                            "from scratch.");
            shouldLoadExample = UserInput.getInstance().nextBoolean("Would you like to start with some example data?");
        }
        employeesLogisticsService = new Service(appConnection);
        employeesLogisticsService.loadEmployeeDB();

        dal = ioc.get(PersistenceController.class);
        inventoryPresentationFacade = ioc.get(InventoryPresentationFacade.class);
        supplierPresentation = ioc.get(SupplierPresentationFacade.class);
        inventoryService = ioc.get(InventoryService.class);
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
                    "4. Login or change user\n" +
                    "5. Exit\n");
            switch (in) {
                case (1): {
                    supplierPresentation.startSupplierMenu(subject);
                    break;
                }
                case (2): {
                    startInventoryMenu(subject);
                    break;
                }
                case (3) : {
                    MainEmployeesAndDelivery.mainEmployeesAndDelivery(new String[]{}, employeesLogisticsService, subject);
                    break;
                }
                case (4) : {
                    String id = UserInput.getInstance().nextString("What is your ID?\n");
                    Response<Employee> subjectResponse = employeesLogisticsService.readEmployee(id, id);
                    if (subjectResponse.isError()) {
                        System.out.println("Error: Must be valid employee ID.");
                        break;
                    }
                    this.subject = subjectResponse.getValue();
                    break;
                }
                case (5): {
                    UserOutput.getInstance().println("Goodbye!");
                    return;
                }
                default:
                    UserOutput.getInstance().println("Please try again.");
            }

        }
    }


    private void startInventoryMenu(Employee currentUser) {
        Scanner scan = new Scanner(System.in);
        String input = "";
        do {
            input = scan.nextLine();
            inventoryPresentationFacade.execute(input, currentUser);
        }
        while (!input.equals("exit"));
        System.out.println("thank you");
    }
}
