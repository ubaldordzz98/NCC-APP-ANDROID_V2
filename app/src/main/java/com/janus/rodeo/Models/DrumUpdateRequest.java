package com.janus.rodeo.Models;

public class DrumUpdateRequest {

    public String drumName;
    public String drumLocation;
    public int userId ;

    public String getDrumName() {
        return drumName;
    }

    public void setDrumName(String drumName) {
        this.drumName = drumName;
    }

    public String getDrumLocation() {
        return drumLocation;
    }

    public void setDrumLocation(String drumLocation) {
        this.drumLocation = drumLocation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}