package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

public class HomeCard {
    private String auctId;
    private String Title;
    private String Duration;
    private int MaxBid;


    public HomeCard(String aucID, String title, int maxBid, String duration) {
        this.auctId = aucID;
        Title = title;
        MaxBid = MaxBid;
        Duration = duration;
    }

    public HomeCard(String title, int MaxBid) {

        Title = title;
        MaxBid = MaxBid;

    }

    public int getMaxBid() {
        return MaxBid;
    }

    public void setMaxBid(int maxBid) {
        MaxBid = maxBid;
    }

    public void setHomeCardValues(String Title , String duration,int maxBid){
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
