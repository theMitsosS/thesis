package gr.unipi.solarparkmanager.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "tSolarPanel")
public class SolarPanelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "solarFacilityId")
    private Integer solarFacilityId;

    @Column(name = "type")
    private String type;

    @Column(name = "serialNumber")
    private String serialNumber;

    @Column(name = "size")
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
