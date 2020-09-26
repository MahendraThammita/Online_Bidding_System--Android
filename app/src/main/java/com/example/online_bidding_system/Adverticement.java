package com.example.online_bidding_system;

public class Adverticement {

    private String Title;
    private String MaxBid;
    private String Price;
    private String Duration;
    private String Description;
    private String Contact;
    private String Date;

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

    public String getDate() {

        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMaxBid() {
        return MaxBid;
    }

    public void setMaxBid(String maxBid) {
        MaxBid = maxBid;
    }
}