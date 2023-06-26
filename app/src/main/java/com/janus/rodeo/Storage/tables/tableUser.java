package com.janus.rodeo.Storage.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName ="User")
public class tableUser {
    @PrimaryKey
    @NonNull
    private String usuarioID;

    @ColumnInfo(name = "Username")
    private String username;

    @ColumnInfo(name = "Password")
    private String pasword;

    @ColumnInfo(name = "Token")
    private String token;

    public tableUser() {
        usuarioID = UUID.randomUUID().toString();
    }

    @NonNull
    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(@NonNull String usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
