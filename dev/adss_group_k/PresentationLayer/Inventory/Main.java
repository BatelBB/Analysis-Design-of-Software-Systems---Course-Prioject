package adss_group_k.PresentationLayer.Inventory;

import adss_group_k.BusinessLayer.Inventory.Service.Service;
import adss_group_k.BusinessLayer.Suppliers.Service.SupplierService;
import adss_group_k.dataLayer.dao.PersistenceController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static PersistenceController pc;
    public static void startInventoryMenu() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        pc = new PersistenceController(conn);
        SupplierService supplierService=new SupplierService(conn);
        Service service = new Service(supplierService, pc);
        Scanner scan = new Scanner(System.in);
        String input = "";
        PresentationModel pm = new PresentationModel(service);
        example(pm);
        do {
            input = scan.nextLine();
            pm.execute(input);
        }
        while (!input.equals("exit"));
        System.out.println("thank you");
    }

    private static void example(PresentationModel pm) {
        pm.execute("addCategory Dairy Products");
        pm.execute("addSubCategory Dairy Products,Milks");
        pm.execute("addSubSubCategory Dairy Products,Milks,3%");
        pm.execute("addProduct My Milk,Tnuva,5.0,5.94,10,1,Dairy Products,Milks,3%");
        pm.execute("addItem 0,Yavne,shelf 2,Amos,2018-02-02 13:55,true");
        pm.execute("addSubCategory Dairy Products,Cheeses");
        pm.execute("addSubSubCategory Dairy Products,Cheeses,Non-fat");
        pm.execute("addProduct Emmek,Tnuva,5.0,5.94,10,1,Dairy Products,Cheeses,Non-fat");
        pm.execute("addItem 1,Yehud,shelf 3,Amit,2019-02-03 14:01,true");
        pm.execute("addItem 1,Yehud,shelf 3,Amit,2019-02-04 08:25,true");
        pm.execute("addItem 1,Yehud,shelf 3,Amit,2019-02-03 13:11,true");
        pm.execute("addItem 1,Yehud,shelf 3,Amit,2019-02-03 14:01,true");
        pm.execute("addItem 1,Yehud,shelf 3,Amit,2019-02-03 14:01,true");
        pm.execute("addCategory Meats");
        pm.execute("addSubCategory Meats,Chicken");
        pm.execute("addSubSubCategory Meats,Chicken,Chicken Breast");
        pm.execute("addProduct Mama-Off,Tnuva,55.00,60.01,5,2,Meats,Chicken,Chicken Breast");
        pm.execute("addItem 2,Rishon,storage 3,Alil,2021-02-12 01:02,false");

    }
}
