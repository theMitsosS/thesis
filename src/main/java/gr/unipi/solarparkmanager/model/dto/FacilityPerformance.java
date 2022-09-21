package gr.unipi.solarparkmanager.model.dto;

import java.util.List;

public class FacilityPerformance {

    private Integer facilityId;
    private Double totalKwh;
    private List<PanelPerformance> panels;

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public Double getTotalKwh() {
        return totalKwh;
    }

    public void setTotalKwh(Double totalKwh) {
        this.totalKwh = totalKwh;
    }

    public List<PanelPerformance> getPanels() {
        return panels;
    }

    public void setPanels(List<PanelPerformance> panels) {
        this.panels = panels;
    }
}
