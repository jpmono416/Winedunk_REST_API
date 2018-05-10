package priceComparison.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "tblPartnersContacts")
public class tblPartnersContacts {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @ManyToOne
	@JoinColumn(name= "partnerId")
	tblPartners partner;
    public tblPartners getPartner() { return partner; }
	public void setPartner(tblPartners partner) { this.partner = partner; }
	
	// aripe length attribute added
	@Column(name= "name", length = 45)
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // aripe length attribute added
    @Column(name= "position", length = 45)
    private String position;
    public String getPosition() { return position; }
	public void setPosition(String position) { this.position = position; }

	// aripe length attribute added
	@Column(name= "primaryEmail", length = 45)
	private String primaryEmail;
	public String getPrimaryEmail() { return primaryEmail; }
	public void setPrimaryEmail(String primaryEmail) { this.primaryEmail = primaryEmail; }
	
	// aripe length attribute added
	@Column(name= "primaryPhoneNumber", length = 20)
	private String primaryPhoneNumber;
	public String getPrimaryPhoneNumber() { return primaryPhoneNumber; }
	public void setPrimaryPhoneNumber(String primaryPhoneNumber) { this.primaryPhoneNumber = primaryPhoneNumber; }
	
	// aripe length attribute added
	@Column(name= "secondaryEmail", length = 45)
	private String secondaryEmail;
	public String getSecondaryEmail() { return secondaryEmail; }
	public void setSecondaryEmail(String secondaryEmail) { this.secondaryEmail = secondaryEmail; }
	
	// aripe length attribute added
	@Column(name= "secondaryPhoneNumber", length = 45)
	private String secondaryPhoneNumber;
	public String getSecondaryPhoneNumber() { return secondaryPhoneNumber; }
	public void setSecondaryPhoneNumber(String secondaryPhoneNumber) { this.secondaryPhoneNumber = secondaryPhoneNumber; }

	@Column(name= "deleted")
    private Boolean deleted;
    public Boolean isDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }
	
	public tblPartnersContacts(Integer id) { this.id = id;}
    public tblPartnersContacts()
    {
        this.id = null;
        this.name = null;
        this.deleted = null;
        this.primaryEmail = null;
        this.primaryPhoneNumber = null;
        this.secondaryEmail = null;
        this.secondaryPhoneNumber = null;
    }
    public tblPartnersContacts(String name) { this.name = name; }
    
	@Override
	public String toString() {
		return "tblPartners [id=" + id + ", name=" + name + ", deleted=" + deleted + "]";
	}
    
}
 