package priceComparison.models;

public class userPhoneNumbers {

    private Integer id;
    public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

    private String userPhoneNumber;
    public String getPhoneNumber() { return userPhoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.userPhoneNumber = phoneNumber; }

    private Integer numericUserId;
    public Integer getNumericUserId() { return numericUserId; }
	public void setNumericUserId(Integer numericUserId) { this.numericUserId = numericUserId; }

    public userPhoneNumbers() 
    {
        this.id = null;
        this.userPhoneNumber = null;
        this.numericUserId = null;
    }
}