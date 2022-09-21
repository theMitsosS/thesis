package gr.unipi.solarparkmanager.repository;

import gr.unipi.solarparkmanager.model.entity.SolarFacilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolarFacilityRepository extends JpaRepository<SolarFacilityEntity, Integer> {
}
