package com.api.automation.bolt;
 
import com.api.automation.util.VerifyValueAPICommon;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
 
import java.io.IOException;
 
public class ResponseGET {
	public static int statusCode;
	public static String responsePhrase;
	public static String JSONResponseString;
 
    public String testGetResponse(CloseableHttpResponse closeableHttpResponeGET, Object ExpResponseCode) {
    	responsePhrase = closeableHttpResponeGET.getStatusLine().getReasonPhrase();
    	statusCode = closeableHttpResponeGET.getStatusLine().getStatusCode();
    	VerifyValueAPICommon.verifyResponseCodeStatus(statusCode, Integer.parseInt((String) ExpResponseCode));
 
		try {
				JSONResponseString = EntityUtils.toString(closeableHttpResponeGET.getEntity(), "UTF-8");
				if (JSONResponseString.isEmpty()) {
					JSONResponseString = closeableHttpResponeGET.toString();
				}
	        } catch(ParseException e) {
	        	System.out.println(e.getMessage());
	        } catch(IOException e) {
	        	System.out.println(e.getMessage());
	        }
        return JSONResponseString;
    }
}