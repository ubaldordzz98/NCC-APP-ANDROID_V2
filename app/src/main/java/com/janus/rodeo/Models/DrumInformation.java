package com.janus.rodeo.Models;

public class DrumInformation {
    private  int DrumId;
    public int getDrumId() {
        return DrumId;
    }
    public void setDrumId(int drumId) {
        DrumId = drumId;
    }
    private String DrumName;
    private String CurrentLocation;
    private String StatusDescription;
    private String BackgroundColor;
    private String ForegroundColor;
    private String Gallons;

    public String getGallons() {
        return Gallons;
    }

    public void setGallons(String gallons) {
        Gallons = gallons;
    }

    public String getDrumName() {
        return DrumName;
    }

    public void setDrumName(String drumName) {
        DrumName = drumName;
    }

    public String getCurrentLocation() {
        return CurrentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        CurrentLocation = currentLocation;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
    }

    public String getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        BackgroundColor = backgroundColor;
    }

    public String getForegroundColor() {
        return ForegroundColor;
    }

    public void setForegroundColor(String foregroundColor) {
        ForegroundColor = foregroundColor;
    }

}
