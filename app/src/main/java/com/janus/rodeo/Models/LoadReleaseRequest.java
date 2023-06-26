package com.janus.rodeo.Models;

import java.util.List;

public class LoadReleaseRequest {

    private String release_num;
    public List<CoilShRelease> list_coils;

    public String getRelease_num() {
        return release_num;
    }

    public void setRelease_num(String release_num) {
        this.release_num = release_num;
    }

    public List<CoilShRelease> getList_coils() {
        return list_coils;
    }

    public void setList_coils(List<CoilShRelease> list_coils) {
        this.list_coils = list_coils;
    }

}