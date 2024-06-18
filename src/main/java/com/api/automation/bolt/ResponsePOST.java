package com.api.automation.bolt;
 
import com.api.automation.util.VerifyValueAPICommon;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
 
import java.io.IOException;
 
public class ResponsePOST {
	public static int statusCode;
	public static String responsePhrase;
	public static String JSONResponseString;
 
	public String testPostResponse(CloseableHttpResponse closeableHttpResponePOST, Object ExpResponseCode) {
	    statusCode = closeableHttpResponePOST.getStatusLine().getStatusCode();
	    responsePhrase = closeableHttpResponePOST.getStatusLine().getReasonPhrase();
	    VerifyValueAPICommon.verifyResponseCodeStatus(statusCode, ExpResponseCode);
 
        try {
            JSONResponseString = EntityUtils.toString(closeableHttpResponePOST.getEntity(), "UTF-8");
            if (JSONResponseString.isEmpty()) {
            	JSONResponseString = closeableHttpResponePOST.toString();
            }
        } catch(ParseException e) {
                    System.out.println(e.getMessage());
        } catch(IOException e) {
                    System.out.println(e.getMessage());
        }
        return JSONResponseString;
       
	}
}