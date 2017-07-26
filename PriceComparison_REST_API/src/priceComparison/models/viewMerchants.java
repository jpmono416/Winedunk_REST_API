package priceComparison.models;

import java.util.List;

public class viewMerchants {

	private Integer id;
    public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	private String imageURL;
	public String getImageURL() { return imageURL; }
	public void setImageURL(String imageURL) { this.imageURL = imageURL; }
	
	private String homePage;
	public String getHomePage() { return homePage; }
	public void setHomePage(String homePage) { this.homePage = homePage; }
	
	private String genericProductPage;
	public String getGenericProductPage() { return genericProductPage; }
	public void setGenericProductPage(String genericProductPage) { this.genericProductPage = genericProductPage; }
	
	private Boolean deleted;
	public Boolean getDeleted() { return deleted; }
	public void setDeleted(Boolean deleted) { this.deleted = deleted; }
	
	public List<viewBestOffersbyMerchants> bestOffers;
	public List<viewBestOffersbyMerchants> getBestOffers() { return bestOffers; }
	public void setBestOffers(List<viewBestOffersbyMerchants> bestOffers) { 	this.bestOffers = bestOffers; }
	
	public viewMerchants(Integer id) { this.id = id; } 
	public viewMerchants() 
	{
        this.id = null;
        this.name = null;
        this.homePage = null;
        this.imageURL = null;
        this.genericProductPage = null;
        this.deleted = null;
        this.bestOffers = null;
    }
	
	@Override
	public String toString() {
		return "{ \"id\" : \"" + id + "\", name\" : \"" + name + "\", imageURL\" : \"" + imageURL
				+ "\", homePage\" : \"" + homePage + "\", genericProductPage\" : \"" + genericProductPage
				+ "\", deleted\" : \"" + deleted + "\", bestOffers\" : \"" + bestOffers + " }";
	}
}
