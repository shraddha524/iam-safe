package com.example.iamsafe;

public class Police {
    private int id;
    private String username;
    private String password;
    private String mobileNumber;

    public Police(int id, String username, String password, String mobileNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mobileNumber = mobileNumber;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}
