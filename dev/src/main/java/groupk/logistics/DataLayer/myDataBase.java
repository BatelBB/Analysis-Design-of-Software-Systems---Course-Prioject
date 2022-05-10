package groupk.logistics.DataLayer;

import groupk.logistics.business.Area;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

abstract class myDataBase<T> {
    static Connection con;
    protected static String JDBCurl="jdbc:sqlite:";
    protected static String path = (new File("").getAbsolutePath()).concat("\\superLee.db");
    protected static String finalCurl;
    private String tableName;

    public myDataBase(String tableName) throws SQLException {
        this.tableName = tableName;
        createNewTable();
    }

    private void createNewTable() throws SQLException {
        String vehiclesTable = "CREATE TABLE IF NOT EXISTS " + "Vehicles" + " (\n" +
                "\tregistration_plate INTEGER PRIMARY KEY,\n" +
                "\tmodel TEXT NOT NULL,\n" +
                "\tlicense INTEGER NOT NULL,\n" +
                "\tweight INTEGER NOT NULL,\n" +
                "\tmax_weight INTEGER NOT NULL\n" +
                "\t);\n" +
                "\n";

        String truckManagersTable = "CREATE TABLE IF NOT EXISTS " + "TruckManagers" + " (\n" +
                "\tusername Text PRIMARY KEY,\n" +
                "\tcode TEXT NOT NULL,\n" +
                "FOREIGN KEY(username) REFERENCES Users(username)\n" +
                "\t);\n" +
                "\n";

        String dirversTable = "CREATE TABLE IF NOT EXISTS " + "Drivers" + " (\n" +
                "\tusername Text PRIMARY KEY,\n" +
                "FOREIGN KEY(username) REFERENCES Users(username)\n" +
                "\t);\n" +
                "\n";
        String dirversLicencesTable = "CREATE TABLE IF NOT EXISTS " + "Drivers_Licences" + " (\n" +
                "\tusername Text NOT NULL,\n" +
                "\tlicence TEXT NOT NULL,\n" +
                "\tPRIMARY KEY (username,licence),\n" +
                "FOREIGN KEY(username) REFERENCES Drivers(username)\n" +
                "\t);\n" +
                "\n";
        String Truckings = "CREATE TABLE IF NOT EXISTS " + "Truckings" + " (\n" +
                "\tTID INTEGER PRIMARY KEY,\n" +
                "\ttruck_manager TEXT NOT NULL,\n" +
                "\tregistration_plate TEXT NOT NULL,\n" +
                "\tdriver_username TEXT NOT NULL,\n" +
                "\tdate DATE NOT NULL,\n" +
                "\thours INTEGER NOT NULL,\n" +
                "\tminutes INTEGER NOT NULL,\n" +
                "\tFOREIGN KEY(truck_manager) REFERENCES TruckManagers(username)\n" +
                "\tFOREIGN KEY(registration_plate) REFERENCES Vehicles(registration_plate)\n" +
                "FOREIGN KEY(driver_username) REFERENCES Drivers(username)\n" +
                "\t);\n" +
                "\n";
        String Truckings_Destinations = "CREATE TABLE IF NOT EXISTS " + "Truckings_Destinations" + " (\n" +
                "\tTID INTEGER PRIMARY KEY,\n" +
                "\tcontact_guy TEXT NOT NULL,\n" +
                "\tcity TEXT NOT NULL,\n" +
                "\tphone_number TEXT NOT NULL,\n" +
                "\tstreet TEXT NOT NULL,\n" +
                "\tarea TEXT NOT NULL,\n" +
                "\thouse_number INTEGER NOT NULL,\n" +
                "\tfloor INTEGER NOT NULL,\n" +
                "\tapartment_number INTEGER NOT NULL,\n" +
                "FOREIGN KEY(TID) REFERENCES Truckings(TID)\n" +
                "\t);\n" +
                "\n";
        String Truckings_Sources = "CREATE TABLE IF NOT EXISTS " + "Truckings_Sources" + " (\n" +
                "\tTID INTEGER PRIMARY KEY,\n" +
                "\tcontact_guy TEXT NOT NULL,\n" +
                "\tcity TEXT NOT NULL,\n" +
                "\tphone_number TEXT NOT NULL,\n" +
                "\tstreet TEXT NOT NULL,\n" +
                "\tarea TEXT NOT NULL,\n" +
                "\thouse_number INTEGER NOT NULL,\n" +
                "\tfloor INTEGER NOT NULL,\n" +
                "\tapartment_number INTEGER NOT NULL,\n" +
                "FOREIGN KEY(TID) REFERENCES Truckings(TID)\n" +
                "\t);\n" +
                "\n";
        String Truckings_Products = "CREATE TABLE IF NOT EXISTS " + "Truckings_Products" + " (\n" +
                "\tTID INTEGER PRIMARY KEY,\n" +
                "\tproduct TEXT NOT NULL,\n" +
                "\tquantity INTEGER  NOT NULL,\n" +
                "FOREIGN KEY(TID) REFERENCES Truckings(TID)\n" +
                "\t);\n" +
                "\n";
        String UsersTable = "CREATE TABLE IF NOT EXISTS " + "Users" + " (\n" +
                "\tusername TEXT PRIMARY KEY,\n" +
                "\tpassword TEXT NOT NULL,\n" +
                "\tname TEXT NOT NULL,\n" +
                "\trole TEXT NOT NULL\n" +
                "\t);\n" +
                "\n";
    //    boolean added = truckingMapper.addTrucking(truckingIdCounter,getActiveUser().getUsername(),registrationPlateOfVehicle,driverUsername,date,hours,minutes);

////
//
//        try (Connection conn = DriverManager.getConnection(JDBCurl);
        con = getConnection();
        try(Statement s = con.createStatement()) {
            s.addBatch(UsersTable);
            s.addBatch(vehiclesTable);
            s.addBatch(truckManagersTable);
            s.addBatch(dirversTable);
            s.addBatch(dirversLicencesTable);
            s.addBatch(Truckings);
            s.addBatch(Truckings_Destinations);
            s.addBatch(Truckings_Products);
            s.addBatch(Truckings_Sources);
            s.executeBatch();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static Connection getConnection(){
        finalCurl = JDBCurl.concat(path);
        if (con!= null) {return con;}
        try {
            con = DriverManager.getConnection(finalCurl);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }


    public List<T> selectAll(){
        String query = "SELECT * FROM "+tableName;
        List<T> DTOList=new ArrayList<T>();
        try (Connection conn = this.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            while (rs.next()) {
                DTOList.add(ConvertResultSetToDTO(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return DTOList;
    }

    abstract T ConvertResultSetToDTO(ResultSet rs) throws SQLException;

}
