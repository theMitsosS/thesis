package gr.unipi.solarparkmanager.model.dto;

import javax.validation.constraints.NotNull;

public class SolarFacilityJson {

    private Integer id;
    @NotNull(message = "Facility name is required")
    private String name;
    @NotNull(message = "Facility address is required")
    private String address;
    @NotNull(message = "Facility capacity is required")
    private Integer totalSolarPanelCapacity;
    @NotNull(message = "Facility coordinates is required")
    private Coordinates coordinates;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTotalSolarPanelCapacity() {
        return totalSolarPanelCapacity;
    }

    public void setTotalSolarPanelCapacity(Integer totalSolarPanelCapacity) {
        this.totalSolarPanelCapacity = totalSolarPanelCapacity;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
