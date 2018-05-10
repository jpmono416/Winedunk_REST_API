package priceComparison.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "tblPartnersTypes")
public class tblPartnersTypes {

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
	
	@Column(name = "deleted")
	private Boolean deleted;
	public Boolean getDeleted() { return deleted; }
	public void setDeleted(Boolean deleted) { this.deleted = deleted; }
	
	@OneToMany(mappedBy = "partnerTypeId", targetEntity = tblPartners.class)
	@JsonBackReference("partners")
	List<tblPartners> partners;
	public List<tblPartners> getPartners() { return partners; }
	public void setPartners(List<tblPartners> partners) { this.partners = partners; }
	
	public tblPartnersTypes(Integer id) { this.id = id; }
    public tblPartnersTypes()
    {
        this.id = null;
        this.name = null;    
        this.deleted = null;
        this.partners = null;
    }
}
