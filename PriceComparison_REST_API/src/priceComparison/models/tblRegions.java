package priceComparison.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "tblRegions")
@NamedQuery(name="tblRegions.findByName", query="SELECT t FROM tblRegions t WHERE t.name = :name")
public class tblRegions {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id;}
    
    // aripe length attribute added
    @Column(name= "name", length = 45)
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Column(name= "deleted")
    private Boolean deleted;
    public Boolean isDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

    @OneToMany(mappedBy = "region")
    @JsonBackReference("user_country")
    private List<tblWines> wines;
    public List<tblWines> getWines() { return wines; }
    public void setWines(List<tblWines> wines) { this.wines = wines; }

    @OneToMany(mappedBy = "regionId")
    @JsonBackReference("appellation_region")
    private List<tblAppellations> appellations;
    public List<tblAppellations> getAppellations() { return appellations; }
    public void setAppellations(List<tblAppellations> appellations) { this.appellations = appellations; }

    @ManyToOne
    @JoinColumn(name = "countryId")
    private tblCountries tblCountries;
    public tblCountries getTblCountries() { return tblCountries; }
	public void setTblCountries(tblCountries tblCountries) { this.tblCountries = tblCountries; }
	
	public tblRegions(Integer id) { this.id = id; }
    public tblRegions()
    {
        this.id = null;
        this.name = null;
        this.deleted = null;
        this.wines = null;
        this.tblCountries = null;
    }

    @Override
	public String toString() {
		return "tblRegions [id=" + id + ", name=" + name + ", deleted=" + deleted + ", wines=" + wines
				+ ", appellations=" + appellations + ", tblCountries=" + tblCountries + "]";
	}
}
