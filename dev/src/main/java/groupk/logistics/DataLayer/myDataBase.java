package groupk.logistics.DataLayer;

import groupk.logistics.business.Area;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class myDataBase {
    static Connection con;
    private static String JDBCurl = "jdbc:sqlite:";
    private static String path = (new File("").getAbsolutePath()).concat("\\superLee2.db");
    public static String finalCurl = JDBCurl.concat(path);

    public myDataBase() throws Exception {
        createNewTable();
    }


    public void deleteDB()
    {
        try(Connection conn = DriverManager.getConnection(finalCurl);
            Statement stmt = conn.createStatement();
        ) {
            String table1 = "DROP TABLE Vehicles";
            String table2 = "DROP TABLE Drivers_Licences";
            String table3 = "DROP TABLE Truckings";
            String table4 = "DROP TABLE Truckings_Destinations";
            String table5 = "DROP TABLE Truckings_Sources";
            String table6 = "DROP TABLE Truckings_Products";

            stmt.executeUpdate(table1);
            stmt.executeUpdate(table2);
            stmt.executeUpdate(table3);
            stmt.executeUpdate(table4);
            stmt.executeUpdate(table5);
            stmt.executeUpdate(table6);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNewTable() throws Exception {
        String vehiclesTable = "CREATE TABLE IF NOT EXISTS " + "Vehicles" + " (\n" +
                "\tregistration_plate INTEGER PRIMARY KEY,\n" +
                "\tmodel TEXT NOT NULL,\n" +
                "\tlicense TEXT NOT NULL,\n" +
                "\tweight INTEGER NOT NULL,\n" +
                "\tmax_weight INTEGER NOT NULL\n" +
                "\t);\n" +
                "\n";
        String dirversLicencesTable = "CREATE TABLE IF NOT EXISTS " + "Drivers_Licences" + " (\n" +
                "\tusername INTEGER NOT NULL,\n" +
                "\tlicence TEXT NOT NULL,\n" +
                "\tPRIMARY KEY (username,licence)\n" +
                "\t);\n" +
                "\n";
        String Truckings = "CREATE TABLE IF NOT EXISTS " + "Truckings" + " (\n" +
                "\tTID INTEGER PRIMARY KEY,\n" +
                "\ttruck_manager INTEGER NOT NULL,\n" +
                "\tregistration_plate TEXT NOT NULL,\n" +
                "\tdriver_username INTEGER NOT NULL,\n" +
                "\tdate TEXT NOT NULL,\n" +
                "\thours INTEGER NOT NULL,\n" +
                "\tminutes INTEGER NOT NULL,\n" +
                "\tweight INTEGER NOT NULL,\n" +
                "\tFOREIGN KEY(registration_plate) REFERENCES Vehicles(registration_plate)\n" +
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
                "\tTID INTEGER  NOT NULL,\n" +
                "\tproduct TEXT NOT NULL,\n" +
                "\tquantity INTEGER  NOT NULL,\n" +
                "\tPRIMARY KEY (TID,product),\n" +
                "FOREIGN KEY(TID) REFERENCES Truckings(TID)\n" +
                "\t);\n" +
                "\n";
        //    boolean added = truckingMapper.addTrucking(truckingIdCounter,getActiveUser().getUsername(),registrationPlateOfVehicle,driverUsername,date,hours,minutes);

////
//
//        try (Connection conn = DriverManager.getConnection(JDBCurl);
        try (Statement s = DriverManager.getConnection(finalCurl).createStatement()) {
            s.addBatch(vehiclesTable);
            s.addBatch(dirversLicencesTable);
            s.addBatch(Truckings);
            s.addBatch(Truckings_Destinations);
            s.addBatch(Truckings_Products);
            s.addBatch(Truckings_Sources);
            s.executeBatch();

        } catch (Exception e) {
            throw new Exception("There was a problem to connect the database: " + e.getMessage());
        }
    }


    public static Connection getConnection() throws Exception {
        finalCurl = JDBCurl.concat(path);
        if (con != null) {
            return con;
        }
        try {
            con = DriverManager.getConnection(finalCurl);
        } catch (SQLException e) {
            throw new Exception("There was a problem to connect the database: " + e.getMessage());
        }
        return con;
    }

}