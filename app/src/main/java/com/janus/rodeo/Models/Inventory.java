package com.janus.rodeo.Models;

public class Inventory {
    private int Id;
    private String Date;
    private int CoilsAssigned;
    private String CreatedByUserName;
    private String CreatedOn;
    private int FirstTag;
    private int LastTag;

    public int getDrumsAssigned() {
        return DrumsAssigned;
    }

    public void setDrumsAssigned(int drumsAssigned) {
        DrumsAssigned = drumsAssigned;
    }

    private  int DrumsAssigned;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getCoilsAssigned() {
        return CoilsAssigned;
    }

    public void setCoilsAssigned(int coilsAssigned) {
        CoilsAssigned = coilsAssigned;
    }

    public String getCreatedByUserName() {
        return CreatedByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) { CreatedByUserName = createdByUserName; }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public int getFirstTag() { return FirstTag; }

    public void setFirstTag(int firstTag) { FirstTag = firstTag; }

    public int getLastTag(){ return LastTag; }

    public void setLastTag(int lastTag) { LastTag = lastTag; }

}