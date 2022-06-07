package adss_group_k.PresentationLayer;

import adss_group_k.BusinessLayer.Inventory.Controllers.CategoryController;
import adss_group_k.BusinessLayer.Inventory.Controllers.ProductController;
import adss_group_k.BusinessLayer.Inventory.Service.CategoryService;
import adss_group_k.BusinessLayer.Inventory.Service.ProductService;
import adss_group_k.BusinessLayer.Inventory.Service.ReportService;
import adss_group_k.BusinessLayer.Inventory.Service.Service;
import adss_group_k.BusinessLayer.Suppliers.Service.ISupplierService;
import adss_group_k.BusinessLayer.Suppliers.Service.SupplierService;
import adss_group_k.PresentationLayer.Inventory.InventoryPresentationFacade;
import adss_group_k.PresentationLayer.Suppliers.SupplierPresentationFacade;
import adss_group_k.PresentationLayer.Suppliers.UserInput;
import adss_group_k.PresentationLayer.Suppliers.UserOutput;
import adss_group_k.dataLayer.dao.PersistenceController;
import adss_group_k.shared.ioc.ClassContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppContainer {
    private final ClassContainer ioc;
    private final String dbPath;
    
    public AppContainer(String dbPath) {
        this.dbPath = dbPath;
        ioc = new ClassContainer();
        ioc.singleton(java.sql.Connection.class, this::connect);
        ioc.singleton(PersistenceController.class);
        ioc.singleton(ISupplierService.class, SupplierService.class);
        ioc.singleton(CategoryController.class);
        ioc.singleton(ProductService.class);
        ioc.singleton(ProductController.class);
        ioc.singleton(ReportService.class);
        ioc.singleton(CategoryService.class);
        ioc.singleton(Service.class);
        ioc.singleton(SupplierPresentationFacade.class);
        ioc.singleton(InventoryPresentationFacade.class);
    }

    private Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:"+dbPath);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public <T> T get(Class<T> service) {
        return ioc.get(service);
    }
}
