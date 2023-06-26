package com.janus.rodeo.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RodeoSingleton {
    private static RodeoSingleton instance = new RodeoSingleton();
    static Context context;
    private RodeoSingleton(){}

    public static RodeoSingleton getInstance(Context ctx){
        context = ctx.getApplicationContext();
        return instance;
    }

    private int userid;
    private int durationToken;
    private String username;
    private String name;
    private String email;
    private String token;
    private Date loginDate;
    private Date expireDate;
    private String idPermiso;
    private String Permiso;
    private List<String> FragmentListOpen = new ArrayList<>();
    private Bitmap imageCaptured;

    public Bitmap getImageCaptured() {
        return imageCaptured;
    }

    public void setImageCaptured(Bitmap imageCaptured) {
        this.imageCaptured = imageCaptured;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDurationToken() {
        return durationToken;
    }

    public void setDurationToken(int durationToken) {
        this.durationToken = durationToken;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(String idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getPermiso() {
        return Permiso;
    }

    public void setPermiso(String permiso) {
        Permiso = permiso;
    }

    public List<String> getFragmentListOpen() {
        return FragmentListOpen;
    }

    public void setFragmentListOpen(List<String> fragmentListOpen) {
        FragmentListOpen = fragmentListOpen;
    }

    public void addFragmentToList(String fragmentName){
        FragmentListOpen.add(fragmentName);
    }

    public String getLastFragment(){
        if(FragmentListOpen.isEmpty()){
            return "";
        }else{
            return  FragmentListOpen.get(FragmentListOpen.size() - 1);
        }
    }

    public void deleteLastFragment(){
        if(!FragmentListOpen.isEmpty()){
            FragmentListOpen.remove(FragmentListOpen.size() - 1);
        }
    }

    public boolean isTokenOk(){
        boolean response = false;
        return response;
    }

}