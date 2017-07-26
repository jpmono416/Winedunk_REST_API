package priceComparison.models;

public class viewWinePriceComparison {

	private Integer id;
    public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	private Integer wineId;
	public Integer getWineId() { return wineId; }
	public void setWineId(Integer wineId) { this.wineId = wineId; }
	
	private Integer shopId;
	public Integer getShopId() { return shopId; }
	public void setShopId(Integer shopId) { this.shopId = shopId; }
	
	private String shopName;
	public String getShopName() { return shopName; }
	public void setShopName(String shopName) { this.shopName = shopName; }
	
	private String shopImageURL;
	public String getShopImageURL() { return shopImageURL; }
	public void setShopImageURL(String shopImageURL) { this.shopImageURL = shopImageURL; }

	private Integer partnerId;
	public Integer getPartnerId() { return partnerId; }
	public void setPartnerId(Integer partnerId) { this.partnerId = partnerId; }
	
	private String partnerProductId;
	public String getPartnerProductId() { return partnerProductId; }
	public void setPartnerProductId(String partnerProductId) { this.partnerProductId = partnerProductId; }
	
	private String destinationURL;
	public String getDestinationURL() { return destinationURL; }
	public void setDestinationURL(String destinationURL) { this.destinationURL = destinationURL; }
	
	private Float productPrice;
	public Float getProductPrice() { return productPrice; }
	public void setProductPrice(Float productPrice) { this.productPrice = productPrice; }
	
	private Float deliveringCost;
	public Float getDeliveringCost() { return deliveringCost; }
	public void setDeliveringCost(Float deliveringCost) { this.deliveringCost = deliveringCost; }
	
	private Integer stock;
	public Integer getStock() { return stock; }
	public void setStock(Integer stock) { this.stock = stock; }
	
	public viewWinePriceComparison(Integer id) { this.id = id; } 
	public viewWinePriceComparison() 
	{
        this.id = null; 
        this.wineId = null;
        this.shopId = null;
        this.shopName = null;
        this.shopImageURL = null;
        this.partnerId = null;
        this.partnerProductId = null;
        this.destinationURL = null;
        this.productPrice = null;
        this.deliveringCost = null;
        this.stock = null;
    }
}
