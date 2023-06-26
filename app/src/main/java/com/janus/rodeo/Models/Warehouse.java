package com.janus.rodeo.Models;

public class Warehouse {
    private int WarehouseId;
    private String Code;
    private String Description;
    private int StructureId;

    public int getWarehouseId() {
        return WarehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        WarehouseId = warehouseId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getStructureId() {
        return StructureId;
    }

    public void setStructureId(int structureId) {
        StructureId = structureId;
    }

}