package BusinessLayer;

import java.util.LinkedList;
import java.util.List;

public class Site {
    private String contactGuy;
    private String city;
    private String phoneNumber;
    private String street;
    private int houseNumber;
    private int floor;
    private int apartment;
    private Area area;


    public Site(String contactGuy, String city, String phoneNumber, String street, int houseNumber, int floor, int apartment, Area area) {
        this.contactGuy = contactGuy;
        this.city=city;
        this.phoneNumber = phoneNumber;
        this.houseNumber = houseNumber;
        this.street = street;
        this.floor = floor;
        this.apartment = apartment;
        this.area = area;

    }




    public int getApartment() {
        return apartment;
    }

    public int getFloor() {
        return floor;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public String getContactGuy() {
        return contactGuy;
    }

    public String getStreet() {
        return street;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public Area getArea() {
        return area;
    }



}
