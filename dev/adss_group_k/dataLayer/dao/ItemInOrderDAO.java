package adss_group_k.dataLayer.dao;

import adss_group_k.dataLayer.records.ItemInOrderRecord;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class ItemInOrderDAO extends BaseDAO<ItemInOrderRecord.ItemInOrderKey, ItemInOrderRecord> {

    public ItemInOrderDAO(Connection conn) {
        super(conn);
    }

    @Override
    ItemInOrderRecord fetch(ItemInOrderRecord.ItemInOrderKey key) throws SQLException, NoSuchElementException {
        return null;
    }

    @Override
    Stream<ItemInOrderRecord> fetchAll() throws SQLException {
        return null;
    }

    @Override
    protected int runDeleteQuery(ItemInOrderRecord.ItemInOrderKey itemInOrderKey) {
        return 0;
    }

    public void updateAmount(int orderId, int ppn, int catalogNumber, int amount) {

    }

    public ItemInOrderRecord getOrCreate(int orderId, int ppn, int catalogNumber) {
        ItemInOrderRecord.ItemInOrderKey key = new ItemInOrderRecord.ItemInOrderKey(ppn, catalogNumber, orderId);
        if(exists(key)) {
            return get(key).data;
        }
        return create(ppn, catalogNumber, orderId);
    }

    private ItemInOrderRecord create(int ppn, int catalogNumber, int orderId) {
        return create(
                () -> new ItemInOrderRecord(ppn, catalogNumber, orderId, 0),

                 "INSERT INTO ItemInOrder(qty, itemSupplierPPN, itemCatalogNumber," +
                "orderId) VALUES(0, ?, ?, ?)",

                ps -> ps.setInt(1, ppn),
                ps -> ps.setInt(2, catalogNumber),
                ps -> ps.setInt(3, orderId)
        ).getOrThrow(RuntimeException::new);
    }

    /*
    CREATE TABLE "ItemInOrder" (
	"qty"	INTEGER NOT NULL,
	"itemSupplierPPN"	INTEGER NOT NULL,
	"itemCatalogNumber"	INTEGER NOT NULL,
	"orderId"	INTEGER NOT NULL,
	PRIMARY KEY("itemSupplierPPN","itemCatalogNumber","orderId")
     */
}
