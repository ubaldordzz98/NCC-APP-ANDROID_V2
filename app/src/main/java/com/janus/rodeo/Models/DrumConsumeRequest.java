package com.janus.rodeo.Models;

public class DrumConsumeRequest {

    public String drumName;
    public int userId ;

    public String getDrumName() {
        return drumName;
    }

    public void setDrumName(String drumName) {
        this.drumName = drumName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}