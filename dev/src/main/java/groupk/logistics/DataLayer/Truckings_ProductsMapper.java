package groupk.logistics.DataLayer;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Truckings_ProductsMapper {
    public Truckings_ProductsMapper() throws Exception {
    }

    public void addTruckingProducts(int truckingIdCounter, List<ProductForTruckingDTO> productForTruckings) {
        for (ProductForTruckingDTO productForTrucking: productForTruckings) {
            addTruckingProduct(truckingIdCounter, productForTrucking);
        }
    }

    public void addTruckingProduct(int truckingIdCounter, ProductForTruckingDTO productForTrucking) {
        int n = 0;
        String query = "INSERT INTO Truckings_Products(TID,product,quantity) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl)){
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setInt(1, truckingIdCounter);
                prepStat.setString(2, productForTrucking.getProduct());
                prepStat.setInt(3, productForTrucking.getQuantity());
                n = prepStat.executeUpdate();
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public List<ProductForTruckingDTO> getProducts(int truckingID) throws Exception {
        List <ProductForTruckingDTO> productForTruckings = new LinkedList<>();
        String query = "SELECT * FROM Truckings_Products WHERE TID = '" + truckingID + "'" ;
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                productForTruckings.add(new ProductForTruckingDTO(rs.getString(2),rs.getInt(3)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return productForTruckings;
    }


    public boolean removeProductsByTruckingId(int TruckingID,String productSKU) throws Exception {
        String Query = "DELETE FROM Truckings_Products WHERE TID = '" + TruckingID + "'" + " and product = '" + productSKU + "'" ;
        int n = 0;
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
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
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
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
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(Query)) {
            n = pstmt.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public String getQuantity(int truckingID, String productSKU) throws Exception {
        String Query = "SELECT * FROM Truckings_Products WHERE TID = '" + truckingID + "'" + " AND product = '" + productSKU + "'" ;
        int n = 0;
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl))
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
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(Query)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return n > 0;
    }
}