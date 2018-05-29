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
@Table(name = "tblUserEmails")
public class tblUserEmails {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }


	// aripe length attribute added
	@Column(name= "emailAddress", length = 200, nullable = false)
    @NotNull
    private String emailAddress;
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    @ManyToOne
    @JoinColumn(name = "userId")
    private tblUsers userId;
    public tblUsers getUserId() { return userId; }
    public void setUserId(tblUsers userId) { this.userId = userId; }
    
    @Column(name = "numericUserId")
    private Integer numericUserId;
    public Integer getNumericUserId() { return numericUserId; }
	public void setNumericUserId(Integer numericUserId) { this.numericUserId = numericUserId; }

	public tblUserEmails(Integer id) {this.id = id;}
    public tblUserEmails() 
    {
        this.id = null;
        this.userId = null;
        this.emailAddress = null;
        this.numericUserId = null;
    }
}
