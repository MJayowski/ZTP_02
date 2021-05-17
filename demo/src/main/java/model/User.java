package model;

public class User {

    protected String login;
    protected String pass;
    final protected Role role;

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
        this.role = Role.USER;
    }

    public User(String login, String pass, Role role) {
        this.login = login;
        this.pass = pass;
        this.role = role;
    }

    public boolean validatePassword(String pass) {
        return (pass != null && pass.equals(this.pass));
    }

    public String getUserName() {
        return login;
    }
}
