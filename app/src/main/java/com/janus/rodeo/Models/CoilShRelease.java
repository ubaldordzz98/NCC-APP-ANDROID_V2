package com.janus.rodeo.Models;

public class CoilShRelease {
    private int IdNode;
    private  int ParentIdNode;
    private String ShReleaseNumber;
    private String Name;
    private String CoilType;
    private String StatusDescription;
    private String CurrentLocation;
    private String BackgroundStatus;
    private  int HasScanned;
    private int AssignProducts;
    private double ReleaseTotalWeight;
    private double CoilWeight;
    private  int IsLoaded;
    private int Action;

    public int getAssignProducts() {
        return AssignProducts;
    }

    public void setAssignProducts(int assignProducts) {
        AssignProducts = assignProducts;
    }

    public double getReleaseTotalWeight() {
        return ReleaseTotalWeight;
    }

    public void setReleaseTotalWeight(double releaseTotalWeight) {
        ReleaseTotalWeight = releaseTotalWeight;
    }

    public double getCoilWeight() {
        return CoilWeight;
    }

    public void setCoilWeight(double coilWeight) {
        CoilWeight = coilWeight;
    }

    public int getIsLoaded() {
        return IsLoaded;
    }

    public void setIsLoaded(int isLoaded) {
        IsLoaded = isLoaded;
    }

    public int getAction() {
        return Action;
    }

    public void setAction(int action) {
        Action = action;
    }

    public int getHasScanned() {
        return HasScanned;
    }

    public void setHasScanned(int hasScanned) {
        HasScanned = hasScanned;
    }

    public int getIdNode() {
        return IdNode;
    }

    public void setIdNode(int idNode) {
        IdNode = idNode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getParentIdNode() {
        return ParentIdNode;
    }

    public void setParentIdNode(int parentIdNode) {
        ParentIdNode = parentIdNode;
    }

    private String ForegroundStatus;
    private String OrderName;
    private Integer TransportItemId;

    public int getIsOrderHeader() {
        return IsOrderHeader;
    }

    public void setIsOrderHeader(int isOrderHeader) {
        IsOrderHeader = isOrderHeader;
    }

    private int TransportItemType;
    private  int IsOrderHeader;

    public String getShReleaseNumber() {
        return ShReleaseNumber;
    }

    public void setShReleaseNumber(String shReleaseNumber) {
        ShReleaseNumber = shReleaseNumber;
    }


    public String getCoilType() {
        return CoilType;
    }

    public void setCoilType(String coilType) {
        CoilType = coilType;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
    }

    public String getCurrentLocation() {
        return CurrentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        CurrentLocation = currentLocation;
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

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    public int getTransportItemId() {
        return TransportItemId;
    }

    public void setTransportItemId(int transportItemId) {
        TransportItemId = transportItemId;
    }

    public int getTransportItemType() {
        return TransportItemType;
    }

    public void setTransportItemType(int transportItemType) {
        TransportItemType = transportItemType;
    }
}