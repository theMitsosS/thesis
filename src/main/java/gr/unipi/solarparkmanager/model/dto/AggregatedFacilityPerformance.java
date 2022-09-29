package gr.unipi.solarparkmanager.model.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AggregatedFacilityPerformance {

    @Id
    private Integer totalPanels;

    private Double averagePanelPerformance;

    private Double totalFacilityPerformance;

    public Integer getTotalPanels() {
        return totalPanels;
    }

    public void setTotalPanels(Integer totalPanels) {
        this.totalPanels = totalPanels;
    }

    public Double getAveragePanelPerformance() {
        return averagePanelPerformance;
    }

    public void setAveragePanelPerformance(Double averageFacilityPerformance) {
        this.averagePanelPerformance = averageFacilityPerformance;
    }

    public Double getTotalFacilityPerformance() {
        return totalFacilityPerformance;
    }

    public void setTotalFacilityPerformance(Double totalFacilityPerformance) {
        this.totalFacilityPerformance = totalFacilityPerformance;
    }
}
