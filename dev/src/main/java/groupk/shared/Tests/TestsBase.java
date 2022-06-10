package groupk.shared.Tests;

import groupk.inventory_suppliers.SchemaInit;
import groupk.inventory_suppliers.dataLayer.dao.PersistenceController;
import groupk.shared.PresentationLayer.AppContainer;
import groupk.shared.business.Inventory.Service.Service;
import groupk.shared.business.Suppliers.Service.ISupplierService;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

public class TestsBase {
    protected Service inventory;
    protected ISupplierService suppliers;
    protected PersistenceController pc;
    protected Connection conn;

    @BeforeEach
    public void setService() {
        AppContainer ioc = new AppContainer(":memory:");
        this.conn = ioc.get(Connection.class);
        SchemaInit.init(this.conn);
        this.inventory = ioc.get(Service.class);
        this.pc = ioc.get(PersistenceController.class);
        this.suppliers = ioc.get(ISupplierService.class);
    }
}
