package priceComparison.models;

import java.util.Date;

public class tblUserFavouriteWines {

	
	private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private Date addedDate;
    public Date getAddedDate() { return addedDate; }
    public void setAddedDate(Date addedDate) { this.addedDate = addedDate; }

    private Date addedTimestamp;
    public Date getAddedTimestamp() { return addedTimestamp; }
    public void setAddedTimestamp(Date addedTimestamp) { this.addedTimestamp = addedTimestamp; }
    
    private Integer numericUserId;
    public Integer getNumericUserId() { return numericUserId; }
	public void setNumericUserId(Integer numericUserId) { this.numericUserId = numericUserId; }
	
	private Integer numericWineId;
	public Integer getNumericWineId() { return numericWineId; }
	public void setNumericWineId(Integer numericWineId) { this.numericWineId = numericWineId; }


    public tblUserFavouriteWines()
    {
        this.id = null;
        this.addedDate = null;
        this.addedTimestamp = null;
        this.numericUserId = null;
        this.numericWineId = null;
    }
}
