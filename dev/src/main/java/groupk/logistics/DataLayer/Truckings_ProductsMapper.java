package groupk.logistics.DataLayer;

import groupk.logistics.business.ProductForTrucking;

import java.sql.*;
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

    private void addTruckingProduct(int truckingIdCounter, ProductForTrucking productForTrucking) {
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
}
