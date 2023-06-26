package com.janus.rodeo.Models;

import java.util.Arrays;

public class InventoryPost {
    private Integer codigo_locacion;
    private String descripcion_locacion;
    private Integer usuario_asignado;
    private Integer usuario_alta;
    private  boolean status;
    private String descripcion;
    private Integer[][] Assets;
    private Integer id_actividad;

    public Integer getCodigo_locacion() {
        return codigo_locacion;
    }

    public void setCodigo_locacion(Integer codigo_locacion) {
        this.codigo_locacion = codigo_locacion;
    }

    public String getDescripcion_locacion() {
        return descripcion_locacion;
    }

    public void setDescripcion_locacion(String descripcion_locacion) {
        this.descripcion_locacion = descripcion_locacion;
    }

    public Integer getUsuario_asignado() {
        return usuario_asignado;
    }

    public void setUsuario_asignado(Integer usuario_asignado) {
        this.usuario_asignado = usuario_asignado;
    }

    public Integer getUsuario_alta() {
        return usuario_alta;
    }

    public void setUsuario_alta(Integer usuario_alta) {
        this.usuario_alta = usuario_alta;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer[][] getAssets() {
        return Assets;
    }

    public void setAssets(Integer[][] assets) {
        Assets = assets;
    }

    public Integer getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(Integer id_actividad) {
        this.id_actividad = id_actividad;
    }

    @Override
    public String toString() {
        return "InventoryPost{" +
                "codigo_locacion=" + codigo_locacion +
                ", usuario_alta=" + usuario_alta +
                ", status=" + status +
                ", descripcion='" + descripcion + '\'' +
                ", Assets=" + Arrays.toString(Assets) +
                '}';
    }

}