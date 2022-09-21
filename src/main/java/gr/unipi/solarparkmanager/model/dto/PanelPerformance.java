package gr.unipi.solarparkmanager.model.dto;

public class PanelPerformance {
    private Integer panelId;
    private Double averageKwh;

    public Integer getPanelId() {
        return panelId;
    }

    public void setPanelId(Integer panelId) {
        this.panelId = panelId;
    }

    public Double getAverageKwh() {
        return averageKwh;
    }

    public void setAverageKwh(Double averageKwh) {
        this.averageKwh = averageKwh;
    }
}
