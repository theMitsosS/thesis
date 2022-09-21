package gr.unipi.solarparkmanager.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static gr.unipi.solarparkmanager.util.PanelConverter.convertSolarPanel;

import gr.unipi.solarparkmanager.model.dto.AggregatedPanelPerformance;
import gr.unipi.solarparkmanager.model.dto.Coordinates;
import gr.unipi.solarparkmanager.model.dto.FacilityPerformance;
import gr.unipi.solarparkmanager.model.dto.PanelPerformance;
import gr.unipi.solarparkmanager.model.dto.SolarFacility;
import gr.unipi.solarparkmanager.model.dto.SolarPanel;
import gr.unipi.solarparkmanager.model.entity.SolarFacilityEntity;
import gr.unipi.solarparkmanager.model.entity.SolarPanelEntity;
import gr.unipi.solarparkmanager.model.dto.exception.SolarParkManagerException;
import gr.unipi.solarparkmanager.repository.AggregatedPanelPerformanceRepository;
import gr.unipi.solarparkmanager.repository.SolarFacilityRepository;
import gr.unipi.solarparkmanager.repository.SolarPanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolarFacilityService {

    @Autowired
    private SolarFacilityRepository solarFacilityRepository;

    @Autowired
    private SolarPanelRepository solarPanelRepository;

    @Autowired
    private AggregatedPanelPerformanceRepository aggregatedPanelPerformanceRepository;

    public SolarFacility create(SolarFacility solarFacility) {

        SolarFacilityEntity newSolarFacility =
                convertSolarFacilityEntity(solarFacility);
        SolarFacilityEntity createdSolarFacilityEntity = solarFacilityRepository.save(newSolarFacility);
        return convertSolarFacility(createdSolarFacilityEntity);
    }

    public List<SolarFacility> getAll() {
        List<SolarFacilityEntity> solarFacilityEntities = solarFacilityRepository.findAll();
        List<SolarFacility> results = new ArrayList<>();
        for (SolarFacilityEntity item : solarFacilityEntities) {
            results.add(convertSolarFacility(item));
        }
        return results;
    }

    public SolarFacility getSolarFacility(Integer solarFacilityId) throws SolarParkManagerException {
        Optional<SolarFacilityEntity> solarFacilityEntityOptional =
                solarFacilityRepository.findById(solarFacilityId);
        if (!solarFacilityEntityOptional.isPresent()) {
            throw new SolarParkManagerException("solar facility does not exist!");
        }
        List<SolarPanelEntity> solarPanels =
                solarPanelRepository.getSolarPanelsForFacility(solarFacilityId);
        SolarFacility result = convertSolarFacility(solarFacilityEntityOptional.get());
        List<SolarPanel> resultSolarPanels = new ArrayList<>();
        for (SolarPanelEntity item : solarPanels) {
            resultSolarPanels.add(convertSolarPanel(item));
        }
        result.setSolarPanels(resultSolarPanels);
        return result;
    }

    @Transactional
    public void deleteFacility(Integer facilityId) {
        solarPanelRepository.deleteBySolarFacilityId(facilityId);
        solarFacilityRepository.deleteById(facilityId);
    }

    private SolarFacility convertSolarFacility(SolarFacilityEntity createdSolarFacilityEntity) {
        SolarFacility createdSolarFacility = new SolarFacility();
        createdSolarFacility.setAddress(createdSolarFacilityEntity.getAddress());
        createdSolarFacility.setCapacity(createdSolarFacilityEntity.getCapacity());
        createdSolarFacility.setId(createdSolarFacilityEntity.getId());
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(createdSolarFacilityEntity.getLatitude());
        coordinates.setLongitude(createdSolarFacilityEntity.getLongitude());
        createdSolarFacility.setCoordinates(coordinates);
        createdSolarFacility.setName(createdSolarFacilityEntity.getName());
        return createdSolarFacility;
    }

    private SolarFacilityEntity convertSolarFacilityEntity(SolarFacility solarFacility) {
        SolarFacilityEntity newSolarFacility = new SolarFacilityEntity();
        newSolarFacility.setAddress(solarFacility.getAddress());
        newSolarFacility.setCapacity(solarFacility.getCapacity());
        newSolarFacility.setLatitude(solarFacility.getCoordinates().getLatitude());
        newSolarFacility.setLongitude(solarFacility.getCoordinates().getLongitude());
        newSolarFacility.setName(solarFacility.getName());
        return newSolarFacility;
    }

    public SolarFacility updateSolarFacility(SolarFacility solarFacility)
            throws SolarParkManagerException {
        if (solarFacility.getId() == null) {
            throw new SolarParkManagerException("facility id is mandatory");
        }
        Integer facilityPanels = solarPanelRepository.countFacilityPanels(solarFacility.getId());
        if (facilityPanels > solarFacility.getCapacity()) {
            throw new SolarParkManagerException("non valid update ,too small capacity!");
        }

        SolarFacilityEntity facilityToUpdate = convertSolarFacilityEntity(solarFacility);
        facilityToUpdate.setId(solarFacility.getId());
        SolarFacilityEntity updatedSolarFacilityEntity = solarFacilityRepository.save(facilityToUpdate);
        return convertSolarFacility(updatedSolarFacilityEntity);
    }

    public FacilityPerformance getFacilityPerformance(Integer facilityId, LocalDate date) {

        LocalDateTime from = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 23, 59, 59);
        List<AggregatedPanelPerformance> aggregatedPanelPerformanceList = aggregatedPanelPerformanceRepository.getFacilityPerformance(facilityId, from.toString(), to.toString());
        FacilityPerformance facilityPerformance = new FacilityPerformance();
        facilityPerformance.setFacilityId(facilityId);
        Double totalKwh = 0D;
        List<PanelPerformance> panelPerformanceList = new ArrayList<>();
        for (AggregatedPanelPerformance entity : aggregatedPanelPerformanceList) {
            PanelPerformance panelPerformance = new PanelPerformance();
            panelPerformance.setPanelId(entity.getPanelId());
            panelPerformance.setAverageKwh(entity.getKwh());
            totalKwh += entity.getKwh();
            panelPerformanceList.add(panelPerformance);
        }
        facilityPerformance.setTotalKwh(totalKwh);
        facilityPerformance.setPanels(panelPerformanceList);

        return facilityPerformance;

    }
}
