package com.janus.rodeo.Models;

public class DrumInventory {
    private  String DrumNumber;
    private  double Qty;
    private  String Warehouse;
    private  String Row;
    private  String Column;
    private  String Layer;
    private  String Tag;

    public String getDrumNumber() {
        return DrumNumber;
    }

    public void setDrumNumber(String drumNumber) {
        DrumNumber = drumNumber;
    }

    public double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }

    public String getWarehouse() {
        return Warehouse;
    }

    public void setWarehouse(String warehouse) {
        Warehouse = warehouse;
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

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

}