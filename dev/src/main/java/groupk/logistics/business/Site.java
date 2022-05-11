package groupk.logistics.business;

public class Site {
    private String contactGuy;
    private String city;
    private String phoneNumber;
    private String street;
    private int houseNumber;
    private int floor;
    private int apartment;
    private Area area;

    public Site(String contactGuy, String city, String phoneNumber, String street, int houseNumber, int floor, int apartment, String Sarea) throws Exception {
        if (contactGuy == null | contactGuy.length() == 0 | city == null | city.length() == 0 | phoneNumber == null | phoneNumber.length() == 0 | street == null | street.length() == 0)
            throw new IllegalArgumentException("One or more of the site details are empty");
        this.contactGuy = contactGuy;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.houseNumber = houseNumber;
        this.street = street;
        this.floor = floor;
        this.apartment = apartment;
        this.area = castStringToArea(Sarea);
        validateInt(floor, "floor", 0, 100);
        validateInt(apartment, "apartment", 0, 100);
        validateInt(houseNumber, "house number", 1, 300);
        validateString(city, "city", 2, 20);
        validateString(city, "city", 2, 20);
        validateString(city, "contact guy", 2, 15);
        validatePhoneNumber();
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    private Area castStringToArea(String area) throws Exception {
        if(area.equals("center")) return Area.center;
        else if(area.equals("north")) return Area.north;
        else  if(area.equals("south")) return Area.south;
        else throw new IllegalArgumentException(area + " is invalid area");
    }

    public String printSite() {
        String toReturn = "Area: " + area + "\n";
        toReturn += "Address: " + street + " " + houseNumber + ", " + getCity() + "\n";
        if (apartment != 0 | floor != 0)
            toReturn += "floor: " + floor + " apartment: " + apartment + "\n";
        toReturn += "Contact guy: " + contactGuy + "  phone number: " + phoneNumber + "\n";
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


    public Area getArea() {
        return area;
    }

    private boolean validatePhoneNumber() {
        String phoneNumberWithoutHyphens = "";
        for (int index = 0; index < phoneNumber.length(); index++) {
            char charAt = phoneNumber.charAt(index);
            if (charAt > 47 && charAt < 58)
                phoneNumberWithoutHyphens += charAt;
            else if (charAt != 45)
                throw new IllegalArgumentException("The phone number is not validate. the phone number must contains only digits");
        }
        if (phoneNumberWithoutHyphens.length() < 8 | phoneNumberWithoutHyphens.length() > 12)
            throw new IllegalArgumentException("The phone number is too short/long");
        phoneNumber = phoneNumberWithoutHyphens;
        return true;
    }

    private boolean validateInt(int fieldToCheck, String fieldToCheckName, int min, int max) {
        if(fieldToCheck >= min & fieldToCheck <= max)
            return true;
        throw new IllegalArgumentException(fieldToCheckName + " isn't valid. Need to be between " + String.valueOf(min) + "-" + String.valueOf(max) + ".");
    }

    private boolean validateString(String fieldToCheck, String fieldToCheckName, int minLength, int maxLength) {
        if (fieldToCheck == null)
            throw new IllegalArgumentException(fieldToCheckName + " is empty");
        if (fieldToCheck.length() < minLength | fieldToCheck.length() > maxLength)
            throw new IllegalArgumentException(fieldToCheckName + " isn't valid");
        return true;
    }

    public String getStringArea() {
        String area = "";
        if(this.area==Area.north)area="north";
        if(this.area==Area.center)area="center";
        if(this.area==Area.south)area="south";
        return area;
    }
}