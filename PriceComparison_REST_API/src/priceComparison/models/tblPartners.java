package priceComparison.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name = "tblPartners")
@NamedQueries({
	@NamedQuery(name="tblPartners.findAll", query="SELECT p FROM tblPartners p"),
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class tblPartners {

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

    @Column(name= "deleted")
    private Boolean deleted = false;
    public Boolean isDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }
    
    @ManyToOne
    @JoinColumn(name = "partnersTypesId")
    private tblPartnersTypes partnerTypeId;
    public tblPartnersTypes getPartnerTypeId() { return partnerTypeId; }
	public void setPartnerTypeId(tblPartnersTypes partnerTypeId) { this.partnerTypeId = partnerTypeId; 	}
	
	@OneToMany(mappedBy = "partner", targetEntity = tblPartnersContacts.class)
	@JsonBackReference("partners_contacts")
	List<tblPartnersContacts> contacts;
	public List<tblPartnersContacts> getContacts() { return contacts; }
	public void setContacts(List<tblPartnersContacts> contacts) { this.contacts = contacts; }
	
	@OneToMany(mappedBy = "partnerId", targetEntity = tblClicks.class)
	@JsonBackReference("partners_clicks")
	private List<tblClicks> clicks;
	public List<tblClicks> getClicks() { return clicks; }
	public void setClicks(List<tblClicks> clicks) { this.clicks = clicks; }

	@OneToMany(mappedBy = "partnerId", targetEntity = Tblpf.class)
	private List<Tblpf> tblPfs;
	public List<Tblpf> getTblpfs() { return tblPfs; }
	public void setTblpfs(List<Tblpf> tblPfs) { this.tblPfs = tblPfs; }
	
	// aripe 2018-04-05
	//bi-directional many-to-one association to tblPartnersMerchants
	@OneToMany(mappedBy = "partner", targetEntity = tblPartnersMerchants.class)
	@JsonBackReference
	private List<tblPartnersMerchants> partnersMerchants;
	public List<tblPartnersMerchants> getPartnersMerchants() { return partnersMerchants; }
	public void setPartnersMerchants(List<tblPartnersMerchants> partnersMerchants) { this.partnersMerchants = partnersMerchants; }

	// aripe 2018-04-14
	//bi-directional many-to-one association to Tblpflog
	/*
	@OneToMany(mappedBy="tblpartner")
	private List<TblPFLog> tblPFLogs;
	public List<TblPFLog> getPartnerLogs() { return tblPFLogs; }
	*/

	public tblPartners(Integer id) { this.id = id;}
    public tblPartners()
    {
        this.id = null;
        this.name = null;
        this.deleted = null;
        this.partnerTypeId = null;
    }
    public tblPartners(String name) { this.name = name; }
	@Override
	public String toString() {
		return "tblPartners [id=" + id + ", name=" + name + ", deleted=" + deleted + ", partnerTypeId=" + partnerTypeId + "]";
	}

    
}
 