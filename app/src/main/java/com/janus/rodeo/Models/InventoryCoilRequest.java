package com.janus.rodeo.Models;

public class InventoryCoilRequest {
    private String CoilNumber;
    private String CoilType;
    private String Row;
    private int Column;
    private int Layer;
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

    public String getCoilNumber() {
        return CoilNumber;
    }

    public void setCoilNumber(String coilNumber) {
        CoilNumber = coilNumber;
    }

    public String getCoilType() {
        return CoilType;
    }

    public void setCoilType(String coilType) {
        CoilType = coilType;
    }

    public String getRow() {
        return Row;
    }

    public void setRow(String row) {
        Row = row;
    }

    public int getColumn() {
        return Column;
    }

    public void setColumn(int column) {
        Column = column;
    }

    public int getLayer() {
        return Layer;
    }

    public void setLayer(int layer) {
        Layer = layer;
    }

    public int getPhysicalInventory() {
        return PhysicalInventory;
    }

    public void setPhysicalInventory(int physicalInventory) {
        PhysicalInventory = physicalInventory;
    }
}