package adss_group_k.dataLayer.dao;

import adss_group_k.dataLayer.records.ProductRecord;
import adss_group_k.dataLayer.records.readonly.ProductData;
import adss_group_k.shared.response.ResponseT;

import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/*
CREATE TABLE "Product" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"customerPrice"	REAL NOT NULL,
	"minQty"	INTEGER NOT NULL,
	"storageQty"	INTEGER NOT NULL,
	"shelfQty"	INTEGER NOT NULL,
	"subSubcategory"	TEXT NOT NULL,
	"subcategory"	TEXT NOT NULL,
	"category"	TEXT NOT NULL,
)
 */
public class ProductDAO extends BaseDAO<Integer, ProductRecord> {

    public ProductDAO(Connection conn) {
        super(conn);
    }

    public ResponseT<ProductData> create(int id, String name, float customerPrice,
                                         int minQty, int storageQty, int shelfQty,
                                         String category, String subcategory, String subSubcategory) {
        return create(
            () -> new ProductRecord(id, name, customerPrice,
                minQty, storageQty, shelfQty,
                category, subcategory, subSubcategory),
            "INSERT INTO Product(" +
                    "id,name,customerPrice," +
                    "minQty,storageQty,shelfQty," +
                    "subSubcategory,subcategory,category" +
                ")",
                ps -> ps.setInt(1, id),
                ps -> ps.setString(2, name),
                ps -> ps.setFloat(3, customerPrice),
                ps -> ps.setInt(4, minQty),
                ps -> ps.setInt(5, storageQty),
                ps -> ps.setInt(6, shelfQty),
                ps -> ps.setString(7, subSubcategory),
                ps -> ps.setString(8, subcategory),
                ps -> ps.setString(9, category)
        ).castUnchecked();
    }

    @Override
    ProductRecord fetch(Integer id) throws SQLException, NoSuchElementException {
        PreparedStatement stmt = conn.prepareCall("SELECT * FROM Product WHERE id=?");
        stmt.setInt(1, id);
        ResultSet query = stmt.executeQuery();
        if(!query.next()) {
            throw new NoSuchElementException("Product: " + id);
        }
        return readOne(id, query);
    }

    @Override
    Stream<ProductRecord> fetchAll() throws SQLException {
        PreparedStatement stmt = conn.prepareCall("SELECT * FROM Product");
        ResultSet query = stmt.executeQuery();
        ArrayList<ProductRecord> res = new ArrayList<>();
        while(query.next()) {
            res.add(readOne(query.getInt("id"), query));
        }
        return res.stream();
    }

    @Override
    protected int runDeleteQuery(Integer id) {
        return runUpdate("DELETE FROM Product WHERE id=?", ps -> ps.setInt(1, id));
    }

    public void updateStorageQty(int id, int storageQty) {
        update(id, "storageQty", storageQty, Types.INTEGER);
    }

    private int update(int id, String field, Object value, int type) {
        return runUpdate(
                "UPDATE Product SET " + field + "=? WHERE id=?",
                ps -> ps.setObject(1, value, type),
                ps -> ps.setInt(2, id)
        );
    }

    private ProductRecord readOne(Integer id, ResultSet query) throws SQLException {
        return new ProductRecord(
                id,
                query.getString("name"),
                query.getFloat("customerPrice"),
                query.getInt("minQty"),
                query.getInt("storageQty"),
                query.getInt("shelfQty"),
                query.getString("category"),
                query.getString("subcategory"),
                query.getString("subSubcategory")
        );
    }

}
