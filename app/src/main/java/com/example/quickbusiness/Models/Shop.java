package com.example.quickbusiness.Models;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickbusiness.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Shop {
//    Attributes declaration
//    String id, owner, location;
    String name, ownerName, location; // title is shop name
    String timeStamp;
//    String timeStamp, UID;

//    ArrayList<Product> productList;

//    Empty Constructor
    public Shop(){

    }

    public Shop(String name, String ownerName, String location, String timeStamp) {
        this.name = name;
        this.ownerName = ownerName;
        this.location = location;
        this.timeStamp = timeStamp;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", location='" + location + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }



}