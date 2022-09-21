package gr.unipi.solarparkmanager.repository;

import java.util.List;

import gr.unipi.solarparkmanager.model.entity.SolarPanelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolarPanelRepository extends JpaRepository<SolarPanelEntity, Integer> {

    @Query(value = "SELECT count(*) FROM T_SOLAR_PANEL WHERE SOLAR_FACILITY_ID= :facilityId", nativeQuery = true)
    Integer countFacilityPanels(@Param("facilityId") Integer facilityId);

    @Query(value = "SELECT * FROM T_SOLAR_PANEL WHERE SOLAR_FACILITY_ID= :facilityId", nativeQuery = true)
    List<SolarPanelEntity> getSolarPanelsForFacility(@Param("facilityId") Integer facilityId);

    void deleteBySolarFacilityId(Integer facilityId);

}

