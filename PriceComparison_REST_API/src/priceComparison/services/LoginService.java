package priceComparison.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import priceComparison.models.viewUsers;


public class LoginService {

	/*
	 * This class is used for logging the user in and registering. It has 2 main methods
	 * Which are for the sated functions. There are a series of parameters, which reference
	 * The possible fields of the forms to be submitted.
	 */
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }

	private String email;
   	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	private String repeatEmail;
    public String getRepeatEmail() { return repeatEmail; }
	public void setRepeatEmail(String repeatEmail) { this.repeatEmail = repeatEmail; }
	
    private String password;
    public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
    
	private String repeatPassword;
	public String getRepeatPassword() { return repeatPassword; }
	public void setRepeatPassword(String repeatPassword) { this.repeatPassword = repeatPassword; }

	private Boolean rememberMe = false;
	public Boolean getRememberMe() { return rememberMe; }
	public void setRememberMe(Boolean rememberMe) { this.rememberMe = rememberMe; }
	
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	private String surname;
	public String getSurname() { return surname; }
	public void setSurname(String surname) { this.surname = surname; }
	
	private String phoneNumber;
	public String getPhoneNumber() { return phoneNumber; }
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
	private String day;
	public String getDay() { return day; }
	public void setDay(String day) { this.day = day; }
    
	private String month;
	public String getMonth() { return month; }
	public void setMonth(String month) { this.month = month; }
    
	private String year;
	public String getYear() { return year; }
	public void setYear(String year) { this.year = year; }
	
	private String date;
	public String getDate() { return date; }
	public void setDate(String date) { this.date = date; }
	
	private String cookieString;
	public String getCookieString() { return cookieString; }
	public void setCookieString(String cookieString) { this.cookieString = cookieString; }
	
	private List<String> registerErrors = new ArrayList<String>();
	public List<String> getRegisterErrors() { return registerErrors; }
	public void setRegisterErrors(List<String> registerErrors) { this.registerErrors = registerErrors; }
	
	private viewUsers user = new viewUsers();
	public viewUsers getUser() { return user; }
	
	RequestsCreator requestCreator = new RequestsCreator();
	JsonChecker jsonChecker = new JsonChecker();
	GeneralService generalService = new GeneralService();
	UserEmailAddressesService userEmailsService = new UserEmailAddressesService(); 
	
	public  LoginService() { }
	
	
	public Boolean LoginUser() throws NullPointerException, NoSuchAlgorithmException, IOException
	{
		/*
		 * On this method I first check that no required fields are empty
		 * I encrypt the password to be sent using an MD5 hashing algorithm
		 * Then I create and open a connection with the CRUD API, the response contains:
		 * The user, if found; or a null result if no user was found for the entered email
		 * Then, I create a JsonNode object of the user and check if the passwords match
		 * If they do, I check whether a cookie needs to be set and if so, set it to last 3 months
		 * Finally, I set the session variable with the user that's logged in and return true
		 */
		
		//Check that email and password are not empty
		if(email == null || password == null) { return false; }

		//Converting to bytes and encrypting
		String encryptedPassword = encryptPassword();
		
		//Send request to CRUD
		String relURL = "users?action=getUserByEmail";
		String wholeResult = requestCreator.createPostRequest(urlPath, relURL, email);
		
		//Convert the result to a list of fields
		if(wholeResult == null) { return false; }
		ObjectMapper objectMapper = new ObjectMapper();
		
		try
		{
			user = objectMapper.readValue(wholeResult, viewUsers.class);
			//Compare passwords
			String userPassword = user.getLoginPassword();
			if (userPassword.equals(encryptedPassword)) { return true; }
			
			return false;
		} catch(Exception e) { e.printStackTrace(); return false; }
    }
	
	public Boolean RegisterUser() throws IOException, NoSuchAlgorithmException, AddressException
	{ 
		
		/*
		 * On this method, I check first of all that no fields are missing.
		 * Then, I validate the email address's format using jaxav.mail interface
		 * And check that both email addresses entered match
		 * Then I check that the user is over 18 by parsing the date entered
		 * I encrypt the password using an MD5 hashing algorithm and construct the JSON to be sent to the CRUD
		 * I send the request to register the user and get the Json of the new user back from the CRUD
		 * I set this to a session variable 
		 */
		
		//Check all required data is present
		checkNotNull();
		
		// Validate email and check they match
		emailsOK();

		// Check passwords are strong enough and match
		passwordsOK();

		//Check the user is over 18 years old
		isOverAged();

		//Checking everything is fine
		if(!registerErrors.isEmpty()) { return false; }
		//Converting to bytes and encrypting password
		String encryptedPassword = encryptPassword();
		
		//Login token for the user
		String loginToken = createToken();
		
		//Check Json is properly formed
		String userJsonString = createJson(encryptedPassword, loginToken);
		if(!jsonChecker.checkJson(userJsonString)) { return false; }
		
		//Send request and get result
		
		String relURL = "users?action=addUser";
		Integer responseUserId = Integer.parseInt(requestCreator.createPostRequest(urlPath, relURL, userJsonString));
		
		//Check if user was created and add the email to the table of user emails
		if(responseUserId <= 0) { registerErrors.add("userNotCreated"); return false; }
		userEmailsService.setUrlPath(urlPath);
		if(!userEmailsService.addEmailAddress(email)) {registerErrors.add("userNotCreated"); return false; }
		
		cookieString = userJsonString;
		LoginUser();
		return true;
	}
	
	//Used to encrypt the password
	
	private String encryptPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		//Converting to bytes and encrypting password
		MessageDigest md = MessageDigest.getInstance("MD5");
		String hashedPasswordHEX;
		byte[] bytesPassword;
		
		bytesPassword = password.getBytes("UTF-8");
		byte[] hashed = md.digest(bytesPassword);
		//Get MD5 string
		hashedPasswordHEX = generalService.bytesToHex(hashed);	
		return hashedPasswordHEX; 
	}
	
	private String createToken() throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] loginTokenBytes = md.digest(new String(email + password + date).getBytes());
		String loginToken = generalService.bytesToHex(loginTokenBytes);
		
		return loginToken;
	}
	
	private void checkNotNull()
	{
		if(name     == null || surname == null) 				{ System.out.println("Name null"); registerErrors.add("nameNotEntered"); }
		if(email    == null || repeatEmail == null) 			{ System.out.println("Email null"); registerErrors.add("emailsNotEntered"); } 
		if(password == null || repeatPassword == null) 			{ System.out.println("Password null"); registerErrors.add("passwordsNotEntered"); }
		if(day      == null || month == null || year == null) 	{ System.out.println("Date null"); registerErrors.add("dateNotEntered"); }
	}
	
	private void emailsOK() throws AddressException
	{
		// Declare email address for validation
		InternetAddress emailAddress = new InternetAddress(email);
		
		try { emailAddress.validate(); } 
		catch (AddressException e) { registerErrors.add("wrongEmailFormat"); }
		if(!email.equals(repeatEmail)) { registerErrors.add("emailsDontMatch"); }
	}
	
	private void passwordsOK()
	{
		// Define password requirements
		Pattern pattern;
		Matcher matcher;
		String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,20})";
		
		pattern = Pattern.compile(passwordPattern);
		matcher = pattern.matcher(password);
		
		if(!matcher.matches()) { registerErrors.add("passwordWrongFormatted"); }
		if(!password.equals(repeatPassword)) { registerErrors.add("passwordsDontMatch"); }
	}
	
	private void isOverAged()
	{
		//Convert numbers to date string
		date = year + "-" + month + "-" + day;
		
		//Check user is over 18
		try
		{
			LocalDate lDate = LocalDate.parse(date);
			LocalDate now = LocalDate.now();
			long yearsPassed = ChronoUnit.YEARS.between(lDate, now);
			if(yearsPassed < 18) { registerErrors.add("notOldEnough"); }
		} catch (DateTimeParseException parseEx) { registerErrors.add("invalidDate"); } 
	}
	
	private String createJson(String hashedPassword, String loginToken)
	{
		// TODO change this, use a userObject and set values with native methods
		String userJsonString = 
			"{"
			+ "\"countryId\": 484,"
			+ "\"preferredCurrencyId\": 58,"
			+ "\"preferredTimeZoneId\": 1,"
			+ "\"preferredLanguageId\": 1,"
			+ "\"name\": \"" + name + " " + surname + "\","
			+ "\"preferredEmail\": \"" + email + "\","
			+ "\"loginEmail\": \"" + email + "\","
			+ "\"loginPassword\": \"" + hashedPassword + "\","
			+ "\"loginToken\": \"" + loginToken + "\","
			+ "\"recoveringPassEmail\": \"" + email + "\","
			+ "\"doB\": \"" + date + "\","
			+ "\"recoveringPassEmail\": \"" + email + "\","
			+ "\"deleted\": false"
			+ "}";
		return userJsonString;
	}
	
	public void clearVariables(HttpServletRequest request)
	{
		String[] variablesToClear = new String[] 
				{ 
					"nameNotEntered", "emailsNotEntered", "passwordsNotEntered", "dateNotEntered",
					"wrongEmailFormat", "emailsDontMatch", "passwordWrongFormatted", "passwordsDontMatch",
					"notOldEnough", "invalidDate", "userNotCreated"
				};
		
		for(String variable : variablesToClear)
		{
			if(request.getAttribute(variable) != null) { request.removeAttribute(variable); }
		}
		
		registerErrors.clear();
	}
}