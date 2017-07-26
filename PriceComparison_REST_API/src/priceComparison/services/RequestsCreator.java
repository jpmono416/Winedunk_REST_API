package priceComparison.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestsCreator extends EncodeURL {
	
	public String createPostRequest(String urlPath, String relURL, String content) throws IOException
	{
		//Create request
		String fullURL = urlPath + relURL;
		fullURL.replace(" ", "+");
		System.out.println("Full URL: " + fullURL);  //TODO DELETE
		URL obj = new URL(fullURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "text/plain");


		// Send post request
		con.setDoOutput(true);
		OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
		os.write(content);
		os.flush();
		os.close();
		
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer responseBuffer = new StringBuffer();

		while ((inputLine = in.readLine()) != null) { responseBuffer.append(inputLine); }
		in.close();
		
		return responseBuffer.toString();
	}
	
	public String createGetRequest(String urlPath, String relURL) throws IOException
	{
		//Create request
		String fullURL = new String(urlPath + relURL);
		fullURL = fullURL.replaceAll(" ", "+");
		System.out.println("Full URL: " + fullURL);  //TODO DELETE
		URL obj = new URL(fullURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				
		//add request header
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "text/plain");
		
		//Get result
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer responseBuffer = new StringBuffer();

		while ((inputLine = in.readLine()) != null) { responseBuffer.append(inputLine); }
		in.close();
		return responseBuffer.toString();
	}
}
