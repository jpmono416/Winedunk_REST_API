package priceComparison.models;

public class viewUsersWinesReviews {

    private Integer id;
    public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
    private Integer userId;
	public Integer getUserId() { return userId; }
	public void setUserId(Integer userId) { this.userId = userId; }
	
	private Integer wineId;
	public Integer getWineId() { return wineId; }
	public void setWineId(Integer wineId) { this.wineId = wineId; }
	
	private String reviewDate;
	public String getReviewDate() { return reviewDate; }
	public void setReviewDate(String reviewDate) { this.reviewDate = reviewDate; }

	private String reviewTimestamp;
	public String getReviewTimestamp() { return reviewTimestamp; }
	public void setReviewTimestamp(String reviewTimestamp) { this.reviewTimestamp = reviewTimestamp; }

	private String reviewTitle;
	public String getReviewTitle() { return reviewTitle; }
	public void setReviewTitle(String reviewTitle) { this.reviewTitle = reviewTitle; }

	private String reviewComments;
	public String getReviewComments() { return reviewComments; }
	public void setReviewComments(String reviewComments) { this.reviewComments = reviewComments; }

	private Integer userRating;
	public Integer getUserRating() { return userRating; }
	public void setUserRating(Integer userRating) { this.userRating = userRating; }

	private String wineName;
	public String getWineName() { return wineName; }
	public void setWineName(String wineName) { this.wineName = wineName; }
	
	public viewUsersWinesReviews() 
	{
		this.id = null;
        this.userId = null;
        this.wineId = null;
        this.reviewDate = null;
        this.reviewTitle = null;
        this.reviewComments = null;
        this.userRating = null;
        this.wineName = null;
    }
}
