package groupk.shared.PresentationLayer;

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
import groupk.inventory_suppliers.dataLayer.dao.PersistenceController;
import groupk.inventory_suppliers.shared.ioc.ClassContainer;

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
