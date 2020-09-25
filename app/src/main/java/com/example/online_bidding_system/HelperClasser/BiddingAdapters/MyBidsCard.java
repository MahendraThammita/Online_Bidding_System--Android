package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

public class MyBidsCard {


    private String ContactNo;
    private String Description;
    private String Duration;
    private String Title;
    private String Type;
    private String endDate;
    private int MaxBid;
    private int Mybid;
    private int Start_Price;

    public MyBidsCard() {

    }

    public MyBidsCard(String contactNo, String description, String duration, String title, String type, String endDate, int maxBid, int mybid, int start_Price) {
        ContactNo = contactNo;
        Description = description;
        Duration = duration;
        Title = title;
        Type = type;
        this.endDate = endDate;
        MaxBid = maxBid;
        Mybid = mybid;
        Start_Price = start_Price;
    }

    //Getters

    public String getContactNo() {
        return ContactNo;
    }

    public String getDescription() {
        return Description;
    }

    public String getDuration() {
        return Duration;
    }

    public String getTitle() {
        return Title;
    }

    public String getType() {
        return Type;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getMaxBid() {
        return MaxBid;
    }

    public int getMybid() {
        return Mybid;
    }

    public int getStart_Price() {
        return Start_Price;
    }

    //Setters


    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setMaxBid(int maxBid) {
        MaxBid = maxBid;
    }

    public void setMybid(int mybid) {
        Mybid = mybid;
    }

    public void setStart_Price(int start_Price) {
        Start_Price = start_Price;
    }
}
