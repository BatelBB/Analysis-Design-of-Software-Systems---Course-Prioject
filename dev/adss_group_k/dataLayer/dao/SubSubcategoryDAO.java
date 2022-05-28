package adss_group_k.dataLayer.dao;

import adss_group_k.dataLayer.records.SubSubCategoryRecord;
import adss_group_k.dataLayer.records.readonly.SubSubcategoryData;
import adss_group_k.dataLayer.records.readonly.SubcategoryData;
import adss_group_k.shared.response.ResponseT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/*
CREATE TABLE IF NOT EXISTS "SubSubCategory" (
	"name"	TEXT NOT NULL,
	"subcategory"	TEXT NOT NULL,
	"category"	TEXT NOT NULL,
	PRIMARY KEY("name","subcategory","category")
);
 */
public class SubSubcategoryDAO extends BaseDAO<SubSubCategoryRecord.SubSubcategoryKey, SubSubCategoryRecord> {

    public SubSubcategoryDAO(Connection conn) {
        super(conn);
    }

    public ResponseT<SubSubcategoryData> create(String category, String subcategory, String name) {
        return create(
                () -> new SubSubCategoryRecord(category, subcategory, name),
                "INSERT INTO SubSubcategory(category, subcategory, name) VALUES(?, ?, ?)",
                ps -> ps.setString(1, category),
                ps -> ps.setString(2, subcategory),
                ps -> ps.setString(3, name)
        ).castUnchecked();
    }

    @Override
    SubSubCategoryRecord fetch(SubSubCategoryRecord.SubSubcategoryKey key) throws SQLException, NoSuchElementException {
       PreparedStatement ps = conn.prepareStatement(
                "SELECT name, category FROM SubSubcategory WHERE name=? AND subcategory=? AND category=?"
        );
        ps.setString(1, key.name);
        ps.setString(2, key.subcategory);
        ps.setString(3, key.category);
        ResultSet resultSet = ps.executeQuery();
        if(resultSet.next()) {
            return (new SubSubCategoryRecord(key.category, key.subcategory ,key.name));
        } else {
            throw new NoSuchElementException("no such subcategory: " + key.category + " > " + key.name);
        }
    }

    @Override
    Stream<SubSubCategoryRecord> fetchAll() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT category, subcategory, name FROM Subcategory");
        ResultSet resultSet = ps.executeQuery();
        ArrayList<SubSubCategoryRecord> list = new ArrayList<>();
        while(resultSet.next()) {
            list.add(new SubSubCategoryRecord(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3))
            );
        }
        return list.stream();
    }

    @Override
    protected int runDeleteQuery(SubSubCategoryRecord.SubSubcategoryKey key) {
        return runUpdate(
                "DELETE FROM Subcategory WHERE name=? AND category=? AND subcategory=?",
                ps -> ps.setString(1, key.name),
                ps -> ps.setString(2, key.category),
                ps -> ps.setString(3, key.subcategory)
        );
    }

    public int runDeleteQuery(String cat_name, String sub_cat_name, String sub_sub_cat_name) {
        return runUpdate(
                "DELETE FROM SubSubcategory WHERE name=? AND category=? AND subcategory=?",
                ps -> ps.setString(1, cat_name),
                ps -> ps.setString(2, sub_cat_name),
                ps -> ps.setString(3, sub_sub_cat_name)
        );
    }

    /*
    CREATE TABLE "SubSubCategory" (
	"name"	TEXT NOT NULL,
	"subcategory"	TEXT NOT NULL,
	"category"	TEXT NOT NULL
     */
}
