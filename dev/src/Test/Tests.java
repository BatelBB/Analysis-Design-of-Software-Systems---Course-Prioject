package Test;


import BusinessLayer.DriverController;
import BusinessLayer.Site;
import BusinessLayer.TruckManagerController;
import BusinessLayer.UserController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Tests {

    private UserController userController;
    private TruckManagerController truckManagerController ;
    private DriverController driverController;

    public Tests() throws Exception {
        userController = UserController.getInstance();
        truckManagerController = TruckManagerController.getInstance();
        driverController = DriverController.getInstance();
    }


    public void setLicenses() throws Exception {
        userController.login("tam1Tamir1", "tam1Tamir1");
        driverController.addLicense("C");
        userController.logout();
        userController.login("ramiF", "rami1Rami1");
        driverController.addLicense("C");
        userController.logout();
    }
    public void addTrucking() throws Exception {
        userController.login("ido1Ido1", "ido1Ido1");
        truckManagerController.addVehicle("C","12345642","BMW",12,14);
        truckManagerController.addVehicle("C","45345642","volvo",12,14);
        List <String> site1 = new LinkedList<>();
        List <String> site2 = new LinkedList<>();
        addToList(site1,"mega","herzliya","0543397995","hamatganit","13","2","3","center");
        addToList(site2,"viktory","haifa","0524321231","hayarden","100","1","6","north");
        Site d1 = new Site("viktory","haifa","0524321231","hayarden",100,1,6,"north");
        List sources = new LinkedList();
        List destinations = new LinkedList();
        sources.add(site1);
        destinations.add(site2);
        String dInStr = "2022-09-09";
        LocalDate localDate = LocalDate.parse(dInStr);
        LocalDateTime datetime = localDate.atTime(1,50,0);
        List <Map<String,Integer>> products = new LinkedList<>();
        Map<String,Integer> prod = new ConcurrentHashMap<>();
        prod.put("water",2);
        products.add(prod);
        truckManagerController.addTrucking("45345642",datetime,"tam1Tamir1", sources,destinations,products,2,30);
        userController.logout();
    }

    private void addToList(List<String> list,String a,String b,String c,String  d,String e, String f ,String g ,String h)
    {
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        list.add(f);
        list.add(g);
        list.add(h);

    }
    public void registerIdoTamirRami() throws Exception {
        userController.registerUser("ido shapira", "ido1Ido1", "ido1Ido1", "trucking manager", "tm1234tm");
        userController.login("ido1Ido1", "ido1Ido1");
        String hashCode = truckManagerController.getRegisterCode();
        userController.logout();
        userController.registerUser("rami fozis", "ramiF", "rami1Rami1", "driver", hashCode);
        userController.registerUser("tamir avisar", "tam1Tamir1", "tam1Tamir1", "driver", hashCode);
    }

    public void resetForTests()
    {
        userController.reserForTests();
        truckManagerController.reserForTests();
    }

    @Before
    public void setUp() throws Exception {
        resetForTests();
        registerIdoTamirRami();
        setLicenses();
        addTrucking();
    }

    @Test
    public void multiplayTruckManagersTests() throws Exception {
        userController.registerUser("ben gurion", "bgu", "Ubgu123", "trucking manager", "tm1234tm");
        userController.login("bgu", "Ubgu123");
        String hashCode = truckManagerController.getRegisterCode();
        userController.logout();
        userController.registerUser("tel aviv", "tau", "Utau123", "driver", hashCode);
        userController.login("bgu", "Ubgu123");
        Assert.assertEquals(truckManagerController.getDriversUsernames().contains("tau"), true);
        Assert.assertEquals(truckManagerController.getDriversUsernames().contains("tam1Tamir1"), false);
        Assert.assertEquals(truckManagerController.getVehiclesRegistrationPlates().contains("12345642"), false);
        userController.logout();
        userController.login("ido1Ido1", "ido1Ido1");
        Assert.assertEquals(truckManagerController.getDriversUsernames().contains("tau"), false);
        Assert.assertEquals(truckManagerController.getDriversUsernames().contains("tam1Tamir1"), true);
        Assert.assertEquals(truckManagerController.getVehiclesRegistrationPlates().contains("12345642"), true);

    }

    @Test
    public void addLicensesFailTests() throws Exception {
        userController.login("tam1Tamir1", "tam1Tamir1");
        try {
            driverController.addLicense("C");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "License is already exist");
        }
        List<String> licenses = new LinkedList<>();
        licenses.add("C");
        try {
            driverController.addLicenses(licenses);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "All the licenses are already exist");
        }

    }
    @Test
    public void updatePasswordTests() throws Exception {
        userController.login("tam1Tamir1", "tam1Tamir1");
        try {
            Assert.assertEquals(userController.updatePassword("pass"), true);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "The minimum password length should be at least 6 characters");
        }
        Assert.assertEquals(userController.updatePassword("passIdo1"), true);
    }
    @Test
    public void loginLogoutTests() throws Exception {
        Assert.assertEquals(userController.login("ido1Ido1", "ido1Ido1"), false);
        try {
            Assert.assertEquals(userController.login("ramiF","rami1Rami1"),true);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"There is a user already connected to the system");
        }
        userController.logout();
        try {
            Assert.assertEquals(userController.login("ramiF","rami2Rami1"),true);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"Wrong username or wrong password.");
        }
        Assert.assertEquals(userController.login("ramiF","rami1Rami1"),true);
        Assert.assertEquals(userController.logout(), true);
    }

    @Test
    public void addVehiclesTests() throws Exception {
        Assert.assertEquals(userController.login("ido1Ido1", "ido1Ido1"), false);
        try {
            truckManagerController.addVehicle("C","45345E642","volvo",12,14);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"Invalid registration plate");
        }
        try {
            truckManagerController.addVehicle("C","45345642","volvo",-4,14);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"Weight is positive");
        }
        try {
            truckManagerController.addVehicle("C","45345642","volvo",12,9);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"Max wight is bigger then weight");
        }
        try {
            truckManagerController.addVehicle("C","45345642","BMW122414124124",12,14);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"Invalid model");
        }
    }

    @Test
    public void addSitesTests() throws Exception {
        Assert.assertEquals(userController.login("ido1Ido1", "ido1Ido1"), false);
        try {
            Site s1 = new Site("mega","herzliya","0543397995","hamatganit",13,500,3,"center");
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"floor isn't valid. Need to be between 0-100.");
        }

        try {
            Site s1 = new Site("mega","herzliya","0543397995","hamatganit",-4,100,3,"center");
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"house number isn't valid. Need to be between 1-300.");
        }
        try {
            Site s1 = new Site("mega","herzliya","0543397995","hamatganit",13,100,8000,"center");
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"apartment isn't valid. Need to be between 0-100.");
        }
        try {
            Site s1 = new Site("this is to long to real name","herzliya","0543397995","hamatganit",13,100,3,"center");
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage()," isn't valid");
        }
        try {
            Site s1 = new Site("mega","herzliya","054323397995","pardiso",13,100,3,"center");
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"The phone number is too short/long");
        }
        try {
            Site s1 = new Site("mega","herzliya","0543397995","hamatganit",13,100,3,"west");
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"wrong area");
        }

    }


    @Test
    public void addlegalTruckingsTests() throws Exception {
        Assert.assertEquals(userController.login("ido1Ido1", "ido1Ido1"), false);
        createTrucking3();
        createTrucking4();
    }

    @Test
    public void addIllegalTruckingsTests() throws Exception {
        Assert.assertEquals(userController.login("ido1Ido1", "ido1Ido1"), false);
        createTrucking1();
        createTrucking2();
    }

    @Test
    public void checkBoardTests() throws Exception {
        Assert.assertEquals(userController.login("ido1Ido1", "ido1Ido1"), false);
        createTrucking3();
        createTrucking4();
        Assert.assertEquals(userController.logout(), true);
        Assert.assertEquals(userController.login("ramiF", "rami1Rami1"), true);
        Assert.assertEquals(driverController.printMyTruckings().contains("herzliya"), true);
        Assert.assertEquals(driverController.printMyFutureTruckings().contains("herzliya"), true);
        Assert.assertEquals(driverController.printMyTruckingsHistory().contains("herzliya"), false);
        Assert.assertEquals(userController.logout(), true);
        Assert.assertEquals(userController.login("ido1Ido1", "ido1Ido1"), false);
        Assert.assertEquals(truckManagerController.printFutureTruckings().contains("herzliya"), true);
        Assert.assertEquals(truckManagerController.getVehiclesRegistrationPlates().get(0), "45345642");
        Assert.assertEquals(truckManagerController.printBoardOfDriver("tam1Tamir1").contains("herzliya"), true);
    }

    @Test
    public void updateTruckTests() throws Exception {
        Assert.assertEquals(userController.login("ido1Ido1", "ido1Ido1"), false);
        createTrucking3();
        createTrucking4();
        List <String> site1 = new LinkedList<>();
        List <String> site2 = new LinkedList<>();
        addToList(site1,"halfree", "herzliya", "0543397993", "davidHamelech", "2", "4", "3", "center");
        addToList(site2,"tivTaam", "haifa", "0543397912", "haravSade", "13", "2", "3", "north");
        List sources = new LinkedList();
        List destinations = new LinkedList();
        sources.add(site1);
        destinations.add(site2);
        truckManagerController.addSourcesToTrucking(1, sources);
        truckManagerController.addDestinationToTrucking(1, destinations);
        truckManagerController.addProductToTrucking(1, "eggs",1);
        Assert.assertEquals(truckManagerController.printBoardOfDriver("tam1Tamir1").contains("halfree"), true);
        Assert.assertEquals(truckManagerController.printBoardOfDriver("tam1Tamir1").contains("tivTaam"), true);
        Assert.assertEquals(truckManagerController.printBoardOfDriver("tam1Tamir1").contains("Eggs"), true);
        truckManagerController.updateDriverOnTrucking(1, "ramiF");
        truckManagerController.updateDestinationsOnTrucking(1, sources);
        truckManagerController.updateVehicleOnTrucking(1, "12345642");
        truckManagerController.updateSourcesOnTrucking(1, destinations);
        Assert.assertEquals(truckManagerController.printBoardOfDriver("ramiF").contains("12345642"), true);
        Assert.assertEquals(truckManagerController.printBoardOfDriver("ramiF").contains("hayarden"), false);
        Assert.assertEquals(truckManagerController.printBoardOfDriver("ramiF").contains("hamatganit"), false);

    }
    private void createTrucking1 () throws Exception {
        List <String> site1 = new LinkedList<>();
        List <String> site2 = new LinkedList<>();
        addToList(site1,"tamirHouse","batYam","0543397995","tamirStr","13","2","3","center");
        addToList(site2,"idoHouse","herzliya","0524321231","idoStr","100","1","6","center");
        List sources = new LinkedList();
        List destinations = new LinkedList();
        sources.add(site1);
        destinations.add(site2);
        String dInStr = "2022-09-09";
        LocalDate localDate = LocalDate.parse(dInStr);
        LocalDateTime datetime = localDate.atTime(1,20,9);
        List <Map<String,Integer>> products = new LinkedList<>();
        Map<String,Integer> prod = new ConcurrentHashMap<>();
        prod.put("milk",2);
        products.add(prod);
        try {
            truckManagerController.addTrucking("12345642",datetime,"tam1Tamir1", sources,destinations,products,1,30);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"Oops, there is another trucking at the same date and with the same driver");
        }
    }

    private void createTrucking2 () throws Exception {
        List <String> site1 = new LinkedList<>();
        List <String> site2 = new LinkedList<>();
        addToList(site1,"tamirHouse","batYam","0543397995","tamirStr","13","2","3","center");
        addToList(site2,"idoHouse","herzliya","0524321231","idoStr","100","1","6","center");
        List sources = new LinkedList();
        List destinations = new LinkedList();
        sources.add(site1);
        destinations.add(site2);
        String dInStr = "2022-09-09";
        LocalDate localDate = LocalDate.parse(dInStr);
        LocalDateTime datetime = localDate.atTime(1,55,0);
        List <Map<String,Integer>> products = new LinkedList<>();
        Map<String,Integer> prod = new ConcurrentHashMap<>();
        prod.put("milk",2);
        products.add(prod);
        try {
            truckManagerController.addTrucking("45345642",datetime,"ramiF", sources,destinations,products,1,30);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"Oops, there is another trucking at the same date and with the same vehicle");
        }
    }

    private void createTrucking3 () throws Exception {
        List <String> site1 = new LinkedList<>();
        List <String> site2 = new LinkedList<>();
        addToList(site1,"tamirHouse","batYam","0543397995","tamirStr","13","2","3","center");
        addToList(site2,"idoHouse","herzliya","0524321231","idoStr","100","1","6","center");
        List sources = new LinkedList();
        List destinations = new LinkedList();
        sources.add(site1);
        destinations.add(site2);
        String dInStr = "2022-09-09";
        LocalDate localDate = LocalDate.parse(dInStr);
        LocalDateTime datetime = localDate.atTime(1,20,9);
        List <Map<String,Integer>> products = new LinkedList<>();
        Map<String,Integer> prod = new ConcurrentHashMap<>();
        prod.put("milk",2);
        products.add(prod);
        try {
            truckManagerController.addTrucking("45345642",datetime,"ramiF", sources,destinations,products,1,30);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"Oops, there is another trucking at the same date and with the same vehicle");
        }
    }
    private void createTrucking4 () throws Exception {
        List <String> site1 = new LinkedList<>();
        List <String> site2 = new LinkedList<>();
        addToList(site1,"tamirHouse","batYam","0543397995","tamirStr","13","2","3","center");
        addToList(site2,"idoHouse","herzliya","0524321231","idoStr","100","1","6","center");
        List sources = new LinkedList();
        List destinations = new LinkedList();
        sources.add(site1);
        destinations.add(site2);
        String dInStr = "2022-09-09";
        LocalDate localDate = LocalDate.parse(dInStr);
        LocalDateTime datetime = localDate.atTime(1,20,9);
        List <Map<String,Integer>> products = new LinkedList<>();
        Map<String,Integer> prod = new ConcurrentHashMap<>();
        prod.put("milk",2);
        products.add(prod);
        truckManagerController.addTrucking("12345642",datetime,"ramiF", sources,destinations,products,1,30);
        Assert.assertEquals(truckManagerController.printBoardOfDriver("ramiF").contains("tamirStr"), true);

    }

}