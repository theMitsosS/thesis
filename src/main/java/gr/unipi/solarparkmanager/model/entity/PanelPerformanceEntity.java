package gr.unipi.solarparkmanager.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
//configuration to allow only 1 insertion of panel performance per day.
@Table(name = "SOLAR_PANEL_PERFORMANCE", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "panelId"}))
public class PanelPerformanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer panelId;
    private Long kwh;
    private LocalDate date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPanelId() {
        return panelId;
    }

    public void setPanelId(Integer panelId) {
        this.panelId = panelId;
    }

    public Long getKwh() {
        return kwh;
    }

    public void setKwh(Long kwh) {
        this.kwh = kwh;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
