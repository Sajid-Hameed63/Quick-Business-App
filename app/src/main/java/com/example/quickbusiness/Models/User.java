package com.example.quickbusiness.Models;

public class User {
//    Attributes declaration
    public String email, password;

//    Empty Constructor
    public User(){
    }

//    Parameterized Constructor
    public User(String email, String password){
        this.email = email;
        this.password =  password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}