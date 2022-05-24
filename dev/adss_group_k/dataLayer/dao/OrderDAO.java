package adss_group_k.dataLayer.dao;

import adss_group_k.dataLayer.records.OrderRecord;
import adss_group_k.dataLayer.records.readonly.OrderData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

class OrderDAO extends BaseDAO<Integer, OrderRecord> {

    public OrderDAO(Connection conn) {
        super(conn);
    }

    @Override
    OrderRecord fetch(Integer id) throws SQLException, NoSuchElementException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Order WHERE id=?");
        ps.setInt(1, id);
        ResultSet qu = ps.executeQuery();
        if (!qu.next()) {
            throw new NoSuchElementException("No order " + id);
        }
        return readOneFromQuery(id, qu);
    }
    @Override
    Stream<OrderRecord> fetchAll() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Order");
        ResultSet qu = ps.executeQuery();
        ArrayList<OrderRecord> list = new ArrayList<>();
        while (qu.next()) {
            list.add(readOneFromQuery(qu.getInt("id"), qu));
        }
        return list.stream();
    }

    @Override
    protected int runDeleteQuery(Integer integer) {
        return 0;
    }


    private OrderRecord readOneFromQuery(Integer id, ResultSet qu) throws SQLException {
        return new OrderRecord(
                id,
                qu.getInt("ppn"),
                qu.getFloat("price"),
                qu.getDate("ordered").toLocalDate(),
                qu.getDate("delivered").toLocalDate()
        );
    }
}