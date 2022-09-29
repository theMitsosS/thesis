package gr.unipi.solarparkmanager.repository;

import gr.unipi.solarparkmanager.model.dto.AggregatedFacilityPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AggregatedPanelPerformanceRepository
        extends JpaRepository<AggregatedFacilityPerformance, Integer> {


    @Query(value = "select count(*) as \"total_panels\", AVG(performance.kwh) as \"average_panel_performance\", sum(performance.kwh) as \"total_facility_performance\" " +
            "from SOLAR_PANEL_PERFORMANCE performance, SOLAR_FACILITY facility, SOLAR_PANEL panel " +
            "where panel.solar_facility_id = facility.id and panel.id=performance.panel_id " +
            "and facility.id=:facilityId and date =:date ;", nativeQuery = true)
    AggregatedFacilityPerformance getFacilityPerformance(Integer facilityId, String date);
}


