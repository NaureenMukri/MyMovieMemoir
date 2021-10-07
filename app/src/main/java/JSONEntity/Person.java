package JSONEntity;

import java.sql.Date;

public class Person {
    private int personid;
    private String firstname;
    private String surname;
    private String gender;
    private Date dateofbirth;
    private String address;
    private String state;
    private int postcode;

    public Person(int personid, String firstname, String surname, String gender, Date dateofbirth,
                  String address, String state, int postcode){
        this.personid = personid;
        this.firstname = firstname;
        this.surname = surname;
        this.gender = gender;
        this.dateofbirth = dateofbirth;
        this.address = address;
        this.state = state;
        this.postcode = postcode;
    }

    public Person(int personid){
        this.personid = personid;
    }

    public int getPersonid() {
        return personid;
    }

    public void setPersonid(int personid) {
        this.personid = personid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }
}
