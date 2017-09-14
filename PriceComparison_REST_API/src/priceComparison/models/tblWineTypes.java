package priceComparison.models;

import java.util.List;


public class tblWineTypes {

    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private Boolean deleted;
    public Boolean isDeleted() {return deleted;}
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

	private List<viewBestOffersbyWineTypes> bestOffersByType;
    public List<viewBestOffersbyWineTypes> getBestOffersByType() { return bestOffersByType; }
	public void setBestOffersByType(List<viewBestOffersbyWineTypes> bestOffersByType) { this.bestOffersByType = bestOffersByType; }
	
	public tblWineTypes(Integer id) { this.id = id; }
    public tblWineTypes()
    {
        this.id = null;
        this.name = null;
        this.deleted = null;
    }
    public tblWineTypes(String name) {
        this.name = name;
    }
}