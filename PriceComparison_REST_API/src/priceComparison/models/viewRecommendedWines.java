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
        this.destinationURL = null;
    }
	@Override
	public String toString() {
		return "{ \"id\" : \"" + id + "\", name\" : \"" + name + "\", defaultDescription\" : \"" + defaultDescription
				+ "\", imageURL\" : \"" + imageURL + "\", minimumPrice\" : \"" + minimumPrice
				+ "\", destinationURL\" : \"" + destinationURL + "] }";
	}
	
}