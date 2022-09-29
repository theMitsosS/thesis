package gr.unipi.solarparkmanager.model.dto;

import java.util.List;

public class SolarFacilityDetailsJson extends SolarFacilityJson {
    private List<SolarPanelJson> solarPanels;

    public List<SolarPanelJson> getSolarPanels() {
        return solarPanels;
    }

    public void setSolarPanels(List<SolarPanelJson> solarPanels) {
        this.solarPanels = solarPanels;
    }
}
