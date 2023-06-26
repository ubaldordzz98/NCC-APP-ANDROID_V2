package com.janus.rodeo.Storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.janus.rodeo.Storage.tables.tableCoilInformation;
import com.janus.rodeo.Storage.tables.tableDrumInformation;
import com.janus.rodeo.Storage.tables.tableLastLocationSaved;
import com.janus.rodeo.Storage.tables.tableHistory;
import com.janus.rodeo.Storage.tables.tableUser;

@Database(entities = {  tableUser.class,  tableHistory.class, tableCoilInformation.class, tableDrumInformation.class, tableLastLocationSaved.class}, version = 1)

public abstract class DataBase extends RoomDatabase {
    public abstract DatabaseDao getDatabaseDao();
}