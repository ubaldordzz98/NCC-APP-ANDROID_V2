package com.janus.rodeo.Models;

public class ResponsePost {
    private String status_code;
    private String action;
    private String message;
    private String quantity;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ResponsePost{" +
                "status_code='" + status_code + '\'' +
                ", action='" + action + '\'' +
                ", message='" + message + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

}