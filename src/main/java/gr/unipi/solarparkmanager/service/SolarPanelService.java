package gr.unipi.solarparkmanager.service;

import java.util.Optional;

import static gr.unipi.solarparkmanager.util.PanelConverter.convertSolarPanel;

import gr.unipi.solarparkmanager.model.dto.PanelInputData;
import gr.unipi.solarparkmanager.model.dto.SolarPanel;
import gr.unipi.solarparkmanager.model.entity.PanelPerformanceEntity;
import gr.unipi.solarparkmanager.model.entity.SolarFacilityEntity;
import gr.unipi.solarparkmanager.model.entity.SolarPanelEntity;
import gr.unipi.solarparkmanager.model.dto.exception.SolarParkManagerException;
import gr.unipi.solarparkmanager.repository.PanelPerformanceRepository;
import gr.unipi.solarparkmanager.repository.SolarFacilityRepository;
import gr.unipi.solarparkmanager.repository.SolarPanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolarPanelService {

    @Autowired
    private SolarFacilityRepository solarFacilityRepository;
    @Autowired
    private SolarPanelRepository solarPanelRepository;

    @Autowired
    private PanelPerformanceRepository panelPerformanceRepository;

    public SolarPanel create(SolarPanel solarPanel) throws SolarParkManagerException {
        if (solarPanel.getSolarFacilityId() == null) {
            throw new SolarParkManagerException("solar facility id is null!");
        }
        Optional<SolarFacilityEntity> solarFacilityEntityOptional =
                solarFacilityRepository.findById(solarPanel.getSolarFacilityId());
        if (!solarFacilityEntityOptional.isPresent()) {
            throw new SolarParkManagerException("solar facility does not exist!");
        }
        Integer panelsInFacility =
                solarPanelRepository.countFacilityPanels(solarPanel.getSolarFacilityId());
        if (panelsInFacility >= solarFacilityEntityOptional.get().getCapacity()) {
            throw new SolarParkManagerException(
                    "max Capacity for facility " + solarPanel.getSolarFacilityId() + " has been reached");
        }
        SolarPanelEntity newSolarPanel = convertSolarPanelEntity(solarPanel);

        SolarPanelEntity createdSolarPanelEntity = solarPanelRepository.save(newSolarPanel);

        return convertSolarPanel(createdSolarPanelEntity);
    }

    public void deletePanel(Integer panelId) {
        solarPanelRepository.deleteById(panelId);
    }

    public SolarPanel updateSolarPanel(SolarPanel solarPanel) throws SolarParkManagerException {
        if (solarPanel.getId() == null) {
            throw new SolarParkManagerException("solar panel is mandatory");
        }
        Optional<SolarPanelEntity> solarPanelEntityOptional = solarPanelRepository.findById(solarPanel.getId());
        if (!solarPanelEntityOptional.isPresent()) {
            throw new SolarParkManagerException("solar panel id " + solarPanel.getId() + " not found!");
        }
//check if we can update facility id,in case it is required to get updated
        if (solarPanelEntityOptional.get().getSolarFacilityId().intValue() !=
                solarPanel.getSolarFacilityId()) {
            Optional<SolarFacilityEntity> solarFacilityEntityOptional = solarFacilityRepository.findById(solarPanel.getSolarFacilityId());
            if (!solarFacilityEntityOptional.isPresent()) {
                throw new SolarParkManagerException("solar facility does not exist!");
            }
            Integer panelsInFacility = solarPanelRepository.countFacilityPanels(solarPanel.getSolarFacilityId());
            if (panelsInFacility >= solarFacilityEntityOptional.get().getCapacity()) {
                throw new SolarParkManagerException(
                        "max Capacity for facility " + solarPanel.getSolarFacilityId() + " has been reached");
            }
        }
        SolarPanelEntity updateSolarPanelEntity = convertSolarPanelEntity(solarPanel);
        updateSolarPanelEntity.setId(solarPanel.getId());
        SolarPanelEntity updatedSolarPanelEntity = solarPanelRepository.save(updateSolarPanelEntity);

        return convertSolarPanel(updatedSolarPanelEntity);
    }

    public SolarPanel getSolarPanelById(Integer panelId) throws SolarParkManagerException {
        Optional<SolarPanelEntity> solarPanelEntityOptional = solarPanelRepository.findById(panelId);
        if (!solarPanelEntityOptional.isPresent()) {
            throw new SolarParkManagerException("solar panel does not exist!");
        }
        return convertSolarPanel(solarPanelEntityOptional.get());
    }

    public void insertPerformance(PanelInputData panelInputData) throws SolarParkManagerException {
        if (panelInputData.getId() == null) {
            throw new SolarParkManagerException("id is mandatory");
        }
        Optional<SolarPanelEntity> solarPanelEntityOptional = solarPanelRepository.findById(
                panelInputData.getId());
        if (!solarPanelEntityOptional.isPresent()) {
            throw new SolarParkManagerException("solar panel id " + panelInputData.getId() + " not found!");
        }
        PanelPerformanceEntity panelPerformanceEntity = convertPanelPerformanceEntity(panelInputData);
        panelPerformanceRepository.save(panelPerformanceEntity);
    }

    private PanelPerformanceEntity convertPanelPerformanceEntity(PanelInputData panelInputData) {
        PanelPerformanceEntity panelPerformanceEntity = new PanelPerformanceEntity();
        panelPerformanceEntity.setPanelId(panelInputData.getId());
        panelPerformanceEntity.setDate(panelInputData.getDate());
        panelPerformanceEntity.setKwh(panelInputData.getKwh());
        return panelPerformanceEntity;
    }


    private SolarPanelEntity convertSolarPanelEntity(SolarPanel solarPanel) {
        SolarPanelEntity newSolarPanel = new SolarPanelEntity();
        newSolarPanel.setSolarFacilityId(solarPanel.getSolarFacilityId());
        newSolarPanel.setSerialNumber(solarPanel.getSerialNumber());
        newSolarPanel.setSize(solarPanel.getSize());
        newSolarPanel.setType(solarPanel.getType());
        return newSolarPanel;
    }
}
