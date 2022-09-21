package gr.unipi.solarparkmanager.model.dto;

public class SolarPanel {

    private Integer id;
    private Integer solarFacilityId;
    private String type;
    private String serialNumber;
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
