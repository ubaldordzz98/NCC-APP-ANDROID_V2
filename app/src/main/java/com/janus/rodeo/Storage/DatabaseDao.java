package com.janus.rodeo.Storage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.janus.rodeo.Storage.tables.tableHistory;
import com.janus.rodeo.Storage.tables.tableUser;
import com.janus.rodeo.Storage.tables.tableCoilInformation;
import com.janus.rodeo.Storage.tables.tableLastLocationSaved;
import com.janus.rodeo.Storage.tables.tableDrumInformation;
import java.util.List;

@Dao
public interface DatabaseDao {
    @Insert
    void addHistoryElements(tableHistory item);

    @Query("SELECT * FROM History ORDER BY HistoryId DESC")
    List<tableHistory> getHistoryDesc();

    @Query("SELECT * FROM History Where ItemType=:itemType ORDER BY HistoryId DESC")
    List<tableHistory>  getHistoryDescByType(int itemType);

    @Query("SELECT * FROM History Where ItemType=:itemType ORDER BY HistoryId")
    List<tableHistory>  getHistoryAscByType(int itemType);

    @Query("SELECT * FROM History Where ItemType=:itemType AND ItemName LIKE '%' || :search || '%' ORDER BY HistoryId DESC")
    List<tableHistory>  getHistoryDescOflineSearch(int itemType, String search);

    @Query("DELETE FROM History where HistoryId =:historyId")
    void deleteHistoryItem(int historyId);

    @Query("DELETE FROM History where ItemId =:itemId AND ItemType=:itemType")
    void deleteHistoryItembyType(int itemId, int itemType);

    @Query("UPDATE History SET ItemLocation=:itemLocation WHERE ItemName=:name")
    void updateHistory(String name, String itemLocation);

    @Query("SELECT * FROM CoilInformation ORDER BY CoilInformationId")
    List<tableCoilInformation> getCoilInfoList();

    @Query("SELECT * FROM CoilInformation WHERE CoilInformationId=:Id")
    tableCoilInformation getCoilByItemId(int Id);

    @Query("SELECT * FROM CoilInformation WHERE Name=:coilName")
    tableCoilInformation getCoilByName(String coilName);

    @Query("SELECT * FROM CoilInformation ORDER BY CoilInformationId DESC LIMIT 1")
    tableCoilInformation getLastCoilAdded();

    @Insert
    void addCoilEntry(tableCoilInformation item);

    @Query("DELETE FROM CoilInformation where CoilInformationId =:coilId")
    void deleteCoil(int coilId);

    @Query("UPDATE CoilInformation SET Name=:name, Status=:status, Location=:location, BackgroundColor=:backgroundColor, ForegroundColor=:foregroundColor, MillCoilNumber=:millCoilNumber,MetalOwner=:metalOwner, AssignedOrder=:assignedOrder  where Name =:name")
    void updateCoil(String name, String status, String location, String backgroundColor, String foregroundColor, String millCoilNumber, String metalOwner, String assignedOrder);

    @Query("SELECT * FROM DrumInformation  ORDER BY DrumInformationId")
    List<tableDrumInformation> getDrumsInfoList();

    @Query("SELECT * FROM DrumInformation WHERE DrumInformationId=:Id")
    tableDrumInformation getDrumByItemId(int Id);

    @Query("SELECT * FROM DrumInformation WHERE Name=:drumName")
    tableDrumInformation getDrumByName(String drumName);

    @Query("SELECT * FROM DrumInformation ORDER BY DrumInformationId DESC LIMIT 1")
    tableDrumInformation getLastDrumAdded();

    @Insert
    void addDrumEntry(tableDrumInformation item);

    @Query("DELETE FROM DrumInformation where DrumInformationId =:drumId")
    void deleteDrum(int drumId);

    @Query("UPDATE DrumInformation SET Name=:name, Status=:status, Location=:location, BackgroundColor=:backgroundColor, ForegroundColor=:foregroundColor, Gallons=:gallons  where Name =:name")
    void updateDrum(String name, String status, String location, String backgroundColor, String foregroundColor, String gallons);

    @Query("SELECT * FROM User")
    tableUser getUsuario();

    @Insert
    void addUsuario(tableUser user);

    @Query("DELETE FROM User where username =:username")
    void deleteUsuario(String username);

    @Query("SELECT * FROM LastLocationSaved WHERE ItemType=:TypeId")
    tableLastLocationSaved getLastLocationByType(int TypeId);

    @Insert
    void addLocationSavedEntry(tableLastLocationSaved item);

    @Query("UPDATE LastLocationSaved SET `Row`=:row, `Column`=:column, Layer=:layer   where ItemType =:type")
    void updateLastLocation(String row, String column, String layer, Integer type);

}