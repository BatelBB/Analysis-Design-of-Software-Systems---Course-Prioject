package BusinessLayer;

public class Account {
    String passowrd;
    String userName;
    Role role;

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public void setPassword(String password) {
        this.passowrd = password;
    }
}
