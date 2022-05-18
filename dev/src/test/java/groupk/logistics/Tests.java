package groupk.logistics;


import groupk.logistics.DataLayer.myDataBase;
import groupk.logistics.business.DLicense;
import groupk.logistics.business.DriverController;
import groupk.logistics.business.TruckManagerController;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Tests {

    private TruckManagerController truckManagerController ;
    private DriverController driverController;
    myDataBase myDataBase = new myDataBase();
    public Tests() throws Exception {
        truckManagerController = TruckManagerController.getInstance();
        driverController = DriverController.getInstance();
    }



    private void resetDB () throws Exception {
        myDataBase.deleteDB();
        myDataBase.createNewTable();
        truckManagerController.forTests();
    }



    @Test
    public void addLicenses() throws Exception {
        resetDB();
        assertEquals(true, driverController.addLicense(319034121, DLicense.B));
        assertEquals(true, driverController.addLicense(319034121, DLicense.C));
        try {
            assertEquals(true, driverController.addLicense(319034121, DLicense.B));
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Oops, there was unexpected problem with add the license 'B' to the driver: '319034121'\n" +
                    "Error description: [SQLITE_CONSTRAINT_PRIMARYKEY]  A PRIMARY KEY constraint failed (UNIQUE constraint failed: Drivers_Licences.username, Drivers_Licences.licence)");
        }
        assertEquals(true, driverController.getMyLicenses(319034121).size() == 2);
    }

    @Test
    public void addVehicle() throws Exception {
        resetDB();
        truckManagerController.addVehicle("B","12345678","volvo",20,40);
        try {
            truckManagerController.addVehicle("B","12345678","volvo",20,40);
        }
        catch (Exception e) {
            assertEquals(e.getMessage(), "[SQLITE_CONSTRAINT_PRIMARYKEY]  A PRIMARY KEY constraint failed (UNIQUE constraint failed: Vehicles.registration_plate)");
        }
        truckManagerController.addVehicle("B", "12315678", "mercedes", 4, 32);
        assertEquals(true, truckManagerController.getVehiclesRegistrationPlates().size() == 2);
    }




    @Test
    public void addTrucking() throws Exception {
        resetDB();
        driverController.addLicense(319034121, DLicense.B);
        truckManagerController.addVehicle("B","12345678","volvo",20,40);
        LocalDate date =  LocalDate.of(2023,10,18);
        LocalTime time =  LocalTime.of(20,0,0);
        LocalDateTime localDateTime =  LocalDateTime.of(date,time);
        localDateTime.plusSeconds(10);
        String[] source = {"tamirHouse","batYam","0543397995","tamirStr","13","2","3","center"};
        String[]  destination = {"idoHouse","herzliya","0524321231","idoStr","100","1","6","center"};
        List<String[]> sources = new LinkedList<>();
        List<String[]> destinations = new LinkedList<>();
        sources.add(source);
        destinations.add(destination);
        List<Map<String,Integer>> products = new LinkedList<>();
        Map<String,Integer> product = new ConcurrentHashMap<>();
        product.put("eggs",2);
        products.add(product);
        truckManagerController.addTrucking(1,"12345678",localDateTime,319034121,sources,destinations,products,2,0);
        truckManagerController.forTests();
        try {
            truckManagerController.addTrucking(1,"12345678",localDateTime,319034121,sources,destinations,products,2,0);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Oops, there is another trucking at the same date and with the same driver");
        }
        assertEquals(driverController.printMyTruckings(319034121).contains("tamirStr"), true);
    }

    private void setDetails() throws Exception {
        driverController.addLicense(319034121, DLicense.B);
        truckManagerController.addVehicle("B","12345678","volvo",20,40);
        LocalDate date =  LocalDate.of(2023,10,18);
        LocalTime time =  LocalTime.of(20,0,0);
        LocalDateTime localDateTime =  LocalDateTime.of(date,time);
        localDateTime.plusSeconds(10);
        String[] source = {"tamirHouse","batYam","0543397995","tamirStr","13","2","3","center"};
        String[]  destination = {"idoHouse","herzliya","0524321231","idoStr","100","1","6","center"};
        List<String[]> sources = new LinkedList<>();
        List<String[]> destinations = new LinkedList<>();
        sources.add(source);
        destinations.add(destination);
        List<Map<String,Integer>> products = new LinkedList<>();
        Map<String,Integer> product = new ConcurrentHashMap<>();
        product.put("Eggs_4902505139314",2);
        products.add(product);
        truckManagerController.addTrucking(1,"12345678",localDateTime,319034121,sources,destinations,products,2,0);

    }

    @Test
    public void updateVehicle() throws Exception {
        resetDB();
        setDetails();
        try {
            truckManagerController.updateVehicleOnTrucking(1,319034121,1,"12315678");
        }
        catch (Exception e) {
            assertEquals(e.getMessage(),"There is a user already connected to the system");
        }
       driverController.printMyTruckings(319034121);
        assertEquals(driverController.printMyTruckings(319034121).contains("12315678"),true);
    }

    @Test
    public void setWeight() throws Exception {
        resetDB();
        setDetails();
        try {
            driverController.setWeightForTrucking(319034121,1,9);
        }
        catch (Exception e) {
            assertEquals(e.getMessage(),"There is a user already connected to the system");
        }
        driverController.printMyTruckings(319034121);
        assertEquals(driverController.printMyTruckings(319034121).contains("Total weight : 9"),true);
    }

    @Test
    public void addProducts() throws Exception {
        resetDB();
        setDetails();
        try {
            truckManagerController.addProductToTrucking(1,1,"Eggs_4902505139314",2);
        }
        catch (Exception e) {
            assertEquals(e.getMessage(),"Oops, the product SKU doesn't exist");
        }
        assertEquals(driverController.printMyTruckings(319034121).contains("Product: " + "Eggs_4902505139314" + ", Quantity: " + "4"),true);
    }
    //
//
    @Test
    public void removeProducts() throws Exception {
        resetDB();
        setDetails();
        truckManagerController.moveProductsToTrucking(1,1,"Eggs_4902505139314",1);
        assertEquals(driverController.printMyTruckings(319034121).contains("Product: " + "Eggs_4902505139314" + ", Quantity: " + "1"),true);


    }
    //
    @Test
    public void removeTrucking() throws Exception {
        resetDB();
        setDetails();
        try {
            truckManagerController.removeTrucking(1,1);
        }
        catch (Exception e) {
            assertEquals(e.getMessage(),"There is a user already connected to the system");
        }
        assertEquals(driverController.printMyTruckings(319034121).contains("Product: " + "Eggs_4902505139314"),false);
    }

    @Test
    public void getRegistrationPlates() throws Exception {
        resetDB();
        setDetails();
        truckManagerController.addVehicle("B","12315678","mercedes",4,32);
        assertEquals(truckManagerController.getVehiclesRegistrationPlates().size()==2,true);
    }
    //
    @Test
    public void checkSites() {
        String[] site1 = {"tamirHouse","batYam","0543397995","tamirStr","13","2","3","center"};
        String[]  site2 = {"idoHouse","herzliya","0524321231","idoStr","100","1","6","north"};
        List<String[]> sites = new LinkedList<>();
        sites.add(site1);
        sites.add(site2);
        try {
            truckManagerController.checkSites(sites);
        }
        catch (Exception e) {
            assertEquals(e.getMessage(),"Not all sites from the same area");
        }

    }


}