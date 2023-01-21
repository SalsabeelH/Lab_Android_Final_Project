package birzeit.edu.labandroidfinalproject.Models;

public class User {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String password;
    private String preferredContinent;

    public User() {
    }

    public User(String emailAddress, String firstName, String lastName, String password, String preferredContinent) {
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.preferredContinent = preferredContinent;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPreferredContinent() {
        return preferredContinent;
    }

    public void setPreferredContinent(String preferredContinent) {
        this.preferredContinent = preferredContinent;
    }

    @Override
    public String toString() {
        return "User{" +
                "emailAddress='" + emailAddress + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", preferredContinent='" + preferredContinent + '\'' +
                '}';
    }
}
