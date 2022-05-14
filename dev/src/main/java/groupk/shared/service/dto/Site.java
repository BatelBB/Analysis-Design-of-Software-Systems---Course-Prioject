package groupk.shared.service.dto;

public class Site {
    public Site(String contactName, String contactPhone, Area area, String city, String street, int houseNumber, int apartment) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.area = area;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartment = apartment;
    }

    public static enum Area {
        South,
        Center,
        North
    }

    public String contactName;
    public String contactPhone;
    public Area area;
    public String city;
    public String street;
    public int houseNumber;
    public int apartment;
}
