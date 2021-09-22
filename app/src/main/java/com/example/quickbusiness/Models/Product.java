package com.example.quickbusiness.Models;

public class Product {
//    Attributes declaration
//    String id, title, url, description, expiryDate;
//    Integer price, quantity;
//    String id, title;

    String productName, productPrice, productDescription, productExpiryDate;
    String url;
    String timeStamp;



//    Empty Constructor
    public Product(){
    }

    public Product(String productName, String productPrice, String productDescription, String productExpiryDate, String url, String timeStamp) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productExpiryDate = productExpiryDate;
        this.url = url;
        this.timeStamp = timeStamp;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(String productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productExpiryDate='" + productExpiryDate + '\'' +
                ", url='" + url + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}


