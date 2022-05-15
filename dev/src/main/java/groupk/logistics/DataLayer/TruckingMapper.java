package groupk.logistics.DataLayer;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TruckingMapper {
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private DriverLicencesMapper driverLicensesMapper;
    private VehicleMapper vehicleMapper;
    private TruckingIDMap truckingIDMap;
    public TruckingMapper() throws Exception {
        driverLicensesMapper = new DriverLicencesMapper();
        vehicleMapper = new VehicleMapper();
        truckingIDMap = TruckingIDMap.getInstance();
    }

    public boolean addTrucking(TruckingDTO trucking) throws Exception {
        int n = 0;
        String query = "INSERT INTO Truckings(TID,truck_manager,registration_plate,driver_username,date,hours,minutes,weight) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl))
        {
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                String formattedDateTime = trucking.getDate().format(formatter);
                formattedDateTime = formattedDateTime.replace("T"," ");
                prepStat.setInt(1, trucking.getId());
                prepStat.setInt(2, trucking.getTruckManager());
                prepStat.setString(3, trucking.getVehicleRegistrationPlate());
                prepStat.setInt(4, trucking.getDriverUsername());
                prepStat.setString(5, formattedDateTime);
                prepStat.setLong(6, trucking.getHours());
                prepStat.setLong(7, trucking.getMinutes());
                prepStat.setInt(8, trucking.getWeight());

                n = prepStat.executeUpdate();
            }
            else
                return false;
        }
        catch (SQLException e){
            throw new Exception("Oops, something got wrong and we couldn't add your trucking :( \nbecause: " + e.getMessage());
        }
        if (n == 1)
            truckingIDMap.truckingsMap.put(trucking.getId(), trucking);
        return n == 1;
    }

    public boolean removeTrucking(int truckingID) throws Exception {
        String Query = "DELETE FROM Truckings WHERE TID = '" + truckingID + "'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(Query)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        truckingIDMap.truckingsMap.remove(truckingID);
        return n > 0;
    }

    public boolean setWeightForTrucking(int truckingId, int weight) throws Exception {
        String sql = "UPDATE Truckings SET weight="+weight+" WHERE TID='"+truckingId+"'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        if (n > 0)
            truckingIDMap.truckingsMap.get(truckingId).setWeight(weight);
        return n > 0;
    }

    public boolean updateVehicle(int truckingId, String vehicleRegistrationPlate) throws Exception {
        String sql = "UPDATE Truckings SET registration_plate = '" + vehicleRegistrationPlate + "' WHERE TID = '" + truckingId + "'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        if (n > 0)
            truckingIDMap.truckingsMap.get(truckingId).updateVehicle(vehicleRegistrationPlate);
        return n > 0;
    }

    public boolean updateDriver(int truckingId, int driverUsername) throws Exception {
        String sql = "UPDATE Truckings SET driver_username = '" + driverUsername + "' WHERE TID = '" + truckingId + "'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        if (n > 0)
            truckingIDMap.truckingsMap.get(truckingId).updateDriverUsername(driverUsername);
        return n > 0;
    }

    public boolean updateDate(int truckingID, LocalDateTime date) throws Exception {
        String sql = "UPDATE Truckings SET date = '" + date.format(dateFormat).replace('T',' ') + "' WHERE TID = '" + truckingID + "'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        if (n > 0)
            truckingIDMap.truckingsMap.get(truckingID).updateDate(date.plusHours(0));
        return n > 0;
    }

    public String getRegistrationPlateOfTrucking(int TruckingId) throws Exception {
        if (truckingIDMap.truckingsMap.containsKey(truckingIDMap))
            return truckingIDMap.truckingsMap.get(truckingIDMap).getVehicleRegistrationPlate();
        String toReturn = "";
        String query = "SELECT registration_plate FROM Truckings " +
                "WHERE TID = '" + TruckingId + "'";
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            if (rs.next()) {
                toReturn += rs.getString(1);
            }
            else
                throw new Exception("There is no trucking with id: " + TruckingId);
        } catch (SQLException e) {
            throw new Exception("Oops something got wrong: \n" + e.getMessage());
        }
        return toReturn;
    }

    public int getDriverUsernameOfTrucking(int TruckingId) throws Exception {
        if (truckingIDMap.truckingsMap.containsKey(truckingIDMap))
            return truckingIDMap.truckingsMap.get(truckingIDMap).getDriverUsername();
        int toReturn = 0;
        String query = "SELECT driver_username FROM Truckings " +
                "WHERE TID = '" + TruckingId + "'";
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            if (rs.next()) {
                toReturn += rs.getInt(1);
            }
            else
                throw new Exception("There is no trucking with id: " + TruckingId);
        } catch (SQLException e) {
            throw new Exception("Oops something got wrong: \n" + e.getMessage());
        }
        return toReturn;
    }

    public TruckingDTO getTruckingByID(int truckingID) throws Exception{
        if (truckingIDMap.truckingsMap.containsKey(truckingID))
            return truckingIDMap.truckingsMap.get(truckingID);
        TruckingDTO toReturn;
        String query = "SELECT * FROM Truckings " +
                "WHERE TID = '" + truckingID + "'";
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            if (rs.next()) {
                toReturn = new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8));
            }
            else
                throw new Exception("There is no trucking with that id");
        } catch (SQLException e) {
            throw new Exception("Oops something in verification process got wrong: \n" + e.getMessage());
        }
        return toReturn;
    }

    public List<TruckingDTO> getTruckManagerBoard(int truckManagerUsername) throws Exception {
        return getBoardOfUserOrVehicle("truck_manager", "" + truckManagerUsername);
    }

    public List<TruckingDTO> getTruckManagerFutureTruckings(int truckManagerUsername) throws Exception {
        return getFutureTruckingsOfUserOrVehicle("truck_manager", "" + truckManagerUsername);
    }

    public List<TruckingDTO> getTruckManagerHistoryTruckings(int truckManagerUsername) throws Exception {
        return getHistoryTruckingsOfUserOrVehicle("truck_manager", "" + truckManagerUsername);
    }

    public List<TruckingDTO> getVehicleBoard(String regristrationPlate) throws Exception {
        return getBoardOfUserOrVehicle("registration_plate", regristrationPlate);
    }

    public List<TruckingDTO> getVehicleFutureTruckings(String regristrationPlate) throws Exception {
        return getFutureTruckingsOfUserOrVehicle("registration_plate", regristrationPlate);
    }

    public List<TruckingDTO> getVehicleHistoryTruckings(String regristrationPlate) throws Exception {
        return getHistoryTruckingsOfUserOrVehicle("registration_plate", regristrationPlate);
    }

    public List<TruckingDTO> getDriverBoard(int driverUsername) throws Exception {
        return getBoardOfUserOrVehicle("driver_username", "" + driverUsername);
    }

    public List<TruckingDTO> getDriverFutureTruckings(int driverUsername) throws Exception {
        return getFutureTruckingsOfUserOrVehicle("driver_username", "" + driverUsername);
    }

    public List<TruckingDTO> getDriverHistoryTruckings(int driverUsername) throws Exception {
        return getHistoryTruckingsOfUserOrVehicle("driver_username", "" + driverUsername);
    }

    public List<TruckingDTO> getRelevantTruckings(LocalDateTime date) throws Exception{
        List<TruckingDTO> toReturn = new LinkedList<TruckingDTO>();
        String query = "SELECT * FROM Truckings " +
                "WHERE strftime(date) between strftime('" + date.minusHours(7).format(dateFormat) + "') and strftime('" + date.plusHours(7).format(dateFormat) + "') ORDER BY date";
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                toReturn.add(new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
            }
        } catch (SQLException e) {
            throw new Exception("Oops something in verification process got wrong: \n" + e.getMessage());
        }
        return toReturn;
    }

    private List<TruckingDTO> getBoardOfUserOrVehicle(String fieldName, String usernameOrRegistration) throws Exception {
        List<TruckingDTO> toReturn = new LinkedList<TruckingDTO>();
        String query = "SELECT * FROM Truckings " +
                "WHERE " + fieldName + " = '" + usernameOrRegistration + "'"+
                " ORDER BY date";
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                TruckingDTO truckingDTO = new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8));
                toReturn.add(truckingDTO);
            }
        } catch (SQLException e) {
            throw new Exception("Oops, something got wrong: \n" + e.getMessage());
        }
        return toReturn;
    }

    private List<TruckingDTO> getFutureTruckingsOfUserOrVehicle(String fieldName, String usernameOrRegistration) throws Exception {
        List<TruckingDTO> toReturn = new LinkedList<TruckingDTO>();
        String query = "SELECT * FROM Truckings " +
                "WHERE strftime(date) > DATE('now') and " + fieldName + " = '" + usernameOrRegistration + "'"+
                " ORDER BY date";
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                toReturn.add(new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
            }
        } catch (SQLException e) {
            throw new Exception("Oops something got wrong: \n" + e.getMessage());
        }
        return toReturn;
    }

    private List<TruckingDTO> getHistoryTruckingsOfUserOrVehicle(String fieldName, String usernameOrRegistration) throws Exception {
        List<TruckingDTO> toReturn = new LinkedList<TruckingDTO>();
        String query = "SELECT * FROM Truckings " +
                "WHERE strftime(date) <= DATE('now') and "+ fieldName + " = '" + usernameOrRegistration + "'"+
                " ORDER BY date";
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                toReturn.add(new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
            }
        } catch (SQLException e) {
            throw new Exception("Oops something got wrong: \n" + e.getMessage());
        }
        return toReturn;
    }

    public int getNextIdForTrucking() throws Exception {
        int toReturn = 1;
        String query = "SELECT TID FROM Truckings ORDER BY TID DESC";
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            if (rs.next()) {
                toReturn += rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new Exception("Oops something got wrong: \n" + e.getMessage());
        }
        return toReturn;
    }

    public String getLicencePlate(int truckingId) throws Exception {
        String regisrationPlate = "";
        String query = "SELECT * FROM Truckings WHERE TID='"+truckingId+"'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            if (rs.next()) {
                regisrationPlate = rs.getString(3);
            }
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return regisrationPlate;
    }



}
