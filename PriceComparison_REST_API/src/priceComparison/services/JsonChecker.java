package priceComparison.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonChecker {

	public JsonChecker() {}
	
	public Boolean checkJson(String jsonString)
	{
		try
		{
			JsonNode testJNode = new ObjectMapper().readTree(jsonString);
			if(testJNode == null) { return false; }
		} catch (Exception e) { e.printStackTrace(); return false; }
		
		return true;
	}
}
