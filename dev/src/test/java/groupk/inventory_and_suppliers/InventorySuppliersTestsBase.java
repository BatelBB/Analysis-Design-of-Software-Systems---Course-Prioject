package groupk.inventory_and_suppliers;

import groupk.inventory_suppliers.SchemaInit;
import groupk.inventory_suppliers.dataLayer.dao.PersistenceController;
import groupk.shared.PresentationLayer.AppContainer;
import groupk.shared.business.Facade;
import groupk.shared.service.Service;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import static groupk.CustomAssertions.*;
import java.sql.Connection;

public class InventorySuppliersTestsBase {
    protected PersistenceController pc;
    protected Connection conn;
    protected Service facade;

    @BeforeEach
    public void setService() {
        AppContainer ioc = new AppContainer(":memory:");
        this.conn = ioc.get(Connection.class);
        SchemaInit.init(this.conn);
        this.pc = ioc.get(PersistenceController.class);
        this.facade = ioc.get(Service.class);
    }
}
