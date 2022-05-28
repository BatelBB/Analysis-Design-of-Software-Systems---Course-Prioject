package adss_group_k.dataLayer.dao;

import adss_group_k.dataLayer.records.ItemRecord;
import adss_group_k.dataLayer.records.QuantityDiscountRecord;
import adss_group_k.dataLayer.records.readonly.ItemData;
import adss_group_k.shared.response.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class QuantityDiscountDAO extends BaseDAO<Integer, QuantityDiscountRecord> {

    private int maxId;
    public QuantityDiscountDAO(Connection conn) {
        super(conn);
        maxId = 1 + super.oneResultQuery("SELECT MAX(id) FROM QuantityDiscount", rs -> rs.getInt(1));
    }

    public ResponseT<QuantityDiscountRecord> createQuantityDiscount(
            int quantity, float discount, ItemRecord.ItemKey item) {
        maxId++;
        int id = maxId;
        return create(
                () -> new QuantityDiscountRecord(id, item, quantity, discount),
                "INSERT INTO QuantityDiscount(id, quantity, discount, itemSupplierPPN, itemCatalogNumber)" +
                        " VALUES(?, ?, ?, ?, ?)",
                ps -> ps.setInt(1, id),
                ps -> ps.setInt(2, quantity),
                ps -> ps.setFloat(3, discount),
                ps -> ps.setInt(4, item.ppn),
                ps -> ps.setInt(5, item.catalogNumber)
        );
    }
    @Override
    QuantityDiscountRecord fetch(Integer id) throws SQLException, NoSuchElementException {
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * " +
                "FROM QuantityDiscount WHERE id=?");
        stmt.setInt(1, id);
        ResultSet query = stmt.executeQuery();
        if(!query.next()) {
            throw new NoSuchElementException("no QuantityDiscount id " + id);
        }
        return readOneFromResultSet(id, query);
    }


    @Override
    Stream<QuantityDiscountRecord> fetchAll() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM QuantityDiscount");
        ResultSet query = stmt.executeQuery();
        ArrayList<QuantityDiscountRecord> entities = new ArrayList<>();
        while (query.next()) {
            entities.add(readOneFromResultSet(query.getInt("id"), query));
        }
        return entities.stream();
    }

    @Override
    protected int runDeleteQuery(Integer id) {
        return runUpdate(
                "DELETE From QuantityDiscount WHERE id=?",
                ps -> ps.setInt(1, id)
        );
    }

    private QuantityDiscountRecord readOneFromResultSet(Integer id, ResultSet query) throws SQLException {
        return new QuantityDiscountRecord(
                id,
                new ItemRecord.ItemKey(
                        query.getInt("itemSupplierPPN"),
                        query.getInt("itemCatalogNumber")
                ),
                query.getInt("quantity"),
                query.getFloat("discount")
        );
    }

    public void deleteAllForItem(ItemRecord.ItemKey itemKey) {
        all()
                .filter(x -> x.itemKey.equals(itemKey))
                .map(x -> x.id)
                .forEach(this::delete);
    }
}
