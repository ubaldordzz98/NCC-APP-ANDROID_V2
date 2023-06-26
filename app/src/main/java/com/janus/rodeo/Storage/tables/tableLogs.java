package com.janus.rodeo.Storage.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName ="Logs")
public class tableLogs {
    @PrimaryKey
    @NonNull
    private String logId;
    @ColumnInfo(name = "TAG")
    private String TAG;
    @ColumnInfo(name = "Message")
    private String Message;

    public tableLogs() {
        logId = UUID.randomUUID().toString();
    }

    @NonNull
    public String getLogId() {
        return logId;
    }

    public void setLogId(@NonNull String logId) {
        this.logId = logId;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
