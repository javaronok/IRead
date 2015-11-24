package com.iread.form;

public class UserSignUpForm {
    private String login;
    private String firstName;
    private String lastName;
    private String passwd;
    private String passwdRepeat;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPasswdRepeat() {
        return passwdRepeat;
    }

    public void setPasswdRepeat(String passwdRepeat) {
        this.passwdRepeat = passwdRepeat;
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

    public void setLastName(String lasstName) {
        this.lastName = lasstName;
    }
}
