package com.example.uangmantapkali.models;

public class User {

    private String name;
    private String phone;
    private String dateOfBirth;

    public User() {
    }

    public User(String name, String phone, String dateOfBirth) {
        this.name = name;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
