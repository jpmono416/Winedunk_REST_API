package priceComparison.models;

public class viewWinesMinimumPrice extends Object {
	
	
    private Integer wineId;
    public Integer getWineId() { return wineId; }
	public void setWineId(Integer wineId) { this.wineId = wineId; }
	
	private String countryName;
    public String getCountryName() { return countryName; }
	public void setCountryName(String countryName) { this.countryName = countryName; }
	
    private String regionName;
    public String getRegionName() { return regionName; }
	public void setRegionName(String regionName) { this.regionName = regionName; }
	
    private String wineryName;
    public String getWineryName() { return wineryName; }
	public void setWineryName(String wineryName) { this.wineryName = wineryName; }
	
    private String appellationName;
    public String getAppellationName() { return appellationName; }
	public void setAppellationName(String appellationName) { this.appellationName = appellationName; }
	
    private String colourName;
    public String getColourName() { return colourName; }
	public void setColourName(String colourName) { this.colourName = colourName; }
	
    private Integer vintage;
    public Integer getVintage() { return vintage; }
	public void setVintage(Integer vintage) { this.vintage = vintage; }
	
    private String name;
    public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
    private String defaultDescription;
    public String getDefaultDescription() { return defaultDescription; }
	public void setDefaultDescription(String defaultDescription) { this.defaultDescription = defaultDescription; }
	
    private Integer bottleSize;
    public Integer getBottleSize() { return bottleSize; }
	public void setBottleSize(Integer bottleSize) { this.bottleSize = bottleSize; }
	
	private Float abv;
    public Float getAbv() { return abv; } 
	public void setAbv(Float abv) { this.abv = abv; }
	
    private String imageURL;
    public String getImageURL() { return imageURL; }
	public void setImageURL(String imageURL) { this.imageURL = imageURL; }
	
    private String closureName;
    public String getClosureName() { return closureName; }
	public void setClosureName(String closureName) { this.closureName = closureName; }
	
    private String gtin;
    public String getGtin() { return gtin; }
	public void setGtin(String gtin) { this.gtin = gtin; }
	
	private String wineTypeName;
	public String getWineTypeName() { return wineTypeName;  } 
	public void setWineTypeName(String wineType) { this.wineTypeName = wineType; }
	
	private String grapeVarietyName;
	public String getGrapeVarietyName() { return grapeVarietyName; }
	public void setGrapeVarietyName(String grapeVariety) { this.grapeVarietyName = grapeVariety; }
	
	private String partnerProductId;
    public String getPartnerProductId() { return partnerProductId; }
	public void setPartnerProductId(String partnerProductId) { this.partnerProductId = partnerProductId; }

	private Integer partnerMerchantId;
    public Integer getPartnerMerchantId() { return partnerMerchantId; }
	public void setPartnerMerchantId(Integer partnerMerchantId) { this.partnerMerchantId = partnerMerchantId; }
	
    private String partnerMerchantProductId;
    public String getPartnerMerchantProductId() { return partnerMerchantProductId; }
	public void setPartnerMerchantProductId(String partnerMerchantProductId) { this.partnerMerchantProductId = partnerMerchantProductId; }

	private Float minimumPrice;
	public Float getMinimumPrice() { return minimumPrice; }
	public void setMinimumPrice(Float minimumPrice) { this.minimumPrice = minimumPrice; }
	
	private String minimumPriceShopName;
	public String getMinimumPriceShopName() { return minimumPriceShopName; }
	public void setMinimumPriceShopName(String minimumPriceShopName) { this.minimumPriceShopName = minimumPriceShopName; }
	
	private String minimumPriceShopImgURL;
	public String getMinimumPriceShopImgURL() { return minimumPriceShopImgURL; }
	public void setMinimumPriceShopImgURL(String minimumPriceShopImgURL) { this.minimumPriceShopImgURL = minimumPriceShopImgURL; }
	
	private String minimumPriceDestinationURL;
	public String getMinimumPriceDestinationURL() { return minimumPriceDestinationURL; }
	public void setMinimumPriceDestinationURL(String minimumPriceDestinationURL) { this.minimumPriceDestinationURL = minimumPriceDestinationURL; }

    private Boolean deleted;
	public Boolean getDeleted() { return deleted; }
	public void setDeleted(Boolean deleted) { this.deleted = deleted; }
	
	private Float avgRating;
	public Float getAvgRating() { return avgRating; }
	public void setAvgRating(Float avgRating) { this.avgRating = avgRating; }
	
	public viewWinesMinimumPrice(Integer wineId) { this.wineId = wineId; }
	
	public viewWinesMinimumPrice()
	{
		this.wineId = null;
		this.countryName = null;
		this.regionName = null;
		this.wineryName = null;
		this.appellationName = null;
		this.colourName = null;
		this.vintage = null;
		this.name = null;
		this.defaultDescription = null;
		this.bottleSize = null;
		this.abv = null;
		this.imageURL = null;
		this.wineTypeName = null;
		this.grapeVarietyName = null;
		this.closureName = null;
		this.gtin = null;
		this.partnerMerchantId = null;
		this.partnerProductId = null;
		this.partnerMerchantProductId = null;
		this.minimumPrice = null;
		this.minimumPriceShopName = null;
		this.minimumPriceShopImgURL = null;
		this.minimumPriceDestinationURL = null;
		this.deleted = null;
		
	}
	
	@Override
	public String toString() 
	{
		return "{ \"wineId\" : \"" + wineId + "\", countryName\" : \"" + countryName + "\", regionName\" : \""
				+ regionName + "\", wineryName\" : \"" + wineryName + "\", appellationName\" : \"" + appellationName
				+ "\", colourName\" : \"" + colourName + "\", vintage\" : \"" + vintage + "\", name\" : \"" + name
				+ "\", defaultDescription\" : \"" + defaultDescription + "\", bottleSize\" : \"" + bottleSize
				+ "\", abv\" : \"" + abv + "\", imageURL\" : \"" + imageURL + "\", closureName\" : \"" + closureName
				+ "\", gtin\" : \"" + gtin + "\", wineTypeName\" : \"" + wineTypeName + "\", grapeVarietyName\" : \""
				+ grapeVarietyName + "\", partnerProductId\" : \"" + partnerProductId + "\", partnerMerchantId\" : \""
				+ partnerMerchantId + "\", partnerMerchantProductId\" : \"" + partnerMerchantProductId
				+ "\", minimumPrice\" : \"" + minimumPrice + "\", minimumPriceShopName\" : \"" + minimumPriceShopName
				+ "\", minimumPriceShopImgURL\" : \"" + minimumPriceShopImgURL + "\", minimumPriceDestinationURL\" : \""
				+ minimumPriceDestinationURL + "\", deleted\" : \"" + deleted + " }";
	}
		
}