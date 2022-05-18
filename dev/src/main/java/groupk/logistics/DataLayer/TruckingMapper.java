package groupk.logistics.DataLayer;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TruckingMapper {
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private TruckingIDMap truckingIDMap;
    public TruckingMapper() throws Exception {
        truckingIDMap = TruckingIDMap.getInstance();
    }

    public List<String>[] addTrucking(TruckingDTO trucking) throws Exception {
        List<String> sourcesExceptions;
        List<String> destinationsExceptions;
        if(!addTruckingToTruckingTable(trucking))
            throw new IllegalArgumentException("Oops something got wrong with adding that trucking");
        try {
            sourcesExceptions = addTruckingSources(trucking.getId(), trucking.getSources());
            if(sourcesExceptions.size() == trucking.getSources().size())
                throwExceptionOfList(sourcesExceptions, "sources");
        }
        catch (Exception e) {
            removeTruckingDetails(trucking.getId());
            throw e;
        }
        try {
            destinationsExceptions = addTruckingDestinations(trucking.getId(), trucking.getDestinations());
            if(destinationsExceptions.size() == trucking.getSources().size())
                throwExceptionOfList(destinationsExceptions, "sources");
        }
        catch (Exception e) {
            removeTruckingDetails(trucking.getId());
            removeSourcesTrucking(trucking.getId());
            throw e;
        }
        try {
            addTruckingProducts(trucking.getId(), trucking.getProducts());
        }
        catch (Exception e) {
            removeTruckingDetails(trucking.getId());
            removeSourcesTrucking(trucking.getId());
            removeDestinationsTrucking(trucking.getId());
            throw e;
        }
        List<String>[] toReturn = new List[2];
        toReturn[0] = sourcesExceptions;
        toReturn[1] = destinationsExceptions;
        return toReturn;
    }

    private void throwExceptionOfList(List<String> exceptions, String nameOfList) throws Exception {
        String exceptionError = "Oops, we couldnt add any site from your " + nameOfList + ", because that reasons:\n";
        for (String exception : exceptions) {
            exceptionError += exception + "\n";
        }
        throw new IllegalArgumentException(exceptionError);
    }

    public boolean addTruckingToTruckingTable(TruckingDTO trucking) throws Exception {
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
            truckingIDMap.insertTrucking(trucking);
        return n == 1;
    }

    public boolean removeTrucking(int truckingID) throws Exception {
        removeSourcesTrucking(truckingID);
        removeDestinationsTrucking(truckingID);
        removeProductsTrucking(truckingID);
        return removeTruckingDetails(truckingID);
    }

    public boolean removeTruckingDetails(int truckingID) throws Exception {
        String Query = "DELETE FROM Truckings WHERE TID = '" + truckingID + "'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(Query)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        truckingIDMap.removeTrucking(truckingID);
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
            truckingIDMap.getTruckingById(truckingId).setWeight(weight);
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
            truckingIDMap.getTruckingById(truckingId).updateVehicle(vehicleRegistrationPlate);
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
            truckingIDMap.getTruckingById(truckingId).updateDriverUsername(driverUsername);
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
            truckingIDMap.getTruckingById(truckingID).updateDate(date.plusHours(0));
        return n > 0;
    }

    public String getRegistrationPlateOfTrucking(int TruckingId) throws Exception {
        if (truckingIDMap.contains(TruckingId))
            return truckingIDMap.getTruckingById(TruckingId).getVehicleRegistrationPlate();
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
        if (truckingIDMap.contains(TruckingId))
            return truckingIDMap.getTruckingById(TruckingId).getDriverUsername();
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
        if (truckingIDMap.contains(truckingID))
            return truckingIDMap.getTruckingById(truckingID);
        TruckingDTO toReturn;
        List<SiteDTO> sources = getSourcesByTruckingId(truckingID);
        List<SiteDTO> destinations = getDestinationsByTruckingId(truckingID);
        List<ProductForTruckingDTO> products = getProducts(truckingID);
        String query = "SELECT * FROM Truckings " +
                "WHERE TID = '" + truckingID + "'";
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            if (rs.next()) {
                toReturn = new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8), sources, destinations, products);
                truckingIDMap.insertTrucking(toReturn);
            }
            else
                throw new Exception("There is no trucking with that id");
        } catch (SQLException e) {
            throw new Exception("Oops something in verification process got wrong: \n" + e.getMessage());
        }
        return toReturn;
    }

    public List<String> addTruckingSources(int truckingIdCounter, List<SiteDTO> sources) {
        //return the exceptions of every destination that got error
        List<String> Exceptions = new LinkedList<String>();
        String query = "INSERT INTO Truckings_Sources(TID,contact_guy,city,phone_number,street,area,house_number,floor,apartment_number) VALUES(?,?,?,?,?,?,?,?,?)";

        for (SiteDTO source: sources) {
            try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl)){
                if(conn != null) {
                    PreparedStatement prepStat = conn.prepareStatement(query);
                    prepStat.setInt(1, truckingIdCounter);
                    prepStat.setString(2, source.getContactGuy());
                    prepStat.setString(3, source.getCity());
                    prepStat.setString(4, source.getPhoneNumber());
                    prepStat.setString(5, source.getStreet());
                    prepStat.setString(6, source.getArea());
                    prepStat.setInt(7, source.getHouseNumber());
                    prepStat.setInt(8, source.getFloor());
                    prepStat.setInt(9, source.getApartment());
                    if (prepStat.executeUpdate() < 1)
                        throw new Exception("The destination is already exist");
                }
                else
                    throw new Exception("The connection with the data lost");
            }
            catch (Exception e){
                Exceptions.add("There was a problem with the destination with the contact guy: " + source.getContactGuy() + "\nthe error description: " + e.getMessage());
            }
        }
        return Exceptions;
    }

    public boolean removeSourcesTrucking(int truckingID) throws Exception {
        String Query = "DELETE FROM Truckings_Sources WHERE TID = '" + truckingID+"'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(Query)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return n > 0;
    }

    public List<SiteDTO> getSourcesByTruckingId(int TruckingID) throws Exception {
        List<SiteDTO> sites = new LinkedList<SiteDTO>();
        String query = "SELECT * FROM Truckings_Sources Where TID = '" + TruckingID+"'";
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             PreparedStatement pstmt  = conn.prepareStatement(query)){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                sites.add(new SiteDTO(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(6)));
            }
        } catch (Exception e) {
            throw new Exception("Oops, there was unexpected problem with get the sources of trucking: \"" + TruckingID + "\"\nError description: " + e.getMessage());
        }
        return sites;
    }

    public List<String> addTruckingDestinations(int truckingIdCounter, List<SiteDTO> destinations) {
        //return the exceptions of every destination that got error
        List<String> Exceptions = new LinkedList<String>();
        String query = "INSERT INTO Truckings_Destinations(TID,contact_guy,city,phone_number,street,area,house_number,floor,apartment_number) VALUES(?,?,?,?,?,?,?,?,?)";

        for (SiteDTO destination : destinations) {
            try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl)){
                if(conn != null) {
                    PreparedStatement prepStat = conn.prepareStatement(query);
                    prepStat.setInt(1, truckingIdCounter);
                    prepStat.setString(2, destination.getContactGuy());
                    prepStat.setString(3, destination.getCity());
                    prepStat.setString(4, destination.getPhoneNumber());
                    prepStat.setString(5, destination.getStreet());
                    prepStat.setString(6, destination.getArea());
                    prepStat.setInt(7, destination.getHouseNumber());
                    prepStat.setInt(8, destination.getFloor());
                    prepStat.setInt(9, destination.getApartment());
                    if (prepStat.executeUpdate() < 1)
                        throw new Exception("The destination is already exist");
                }
                else
                    throw new Exception("The connection with the data lost");
            }
            catch (Exception e){
                Exceptions.add("There was a problem with the destination with the contact guy: " + destination.getContactGuy() + "\nthe error description: " + e.getMessage());
            }
        }
        return Exceptions;
    }

    public boolean removeDestinationsTrucking(int truckingID) throws Exception {
        String Query = "DELETE FROM Truckings_Destinations WHERE TID = '" + truckingID +"'";
        int n = 0;
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             PreparedStatement pstmt = conn.prepareStatement(Query)) {
            n = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return n > 0;
    }

    public List<SiteDTO> getDestinationsByTruckingId(int TruckingID) throws Exception {
        List<SiteDTO> sites = new LinkedList<SiteDTO>();
        String query = "SELECT * FROM Truckings_Destinations Where TID = '" + TruckingID+"'";
        try (Connection conn = DriverManager.getConnection(myDataBase.finalCurl);
             PreparedStatement pstmt  = conn.prepareStatement(query)){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                sites.add(new SiteDTO(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(6)));
            }
        } catch (Exception e) {
            throw new Exception("Oops, there was unexpected problem with get the destinations of trucking: \"" + TruckingID + "\"\nError description: " + e.getMessage());
        }
        return sites;
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


    public boolean removeProductsTrucking(int truckingID) throws Exception {
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

    public List<TruckingDTO> getTruckManagerBoard(int truckManagerUsername) throws Exception {
        if (truckingIDMap.isUserHasUpdatedData(truckManagerUsername))
            return truckingIDMap.getTruckingsOfTruckManager(truckManagerUsername);
        else {
            List<TruckingDTO> toReturn = getBoardOfUserOrVehicle("truck_manager", "" + truckManagerUsername);
            truckingIDMap.updateUser(truckManagerUsername);
            return toReturn;
        }
    }

    public List<TruckingDTO> getTruckManagerFutureTruckings(int truckManagerUsername) throws Exception {
        if (truckingIDMap.isUserHasUpdatedData(truckManagerUsername))
            return truckingIDMap.getFutureTruckingsOfTruckManager(truckManagerUsername);
        else
            return getFutureTruckingsOfUserOrVehicle("truck_manager", "" + truckManagerUsername);
    }

    public List<TruckingDTO> getTruckManagerHistoryTruckings(int truckManagerUsername) throws Exception {
        if (truckingIDMap.isUserHasUpdatedData(truckManagerUsername))
            return truckingIDMap.getHistoryOfTruckManager(truckManagerUsername);
        else
            return getHistoryTruckingsOfUserOrVehicle("truck_manager", "" + truckManagerUsername);
    }

    public List<TruckingDTO> getVehicleBoard(String regristrationPlate) throws Exception {
        if (truckingIDMap.isVehicleHasUpdatedData(regristrationPlate))
            return truckingIDMap.getTruckingsOfVehicle(regristrationPlate);
        else {
            List<TruckingDTO> toReturn = getBoardOfUserOrVehicle("registration_plate", regristrationPlate);
            truckingIDMap.updateVehicle(regristrationPlate);
            return toReturn;
        }
    }

    public List<TruckingDTO> getVehicleFutureTruckings(String regristrationPlate) throws Exception {
        if (truckingIDMap.isVehicleHasUpdatedData(regristrationPlate))
            return truckingIDMap.getFutureTruckingsOfVehicle(regristrationPlate);
        else
            return getFutureTruckingsOfUserOrVehicle("registration_plate", regristrationPlate);
    }

    public List<TruckingDTO> getVehicleHistoryTruckings(String regristrationPlate) throws Exception {
        if (truckingIDMap.isVehicleHasUpdatedData(regristrationPlate))
            return truckingIDMap.getHistoryOfVehicle(regristrationPlate);
        else
            return getHistoryTruckingsOfUserOrVehicle("registration_plate", regristrationPlate);
    }

    public List<TruckingDTO> getDriverBoard(int driverUsername) throws Exception {
        if (truckingIDMap.isUserHasUpdatedData(driverUsername))
            return truckingIDMap.getTruckingsOfDriver(driverUsername);
        else {
            List<TruckingDTO> toReturn = getBoardOfUserOrVehicle("driver_username", "" + driverUsername);
            truckingIDMap.updateUser(driverUsername);
            return toReturn;
        }
    }

    public List<TruckingDTO> getDriverFutureTruckings(int driverUsername) throws Exception {
        if (truckingIDMap.isUserHasUpdatedData(driverUsername))
            return truckingIDMap.getFutureTruckingsOfDriver(driverUsername);
        else
            return getFutureTruckingsOfUserOrVehicle("driver_username", "" + driverUsername);
    }

    public List<TruckingDTO> getDriverHistoryTruckings(int driverUsername) throws Exception {
        if (truckingIDMap.isUserHasUpdatedData(driverUsername))
            return truckingIDMap.getHistoryOfDriver(driverUsername);
        else
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
                int truckingId = rs.getInt(1);
                List<SiteDTO> sources = getSourcesByTruckingId(truckingId);
                List<SiteDTO> destinations = getDestinationsByTruckingId(truckingId);
                List<ProductForTruckingDTO> products = getProducts(truckingId);
                TruckingDTO newTrucking = new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8), sources, destinations, products);
                toReturn.add(newTrucking);
                truckingIDMap.insertTrucking(newTrucking);
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
                int truckingId = rs.getInt(1);
                List<SiteDTO> sources = getSourcesByTruckingId(truckingId);
                List<SiteDTO> destinations = getDestinationsByTruckingId(truckingId);
                List<ProductForTruckingDTO> products = getProducts(truckingId);
                TruckingDTO newTrucking = new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8), sources, destinations, products);
                toReturn.add(newTrucking);
                truckingIDMap.insertTrucking(newTrucking);
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
                int truckingId = rs.getInt(1);
                List<SiteDTO> sources = getSourcesByTruckingId(truckingId);
                List<SiteDTO> destinations = getDestinationsByTruckingId(truckingId);
                List<ProductForTruckingDTO> products = getProducts(truckingId);
                TruckingDTO newTrucking = new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8), sources, destinations, products);
                toReturn.add(newTrucking);
                truckingIDMap.insertTrucking(newTrucking);
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
                int truckingId = rs.getInt(1);
                List<SiteDTO> sources = getSourcesByTruckingId(truckingId);
                List<SiteDTO> destinations = getDestinationsByTruckingId(truckingId);
                List<ProductForTruckingDTO> products = getProducts(truckingId);
                TruckingDTO newTrucking = new TruckingDTO(rs.getInt(1), rs.getString(5), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getInt(6), rs.getInt(7), rs.getInt(8), sources, destinations, products);
                toReturn.add(newTrucking);
                truckingIDMap.insertTrucking(newTrucking);
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
