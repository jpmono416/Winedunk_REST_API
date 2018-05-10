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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "tblShops")
@NamedQueries({
	@NamedQuery(name="tblShops.findAll", query="SELECT t FROM tblShops t"),
	@NamedQuery(name="tblShops.findByName", query="SELECT t FROM tblShops t WHERE t.name = :name")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class tblShops {
	
	@Transient
    private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	@NotNull
	// aripe length attribute added
	@Column(name= "name", length = 45)
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	// aripe length attribute added
	@Column(name= "Logo", length = 200)
	private String logo;
	public String getLogo() { return logo; }
	public void setLogo(String logo) { this.logo = logo; }
	
	// aripe length attribute added
	@Column(name= "homePage", length = 1000)
	private String homePage;
	public String getHomePage() { return homePage; }
	public void setHomePage(String homePage) { this.homePage = homePage; }
	
	// aripe length attribute added
	@Column(name= "genericProductPage", length = 1000)
	private String genericProductPage;
	public String getGenericProductPage() { return genericProductPage; }
	public void setGenericProductPage(String genericProductPage) { this.genericProductPage = genericProductPage; }

	@NotNull
	@ManyToOne
	@JoinColumn(name = "dataSource")
	private DataSource dataSource;
	public DataSource getDataSource() { return this.dataSource; }
	public void setDataSource(DataSource dataSource) { this.dataSource = dataSource;}

	@Column(name= "deleted")
	private Boolean deleted;
	public Boolean isDeleted() {return deleted;}
	public void setDeleted(Boolean deleted) { this.deleted = deleted; }
	
	@OneToMany(mappedBy = "shopId", targetEntity = tblClicks.class)
	@JsonBackReference("shop_click")
	private List<tblClicks> clicks;
    public List<tblClicks> getClicks() { return clicks; }
	public void setClicks(List<tblClicks> clicks) { this.clicks = clicks; }
	
	@OneToMany(mappedBy = "minimumPriceShopId", targetEntity = tblWines.class)
	@JsonBackReference("wine_shop")
	private List<tblWines> wines;
	public List<tblWines> getWines() { return wines; }
	public void setWines(List<tblWines> wines) { this.wines = wines; }
	
	@OneToMany(mappedBy="tblShops", targetEntity = Tblpfmerchanthtmlparsing.class)
	private List<Tblpfmerchanthtmlparsing> parsingByMerchant;
	public List<Tblpfmerchanthtmlparsing> getTblpfmerchanthtmlparsing() { return parsingByMerchant; }
	public void setTblpfmerchanthtmlparsing(List<Tblpfmerchanthtmlparsing> parsingByMerchant) { this.parsingByMerchant = parsingByMerchant; }
	
	// aripe 2018-04-05
	//bi-directional many-to-one association to tblPartnersMerchants
	@OneToMany(mappedBy = "shop", targetEntity = tblPartnersMerchants.class)
	@JsonBackReference
	private List<tblPartnersMerchants> partnersMerchants;
	public List<tblPartnersMerchants> getPartnersMerchants() { return partnersMerchants; }
	public void setPartnersMerchants(List<tblPartnersMerchants> partnersMerchants) { this.partnersMerchants = partnersMerchants; }

	public tblShops(Integer id) { this.id = id; }
	public tblShops(String name) { this.name = name; }

	public tblShops()
	{
		this.id = null;
		this.name = null;
		this.logo = null;
		this.homePage = null;
		this.genericProductPage = null;
		this.deleted = null;
		this.clicks = null;
		this.wines = null;
	}

	@Override
	public String toString() {
		return "tblShops [id=" + id + ", name=" + name + ", logo=" + logo + ", homePage=" + homePage
				+ ", genericProductPage=" + genericProductPage + ", dataSource=" + dataSource + ", deleted=" + deleted
				+ "]";
	}
}
