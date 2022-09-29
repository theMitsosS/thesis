package gr.unipi.solarparkmanager.service;

import gr.unipi.solarparkmanager.exception.SolarFacilityNotFoundException;
import gr.unipi.solarparkmanager.exception.SolarParkManagerException;
import gr.unipi.solarparkmanager.model.dto.*;
import gr.unipi.solarparkmanager.model.entity.SolarFacilityEntity;
import gr.unipi.solarparkmanager.model.entity.SolarPanelEntity;
import gr.unipi.solarparkmanager.repository.AggregatedPanelPerformanceRepository;
import gr.unipi.solarparkmanager.repository.SolarFacilityRepository;
import gr.unipi.solarparkmanager.repository.SolarPanelRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static gr.unipi.solarparkmanager.util.FacilityConverter.convertSolarFacilityToEntity;
import static gr.unipi.solarparkmanager.util.FacilityConverter.convertToSolarFacilityJson;
import static gr.unipi.solarparkmanager.util.PanelConverter.convertToSolarPanelJson;

@Service
public class SolarFacilityService {

    @Autowired
    private SolarFacilityRepository solarFacilityRepository;

    @Autowired
    private SolarPanelRepository solarPanelRepository;

    @Autowired
    private AggregatedPanelPerformanceRepository aggregatedPanelPerformanceRepository;

    public SolarFacilityJson create(SolarFacilityJson solarFacilityJson) {
        SolarFacilityEntity newSolarFacility = convertSolarFacilityToEntity(solarFacilityJson);
        SolarFacilityEntity createdSolarFacilityEntity = solarFacilityRepository.save(newSolarFacility); //solar facility id will be auto generated and thus not provided
        return convertToSolarFacilityJson(createdSolarFacilityEntity);
    }

    public List<SolarFacilityJson> getAll() {
        List<SolarFacilityEntity> solarFacilityEntities = solarFacilityRepository.findAll();
        List<SolarFacilityJson> results = new ArrayList<>();
        for (SolarFacilityEntity item : solarFacilityEntities) {
            results.add(convertToSolarFacilityJson(item));
        }
        return results;
    }

    private SolarFacilityEntity validateFacilityWithGivenIdExists(Integer solarFacilityId) throws SolarFacilityNotFoundException {
        Optional<SolarFacilityEntity> solarFacilityEntityOptional = solarFacilityRepository.findById(solarFacilityId);
        if (!solarFacilityEntityOptional.isPresent()) {
            throw new SolarFacilityNotFoundException(solarFacilityId);
        }
        return solarFacilityEntityOptional.get();
    }

    public SolarFacilityDetailsJson getSolarFacility(Integer solarFacilityId) throws InvocationTargetException, IllegalAccessException, SolarFacilityNotFoundException {
        SolarFacilityEntity solarFacilityEntity = validateFacilityWithGivenIdExists(solarFacilityId);

        List<SolarPanelEntity> solarPanelsInFacility = solarPanelRepository.getSolarPanelsInFacility(solarFacilityId);
        SolarFacilityJson solarFacilityJson = convertToSolarFacilityJson(solarFacilityEntity);
        SolarFacilityDetailsJson solarFacilityDetailsJson = new SolarFacilityDetailsJson();
        BeanUtils.copyProperties(solarFacilityDetailsJson, solarFacilityJson);
        List<SolarPanelJson> solarPanelsJson = new ArrayList<>();
        for (SolarPanelEntity item : solarPanelsInFacility) {
            solarPanelsJson.add(convertToSolarPanelJson(item));
        }
        solarFacilityDetailsJson.setSolarPanels(solarPanelsJson);
        return solarFacilityDetailsJson;
    }

    @Transactional
    public void deleteFacility(Integer facilityId) {
        solarPanelRepository.deleteBySolarFacilityId(facilityId);
        solarFacilityRepository.deleteById(facilityId);
    }

    public SolarFacilityJson updateSolarFacility(SolarFacilityJson solarFacilityJson) throws SolarParkManagerException, SolarFacilityNotFoundException {
        if (solarFacilityJson.getId() == null) {
            throw new SolarParkManagerException("Facility ID is mandatory");
        }
        validateFacilityWithGivenIdExists(solarFacilityJson.getId());
        Integer totalPanelsInTheFacility = solarPanelRepository.countTotalExistingPanelsInAFacility(solarFacilityJson.getId());
        if (totalPanelsInTheFacility > solarFacilityJson.getTotalSolarPanelCapacity()) {
            throw new SolarParkManagerException("non valid update, too small capacity! Total capacity must be greater or equal to:" + totalPanelsInTheFacility);
        }
        SolarFacilityEntity facilityToUpdate = convertSolarFacilityToEntity(solarFacilityJson);
        facilityToUpdate.setId(solarFacilityJson.getId());
        SolarFacilityEntity updatedSolarFacilityEntity = solarFacilityRepository.save(facilityToUpdate);
        return convertToSolarFacilityJson(updatedSolarFacilityEntity);
    }

    public FacilityPerformanceJson getFacilityPerformance(Integer facilityId, LocalDate date) throws SolarFacilityNotFoundException {

        validateFacilityWithGivenIdExists(facilityId);
        AggregatedFacilityPerformance aggregatedFacilityPerformance = aggregatedPanelPerformanceRepository.getFacilityPerformance(facilityId, date.toString());

        FacilityPerformanceJson facilityPerformanceJson = new FacilityPerformanceJson();
        facilityPerformanceJson.setFacilityId(facilityId);
        facilityPerformanceJson.setTotalFacilityKwh(aggregatedFacilityPerformance.getTotalFacilityPerformance());
        facilityPerformanceJson.setTotalInstalledPanels(aggregatedFacilityPerformance.getTotalPanels());
        facilityPerformanceJson.setAveragePanelKwh(aggregatedFacilityPerformance.getAveragePanelPerformance());

        return facilityPerformanceJson;

    }
}
