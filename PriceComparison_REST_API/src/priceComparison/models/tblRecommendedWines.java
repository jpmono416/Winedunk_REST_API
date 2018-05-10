package priceComparison.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "tblRecommendedWines")
public class tblRecommendedWines {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @ManyToOne
    @JoinColumn(name = "wineId")
    private tblWines wineId;
    public tblWines getWineId() { return wineId; }
    public void setWineId(tblWines wineId) { this.wineId = wineId; }

    @Column(name = "dateFrom")
    @Temporal(TemporalType.DATE)
    private Date dateFrom;
    public Date getDateFrom() { return dateFrom; }
	public void setDateFrom(Date dateFrom) { this.dateFrom = dateFrom; }
	
	@Column(name = "dateTo")
    @Temporal(TemporalType.DATE)
    private Date dateTo;
	public Date getDateTo() { return dateTo; }
	public void setDateTo(Date dateTo) { this.dateTo = dateTo; }

    public tblRecommendedWines()
    {
        this.id = null;
        this.wineId = null;
        this.dateFrom = null;
        this.dateTo = null;
    }
}
