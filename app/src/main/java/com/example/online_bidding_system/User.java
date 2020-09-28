package com.example.online_bidding_system;

public class User {
    private String customeriId;
    private String FullName;
    private String NIC;
    private String Email;
    private String Address;
    private String Pwd;
    private String ContactNo;

    public User() {
    }

    public User(String customeriId, String fullName, String email, String address, String contactNo) {
        this.customeriId = customeriId;
        FullName = fullName;
        Email = email;
        Address = address;
        ContactNo = contactNo;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }


    //Setters


    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getCustomeriId() {
        return customeriId;
    }

    public void setCustomeriId(String customeriId) {
        this.customeriId = customeriId;
    }
}
