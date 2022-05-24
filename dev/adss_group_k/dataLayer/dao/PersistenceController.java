package adss_group_k.dataLayer.dao;

import java.sql.Connection;

public class PersistenceController {
    private final Connection conn;

    private final CategoryDAO categories;
    private final SubcategoryDAO subcategories;
    private final SupplierDAO suppliers;
    private final ItemDAO items;

    private final ProductDAO products;
    private final SubSubcategoryDAO subSubCategorys;

    public Connection getConn() {
        return conn;
    }

    public CategoryDAO getCategories() {
        return categories;
    }

    public SubcategoryDAO getSubcategories() {
        return subcategories;
    }

    public SupplierDAO getSuppliers() {
        return suppliers;
    }

    public ItemDAO getItems() {
        return items;
    }

    public ProductDAO getProducts() {
        return products;
    }

    public PersistenceController(Connection conn) {
        this.conn = conn;
        categories = new CategoryDAO(conn);
        subcategories = new SubcategoryDAO(conn);
        suppliers = new SupplierDAO(conn);
        products = new ProductDAO(conn);
        items = new ItemDAO(conn);
        subSubCategorys = new SubSubcategoryDAO(conn);
    }
}
