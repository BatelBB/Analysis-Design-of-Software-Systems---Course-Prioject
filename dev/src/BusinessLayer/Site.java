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
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.houseNumber = houseNumber;
        this.street = street;
        this.floor = floor;
        this.apartment = apartment;
        this.area = area;
        validateInt(floor, "floor", 0, 100);
        validateInt(apartment, "apartment", 1, 100);
        validateInt(houseNumber, "house number", 0, 300);
        validateString(city, "city", 2, 20);
        validateString(city, "city", 2, 20);
        validateString(city, "contact guy", 2, 15);
        validatePhoneNumber();
    }

    public String printSite() {
        String toReturn = "Area: " + area + "\n";
        toReturn += "   Address: " + street + " " + houseNumber + ", " + getCity() + "\n";
        if (houseNumber != 0)
            toReturn += "            floor: " + floor + " apartment: " + apartment + "\n";
        toReturn += "   Contact guy: " + contactGuy + "  phone number: " + phoneNumber + "\n";
        return toReturn;
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

    private boolean validatePhoneNumber() throws Exception {
        String phoneNumberWithoutHyphens = "";
        for (int index = 0; index < phoneNumber.length(); index++) {
            char charAt = phoneNumber.charAt(index);
            if (charAt > 47 || charAt < 58)
                phoneNumberWithoutHyphens += charAt;
            else if (charAt != 45)
                throw new IllegalArgumentException("The phone number is not validate. the phone number must contains only digits");
        }
        if (phoneNumberWithoutHyphens.length() < 9 | phoneNumberWithoutHyphens.length() > 12)
            throw new IllegalArgumentException("The phone number is too short/long");
        phoneNumber = phoneNumberWithoutHyphens;
        return true;
    }

    private boolean validateInt(int fieldToCheck, String fieldToCheckName, int min, int max) {
        if(houseNumber >= min & houseNumber <= max)
            return true;
        throw new IllegalArgumentException(fieldToCheckName + " isn't valid");
    }

    private boolean validateString(String fieldToCheck, String fieldToCheckName, int minLength, int maxLength) {
        if (fieldToCheck == null)
            throw new IllegalArgumentException(fieldToCheckName + " is empty");
        if (fieldToCheck.length() < minLength | fieldToCheck.length() > maxLength)
            throw new IllegalArgumentException(fieldToCheckName + " isn't valid");
        return true;
    }

}
