package groupk.shared.service.dto;

import java.util.Calendar;
import java.util.List;

public class Delivery {
    public int id;
    public Calendar date;
    public Driver driver;
    public String vehicleRegistration;
    public List<Site> sources;
    public List<Site> destinations;
    public int totalWeight;
    public List<Product> load;
    public long durationInMinutes;
}
