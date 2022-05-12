package groupk.logistics.DataLayer;

import groupk.logistics.business.ProductForTrucking;
import groupk.logistics.business.Products;
import groupk.logistics.business.Site;
import groupk.logistics.business.Vehicle;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Truckings_ProductsMapper extends  myDataBase {
    public Truckings_ProductsMapper() throws Exception {
        super("Truckings_Products");
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) throws SQLException {
        return null;
    }
    public void addTruckingProducts(int truckingIdCounter, List<ProductForTrucking> productForTruckings) {
        for (ProductForTrucking productForTrucking: productForTruckings) {
            addTruckingProduct(truckingIdCounter, productForTrucking);
        }
    }

    public void addTruckingProduct(int truckingIdCounter, ProductForTrucking productForTrucking) {
        int n = 0;
        String query = "INSERT INTO Truckings_Products(TID,product,quantity) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(finalCurl)){
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setInt(1, truckingIdCounter);
                prepStat.setString(2, productForTrucking.getProductString());
                prepStat.setInt(3, productForTrucking.getQuantity());
                n = prepStat.executeUpdate();
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public List<ProductForTrucking> getProducts(int truckingID) throws Exception {
        List <ProductForTrucking> productForTruckings = new LinkedList<>();
        String query = "SELECT * FROM Truckings_Products WHERE TID = '" + truckingID + "'" ;
        try (Connection conn = DriverManager.getConnection(finalCurl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                productForTruckings.add(new ProductForTrucking(product(rs.getString(2)),rs.getInt(3)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return productForTruckings;
    }

    private Products product(String pruductName) throws Exception {
        Products products =null;
        if(!(pruductName.equals("eggs") | pruductName.equals("water") | pruductName.equals("milk"))) throw new Exception("Illegal product");
        else
        {
            if((pruductName.equals("eggs"))) products = Products.Eggs_4902505139314;
            else if((pruductName.equals("milk"))) products = Products.Milk_7290111607400;
            else products = Products.Water_7290019056966;
        }
        return products;
    }

    public boolean removeProductsByTruckingId(int TruckingID,String productSKU) throws Exception {
        String Query = "DELETE FROM Truckings_Products WHERE TID = '" + TruckingID + "'" + " and product = '" + productSKU + "'" ;
        int n = 0;
        try (Connection conn = DriverManager.getConnection(finalCurl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(Query)) {
            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            throw new Exception("Deleted products successfully");
        }
        return false;

    }

    public boolean existProduct(int TruckingID,String productSKU) throws Exception {
        String Query = "SELECT * FROM Truckings_Products WHERE TID = '" + TruckingID + "'" + " and product = '" + productSKU + "'" ;
        int n = 0;
        try (Connection conn = DriverManager.getConnection(finalCurl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(Query)) {
            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return false;

    }

    public void increaseQuantity(int TruckingID,String productSKU,int quantity) throws Exception {
        int addedQuantity = Integer.parseInt(getQuantity(TruckingID,productSKU)) + quantity;
        String Query = "UPDATE Truckings_Products SET quantity = '" + addedQuantity + "'" + " WHERE TID = '" + TruckingID + "'" + " AND product = '" + productSKU + "'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(Query)) {
            n = pstmt.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public String getQuantity(int truckingID, String productSKU) throws Exception {
        String Query = "SELECT * FROM Truckings_Products WHERE TID = '" + truckingID + "'" + " AND product = '" + productSKU + "'" ;
        int n = 0;
        try (Connection conn = DriverManager.getConnection(finalCurl))
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(Query);
            while (rs.next()) {
                return rs.getString(3);
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return "no product like this";
    }


    public boolean removeTrucking(int truckingID) throws Exception {
        String Query = "DELETE FROM Truckings_Products WHERE TID = '" + truckingID+"'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(Query)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return n > 0;
    }
}