package rszymani.api.users;


import java.util.Date;
import java.util.concurrent.TimeUnit;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Integer phoneNo;

    public User() {
    }
    public User(String firstName, String lastName, Date birthDate, Integer phoneNo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNo = phoneNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Integer phoneNo) {
        this.phoneNo = phoneNo;
    }
    private long age(){
        Date now = new Date(System.currentTimeMillis());
        long diffInMillies = Math.abs(now.getTime() - birthDate.getTime());
        long diff_days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff_days/365;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", firstName:'" + firstName + '\'' +
                ", lastName:" + lastName +
                ", age:" + age() +
                ", phoneNo:" + phoneNo +
                '}';
    }
}




