package gr.unipi.solarparkmanager.model.dto;

import javax.validation.constraints.NotNull;

public class SolarPanelJson {
    private Integer id;

    @NotNull(message = "Solar facility ID is required")
    private Integer solarFacilityId;

    @NotNull(message = "Solar panel type is required")
    private String type;

    @NotNull(message = "Solar panel serial number is required")
    private String serialNumber;

    @NotNull(message = "Solar panel size is required")
    private Integer size;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSolarFacilityId() {
        return solarFacilityId;
    }

    public void setSolarFacilityId(Integer solarFacilityId) {
        this.solarFacilityId = solarFacilityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
