package JSONEntity;

import java.sql.Date;

public class Credentials {
    private String username;
    private String password;
    private Date signupdate;
    private Person personid;

    public Credentials(String username, String password, Date signupdate){
        this.username = username;
        this.password = password;
        this.signupdate = signupdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getSignupdate() {
        return signupdate;
    }

    public void setSignupdate(Date signupdate) {
        this.signupdate = signupdate;
    }

    public Person getPersonid() {
        return personid;
    }

    public void setPersonid(int pid) {
        personid = new Person(pid);
    }
}
