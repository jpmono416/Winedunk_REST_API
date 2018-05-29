package priceComparison.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import priceComparison.models.tblUsers;
import priceComparison.models.tblWines;

public class tblUserWineReviews {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @ManyToOne
    @JoinColumn(name = "userId")
    private tblUsers userId;
    public tblUsers getUserId() { return userId; }
    public void setUserId(tblUsers userId) { this.userId = userId; }

    @ManyToOne
    @JoinColumn(name = "wineId")
    private tblWines wineId;
    public tblWines getWineId() { return wineId; }
    public void setWineId(tblWines wineId) { this.wineId = wineId; }

    @Column(name = "addedDate")
    @Temporal(TemporalType.DATE)
    private Date addedDate;
    public Date getAddedDate() { return addedDate; }
    public void setAddedDate(Date addedDate) { this.addedDate = addedDate; }

    @Column(name = "addedTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedTimestamp;
    public Date getAddedTimestamp() { return addedTimestamp; }
    public void setAddedTimestamp(Date addedTimestamp) { this.addedTimestamp = addedTimestamp; }

    // aripe the whole `title` attribute was missing
    @Column(name= "title", length = 100)
    private String title;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    // aripe length attribute added
    @Column(name= "comments", length = 1000)
    private String comments;
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
	
    public tblUserWineReviews(tblUsers id) {this.userId = id;}
    public tblUserWineReviews()
    {
        this.id = null;
        this.userId = null;
        this.wineId = null;
        this.addedDate = null;
        this.addedTimestamp = null;
        this.comments = null;
    }
    
	@Override
	public String toString() {
		return "{ \"id\" : " + id + " , \"userId\" : " + userId + " , \"wineId\" : " + wineId
				+ " , \"addedDate\" : \"" + addedDate + "\" , \"addedTimestamp\" : \"" + addedTimestamp
				+ "\" , \"title\" : \"" + title + "\" , \"comments\" : \"" + comments + "\" }";
	}
}
