package priceComparison.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "tblUserPhoneNumbers")
public class tblUserPhoneNumbers {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	
	// aripe length attribute added
	@Column(name= "userPhoneNumber", length = 20, nullable = false)
    @NotNull
    private String userPhoneNumber;
    public String getPhoneNumber() { return userPhoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.userPhoneNumber = phoneNumber; }

    @ManyToOne
    @JoinColumn(name = "userId")
    private tblUsers userId;
    public tblUsers getUserId() { return userId; }
    public void setUserId(tblUsers user) { this.userId = user; }
    
    @Transient
    private Integer numericUserId;
    public Integer getNumericUserId() { return numericUserId; }
	public void setNumericUserId(Integer numericUserId) { this.numericUserId = numericUserId; }

    public tblUserPhoneNumbers() 
    {
        this.id = null;
        this.userPhoneNumber = null;
        this.userId = null;
        this.numericUserId = null;
    }
}
