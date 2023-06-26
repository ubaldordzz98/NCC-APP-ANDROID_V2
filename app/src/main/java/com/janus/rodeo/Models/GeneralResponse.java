package com.janus.rodeo.Models;

public class GeneralResponse {

    private String code;
    private String message;
    private Object general_object;
    private int has_errors;
    private int in_release;
    private CoilShRelease Coil;

    public CoilShRelease getCoil() {
        return Coil;
    }

    public void setCoil(CoilShRelease coil) {
        Coil = coil;
    }

    public int getIn_release() {
        return in_release;
    }

    public void setIn_release(int in_release) {
        this.in_release = in_release;
    }

    public Object getGeneral_object() {
        return general_object;
    }

    public void setGeneral_object(Object general_object) {
        this.general_object = general_object;
    }

    public int getHas_errors() {
        return has_errors;
    }

    public void setHas_errors(int has_errors) {
        this.has_errors = has_errors;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}