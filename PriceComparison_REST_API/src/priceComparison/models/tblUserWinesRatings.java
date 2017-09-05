package priceComparison.models;

public class tblUserWinesRatings {

    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private Integer userId;
    public Integer getUserId() { return userId; }
	public void setUserId(Integer userId) { this.userId = userId; }
	
	private Integer wineId;
	public Integer getWineId() { return wineId; }
	public void setWineId(Integer wineId) { this.wineId = wineId; }

    private String addedDate;
    public String getAddedDate() { return addedDate; }
    public void setAddedDate(String addedDate) { this.addedDate = addedDate; }

    private long addedTimestamp;
    public long getAddedTimestamp() { return addedTimestamp; }
    public void setAddedTimestamp(long addedTimestamp) { this.addedTimestamp = addedTimestamp; }

    private Integer rating;
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public tblUserWinesRatings()
    {
        this.id = null;
        this.userId = null;
        this.wineId = null;
        this.addedDate = null;
        this.rating = null;
    }
	@Override
	public String toString() {
		return "{ \"id\" : " + id + " , \"userId\" : " + userId + " , \"wineId\" : "
				+ wineId + " , \"addedDate\" : \"" + addedDate + "\" , \"addedTimestamp\" : \"" + addedTimestamp
				+ "\" , \"rating\" : \"" + rating + "\" }";
	}
    
}
