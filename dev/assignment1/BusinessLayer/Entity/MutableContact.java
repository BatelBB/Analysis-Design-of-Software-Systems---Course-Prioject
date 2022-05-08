package assignment1.BusinessLayer.Entity;

import assignment1.BusinessLayer.Entity.readonly.Contact;

public class MutableContact implements Contact {
    String name;
    String email;
    String phoneNumber;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public MutableContact(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
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
