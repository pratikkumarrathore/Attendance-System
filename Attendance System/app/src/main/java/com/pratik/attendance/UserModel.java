package com.pratik.attendance;

public class UserModel {
    private String Email;

    public UserModel() {
    }

    public UserModel(String email) {
        this.Email=email;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
