package priceComparison.models;

import java.util.List;

public class viewWineTypesWithBestOffers extends Object {

	private Integer id;
    public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
    private String wineTypeName;
	public String getWineTypeName() { return wineTypeName; }
	public void setWineTypeName(String wineTypeName) { this.wineTypeName = wineTypeName; }
	
	public List<viewBestOffersbyWineTypes> bestOffers;
	public List<viewBestOffersbyWineTypes> getBestOffers() { return bestOffers; }
	public void setBestOffers(List<viewBestOffersbyWineTypes> bestOffers) { this.bestOffers = bestOffers; }
	
	public viewWineTypesWithBestOffers() 
	{
		this.id = null;
        this.wineTypeName = null;
        this.bestOffers = null;
    }
	@Override
	public String toString() {
		return "{ \"id\" : \"" + id + "\" , \"wineTypeName\" : \"" + wineTypeName + "\" , \"bestOffers\" : \""
				+ bestOffers + "\" }";
	}
}
