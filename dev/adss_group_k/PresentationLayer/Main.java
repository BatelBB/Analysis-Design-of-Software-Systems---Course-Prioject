package adss_group_k.PresentationLayer;

import adss_group_k.BusinessLayer.Inventory.Service.Service;
import adss_group_k.BusinessLayer.Suppliers.Service.SupplierService;
import adss_group_k.PresentationLayer.Inventory.InventoryPresentationFacade;
import adss_group_k.dataLayer.dao.PersistenceController;

public class Main {
    public static void main(String[] args) {
        System.out.println("hi");
        new App("database.db").main();
    }
}
