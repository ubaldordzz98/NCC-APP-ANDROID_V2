package com.janus.rodeo.Storage.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CoilInformation")
public class tableCoilInformation {

    @PrimaryKey (autoGenerate = true)

    private  Integer CoilInformationId;

    @ColumnInfo(name="Name")
    private  String Name;

    @ColumnInfo (name="Status")
    private  String Status;

    @ColumnInfo (name="Location")
    private  String Location;

    @ColumnInfo (name="MetalOwner")
    private  String MetalOwner;

    @ColumnInfo (name="MillCoilNumber")
    private  String MillCoilNumber;

    @ColumnInfo (name="AssignedOrder")
    private  String AssignedOrder;

    @ColumnInfo (name="CoilType")
    private  String CoilType;

    @ColumnInfo (name="BackgroundColor")
    private  String BackgroundColor;

    @ColumnInfo (name="ForegroundColor")
    private  String ForegroundColor;

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

    public String getCoilType() {
        return CoilType;
    }

    public void setCoilType(String coilType) {
        CoilType = coilType;
    }

    public Integer getCoilInformationId() {
        return CoilInformationId;
    }

    public void setCoilInformationId( Integer coilInformationId) {
        CoilInformationId = coilInformationId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getMetalOwner() {
        return MetalOwner;
    }

    public void setMetalOwner(String metalOwner) {
        MetalOwner = metalOwner;
    }

    public String getMillCoilNumber() {
        return MillCoilNumber;
    }

    public void setMillCoilNumber(String millCoilNumber) {
        MillCoilNumber = millCoilNumber;
    }

    public String getAssignedOrder() {
        return AssignedOrder;
    }

    public void setAssignedOrder(String assignedOrder) {
        AssignedOrder = assignedOrder;
    }

}