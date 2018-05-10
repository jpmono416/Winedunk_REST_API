package priceComparison.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tblClicks")
public class tblClicks {
	
	@Transient
    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	@ManyToOne
	@JoinColumn(name = "userId")
	private tblUsers userId;
	public tblUsers getUserId() { return userId; }
	public void setUserId(tblUsers userId) { this.userId = userId; }
	
	@ManyToOne
	@JoinColumn(name = "wineId")
	private tblWines wineId;
	public tblWines getWineId() { return wineId; }
	public void setWineId(tblWines wineId) { this.wineId = wineId; }
	
	@ManyToOne
	@JoinColumn(name = "shopId")
	private tblShops shopId;
	public tblShops getShopId() { return shopId; }
	public void setShopId(tblShops shopId) { this.shopId = shopId; }
	
	@ManyToOne
	@JoinColumn(name = "partnerId")
	private tblPartners partnerId;
	public tblPartners getPartnerId() { return partnerId; }
	public void setPartnerId(tblPartners partnerId) { this.partnerId = partnerId; }
	
	@JoinColumn(name = "date")
    @Temporal(TemporalType.DATE)
	private Date date;
	public Date getDate() { return date; }
	public void setDate(Date date) { this.date = date; }
	
	@Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "timestamp")
	private Date timestamp;
	public Date getTimestamp() { return timestamp; }
	public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
	
	// aripe length attribute added
	@Column(name= "destinationURL", length = 400)
	private String destinationURL;
	public String getDestinationURL() { return destinationURL; }
	public void setDestinationURL(String destinationURL) { this.destinationURL = destinationURL; }
	
	@Column(name = "productPrice")
	private Float productPrice;
	public Float getProductPrice() { return productPrice; }
	public void setProductPrice(Float productPrice) { this.productPrice = productPrice; }
	
	@Column(name= "deleted")
	private Boolean deleted;
	public Boolean isDeleted() {return deleted;}
	public void setDeleted(Boolean deleted) { this.deleted = deleted; }
	
	public tblClicks(Integer id) { this.id = id; }
	public tblClicks()
	{
		this.id = null;
		this.userId = null;
		this.wineId = null;
		this.shopId = null;
		this.date = null;
		this.timestamp = null;
		this.destinationURL = null;
		this.productPrice = null;
		this.deleted = null;
	}
}