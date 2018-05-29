package priceComparison.models;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "viewWineReviewsAndRatingByUsers")
public class ViewWineReviewsAndRatingByUsers {
	
	@EmbeddedId
    private ViewWineReviewsAndRatingByUsersPK id;

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
    
    private String wineClicktag;
    public String getWineClicktag() { return wineClicktag; }
    public void setWineClicktag(String wineClicktag) { this.wineClicktag = wineClicktag; }
    
    private Integer ratingId;
    public Integer getRatingId() { return ratingId; }
    public void setRatingId(Integer ratingId) { this.ratingId = ratingId; }

    private Date ratingDate;
    public Date getRatingDate() { return ratingDate; }
    public void setRatingDate(Date ratingDate) { this.ratingDate = ratingDate; }

    private Date ratingTimestamp;
    public Date getRatingTimestamp() { return ratingTimestamp; }
    public void setRatingTimestamp(Date ratingTimestamp) { this.ratingTimestamp = ratingTimestamp; }
    
    private Integer ratingValue;
    public Integer getRatingValue() { return ratingValue; }
    public void setRatingValue(Integer ratingValue) { this.ratingValue = ratingValue; }
    
    private Integer reviewId;
    public Integer getReviewId() { return reviewId; }
    public void setReviewId(Integer reviewId) { this.reviewId = reviewId; }

    private Date reviewDate;
    public Date getReviewDate() { return reviewDate; }
    public void setReviewDate(Date reviewDate) { this.reviewDate = reviewDate; }

    private Date reviewTimestamp;
    public Date getReviewTimestamp() { return reviewTimestamp; }
    public void setReviewTimestamp(Date reviewTimestamp) { this.reviewTimestamp = reviewTimestamp; }
    
    private String reviewTitle;
    public String getReviewTitle() { return reviewTitle; }
    public void setReviewTitle(String reviewTitle) { this.reviewTitle = reviewTitle; }
    
    private String reviewComments;
    public String getReviewComments() { return reviewComments; }
    public void setReviewComments(String reviewComments) { this.reviewComments = reviewComments; }

}
