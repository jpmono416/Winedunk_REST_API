package priceComparison.models;

public class tblUserSavedSearches {

    private Integer id;
    public Integer getId() { 	return id; }
	public void setId(Integer id) { this.id = id; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private Integer numericUserId;
    public Integer getNumericUserId() { return numericUserId; }
	public void setNumericUserId(Integer numericUserId) { this.numericUserId = numericUserId; }

	private String created;
    public String getCreated() { return created; }
    public void setCreated(String created) { this.created = created; }

    private String urlString;
    public String getUrlString() { return urlString; }
	public void setUrlString(String urlString) { this.urlString = urlString; }
	
	public tblUserSavedSearches()
    {
        this.id = null;
        this.numericUserId = null;
        this.name = null;
        this.created = null;
        this.urlString = null;
    }
	
    @Override
	public String toString() {
		return "{ \"id\" : \"" + id + "\" , \"name\" : \"" + name + "\" , \"numericUserId\" : \"" + numericUserId
				+ "\" , \"created\" : \"" + created + "\" , \"urlString\" : \"" + urlString + "\" }";
	}
    
}
