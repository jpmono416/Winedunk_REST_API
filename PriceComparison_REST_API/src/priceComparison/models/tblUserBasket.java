package priceComparison.models;

import java.util.Date;

public class tblUserBasket {
	
	private Integer id;
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	private Integer numericUserId;
	public Integer getNumericUserId() { return numericUserId; }
	public void setNumericUserId(Integer numericUserId) { this.numericUserId = numericUserId; }
	
	private Integer numericWineId;
	public Integer getNumericWineId() { return numericWineId; }
	public void setNumericWineId(Integer numericWineId) { this.numericWineId = numericWineId; }
	
	private Integer numericShopId;
	public Integer getNumericShopId() { return numericShopId; }
	public void setNumericShopId(Integer numericShopId) { this.numericShopId = numericShopId; }
	
	private Integer numericPartnerId;
	public Integer getNumericPartnerId() { return numericPartnerId; }
	public void setNumericPartnerId(Integer numericPartnerId) { this.numericPartnerId = numericPartnerId; }

	private String date;
	public String getDate() { return date; }
	public void setDate(String date) { this.date = date; }

	private Date timestamp;
	public Date getTimestamp() { return timestamp; }
	public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
	
	private String destinationURL;
	public String getDestinationURL() { return destinationURL; }
	public void setDestinationURL(String destinationURL) { this.destinationURL = destinationURL; }
	
	private Float productPrice;
	public Float getProductPrice() { return productPrice; }
	public void setProductPrice(Float productPrice) { this.productPrice = productPrice; }
	 
	private String partnerProductId;
	public String getPartnerProductId() { return partnerProductId; } 
	public void setPartnerProductId(String partnerProductId) {this.partnerProductId = partnerProductId; }
	
	public tblUserBasket(Integer id) { this.id = id; }
	public tblUserBasket()
	{
		this.id = null;
		this.numericUserId = null;
		this.numericWineId = null;
		this.numericShopId = null;
		this.date = null;
		this.timestamp = null;
		this.destinationURL = null;
		this.productPrice = null;
		this.partnerProductId = null;
	}
}