package com.janus.rodeo.Models;

public class Activity {
    private int idActividad;
    private int idzona;
    private String zonanombre;
    private String descripcion;
    private String comentarios;
    private String fechaprogramada;
    private String fecharealizada;
    private int idusuarioalta;
    private String usuarioaltanombre;
    private int idusuarioasignado;
    private String usuarioasignadonombre;
    private int idusuariorealizado;
    private String usuariorealizadonombre;
    private int idTipoActividad;
    private String tipoActividad;

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getIdzona() {
        return idzona;
    }

    public void setIdzona(int idzona) {
        this.idzona = idzona;
    }

    public String getZonanombre() {
        return zonanombre;
    }

    public void setZonanombre(String zonanombre) {
        this.zonanombre = zonanombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getFechaprogramada() {
        return fechaprogramada;
    }

    public void setFechaprogramada(String fechaprogramada) {
        this.fechaprogramada = fechaprogramada;
    }

    public String getFecharealizada() {
        return fecharealizada;
    }

    public void setFecharealizada(String fecharealizada) {
        this.fecharealizada = fecharealizada;
    }

    public int getIdusuarioalta() {
        return idusuarioalta;
    }

    public void setIdusuarioalta(int idusuarioalta) {
        this.idusuarioalta = idusuarioalta;
    }

    public String getUsuarioaltanombre() {
        return usuarioaltanombre;
    }

    public void setUsuarioaltanombre(String usuarioaltanombre) {
        this.usuarioaltanombre = usuarioaltanombre;
    }

    public int getIdusuarioasignado() {
        return idusuarioasignado;
    }

    public void setIdusuarioasignado(int idusuarioasignado) {
        this.idusuarioasignado = idusuarioasignado;
    }

    public String getUsuarioasignadonombre() {
        return usuarioasignadonombre;
    }

    public void setUsuarioasignadonombre(String usuarioasignadonombre) {
        this.usuarioasignadonombre = usuarioasignadonombre;
    }

    public int getIdusuariorealizado() {
        return idusuariorealizado;
    }

    public void setIdusuariorealizado(int idusuariorealizado) {
        this.idusuariorealizado = idusuariorealizado;
    }

    public String getUsuariorealizadonombre() {
        return usuariorealizadonombre;
    }

    public void setUsuariorealizadonombre(String usuariorealizadonombre) {
        this.usuariorealizadonombre = usuariorealizadonombre;
    }

    public int getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(int idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "idActividad=" + idActividad +
                ", idzona=" + idzona +
                ", zonanombre='" + zonanombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", fechaprogramada='" + fechaprogramada + '\'' +
                ", fecharealizada='" + fecharealizada + '\'' +
                ", idusuarioalta=" + idusuarioalta +
                ", usuarioaltanombre='" + usuarioaltanombre + '\'' +
                ", idusuarioasignado=" + idusuarioasignado +
                ", usuarioasignadonombre='" + usuarioasignadonombre + '\'' +
                ", idusuariorealizado=" + idusuariorealizado +
                ", usuariorealizadonombre='" + usuariorealizadonombre + '\'' +
                ", idTipoActividad=" + idTipoActividad +
                ", tipoActividad='" + tipoActividad + '\'' +
                '}';
    }
}