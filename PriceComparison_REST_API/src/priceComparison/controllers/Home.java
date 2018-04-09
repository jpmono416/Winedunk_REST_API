package priceComparison.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
					if(recoveringPassword.recoverPassword())
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
							
							// @var content is the thank you email sent to the user once they register 
							//TODO get this variable from an online file
							String content = "<HTML xmlns=\"http://www.w3.org/1999/xhtml\"><HEAD><META content=\"text/html; charset=utf-8\" http-equiv=Content-Type><META name=viewport content=\"width=device-width, initial-scale=1.0\"><STYLE type=text/css> .ExternalClass{WIDTH: 100%; LINE-HEIGHT: 100%}.defaultStyle *{LINE-HEIGHT: 100%}.defaultStyle P{LINE-HEIGHT: 100%}.defaultStyle SPAN{LINE-HEIGHT: 100%}.defaultStyle FONT{LINE-HEIGHT: 100%}.defaultStyle TD{LINE-HEIGHT: 100%}.defaultStyle DIV{LINE-HEIGHT: 100%}.defaultStyle H1 A{TEXT-DECORATION: none; COLOR: #800000 !important}.defaultStyle H1 A:link{COLOR: #800000 !important}.defaultStyle H1 A:visited{COLOR: #800000 !important}.defaultStyle .button A{TEXT-DECORATION: none !important; COLOR: #f9f9fb !important; BACKGROUND-COLOR: #ec2c22}.defaultStyle .button A:link{TEXT-DECORATION: none !important; COLOR: #f9f9fb !important; BACKGROUND-COLOR: #ec2c22}.defaultStyle .button A:link{COLOR: #f9f9fb !important; BACKGROUND-COLOR: #ec2c22}.defaultStyle .button A:visited{COLOR: #f9f9fb !important; BACKGROUND-COLOR: #ec2c22}.defaultStyle .secondary-button A{TEXT-DECORATION: none !important; COLOR: #800000 !important; PADDING-BOTTOM: 10px !important; PADDING-TOP: 10px !important; PADDING-LEFT: 10px !important; PADDING-RIGHT: 10px !important}.defaultStyle .secondary-button A:link{TEXT-DECORATION: none !important; COLOR: #800000 !important; PADDING-BOTTOM: 10px !important; PADDING-TOP: 10px !important; PADDING-LEFT: 10px !important; PADDING-RIGHT: 10px !important}.defaultStyle .secondary-button A:visited{TEXT-DECORATION: none !important; COLOR: #800000 !important; PADDING-BOTTOM: 10px !important; PADDING-TOP: 10px !important; PADDING-LEFT: 10px !important; PADDING-RIGHT: 10px !important}.defaultStyle P.paragraph A{TEXT-DECORATION: underline !important; COLOR: #800000 !important}.defaultStyle P.paragraph A:link{TEXT-DECORATION: underline !important; COLOR: #800000 !important}.defaultStyle P.paragraph A:visited{COLOR: #26727f !important}.defaultStyle P.small A{TEXT-DECORATION: underline !important; COLOR: #800000 !important}.defaultStyle P.small A:link{TEXT-DECORATION: underline !important; COLOR: #800000 !important}.defaultStyle P.small A:visited{COLOR: #800000 !important}.defaultStyle TABLE{BORDER-COLLAPSE: collapse !important}.defaultStyle TABLE TD{BORDER-COLLAPSE: collapse !important}TABLE{BORDER-COLLAPSE: collapse !important; mso-table-lspace: 0pt; mso-table-rspace: 0pt}TABLE TD{BORDER-COLLAPSE: collapse !important}P{-webkit-margin-before: 0; -webkit-margin-after: 0}H1 A:link{COLOR: #191d1d !important}.button A:link{TEXT-DECORATION: none; COLOR: #f9f9fb !important}.secondary-button A:link{COLOR: #800000}.starter-image{border-radius: 50%}.footer-hide-link A{TEXT-DECORATION: none !important; COLOR: #9b9f9f !important}.responsive-full-width{WIDTH: 480px}*[class~='narrative-content'] IMG{HEIGHT: auto !important; WIDTH: 100% !important}@media Unknown{*[class='responsive-full-width']{WIDTH: 320px !important}*[class~='responsive-content-width']{MAX-WIDTH: 300px !important; WIDTH: 300px !important}*[class~='button-line']{WIDTH: 0px !important}*[class~='button-line'] > tablef{WIDTH: 0px !important}*[class='align-right']{FLOAT: none !important; MARGIN: auto}TABLE[class='featured-image']{WIDTH: 270px !important}TABLE[class='featured-image'] TD{HEIGHT: auto !important; WIDTH: 270px !important}TABLE[class='featured-image'] IMG{HEIGHT: auto !important; WIDTH: 270px !important}IMG[class~='featured-full-image']{HEIGHT: auto !important; WIDTH: 298px !important}*[class~='responsive-hide']{DISPLAY: none !important}DIV[class~='phone-show']{DISPLAY: block !important}DIV[class~='phone-show'] *{DISPLAY: block !important}H1{FONT-SIZE: 24px; LINE-HEIGHT: 27px}TABLE[class~='secondary-button'] TR TD DIV[class~='secondary-button']{TEXT-DECORATION: none !important; COLOR: #f9f9fb !important; BACKGROUND-COLOR: #ec2c22}TABLE[class~='secondary-button'] TR TD DIV[class~='secondary-button'] A{FONT-SIZE: 16px !important; TEXT-DECORATION: none !important; FONT-WEIGHT: bold !important; COLOR: #f9f9fb !important; PADDING-BOTTOM: 10px !important; PADDING-TOP: 10px !important; PADDING-LEFT: 0px !important; DISPLAY: block !important; PADDING-RIGHT: 0px !important; BACKGROUND-COLOR: #ec2c22}TABLE[class~='secondary-button'] TR TD DIV[class~='secondary-button'] A:link{COLOR: #f9f9fb !important}TABLE[class~='secondary-button'] TR TD DIV[class~='secondary-button'] A:visited{COLOR: #f9f9fb !important}*[class='responsive-full-width']{WIDTH: 320px !important}*[class~='responsive-content-width']{MAX-WIDTH: 300px !important; WIDTH: 300px !important}*[class~='button-line']{WIDTH: 0px !important}*[class~='button-line'] > tablef{WIDTH: 0px !important}*[class='align-right']{FLOAT: none !important; MARGIN: auto}TABLE[class='featured-image']{WIDTH: 270px !important}TABLE[class='featured-image'] TD{HEIGHT: auto !important; WIDTH: 270px !important}TABLE[class='featured-image'] IMG{HEIGHT: auto !important; WIDTH: 270px !important}IMG[class~='featured-full-image']{HEIGHT: auto !important; WIDTH: 298px !important}*[class~='responsive-hide']{DISPLAY: none !important}DIV[class~='phone-show']{DISPLAY: block !important}DIV[class~='phone-show'] *{DISPLAY: block !important}H1{FONT-SIZE: 24px; LINE-HEIGHT: 27px}TABLE[class~='secondary-button'] TR TD DIV[class~='secondary-button']{TEXT-DECORATION: none !important; COLOR: #f9f9fb !important; BACKGROUND-COLOR: #ec2c22}TABLE[class~='secondary-button'] TR TD DIV[class~='secondary-button'] A{FONT-SIZE: 16px !important; TEXT-DECORATION: none !important; FONT-WEIGHT: bold !important; COLOR: #f9f9fb !important; PADDING-BOTTOM: 10px !important; PADDING-TOP: 10px !important; PADDING-LEFT: 0px !important; DISPLAY: block !important; PADDING-RIGHT: 0px !important; BACKGROUND-COLOR: #ec2c22}TABLE[class~='secondary-button'] TR TD DIV[class~='secondary-button'] A:link{COLOR: #f9f9fb !important}TABLE[class~='secondary-button'] TR TD DIV[class~='secondary-button'] A:visited{COLOR: #f9f9fb !important}}</STYLE></HEAD><BODY style=\"HEIGHT: 100%; WIDTH: 100% !important; PADDING-BOTTOM: 0px; -MS-TEXT-SIZE-ADJUST: 100%; PADDING-TOP: 0px; PADDING-LEFT: 0px; MARGIN: 0px; PADDING-RIGHT: 0px; BACKGROUND-COLOR: #e7e7e7; -webkit-text-size-adjust: none; -webkit-font-smoothing: antialiased\" bgColor=#f9f9fb width=\"100%\" align=\"center\"><TABLE style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 align=center border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\"><TABLE class=responsive-full-width style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 width=480 align=center border=0><TBODY><TR><TD class=body-wrap style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; PADDING-BOTTOM: 0px; PADDING-TOP: 0px; PADDING-LEFT: 10px; PADDING-RIGHT: 10px; BORDER-TOP-WIDTH: 0px; BACKGROUND-COLOR: #f9f9fb\" bgColor=#f9f9fb><TABLE class=responsive-content-width style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 align=center border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\" align=left><TABLE class=responsive-content-width style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 width=\"100%\" align=left border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\"><TABLE style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 align=left border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\"><IMG class=\"logo block\" style=\"TEXT-DECORATION: none; MAX-WIDTH: 100% !important; HEIGHT: 55px; WIDTH: 200px !important; OUTLINE-STYLE: none; DISPLAY: block; -MS-INTERPOLATION-MODE: bicubic; margin-bottom:15px;\" alt=Winedunk.com src=\"https://cqarnq.db.files.1drv.com/y4mYIs1kxy9xCyFv0zapdmmVNn3eav-h1LdunXEGQZRfK-gVu7BbhPo4pVBl295KyBjGqTxuctn_b79O0AbkpQdz0VKl87AQK8w2cHOS3YUzF2sDJAlJtTwblJ12U7lG-vyjZY86d1r2U5MqM7-ARURh1Qmn540jd48awT6I12M0CA8Dvd1H9rslPSNpBt2VllzUqjGcjPgwezzfLFkNKenfg?width=170&height=50&cropmode=none\" width=120 height=24></TD></TR></TBODY></TABLE></TD></TR><TR><TD class=\"rule responsive-content-width\" style=\"BORDER-LEFT-WIDTH: 0px; FONT-SIZE: 1px; HEIGHT: 1px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; PADDING-BOTTOM: 0px; PADDING-TOP: 0px; PADDING-LEFT: 0px; MARGIN: 0px; LINE-HEIGHT: 1px; PADDING-RIGHT: 0px; BORDER-TOP-WIDTH: 0px; BACKGROUND-COLOR: #e1e5e5\" bgColor=#e6e6e6 height=1 width=\"100%\" align=left></TD></TR></TBODY></TABLE></TD></TR><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\"><TABLE style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\" align=left><DIV class=narrative-content style=\"FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif\"><P style=\"FONT-SIZE: 15px; MARGIN-BOTTOM: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; MARGIN-TOP: 15px; FONT-WEIGHT: normal; COLOR: #800000; LINE-HEIGHT: 20px; mso-line-height-rule: exactly\"><STRONG>[[NAME OF USER]]</STRONG></P><P style=\"FONT-SIZE: 15px; MARGIN-BOTTOM: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; MARGIN-TOP: 15px; FONT-WEIGHT: normal; COLOR: #4b4f4f; LINE-HEIGHT: 20px; mso-line-height-rule: exactly\">Thanks for joining our community of wine lovers! I hope you already got started and used our powerful search functionality to find your favourite wine at the best price or possibly found a new wine that you want to try. We will continuously build on our offering to ensure that finding the perfect wine should be easy and fun as well as reassuring that you pay the best possible price for your wine.</P><BR></DIV></TD></TR></TBODY></TABLE></TD></TR><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\"><TABLE class=\"letter-canvas responsive-content-width\" style=\"BORDER-TOP: #e1e5e5 1px solid; BORDER-RIGHT: #e1e5e5 1px solid; BORDER-COLLAPSE: collapse !important; BORDER-BOTTOM: #e1e5e5 1px solid; BORDER-LEFT: #e1e5e5 1px solid; BACKGROUND-COLOR: white; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=15 width=\"100%\" bgColor=#ffffff border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\" align=center><TABLE class=responsive-content-width-inside style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 align=center bgColor=#ffffff border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\" colSpan=3><TABLE class=featured-image style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; WIDTH: 100%; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 align=center border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\" align=center cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><A style=\"WIDTH: 100%; COLOR: #800000; DISPLAY: block\" href=\"http://www.winedunk.com\"><IMG class=block style=\"BORDER-TOP-STYLE: none; TEXT-DECORATION: none; MAX-WIDTH: 100% !important; BORDER-LEFT-STYLE: none; HEIGHT: 253px; WIDTH: 450px; BORDER-BOTTOM-STYLE: none; BORDER-RIGHT-STYLE: none; OUTLINE-STYLE: none; DISPLAY: block; -MS-INTERPOLATION-MODE: bicubic\" src=\"https://b6ytqg.db.files.1drv.com/y4mQPLrofLc_-tmtWbVIoN0rVp_37gplxOkx1087hSiE4Z0cPX5SSXl84_yFtfl9TaWiLw80xVT2AiH2xKuiKgfG_PoflDR2RIzKw3c7Iv-taCYwHAC6HZC5WOzoLJKPq5C2ovTCyyF6zcIY3GiHxY3eYEa6RqpOmO5qNpukLmP4GD9CDtKns2IUrwWXlLgCQZ7vh7u5eVduqkqT5R3BAgAmg?width=8736&height=5824&cropmode=none\" width=450 height=253></A></TD></TR></TBODY></TABLE><BR><TABLE class=responsive-content-width-inside style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 width=\"100%\" border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\" align=left><H1 style=\"FONT-SIZE: 21px; TEXT-DECORATION: none; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; FONT-WEIGHT: normal; COLOR: #191d1d; PADDING-BOTTOM: 0px; PADDING-TOP: 0px; PADDING-LEFT: 0px; MARGIN: 0px; LINE-HEIGHT: 26px; PADDING-RIGHT: 0px\"><A style=\"TEXT-DECORATION: none; COLOR: #191d1d !important\" href=\"http://www.facebook.com/winedunk\">Follow us</A></H1></TD></TR></TBODY></TABLE><BR><TABLE class=responsive-content-width-inside style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 width=\"100%\" border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\" align=left><TABLE style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 width=\"100%\" border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\"><SPAN class=\"type-small type-muted\" style=\"FONT-SIZE: 14px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; COLOR: #737777; LINE-HEIGHT: 17px\">What�s next? Why don�t you check out our <A style=\"TEXT-DECORATION: underline !important; COLOR: #800000 !important\" href=\"https://blog.winedunk.com/\">Blog</A> or follow us on <A style=\"TEXT-DECORATION: underline !important; COLOR: #800000 !important\" href=\"https://www.facebook.com/winedunk\">Facebook</A>, <A style=\"TEXT-DECORATION: underline !important; COLOR: #800000 !important\" href=\"https://www.twitter.com/winedunk\">Twitter</A> and <A style=\"TEXT-DECORATION: underline !important; COLOR: #800000 !important\" href=\"https://www.instagram.com/winedunk\">Instagram</A>!</SPAN></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><TABLE style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" width=\"100%\"><TBODY><TR><TD class=spacer style=\"BORDER-LEFT-WIDTH: 0px; FONT-SIZE: 1px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; PADDING-BOTTOM: 0px; PADDING-TOP: 0px; PADDING-LEFT: 0px; LINE-HEIGHT: 1px !important; PADDING-RIGHT: 0px; BORDER-TOP-WIDTH: 0px\"><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR></TD></TR></TBODY></TABLE><TABLE class=responsive-content-width-inside style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; WIDTH: 100%; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 width=\"100%\" align=center border=0><TBODY><TR><TD class=\"button-line responsive-hide\" style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\" width=\"20%\"></TD><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\"><TABLE class=button style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 width=\"100%\" align=center border=0 color=\"#FFFFFF\"><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; DISPLAY: block; BORDER-TOP-WIDTH: 0px; BACKGROUND-COLOR: #800000; border-radius: 2px; -webkit-border-radius: 2px; -moz-border-radius: 2px\" bgColor=#800000 align=center cellspacing=\"0\" cellpadding=\"0\" border=\"0\" color=\"#FFFFFF\"><DIV class=button style=\"FONT-SIZE: 16px; TEXT-DECORATION: none; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; MIN-WIDTH: 200px; COLOR: #e7e7e7 !important; TEXT-ALIGN: center; LINE-HEIGHT: 18px\" align=center><A style=\"FONT-SIZE: 16px; TEXT-DECORATION: none !important; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; FONT-WEIGHT: bold !important; COLOR: #e7e7e7 !important; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; PADDING-LEFT: 10px; DISPLAY: block; LINE-HEIGHT: 18px; PADDING-RIGHT: 10px; BACKGROUND-COLOR: #800000; border-radius: 2px; -webkit-border-radius: 2px; -moz-border-radius: 2px\" href=\"http://www.winedunk.com/Login\" alt=\"Winedunk\">Go to winedunk</A></DIV></TD></TR></TBODY></TABLE></TD><TD class=\"button-line responsive-hide\" style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\" width=\"20%\"></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><TABLE style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; WIDTH: 100%; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 border=0><TBODY><TR><TD class=letter-appendix style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\" align=center><TABLE class=responsive-content-width style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" width=\"100%\"></TABLE><TABLE class=\"responsive-content-width secondary-button\" style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; PADDING-BOTTOM: 0px; PADDING-TOP: 0px; PADDING-LEFT: 0px; BORDER-SPACING: 0; PADDING-RIGHT: 0px; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 align=center border=0><BR><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; DISPLAY: block; BORDER-TOP-WIDTH: 0px; border-radius: 2px; -webkit-border-radius: 2px; -moz-border-radius: 2px\" align=center><DIV class=secondary-button style=\"FONT-SIZE: 15px; TEXT-DECORATION: underline; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; COLOR: #800000; LINE-HEIGHT: 18px; border-radius: 2px; -webkit-border-radius: 2px; -moz-border-radius: 2px\"><A style=\"FONT-SIZE: 15px; TEXT-DECORATION: underline; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; FONT-WEIGHT: bold; COLOR: #800000; DISPLAY: block; LINE-HEIGHT: 18px; border-radius: 2px; -webkit-border-radius: 2px; -moz-border-radius: 2px; margin-bottom: 25px\" href=\"http://www.winedunk.com/Home\">Check our recommended wines </A></DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR><BR><TR><TD class=\"rule responsive-content-width\" style=\"BORDER-LEFT-WIDTH: 0px; FONT-SIZE: 1px; HEIGHT: 1px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; PADDING-BOTTOM: 0px; PADDING-TOP: 0px; PADDING-LEFT: 0px; MARGIN: 0px; LINE-HEIGHT: 1px; PADDING-RIGHT: 0px; BORDER-TOP-WIDTH: 0px; BACKGROUND-COLOR: #e1e5e5\" bgColor=#e6e6e6 height=1 width=\"100%\" align=left></TD></TR></TR><TR><TD class=footer style=\"BORDER-LEFT-WIDTH: 0px; MAX-WIDTH: 420px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\"><TABLE class=responsive-content-width style=\"BORDER-LEFT-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 align=center border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\" align=center><TABLE class=\"responsive-content-width type-tiny type-muted\" style=\"BORDER-LEFT-WIDTH: 0px; FONT-SIZE: 12px; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; COLOR: #737777; LINE-HEIGHT: 15px; BORDER-TOP-WIDTH: 0px; mso-table-lspace: 0pt; mso-table-rspace: 0pt\" cellSpacing=0 cellPadding=0 border=0><TBODY><TR><TD style=\"BORDER-LEFT-WIDTH: 0px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; BORDER-RIGHT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-COLLAPSE: collapse !important; BORDER-TOP-WIDTH: 0px\" align=left><P class=\"large type-face-din\" style=\"FONT-SIZE: 15px; FONT-FAMILY: Helvetica Neue, Helvetica, sans-serif; FONT-WEIGHT: bold; COLOR: #737777; MARGIN: 15px 0px 25px; LINE-HEIGHT: 1.5em; mso-line-height-rule: exactly\" align=center color=\"#e7e7e7\">Start comparing prices of over 9000 wines! <BR><A style=\"TEXT-DECORATION: underline !important; COLOR: #800000 !important\" href=\"http://winedunk.com/Results\">Browse wines now</A></P><HR style=\"BORDER-TOP-STYLE: none; BORDER-LEFT-STYLE: none; HEIGHT: 1px; BORDER-BOTTOM-STYLE: none; BORDER-RIGHT-STYLE: none; BACKGROUND-COLOR: #e1e5e5\"><P class=small style=\"FONT-SIZE: 13px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; FONT-WEIGHT: normal; COLOR: #9b9f9f; MARGIN: 0px 0px 1em; LINE-HEIGHT: 1.5em; mso-line-height-rule: exactly\">No longer want to receive our emails? Unsubscribe me from all winedunk emails \r\n" + 
									"\r\n" + 
									"If you've got a question about winedunk, or want to get in touch, visit <A style=\"TEXT-DECORATION: underline !important; COLOR: #800000 !important\" href=\"http://www.winedunk.com/AboutUs\">our site</A>. Copyright � 2016-2017 winedunk Limited �. All rights reserved.</P><P class=small style=\"FONT-SIZE: 13px; FONT-FAMILY: 'Helvetica Neue', Helvetica, sans-serif; FONT-WEIGHT: normal; COLOR: #9b9f9f; MARGIN: 0px 0px 1em; LINE-HEIGHT: 1.5em; mso-line-height-rule: exactly\"><A style=\"TEXT-DECORATION: underline !important; COLOR: #800000 !important\" href=\"http://www.winedunk.com/AdvertiseWithUs\">Contact us</A> � <A style=\"TEXT-DECORATION: underline !important; COLOR: #800000 !important\" href=\"http://www.winedunk.com/PrivacyPolicy\">Privacy policy</A><BR><STRONG class=footer-hide-link>Winedunk.com</STRONG>� 24 Colby Road, London � SE19 1HA � United Kingdom </P><BR></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></BODY></HTML>";
							content = content.replace("[[NAME OF USER]]", loginService.getUser().getName());
							
							
							// sending "recover password" email
							EmailSender emailSender = new EmailSender();

							// emailSender.set
							emailSender.setReceiverEmailAddress(loginService.getUser().getEmail());
							emailSender.setEmailSubject("Welcome to Winedunk");

							emailSender.setEmailText(content);
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
}