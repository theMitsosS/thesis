package gr.unipi.solarparkmanager.util;

import gr.unipi.solarparkmanager.model.dto.SolarPanel;
import gr.unipi.solarparkmanager.model.entity.SolarPanelEntity;

public class PanelConverter {

    public static SolarPanel convertSolarPanel(SolarPanelEntity createdSolarPanelEntity) {
        SolarPanel createdSolarPanel = new SolarPanel();
        createdSolarPanel.setId(createdSolarPanelEntity.getId());
        createdSolarPanel.setSolarFacilityId(createdSolarPanelEntity.getSolarFacilityId());
        createdSolarPanel.setSize(createdSolarPanelEntity.getSize());
        createdSolarPanel.setSerialNumber(createdSolarPanelEntity.getSerialNumber());
        createdSolarPanel.setType(createdSolarPanelEntity.getType());
        return createdSolarPanel;
    }
}
