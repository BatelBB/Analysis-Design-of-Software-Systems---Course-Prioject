package adss_group_k.dataLayer.dao;

import adss_group_k.dataLayer.records.OrderRecord;
import adss_group_k.dataLayer.records.readonly.OrderData;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class OrderDAO extends BaseDAO<Integer, OrderRecord> {

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

    public void updateProvided(int id, LocalDate provided) {
        runUpdate(
                "UPDATE Order SET provided=? WHERE id=?",
                ps -> ps.setDate(1, Date.valueOf(provided)),
                ps -> ps.setInt(2, id)
        );
        get(id).data.setProvided(provided);
    }

    public void updateOrdered(int id, LocalDate ordered) {
        runUpdate(
                "UPDATE Order SET ordered=? WHERE id=?",
                ps -> ps.setDate(1, Date.valueOf(ordered)),
                ps -> ps.setInt(2, id)
        );
        get(id).data.setOrdered(ordered);
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