package com.janus.rodeo.Storage.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LastLocationSaved")
public class tableLastLocationSaved {

    @PrimaryKey(autoGenerate = true)
    private  Integer LastLocationSavedId;

    @ColumnInfo(name="ItemType")
    private  Integer ItemType;

    @ColumnInfo(name="Row")
    private  String Row;

    @ColumnInfo(name="Column")
    private  String Column;

    @ColumnInfo(name="Layer")
    private  String Layer;

    public Integer getLastLocationSavedId() {
        return LastLocationSavedId;
    }

    public void setLastLocationSavedId(Integer lastLocationSavedId) {
        LastLocationSavedId = lastLocationSavedId;
    }

    public Integer getItemType() {
        return ItemType;
    }

    public void setItemType(Integer itemType) {
        ItemType = itemType;
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
}