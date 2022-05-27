package adss_group_k.dataLayer.dao;

import adss_group_k.dataLayer.records.ItemInOrderRecord;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class ItemInOrderDAO extends BaseDAO<ItemInOrderRecord.ItemInOrderKey, ItemInOrderRecord> {

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

}
