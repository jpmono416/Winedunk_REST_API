package priceComparison.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ViewWineReviewsAndRatingByUsersPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "userId")
    private Integer userId;
	public Integer getUserId() { return this.userId; }
	public void setUserId(Integer userId) { this.userId = userId; }
	
	@Column(name = "wineId")
    private Integer wineId;
	public Integer getWineId() { return this.wineId; }
	public void setWineId(Integer wineId) { this.wineId = wineId; }

	public ViewWineReviewsAndRatingByUsersPK() {
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ViewWineReviewsAndRatingByUsersPK)) {
			return false;
		}
		
		ViewWineReviewsAndRatingByUsersPK castOther = (ViewWineReviewsAndRatingByUsersPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& (this.userId == castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.wineId.hashCode();
		
		return hash;
	}

}
