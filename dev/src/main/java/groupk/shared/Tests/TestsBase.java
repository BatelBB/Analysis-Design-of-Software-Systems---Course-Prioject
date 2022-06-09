package groupk.shared.Tests;

import adss_group_k.BusinessLayer.Inventory.Service.Service;
import adss_group_k.BusinessLayer.Suppliers.Service.ISupplierService;
import adss_group_k.PresentationLayer.AppContainer;
import adss_group_k.SchemaInit;
import adss_group_k.dataLayer.dao.PersistenceController;
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
