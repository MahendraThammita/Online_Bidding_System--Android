package com.example.online_bidding_system;

import java.util.HashMap;

public class Adverticement  {

    private String Title;
    private String Price;
    private String Duration;
    private String Description;
    private String Contact;
    private String Date;
    private String MaxBid;
    private String Status;
    private String Type;
    private HashMap<String , String> imageMap;

    public HashMap<String, String> getImageMap() {
        return imageMap;
    }

    public void setImageMap(HashMap<String, String> imageMap) {
        this.imageMap = imageMap;
    }

    public String getSeller_ID() {
        return seller_ID;
    }

    public void setSeller_ID(String seller_ID) {
        this.seller_ID = seller_ID;
    }

    private String seller_ID;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getDate(){

        return Date;
    }

    public void setDate(String date){
        Date = date;
    }

    public String getMaxBid() {
        return MaxBid;
    }

    public void setMaxBid(String maxBid) {
        MaxBid = maxBid;
    }

}
