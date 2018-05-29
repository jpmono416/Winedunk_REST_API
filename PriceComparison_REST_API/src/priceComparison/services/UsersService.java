package priceComparison.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import priceComparison.models.tblUsers;

public class UsersService {    
	private String urlPath;
	public String getUrlPath() { return urlPath; }
	public void setUrlPath(String urlPath) { this.urlPath = urlPath; }

	RequestsCreator requestCreator = new RequestsCreator();
	
	public tblUsers getUserById(Integer userId)
    {
		if (userId > 0) {
		
			try
	    	{
	    		//Create request
	    		String relURL = "users?action=getUser&id=" + userId;
	    		String responseString = requestCreator.createGetRequest(urlPath, relURL);
			
	    		//Convert to object
				ObjectMapper mapper = new ObjectMapper();
				JsonNode responseJson = mapper.readTree(responseString);
				if(responseJson != null) { 
					return mapper.treeToValue(responseJson, tblUsers.class);
				} else {
					return null;
				}
				
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		return null;
	    	}
			
		} else {
			return null;
		}
    }
	
}
