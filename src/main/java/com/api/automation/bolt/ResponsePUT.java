package com.api.automation.bolt;
 
import com.api.automation.util.VerifyValueAPICommon;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
 
import java.io.IOException;
 
public class ResponsePUT {
	public static int statusCode;
	public static String responsePhrase;
	public static String JSONResponseString;
 
    public String testPutResponse(CloseableHttpResponse closeableHttpResponePUT, Object ExpResponseCode) {
		statusCode = closeableHttpResponePUT.getStatusLine().getStatusCode();
		responsePhrase = closeableHttpResponePUT.getStatusLine().getReasonPhrase();
		VerifyValueAPICommon.verifyResponseCodeStatus(statusCode, Integer.parseInt((String) ExpResponseCode));
 
        try {
            JSONResponseString = EntityUtils.toString(closeableHttpResponePUT.getEntity(), "UTF-8");
            if (JSONResponseString.isEmpty()) {
                        JSONResponseString = closeableHttpResponePUT.toString();
            }
        } catch(ParseException e) {
                    System.out.println(e.getMessage());
        } catch(IOException e) {
                    System.out.println(e.getMessage());
        }
        
        return JSONResponseString;
    }
}