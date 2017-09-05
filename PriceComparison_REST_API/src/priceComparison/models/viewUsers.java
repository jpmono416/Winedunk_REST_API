package priceComparison.models;

import java.util.Date;

public class viewUsers {

	private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private Integer countryId;
    public Integer getCountryId() { return countryId; }
	public void setCountryId(Integer countryId) { this.countryId = countryId; }

	private String countryShortName;
	public String getCountryShortName() { return countryShortName; }
	public void setCountryShortName(String countryShortName) { this.countryShortName = countryShortName; }
	
	private String countryName;
	public String getCountryName() { return countryName; }
	public void setCountryName(String countryName) { this.countryName = countryName; }
	
    private Integer preferredCurrencyId;
    public Integer getPreferredCurrencyId() { return preferredCurrencyId; }
    public void setPreferredCurrencyId(Integer preferredCurrencyId) { this.preferredCurrencyId = preferredCurrencyId; }
	
    private String preferredCurrencyName;
    public String getPreferredCurrencyName() { return preferredCurrencyName; }
	public void setPreferredCurrencyName(String preferredCurrencyName) { this.preferredCurrencyName = preferredCurrencyName; }
	
    private Integer preferredTimeZoneId;
    public Integer getPreferredTimeZoneId() { return preferredTimeZoneId; }
    public void setPreferredTimeZoneId(Integer preferredTimeZoneId) { this.preferredTimeZoneId = preferredTimeZoneId; }

    private String preferredTimeZoneShortName;
    public String getPreferredTimeZoneShortName() { return preferredTimeZoneShortName; }
	public void setPreferredTimeZoneShortName(String preferredTimeZoneShortName) { this.preferredTimeZoneShortName = preferredTimeZoneShortName; }
	
    private String preferredTimeZoneName;
    public String getPreferredTimeZoneName() { return preferredTimeZoneName; }
	public void setPreferredTimeZoneName(String preferredTimeZoneName) { this.preferredTimeZoneName = preferredTimeZoneName; }
	
    private String preferredTimeZoneUtcOffset;
    public String getPreferredTimeZoneUtcOffset() { return preferredTimeZoneUtcOffset; }
	public void setPreferredTimeZoneUtcOffset(String preferredTimeZoneUtcOffset) { this.preferredTimeZoneUtcOffset = preferredTimeZoneUtcOffset; }
	
    private Integer preferredLanguageId;
    public Integer getPreferredLanguageId() { return preferredLanguageId; }
    public void setPreferredLanguageId(Integer preferredLanguageId) { this.preferredLanguageId = preferredLanguageId; }
    
    private String preferredLanguageShortName;
    public String getPreferredLanguageShortName() { return preferredLanguageShortName; }
	public void setPreferredLanguageShortName(String preferredLanguageShortName) { this.preferredLanguageShortName = preferredLanguageShortName; }
	
    private String preferredLanguageName;
    public String getPreferredLanguageName() { return preferredLanguageName; }
	public void setPreferredLanguageName(String preferredLanguageName) { this.preferredLanguageName = preferredLanguageName; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    private String phoneNumber;
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

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

    public viewUsers(Integer id) {this.id = id;}
    public viewUsers()
    {
        this.id = null;
        this.countryId = null;
        this.countryName = null;
        this.countryShortName = null;
        this.preferredCurrencyId = null;
        this.preferredCurrencyName = null;
        this.preferredTimeZoneId = null;
        this.preferredTimeZoneName = null;
        this.preferredTimeZoneShortName = null;
        this.preferredTimeZoneUtcOffset = null;
        this.preferredLanguageId = null;
        this.preferredLanguageName = null;
        this.preferredLanguageShortName = null;
        this.name = null;
        this.email = null;
        this.phoneNumber = null;
        this.DoB = null;
        this.recoveringPassEmail = null;
        this.deleted = false;
        this.loginEmail = null;
        this.loginPassword = null;
        this.loginToken = null;
        this.recoveringPassToken = null;
        this.isAdmin = null;
    }
}
