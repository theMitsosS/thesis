package gr.unipi.solarparkmanager.model.dto;

public class FacilityPerformanceJson {

    private Integer facilityId;

    private Integer totalInstalledPanels;

    private Double totalFacilityKwh;

    private Double averagePanelKwh;

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public Double getTotalFacilityKwh() {
        return totalFacilityKwh;
    }

    public void setTotalFacilityKwh(Double totalFacilityKwh) {
        this.totalFacilityKwh = totalFacilityKwh;
    }

    public Double getAveragePanelKwh() {
        return averagePanelKwh;
    }

    public void setAveragePanelKwh(Double averagePanelKwh) {
        this.averagePanelKwh = averagePanelKwh;
    }

    public Integer getTotalInstalledPanels() {
        return totalInstalledPanels;
    }

    public void setTotalInstalledPanels(Integer totalInstalledPanels) {
        this.totalInstalledPanels = totalInstalledPanels;
    }
}
