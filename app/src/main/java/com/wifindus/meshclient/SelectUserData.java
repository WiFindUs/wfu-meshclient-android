package com.wifindus.meshclient;

public class SelectUserData {

    private String name;
    private int profilePicUrl;

    public SelectUserData(String name,int profilePicUrl){

        this.name = name;
        this.profilePicUrl = profilePicUrl;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(int profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}