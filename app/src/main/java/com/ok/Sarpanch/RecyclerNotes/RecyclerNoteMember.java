package com.ok.Sarpanch.RecyclerNotes;

public class RecyclerNoteMember {
    private String userName,userID,userEmail, userMobile;

    public RecyclerNoteMember(String userName, String userID, String userEmail, String userMobile) {
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
}
