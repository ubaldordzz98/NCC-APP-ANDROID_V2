package com.janus.rodeo.Storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import com.janus.rodeo.Models.CoilInformation;
import com.janus.rodeo.Models.DrumInformation;
import com.janus.rodeo.Models.HistoryInfo;
import com.janus.rodeo.Models.User;
import com.janus.rodeo.Storage.tables.tableHistory;
import com.janus.rodeo.Storage.tables.tableCoilInformation;
import com.janus.rodeo.Storage.tables.tableDrumInformation;
import com.janus.rodeo.Storage.tables.tableLastLocationSaved;
import com.janus.rodeo.Storage.tables.tableUser;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConection {
    @SuppressLint("StaticFieldLeak")
    private static DataBaseConection sDataBaseConection;
    private DatabaseDao databaseDao;

    private DataBaseConection(Context context) {
        Context appContext = context.getApplicationContext();
        try {
            DataBase database = Room.databaseBuilder(appContext, DataBase.class, "NCCPLine")
                    .allowMainThreadQueries().build();
            databaseDao = database.getDatabaseDao();
        } catch (Exception ex) {
            Log.d("Error",ex.toString());
        }

    }

    public static DataBaseConection get(Context context) {
        if (sDataBaseConection == null) {
            sDataBaseConection = new DataBaseConection(context);
        }
        return sDataBaseConection;
    }

    public User getUser(){
        User userLogged = new User();
        tableUser userDB = databaseDao.getUsuario();
        if(userDB != null){
            userLogged.setUserName(userDB.getUsername());
            userLogged.setPassword(userDB.getPasword());
            userLogged.setToken(userDB.getToken());

            return userLogged;
        }else {
            return null;
        }

    }

    public void addUser(User user){
        tableUser userDB = new tableUser();
        userDB.setUsername(user.getUserName());
        userDB.setPasword(user.getPassword());
        userDB.setToken(user.getToken());
        databaseDao.addUsuario(userDB);
    }

    public void deleteUser(User user){
        tableUser userDB = new tableUser();
        userDB.setUsername(user.getUserName());
        userDB.setPasword(user.getPassword());
        userDB.setToken(user.getToken());
        databaseDao.deleteUsuario(userDB.getUsername());
    }

    public List<HistoryInfo> get_history_items() {
        List<HistoryInfo> listItems = new ArrayList<HistoryInfo>();
        List<tableHistory> listdb = databaseDao.getHistoryDesc();
        for (tableHistory e: listdb) {
            HistoryInfo item = new HistoryInfo();
            item.setId(e.getHistoryId());
            item.setItemName(e.getItemName());
            item.setItem_type(e.getItemType());
            item.setItemLocation(e.getItemLocation());
            listItems.add(item);
        }
        return listItems;
    }

    public void addHistoryItem(HistoryInfo item){
        tableHistory history = new tableHistory();
        history.setItemId(item.getItemId());
        history.setItemLocation(item.getItemLocation());
        history.setItemType(item.getItem_type());
        history.setItemName(item.getItemName());
        databaseDao.addHistoryElements(history);
    }

    public void deleteHistoryItem(HistoryInfo history){
        tableHistory _history = new tableHistory();
        _history.setHistoryId(history.getId());
        databaseDao.deleteHistoryItem(_history.getHistoryId());
    }

    public List<HistoryInfo>  getHistoryByType(Integer code){
        List<HistoryInfo> listItems = new ArrayList<HistoryInfo>();
        List<tableHistory> listdb = databaseDao.getHistoryDescByType(code);
        for (tableHistory e: listdb) {
            HistoryInfo item = new HistoryInfo();
            item.setId(e.getHistoryId());
            item.setItemName(e.getItemName());
            item.setItem_type(e.getItemType());
            item.setItemLocation(e.getItemLocation());
            item.setItemId(e.getItemId());
            listItems.add(item);
        }
        return listItems;
    }

    public  List<HistoryInfo>  getHistoryOfline(int itemType, String search){
        List<HistoryInfo> listItems = new ArrayList<HistoryInfo>();
        List<tableHistory> listdb = databaseDao.getHistoryDescOflineSearch(itemType, search);
        for (tableHistory e: listdb) {
            HistoryInfo item = new HistoryInfo();
            item.setId(e.getHistoryId());
            item.setItemName(e.getItemName());
            item.setItem_type(e.getItemType());
            item.setItemLocation(e.getItemLocation());
            item.setItemId(e.getItemId());

            listItems.add(item);
        }
        return listItems;
    }

    public void addCoil(CoilInformation item){
        List<tableCoilInformation> listCoils=databaseDao.getCoilInfoList();
        if(listCoils.size()>=10){
            databaseDao.deleteCoil(listCoils.get(0).getCoilInformationId());
            databaseDao.deleteHistoryItembyType(listCoils.get(0).getCoilInformationId(), 1);
        }

        tableCoilInformation coil = new tableCoilInformation();
        coil.setName(item.getCoilName());
        coil.setAssignedOrder(item.getAssignedOrder());
        coil.setCoilType(item.getCoilType());
        coil.setLocation(item.getCurrentLocation());
        coil.setMetalOwner(item.getMetalOwner());
        coil.setMillCoilNumber(item.getMillCoilNumber());
        coil.setStatus(item.getStatusDescription());
        coil.setBackgroundColor(item.getBackgroundStatus());
        coil.setForegroundColor(item.getForegroundStatus());
        databaseDao.addCoilEntry(coil);
    }

    public void updateCoilInformation(CoilInformation item){
        databaseDao.updateCoil(item.getCoilName(),item.getStatusDescription(),item.getCurrentLocation(), item.getBackgroundStatus(),
                item.getForegroundStatus(),item.getMillCoilNumber(), item.getMetalOwner(), item.getAssignedOrder());

        databaseDao.updateHistory(item.getCoilName(), item.getCurrentLocation());
    }

    public CoilInformation  getCoilByItemId(int id){
        CoilInformation modelCoilInformation = new CoilInformation();
        tableCoilInformation coilDb = databaseDao.getCoilByItemId(id);
        if(coilDb!=null){
            modelCoilInformation.setCoilId(coilDb.getCoilInformationId());
            modelCoilInformation.setCoilName(coilDb.getName());
            modelCoilInformation.setAssignedOrder((coilDb.getAssignedOrder()));
            modelCoilInformation.setCoilType(coilDb.getCoilType());
            modelCoilInformation.setCurrentLocation(coilDb.getLocation());
            modelCoilInformation.setMetalOwner(coilDb.getMetalOwner());
            modelCoilInformation.setMillCoilNumber(coilDb.getMillCoilNumber());
            modelCoilInformation.setStatusDescription(coilDb.getStatus());
            modelCoilInformation.setBackgroundStatus(coilDb.getBackgroundColor());
            modelCoilInformation.setForegroundStatus(coilDb.getForegroundColor());
            return modelCoilInformation;
        } else {
            return  null;
        }
    }

    public CoilInformation  getCoilByBarcode(String  name){
        CoilInformation modelCoilInformation = new CoilInformation();
        tableCoilInformation coilDb = databaseDao.getCoilByName(name);
        if(coilDb!=null) {
            modelCoilInformation.setCoilId(coilDb.getCoilInformationId());
            modelCoilInformation.setCoilName(coilDb.getName());
            modelCoilInformation.setAssignedOrder((coilDb.getAssignedOrder()));
            modelCoilInformation.setCoilType(coilDb.getCoilType());
            modelCoilInformation.setCurrentLocation(coilDb.getLocation());
            modelCoilInformation.setMetalOwner(coilDb.getMetalOwner());
            modelCoilInformation.setMillCoilNumber(coilDb.getMillCoilNumber());
            modelCoilInformation.setStatusDescription(coilDb.getStatus());
            modelCoilInformation.setBackgroundStatus(coilDb.getBackgroundColor());
            modelCoilInformation.setForegroundStatus(coilDb.getForegroundColor());
            return modelCoilInformation;
        } else {
            return  null;
        }
    }

    public CoilInformation  getLastCoilAdded(){
        CoilInformation modelCoilInformation = new CoilInformation();
        tableCoilInformation coilDb = databaseDao.getLastCoilAdded();
        if(coilDb!=null) {
            modelCoilInformation.setCoilId((coilDb.getCoilInformationId()));
            modelCoilInformation.setCoilName(coilDb.getName());
            modelCoilInformation.setAssignedOrder((coilDb.getAssignedOrder()));
            modelCoilInformation.setCoilType(coilDb.getCoilType());
            modelCoilInformation.setCurrentLocation(coilDb.getLocation());
            modelCoilInformation.setMetalOwner(coilDb.getMetalOwner());
            modelCoilInformation.setMillCoilNumber(coilDb.getMillCoilNumber());
            modelCoilInformation.setStatusDescription(coilDb.getStatus());
            modelCoilInformation.setBackgroundStatus(coilDb.getBackgroundColor());
            modelCoilInformation.setForegroundStatus(coilDb.getForegroundColor());
            return modelCoilInformation;
        } else {
            return  null;
        }
    }

    public void deleteCoilInformation(CoilInformation coil){
        tableCoilInformation coildb = new tableCoilInformation();
        coildb.setCoilInformationId(coil.getCoilId());
        databaseDao.deleteCoil(coildb.getCoilInformationId());
    }

    public DrumInformation  getDrumByBarcode(String  name){
        DrumInformation modelDrumInformation = new DrumInformation();
        tableDrumInformation drum = databaseDao.getDrumByName(name);
        if(drum!=null){
            modelDrumInformation.setDrumId(drum.getDrumInformationId());
            modelDrumInformation.setDrumName(drum.getName());
            modelDrumInformation.setStatusDescription((drum.getStatus()));
            modelDrumInformation.setForegroundColor(drum.getForegroundColor());
            modelDrumInformation.setCurrentLocation(drum.getLocation());
            modelDrumInformation.setBackgroundColor(drum.getBackgroundColor());
            modelDrumInformation.setGallons(drum.getGallons());
            return modelDrumInformation;
        }
        else{
            return  null;
        }


    }

    public void addDrum(DrumInformation item){
        List<tableDrumInformation> listDrums=databaseDao.getDrumsInfoList();
        if(listDrums.size()>=10){
            databaseDao.deleteDrum(listDrums.get(0).getDrumInformationId());
            databaseDao.deleteHistoryItembyType(listDrums.get(0).getDrumInformationId(), 2);
        }

        tableDrumInformation drum = new tableDrumInformation();
        drum.setName(item.getDrumName());
        drum.setBackgroundColor(item.getBackgroundColor());
        drum.setForegroundColor(item.getForegroundColor());
        drum.setLocation(item.getCurrentLocation());
        drum.setStatus(item.getStatusDescription());
        drum.setGallons(item.getGallons());
        databaseDao.addDrumEntry(drum);
    }

    public void updateDrumInformation(DrumInformation item){
        databaseDao.updateDrum(item.getDrumName(),item.getStatusDescription(),item.getCurrentLocation(), item.getBackgroundColor(),
                item.getForegroundColor(), item.getGallons());

        databaseDao.updateHistory(item.getDrumName(), item.getCurrentLocation());
    }

    public DrumInformation  getLastDrumAdded(){
        DrumInformation modelDrumInformation = new DrumInformation();
        tableDrumInformation drumDb = databaseDao.getLastDrumAdded();
        if(drumDb!=null) {
            modelDrumInformation.setDrumId((drumDb.getDrumInformationId()));
            modelDrumInformation.setBackgroundColor((drumDb.getBackgroundColor()));
            modelDrumInformation.setCurrentLocation((drumDb.getLocation()));
            modelDrumInformation.setDrumName((drumDb.getName()));
            modelDrumInformation.setForegroundColor((drumDb.getForegroundColor()));
            modelDrumInformation.setStatusDescription((drumDb.getStatus()));
            modelDrumInformation.setGallons((drumDb.getGallons()));
            return modelDrumInformation;
        } else {
            return null;
        }
    }

    public void saveLastLocationInfo(tableLastLocationSaved item){
        tableLastLocationSaved getLastItem;
        getLastItem = databaseDao.getLastLocationByType(item.getItemType());
        if(getLastItem!=null) {
            databaseDao.updateLastLocation(item.getRow(), item.getColumn(), item.getLayer(), item.getItemType());
        } else{
            databaseDao.addLocationSavedEntry(item);
        }
    }

    public tableLastLocationSaved getLastLocationByType(int typeId){
        tableLastLocationSaved lastLocation;
        lastLocation= databaseDao.getLastLocationByType(typeId);
        return lastLocation;
    }

}