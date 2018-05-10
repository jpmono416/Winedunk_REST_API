package priceComparison.models;

public class viewRecommendedWines {

    private Integer id;
    public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
    private String name;
    public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	private String shortDescription;
	public String getShortDescription() { return shortDescription; }
	public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
	
	private String defaultDescription;
	public String getDefaultDescription() { return defaultDescription; }
	public void setDefaultDescription(String defaultDescription) { this.defaultDescription = defaultDescription; }
	
	private String imageURL;
	public String getImageURL() { return imageURL; }
	public void setImageURL(String imageURL) {this.imageURL = imageURL; }
	
	private Float minimumPrice;
	public Float getMinimumPrice() { return minimumPrice; }
	public void setMinimumPrice(Float minimumPrice) { this.minimumPrice = minimumPrice; }
	
	private Float previousMaxPrice;
	public Float getPreviousMaxPrice() { return previousMaxPrice; }
	public void setPreviousMaxPrice(Float previousMaxPrice) { this.previousMaxPrice = previousMaxPrice; }
	
	private Float saving;
	public Float getSaving() { return saving; }
	public void setSaving(Float saving) { this.saving = saving; }
	
	private Integer percentageOff;
	public Integer getPercentageOff() { return percentageOff; }
	public void setPercentageOff(Integer percentageOff) { this.percentageOff = percentageOff; }
	
	private String  minimumPriceClicktag;
	public String  getMinimumPriceClicktag() { return minimumPriceClicktag; }
	public void setMinimumPriceClicktag(String  minimumPriceClicktag) { this.minimumPriceClicktag = minimumPriceClicktag; }
	
	private String destinationURL;
	public String getDestinationURL() { return destinationURL; }
	public void setDestinationURL(String destinationURL) { this.destinationURL = destinationURL; }
	
	
	public viewRecommendedWines() 
	{
		this.id = null;
        this.name = null;
        this.defaultDescription = null;
        this.imageURL = null;
        this.minimumPrice = null;
        this.previousMaxPrice = null;
        this.saving = null;
        this.percentageOff = null;
        this.minimumPriceClicktag = null;
        this.destinationURL = null;
    }
	@Override
	public String toString() {
		return "{ \"id\" : \"" + id + "\", name\" : \"" + name + "\", defaultDescription\" : \"" + defaultDescription
				+ "\", imageURL\" : \"" + imageURL + "\", minimumPrice\" : \"" + minimumPrice
				+ "\", previousMaxPrice\" : \"" + previousMaxPrice 
				+ "\", saving\" : \"" + saving 
				+ "\", percentageOff\" : \"" + percentageOff 
				+ "\", minimumPriceClicktag\" : \"" + minimumPriceClicktag 
				+ "\", destinationURL\" : \"" + destinationURL + "] }";
	}
	
}