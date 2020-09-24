package com.example.online_bidding_system;

public class auction {

    private String Title;
    private String Price;
    private String Duration;
    private String Contact;
    private String Materials;
    private String Description;
    private String Type;
    private String Time_period;
    private String Date;


    public auction() {

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

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getMaterials() {
        return Materials;
    }

    public void setMaterials(String materials) {
        Materials = materials;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType(){

        return Type;
    }

    public void setType(String type){
        Type = type;
    }

    public String getTime_period(){

        return Time_period;
    }

    public void setTime_period(String time_period){
        Time_period = time_period;
    }



}
