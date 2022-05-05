package groupk.logistics.business;

public abstract class User {
    protected String name;
    protected String username;
    protected Password password;
    protected boolean isLogin;
    protected Role role;

    public User(String name, String username, String password) throws Exception {
        this.name = name;
        this.username = username;
        this.password = new Password(password);
    }

    public boolean updatePassword(String newPassword) throws Exception {
        return password.setPassword(newPassword);
    }

    public synchronized boolean login(String passwordToCheck) throws Exception {
        if (isLogin)
            throw new Exception("This user is already logged in. First, log out.");
        if (password.checkPassword(passwordToCheck))
            isLogin = true;
        else
            throw new Exception("Wrong username or wrong password.");
        return isLogin;
    }

    public synchronized boolean logout() throws Exception {
        if (!isLogin)
            throw new Exception("The user " + username + " is actually not connected");
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

}
