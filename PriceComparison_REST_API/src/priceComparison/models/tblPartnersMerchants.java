// aripe 2018-04-05

package priceComparison.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "tblPartnersMerchants")
@NamedQueries({
	@NamedQuery(name="tblPartnersMerchants.findAll", query="SELECT x FROM tblPartnersMerchants x"),
	@NamedQuery(name="tblPartnersMerchants.findOneById", query="SELECT x FROM tblPartnersMerchants x WHERE x.id = :id"),
	@NamedQuery(name="tblPartnersMerchants.findOneByShopIdAndPartnerId", query="SELECT x FROM tblPartnersMerchants x WHERE x.shop = :shop AND x.partner = :partner"),
	@NamedQuery(name="tblPartnersMerchants.findOneBypartnerMerchantName", query="SELECT x FROM tblPartnersMerchants x WHERE x.partnerMerchantName = :partnerMerchantName")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class tblPartnersMerchants {
	
	@Transient
    private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	//bi-directional many-to-one association to tblShops
	@ManyToOne
	@JoinColumn(name="shopId")
	private tblShops shop;
	public tblShops getShop() { return shop; }
	public void setShop(tblShops shop) { this.shop = shop; }

	//bi-directional many-to-one association to tblPartners
	@ManyToOne
	@JoinColumn(name="partnerId")
	private tblPartners partner;
	public tblPartners getPartner() { return partner; }
	public void setPartner(tblPartners partner) { this.partner = partner; }

	// aripe length attribute added
	@Column(name= "partnerMerchantId", length = 20)
	private String partnerMerchantId;
	public String getPartnerMerchantId() { return partnerMerchantId; }
	public void setPartnerMerchantId(String partnerMerchantId) { this.partnerMerchantId = partnerMerchantId; }
	
	// aripe length attribute added
	@Column(name= "partnerMerchantName", length = 45)
	private String partnerMerchantName;
	public String getPartnerMerchantName() { return partnerMerchantName; }
	public void setPartnerMerchantName(String partnerMerchantName) { this.partnerMerchantName = partnerMerchantName; }

	public tblPartnersMerchants()
	{
		this.id = null;
		this.shop = null;
		this.partner = null;
		this.partnerMerchantId = null;
		this.partnerMerchantName = null;
	}

	@Override
	public String toString() {
		return "tblPartnersMerchants [id=" + id + ", shop=" + shop + ", partner=" + partner +
				",partnerMerchantId=" + partnerMerchantId + ", partnerMerchantName=" + partnerMerchantName
				+ "]";
	}
}
