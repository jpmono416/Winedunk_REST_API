package priceComparison.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name = "tblUsers_Wines_Viewed")
public class tblUserWinesViewed {

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

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public tblUserWinesViewed(tblUsers id) {this.userId = id;}
    public tblUserWinesViewed()
    {
        this.id = null;
        this.userId = null;
        this.wineId = null;
        this.date = null;
        this.timestamp = null;
    }
	@Override
	public String toString() {
		return "{ \"id\" : \"" + id + "\" , \"userId\" : \"" + userId + "\" , \"wineId\" : \"" + wineId
				+ "\" , \"date\" : \"" + date + "\" , \"timestamp\" : \"" + timestamp + "\" }";
	}
    
    
}
