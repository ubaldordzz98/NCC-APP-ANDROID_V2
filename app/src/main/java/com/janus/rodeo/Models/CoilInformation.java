package com.janus.rodeo.Models;

public class CoilInformation {
    private  int CoilId;

    private  String CoilName;
    private  String AssignedOrder;
    private  String  StatusDescription;
    private  String MetalOwner;
    private  String CurrentLocation;
    private  String MillCoilNumber;
    private  String CoilType;
    private  String BackgroundStatus;
    private  String ForegroundStatus;

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

    public String getCoilType() {
        return CoilType;
    }

    public void setCoilType(String coilType) {
        CoilType = coilType;
    }

    public int getCoilId() {
        return CoilId;
    }

    public void setCoilId(int coilId) {
        CoilId = coilId;
    }


    public String getCoilName() {
        return CoilName;
    }

    public void setCoilName(String coilCoilName) {
        CoilName = coilCoilName;
    }

    public String getAssignedOrder() {
        return AssignedOrder;
    }

    public void setAssignedOrder(String assignedOrder) {

        AssignedOrder = assignedOrder;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
    }

    public String getMetalOwner() {
        return MetalOwner;
    }

    public void setMetalOwner(String metalOwner) {
        MetalOwner = metalOwner;
    }

    public String getCurrentLocation() {
        return CurrentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        CurrentLocation = currentLocation;
    }

    public String getMillCoilNumber() {
        return MillCoilNumber;
    }

    public void setMillCoilNumber(String millCoilNumber) {
        MillCoilNumber = millCoilNumber;
    }
}