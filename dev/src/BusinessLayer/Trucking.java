package BusinessLayer;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Trucking {
    private int truckingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Driver driver;
    private List<Site> sources;
    private List<Site> destinations;
    private Truck truck;


    public Trucking(Truck truck, int truckingId, LocalDateTime startTime, LocalDateTime endTime, Driver driver, List<Site> sources, List<Site> destinations) {
        this.truck = truck;
        this.truckingId = truckingId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.driver = driver;
        this.sources = sources;
        this.destinations = destinations;

    }


    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getTruckingId() {
        return truckingId;
    }

    public List<Site> getDestinations() {
        return destinations;
    }

    public List<Site> getSources() {
        return sources;
    }


    public Truck getTruck() {
        return truck;
    }


}
