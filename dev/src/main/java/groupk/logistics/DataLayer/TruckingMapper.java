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
    private TruckingsIDMap truckingsIDMap;
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private DriverLicencesMapper driverLicensesMapper;
    private VehicleMapper vehicleMapper;
    public TruckingMapper() throws Exception {
        super("Truckings");
        truckingsIDMap = TruckingsIDMap.getInstance();
        driverLicensesMapper = new DriverLicencesMapper();
        vehicleMapper = new VehicleMapper();
    }

    public boolean addTrucking(int TID, String username, String driverUserName, String RegistrationPlate, LocalDateTime date, long hours, long minutes) throws Exception {
        if (!checkDriverLicenseMatch(driverUserName, RegistrationPlate))
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license compatible with this vehicle");
        checkConflicts(driverUserName, RegistrationPlate, date, hours, minutes);
        int n = 0;
        String query = "INSERT INTO Truckings(TID,truck_manager,registration_plate,driver_username,date,hours,minutes) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(finalCurl))
        {
            if(conn != null) {
                PreparedStatement prepStat = conn.prepareStatement(query);
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                String formattedDateTime = date.format(formatter);
                prepStat.setInt(1, TID);
                prepStat.setString(2, username);
                prepStat.setString(3, RegistrationPlate);
                prepStat.setString(4, driverUserName);
                prepStat.setString(5, formattedDateTime);
                prepStat.setLong(6, hours);
                prepStat.setLong(7, minutes);


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

    private boolean checkDriverLicenseMatch(String driverUserName, String RegistrationPlate) throws Exception{
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

    private boolean checkConflicts(String driverUserName, String VehicleRegristationPlate, LocalDateTime date, long hoursOfTrucking, long minutesOfTrucking) throws Exception {
        List<TruckingDTO> conflictingEvents = getRelevantTruckings(date);
        LocalDateTime endTruck = date.plusHours(hoursOfTrucking).plusMinutes(minutesOfTrucking);
        ListIterator<TruckingDTO> truckingListIterator = conflictingEvents.listIterator();
        while (truckingListIterator.hasNext()) {
            TruckingDTO currentTrucking = truckingListIterator.next();
            LocalDateTime startCurr = currentTrucking.date;
            LocalDateTime endCurr = currentTrucking.date.plusHours(currentTrucking.hours).plusMinutes(currentTrucking.minutes);
            if (!(endTruck.isBefore(startCurr) | date.isAfter(endCurr))) {
                checkAvailibility(currentTrucking.vehicleRegistrationPlate ,VehicleRegristationPlate, currentTrucking.driverUsername,driverUserName);
            }
        }
        return true;
    }

    private List<TruckingDTO> getRelevantTruckings(LocalDateTime date) throws Exception{
        List<TruckingDTO> toReturn = new LinkedList<TruckingDTO>();
        String query = "SELECT * FROM Truckings " +
                "WHERE strftime(date) between strftime('" + date.minusHours(7).format(dateFormat) + "') and strftime('" + date.plusHours(7).format(dateFormat) + "')";
        try (Connection conn = this.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                toReturn.add(new TruckingDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
            }
        } catch (SQLException e) {
            throw new Exception("Oops something in verification process got wrong: \n" + e.getMessage());
        }
        return toReturn;
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

    public void addTrucking(Trucking trucking) {
        truckingsIDMap.truckingMap.put(trucking.getId(),trucking);
    }

    public boolean setWeightForTrucking(int truckingId, int weight) {
        if(truckingsIDMap.truckingMap.containsKey(truckingId))truckingsIDMap.truckingMap.get(truckingId).updateWeight(weight);
        String sql = "UPDATE Trucking SET weight="+weight+" WHERE TID='"+truckingId+"'";
        int n=0;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            n = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return n>0;
    }
}
