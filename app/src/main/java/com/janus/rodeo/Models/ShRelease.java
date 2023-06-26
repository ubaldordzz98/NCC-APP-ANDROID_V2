package com.janus.rodeo.Models;

public class ShRelease {
    private int ReleaseId;
    private String ReleaseNumber;
    private int TransportType;
    private String TransportDescription;
    private String StatusDescription;
    private String BackgroundStatus;
    private String ForegroundStatus;
    private String Coils;

    public String getReleaseNumber() {
        return ReleaseNumber;
    }

    public void setReleaseNumber(String releaseNumber) {
        ReleaseNumber = releaseNumber;
    }

    public String getCoils() {
        return Coils;
    }

    public void setCoils(String coils) {
        Coils = coils;
    }

    public int getReleaseId() {
        return ReleaseId;
    }

    public void setReleaseId(int releaseId) {
        ReleaseId = releaseId;
    }

    public int getTransportType() {
        return TransportType;
    }

    public void setTransportType(int transportType) {
        TransportType = transportType;
    }

    public String getTransportDescription() {
        return TransportDescription;
    }

    public void setTransportDescription(String transportDescription) {
        TransportDescription = transportDescription;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
    }

    public String getBackgroundStatus() {
        return BackgroundStatus;
    }

    public void setBackgroundStatus(String backgroundStatus) {
        BackgroundStatus = backgroundStatus;
    }

    public String getForegroundStatus() {
        return ForegroundStatus;
    }

    public void setForegroundStatus(String foregroundStatus) {
        ForegroundStatus = foregroundStatus;
    }

}