package groupk.logistics.business;

public abstract class User {
    protected String name;
    protected String username;
    private Password password;
    protected boolean isLogin;
    protected Role role;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = new Password(password);
    }

    public boolean updatePassword(String newPassword) {
        return password.setPassword(newPassword);
    }

    public synchronized boolean login(String passwordToCheck) {
        if (isLogin)
            throw new IllegalArgumentException("This user is already logged in. First, log out.");
        if (password.checkPassword(passwordToCheck))
            isLogin = true;
        else
            throw new IllegalArgumentException("Wrong username or wrong password.");
        return isLogin;
    }

    public synchronized boolean logout() {
        if (!isLogin)
            throw new IllegalArgumentException("The user " + username + " is actually not connected");
        isLogin = false;
        return true;
    }

    public synchronized boolean isLogin() {
        return isLogin;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public  boolean checkPassword(String password)
    {
        return this.password.checkPassword(password);
    }
}
