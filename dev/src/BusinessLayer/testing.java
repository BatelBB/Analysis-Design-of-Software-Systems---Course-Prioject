package BusinessLayer;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class testing {
    private static LinkedList<String> list = new LinkedList<>();
    public static void main(String[] args) throws Exception {
        /*
        Site site = new Site("tamir", "bat-yam", "052-3850279", "Hadadi tzion shaul", 14, 1,3, Area.center);
        Vehicle vehicle = new Vehicle(DLicense.C, "21345687", "skoda", 50000, 100000);
        Driver driver = new Driver("tamir", "tamir_avisar", "Aaaaaa1");
        driver.addLicense(DLicense.C);
        LinkedList<Site> sources = new LinkedList<Site>();
        LinkedList<Site> destinations = new LinkedList<Site>();
        sources.add(site);
        destinations.add(site);
        ProductForTrucking product = new ProductForTrucking(Products.Milk_7290111607400, 12);
        LinkedList<ProductForTrucking> products = new LinkedList<ProductForTrucking>();
        products.add(product);
        Trucking truck = new Trucking(12, vehicle, LocalDateTime.now().plusDays(1), driver, sources, destinations, products);
        System.out.println(truck.printTrucking());
         */
        try {
            add();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println(list.size());

    }

    private static void add() throws Exception {
        list.add("2");
        throwExc();
        list.add("3");
    }

    private static void throwExc() throws Exception {
        throw new Exception("catch me");
    }
}
