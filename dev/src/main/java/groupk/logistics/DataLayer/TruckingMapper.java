package groupk.logistics.DataLayer;

import groupk.logistics.business.DLicense;
import groupk.logistics.business.Trucking;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TruckingMapper extends myDataBase {
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private DriverLicencesMapper driverLicensesMapper;
    private VehicleMapper vehicleMapper;
    public TruckingMapper() throws Exception {
        super("Truckings");
        driverLicensesMapper = new DriverLicencesMapper();
        vehicleMapper = new VehicleMapper();
    }

    public boolean addTrucking(TruckingDTO trucking) throws Exception {
        int n = 0;
        String query = "INSERT INTO Truckings(TID,truck_manager,registration_plate,driver_username,date,hours,minutes,weight) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(finalCurl))
        {
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                String formattedDateTime = trucking.getDate().format(formatter);
                prepStat.setInt(1, trucking.getId());
                prepStat.setString(2, trucking.getTruckManager());
                prepStat.setString(3, trucking.getVehicleRegistrationPlate());
                prepStat.setString(4, trucking.getDriverUsername());
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

        return n == 1;
    }

    public boolean removeTrucking(int truckingID) throws Exception {
        String Query = "DELETE FROM Truckings WHERE TID = '" + truckingID + "'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(Query)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return n > 0;
    }

    public boolean checkAvailibility(String registrationPlate1, String registrationPlate2,String driverUserName1,String driverUserName2) {
        if (registrationPlate1 == registrationPlate2)
            throw new IllegalArgumentException("Oops, there is another trucking at the same date and with the same vehicle");
        if (driverUserName1 == driverUserName2)
            throw new IllegalArgumentException("Oops, there is another trucking at the same date and with the same driver");
        return true;
    }

    @Override
    Object ConvertResultSetToDTO(ResultSet rs) {
        return null;
    }

    public boolean setWeightForTrucking(int truckingId, int weight) throws Exception {
        String sql = "UPDATE Truckings SET weight="+weight+" WHERE TID='"+truckingId+"'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return n > 0;
    }

    public List<TruckingDTO> getTruckManagerBoard(String truckManagerUsername) throws Exception {
        return getBoardOfUserOrVehicle("truck_manager", truckManagerUsername);
    }

    public List<TruckingDTO> getTruckManagerFutureTruckings(String truckManagerUsername) throws Exception {
        return getFutureTruckingsOfUserOrVehicle("truck_manager", truckManagerUsername);
    }

    public List<TruckingDTO> getTruckManagerHistoryTruckings(String truckManagerUsername) throws Exception {
        return getHistoryTruckingsOfUserOrVehicle("truck_manager", truckManagerUsername);
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

    public List<TruckingDTO> getDriverBoard(String driverUsername) throws Exception {
        return getBoardOfUserOrVehicle("driver_username", driverUsername);
    }

    public List<TruckingDTO> getDriverFutureTruckings(String driverUsername) throws Exception {
        return getFutureTruckingsOfUserOrVehicle("driver_username", driverUsername);
    }

    public List<TruckingDTO> getDriverHistoryTruckings(String driverUsername) throws Exception {
        return getHistoryTruckingsOfUserOrVehicle("driver_username", driverUsername);
    }

    public boolean checkDriverLicenseMatch(String driverUserName, String RegistrationPlate) throws Exception{
        List<DLicense> driverLicenses = driverLicensesMapper.getMyLicenses(driverUserName);
        DLicense license = vehicleMapper.getLicense(RegistrationPlate);
        if (driverLicenses == null)
            throw new IllegalArgumentException("There is not licenses we can see of that driver");
        if (license == null)
            throw new IllegalArgumentException("We cannot found the vehicle's license");
        if (driverLicenses.contains(license))
            return true;
        return false;
    }

    public boolean checkConflicts(String driverUserName, String VehicleRegristationPlate, LocalDateTime date, long hoursOfTrucking, long minutesOfTrucking) throws Exception {
        List<TruckingDTO> conflictingEvents = getRelevantTruckings(date);
        LocalDateTime endTruck = date.plusHours(hoursOfTrucking).plusMinutes(minutesOfTrucking);
        ListIterator<TruckingDTO> truckingListIterator = conflictingEvents.listIterator();
        while (truckingListIterator.hasNext()) {
            TruckingDTO currentTrucking = truckingListIterator.next();
            LocalDateTime startCurr = currentTrucking.getDate();
            LocalDateTime endCurr = currentTrucking.getDate().plusHours(currentTrucking.getHours()).plusMinutes(currentTrucking.getMinutes());
            if (!(endTruck.isBefore(startCurr) | date.isAfter(endCurr))) {
                checkAvailibility(currentTrucking.getVehicleRegistrationPlate() ,VehicleRegristationPlate, currentTrucking.getDriverUsername(),driverUserName);
            }
        }
        return true;
    }

    private List<TruckingDTO> getRelevantTruckings(LocalDateTime date) throws Exception{
        List<TruckingDTO> toReturn = new LinkedList<TruckingDTO>();
        String query = "SELECT * FROM Truckings " +
                "WHERE strftime(date) between strftime('" + date.minusHours(7).format(dateFormat) + "') and strftime('" + date.plusHours(7).format(dateFormat) + "') ORDER BY date";
        try (Connection conn = DriverManager.getConnection(finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                toReturn.add(new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getString(2), rs.getString(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
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
        try (Connection conn = DriverManager.getConnection(finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                toReturn.add(new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getString(2), rs.getString(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
            }
        } catch (SQLException e) {
            throw new Exception("Oops something got wrong: \n" + e.getMessage());
        }
        return toReturn;
    }

    private List<TruckingDTO> getFutureTruckingsOfUserOrVehicle(String fieldName, String usernameOrRegistration) throws Exception {
        List<TruckingDTO> toReturn = new LinkedList<TruckingDTO>();
        String query = "SELECT * FROM Truckings " +
                "WHERE strftime(date) > DATE('now') and " + fieldName + " = '" + usernameOrRegistration + "'"+
                " ORDER BY date";
        try (Connection conn = DriverManager.getConnection(finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                toReturn.add(new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getString(2), rs.getString(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
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
        try (Connection conn = DriverManager.getConnection(finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                toReturn.add(new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getString(2), rs.getString(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
            }
        } catch (SQLException e) {
            throw new Exception("Oops something got wrong: \n" + e.getMessage());
        }
        return toReturn;
    }

}
