package priceComparison.models;

public class tblUserWineReviews {

	private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    private String addedDate;
    public String getAddedDate() { return addedDate; }
	public void setAddedDate(String addedDate) { this.addedDate = addedDate; }

	private long addedTimestamp;
    public long getAddedTimestamp() { return addedTimestamp; }
    public void setAddedTimestamp(long l) { this.addedTimestamp = l; }

    private String title;
    public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	private String comments;
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    
    private Integer numericUserId;
    public Integer getNumericUserId() { return numericUserId; } 	
    public void setNumericUserId(Integer numericUserId) { this.numericUserId = numericUserId; }

    private Integer numericWineId;
	public Integer getNumericWineId() { return numericWineId; }
	public void setNumericWineId(Integer numericWineId) { this.numericWineId = numericWineId; }
	
	
	public tblUserWineReviews()
    {
        this.id = null;
        this.addedDate = null;
        this.title = null;
        this.comments = null;
        this.numericUserId = null;
        this.numericWineId = null;
    }
	
	@Override
	public String toString() {
		return "{ \"id\" : " + id + " , \"addedDate\" : \"" + addedDate + "\" , \"addedTimestamp\" : \""
				+ addedTimestamp + "\" , \"title\" : \"" + title + "\" , \"comments\" : \"" + comments
				+ "\" , \"numericUserId\" : " + numericUserId + " , \"numericWineId\" : " + numericWineId
				+ " }";
	}
}
