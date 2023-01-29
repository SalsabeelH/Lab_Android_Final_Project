package birzeit.edu.labandroidfinalproject;



public class User {
    private String fname;
    private String lname;
    private String password;
    private String emaile;


    public User(String emaile,String fname, String lname, String password) {
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.emaile = emaile;
    }

    public User() {

    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmaile(String emaile) {
        this.emaile = emaile;
    }

    public String getFname() {
        return fname;
    }

    public String getPassword() {
        return password;
    }

    public String getLname() {
        return lname;
    }


    public String getEmaile() {
        return emaile;
    }
}

