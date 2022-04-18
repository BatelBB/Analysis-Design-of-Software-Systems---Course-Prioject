package BusinessLayer;

public abstract class User {
    protected String name;
    protected String id;
    protected Account account;
    protected boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public  void setAccount(Role role, String password, String username)
    {
        account.setRole(role);
        account.setUserName(username);
        account.setPassword(password);
    }

    public  void setLogin(){isLogin = true;}
}
