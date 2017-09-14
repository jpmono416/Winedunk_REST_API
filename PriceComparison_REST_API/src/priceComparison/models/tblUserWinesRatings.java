package priceComparison.models;

public class tblUserWinesRatings {

    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private String addedDate;
    public String getAddedDate() { return addedDate; }
    public void setAddedDate(String addedDate) { this.addedDate = addedDate; }

    private long addedTimestamp;
    public long getAddedTimestamp() { return addedTimestamp; }
    public void setAddedTimestamp(long addedTimestamp) { this.addedTimestamp = addedTimestamp; }

    private Integer rating;
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    private Integer numericUserId;
	public Integer getNumericUserId() { return numericUserId; }
	public void setNumericUserId(Integer numericUserId) { this.numericUserId = numericUserId; }

	private Integer numericWineId;
	public Integer getNumericWineId() { return numericWineId; }
	public void setNumericWineId(Integer numericWineId) { this.numericWineId = numericWineId; }
	
    public tblUserWinesRatings()
    {
        this.id = null;
        this.addedDate = null;
        this.rating = null;
        this.numericUserId = null;
        this.numericWineId = null;
    }
    
    @Override
	public String toString() {
		return "{ \"id\" : \"" + id + "\" , \"addedDate\" : \"" + addedDate + "\" , \"addedTimestamp\" : \""
				+ addedTimestamp + "\" , \"rating\" : \"" + rating + "\" , \"numericUserId\" : \"" + numericUserId
				+ "\" , \"numericWineId\" : \"" + numericWineId + "\" }";
	}
}
