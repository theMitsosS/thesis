package gr.unipi.solarparkmanager.repository;

import gr.unipi.solarparkmanager.model.entity.SolarPanelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolarPanelRepository extends JpaRepository<SolarPanelEntity, Integer> {

    @Query(value = "SELECT count(*) FROM SOLAR_PANEL WHERE SOLAR_FACILITY_ID= :facilityId", nativeQuery = true)
    Integer countTotalExistingPanelsInAFacility(@Param("facilityId") Integer facilityId);

    @Query(value = "SELECT * FROM SOLAR_PANEL WHERE SOLAR_FACILITY_ID= :facilityId", nativeQuery = true)
    List<SolarPanelEntity> getSolarPanelsInFacility(@Param("facilityId") Integer facilityId);

    void deleteBySolarFacilityId(Integer facilityId);

}

