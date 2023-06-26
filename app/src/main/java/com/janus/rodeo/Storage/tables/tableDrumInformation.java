package com.janus.rodeo.Storage.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DrumInformation")
public class tableDrumInformation {

    @PrimaryKey(autoGenerate = true)
    private  Integer DrumInformationId;

    @ColumnInfo(name="Name")
    private  String Name;

    @ColumnInfo(name="Location")
    private  String Location;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @ColumnInfo (name="Status")
    private  String Status;

    public Integer getDrumInformationId() {
        return DrumInformationId;
    }

    public void setDrumInformationId(Integer drumInformationId) {
        DrumInformationId = drumInformationId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
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

    @ColumnInfo(name="BackgroundColor")
    private String BackgroundColor;

    @ColumnInfo(name="ForegroundColor")
    private String ForegroundColor;

    @ColumnInfo(name="Gallons")
    private String Gallons;

    public String getGallons() {
        return Gallons;
    }

    public void setGallons(String gallons) {
        Gallons = gallons;
    }
}