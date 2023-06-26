package com.janus.rodeo.Models;

public class CoilInventory {
    private String CoilNumber;
    private String CoilType;
    private String Warehouse;
    private String Row;
    private String Column;
    private String Layer;
    private String Tag;

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