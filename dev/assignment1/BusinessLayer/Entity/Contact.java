package assignment1.BusinessLayer.Entity;

public class Contact {
    String name;
    String email;
    String phoneNumber;

    public Contact(String name, String email, String phoneNumber){
        this.name=name;
        this.email=email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format(
                "[CONTACT: %s | %s | %s]",
                name, email, phoneNumber
        );
    }
}
