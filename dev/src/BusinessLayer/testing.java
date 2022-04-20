package BusinessLayer;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class testing {
    public static void main(String[] args) throws Exception {
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
    }
}
