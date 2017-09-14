package priceComparison.models;

import java.util.List;

public class viewMerchantsWithBestOffers extends Object {

    private Integer id;
    public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
    private String name;
    public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	private String merchantImageURL;
	public String getMerchantImageURL() { return merchantImageURL; }
	public void setMerchantImageURL(String merchantImageURL) { this.merchantImageURL = merchantImageURL; }
	
	public List<viewBestOffersbyMerchants> bestOffers;
	public List<viewBestOffersbyMerchants> getBestOffers() { return bestOffers; }
	public void setBestOffers(List<viewBestOffersbyMerchants> bestOffers) { this.bestOffers = bestOffers; }
	
	public viewMerchantsWithBestOffers() 
	{
		this.id = null;
        this.name = null;
        this.merchantImageURL = null;
        this.bestOffers = null;
    }
}
