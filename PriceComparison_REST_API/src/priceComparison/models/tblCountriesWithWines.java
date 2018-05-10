package priceComparison.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "tblCountriesWithWines")
public class tblCountriesWithWines {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @ManyToOne
    @JoinColumn(name = "countryId")
    private tblCountries countryId;
    public tblCountries getCountryId() { return countryId; }
	public void setCountryId(tblCountries countryId) { this.countryId = countryId; }
	
	@Column(name = "totalWines")
	private Integer totalWines;
	public Integer getTotalWines() { return totalWines; }
	public void setTotalWines(Integer totalWines) { this.totalWines = totalWines; }

	
	public tblCountriesWithWines(Integer id) { this.id = id; }
    public tblCountriesWithWines()
    {
        this.id = null;
        this.totalWines = null;
        this.countryId = null;
    }
}
