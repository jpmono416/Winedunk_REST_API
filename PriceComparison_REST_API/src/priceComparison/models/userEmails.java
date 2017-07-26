package priceComparison.models;

public class userEmails {

    private Integer id;
    public Integer getId() { return this.id; }    
    public void setId(Integer id) { this.id = id; }

    private String emailAddress;
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    private Integer numericUserId;
    public Integer getNumericUserId() { return numericUserId; }
	public void setNumericUserId(Integer numericUserId) { this.numericUserId = numericUserId; }

    public userEmails(Integer id) {this.id = id;}
    public userEmails() 
    {
        this.id = null;
        this.numericUserId = null;
        this.emailAddress = null;
    }
}
