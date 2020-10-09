package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

public class HomeCard {
    private String auctId;
    private String Title;
    private String Duration;
    private String MaxBid;
    private String Image ;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public HomeCard(String aucID, String title, String maxBid, String duration,String image) {
        this.auctId = aucID;
        Title = title;
        MaxBid = maxBid;
        Duration = duration;
        Image = image;
    }

    public HomeCard(String aucID, String title, String maxBid, String duration) {
        this.auctId = aucID;
        Title = title;
        MaxBid = maxBid;
        Duration = duration;

    }



    public String getMaxBid() {
        return MaxBid;
    }

    public void setMaxBid(String maxBid) {
        MaxBid = maxBid;
    }

    public void setHomeCardValues(String Title , String duration,String maxBid){
        this.Title = Title;
        this.Duration = duration;
        this.MaxBid = maxBid;

    }


    public String getDuration() {
        return Duration;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuctId() {
        return auctId;
    }

    public void setAuctId(String auctId) {
        this.auctId = auctId;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public void setTitle(String title) {
        Title = title;
    }

}
