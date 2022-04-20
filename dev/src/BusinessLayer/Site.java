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


    public Site(String contactGuy, String city, String phoneNumber, String street, int houseNumber, int floor, int apartment, Area area) throws Exception {
        if (contactGuy == null | contactGuy.length() == 0 | city == null | city.length() == 0 | phoneNumber == null | phoneNumber.length() == 0 | street == null | street.length() == 0 | area == null)
            throw new IllegalArgumentException("One or more of the site details are empty");
        this.contactGuy = contactGuy;
        this.city=city;
        this.phoneNumber = phoneNumber;
        this.houseNumber = houseNumber;
        this.street = street;
        this.floor = floor;
        this.apartment = apartment;
        this.area = area;
        validateContactGuy();
        validatePhoneNumber();
    }

    private boolean validateContactGuy() throws Exception {
        if (contactGuy == null | contactGuy.length() == 0)
            throw new IllegalArgumentException("Oops, the contact guy field is empty");
        if (contactGuy.length() < 2)
            throw new IllegalArgumentException("Oops, the contact guy name is too short");
        return true;
    }

    private boolean validatePhoneNumber() throws Exception {
        String phoneNumberWithoutHyphens = "";
        for (int index = 0; index < phoneNumber.length(); index++) {
            char charAt = contactGuy.charAt(index);
            if (charAt > 47 || charAt < 58)
                phoneNumberWithoutHyphens += charAt;
            else if (charAt != 45)
                throw new IllegalArgumentException("The phone number is not validate. the phone number must contains only digits");
        }
        phoneNumber = phoneNumberWithoutHyphens;
        return true;
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

    public String printSite() {
        String toReturn = "Area: " + area + "\n";
        toReturn += "   Address: " + street + " " + houseNumber + ", " + getCity() + "\n";
        if (houseNumber != 0)
            toReturn += "            floor: " + floor + " house number: " + houseNumber + "\n";
        toReturn += "   Contact guy: " + contactGuy + "  phone number: " + phoneNumber + "\n";
        return toReturn;
    }

}
