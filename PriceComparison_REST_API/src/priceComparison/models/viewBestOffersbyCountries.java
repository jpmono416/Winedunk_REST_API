package priceComparison.models;

import javax.persistence.Column;

public class viewBestOffersbyCountries extends Object {

    private Integer id;
    public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	private Integer countryId;
	public Integer getCountryId() { return countryId; }
	public void setCountryId(Integer countryId) { this.countryId = countryId; }
	
	private Integer wineId;
	public Integer getWineId() { return wineId; }
	public void setWineId(Integer wineId) { this.wineId = wineId; }

	private Integer positionIndex;
	public Integer getPositionIndex() { return positionIndex; }
	public void setPositionIndex(Integer positionIndex) { this.positionIndex = positionIndex; }
	
	private String countryName;
	public String getCountryName() { return countryName; }
	public void setCountryName(String countryName) { this.countryName = countryName; }
	
	private String countryIsoAlpha2Code;
	public String getCountryIsoAlpha2Code() { return countryIsoAlpha2Code; }
	public void setCountryIsoAlpha2Code(String countryIsoAlpha2Code) { this.countryIsoAlpha2Code = countryIsoAlpha2Code; }
	
	private String countryIsoAlpha3Code;
	public String getCountryIsoAlpha3Code() { return countryIsoAlpha3Code; }
	public void setCountryIsoAlpha3Code(String countryIsoAlpha3Code) { this.countryIsoAlpha3Code = countryIsoAlpha3Code; }

	private Integer wineCountryId;
	public Integer getWineCountryId() { return wineCountryId; }
	public void setWineCountryId(Integer wineCountryId) { this.wineCountryId = wineCountryId; }
	
    private String wineCountryName;
    public String getWineCountryName() { return wineCountryName; }
	public void setWineCountryName(String wineCountryName) { this.wineCountryName = wineCountryName; }
	
    private Integer wineRegionId;
    public Integer getWineRegionId() { return wineRegionId; }
	public void setWineRegionId(Integer wineRegionId) { this.wineRegionId = wineRegionId; }
	
    private String wineRegionName;
    public String getWineRegionName() { return wineRegionName; }
	public void setWineRegionName(String wineRegionName) { this.wineRegionName = wineRegionName; }
	
    private Integer wineWineryId;
    public Integer getWineWineryId() { return wineWineryId; }
	public void setWineWineryId(Integer wineWineryId) { this.wineWineryId = wineWineryId; }
	
    private String wineWineryName;
    public String getWineWineryName() { return wineWineryName; }
	public void setWineWineryName(String wineWineryName) { this.wineWineryName = wineWineryName; }
	
    private Integer wineAppellationId;
    public Integer getWineAppellationId() { return wineAppellationId; }
	public void setWineAppellationId(Integer wineAppellationId) { this.wineAppellationId = wineAppellationId; }
	
    private String wineAppellationName;
    public String getWineAppellationName() { return wineAppellationName; }
	public void setWineAppellationName(String wineAppellationName) { this.wineAppellationName = wineAppellationName; }
	
    private Integer wineColourId;
    public Integer getWineColourId() { return wineColourId; }
	public void setWineColourId(Integer wineColourId) { this.wineColourId = wineColourId; }
	
    private String wineColourName;
    public String getWineColourName() { return wineColourName; }
	public void setWineColourName(String wineColourName) { this.wineColourName = wineColourName; }
	
    private Integer wineVintage;
    public Integer getWineVintage() { return wineVintage; }
	public void setWineVintage(Integer wineVintage) { this.wineVintage = wineVintage; }
	
    private String wineName;
    public String getWineName() { return wineName; }
	public void setWineName(String wineName) { this.wineName = wineName; }
	
	private String wineShortDescription;
	public String getWineShortDescription() { return wineShortDescription; }
	public void setWineShortDescription(String wineShortDescription) { this.wineShortDescription = wineShortDescription; }
	
    private String wineDefaultDescription;
    public String getWineDefaultDescription() { return wineDefaultDescription; }
	public void setWineDefaultDescription(String wineDefaultDescription) { this.wineDefaultDescription = wineDefaultDescription; }
	
    private Integer wineBottleSize;
    public Integer getWineBottleSize() { return wineBottleSize; }
	public void setWineBottleSize(Integer wineBottleSize) { this.wineBottleSize = wineBottleSize; }
	
    private Float wineAbv;
    public Float getWineAbv() { return wineAbv; }
	public void setWineAbv(Float wineAbv) { this.wineAbv = wineAbv; }
	
    private String wineImageURL;
    public String getWineImageURL() { return wineImageURL; }
	public void setWineImageURL(String wineImageURL) { this.wineImageURL = wineImageURL; }
	
    private Integer wineClosureId;
    public Integer getWineClosureId() { return wineClosureId; }
	public void setWineClosureId(Integer wineClosureId) { this.wineClosureId = wineClosureId; }
	
    private String wineClosureName;
    public String getWineClosureName() { return wineClosureName; }
	public void setWineClosureName(String wineClosureName) { this.wineClosureName = wineClosureName; }
	
    private String wineGtin;
    public String getWineGtin() { return wineGtin; }
	public void setWineGtin(String wineGtin) { this.wineGtin = wineGtin; }
	
	private Float wineMinimumPrice;
	public Float getWineMinimumPrice() { return wineMinimumPrice; }
	public void setWineMinimumPrice(Float wineMinimumPrice) { this.wineMinimumPrice = wineMinimumPrice; }
	
	private Float winePreviousMaxPrice;
	public Float getWinePreviousMaxPrice() { return winePreviousMaxPrice; }
	public void setWinePreviousMaxPrice(Float winePreviousMaxPrice) { this.winePreviousMaxPrice = winePreviousMaxPrice; }
	
	private Float wineMoneySaving;
	public Float getWineMoneySaving() { return wineMoneySaving; }
	public void setWineMoneySaving(Float wineMoneySaving) { this.wineMoneySaving = wineMoneySaving; }
	
	private Integer winePercentageOff;
	public Integer getWinePercentageOff() { return winePercentageOff; }
	public void setWinePercentageOff(Integer winePercentageOff) { this.winePercentageOff = winePercentageOff; }
	
	private String wineMinimumPriceClicktag;
	public String getWineMinimumPriceClicktag() { return wineMinimumPriceClicktag; }
	public void setWineMinimumPriceClicktag(String wineMinimumPriceClicktag) { this.wineMinimumPriceClicktag = wineMinimumPriceClicktag; }
	
    private Boolean wineDeleted;
	public Boolean getWineDeleted() { return wineDeleted; }
	public void setWineDeleted(Boolean wineDeleted) { this.wineDeleted = wineDeleted; }
	
	
	public viewBestOffersbyCountries(Integer id) { this.id = id; } 
	public viewBestOffersbyCountries() 
	{
        this.id = null;
        this.countryId = null;
        this.countryName = null;
        this.countryIsoAlpha2Code = null;
        this.countryIsoAlpha3Code = null;
    	this.wineId = null;
    	this.positionIndex = null;
        this.wineCountryId = null; 
        this.wineCountryName = null;
        this.wineRegionId = null;
        this.wineRegionName = null;
        this.wineWineryId = null;
        this.wineWineryName = null;
        this.wineAppellationId = null;
        this.wineAppellationName = null;
        this.wineColourId = null;
        this.wineColourName = null;
        this.wineVintage = null;
        this.wineName = null;
        this.wineShortDescription = null;
        this.wineDefaultDescription = null;
        this.wineBottleSize = null;
        this.wineAbv = null;
        this.wineImageURL = null;
        this.wineClosureId = null;
        this.wineClosureName = null;
        this.wineGtin = null;
        this.wineMinimumPrice = null;
        this.winePreviousMaxPrice = null;
        this.wineMoneySaving = null;
        this.winePercentageOff = null;
        this.wineMinimumPriceClicktag = null;
        this.wineDeleted = false;
    }
	
	@Override
	public String toString() {
		return "{ \"id\" : \"" + id + "\" , \"countryId\" : \"" + countryId + "\" , \"wineId\" : \"" + wineId
				+ "\" , \"positionIndex\" : \"" + positionIndex + "\" , \"countryName\" : \"" + countryName
				+ "\" , \"countryIsoAlpha2Code\" : \"" + countryIsoAlpha2Code + "\" , \"countryIsoAlpha3Code\" : \""
				+ countryIsoAlpha3Code + "\" , \"wineCountryId\" : \"" + wineCountryId + "\" , \"wineCountryName\" : \""
				+ wineCountryName + "\" , \"wineRegionId\" : \"" + wineRegionId + "\" , \"wineRegionName\" : \""
				+ wineRegionName + "\" , \"wineWineryId\" : \"" + wineWineryId + "\" , \"wineWineryName\" : \""
				+ wineWineryName + "\" , \"wineAppellationId\" : \"" + wineAppellationId
				+ "\" , \"wineAppellationName\" : \"" + wineAppellationName + "\" , \"wineColourId\" : \""
				+ wineColourId + "\" , \"wineColourName\" : \"" + wineColourName + "\" , \"wineVintage\" : \""
				+ wineVintage + "\" , \"wineName\" : \"" + wineName + "\" , \"wineShortDescription\" : \""
				+ wineShortDescription + "\" , \"wineDefaultDescription\" : \"" + wineDefaultDescription
				+ "\" , \"wineBottleSize\" : \"" + wineBottleSize + "\" , \"wineAbv\" : \"" + wineAbv
				+ "\" , \"wineImageURL\" : \"" + wineImageURL + "\" , \"wineClosureId\" : \"" + wineClosureId
				+ "\" , \"wineClosureName\" : \"" + wineClosureName + "\" , \"wineGtin\" : \"" + wineGtin
				+ "\" , \"wineMinimumPrice\" : \"" + wineMinimumPrice
				+ "\" , \"winePreviousMaxPrice\" : \"" + winePreviousMaxPrice
				+ "\" , \"wineMoneySaving\" : \"" + wineMoneySaving
				+ "\" , \"winePercentageOff\" : \"" + winePercentageOff
				+ "\" , \"wineMinimumPriceClicktag\" : \"" + wineMinimumPriceClicktag
				+ "\" , \"wineDeleted\" : \"" + wineDeleted
				+ "\" }";
	}
}
