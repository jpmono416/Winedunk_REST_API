package priceComparison.models;

import java.util.Date;


public class viewUserWinesRatings {

    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private Integer userId;
    public Integer getUserId() { return userId; }
	public void setUserId(Integer userId) { this.userId = userId; }
	
    private Integer wineId;
    public Integer getWineId() { return wineId; }
	public void setWineId(Integer wineId) { this.wineId = wineId; }
	
    private String wineName;
    public String getWineName() { return wineName; }
	public void setWineName(String wineName) { this.wineName = wineName; }
	
    private String wineImageURL;
    public String getWineImageURL() { return wineImageURL; }
	public void setWineImageURL(String wineImageURL) { this.wineImageURL = wineImageURL; }

    private Date addedDate;
    public Date getAddedDate() { return addedDate; }
    public void setAddedDate(Date addedDate) { this.addedDate = addedDate; }

    private Date addedTimestamp;
    public Date getAddedTimestamp() { return addedTimestamp; }
    public void setAddedTimestamp(Date addedTimestamp) { this.addedTimestamp = addedTimestamp; }

    private Integer userRating;
    public Integer getUserRating() { return userRating; }
	public void setUserRating(Integer userRating) { this.userRating = userRating; }
	
	private Integer numericUserId;
    public Integer getNumericUserId() { return numericUserId; } 
    public void setNumericUserId(Integer numericUserId) { this.numericUserId = numericUserId; }

    public viewUserWinesRatings()
    {
        this.id = null;
        this.wineId = null;
        this.wineImageURL = null;
        this.wineName = null;
        this.userId = null;
        this.wineId = null;
        this.addedDate = null;
        this.addedTimestamp = null;
        this.userRating = null;
        this.numericUserId = null;
    }
    
	@Override
	public String toString() {
		return "{ \"id\" : \"" + id + "\" , \"userId\" : \"" + userId + "\" , \"wineId\" : \"" + wineId
				+ "\" , \"addedDate\" : \"" + addedDate + "\" , \"addedTimestamp\" : \"" + addedTimestamp
				+ "\" , \"userRating\" : \"" + userRating + "\" }";
	}
}