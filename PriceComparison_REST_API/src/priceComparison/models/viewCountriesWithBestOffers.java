package priceComparison.models;

import java.util.List;

public class viewCountriesWithBestOffers extends Object {

    private Integer id;
    public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
    private String countryName;
	public String getCountryName() { return countryName; }
	public void setCountryName(String countryName) { this.countryName = countryName; }
	
	public List<viewBestOffersbyCountries> bestOffers;
	public List<viewBestOffersbyCountries> getBestOffers() { return bestOffers; }
	public void setBestOffers(List<viewBestOffersbyCountries> bestOffers) { this.bestOffers = bestOffers; }
	
	public viewCountriesWithBestOffers() 
	{
		this.id = null;
        this.countryName = null;
        this.bestOffers = null;
    }
	@Override
	public String toString() {
		return "{ \"id\" : \"" + id + "\" , \"countryName\" : \"" + countryName + "\" , \"bestOffers\" : \""
				+ bestOffers + "\" }";
	}
}
