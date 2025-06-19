package com.example.iamsafe;

public class User {
    private String userName;
    private String mobileNumber;
    private String Name;

    public User(int id, String userName, String mobileNumber) {
        this.userName = userName;
        this.mobileNumber = mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getName() {
        return Name;

    }
}
