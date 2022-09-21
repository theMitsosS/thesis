package gr.unipi.solarparkmanager.model.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AggregatedPanelPerformance {

    @Id
    private Integer panelId;
    private Double kwh;

    public Integer getPanelId() {
        return panelId;
    }

    public void setPanelId(Integer panelId) {
        this.panelId = panelId;
    }

    public Double getKwh() {
        return kwh;
    }

    public void setKwh(Double averageKwh) {
        this.kwh = averageKwh;
    }
}
