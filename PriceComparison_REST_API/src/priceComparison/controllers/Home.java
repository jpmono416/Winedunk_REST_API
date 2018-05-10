package priceComparison.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import priceComparison.models.TblWinesGrapeVariety;
import priceComparison.models.TblWinesWineType;
import priceComparison.models.tblWines;
import priceComparison.models.viewBestOffersbyCountries;
import priceComparison.models.viewBestOffersbyMerchants;
import priceComparison.models.viewBestOffersbyWineTypes;
import priceComparison.models.viewCountriesWithBestOffers;
import priceComparison.models.viewMerchantsWithBestOffers;
import priceComparison.models.viewRecommendedWines;
import priceComparison.models.viewWineTypesWithBestOffers;
import priceComparison.services.EmailSender;
import priceComparison.services.GeneralService;
import priceComparison.services.HomeService;
import priceComparison.services.LoginService;
import priceComparison.services.RecoveringPasswordService;
import priceComparison.services.RequestsCreator;
import priceComparison.services.ResultsService;
import priceComparison.services.ValidationService;
import priceComparison.services.WinesService;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Home() { super(); }
	ResultsService resultsService = new ResultsService();
	HomeService homeService = new HomeService();
	LoginService loginService = new LoginService();
	ValidationService validationService = new ValidationService();
	GeneralService generalService = new GeneralService();
	RequestsCreator requestsCreator = new RequestsCreator();
	RecoveringPasswordService recoveringPassword = new RecoveringPasswordService(); 
	WinesService wineService = new WinesService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if (request.getParameter("JSESSIONID") != null) {
		    Cookie userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
		    response.addCookie(userCookie);
		} else {
		    String sessionId = session.getId();
		    Cookie userCookie = new Cookie("JSESSIONID", sessionId);
		    response.addCookie(userCookie);
		}
		
		RequestDispatcher homePage = request.getRequestDispatcher("WEB-INF/views/home.jsp");
		final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
		//system's path separator
		final String sep = System.getProperty("file.separator");
		//load winedunk configuration
		final Properties serviceProperties = new Properties();
		serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
		
		//Set URL path of CRUD API
		homeService.setCrudURL(serviceProperties.getProperty("crud.url"));
		generalService.setCrudURL(serviceProperties.getProperty("crud.url"));
		validationService.setUrlPath(serviceProperties.getProperty("crud.url"));
		wineService.setUrlPath(serviceProperties.getProperty("crud.url"));
		
		validationService.validateUser(request, response);
		
		try 
		{ 
			generalService.checkRecommended(request);
		} catch (Exception e) { e.printStackTrace(); }
		
		
		if(generalService.checkForRegistration(request, response) == true)
		{
			request.setAttribute("displayMessage", true);
			Cookie hasSeenMessage = new Cookie("nsu", "true");
			hasSeenMessage.setMaxAge(60*60*24*30); //1 month
			response.addCookie(hasSeenMessage); //Add cookie if the message prompting the user to register is displayed
		}
				
		try
		{
			List<viewMerchantsWithBestOffers> merchants = generalService.getMerchantsWithBestOffers();
			// Use listIterators to pass the objects byRef and make them remain modified when setting on session
			for(ListIterator<viewMerchantsWithBestOffers> i = (ListIterator<viewMerchantsWithBestOffers>) merchants.listIterator(); i.hasNext();)
			{
				viewMerchantsWithBestOffers merchant = i.next();
				Integer id = merchant.getId();
				
				List<viewBestOffersbyMerchants> bestOffers = generalService.getBestOffersByMerchant(id);
				if(bestOffers != null) { merchant.setBestOffers(bestOffers); }
			}
			session.setAttribute("merchantsWithOffers", merchants);
		} catch (Exception e) { e.printStackTrace(); }
		
		try
		{
			List<viewWineTypesWithBestOffers> wineTypes = generalService.getWineTypesWithBestOffers();
			for(ListIterator<viewWineTypesWithBestOffers> i = (ListIterator<viewWineTypesWithBestOffers>) wineTypes.listIterator(); i.hasNext();)
			{
				viewWineTypesWithBestOffers wineType = i.next();
				Integer id = wineType.getId();
				
				List<viewBestOffersbyWineTypes> bestOffers = generalService.getBestOffersByWineType(id);
				if(bestOffers != null) { wineType.setBestOffers(bestOffers); }
			}
			session.setAttribute("typesWithOffers", wineTypes);
		} catch (Exception e) { e.printStackTrace(); }
		
		try
		{
			List<viewCountriesWithBestOffers> countries = generalService.getCountriesWithBestoffers();
			for(ListIterator<viewCountriesWithBestOffers> i = (ListIterator<viewCountriesWithBestOffers>) countries.listIterator(); i.hasNext();)
			{
				viewCountriesWithBestOffers country = i.next();
				Integer id = country.getId();
				
				List<viewBestOffersbyCountries> bestOffers = generalService.getBestOffersByCountry(id);
				if(bestOffers != null) { country.setBestOffers(bestOffers); }
			}
			System.out.println("OBJECTS: " + countries); //TODO DELETE
			session.setAttribute("countriesWithOffers", countries);
		} catch (Exception e) { e.printStackTrace(); }
		homePage.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * This is executed when a form is submitted on the login page (either login, register or recover password)
		 * I first check which of the forms is being submitted and set the URL of the CRUD API
		 */
		
		HttpSession session = request.getSession();
		
		if (request.getParameter("JSESSIONID") != null) {
		    Cookie userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
		    response.addCookie(userCookie);
		} else {
		    String sessionId = session.getId();
		    Cookie userCookie = new Cookie("JSESSIONID", sessionId);
		    response.addCookie(userCookie);
		}
		
		
		RequestDispatcher loginPage = request.getRequestDispatcher("WEB-INF/views/login.jsp");
		RequestDispatcher homePage = request.getRequestDispatcher("WEB-INF/views/home.jsp");
		String hiddenParam = request.getParameter("formChosen");

		if(hiddenParam != null)
		{
			
			final String webInfPath = getServletContext().getRealPath("/WEB-INF/");
			//system's path separator
			final String sep = System.getProperty("file.separator");
			//load winedunk configuration
			final Properties serviceProperties = new Properties();
			serviceProperties.load(new FileInputStream(webInfPath+sep+"winedunk.properties"));
			
			switch(hiddenParam)
			{
				case "":
					return; //Avoid the form trying to be accessed by anything else other than the page
					
				case "recoverPasswordForm":
					System.out.println("Entered case"); // TODO DELETE
					recoveringPassword.setCrudURL(serviceProperties.getProperty("crud.url"));
					recoveringPassword.setUserEmail(request.getParameter("emailAddress"));
					System.out.println("Param:" + request.getParameter("emailAddress")); // TODO DELETE
					if(recoveringPassword.recoverPassword(getServletContext()))
					{
						session.setAttribute("passwordRecoverySuccessful", true);
						doGet(request, response);
					} else 
					{ 
						session.setAttribute("passwordRecoverySuccessful", false);
						doGet(request, response);
					} 
				break;
					
				case "loginForm":
					
					/* 
					 * If the login form is submitted, I set the values entered into the form
					 * To the login object's properties. Then I check whether to set a cookie or not
					 * Then, execute the login process and, if the result is successful, redirect the user
					 * If not, user comes back to login, where error messages will appear accordingly to the data entered 
					 */
					
					loginService.clearVariables(request);
					loginService.setEmail(request.getParameter("loginInputEmail"));
					loginService.setPassword(request.getParameter("loginInputPassword"));
					loginService.setUrlPath(serviceProperties.getProperty("crud.url"));
					
					String rememberMe = request.getParameter("chkRememberMe");
					if(rememberMe != null && rememberMe.equals("on")) { loginService.setRememberMe(true); }
					
					try 
					{
						if(!loginService.LoginUser())  
						{ 
							session.setAttribute("isLoggedIn", false);
							session.setAttribute("signUp", false);
							request.setAttribute("emailEntered", request.getParameter("loginInputEmail"));
							loginPage.forward(request, response);
						}
						else
						{
							if(loginService.getRememberMe() == true)
							{
								
								Cookie cookie = new Cookie("uvt", loginService.getUser().getLoginToken());
								Cookie messageCookie = new Cookie("nsu", "true");
								cookie.setMaxAge(60*60*24*90); //3 months
								messageCookie.setMaxAge(60*60*24*30); //1 month
								response.addCookie(cookie);
								response.addCookie(messageCookie);
							}
							
							session.setAttribute("userLoggedIn", loginService.getUser());
							session.setAttribute("isLoggedIn", true);
							doGet(request, response);
						}
					} catch (Exception e) { e.printStackTrace(); }
					break;
					
				case "registerForm":
					
					/*
					 * If the register form is submitted, I set the values entered into the form
					 * To the register object's properties. Then I execute the method to register the user
					 * If it is successful, I redirect to the thank you page, otherwise I send the user back to login page
					 * Error messages will appear then depending on the data entered.
					 */
					
					loginService.clearVariables(request);
					loginService.setUrlPath(serviceProperties.getProperty("crud.url"));
					
					if(!request.getParameter("inputName").equals("")) 			{ loginService.setName(request.getParameter("inputName")); 					}
					if(!request.getParameter("inputSurname").equals("")) 		{ loginService.setSurname(request.getParameter("inputSurname")); 			}
					if(!request.getParameter("registerInputEmail").equals("")) 	{ loginService.setEmail(request.getParameter("registerInputEmail")); 		}
					if(!request.getParameter("registerInputEmail2").equals("")) { loginService.setRepeatEmail(request.getParameter("registerInputEmail2")); }
					if(!request.getParameter("inputPassword").equals("")) 		{ loginService.setPassword(request.getParameter("inputPassword")); 			}
					if(!request.getParameter("inputPassword2").equals("")) 		{ loginService.setRepeatPassword(request.getParameter("inputPassword2")); 	}
					if(!request.getParameter("day").equals("")) 				{ loginService.setDay(request.getParameter("day")); 						}
					if(!request.getParameter("month").equals("")) 				{ loginService.setMonth(request.getParameter("month")); 					}
					if(!request.getParameter("year").equals("")) 				{ loginService.setYear(request.getParameter("year")); 						}
					
					try
					{
						//Check that the email doesn't already exist
						
						String result = requestsCreator.createPostRequest(serviceProperties.getProperty("crud.url"), "users?action=getUserByEmail", loginService.getEmail()); 
						if(result != null && !result.equals("null"))
						{
							request.getSession().setAttribute("signUp", true);
							for(String error : loginService.getRegisterErrors()) { request.setAttribute(error, true); }
							request.setAttribute("emailAlreadyExists", true);
							
							// Get a list with all the names of the fields
							String[] fields = new String[]
									{
											"inputName", "inputSurname", "registerInputEmail", "registerInputEmail2",
											"inputPassword", "inputPassword2", "day", "month", "year"
									};
							// Then set a variable for each one, to store what the user input was
							for(String field : fields) { request.setAttribute("previous"+field, request.getParameter(field)); }
							
							loginPage.forward(request, response); 
							return;
						}
						
						if(loginService.RegisterUser())
						{
							request.getSession().setAttribute("isLoggedIn", true);
							request.getSession().setAttribute("userLoggedIn", loginService.getUser());
							
							Cookie cookie = new Cookie("uvt", loginService.getUser().getLoginToken());
							Cookie messageCookie = new Cookie("nsu", "true");
							cookie.setMaxAge(60*60*24*90); //3 months
							messageCookie.setMaxAge(60*60*24*30); //1 month
							response.addCookie(cookie);
							response.addCookie(messageCookie);
							
							// loading email content from external HTML template
							String htmlContent = this.getRegistrationConfirmationHTMLEmailTemplate();
							// replacing name of user
							htmlContent = htmlContent.replace("[[NAME-OF-USER]]", loginService.getUser().getName());
							
							// Adding current recommended wines into email
							String htmlRecommendedWines = getRecommendedWinesHTMLFormatted(request);
							if ( (htmlRecommendedWines != null) && (!htmlRecommendedWines.equals(""))) {
								
								// having recommended wines - setting both recommended header and content
								htmlContent = htmlContent.replace("[[RECOMMENDED-WINES-HEADER]]", "or take a look at below recommended wines:");
								htmlContent = htmlContent.replace("[[RECOMMENDED-PRODUCTS]]", htmlRecommendedWines);
							} else {
								
								// having no recommended wines - removing both header and content for recommended wines
								htmlContent = htmlContent.replace("[[RECOMMENDED-WINES-HEADER]]", "");
								htmlContent = htmlContent.replace("[[RECOMMENDED-PRODUCTS]]", "");
							}
							
							// sending "recover password" email
							EmailSender emailSender = new EmailSender();

							// emailSender.set
							emailSender.setReceiverEmailAddress(loginService.getUser().getEmail());
							emailSender.setEmailSubject("Welcome to Winedunk");

							emailSender.setEmailText(htmlContent);
							emailSender.send();
							
							RequestDispatcher thankYouPage = request.getRequestDispatcher("WEB-INF/views/thankYou.jsp");
					    	thankYouPage.forward(request, response);
						}
						else 
						{
							request.getSession().setAttribute("signUp", true);
							for(String error : loginService.getRegisterErrors()) { request.setAttribute(error, true); }
							
							// Get a list with all the names of the fields
							String[] fields = new String[]
								{
										"inputName", "inputSurname", "registerInputEmail", "registerInputEmail2",
										"inputPassword", "inputPassword2", "day", "month", "year"
								};
							
							// Then set a variable for each one, to store what the user input was
							for(String field : fields) { request.setAttribute("previous"+field, request.getParameter(field)); }
							
							loginPage.forward(request, response); 
						}
					} catch (Exception e) { e.printStackTrace(); }
				break;
			}
		}
	}

	
	private String replaceLast(String string, String substring, String replacement)
	{
	  int index = string.lastIndexOf(substring);
	  if (index == -1) {
		  return string;
	  } else {
		  return string.substring(0, index) + replacement
		          + string.substring(index+substring.length());
	  }
	}

	private String getRecommendedWinesHTMLFormatted(HttpServletRequest request) {
		String RecommendedWineRowTemplate = getRecommendedWineRowTemplate();
		
		if ( (RecommendedWineRowTemplate != null) && (!RecommendedWineRowTemplate.equals("")) ) {
		
			try {
				List<viewRecommendedWines> recommendedWines = homeService.loadRecommendedWines();
				
				String recommendedWinesStr = "";
				tblWines wine;
				String wineTypename;
				String wineGrapeName;
				
				for (viewRecommendedWines recommendedWine : recommendedWines) { 
					
					wine = wineService.getWineById(recommendedWine.getId());
					if (wine != null) {
						
						// getting domain name, it is needed in order to make it working properly in both UAT and PROD without doing any change
						
						String baseDomainURL = getBaseDomainURLBasedOnAppURL(request.getRequestURL().toString());
						
						try {
							wineTypename = wine.getTblWinesWineType().get(0).getTblWineTypes().getName();
						} catch (Exception e) {
							wineTypename = "";
						}
						
						try {
							wineGrapeName = wine.getTblWinesGrapeVariety().get(0).getGrapeVariety().getName();
						} catch (Exception e) {
							wineGrapeName = "";
						}
						recommendedWinesStr = recommendedWinesStr.concat(RecommendedWineRowTemplate
				    			.replace("[[PRODUCT-IMG-URL]]", recommendedWine.getImageURL())
						    	.replace("[[PRODUCT-NAME]]", recommendedWine.getName())
						    	.replace("[[PRODUCT-DESC]]", recommendedWine.getShortDescription())
						    	.replace("[[PRODUCT-TYPE]]", wineTypename)
						    	.replace("[[PRODUCT-SAVE]]", recommendedWine.getSaving().toString())
						    	.replace("[[PRODUCT-MORE-URL]]", baseDomainURL.concat("Share?chosenColour=").concat(wine.getColour().getId().toString()).concat("&chosenType=").concat(wine.getTblWinesWineType().get(0).getTblWineTypes().getId().toString()))
						    	.replace("[[PRODUCT-COLOR]]", wine.getColour().getName())
						    	.replace("[[PRODUCT-WAS]]", recommendedWine.getPreviousMaxPrice().toString())
						    	.replace("[[PRODUCT-PAGE-URL]]", baseDomainURL.concat("Product?id=").concat(recommendedWine.getId().toString()))
						    	.replace("[[PRODUCT-GRAPE]]", wineGrapeName)
						    	.replace("[[PRODUCT--NOW]]", recommendedWine.getMinimumPrice().toString())
						    	.replace("[[PRODUCT-DEEPLINK-URL]]", recommendedWine.getMinimumPriceClicktag()));
						
					} else {
						recommendedWinesStr = "";
					}
						
				}
				
				return recommendedWinesStr;
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
			
		} else {
			return "";
		}
	}

	private String getBaseDomainURLBasedOnAppURL(String appURL) {

		if (appURL.toLowerCase().contains("uat") ) {
			return "http://uat.winedunk.com/";
		} else {
			return "http://winedunk.com/";
		}
	}


	private String getRecommendedWineRowTemplate() {

		ServletContext context = getServletContext();
		String templateFile = context.getRealPath("/WEB-INF/HTML-Email-Templates/Recommended-Wine-Row.html");
		Charset encoding = Charset.defaultCharset();
		byte[] encodedByteArray;
		try {
			encodedByteArray = Files.readAllBytes(Paths.get(templateFile));
			return new String(encodedByteArray, encoding);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}


	private String getRegistrationConfirmationHTMLEmailTemplate() {

		ServletContext context = getServletContext();
		String templateFile = context.getRealPath("/WEB-INF/HTML-Email-Templates/Registration-confirmation.html");
		Charset encoding = Charset.defaultCharset();
		byte[] encodedByteArray;
		try {
			encodedByteArray = Files.readAllBytes(Paths.get(templateFile));
			return new String(encodedByteArray, encoding);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
}