package com.janus.rodeo.Models;

public class HistoryInfo {
    private  int id;
    private  int itemId;
    private  String itemName;
    private  String itemLocation;
    private  int item_type;
    public  HistoryInfo(){ }

    public  HistoryInfo(int itemId, String itemName, String itemLocation, int imgType){
        this.setItemId(id);
        this.setItemName(itemName);
        this.setItemLocation(itemLocation);
        this.setItem_type(imgType);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }
}
