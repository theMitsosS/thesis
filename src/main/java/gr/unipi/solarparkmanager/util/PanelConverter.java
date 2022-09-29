package gr.unipi.solarparkmanager.util;

import gr.unipi.solarparkmanager.model.dto.PanelPerformanceJson;
import gr.unipi.solarparkmanager.model.dto.SolarPanelJson;
import gr.unipi.solarparkmanager.model.entity.PanelPerformanceEntity;
import gr.unipi.solarparkmanager.model.entity.SolarPanelEntity;

public class PanelConverter {

    public static SolarPanelJson convertToSolarPanelJson(SolarPanelEntity createdSolarPanelEntity) {
        SolarPanelJson createdSolarPanelJson = new SolarPanelJson();
        createdSolarPanelJson.setId(createdSolarPanelEntity.getId());
        createdSolarPanelJson.setSolarFacilityId(createdSolarPanelEntity.getSolarFacilityId());
        createdSolarPanelJson.setSize(createdSolarPanelEntity.getSize());
        createdSolarPanelJson.setSerialNumber(createdSolarPanelEntity.getSerialNumber());
        createdSolarPanelJson.setType(createdSolarPanelEntity.getType());
        return createdSolarPanelJson;
    }

    public static PanelPerformanceEntity convertPanelPerformanceEntity(PanelPerformanceJson panelPerformanceJson) {
        PanelPerformanceEntity panelPerformanceEntity = new PanelPerformanceEntity();
        panelPerformanceEntity.setPanelId(panelPerformanceJson.getPanelId());
        panelPerformanceEntity.setDate(panelPerformanceJson.getDate());
        panelPerformanceEntity.setKwh(panelPerformanceJson.getKwh());
        return panelPerformanceEntity;
    }


    public static SolarPanelEntity convertSolarPanelEntity(SolarPanelJson solarPanelJson) {
        SolarPanelEntity newSolarPanel = new SolarPanelEntity();
        newSolarPanel.setSolarFacilityId(solarPanelJson.getSolarFacilityId());
        newSolarPanel.setSerialNumber(solarPanelJson.getSerialNumber());
        newSolarPanel.setSize(solarPanelJson.getSize());
        newSolarPanel.setType(solarPanelJson.getType());
        return newSolarPanel;
    }
}
