package models;

public class Employee {
    private String name;
    private String username;
    private String passwrod;

    public Employee(String name, String username, String passwrod) {
        this.name = name;
        this.username = username;
        this.passwrod = passwrod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswrod() {
        return passwrod;
    }

    public void setPasswrod(String passwrod) {
        this.passwrod = passwrod;
    }
}
