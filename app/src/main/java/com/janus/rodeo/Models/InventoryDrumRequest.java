package com.janus.rodeo.Models;

public class InventoryDrumRequest {
    private String DrumNumber;
    private double Qty;
    private String Row;
    private String Column;
    private String Layer;
    private int PhysicalInventory;
    private String Tag;
    private int UserId;

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getDrumNumber() {
        return DrumNumber;
    }

    public void setDrumNumber(String drumNumber) {
        DrumNumber = drumNumber;
    }

    public Double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }

    public String getRow() {
        return Row;
    }

    public void setRow(String row) {
        Row = row;
    }

    public String getColumn() {
        return Column;
    }

    public void setColumn(String column) {
        Column = column;
    }

    public String getLayer() {
        return Layer;
    }

    public void setLayer(String layer) {
        Layer = layer;
    }

    public int getPhysicalInventory() {
        return PhysicalInventory;
    }

    public void setPhysicalInventory(int physicalInventory) {
        PhysicalInventory = physicalInventory;
    }
}