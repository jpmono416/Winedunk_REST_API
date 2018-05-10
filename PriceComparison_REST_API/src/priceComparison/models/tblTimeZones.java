package priceComparison.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "tblTimeZones")
public class tblTimeZones {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    // aripe length attribute added
    @Column(name= "name", length = 45)
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // aripe length attribute added
    @Column(name= "shortName", length = 45)
    private String shortName;
    public String getShortName() { return shortName; }
    public void setShortName(String shortName) { this.shortName = shortName; }
    
    @Column(name = "utcOffset")
    private Float utcOffset;
    public Float getUtcOffset() { return utcOffset; }
	public void setUtcOffset(Float utcOffset) { this.utcOffset = utcOffset; }

	@Column(name= "deleted")
    private Boolean deleted;
    public Boolean isDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

    @ManyToMany(mappedBy = "timeZones")
    @JsonBackReference
    private List<tblCountries> countries;
    public List<tblCountries> getCountries() { return countries; }
    public void setCountries(List<tblCountries> countries) { this.countries = countries; }

    @OneToMany(mappedBy = "preferredTimeZoneId")
    @JsonBackReference("user_timeZone")
    private List<tblUsers> users;
    public List<tblUsers> getUsers() { return users; }
    public void setUsers(List<tblUsers> users) { this.users = users; }

    public tblTimeZones(Integer id) { this.id = id; }
    public tblTimeZones()
    {
        this.id = null;
        this.name = null;
        this.shortName = null;
        this.utcOffset = null;
        this.deleted = null;
        this.countries = null;
        this.users = null;
    }
    public tblTimeZones(String name) {
        this.name = name;
    }
}
