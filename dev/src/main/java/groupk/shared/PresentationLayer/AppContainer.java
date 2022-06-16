package groupk.shared.PresentationLayer;

import groupk.inventory_suppliers.shared.ioc.ServiceProvider;
import groupk.shared.business.CategoryController;

import groupk.shared.business.ProductController;

import groupk.shared.PresentationLayer.Inventory.InventoryPresentationFacade;
import groupk.shared.PresentationLayer.Suppliers.SupplierPresentationFacade;
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
        ioc.singleton(ServiceProvider.class, () -> ioc);
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
