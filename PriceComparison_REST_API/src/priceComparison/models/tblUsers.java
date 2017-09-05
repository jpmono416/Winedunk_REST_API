package priceComparison.models;

import java.util.Date;


public class tblUsers {

   
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }


    
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private String preferredEmail;
    public String getPreferredEmail() { return preferredEmail; }
	public void setPreferredEmail(String preferredEmail) { this.preferredEmail = preferredEmail; }
	
    private String preferredPhoneNumber;
    public String getPreferredPhoneNumber() { return preferredPhoneNumber; }
	public void setPreferredPhoneNumber(String preferredPhoneNumber) { this.preferredPhoneNumber = preferredPhoneNumber; }

    private Date DoB;
    public Date getDoB() { return DoB; }
    public void setDoB(Date doB) { DoB = doB; }

    private String loginEmail;
    public String getLoginEmail() { return loginEmail; }
	public void setLoginEmail(String loginEmail) { this.loginEmail = loginEmail; }
	
	private String loginPassword;
	public String getLoginPassword() { return loginPassword; }
	public void setLoginPassword(String loginPassword) { this.loginPassword = loginPassword; }

	private String loginToken;
	public String getLoginToken() { return loginToken; }
	public void setLoginToken(String loginToken) { this.loginToken = loginToken; }
	
    private String recoveringPassEmail;
    public String getRecoveringPassEmail() { return recoveringPassEmail; }
    public void setRecoveringPassEmail(String recoveringPassEmail) { this.recoveringPassEmail = recoveringPassEmail; }

    private String recoveringPassToken;
    public String getRecoveringPassToken() { return recoveringPassToken; }
	public void setRecoveringPassToken(String recoveringPassToken) { this.recoveringPassToken = recoveringPassToken; }
	
	private Boolean isAdmin;
	public Boolean getIsAdmin() { return isAdmin; }
	public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }

    private Boolean deleted;
    public Boolean isDeleted() {return deleted;}
    public void setDeleted(Boolean deleted) {this.deleted = deleted;}

    public tblUsers()
    {
        this.id = null;
        this.name = null;
        this.preferredEmail = null;
        this.preferredPhoneNumber = null;
        this.DoB = null;
        this.recoveringPassEmail = null;
        this.isAdmin = false;
        this.deleted = false;
        this.loginEmail = null;
        this.loginPassword = null;
        this.loginToken = null;
        this.recoveringPassToken = null;
    }
    public tblUsers(Integer id) { this.id = id; }
	
    @Override
	public String toString() {
		return "tblUsers { \"id\" : \"" + id + "\" , name\" : \"" + name + "\" , preferredEmail\" : \"" + preferredEmail
				+ "\" , preferredPhoneNumber\" : \"" + preferredPhoneNumber + "\" , DoB\" : \"" + DoB
				+ "\" , loginEmail\" : \"" + loginEmail + "\" , loginPassword\" : \"" + loginPassword
				+ "\" , loginToken\" : \"" + loginToken + "\" , recoveringPassEmail\" : \"" + recoveringPassEmail
				+ "\" , recoveringPassToken\" : \"" + recoveringPassToken + "\" , isAdmin\" : \"" + isAdmin
				+ "\" , deleted\" : \"" + deleted + " }";
	}
}
