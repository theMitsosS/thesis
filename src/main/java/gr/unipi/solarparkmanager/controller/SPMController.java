package gr.unipi.solarparkmanager.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import gr.unipi.solarparkmanager.model.dto.FacilityPerformance;
import gr.unipi.solarparkmanager.model.dto.PanelInputData;
import gr.unipi.solarparkmanager.model.dto.SolarFacility;
import gr.unipi.solarparkmanager.model.dto.SolarPanel;
import gr.unipi.solarparkmanager.model.dto.exception.SolarParkManagerException;
import gr.unipi.solarparkmanager.service.SolarFacilityService;
import gr.unipi.solarparkmanager.service.SolarPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SPMController {

    @Autowired
    private SolarFacilityService solarFacilityService;

    @Autowired
    private SolarPanelService solarPanelService;

    @PostMapping("/solar/facility/create")
    public ResponseEntity<SolarFacility> createSolarFacility(
            @RequestBody SolarFacility solarFacility) {

        return ResponseEntity.ok(solarFacilityService.create(solarFacility));
    }


    @PostMapping("/solar/panel/create")
    public ResponseEntity<SolarPanel> createSolarPanel(@RequestBody SolarPanel solarPanel)
            throws SolarParkManagerException {
        return ResponseEntity.ok(solarPanelService.create(solarPanel));
    }

    @GetMapping("/solar/facilities")
    public ResponseEntity<List<SolarFacility>> getAllFacilities() {
        return ResponseEntity.ok(solarFacilityService.getAll());
    }

    @GetMapping("/solar/facility/{facilityId}")
    public ResponseEntity<SolarFacility> getFacility(@PathVariable("facilityId") Integer facilityId)
            throws SolarParkManagerException {
        if (facilityId == null) {
            throw new SolarParkManagerException("facility id is mandatory");
        }
        return ResponseEntity.ok(solarFacilityService.getSolarFacility(facilityId));
    }

    @DeleteMapping("/solar/panel/{panelId}")
    public ResponseEntity deletePanel(@PathVariable("panelId") Integer panelId)
            throws SolarParkManagerException {
        if (panelId == null) {
            throw new SolarParkManagerException("panel id is mandatory");
        }
        solarPanelService.deletePanel(panelId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/solar/facility/{facilityId}")
    public ResponseEntity deleteFacility(@PathVariable("facilityId") Integer facilityId)
            throws SolarParkManagerException {
        if (facilityId == null) {
            throw new SolarParkManagerException("facility id is mandatory");
        }
        solarFacilityService.deleteFacility(facilityId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/solar/panel/update")
    public ResponseEntity<SolarPanel> updateSolarPanel(@RequestBody SolarPanel solarPanel)
            throws SolarParkManagerException {
        if (solarPanel == null) {
            throw new SolarParkManagerException("message is null");
        }
        return ResponseEntity.ok(solarPanelService.updateSolarPanel(solarPanel));
    }

    @GetMapping("/solar/panel/{panelId}")
    public ResponseEntity<SolarPanel> getSolarPanel(@PathVariable("panelId") Integer panelId)
            throws SolarParkManagerException {
        if (panelId == null) {
            throw new SolarParkManagerException("panel id is mandatory");
        }
        return ResponseEntity.ok(solarPanelService.getSolarPanelById(panelId));
    }

    @PostMapping("/solar/facility/update")
    public ResponseEntity<SolarFacility> updateSolarFacility(@RequestBody SolarFacility solarFacility)
            throws SolarParkManagerException {
        if (solarFacility == null) {
            throw new SolarParkManagerException("message is null");
        }
        return ResponseEntity.ok(solarFacilityService.updateSolarFacility(solarFacility));
    }

    @PostMapping("/solar/panel/performance")
    public ResponseEntity insertPanelPerformance(@RequestBody PanelInputData panelInputData)
            throws SolarParkManagerException {
        if (panelInputData == null) {
            throw new SolarParkManagerException("message is null");
        }
        solarPanelService.insertPerformance(panelInputData);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/solar/facility/performance/{facilityId}")
    public ResponseEntity<FacilityPerformance> getFacilityPerformance(
            @PathVariable("facilityId") Integer facilityId,
            @RequestParam(value = "date", required = true) String date) throws SolarParkManagerException {
        if (facilityId == null) {
            throw new SolarParkManagerException("facility id is mandatory");
        }
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return ResponseEntity.ok(solarFacilityService.getFacilityPerformance(facilityId, localDate));
    }

}


