package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

public class MyBidsCard {

    private String auctionId;
    private String ContactNo;
    private String Description;
    private String Duration;
    private String Title;
    private String Type;
    private String endDate;
    private String seller_id;
    private  String status;
    private String img;
    private int MaxBid;
    private int Mybid;
    private int Start_Price;


    public MyBidsCard() {

    }

    public MyBidsCard(String auctionId, String contactNo, String description, String duration, String title, String type, String endDate, String seller_id, String status, int maxBid, int start_Price , String img) {
        this.auctionId = auctionId;
        ContactNo = contactNo;
        Description = description;
        Duration = duration;
        Title = title;
        Type = type;
        this.endDate = endDate;
        this.seller_id = seller_id;
        this.status = status;
        MaxBid = maxBid;
        Start_Price = start_Price;
        this.img = img;
    }

    public MyBidsCard(String auctionId, String contactNo, String description, String duration, String title, String type, String endDate, String seller_id, String status, int maxBid, int start_Price) {
        this.auctionId = auctionId;
        ContactNo = contactNo;
        Description = description;
        Duration = duration;
        Title = title;
        Type = type;
        this.endDate = endDate;
        this.seller_id = seller_id;
        this.status = status;
        MaxBid = maxBid;
        Start_Price = start_Price;
    }

    public MyBidsCard(String auctionId , String contactNo, String description, String duration, String title, String type, String endDate, int maxBid, int mybid, int start_Price , String img) {
        this.auctionId = auctionId;
        ContactNo = contactNo;
        Description = description;
        Duration = duration;
        Title = title;
        Type = type;
        this.endDate = endDate;
        MaxBid = maxBid;
        Mybid = mybid;
        Start_Price = start_Price;
        this.img = img;
    }


    public void setMywinCardValues(String Title , String contactNo, String duration, String endDate, int maxBid, int mybid, String seller_id  , String img){
        this.Title = Title;
        this.ContactNo = contactNo;
        this.Duration = duration;
        this.endDate = endDate;
        this.MaxBid = maxBid;
        this.Mybid = mybid;
        this.seller_id = seller_id;
        this.img = img;
    }

    public void setMyAuctionCardValues(String Title, String duration, String endDate, int maxBid, String ADid, String Type, String status , String img){
        this.Title = Title;
        this.Duration = duration;
        this.endDate = endDate;
        this.MaxBid = maxBid;
        this.auctionId = ADid;
        this.Type = Type;
        this.status = status;
        this.img = img;
    }

    public void setDraftAuctionsValues(String Title , String duration, String endDate, int maxBid, String ADid, String Type){
        this.Title = Title;
        this.Duration = duration;
        this.endDate = endDate;
        this.MaxBid = maxBid;
        this.auctionId = ADid;
        this.Type = Type;
    }

    //Getters


    public String getAuctionId() {
        return auctionId;
    }

    public String getSeller_id() {
        return seller_id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //Setters


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

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

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }
}
