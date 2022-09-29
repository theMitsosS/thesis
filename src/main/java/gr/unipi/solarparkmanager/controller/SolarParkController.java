package gr.unipi.solarparkmanager.controller;

import gr.unipi.solarparkmanager.exception.SolarFacilityNotFoundException;
import gr.unipi.solarparkmanager.exception.SolarPanelNotFoundException;
import gr.unipi.solarparkmanager.exception.SolarParkManagerException;
import gr.unipi.solarparkmanager.model.dto.FacilityPerformanceJson;
import gr.unipi.solarparkmanager.model.dto.PanelPerformanceJson;
import gr.unipi.solarparkmanager.model.dto.SolarFacilityJson;
import gr.unipi.solarparkmanager.model.dto.SolarPanelJson;
import gr.unipi.solarparkmanager.service.SolarFacilityService;
import gr.unipi.solarparkmanager.service.SolarPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class SolarParkController {

    @Autowired
    private SolarFacilityService solarFacilityService;

    @Autowired
    private SolarPanelService solarPanelService;

    @PostMapping("/solar/facility/create")
    public ResponseEntity<SolarFacilityJson> createSolarFacility(@Validated @RequestBody SolarFacilityJson solarFacilityJson) {
        return ResponseEntity.ok(solarFacilityService.create(solarFacilityJson));
    }

    @GetMapping("/solar/facilities")
    public ResponseEntity<List<SolarFacilityJson>> getAllFacilities() {
        return ResponseEntity.ok(solarFacilityService.getAll());
    }

    @GetMapping("/solar/facility/{facilityId}")
    public ResponseEntity<SolarFacilityJson> getFacility(@PathVariable("facilityId") Integer facilityId) throws IllegalAccessException, SolarFacilityNotFoundException, InvocationTargetException {
        return ResponseEntity.ok(solarFacilityService.getSolarFacility(facilityId));
    }

    @PutMapping("/solar/facility/update")
    public ResponseEntity<SolarFacilityJson> updateSolarFacility(@Validated @RequestBody SolarFacilityJson solarFacilityJson) throws SolarParkManagerException, SolarFacilityNotFoundException {
        return ResponseEntity.ok(solarFacilityService.updateSolarFacility(solarFacilityJson));
    }

    /**
     * API request that takes as input a facility ID and deletes the facility and all the solar panels that
     * belong to this facility.
     */
    @DeleteMapping("/solar/facility/{facilityId}")
    public ResponseEntity<Object> deleteFacility(@PathVariable("facilityId") Integer facilityId) throws SolarFacilityNotFoundException {
        try {
            solarFacilityService.deleteFacility(facilityId);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) { //this exception will be thrown if no facility exists with given id
            throw new SolarFacilityNotFoundException(facilityId);
        }
    }

    @GetMapping("/solar/facility/performance/{facilityId}")
    public ResponseEntity<FacilityPerformanceJson> getFacilityPerformance(
            @PathVariable("facilityId") Integer facilityId, @RequestParam(value = "date") String date) throws SolarParkManagerException, SolarFacilityNotFoundException {
        if (facilityId == null) {
            throw new SolarParkManagerException("facility ID is mandatory");
        }
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return ResponseEntity.ok(solarFacilityService.getFacilityPerformance(facilityId, localDate));
    }


    @PostMapping("/solar/panel/create")
    public ResponseEntity<SolarPanelJson> createSolarPanel(@Validated @RequestBody SolarPanelJson solarPanelJson) throws SolarParkManagerException, SolarFacilityNotFoundException {
        return ResponseEntity.ok(solarPanelService.create(solarPanelJson));
    }

    @GetMapping("/solar/panel/{panelId}")
    public ResponseEntity<SolarPanelJson> getSolarPanel(@PathVariable("panelId") Integer panelId) throws SolarPanelNotFoundException {
        return ResponseEntity.ok(solarPanelService.getSolarPanelById(panelId));
    }

    @PutMapping("/solar/panel/update")
    public ResponseEntity<SolarPanelJson> updateSolarPanel(@Validated @RequestBody SolarPanelJson solarPanelJson) throws SolarParkManagerException, SolarFacilityNotFoundException, SolarPanelNotFoundException {
        return ResponseEntity.ok(solarPanelService.updateSolarPanel(solarPanelJson));
    }

    @DeleteMapping("/solar/panel/{panelId}")
    public ResponseEntity<Object> deletePanel(@PathVariable("panelId") Integer panelId) throws SolarPanelNotFoundException {
        try {
            solarPanelService.deletePanel(panelId);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) { //this exception will be thrown if no panel exists with given ID
            throw new SolarPanelNotFoundException(panelId);
        }
    }

    @GetMapping("/solar/panels")
    public ResponseEntity<List<SolarPanelJson>> getAllPanels() {
        return ResponseEntity.ok(solarPanelService.getAll());
    }

    @PostMapping("/solar/panel/performance")
    public ResponseEntity<Object> insertDailyPanelPerformance(@Validated @RequestBody PanelPerformanceJson panelPerformanceJson) throws SolarPanelNotFoundException {
        solarPanelService.insertSingleDayPerformanceData(panelPerformanceJson);
        return ResponseEntity.noContent().build();
    }
}


