package groupk.inventory_and_suppliers;

import groupk.inventory_suppliers.SchemaInit;
import groupk.inventory_suppliers.dataLayer.dao.PersistenceController;
import groupk.shared.PresentationLayer.AppContainer;
import groupk.shared.business.Facade;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

public class InventorySuppliersTestsBase {
    protected PersistenceController pc;
    protected Connection conn;
    protected Facade facade;

    @BeforeEach
    public void setService() {
        AppContainer ioc = new AppContainer(":memory:");
        this.conn = ioc.get(Connection.class);
        SchemaInit.init(this.conn);
        this.pc = ioc.get(PersistenceController.class);
        this.facade = ioc.get(Facade.class);
    }
}
