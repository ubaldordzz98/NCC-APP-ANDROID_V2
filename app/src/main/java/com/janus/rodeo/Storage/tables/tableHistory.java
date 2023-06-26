package com.janus.rodeo.Storage.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "History")
public class tableHistory {
    @PrimaryKey (autoGenerate = true)
    private  Integer HistoryId;
    @ColumnInfo (name="ItemId")
    private  Integer ItemId;
    @ColumnInfo (name="ItemName")
    private  String ItemName;
    @ColumnInfo (name="ItemLocation")
    private  String ItemLocation;
    @ColumnInfo (name="ItemType")
    private  Integer ItemType;

    public Integer getHistoryId() {
        return HistoryId;
    }
    public void setHistoryId(Integer historyId) {
        HistoryId = historyId;
    }

    public Integer getItemId() {
        return ItemId;
    }
    public void setItemId(Integer itemId) {
        ItemId = itemId;
    }

    public String getItemName() {
        return ItemName;
    }
    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemLocation() {
        return ItemLocation;
    }
    public void setItemLocation(String itemLocation) {
        ItemLocation = itemLocation;
    }

    public Integer getItemType() {
        return ItemType;
    }
    public void setItemType(Integer itemTpe) {
        ItemType = itemTpe;
    }

}