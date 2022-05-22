package adss_group_k.dataLayer.dao;

import java.sql.Connection;

public class PersistenceController {
    private final Connection conn;
    public final CategoryDAO categories;
    public final SubcategoryDAO subcategories;
    public final SupplierDAO suppliers;

    public PersistenceController(Connection conn) {
        this.conn = conn;
        categories = new CategoryDAO(conn);
        subcategories = new SubcategoryDAO(conn);
        suppliers = new SupplierDAO(conn);
    }
}
