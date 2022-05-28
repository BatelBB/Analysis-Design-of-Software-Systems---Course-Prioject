package adss_group_k.dataLayer.dao;

import adss_group_k.dataLayer.records.ContactRecord;
import adss_group_k.dataLayer.records.SupplierRecord;
import adss_group_k.dataLayer.records.PaymentCondition;
import adss_group_k.dataLayer.records.readonly.SupplierData;
import adss_group_k.shared.dto.CreateSupplierDTO;

import java.sql.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/*
CREATE TABLE "Supplier" (
	"ppn"	INTEGER NOT NULL,
	"bankNumber"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"isDelivering"	BOOLEAN NOT NULL,
	"paymentCondition"	INTEGER NOT NULL,
	"regularSupplyingDay"	INTEGER,
	"contactEmail"	TEXT NOT NULL,
	"contactName"	TEXT NOT NULL,
	"contactPhone"	TEXT NOT NULL,
	PRIMARY KEY("ppn" AUTOINCREMENT)
)
 */
public class SupplierDAO extends BaseDAO<Integer, SupplierRecord> {
    public SupplierDAO(Connection conn) {
        super(conn);
    }

    public SupplierData createSupplier(CreateSupplierDTO createSupplierDTO)
            {
        return create(
                () -> new SupplierRecord(createSupplierDTO),

                "INSERT INTO " +
                "Supplier(ppn, bankNumber, name, isDelivering, paymentCondition, regularSupplyingDay," +
                "contactName, contactPhone, contactEmail)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",

                ps -> ps.setInt(1, createSupplierDTO.ppn),
                ps -> ps.setInt(2, createSupplierDTO.bankNumber),
                ps -> ps.setString(3, createSupplierDTO.name),
                ps -> ps.setBoolean(4, createSupplierDTO.isDelivering),
                ps -> ps.setInt(5, createSupplierDTO.paymentCondition.value),
                ps -> ps.setInt(6, valueOf(createSupplierDTO.regularSupplyDays)),
                ps -> ps.setString(7, createSupplierDTO.contactName),
                ps -> ps.setString(8, createSupplierDTO.contactPhone),
                ps -> ps.setString(9, createSupplierDTO.contactEmail)
        ).getOrThrow(RuntimeException::new);
    }
                       
    public void updateIsDelivering(int ppn, boolean isDelivering) {
        runUpdate(ppn, "isDelivering", isDelivering, Types.BOOLEAN);
        get(ppn).data.setDelivering(isDelivering);
    }

    public void updatePaymentCondition(int ppn,PaymentCondition paymentCondition) {
        runUpdate(ppn, "paymentCondition", paymentCondition.value, Types.INTEGER);
        get(ppn).data.setPaymentCondition(paymentCondition);
    }

    public void updateSupplyingDay(int ppn, DayOfWeek day) {
        runUpdate(ppn, "regularSupplyingDay", valueOf(day), Types.INTEGER);
        get(ppn).data.setRegularSupplyingDays(day);
    }

    public void updateContactEmail(int ppn, String email) {
        runUpdate(ppn, "contactEmail", email, Types.VARCHAR);
        get(ppn).data.getContact().setEmail(email);
    }

    private void runUpdate(int ppn, String field, Object value, int sqlType) {
       runUpdate("UPDATE Supplier SET " + field + "=? WHERE ppn=?",
                ps -> ps.setObject(1, value, sqlType),
                ps -> ps.setInt(2, ppn));
    }

    @Override
    SupplierRecord fetch(Integer ppn) throws SQLException, NoSuchElementException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Supplier WHERE ppn = ?");
        ps.setInt(1, ppn);
        ResultSet resultSet = ps.executeQuery();
        if(resultSet.next()) {
            SupplierRecord sup = nextFromResultSet(ppn, resultSet);
            return sup;
        }
        throw new NoSuchElementException("error finding supplier " + ppn);
    }

    @Override
    Stream<SupplierRecord> fetchAll() throws SQLException {
        ArrayList<SupplierRecord> all = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Supplier");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            all.add(nextFromResultSet(rs.getInt("ppn"), rs));
        }
        return all.stream();
    }

    @Override
    protected int runDeleteQuery(Integer ppn) {
        return runUpdate("DELETE FROM Supplier WHERE ppn=?", ps -> ps.setInt(1, ppn));
    }

    private static SupplierRecord nextFromResultSet(Integer ppn, ResultSet resultSet) throws SQLException {
        int bankNo = resultSet.getInt("bankNumber");
        String name = resultSet.getString("name");
        boolean delivering = resultSet.getBoolean("isDelivering");
        PaymentCondition pm = PaymentCondition.valueOf(resultSet.getInt("paymentCondition"));
        Integer weekday = resultSet.getInt("regularSupplyingDay");
        if(resultSet.wasNull()) { weekday = -1; }
        DayOfWeek rsd = weekday == -1 ? null : DayOfWeek.of(weekday);
        ContactRecord contact = new ContactRecord(
                resultSet.getString("contactName"),
                resultSet.getString("contactEmail"),
                resultSet.getString("contactPhone")
        );
        SupplierRecord sup = new SupplierRecord(ppn, bankNo, name, delivering, pm, rsd, contact);
        return sup;
    }

    private static int valueOf(DayOfWeek weekday) {
        return weekday == null ? -1 : weekday.getValue();
    }

    private static DayOfWeek valueOf(int asInt) {
        return asInt < 0 ? null : DayOfWeek.of(asInt);
    }

    public void updateName(int ppn, String newName) {
        runUpdate(ppn, "name", newName, Types.VARCHAR);
    }
}
