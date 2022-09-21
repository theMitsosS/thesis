package gr.unipi.solarparkmanager.repository;

import gr.unipi.solarparkmanager.model.entity.PanelPerformanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanelPerformanceRepository extends JpaRepository<PanelPerformanceEntity, Integer> {

}
