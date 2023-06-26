package com.janus.rodeo.Storage.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName ="Tags")
public class tableTags {
    @PrimaryKey
    @NonNull
    private String tagId;
    @ColumnInfo(name = "EPC")
    private String EPC;
    @ColumnInfo(name = "EPCDecoded")
    private String EPCDecoded;
    @ColumnInfo(name = "IntensityScan")
    private String IntensityScan;

    public tableTags() {
        tagId = UUID.randomUUID().toString();
    }

    @NonNull
    public String getTagId() {
        return tagId;
    }

    public void setTagId(@NonNull String tagId) {
        this.tagId = tagId;
    }

    public String getEPC() {
        return EPC;
    }

    public void setEPC(String EPC) {
        this.EPC = EPC;
    }

    public String getEPCDecoded() {
        return EPCDecoded;
    }

    public void setEPCDecoded(String EPCDecoded) {
        this.EPCDecoded = EPCDecoded;
    }

    public String getIntensityScan() {
        return IntensityScan;
    }

    public void setIntensityScan(String intensityScan) {
        IntensityScan = intensityScan;
    }
}
