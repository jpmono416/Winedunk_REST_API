package priceComparison.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;

public class EncodeURL {

	private String charset = "UTF-8";
	
	public String encodeURL(String url) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(url, charset); 
	}
	
	public String decodeURL(String url) throws UnsupportedEncodingException
	{
		return URLDecoder.decode(url, charset);
	}
}
