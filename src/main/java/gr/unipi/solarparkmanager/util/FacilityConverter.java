package gr.unipi.solarparkmanager.util;

import gr.unipi.solarparkmanager.model.dto.Coordinates;
import gr.unipi.solarparkmanager.model.dto.SolarFacilityJson;
import gr.unipi.solarparkmanager.model.entity.SolarFacilityEntity;

public class FacilityConverter {

    public static SolarFacilityJson convertToSolarFacilityJson(SolarFacilityEntity createdSolarFacilityEntity) {
        SolarFacilityJson createdSolarFacilityJson = new SolarFacilityJson();
        createdSolarFacilityJson.setAddress(createdSolarFacilityEntity.getAddress());
        createdSolarFacilityJson.setTotalSolarPanelCapacity(createdSolarFacilityEntity.getCapacity());
        createdSolarFacilityJson.setId(createdSolarFacilityEntity.getId());
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(createdSolarFacilityEntity.getLatitude());
        coordinates.setLongitude(createdSolarFacilityEntity.getLongitude());
        createdSolarFacilityJson.setCoordinates(coordinates);
        createdSolarFacilityJson.setName(createdSolarFacilityEntity.getName());
        return createdSolarFacilityJson;
    }

    public static SolarFacilityEntity convertSolarFacilityToEntity(SolarFacilityJson solarFacilityJson) {
        SolarFacilityEntity newSolarFacility = new SolarFacilityEntity();
        newSolarFacility.setAddress(solarFacilityJson.getAddress());
        newSolarFacility.setCapacity(solarFacilityJson.getTotalSolarPanelCapacity());
        newSolarFacility.setLatitude(solarFacilityJson.getCoordinates().getLatitude());
        newSolarFacility.setLongitude(solarFacilityJson.getCoordinates().getLongitude());
        newSolarFacility.setName(solarFacilityJson.getName());
        return newSolarFacility;
    }
}
