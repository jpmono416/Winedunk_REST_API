package priceComparison.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The persistent class for the tblpfproducts database table.
 * 
 */
@Entity
@Table(name="tblPFProducts")


@NamedQueries({
	@NamedQuery(name="Tblpfproduct.findAll", query="SELECT t FROM Tblpfproduct t ORDER BY t.merchantName"),
	@NamedQuery(name="Tblpfproduct.findByTblpfId", query="SELECT t FROM Tblpfproduct t WHERE t.tblpf.id = :id ORDER BY t.merchantName"),
	@NamedQuery(name="Tblpfproduct.findByPartnerIdAndMerchantId", query="SELECT t FROM Tblpfproduct t "
																	  + "WHERE t.merchantProductId = :merchantProductId "
																	  	+ "AND t.partnerProductId = :partnerProductId "
																	  + "ORDER BY t.merchantName"),
	// aripe 2018-03-01, findByPartnerIdAndPartnerProductId added
	@NamedQuery(name="Tblpfproduct.findByPartnerIdAndPartnerProductId", query="SELECT t FROM Tblpfproduct t "
			  + "WHERE t.tblpf.partnerId.id = :partnerId "
			  	+ "AND t.partnerProductId = :partnerProductId "
			  + "ORDER BY t.merchantName")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Tblpfproduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	// aripe annotation added
	@Column(name= "clicktag", length = 100)
	private String clicktag;

	private Float deliveryCost;

	// aripe annotation added
	@Column(name= "imageURL", length = 400)
	private String imageURL;

	// aripe annotation added
	@Column(name= "merchantName", length = 100)
	private String merchantName;

	// aripe annotation added
	@Column(name= "merchantProductId", length = 30)
	private String merchantProductId;

	// aripe annotation added
	@Column(name= "name", length = 100)
	private String name;

	// aripe annotation added
	@Column(name= "partnerMerchantId", length = 20)
	private String partnerMerchantId;

	@Lob
	private String partnerProductDescription;

	// aripe annotation added
	@Column(name= "partnerProductId", length = 30)
	private String partnerProductId;

	private Float price;

	// aripe annotation added
	@Column(name= "productType", length = 100)
	private String productType;

	// aripe annotation added
	@Column(name= "productURL", length = 400)
	private String productURL;

	//bi-directional many-to-one association to Tblpf
	@ManyToOne
	@JoinColumn(name="pfId")
	private Tblpf tblpf;

	public Tblpfproduct() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClicktag() {
		return this.clicktag;
	}

	public void setClicktag(String clicktag) {
		this.clicktag = clicktag;
	}

	public Float getDeliveryCost() {
		return this.deliveryCost;
	}

	public void setDeliveryCost(Float deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public String getImageURL() {
		return this.imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getMerchantName() {
		return this.merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantProductId() {
		return this.merchantProductId;
	}

	public void setMerchantProductId(String merchantProductId) {
		this.merchantProductId = merchantProductId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartnerProductDescription() {
		return this.partnerProductDescription;
	}

	public void setPartnerProductDescription(String partnerProductDescription) {
		this.partnerProductDescription = partnerProductDescription;
	}

	public String getPartnerProductId() {
		return this.partnerProductId;
	}

	public void setPartnerProductId(String partnerProductId) {
		this.partnerProductId = partnerProductId;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductURL() {
		return this.productURL;
	}

	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}

	public Tblpf getTblpf() {
		return this.tblpf;
	}

	public void setTblpf(Tblpf tblpf) {
		this.tblpf = tblpf;
	}

	public String getPartnerMerchantId() {
		return partnerMerchantId;
	}

	public void setPartnerMerchantId(String partnerMerchantId) {
		this.partnerMerchantId = partnerMerchantId;
	}

	@Override
	public String toString() {
		return "Tblpfproduct [id=" + id + ", clicktag=" + clicktag + ", deliveryCost=" + deliveryCost + ", imageURL="
				+ imageURL + ", merchantName=" + merchantName + ", merchantProductId=" + merchantProductId + ", name="
				+ name + ", partnerMerchantId=" + partnerMerchantId + ", partnerProductDescription="
				+ partnerProductDescription + ", partnerProductId=" + partnerProductId + ", price=" + price
				+ ", productType=" + productType + ", productURL=" + productURL + ", tblpf=" + tblpf + "]";
	}

}