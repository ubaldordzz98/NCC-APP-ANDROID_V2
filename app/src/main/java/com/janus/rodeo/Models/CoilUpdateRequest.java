package com.janus.rodeo.Models;

public class CoilUpdateRequest {
    private  String coilName;
    private  String coilLocation;
    private  String coilType;
    private  int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCoilType() {
        return coilType;
    }

    public void setCoilType(String coilType) {
        this.coilType = coilType;
    }

    public String getCoilName() {
        return coilName;
    }

    public void setCoilName(String coilName) {
        this.coilName = coilName;
    }

    public String getCoilLocation() {
        return coilLocation;
    }

    public void setCoilLocation(String coilLocation) {
        this.coilLocation = coilLocation;
    }

}