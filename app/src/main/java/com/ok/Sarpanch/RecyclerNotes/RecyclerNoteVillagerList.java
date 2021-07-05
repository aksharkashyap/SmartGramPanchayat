package com.ok.Sarpanch.RecyclerNotes;

public class RecyclerNoteVillagerList {

    private String userName,userID,userEmail, userMobile;
    private String availingServices;

    public RecyclerNoteVillagerList(String userName, String userID, String userEmail, String userMobile) {
        this.userName = userName;
        this.userID = userID;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
    }

    public String getUserID(){
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getAvailingServices() {
        return availingServices;
    }
}
