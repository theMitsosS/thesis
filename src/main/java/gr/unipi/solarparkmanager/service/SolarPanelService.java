package gr.unipi.solarparkmanager.service;

import gr.unipi.solarparkmanager.exception.SolarFacilityNotFoundException;
import gr.unipi.solarparkmanager.exception.SolarPanelNotFoundException;
import gr.unipi.solarparkmanager.exception.SolarParkManagerException;
import gr.unipi.solarparkmanager.model.dto.PanelPerformanceJson;
import gr.unipi.solarparkmanager.model.dto.SolarPanelJson;
import gr.unipi.solarparkmanager.model.entity.PanelPerformanceEntity;
import gr.unipi.solarparkmanager.model.entity.SolarFacilityEntity;
import gr.unipi.solarparkmanager.model.entity.SolarPanelEntity;
import gr.unipi.solarparkmanager.repository.PanelPerformanceRepository;
import gr.unipi.solarparkmanager.repository.SolarFacilityRepository;
import gr.unipi.solarparkmanager.repository.SolarPanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static gr.unipi.solarparkmanager.util.PanelConverter.*;

@Service
public class SolarPanelService {

    @Autowired
    private SolarFacilityRepository solarFacilityRepository;
    @Autowired
    private SolarPanelRepository solarPanelRepository;

    @Autowired
    private PanelPerformanceRepository panelPerformanceRepository;

    public SolarPanelJson create(SolarPanelJson solarPanelJson) throws SolarParkManagerException, SolarFacilityNotFoundException {

        SolarFacilityEntity entity = validateFacilityWithGivenIdExists(solarPanelJson.getSolarFacilityId());
        validateCapacityIsNotReached(solarPanelJson.getSolarFacilityId(), entity.getCapacity());

        SolarPanelEntity newSolarPanel = convertSolarPanelEntity(solarPanelJson);
        SolarPanelEntity createdSolarPanelEntity = solarPanelRepository.save(newSolarPanel);

        return convertToSolarPanelJson(createdSolarPanelEntity);
    }

    public void deletePanel(Integer panelId) {
        solarPanelRepository.deleteById(panelId);
    }

    public SolarPanelJson updateSolarPanel(SolarPanelJson solarPanelJson) throws SolarParkManagerException, SolarFacilityNotFoundException, SolarPanelNotFoundException {
        if (solarPanelJson.getId() == null) {
            throw new SolarParkManagerException("solar panel ID is mandatory");
        }

        SolarPanelEntity solarPanelEntity = validateSolarPanelExists(solarPanelJson.getId());

        //If user updated solar facility ID, do basic checks for the new facility id (if it exists & if max capacity is not reached)
        if (solarPanelEntity.getSolarFacilityId().intValue() != solarPanelJson.getSolarFacilityId()) {
            validateFacilityWithGivenIdExists(solarPanelJson.getSolarFacilityId());
            validateCapacityIsNotReached(solarPanelJson.getSolarFacilityId(), solarPanelEntity.getSolarFacilityId());
        }
        SolarPanelEntity updateSolarPanelEntity = convertSolarPanelEntity(solarPanelJson);
        updateSolarPanelEntity.setId(solarPanelJson.getId());
        SolarPanelEntity updatedSolarPanelEntity = solarPanelRepository.save(updateSolarPanelEntity);

        return convertToSolarPanelJson(updatedSolarPanelEntity);
    }

    public List<SolarPanelJson> getAll() {
        List<SolarPanelEntity> solarPanelEntities = solarPanelRepository.findAll();
        List<SolarPanelJson> results = new ArrayList<>();
        for (SolarPanelEntity item : solarPanelEntities) {
            results.add(convertToSolarPanelJson(item));
        }
        return results;
    }

    private void validateCapacityIsNotReached(Integer solarFacilityId, Integer solarFacilityCapacity) throws SolarParkManagerException {
        Integer totalExistingPanels = solarPanelRepository.countTotalExistingPanelsInAFacility(solarFacilityId);
        if (totalExistingPanels >= solarFacilityCapacity) {
            throw new SolarParkManagerException(
                    "The maximum capacity for facility with ID:" + solarFacilityId + " has been reached!");
        }
    }

    private SolarFacilityEntity validateFacilityWithGivenIdExists(Integer solarFacilityId) throws SolarFacilityNotFoundException {
        Optional<SolarFacilityEntity> solarFacilityEntityOptional = solarFacilityRepository.findById(solarFacilityId);
        if (!solarFacilityEntityOptional.isPresent()) {
            throw new SolarFacilityNotFoundException(solarFacilityId);
        }
        return solarFacilityEntityOptional.get();
    }

    public SolarPanelJson getSolarPanelById(Integer panelId) throws SolarPanelNotFoundException {
        SolarPanelEntity solarPanelEntity = validateSolarPanelExists(panelId);
        return convertToSolarPanelJson(solarPanelEntity);
    }

    public void insertSingleDayPerformanceData(PanelPerformanceJson panelPerformanceJson) throws SolarPanelNotFoundException {
        validateSolarPanelExists(panelPerformanceJson.getPanelId());

        PanelPerformanceEntity panelPerformanceEntity = convertPanelPerformanceEntity(panelPerformanceJson);
        panelPerformanceRepository.save(panelPerformanceEntity);
    }

    private SolarPanelEntity validateSolarPanelExists(Integer solarPanelId) throws SolarPanelNotFoundException {
        Optional<SolarPanelEntity> solarPanelEntityOptional = solarPanelRepository.findById(solarPanelId);
        if (!solarPanelEntityOptional.isPresent()) {
            throw new SolarPanelNotFoundException(solarPanelId);
        }
        return solarPanelEntityOptional.get();
    }


}
