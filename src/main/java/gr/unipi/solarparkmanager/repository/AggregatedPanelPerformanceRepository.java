package gr.unipi.solarparkmanager.repository;

import java.util.List;

import gr.unipi.solarparkmanager.model.dto.AggregatedPanelPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AggregatedPanelPerformanceRepository
        extends JpaRepository<AggregatedPanelPerformance, Integer> {

    @Query(value =
            "SELECT info.panel_id \"panelId\",AVG(info.kwh) as \"kwh\" FROM PANEL_PERFORMANCE_ENTITY info , T_SOLAR_FACILITY faci , T_SOLAR_PANEL pan where " +
                    "pan.solar_facility_id = faci.id " +
                    "and pan.id = info.panel_id " +
                    "and faci.id=:facilityId " +
                    "and date < :to " +
                    "and date > :from " +
                    "group by info.panel_id ;", nativeQuery = true)
    List<AggregatedPanelPerformance> getFacilityPerformance(Integer facilityId, String from,
                                                            String to);
}
