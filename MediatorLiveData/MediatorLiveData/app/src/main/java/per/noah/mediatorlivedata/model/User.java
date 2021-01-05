package per.noah.mediatorlivedata.model;

public class User {
    private String firstName;
    private String lastName;
    private int old;
    private String sex;

    public User(String firstName, String lastName, int old, String sex){
        this.firstName = firstName;
        this.lastName = lastName;
        this.old = old;
        this.sex = sex;
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

    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}